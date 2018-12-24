package com.dmzl.sentiment.core.constant;

/**
* @Description:    定义返回状态码
* @Author:         moon
* @CreateDate:     2018/12/17 0017 10:25
* @UpdateUser:     moon
* @UpdateDate:     2018/12/17 0017 10:25
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public enum CodeEnum {
    /**
     * 成功标示
     */
    SUCCESS(0, "success"),
    /**
     * 失败标示
     */
    FAILURE(1, "failure");

    private int code;
    private String message;

    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }


    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CodeEnum valueOf(int code) {
        CodeEnum[] var1 = values();
        int var2 = var1.length;
        for (int var3 = 0; var3 < var2; ++var3) {
            CodeEnum codeEnum = var1[var3];
            if (codeEnum.code == code) {
                return codeEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
