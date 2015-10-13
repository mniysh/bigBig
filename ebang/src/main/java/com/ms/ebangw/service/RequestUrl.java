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
    public static final  String msg = domain+"/api/user/index/msg";

    /**
     * 4.短信验证码,注册
     */
    public static final  String msg_register =domain+"/api/user/index/msg/1";

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
     * 1-6.个人信息验证接口
     */
    public static final  String penson_identify = domain + "/api/user/investor/identify";
    /**
     * 1-9.开发商认证接口
     */
    public static final  String developer_identify = domain + "/api/user/developers/identify";
    /**
     * 1-11.获取用户信息
     */
    public static  final String loadUserInfo = domain + "/api/user/index/information";
    /**
     * 11.工头认证接口
     */
    public static final  String header_identify = domain + "/api/user/headman/identify";

    /**
     * 12.工人认证接口
     */
    public static final  String worker_identify = domain + "/api/user/worker/identify";

    /**
     *13.修改昵称接口
     */
    public static final  String modify_nickName = domain + "/api/user/index/change-nickname";


    /**
     * 3-6. 获取全部省市区数据   get方式
     */
    public static final  String province_city_area = domain +
        "/api/common/address/province-city-area";


    /**
     * 14.修改绑定手机接口
     */
    public static final String modify_phone =domain + "/api/user/index/change-phone";
    /**
     * 3-13.仅仅验证验证码的接口
     */
    public static final String only_check_code = domain + "/api/user/index/check-code-only";

    /**
     *3-14、获取银行列表
     */
    public static final String bank_list =domain + "/api/common/bank/list";

    /**
     * 3-7 发布选择的接口 (建筑， 装修， 工程管理)  Get方式
     */
    public static final String publish_craft =domain + "/api/common/craft/publish-craft";

}
