package com.ms.ebangw.service;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-13 22:34
 */
public class RequestUrl {

    /**
     * 测试用域名
     */
    private final static String domain = "http://labour.chinadeer.cn";

    /**
     * 正式上线域名
     */
//    private final static String domain = "http://www.ebngw.com";
/**
     * 1.注册
     */
    public static final String register = domain + "/api/user/index/register";
    /**
     * 2.登录
     */
    public static final String login = domain + "/api/user/index/login";
    /**
     * 1-3.退出接口  请求：get
     */
    public static final String logout = domain + "/api/user/index/logout";
    /**
     * 1-11、获取用户信息接口 get方式
     */
    public static final String user_information = domain + "/api/user/index/information";
    /**
     * 4.短信验证码
     */
    public static final  String msg =domain+"/api/user/index/msg";
    /**
     * 4.1短信验证码
     */
    public static final  String msg_registe =domain+"/api/user/index/msg/1";

    /**
     * 1-4.短信修改密码接口
     */
    public static final  String update_pwd= domain + "/api/user/index/update-pwd";

    /**
     * 3-16、修改密码接口
     */
    public static final  String recovered_pwd= domain + "/api/user/index/recovered-password";

    /**
     * 6.用旧密码修改新密码接口
     */
    public static final  String change_pwd = domain + "/api/user/index/change-pwd";

    /**
     * 7.个人信息验证接口
     */
    public static final  String person_identify = domain + "/api/user/investor/person-identify";

    /**
     * 8.上传图片（private）
     */
    public static final  String upload_image = domain + "/api/user/upload/image";
    /**
     * 上传图片（public）
     */
    public static final String upload_public_image = domain + "/api/user/upload/image-public";

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
     * 2-1.发布接口（开发商/个人/工长）
     */
    public static  final  String release_project = domain + "/api/index/index/add-total";

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
     * 3-13.仅仅验证验证码接口
     */
    public static final String checkCode = domain + "/api/user/index/check-code-only";

    /**
     *3-14、获取银行列表
     */
    public static final String bank_list =domain + "/api/common/bank/list";

    /**
     * 3-7 发布选择的接口 (建筑， 装修， 工程管理)  Get方式
     */
    public static final String publish_craft =domain + "/api/common/craft/publish-craft";

    /**
     *1-12、修改用户头像
     */
    public static final String head_image =domain + "/api/user/upload/head-image";

    /**
     * 1-13、返回上一步重新认证修改认证   get
     */
    public static final String reset_auth =domain + "/api/user/index/reset-auth";

    /**
     * 摇奖WebUrl
     */
    public static final String lottery = domain + "/phone/lottery/main/index";

    /**
     * 2-4 选择工头接口（开发商）
     */
    public static final String selectHeadman = domain + "/api/developers/project/choose";

    /**
     * 2-5.工头竞争工程接口
     * /api/headman/project/contend/+工程id
     * */
    public static final String headman_contend_project = domain + "/api/headman/project/contend/";
    /**
     * 2-6.工人抢单动作 + projectId +craftId;
     */

    public static final String worker_contend_project = domain +"/api/worker/project/contend/";

    /**
     * 2-8.工头邀请工人列表(工长-->人员管理)  get
     */
    public static final String invite_worker_list = domain + "/api/headman/project/invite-worker-list";

    /**
     * 2-9.工头邀请工人 加被邀请者的id
     */
    public static final String headman_invite_worker = domain + "/api/headman/project/invite/";

    /**
     * 2-10.工人推荐工长  get
     */
    public static final String worker_recommend_headman = domain + "/api/worker/project/scanning/";

    /**
     * 2-11.首页工程列表  get
     */
    public static final String home_project_info = domain + "/api/index/index/index";

    /**
     * 2-12.首页工程详细  get(开发商)
     * /api/index/index/project-info/+工程ID
     */
    public static final String project_info_detail = domain + "/api/index/index/project-info/";
    /**
     * 2-23.首页工程详情 +工程ID(工长的工程)
     */
    public static final String project_info_detail_invistor = domain + "/api/index/index/headman-project-info/";

    /**
     *2-15.工长查看推荐过他的工人列表    get
     */
    public static final String recommended_workers = domain + "/api/headman/project/recommended";
    /**
     * 2-8.工头获取符合工种工人列表（邀请）get
     */
    public static final String friend_workers = domain + "/api/headman/project/invite-worker-list";

    /**
     * 2-16.工长解除和工人的推荐关系
     */
    public static final String remove_relation = domain + "/api/headman/project/relieve/";

    /**
     *2-17.发现   get
     */
    public static final String founds = domain + "/api/index/index/founds";


    /**
     * 2-18.抢单 4种状态
     * wating_audit//待审核
     * sign_wating//待通过
     *complete//完成
     *execute//执行中
     */
    public static final String grab_status = domain + "/api/user/grab/index";

    /**
     * 1-15、劳务公司认证接口
     */
    public static final String company_identify = domain + "/api/user/company/identify";



    /**
     * 2-21.评价  post
     */
    public static final String evaluate = domain + "/api/user/evaluate/evaluate";

    /**
     * 2-22.评价列表    get
     *
     */
    public static final String evaluate_list = domain + "/api/user/evaluate/evaluate-list";

    /**
     *2-23.交易明细 get
     */
    public static final String trade_detail = domain + "/api/user/trade/detailed";

    /**
     * 2-22.交易账单    get
     *
     */
    public static final String account = domain + "/api/user/trade/dill";



    /**
     * 1-14、积分列表
     */
    public static final String score = domain + "/api/user/score/index";

    /**
     * 2-24.根据角色显示人员列表（个人中心）（开发商、个人、工人） get
     * /api/user/grab/show-people-by-category/工程ID
     */
    public static final String people_category = domain + "/api/user/grab/show-people-by-category/";

    /**
     * 2-14.工人同意工长邀请参与工程    get
     * /api/worker/project/agree-invite/+邀请表ID
     */
    public static final String agree_invite = domain + "/api/worker/project/agree-invite/";

    /**
     * 2-25  根据工程显示工种（个人中心）（工长、劳务公司）
     * /api/user/grab/show-craft-by-project/工程ID
     */
    public static final String show_craft = domain + "/api/user/grab/show-craft-by-project/";

    /**
     * 2-26  根据工种显示人员（个人中心）（工长、劳务公司）
     * /api/user/grab/show-people-by-craft/工程ID/工种ID
     */
    public static final String show_people = domain + "/api/user/grab/show-people-by-craft/";

    /**
     * 邀友返现
     */
    public static final String invite_friends = domain + "/phone/activity/main/index";

    /**
     * 4-1.发布社区活动接口 post
     */
    public static final String social_publish = domain + "/api/social/social/publish";

    /**
     * 4-2.社区活动首页展示接口   get
     */
    public static final String social_show = domain + "/api/social/social/social";


    /**
     *4-3.社区活动首页点击查看详情接口
     */
    public static final String social_detail = domain + "/api/social/social/details-publish";


    /**
     *4-4.社区活动报名
     */
    public static final String social_apply = domain + "/api/social/social/apply";

    /**
     *4-4.社区活动我的列表
     */
    public static final String social_my_list = domain + "/api/social/social/my-list";

    /**
     *4-5.修改社区活动接口
     */
    public static final String social_update_publish = domain + "/api/social/social/update-publish";




}
