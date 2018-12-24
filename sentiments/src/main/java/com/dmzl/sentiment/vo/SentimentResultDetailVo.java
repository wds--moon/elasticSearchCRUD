package com.dmzl.sentiment.vo;

import com.dmzl.sentiment.entity.SentimentPageInfo;
import com.dmzl.sentiment.entity.SentimentResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;


/**
 * @Description: 北京舆情反馈结果集
 * @Author: moon
 * @CreateDate: 2018/12/17 0017 15:35
 * @UpdateUser: moon
 * @UpdateDate: 2018/12/17 0017 15:35
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class SentimentResultDetailVo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private SentimentPageInfo pageInfo;
    private List<SentimentResult> resultList;

    public SentimentPageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(SentimentPageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<SentimentResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<SentimentResult> resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pageInfo", pageInfo)
                .append("resultList", resultList)
                .toString();
    }
}
