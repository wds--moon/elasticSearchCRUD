package com.dmzl.sentiment.core.util;

import com.alibaba.fastjson.JSON;
import com.dmzl.sentiment.core.constant.SystemParam;
import com.dmzl.sentiment.vo.SentimentResultDetailVo;
import com.dmzl.sentiment.vo.SentimentVo;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 通过北京舆情接口拉取指定舆情数据
 * @Author: moon
 * @CreateDate: 2018/12/17 0017 15:50
 * @UpdateUser: moon
 * @UpdateDate: 2018/12/17 0017 15:50
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Service
public class SentimentSearchUtil {

    public static Logger logger = LoggerFactory.getLogger(SentimentSearchUtil.class);


    @Value("${SENTIMENT_SECRETKEY}")
    public String sentimentSecretkey;
    @Value("${SENTIMENT_SEARCHURL}")
    public String sentimentSearchurl;
    @Value("${SENTIMENT_TIME}")
    public int sentimentTime;

    public SentimentResultDetailVo selectPublicSentimentById(SentimentVo vo) {
        String timeRange = setStartAndEndDate(vo);
        List<BasicNameValuePair> httpParam = new ArrayList<BasicNameValuePair>();
        setHttpParam(vo, timeRange, httpParam);
        String postValue = "";
        logger.info("网络舆情参数列表 request httpParam=" + httpParam.toString());
        try {
            postValue = HttpClientUtil.postHttpUrlHttpEntity(sentimentSearchurl, new UrlEncodedFormEntity(httpParam, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SentimentResultDetailVo sentimentResultDetailVo = JSON.parseObject(postValue, SentimentResultDetailVo.class);
        return sentimentResultDetailVo;
    }

    /**
     * 设置舆情参数
     *
     * @param vo
     * @param timeRange
     * @param httpParam
     */
    private void setHttpParam(SentimentVo vo, String timeRange, List<BasicNameValuePair> httpParam) {
        httpParam.add(new BasicNameValuePair("secretkey", sentimentSecretkey));
        httpParam.add(new BasicNameValuePair("queryKeys", vo.getQueryKeys()));
        if (!StringUtils.isEmpty(vo.getNoQueryKeys())) {
            httpParam.add(new BasicNameValuePair("noQueryKeys", vo.getNoQueryKeys()));
        }
        if (!StringUtils.isEmpty(vo.getMediaTypes())) {
            httpParam.add(new BasicNameValuePair("mediaTypes", vo.getMediaTypes()));
        }
        if (!StringUtils.isEmpty(vo.getSentiments())) {
            httpParam.add(new BasicNameValuePair("sentiments", vo.getSentiments()));
        }
        httpParam.add(new BasicNameValuePair("publishTime", timeRange));
        httpParam.add(new BasicNameValuePair("red", "false"));
        httpParam.add(new BasicNameValuePair("format", "true"));
        httpParam.add(new BasicNameValuePair("sort", "time"));
        if (!StringUtils.isEmpty(vo.getCrawlTime())) {
            httpParam.add(new BasicNameValuePair("crawlTime", vo.getCrawlTime()));
        }
        if (!StringUtils.isEmpty(vo.getMerge())) {
            httpParam.add(new BasicNameValuePair("merge", vo.getMerge()));
        }
        if (!StringUtils.isEmpty(vo.getPageNum())) {
            httpParam.add(new BasicNameValuePair("pageNum", vo.getPageNum()));
        }
        if (!StringUtils.isEmpty(vo.getPageSize())) {
            httpParam.add(new BasicNameValuePair("pageSize", vo.getPageSize()));
        }
    }

    /**
     * 设置开始时间和结束查询时间
     *
     * @param vo
     * @return
     */
    private String setStartAndEndDate(SentimentVo vo) {
        LocalDateTime localDateTime = null;
        if (StringUtils.isEmpty(vo.getPublishTime())) {
            localDateTime = LocalDateTime.now().plusDays(-90);
        } else if (SystemParam.PublishTime.MONTH.equals(vo.getPublishTime())) {
            localDateTime = LocalDateTime.now().plusMonths(-1);
        } else if (SystemParam.PublishTime.WEEK.equals(vo.getPublishTime())) {
            localDateTime = LocalDateTime.now().plusDays(-7);
        } else if (SystemParam.PublishTime.DAY.equals(vo.getPublishTime())) {
            localDateTime = LocalDateTime.now().plusDays(-1);
        } else if (SystemParam.PublishTime.HOUR.equals(vo.getPublishTime())) {
            localDateTime = LocalDateTime.now().plusHours(-1);
        } else if (PatternUtil.isInteger(vo.getPublishTime())) {
            localDateTime = LocalDateTime.now().plusMinutes(-Integer.parseInt(vo.getPublishTime()));
        } else {
            localDateTime = LocalDateTime.now().plusDays(-90);
        }
        String startTime = localDateTime.format(TimeFormat.LONG_DATE_PATTERN_NONE.formatter);
        String endTime = LocalDateTime.now().format(TimeFormat.LONG_DATE_PATTERN_NONE.formatter);
        return startTime + "-" + endTime;
    }


}
