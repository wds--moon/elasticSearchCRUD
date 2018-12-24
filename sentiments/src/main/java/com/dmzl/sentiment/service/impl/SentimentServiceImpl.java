package com.dmzl.sentiment.service.impl;

import com.dmzl.sentiment.core.util.TimeFormat;
import com.dmzl.sentiment.service.SentimentService;
import com.dmzl.sentiment.vo.TopWordsParam;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.support.IncludeExclude;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 舆情接口
 * @Author: moon
 * @CreateDate: 2018/12/18 0018 17:54
 * @UpdateUser: moon
 * @UpdateDate: 2018/12/18 0018 17:54
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Service
public class SentimentServiceImpl implements SentimentService {
    @Autowired
    private TransportClient client;

    @Value("${elasticsearch.indexName}")
    private String indexName;
    @Value("${elasticsearch.indexType}")
    private String indexType;

    /**
     * 舆情热词接口
     *
     * @param twp
     * @return
     */
    @Override
    public List<Map<String, String>> listTopWords(TopWordsParam twp) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName)
                .setTypes(indexType);
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        RangeQueryBuilder rqb = QueryBuilders.rangeQuery("publishTime");
        if (StringUtils.isNoneEmpty(twp.getStart())) {
            rqb = rqb.gte(twp.getStart());
        }

        if (StringUtils.isNoneEmpty(twp.getEnd())) {
            rqb = rqb.lte(twp.getEnd());
        }
        if (StringUtils.isNoneEmpty(twp.getEnd()) || StringUtils.isNoneEmpty(twp.getStart())) {
            bqb = bqb.must(rqb);
        }
        SearchResponse response =
                searchRequestBuilder.setQuery(bqb).addAggregation(
                        AggregationBuilders.terms("messages").field("content").includeExclude(new IncludeExclude("[\u4E00-\u9FA5][\u4E00-\u9FA5]", "")).size(twp.getTopn())
                )
                        .execute().actionGet();
        Terms messages = response.getAggregations().get("messages");
        Map<String, String> map = null;
        for (Terms.Bucket bucket : messages.getBuckets()) {
            map = new HashMap<>(16);
            map.put("word", bucket.getKeyAsString());
            map.put("count", String.valueOf(bucket.getDocCount()));
            result.add(map);
        }
        return result;
    }

    /**
     * 根据条件查询舆情结果数据
     * setSize(0) 标示不反馈具体数据,只返回需要反馈的结果集
     *
     * @param twp
     * @return
     */
    @Override
    public List listSentimentInfo(TopWordsParam twp) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName)
                .setTypes(indexType);
        /**
         * 多条件操作
         */
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        /**
         * 类型查询 0 舆情
         */
        if (twp.getSentimentType() != null) {
            TermQueryBuilder sentimentType = QueryBuilders.termQuery("sentimentType", twp.getSentimentType());
            boolQueryBuilder.must(sentimentType);
        }
        if (twp.getMediaType() != null) {
            TermQueryBuilder mediaType = QueryBuilders.termQuery("mediaType", twp.getMediaType());
            boolQueryBuilder.must(mediaType);
        }
        /**
         * 时间段查询
         */
        RangeQueryBuilder rqb = QueryBuilders.rangeQuery("publishTime");
        if (StringUtils.isNoneEmpty(twp.getStart())) {
            rqb = rqb.gte(twp.getStart());
        }

        if (StringUtils.isNoneEmpty(twp.getEnd())) {
            rqb = rqb.lte(twp.getEnd());
        }
        if (StringUtils.isNoneEmpty(twp.getEnd()) || StringUtils.isNoneEmpty(twp.getStart())) {
            boolQueryBuilder = boolQueryBuilder.must(rqb);
        }
        /**
         * 时间排序
         */
        SearchResponse response = searchRequestBuilder.setQuery(boolQueryBuilder).addSort("publishTime", SortOrder.DESC).setSize(twp.getTopn()).execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            result.add(hit.getSource());
        }
        return result;
    }

    /**
     * 多条件分组 按照时间,情感类型分组
     *
     * @param twp
     * @return
     */
    @Override
    public List listPublishTimeAndSentimentTypeGroupBy(TopWordsParam twp) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName)
                .setTypes(indexType).setSize(0);
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        RangeQueryBuilder rqb = QueryBuilders.rangeQuery("publishTime");
        if (StringUtils.isNoneEmpty(twp.getStart())) {
            rqb = rqb.gte(twp.getStart());
        }

        if (StringUtils.isNoneEmpty(twp.getEnd())) {
            rqb = rqb.lte(twp.getEnd());
        }
        if (StringUtils.isNoneEmpty(twp.getEnd()) || StringUtils.isNoneEmpty(twp.getStart())) {
            bqb = bqb.must(rqb);
        }
        AggregationBuilder aggregationBuilders = null;
        DateHistogramAggregationBuilder dateHisBuilder = AggregationBuilders.dateHistogram("publishTimeGroup").field("publishTime");
        if (StringUtils.isNotEmpty(twp.getDateFormat())) {
            /**
             * 分别按照年月日时分组查询查询,不支持更加细粒度的查询
             */
            if (TimeFormat.SIMPLE_DATE_PATTERN_YEAR.getMsg().equals(twp.getDateFormat())) {
                aggregationBuilders = dateHisBuilder.format(TimeFormat.SIMPLE_DATE_PATTERN_YEAR.getMsg()).dateHistogramInterval(DateHistogramInterval.YEAR);
            } else if (TimeFormat.SIMPLE_DATE_PATTERN_YEAR_MONTH_LINE.getMsg().equals(twp.getDateFormat())) {
                aggregationBuilders = dateHisBuilder.format(TimeFormat.SIMPLE_DATE_PATTERN_YEAR_MONTH_LINE.getMsg()).dateHistogramInterval(DateHistogramInterval.MONTH);
            } else if (TimeFormat.SHORT_DATE_PATTERN_LINE.getMsg().equals(twp.getDateFormat())) {
                aggregationBuilders = dateHisBuilder.format(TimeFormat.SHORT_DATE_PATTERN_LINE.getMsg()).dateHistogramInterval(DateHistogramInterval.DAY);
            } else if (TimeFormat.SHORT_DATE_PATTERN_LINE_HURE.getMsg().equals(twp.getDateFormat())) {
                aggregationBuilders = dateHisBuilder.format(TimeFormat.SHORT_DATE_PATTERN_LINE_HURE.getMsg()).dateHistogramInterval(DateHistogramInterval.HOUR);
            } else {
                aggregationBuilders = dateHisBuilder.format(TimeFormat.SHORT_DATE_PATTERN_LINE.getMsg()).dateHistogramInterval(DateHistogramInterval.DAY);
            }

        } else {
            aggregationBuilders = dateHisBuilder.format(TimeFormat.SHORT_DATE_PATTERN_LINE.getMsg()).dateHistogramInterval(DateHistogramInterval.DAY);
        }
        String keyName = "";
        if (twp.getSentimentAndMediaType() == 0) {
            aggregationBuilders.subAggregation(AggregationBuilders.terms("sentimentTypeGroup").field("sentimentType"));
            keyName = "sentimentType";
        } else {
            aggregationBuilders.subAggregation(AggregationBuilders.terms("mediaTypeGroup").field("mediaType"));
            keyName = "mediaType";
        }
        SearchResponse response =
                searchRequestBuilder.setQuery(bqb).addAggregation(aggregationBuilders).execute().actionGet();
        InternalDateHistogram dateHistogram = response.getAggregations().get("publishTimeGroup");
        setResultMap(result, keyName, dateHistogram);
        return result;
    }

    /**
     * 设置返回数据
     * @param result
     * @param keyName
     * @param dateHistogram
     */
    private void setResultMap(List<Map<String, Object>> result, String keyName, InternalDateHistogram dateHistogram) {
        Map<String, Object> map = null;
        for (InternalDateHistogram.Bucket bucket : dateHistogram.getBuckets()) {
            map = new HashMap<>(16);
            map.put("date", bucket.getKeyAsString());
            map.put("count", bucket.getDocCount() + "");
            Aggregations sentimentTypeHistogram = bucket.getAggregations();
            Aggregation publishTimeGroup = sentimentTypeHistogram.get("publishTimeGroup");
            /**
             * 初始化子集,补全数据初始化为0 避免前端出错
             */
            List<Map<String, String>> sentimentTypeList = null;
            if ("sentimentType".equals(keyName)) {
                sentimentTypeList = initSentimentType(bucket.getKeyAsString(), keyName);
            } else {
                sentimentTypeList = initMediaType(bucket.getKeyAsString(), keyName);
            }
            Map<String, String> sentimentType = null;
            for (Aggregation sentimentTypeInfo : sentimentTypeHistogram.asList()) {
                List<LongTerms.Bucket> buckets = ((LongTerms) sentimentTypeInfo).getBuckets();
                for (LongTerms.Bucket sentiment : buckets) {
                    for (Map<String, String> p : sentimentTypeList) {
                        if (p.get(keyName).toString().equals(sentiment.getKey().toString())) {
                            p.put("count", String.valueOf(sentiment.getDocCount()));
                        }
                    }
                }
            }
            map.put(keyName + "s", sentimentTypeList);
            result.add(map);
        }
    }

    @Override
    public List<Map<String, String>> listGroupSource(TopWordsParam twp) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName)
                .setTypes(indexType).setSize(0);
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        RangeQueryBuilder rqb = QueryBuilders.rangeQuery("publishTime");
        if (StringUtils.isNoneEmpty(twp.getStart())) {
            rqb = rqb.gte(twp.getStart());
        }

        if (StringUtils.isNoneEmpty(twp.getEnd())) {
            rqb = rqb.lte(twp.getEnd());
        }
        if (StringUtils.isNoneEmpty(twp.getEnd()) || StringUtils.isNoneEmpty(twp.getStart())) {
            bqb = bqb.must(rqb);
        }
        /**
         * source 这样的关键字,不能创建索引,奇怪,采用备用字段,去多少条数据的时候需要把size设置在addAggregation里面,分组的取词
         */
        SearchResponse response =
                searchRequestBuilder.setQuery(bqb).addAggregation(AggregationBuilders.terms("sourceGroup").field("threadStarter").size(twp.getTopn()))
                        .execute().actionGet();
        StringTerms stringTerms = (StringTerms) response.getAggregations().asList().get(0);
        Map<String, String> map = null;
        for (StringTerms.Bucket aggregation : stringTerms.getBuckets()) {
            map = new HashMap<>(16);
            map.put("sourceName", aggregation.getKeyAsString());
            map.put("count", String.valueOf(aggregation.getDocCount()));
            result.add(map);
        }
        return result;
    }

    /**
     * 初始化正负舆情
     *
     * @param date
     * @param keyName
     * @return
     */
    public List<Map<String, String>> initSentimentType(String date, String keyName) {
        List<Map<String, String>> init = new ArrayList<>();
        init.add(getStringObjectMap("1", date, keyName));
        init.add(getStringObjectMap("2", date, keyName));
        init.add(getStringObjectMap("3", date, keyName));
        return init;
    }

    private Map<String, String> getStringObjectMap(String flag, String date, String keyName) {
        Map<String, String> map = new HashMap<>(16);
        map.put("date", date);
        map.put("count", "0");
        map.put(keyName, flag);
        return map;
    }

    /**
     * 初始化媒体;类型
     *
     * @param date
     * @param keyName
     * @return
     */
    public List<Map<String, String>> initMediaType(String date, String keyName) {
        List<Map<String, String>> init = new ArrayList<>();
        init.add(getStringObjectMap("1", date, keyName));
        init.add(getStringObjectMap("3", date, keyName));
        init.add(getStringObjectMap("4", date, keyName));
        init.add(getStringObjectMap("5", date, keyName));
        init.add(getStringObjectMap("6", date, keyName));
        init.add(getStringObjectMap("10", date, keyName));
        init.add(getStringObjectMap("11", date, keyName));
        return init;
    }
}


