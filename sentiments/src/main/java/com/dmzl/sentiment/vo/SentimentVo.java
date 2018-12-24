package com.dmzl.sentiment.vo;


/**
 * 舆情入参
 * @author zsy
 *
 */
public class SentimentVo {
	/**
	 * 1：声量走势 2：情感分析 3：媒体来源 top10 4：媒体类型分析 5：媒体类型走势 6：信息发布量区域分布 7:  情感走势 8：媒体类型情感统计 
	 */
	private String chartType;
	/**
	 * search 或chart 
	 */
	private String sentimentUrl;
    /**
     * 授权唯一标识 必填 
     */
	private String secretkey;
	/**
     * 查询页 必填 
     */
	private String pageNum;
	
	/**
     * 每页显示条数 必填（上限 200 条） 
     */
	private String pageSize;
	/**
     * 相似文章是否合并   true，false（查询相似文章时用 false） 
     */
	private String merge;
	/**
     * 发布时间范围 必填  举例：”201608011200-20160802230000” 
     */
	private String publishTime;
	
	/**
     * 搜索词 词语用英文字符逗号分隔，表示或的关系 此中出现空格表示 且的关系 举例：”辽宁  暴乱,辽宁  治安,辽宁” 
     */
	private String queryKeys;
	 
	/**
     * 排除词 屏蔽包含排除词的文章，多词用英文逗号分隔 
     */
	private String noQueryKeys;
	/**
     * 情感类型 选填（默认查询全部） 1：负面    2：中性    3：正面 示例：”1,2”
     */
	private String sentiments;
	/**
     * 媒体类型 选填（默认查询全部） 1：新闻 3：论坛 4：微博 6：博客 10：微信公众号 示例:”1,3” 
     */
	private String mediaTypes;
	/**
     * 排序 “score”  相关度排序（默认） “time”   时间排序 
     */
	private String sort;
	
	/**
     * 相似文章 id 选填（来自文章的 duplicates 值） 
     */
	private String dupid;
	
	private String format;
	
	private String red;
	
	private String crawlTime;
	
	private Integer userId;

	private String desc;

	private String timeType;

	private Integer since;

	private String inType;

	private String orter;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
	}

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getMerge() {
		return merge;
	}

	public void setMerge(String merge) {
		this.merge = merge;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getQueryKeys() {
		return queryKeys;
	}

	public void setQueryKeys(String queryKeys) {
		this.queryKeys = queryKeys;
	}

	public String getNoQueryKeys() {
		return noQueryKeys;
	}

	public void setNoQueryKeys(String noQueryKeys) {
		this.noQueryKeys = noQueryKeys;
	}

	public String getSentiments() {
		return sentiments;
	}

	public void setSentiments(String sentiments) {
		this.sentiments = sentiments;
	}

	public String getMediaTypes() {
		return mediaTypes;
	}

	public void setMediaTypes(String mediaTypes) {
		this.mediaTypes = mediaTypes;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDupid() {
		return dupid;
	}

	public void setDupid(String dupid) {
		this.dupid = dupid;
	}

	public String getSentimentUrl() {
		return sentimentUrl;
	}

	public void setSentimentUrl(String sentimentUrl) {
		this.sentimentUrl = sentimentUrl;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getCrawlTime() {
		return crawlTime;
	}

	public void setCrawlTime(String crawlTime) {
		this.crawlTime = crawlTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public Integer getSince() {
		return since;
	}

	public void setSince(Integer since) {
		this.since = since;
	}

	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public String getOrter() {
		return orter;
	}

	public void setOrter(String orter) {
		this.orter = orter;
	}
}
