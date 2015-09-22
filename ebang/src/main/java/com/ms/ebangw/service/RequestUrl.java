package com.ms.ebangw.service;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-13 22:34
 */
public class RequestUrl {

    private final static String domain = "http://labour.chinadeer.cn";

    /**
     * 1.注册
     */
    public static final String register = domain + "/api/user/index/register";
    /**
     * 2.登录
     */
    public static final String login = domain + "/api/user/index/login";
    /**
     * 3.退出接口
     */
    public static final String logout = domain + "/api/user/index/logout";
    /**
     * 个人信息
     */
    public static final String user_information = domain + "/api/user/index/information";
    /**
     * 4.短信验证码
     */
    public static final  String msg =domain+"/api/user/index/msg";

    /**
     * 5.短信修改密码接口
     */
    public static final  String update_pwd= domain + "/api/user/index/update-pwd";

    /**
     * 6.用旧密码修改新密码接口
     */
    public static final  String change_pwd = domain + "/api/user/index/change-pwd";

    /**
     * 7.个人信息验证接口
     */
    public static final  String person_identify = domain + "api/user/investor/person-identify";

    /**
     * 8.上传图片
     */
    public static final  String upload_image = domain + "/api/user/upload/image";

    /**
     * 9.下载图片   get方式  最后+图片名字
     */
    public static final  String down_image = domain + "/api/user/upload/image/";

    /**
     * 10.省市区接口 /api/common/address/child/+数字（0返回所有省与直辖市）   Get方式
     */
    public static final  String address = domain + "/api/common/address/child/";

    /**
     * 11.工头认证接口
     */
    public static final  String header_identify = domain + "/api/user/investor/header-identify";

    /**
     * 12.工人认证接口
     */
    public static final  String worker_identify = domain + "/api/user/investor/worker-identify";

    /**
     * 13.修改昵称接口
     */
    public static final  String modify_nickName = domain + "/api/user/index/change-nickname";
    /**
     * 14.修改绑定手机接口
     */
    public static final String modify_phone =domain + "/api/user/index/change-phone";
}
