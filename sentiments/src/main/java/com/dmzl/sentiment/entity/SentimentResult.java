package com.dmzl.sentiment.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
/**
* @Description:    舆情反馈列表
* @Author:         moon
* @CreateDate:     2018/12/18 0018 16:24
* @UpdateUser:     moon
* @UpdateDate:     2018/12/18 0018 16:24
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class SentimentResult  implements Serializable {
	/**
	 * 作者
	 */
	private String authorName;
	/**
	 * 城市id
	 */
	private Integer city;
	/**
	 * 正文内容
	 */
	private String content;
	/**
	 * 相似文章数
	 */
	private Integer county;

	private Integer province;
	/**
	 * 相似文章数
	 */
	private Integer dupCount;
	/**
	 * 相似文章id
	 */
	private String duplicates;
	private String uuid;
	/**
	 * 图片地址
	 */
	private String image;
	/**
	 * 媒体id
	 */
	private String mediaId;
	/**
	 * 行业id
	 */
	private String mediaIndustryId;
	/**
	 * 媒体类型
	 */
	private String mediaType;
	/**
	 * 发布时间
	 */
	private String publishTime;
	/**
	 * 采集时间
	 */
	private String crawlTime;
	/**
	 * 回复数
	 */
	private Integer replyCount;
	/**
	 * 情感值
	 */
	private Integer sentimentType;
	/**
	 * 媒体类型
	 */
	private String threadStarter;

	/**
	 * 来源	新闻来自哪个媒体
	 */
	private String source;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 原文链接
	 */
	private String url;
	/**
	 * 浏览数
	 */
	private Integer viewCount;

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCounty() {
		return county;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setCounty(Integer county) {
		this.county = county;
	}

	public String getCrawlTime() {
		return crawlTime;
	}

	public void setCrawlTime(String crawlTime) {
		this.crawlTime = crawlTime;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getDupCount() {
		return dupCount;
	}

	public void setDupCount(Integer dupCount) {
		this.dupCount = dupCount;
	}

	public String getDuplicates() {
		return duplicates;
	}

	public void setDuplicates(String duplicates) {
		this.duplicates = duplicates;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaIndustryId() {
		return mediaIndustryId;
	}

	public void setMediaIndustryId(String mediaIndustryId) {
		this.mediaIndustryId = mediaIndustryId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public Integer getSentimentType() {
		return sentimentType;
	}

	public void setSentimentType(Integer sentimentType) {
		this.sentimentType = sentimentType;
	}

	public String getThreadStarter() {
		return threadStarter;
	}

	public void setThreadStarter(String threadStarter) {
		this.threadStarter = threadStarter;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("authorName", authorName)
				.append("city", city)
				.append("content", content)
				.append("county", county)
				.append("crawlTime", crawlTime)
				.append("province", province)
				.append("dupCount", dupCount)
				.append("duplicates", duplicates)
				.append("uuid", uuid)
				.append("image", image)
				.append("mediaId", mediaId)
				.append("mediaIndustryId", mediaIndustryId)
				.append("mediaType", mediaType)
				.append("publishTime", publishTime)
				.append("replyCount", replyCount)
				.append("sentimentType", sentimentType)
				.append("threadStarter", threadStarter)
				.append("source", source)
				.append("title", title)
				.append("url", url)
				.append("viewCount", viewCount)
				.toString();
	}
}
