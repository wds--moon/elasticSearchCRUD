package com.dmzl.sentiment.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
* @Description:    舆情反馈基本信息
* @Author:         moon
* @CreateDate:     2018/12/18 0018 16:22
* @UpdateUser:     moon
* @UpdateDate:     2018/12/18 0018 16:22
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class SentimentPageInfo implements Serializable {
	private Integer curPageNo;
	private Integer pageSize;
	private Integer timeConsum;
	private Integer totalRecords;
	private Integer totalPages;
	public Integer getCurPageNo() {
		return curPageNo;
	}
	public void setCurPageNo(Integer curPageNo) {
		this.curPageNo = curPageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTimeConsum() {
		return timeConsum;
	}
	public void setTimeConsum(Integer timeConsum) {
		this.timeConsum = timeConsum;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("curPageNo", curPageNo)
				.append("pageSize", pageSize)
				.append("timeConsum", timeConsum)
				.append("totalRecords", totalRecords)
				.append("totalPages", totalPages)
				.toString();
	}
}
