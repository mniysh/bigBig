package com.ms.ebangw.service;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-13 22:34
 */
public class RequestUrl {

    private final static String domain = "http://labour.chinadeer.cn";

    /**
     * 注册
     */
    public static final String register = domain + "/api/user/index/register";
    /**
     * 登录
     */
    public static final String login = domain + "/api/user/index/login";
    /**
     * 登出
     */
    public static final String logout = domain + "/api/user/index/logout";
    /**
     * 个人信息
     */
    public static final String user_information = domain + "/api/user/index/information";
}
