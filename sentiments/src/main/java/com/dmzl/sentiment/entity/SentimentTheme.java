package com.dmzl.sentiment.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
/**
* @Description:    舆情主题实体类
* @Author:         moon
* @CreateDate:     2018/12/18 0018 16:24
* @UpdateUser:     moon
* @UpdateDate:     2018/12/18 0018 16:24
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Table(name = "t_sentiment_theme")
@Data
public class SentimentTheme implements Serializable {
    /**
     * 序号
     */
    @Id
    private Long id;

    /**
     * 主题
     */
    private String theme;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 产品实际价格
     */
    @Column(name = "excluded_words")
    private String excludedWords;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", theme=").append(theme);
        sb.append(", keywords=").append(keywords);
        sb.append(", excludedWords=").append(excludedWords);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}