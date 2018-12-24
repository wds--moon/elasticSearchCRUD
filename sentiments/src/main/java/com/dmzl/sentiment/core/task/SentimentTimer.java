package com.dmzl.sentiment.core.task;

import com.dmzl.sentiment.core.util.SentimentSearchUtil;
import com.dmzl.sentiment.entity.Search;
import com.dmzl.sentiment.entity.SentimentResult;
import com.dmzl.sentiment.entity.SentimentTheme;
import com.dmzl.sentiment.mapper.SearchMapper;
import com.dmzl.sentiment.mapper.SentimentThemeMapper;
import com.dmzl.sentiment.vo.SentimentResultDetailVo;
import com.dmzl.sentiment.vo.SentimentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author moon
 * @create 2018-12-17 15:25
 **/
@Component
@EnableScheduling
public class SentimentTimer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String PAGE_SIZE = "200";
    private static final String PAGE_NUM = "1";

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    SentimentThemeMapper sentimentThemeMapper;

    @Autowired
    SentimentSearchUtil yuqingfenxiService;

    /**
     * 舆情抓取数据,每页以标准的最大拉取数据大小进行获取数据,不为啥因为按照调用次数收费
     * 本想改成线程池消费,但是舆情数据量很少
     * 每隔小时20分钟开始执行舆情抓取
     */
    @Scheduled(cron = "0 20 * * * *")
    public void searchSentimentInfo() {
        List<SentimentTheme> sentimentThemeList = sentimentThemeMapper.selectAll();
        for (SentimentTheme theme : sentimentThemeList) {
            SentimentVo svo = new SentimentVo();
            svo.setPublishTime("60");
            svo.setQueryKeys(theme.getKeywords());
            svo.setPageNum(PAGE_NUM);
            svo.setPageSize(PAGE_SIZE);
            svo.setNoQueryKeys(theme.getExcludedWords());
            boolean flag = saveData(svo);
            while(flag){
                Integer pageNum=  Integer.parseInt(svo.getPageNum())+1;
                svo.setPageNum(pageNum+"");
                boolean nextBoolean = saveData(svo);
                if(!nextBoolean){
                    break;
                }
            }

        }
    }

    /**
     * 保存数据,获取当前页和最大页的比较,判断是否还有下一页
     * @param svo
     */
    private boolean saveData(SentimentVo svo) {
        SentimentResultDetailVo sentimentResultDetailVo = yuqingfenxiService.selectPublicSentimentById(svo);
        List<SentimentResult> listSentimentResult = sentimentResultDetailVo.getResultList();
        Integer totalPages = sentimentResultDetailVo.getPageInfo().getTotalPages();
        Integer curPageNo = sentimentResultDetailVo.getPageInfo().getCurPageNo();
        if (listSentimentResult != null) {
            for (SentimentResult sentimentResult : listSentimentResult) {
                Search search = new Search();
                setSearch(sentimentResult, search);
                Search searchs = new Search();
                searchs.setUuid(sentimentResult.getUuid());
                List<Search> searchss = this.searchMapper.select(searchs);
                if (searchss.size() == 0) {
                    this.searchMapper.insert(search);
                }
            }
        }
        logger.info("pageNum={},pages={}",curPageNo,totalPages);
        return curPageNo<totalPages?true:false;
    }

    /**
     * 设置舆情数据
     *
     * @param sentimentResult
     * @param search
     */
    private void setSearch(SentimentResult sentimentResult, Search search) {
        search.setAuthorName(sentimentResult.getAuthorName());
        if (sentimentResult.getContent() != null) {
            search.setContent(sentimentResult.getContent().replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", ""));
        } else {
            search.setContent(sentimentResult.getContent());
        }
        if (sentimentResult.getDupCount() != null) {
            search.setDupCount(String.valueOf(sentimentResult.getDupCount()));
        } else {
            search.setDupCount(String.valueOf(0));
        }
        search.setCity(sentimentResult.getCity());
        search.setProvince(sentimentResult.getProvince());
        search.setCounty(sentimentResult.getCounty());
        search.setDuplicates(sentimentResult.getDuplicates());
        search.setUuid(sentimentResult.getUuid());
        search.setImage(sentimentResult.getImage());
        search.setMediaId(sentimentResult.getMediaId());
        search.setMediaIndustryId(sentimentResult.getMediaIndustryId());
        search.setMediaType(Integer.parseInt(sentimentResult.getMediaType()));
        if (sentimentResult.getPublishTime() != null) {
            search.setPublishTime(sentimentResult.getPublishTime());
        }
        if (sentimentResult.getCrawlTime() != null) {
            search.setCrawlTime(sentimentResult.getCrawlTime());
        }
        if (sentimentResult.getReplyCount() != null) {
            search.setReplyCount(Integer.valueOf(sentimentResult.getReplyCount()));
        } else {
            search.setReplyCount(0);
        }
        search.setSentimentType(Integer.valueOf(sentimentResult.getSentimentType()));
        search.setThreadStarter(sentimentResult.getThreadStarter());
        search.setSource(sentimentResult.getSource());
        if (sentimentResult.getTitle() != null) {
            search.setTitle(sentimentResult.getTitle().replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", ""));
        } else {
            search.setTitle(sentimentResult.getTitle());
        }

        search.setUrl(sentimentResult.getUrl());
        if (sentimentResult.getViewCount() != null) {
            search.setViewCount(Integer.valueOf(sentimentResult.getViewCount()));
        } else {
            search.setViewCount(0);
        }
    }
}
