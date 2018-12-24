package com.dmzl.sentiment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
/**
* @Description:    舆情热词参数类
* @Author:         moon
* @CreateDate:     2018/12/19 0019 11:48
* @UpdateUser:     moon
* @UpdateDate:     2018/12/19 0019 11:48
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@ApiModel(value = "查询舆情参数对象", description = "查询舆情参数对象")
public class TopWordsParam  implements Serializable {
	/**
	 * 反馈多少条数据
	 */
	@ApiModelProperty(value = "反馈条数", example = "[10-200]每页显示条数")
	private Integer topn;
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间", example = "20181212100000")
	private String start;
	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间", example = "20181215100000")
	private String end;
	/**
	 *  0 情感走势正负舆情  1 媒体类型 用于分组区别
	 */
	@ApiModelProperty(value = "分组字段", example = "0 情感走势正负舆情  1 媒体类型 [0,1]分组")
	private Integer sentimentAndMediaType;
	/**
	 * 正负舆情
	 */
	@ApiModelProperty(value = "正负舆情", example = "情感值参数范围[1 正,2 中,3 负]")
	private  Integer sentimentType;
	/**
	 * 舆情媒体类型
	 */
	@ApiModelProperty(value = "媒体类型", example = "1：新闻3：论坛4：微博5：贴吧6：博客10：微信公众号11：视频")
	private  Integer mediaType;

	/**
	 * 时间格式化格式 比如按年分组,按月分组,按天分组,按小时分组
	 */
	@ApiModelProperty(value = "格式化时间", example = "yyyy 按年分组,yyyy-MM 按照年月 yyyy-MM-dd 按天")
	private String dateFormat;

	public TopWordsParam() {
	}

	public TopWordsParam(Integer topn, String start, String end) {
	}

	public TopWordsParam(Integer topn, String start, String end, Integer sentimentAndMediaType, String dateFormat) {
		this.topn = topn;
		this.start = start;
		this.end = end;
		this.sentimentAndMediaType = sentimentAndMediaType;
		this.dateFormat = dateFormat;
	}

	public TopWordsParam(Integer topn, String start, String end, Integer sentimentAndMediaType, Integer sentimentType, Integer mediaType, String dateFormat) {
		this.topn = topn;
		this.start = start;
		this.end = end;
		this.sentimentAndMediaType = sentimentAndMediaType;
		this.sentimentType = sentimentType;
		this.mediaType = mediaType;
		this.dateFormat = dateFormat;
	}

	public Integer getTopn() {
		return topn;
	}

	public void setTopn(Integer topn) {
		this.topn = topn;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}



	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Integer getSentimentAndMediaType() {
		return sentimentAndMediaType;
	}

	public void setSentimentAndMediaType(Integer sentimentAndMediaType) {
		this.sentimentAndMediaType = sentimentAndMediaType;
	}

	public Integer getSentimentType() {
		return sentimentType;
	}

	public void setSentimentType(Integer sentimentType) {
		this.sentimentType = sentimentType;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}
}
	