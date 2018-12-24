package com.dmzl.sentiment.core.task;

import com.alibaba.fastjson.JSON;
import com.dmzl.sentiment.core.es.IndicesManager;
import com.dmzl.sentiment.entity.Search;
import com.dmzl.sentiment.mapper.SentimentMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
* @Description:    定时把舆情的数据存储到Elasticsearch
 * 这里是开启线程池执行任务,考虑到10分钟舆情条数都很少,线程池核心线程初始化为2
* @Author:         moon
* @CreateDate:     2018/12/18 0018 16:09
* @UpdateUser:     moon
* @UpdateDate:     2018/12/18 0018 16:09
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Component
public class SentimentToElasticsearchTimer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SentimentMapper sentimentMapper;

    @Autowired
    private IndicesManager indicesManager;

    @Value("${elasticsearch.indexName}")
    private String indexName;
    @Value("${elasticsearch.indexType}")
    private String indexType;
    /**
     * 指定线程池大小队列为1000
     * 核心线程2
     * 最大线程20
     * 自定义线程名称,方便排查问题
     * 指定线程异常策略为抛出异常
     */
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 20, 5, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000), new NameableThreadFactory("elasticsearch-write-thread"), new ThreadPoolExecutor.AbortPolicy());

    /**
    * @Description:     初始化数据定时任务注释
    * @Author:         moon
    * @CreateDate:     2018/12/21 0021 15:05
    * @UpdateUser:     moon
    * @UpdateDate:     2018/12/21 0021 15:05
    * @UpdateRemark:   修改内容 @Scheduled(cron = "0 56 14 * * * ")
    * @Version:        1.0
    */
    public void initEsDataBase() {
        try {
            logger.info("======重新初始化索引======");
            try {
                indicesManager.deleteIndex(indexName);
            }catch (Exception e){
                logger.error("{}",e);
            }
            indicesManager.createMapping(indexName, indexType);
            insetEsDatabase();
        } catch (Exception e) {
            logger.error("重新构建索引失败!{}", e);
        }
    }

    /**
     * 数据每隔1一小时查询数据库新增的记录往es里面写
     * 但是需要和舆情抓取定时任务隔开,保证正确解析当前时间之前的数据存储到es
     */
    @Scheduled(cron = "0 30 * * * * ")
    public void search2Es() {
        logger.info("======定时任务start======");
        insetEsDatabase();
    }

    /**
     *
     */
    private void insetEsDatabase() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<String> ids = sentimentMapper.getUnMarked();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        logger.info("有{}条数据可以往es里面存储", ids.size());
        try {

            int pageSize = 50;
            int pages = (int) Math.ceil((float) ids.size() / pageSize);
            int startPosition = 0;
            for (int i = 0; i < pages; i++) {
                logger.info("分配线程{}开始执行.....", i);
                startPosition = pageSize * i;
                Integer endPosition=startPosition + pageSize;
                if(endPosition>ids.size()){
                    endPosition=ids.size();
                }
                List<String> subIds = ids.subList(startPosition, endPosition);
                executor.execute(new WriteToeLasticsearch(subIds));
            }

            logger.info("======定时任务finish======");
        } catch (Exception e) {
            logger.error("向es里面写数据出现异常:{}", e);
        }
    }

    /**
     * @Description: 每一个线程处理类
     * @Author: moon
     * @CreateDate: 2018/12/18 0018 11:18
     * @UpdateUser: moon
     * @UpdateDate: 2018/12/18 0018 11:18
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    class WriteToeLasticsearch implements Runnable {

       private List<String> ids;

        public WriteToeLasticsearch(List<String> ids) {
            this.ids = ids;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("ids", ids)
                    .toString();
        }

        @Override
        public void run() {
            logger.info("线程{}.....", Thread.currentThread().getName());
            String join = String.join(",", ids);
            logger.info("ids={}",join);
            List<Search> searchs = sentimentMapper.getUnEs(join);
            if(CollectionUtils.isEmpty(searchs)){
                return;
            }
            try {

                if(indicesManager.writeToEs(searchs, indexName, indexType)>0){
                    sentimentMapper.setEs(join);
                }
            } catch (Exception e) {
                logger.info("数据写入失败!{}", e);
            }
        }
    }

    /**
     * @Description: 自定义定义线程池名称, 方便线程异常排查问题
     * @Author: moon
     * @CreateDate: 2018/12/18 0018 10:38
     * @UpdateUser: moon
     * @UpdateDate: 2018/12/18 0018 10:38
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    static class NameableThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);
        private final String namePrefix;

        NameableThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = name + "-pool-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + THREAD_NUMBER.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}


