package com.dmzl.sentiment.core.constant;

/**
 * @author wds
 * @Title: SystemParamInfo
 * @ProjectName sentiments
 * @Description: TODO
 * @date 2018/12/17 001715:44
 */
public interface SystemParam {
    /**
     * 查询时间区间
     */
    interface PublishTime{
        String MONTH="month";
        String WEEK="week";
        String DAY="day";
        String HOUR="hour";
    }

    /**
     * 分页基础初始化数据
     */
    interface  PageInitInfo{
        Integer PAGE_NUM=1;
        Integer PAGE_MIN_SIZE=10;
        Integer PAGE_MAX_SIZE=200;
    }
}
