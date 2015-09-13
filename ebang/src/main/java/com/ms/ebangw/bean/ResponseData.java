package com.ms.ebangw.bean;

/**
 * 请求后返回的数据
 * User: WangKai(123940232@qq.com)
 * 2015-09-13 22:44
 */
public class ResponseData {
    private String code;
    private String message;
    private String date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
