package com.dmzl.sentiment.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
* @Description:    舆情实体类
* @Author:         moon
* @CreateDate:     2018/12/18 0018 16:20
* @UpdateUser:     moon
* @UpdateDate:     2018/12/18 0018 16:20
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Table(name = "t_search")
@Data
public class Search implements Serializable {
    @Id
    private Integer id;

    /**
     * 作者
     */
    @Column(name = "author_name")
    private String authorName;

    /**
     * 内容
     */
    private String content;

    /**
     * 带 html 格式的正文内容
     */
    @Column(name = "content_html")
    private String contentHtml;

    /**
     * 相似文章数
     */
    @Column(name = "dup_count")
    private String dupCount;

    /**
     * 相似 id
     */
    private String duplicates;

    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 媒体 id
     */
    @Column(name = "media_id")
    private String mediaId;

    /**
     * 行业id
     */
    @Column(name = "media_industry_id")
    private String mediaIndustryId;

    /**
     * 媒体类型
     */
    @Column(name = "media_type")
    private Integer mediaType;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private String publishTime;

    /**
     * 采集时间
     */
    @Column(name = "crawl_time")
    private String crawlTime;

    /**
     * 回复数
     */
    @Column(name = "reply_count")
    private Integer replyCount;

    /**
     * 情感类型
     */
    @Column(name = "sentiment_type")
    private Integer sentimentType;

    /**
     * 媒体名称
     */
    @Column(name = "thread_starter")
    private String threadStarter;

    /**
     * 来源
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
    @Column(name = "view_count")
    private Integer viewCount;

    private Integer sentiment;
    private Integer city;
    private Integer county;
    private Integer province;

    /**
     * 是否已经添加到es环境标示
     */
    private Integer es;

    private Double probability;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("authorName", authorName)
                .append("content", content)
                .append("contentHtml", contentHtml)
                .append("dupCount", dupCount)
                .append("duplicates", duplicates)
                .append("uuid", uuid)
                .append("image", image)
                .append("mediaId", mediaId)
                .append("mediaIndustryId", mediaIndustryId)
                .append("mediaType", mediaType)
                .append("publishTime", publishTime)
                .append("crawlTime", crawlTime)
                .append("replyCount", replyCount)
                .append("sentimentType", sentimentType)
                .append("threadStarter", threadStarter)
                .append("source", source)
                .append("title", title)
                .append("url", url)
                .append("viewCount", viewCount)
                .append("sentiment", sentiment)
                .append("city", city)
                .append("county", county)
                .append("province", province)
                .append("es", es)
                .append("probability", probability)
                .toString();
    }
}