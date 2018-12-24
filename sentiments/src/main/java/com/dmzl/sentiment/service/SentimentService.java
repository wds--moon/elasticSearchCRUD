package com.dmzl.sentiment.service;

import com.dmzl.sentiment.vo.TopWordsParam;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @Title: SentimentService
 * @ProjectName sentiments
 * @Description: TODO
 * @date 2018/12/21 002110:38
 */
public interface SentimentService {
    /**
     * 热词接口
     * @param twp
     * @return
     */
    public List<Map<String, String>> listTopWords(TopWordsParam twp);

    /**
     * 获取舆情列表信息,按照条件
     * @param twp
     * @return
     */
    public List<Map<String, String>> listSentimentInfo(TopWordsParam twp);

    /**
     * 舆情按照时间分组基础上再对媒体和正负舆情进行分组
     * @param twp
     * @return
     */
    public List<Map<String, String>> listPublishTimeAndSentimentTypeGroupBy(TopWordsParam twp);

    /**
     * 按照来源分组查询
     * @param twp
     * @return
     */
    public  List<Map<String, String>> listGroupSource(TopWordsParam twp);
}
