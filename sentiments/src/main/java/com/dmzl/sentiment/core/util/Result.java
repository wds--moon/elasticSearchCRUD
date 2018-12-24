package com.dmzl.sentiment.core.util;
/**
* @Description:    舆情反馈结果集
* @Author:         moon
* @CreateDate:     2018/12/17 0017 15:37
* @UpdateUser:     moon
* @UpdateDate:     2018/12/17 0017 15:37
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class Result {
    private boolean success = true;
    private int code = 1000;
    private String msg = "操作成功";
    private String title = "成功";
    private Object data;

    /** @deprecated */
    @Deprecated
    public Result() {
    }

    public Result(String title, String msg) {
        this.title = title;
        this.msg = msg;
        this.success = true;
        this.code = 1000;
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(boolean success, int code, String title, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.title = title;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
