package com.dmzl.sentiment.core.util;

import com.github.pagehelper.Page;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统返回值对象
 *
 * @author wds
 */
public class ResultVo implements Serializable {

	/**
	 * 当前页码
	 */
	private Integer page;
	/**
	 * 每页条数
	 */
	private Integer size;
	/**
	 * 参数前后响应一致参数
	 */
	private Long draw;
	/**
	 * 总条数
	 */
	private Long recordsTotal;
	/**
	 * 数据集合
	 */
	private List data;
	/**
	 * 是否成功
	 */
	private Boolean success;
	/**
	 * 错误信息
	 */
	private String error;

	public static ResultVo success(Long draw, List data, Page pageInfo) {
		ResultVo result = new ResultVo();
		result.setDraw(draw);
		result.setPage(pageInfo.getPageNum());
		result.setSize(pageInfo.getPageSize());
		result.setRecordsTotal(pageInfo.getTotal());
		if (!CollectionUtils.isEmpty(data)) {
			result.setData(data);
		} else {
			result.setData(new ArrayList());
		}
		result.setSuccess(true);
		return result;
	}

	public static ResultVo failure(Long draw, Page pageInfo, String err) {
		ResultVo result = new ResultVo();
		result.setDraw(draw);
		result.setError(err);
		result.setPage(pageInfo.getPageNum());
		result.setSize(pageInfo.getPageSize());
		result.setSuccess(false);
		return result;
	}

	public static ResultVo success(Long draw) {
		ResultVo result = new ResultVo();
		result.setDraw(draw);
		result.setSuccess(true);
		return result;
	}

	public static ResultVo failure(Long draw, String err) {
		ResultVo result = new ResultVo();
		result.setDraw(draw);
		result.setError(err);
		result.setSuccess(false);
		return result;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Long getDraw() {
		return draw;
	}

	public void setDraw(Long draw) {
		this.draw = draw;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ResultVo resultVo = (ResultVo) o;

		return new EqualsBuilder()
				.append(page, resultVo.page)
				.append(size, resultVo.size)
				.append(draw, resultVo.draw)
				.append(recordsTotal, resultVo.recordsTotal)
				.append(data, resultVo.data)
				.append(success, resultVo.success)
				.append(error, resultVo.error)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(page)
				.append(size)
				.append(draw)
				.append(recordsTotal)
				.append(data)
				.append(success)
				.append(error)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("page", page)
				.append("size", size)
				.append("draw", draw)
				.append("recordsTotal", recordsTotal)
				.append("data", data)
				.append("success", success)
				.append("error", error)
				.toString();
	}
}