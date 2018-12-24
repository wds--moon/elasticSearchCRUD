package com.dmzl.sentiment.core.es;

import com.alibaba.fastjson.JSON;
import com.dmzl.sentiment.entity.Search;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Description: 索引生成器,如何有新表加入不建议是使用下面的初始化方法
 * @Author: moon
 * @CreateDate: 2018/12/18 0018 16:20
 * @UpdateUser: moon
 * @UpdateDate: 2018/12/18 0018 16:20
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Component
public class IndicesManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TransportClient client;

    private IndicesAdminClient adminClient;

    /**
     * 集群配置初始化方法
     *
     * @throws Exception
     */
    @PostConstruct
    private void init() {
        adminClient = client.admin().indices();
    }

    /**
     * 判断集群中{index}是否存在
     *
     * @param index
     * @return 存在（true）、不存在（false）
     */
    public boolean indexExists(String index) {
        init();
        IndicesExistsRequest request = new IndicesExistsRequest(index);
        IndicesExistsResponse response = adminClient.exists(request).actionGet();
        if (response.isExists()) {
            return true;
        }
        return false;
    }

    public String query(String index, String type, String id) {
        init();
        //搜索数据
        GetResponse response = client.prepareGet(index, type, id).execute().actionGet();
        //输出结果
        return response.getSourceAsString();
    }


    /**
     * 创建mapping(feid("indexAnalyzer","ik")该字段分词IK索引 ；feid("searchAnalyzer","ik")该字段分词ik查询；具体分词插件请看IK分词插件说明)
     *
     * @param indices     索引名称；
     * @param mappingType 类型
     * @throws Exception
     */
    public void createMapping(String indices, String mappingType) throws Exception {
        init();
        adminClient.prepareCreate(indices).execute().actionGet();
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject(mappingType)
                .startObject("properties")
                .startObject("uuid").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("authorName").field("type", "text").field("fielddata", true).endObject()
                .startObject("contentHtml").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("dupCount").field("type", "integer").endObject()
                .startObject("duplicates").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("titleCopy").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("image").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("mediaId").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("mediaIndustryId").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("mediaType").field("type", "integer").field("index", "not_analyzed").endObject()
                .startObject("publishTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss").field("index", "not_analyzed").endObject()
                .startObject("crawlTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss").field("index", "not_analyzed").endObject()
                .startObject("publishDate").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("publishHour").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("replyCount").field("type", "integer").field("index", "not_analyzed").endObject()
                .startObject("sentimentType").field("type", "integer").endObject()
                .startObject("threadStarter").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("source").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("url").field("type", "text").field("index", "not_analyzed").endObject()
                .startObject("viewCount").field("type", "integer").endObject()
                .startObject("city").field("type", "integer").endObject()
                .startObject("county").field("type", "integer").endObject()
                .startObject("province").field("type", "integer").endObject()
                .startObject("sentiment").field("type", "integer").endObject()
                .startObject("es").field("type", "integer").endObject()
                .startObject("probability").field("type", "double").endObject()
                .startObject("title").field("type", "text").field("store", "yes").field("analyzer", "ik_smart").field("fielddata", true).endObject()
                .startObject("content").field("type", "text").field("store", "yes").field("analyzer", "ik_smart").field("fielddata", true).endObject()
                .endObject()
                .endObject()
                .endObject();
        PutMappingRequest mapping = Requests.putMappingRequest(indices).type(mappingType).source(builder);
        client.admin().indices().putMapping(mapping).actionGet();
    }

    /**
     * 删除索引
     *
     * @param index
     */
    public void deleteIndex(String index) {
        init();
        if (indexExists(index)) {
            DeleteIndexResponse dResponse = adminClient.prepareDelete(index)
                    .execute().actionGet();
        }

    }

    /**
     * 关闭es连接
     */
    public void close() {
        client.close();
        client = null;
        adminClient = null;
    }

    /**
     * 将search对象批量写入
     *
     * @param list
     * @param index
     * @param type
     * @return
     */
    public int writeToEs(List<Search> list, String index, String type) {
        BulkProcessor bulkProcessor = BulkProcessor.builder(client,
                new BulkProcessor.Listener() {

                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {
                        logger.info("---尝试插入{}条数据---", request.numberOfActions());
                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request, BulkResponse response) {
                        logger.info("---尝试插入{}条数据成功---", request.numberOfActions());
                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request, Throwable failure) {

                        logger.info("[es错误]---尝试插入数据失败---{}", failure.getMessage());
                    }

                })
                //设置多种条件，对批量操作进行限制，达到限制中的任何一种触发请求的批量提交
                // .setBulkActions(10000)//设置批量操作一次性执行的action个数，根据请求个数批量提交
                //.setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))//设置批量提交请求的大小允许的最大值
                //根据时间周期批量提交请求,间隔50秒提交一次
                .setFlushInterval(TimeValue.timeValueSeconds(50))
                //设置允许并发
                .setConcurrentRequests(1)
                //设置请求失败时的补偿措施，重复请求3次
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        int loopNum = 0;
        if (!CollectionUtils.isEmpty(list)) {
            loopNum = list.size();
            for (Search search : list) {
                bulkProcessor.add(new IndexRequest(index, type).source(JSON.toJSONString(search)));
            }
        }
        try {
            //释放bulkProcessor资源
            bulkProcessor.awaitClose(3, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new IllegalArgumentException("操作异常{}", e);
        }
        return loopNum;
    }
}
