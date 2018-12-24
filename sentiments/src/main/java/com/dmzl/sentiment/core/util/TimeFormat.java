package com.dmzl.sentiment.core.util;

import java.time.format.DateTimeFormatter;

/**
* @Description:    时间格式化格式枚举类
* @Author:         moon
* @CreateDate:     2018/12/24 0024 09:48
* @UpdateUser:     moon
* @UpdateDate:     2018/12/24 0024 09:48
* @UpdateRemark:   修改内容(如果需要可以自己随意添加格式)
* @Version:        1.0
*/
public enum TimeFormat {
    /**
     * 简单格式
     */
    SIMPLE_DATE_PATTERN_YEAR("yyyy"),
    SIMPLE_DATE_PATTERN_MONTH("MM"),
    SIMPLE_DATE_PATTERN_DAY("dd"),
    SIMPLE_DATE_PATTERN_HOUR("HH"),

    SIMPLE_DATE_PATTERN_YEAR_MONTH_LINE("yyyy-MM"),
    SIMPLE_DATE_PATTERN_YEAR_MONTH_NONE("yyyyMM"),
    /**
     * 短时间格式
     */
    SHORT_DATE_PATTERN_CHINESE("yyyy年MM月dd日"),
    SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
    SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
    SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
    SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

    SHORT_DATE_PATTERN_LINE_HURE("yyyy-MM-dd HH"),

    /**
     * 长时间格式
     */
    LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
    LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
    LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
    LONG_DATE_PATTERN_NONE("yyyyMMddHHmmss"),


    /**
     * 长时间格式 带毫秒
     */
    LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMddHHmmssSSS");

    /**
     * transient 去掉序列化
     */
    public transient DateTimeFormatter formatter;


    TimeFormat(String pattern) {
        msg=pattern;
        formatter = DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * 指定一个参数方便获取value值
     */
    private  String msg;

    public String getMsg() {
        return msg;
    }
}
