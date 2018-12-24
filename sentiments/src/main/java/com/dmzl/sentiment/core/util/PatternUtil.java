package com.dmzl.sentiment.core.util;

import java.util.regex.Pattern;

/**
 * @author moon
 * @create 2018-12-17 16:01
 **/
public class PatternUtil {

    public static Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
    /**
     * 正则校验是否是正整数,建议写成预编译,提高效率
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        return pattern.matcher(str).matches();
    }
}
