package com.ms.ebangw.service;

import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.NetUtils;
import com.ms.ebangw.utils.T;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-13 21:44
 */
public class DataAccessUtil {
    private static final String TAG = "DataAccessUtil";

    private static AsyncHttpClient mClient;
    private static void initAsyncHttpClient() {
        if (mClient == null) {
            mClient = new AsyncHttpClient();
            //修改超时时间为15秒
            mClient.setTimeout(15000);
            mClient.setResponseTimeout(15000);
        }
    }

    /**
     * 1. 注册接口
     * @param name 姓名
     * @param phone 电话
     * @param email 邮箱
     * @param gender       性别 MALE、 FEMALE
     * @param code 验证码
     * @param password 密码
     * @param come_from 渠道名称
     * @param asyncHttpResponseHandler 请求
     * @return
     */
    public static RequestHandle register(String name,String phone,String email,String gender,
                                         String code,  String password, String come_from,
                                         AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("phone", phone);
        params.put("password", password);
        params.put("email", email);
        params.put("code", code);
        params.put("gender", gender);

        //增加渠道标识，注册时提交
        params.put("come_from", come_from);

        return doPost(RequestUrl.register, params, asyncHttpResponseHandler);
    }

    /**
     * 2.登录
     * @param phone 电话
     * @param password 密码
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle login(String phone, String password, AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("password", password);

        return doPost(RequestUrl.login, params, asyncHttpResponseHandler);
    }

    /**
     * 3.登出接口
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle exit(AsyncHttpResponseHandler
                                         asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
//        params.put("phone", phone);
//        params.put("password", password);

        return doPost(RequestUrl.logout, params, asyncHttpResponseHandler);
    }

    /**
     * 4.短信接口,普通
     * @param phone 电话
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle messageCode(String phone, AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        return doPost(RequestUrl.msg, params, asyncHttpResponseHandler);
    }
    /**
     * 4.1短信接口,注册
     * @param phone 电话
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle messageCodeRegiste(String phone, AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        return doPost(RequestUrl.msg_registe, params, asyncHttpResponseHandler);
    }




    /**
     * 5.短信修改密码接口
     * @param phone
     * @param code
     * @param password
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle updatePassword(String phone, String code, String password,
                                               AsyncHttpResponseHandler
                                                   asyncHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone",phone);
        params.put("code", code);
        params.put("password", password);

        return doPost(RequestUrl.update_pwd, params, asyncHttpResponseHandler);
    }

    /**
     * 6.用旧密码修改新密码接口
     * @param phone
     * @param password
     * @param newPassword
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle changePwd(String phone, String password, String newPassword,
                                            AsyncHttpResponseHandler
                                                asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("phone",phone);
        params.put("password", password);
        params.put("newpassword", newPassword);

        return doPost(RequestUrl.change_pwd, params, asyncHttpResponseHandler);
    }

    /**
     * 3-16、修改密码接口(找回密码)
     * @param phone
     * @param code
     * @param password
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle recoveredPassword(String phone, String code, String password,
                                               AsyncHttpResponseHandler
                                                   asyncHttpResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone",phone);
        params.put("code", code);
        params.put("password", password);

        return doPost(RequestUrl.recovered_pwd, params, asyncHttpResponseHandler);
    }

    /**
     * 7.个人信息验证接口
     * @param identity_card 身份证
     * @param phone     手机
     * @param province  省
     * @param city      市
     * @param area          区
     * @param area_other    门牌号
     * @param card_image_front  身份证正面
     * @param card_image_back       身份证反面
     * @param card_expiration_time  身份证到期时间
     * @param asyncHttpResponseHandler
     * @return
     */
//    public static RequestHandle personIdentify(String identity_card, String phone, String province,
//                                               String city, String area,String area_other,
//                                               String card_image_front,
//                                               String card_image_back, String card_expiration_time,
//                                               AsyncHttpResponseHandler
//                                              asyncHttpResponseHandler ) {
//        RequestParams params = new RequestParams();
//        params.put("identity_card",identity_card);
//        params.put("phone",phone);
//        params.put("province",province);
//        params.put("city",city);
//        params.put("area",area);
//        params.put("area_other",area_other);
//        params.put("card_image_front",card_image_front);
//        params.put("card_image_back",card_image_back);
//        params.put("card_expiration_time",card_expiration_time);
//
//        return doPost(RequestUrl.person_identify, params, asyncHttpResponseHandler);
//    }

    /**
     * 2-1.开发商发布的接口
     * @param title
     * @param description
     * @param link_man
     * @param link_phone
     * @param province
     * @param city

     * @param area_other
     * @param point_longitude
     * @param image_ary
     * @param staffs
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle releaseProject(String title, String description, String link_man,
                                               String link_phone, String province, String city,
                                               String area_other, float point_longitude,
                                               float point_latitude,
                                               String image_ary, String start_time,
                                               String end_time, String project_money, String staffs, AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("title",title);
        params.put("description",description);
        params.put("link_man",link_man);
        params.put("link_phone",link_phone);
        params.put("province",province);
        params.put("city",city);
        params.put("area_other",area_other);
        params.put("point_longitude",point_longitude);
        params.put("point_latitude",point_latitude);
        params.put("start_time",start_time);
        params.put("end_time", end_time);
        params.put("project_money",project_money);
        params.put("image_ary",image_ary);
        params.put("staffs", staffs);
        //T.show("能进来1");
        return doPost(RequestUrl.release_project, params, asyncHttpResponseHandler);

    }

    /**
     * 工头发布
     * @param title
     * @param description
     * @param link_man
     * @param link_phone
     * @param province
     * @param city
     * @param area_other
     * @param point_longitude
     * @param point_latitude
     * @param image_ary
     * @param start_time
     * @param end_time
     * @param project_money
     * @param staffs
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle headmanRelease(String title, String description, String link_man,
                                                 String link_phone, String province, String city,
                                                 String area_other, float point_longitude,
                                                 float point_latitude,
                                                 String image_ary,String start_time,
                                                 String end_time, String project_money,String staffs, AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("title",title);
        params.put("description",description);
        params.put("link_man",link_man);
        params.put("link_phone",link_phone);
        params.put("province",province);
        params.put("city",city);
        params.put("area_other",area_other);
        params.put("point_longitude",point_longitude);
        params.put("point_latitude",point_latitude);
        params.put("start_time",start_time);
        params.put("end_time", end_time);
        params.put("project_money",project_money);
        params.put("image_ary",image_ary);
        params.put("staffs", staffs);
        //T.show("能进来1");
        return doPost(RequestUrl.release_project, params, asyncHttpResponseHandler);

    }

//    /**
//     * 2-3.发布个人
//     * @param title
//     * @param description
//     * @param link_man
//     * @param link_phone
//     * @param province
//     * @param city
//     * @param area_other
//     * @param point_longitude
//     * @param point_latitude
//     * @param image_ary
//     * @param start_time
//     * @param end_time
//     * @param project_money
//     * @param staffs
//     * @param asyncHttpResponseHandler
//     * @return
//     */
//    public static  RequestHandle investorRelease(String title, String description, String link_man,
//                                                 String link_phone, String province, String city,
//                                                 String area_other, float point_longitude,
//                                                 float point_latitude,
//                                                 String image_ary,String start_time,
//                                                 String end_time, String project_money,String staffs, AsyncHttpResponseHandler asyncHttpResponseHandler){
//        RequestParams params = new RequestParams();
//        params.put("title",title);
//        params.put("description",description);
//        params.put("link_man",link_man);
//        params.put("link_phone",link_phone);
//        params.put("province",province);
//        params.put("city",city);
//        params.put("area_other",area_other);
//        params.put("point_longitude",point_longitude);
//        params.put("point_latitude",point_latitude);
//        params.put("start_time",start_time);
//        params.put("end_time", end_time);
//        params.put("project_money",project_money);
//        params.put("image_ary",image_ary);
//        params.put("staffs", staffs);
//
//        return doPost(RequestUrl.investor_release, params, asyncHttpResponseHandler);
//    }



    /**
     * 8.上传图片
     * @param imageFile 图片文件
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle uploadImage(File imageFile,
                                          AsyncHttpResponseHandler asyncHttpResponseHandler ) {

        return upLoadImage(RequestUrl.upload_image, imageFile, asyncHttpResponseHandler);
    }

    /**
     * 上传图片（public）
     * @param imageFile
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle uploadPublicImage(File imageFile, AsyncHttpResponseHandler asyncHttpResponseHandler){

        return upLoadImage(RequestUrl.upload_public_image, imageFile, asyncHttpResponseHandler);
    }

    /**
     * 9.下载图片       RequestUrl.down_image + 图片名字
     * @param url
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle LoadImage(String url,AsyncHttpResponseHandler asyncHttpResponseHandler ){
//        String imageUrl = getImageUrl(url);
        return doGet(url, null, asyncHttpResponseHandler);
    }

    /**
     * 10.省市区接口 /api/common/address/child/+数字（0返回所有省与直辖市）   Get方式
     * @param num   默认0 (0返回所有省与直辖市)
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle address(String num,
                                          AsyncHttpResponseHandler
                                              asyncHttpResponseHandler ) {
        if (TextUtils.isEmpty(num)) {
            num = "0";
        }
        return doGet(RequestUrl.address + num, new RequestParams(), asyncHttpResponseHandler);
    }



    /**
     * 1-6.个人信息验证接口
     * @param real_name 真实姓名
     * @param identity_card 身份证
     * @param province  省
     * @param city      市
     * @param card_image_front  身份证正面
     * @param card_image_back   身份证反面
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle personIdentify(String real_name,String gender, String identity_card,
                                               String province,String city,
                                               String card_image_front,String card_image_back,
                                               String card_number,String open_account_name,
                                               String open_account_province,String open_account_city,
                                               String bank_id,
                                                AsyncHttpResponseHandler
                                                    asyncHttpResponseHandler ) {

        RequestParams params = new RequestParams();
        params.put("real_name",real_name);
        params.put("gender",gender);
        params.put("identity_card",identity_card);
        params.put("province",province);
        params.put("city",city);
        params.put("card_image_front",card_image_front);
        params.put("card_image_back", card_image_back);
        params.put("card_number",card_number);
        params.put("open_account_name",open_account_name);
        params.put("open_account_province",open_account_province);
        params.put("open_account_city",open_account_city);
        params.put("bank_id", bank_id);
        return doPost(RequestUrl.penson_identify, params, asyncHttpResponseHandler);
    }


    /**
     *11.工头认证接口
     * @param real_name 真实姓名
     * @param identity_card 身份证
     * @param province  省
     * @param city      市
     * @param card_image_front  身份证正面
     * @param card_image_back   身份证反面
     * @param gender    性别
     * @param invitation_code   邀请码
     * @param card_number   银行卡号
     * @param open_account_name 开户名称
     * @param open_account_province 开户行所在省份
     * @param open_account_city 开户行所在城市
     * @param bank_id   开户行名称
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle headmanIdentify(String real_name,String identity_card,
                                                String province,String city,
                                                String card_image_front,String card_image_back,
                                                String gender,String invitation_code,
                                                String card_number,String open_account_name,
                                                String open_account_province,String open_account_city,
                                                String bank_id,
                                        AsyncHttpResponseHandler
                                            asyncHttpResponseHandler ) {

        RequestParams params = new RequestParams();
        params.put("real_name",real_name);
        params.put("identity_card",identity_card);
        params.put("province",province);
        params.put("city",city);
        params.put("card_image_front",card_image_front);
        params.put("card_image_back",card_image_back);
        params.put("gender",gender);
        params.put("invitation_code",invitation_code);
        params.put("card_number",card_number);
        params.put("open_account_name",open_account_name);
        params.put("open_account_province",open_account_province);
        params.put("open_account_city",open_account_city);
        params.put("bank_id", bank_id);

        return doPost(RequestUrl.header_identify, params, asyncHttpResponseHandler);
    }


    /**
     *1-8.工人认证接口
     * @param real_name 真实姓名
     * @param identity_card 身份证
     * @param province  省
     * @param city      市
     * @param card_image_front  身份证正面
     * @param card_image_back   身份证反面
     * @param gender    性别
     * @param card_number   银行卡号
     * @param open_account_name 开户名称
     * @param open_account_province 开户行所在省份
     * @param open_account_city 开户行所在城市
     * @param bank_id   开户行名称
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle workerIdentify(String real_name,String identity_card,
                                                String province,String city,
                                                String card_image_front,String card_image_back,
                                                String gender,
                                                String card_number,String open_account_name,
                                                String open_account_province,String open_account_city,
                                                String bank_id,  String crafts,
                                                AsyncHttpResponseHandler
                                                    asyncHttpResponseHandler ) {

        RequestParams params = new RequestParams();
        params.put("real_name",real_name);
        params.put("identity_card",identity_card);
        params.put("province",province);
        params.put("city",city);
        params.put("card_image_front",card_image_front);
        params.put("card_image_back",card_image_back);
        params.put("gender",gender);
        params.put("card_number",card_number);
        params.put("open_account_name",open_account_name);
        params.put("open_account_province",open_account_province);
        params.put("open_account_city",open_account_city);
        params.put("bank_id",bank_id);
        params.put("crafts",crafts);

        return doPost(RequestUrl.worker_identify, params, asyncHttpResponseHandler);
    }

    /**
     *1-9.开发商认证接口
     * @param real_name 联系人
     * @param identity_card 身份证号(18位字符)
     * @param card_image_front  身份证正面(id)
     * @param card_image_back   身份证反面(id)
     * @param linkman_phone 手机号码(11位数字)
     * @param province  省分(id)
     * @param city  市(id)
     * @param company_name     企业名称
     * @param business_province 营业执照所在省(id)
     * @param business_city 营业执照所在市(id)
     * @param address   常住地址
     * @param business_years    营业年限(数字)
     * @param time_state    长期(值为1是长期 2为非长期）
     * @param company_number    组织结构代码证号
     * @param company_phone 公司座机
     * @param introduce 企业介绍
     * @param open_account_name 户名
     * @param open_account_province 开户地点(id
     * @param card_number   对公账户
     * @param organization_certificate  图片关联id组织机构代码证扫描件
     * @param business_license_number   营业执照注册号
     * @param business_scope    经营范围
     * @param bank_id   开户银行(id)
     * @param open_account_city 开户城市(id)
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle developerIdentify(String real_name, String identity_card,
                                                  String card_image_front, String card_image_back,
                                                  String linkman_phone, String province,
                                                  String city, String company_name,
                                                  String business_province, String business_city,
                                                  String address, String business_years,
                                                  String time_state, String company_number,
                                                  String company_phone, String introduce,
                                                  String open_account_name, String open_account_province,
                                                  String card_number, String organization_certificate,
                                                  String business_license_number, String business_scope,
                                                  String bank_id, String gender, String
                                                      open_account_city,
                                               AsyncHttpResponseHandler
                                                   asyncHttpResponseHandler ) {

        RequestParams params = new RequestParams();
        params.put("real_name",real_name);
        params.put("identity_card",identity_card);
        params.put("card_image_front",card_image_front);
        params.put("card_image_back",card_image_back);
        params.put("linkman_phone",linkman_phone);
        params.put("province",province);
        params.put("city",city);
        params.put("company_name",company_name);
        params.put("business_province",business_province);
        params.put("business_city",business_city);
        params.put("address",address);
        params.put("business_years",business_years);
        params.put("time_state",time_state);
        params.put("company_number",company_number);
        params.put("company_phone",company_phone);
        params.put("introduce",introduce);
        params.put("open_account_name",open_account_name);
        params.put("open_account_province",open_account_province);
        params.put("card_number",card_number);
        params.put("organization_certificate",organization_certificate);
        params.put("business_license_number",business_license_number);
        params.put("business_scope",business_scope);
        params.put("bank_id",bank_id);
        params.put("gender",gender);
        params.put("open_account_city",open_account_city);

        return doPost(RequestUrl.developer_identify, params, asyncHttpResponseHandler);
    }


    /**
     *1-15、劳务公司认证接口
     * @param real_name 联系人
     * @param identity_card 身份证号(18位字符)
     * @param card_image_front  身份证正面(id)
     * @param card_image_back   身份证反面(id)
     * @param linkman_phone 手机号码(11位数字)
     * @param province  省分(id)
     * @param city  市(id)
     * @param company_name     企业名称
     * @param business_province 营业执照所在省(id)
     * @param business_city 营业执照所在市(id)
     * @param address   常住地址
     * @param business_years    营业年限(数字)
     * @param time_state    长期(值为1是长期 2为非长期）
     * @param company_number    组织结构代码证号
     * @param company_phone 公司座机
     * @param introduce 企业介绍
     * @param open_account_name 户名
     * @param open_account_province 开户地点(id
     * @param card_number   对公账户
     * @param organization_certificate  图片关联id组织机构代码证扫描件
     * @param business_license_number   营业执照注册号
     * @param business_scope    经营范围
     * @param bank_id   开户银行(id)
     * @param open_account_city 开户城市(id)
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle companyIdentify(String real_name, String identity_card,
                                                  String card_image_front, String card_image_back,
                                                  String linkman_phone, String province,
                                                  String city, String company_name,
                                                  String business_province, String business_city,
                                                  String address, String business_years,
                                                  String time_state, String company_number,
                                                  String company_phone, String introduce,
                                                  String open_account_name, String open_account_province,
                                                  String card_number, String organization_certificate,
                                                  String business_license_number, String business_scope,
                                                  String bank_id, String gender, String
                                                      open_account_city,
                                                  AsyncHttpResponseHandler
                                                      asyncHttpResponseHandler ) {

        RequestParams params = new RequestParams();
        params.put("real_name",real_name);
        params.put("identity_card",identity_card);
        params.put("card_image_front",card_image_front);
        params.put("card_image_back",card_image_back);
        params.put("linkman_phone",linkman_phone);
        params.put("province",province);
        params.put("city",city);
        params.put("company_name",company_name);
        params.put("business_province",business_province);
        params.put("business_city",business_city);
        params.put("address",address);
        params.put("business_years",business_years);
        params.put("time_state",time_state);
        params.put("company_number",company_number);
        params.put("company_phone",company_phone);
        params.put("introduce",introduce);
        params.put("open_account_name",open_account_name);
        params.put("open_account_province",open_account_province);
        params.put("card_number",card_number);
        params.put("organization_certificate",organization_certificate);
        params.put("business_license_number",business_license_number);
        params.put("business_scope",business_scope);
        params.put("bank_id",bank_id);
        params.put("gender",gender);
        params.put("open_account_city",open_account_city);

        return doPost(RequestUrl.company_identify, params, asyncHttpResponseHandler);
    }

    /**
     * 13.修改昵称接口
     * @param nickname 修改后的昵称
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle modifyNickName(String nickname,AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params=new RequestParams();
        params.put("nick_name", nickname);
        return doPost(RequestUrl.modify_nickName, params, asyncHttpResponseHandler);
    }

    /**
     * 14.修改绑定手机接口
     * @param phone
     * @param code
     * @param asyncHttpResponseHandler
     * @return
     */
    public static  RequestHandle modifyPhone(String phone,String code,AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params=new RequestParams();
        params.put("code",code);
        params.put("phone",phone);
        return  doPost(RequestUrl.modify_phone, params, asyncHttpResponseHandler);

    }

    /**
     * 3-6. 获取全部省市区数据       get
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle provinceCityArea(AsyncHttpResponseHandler
        asyncHttpResponseHandler){
        return doGet(RequestUrl.province_city_area, null, asyncHttpResponseHandler);
    }


    /**
     * 3-7 发布选择的接口 (建筑， 装修， 工程管理)  Get方式
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle publishCraft(AsyncHttpResponseHandler
                                                     asyncHttpResponseHandler){
        return doGet(RequestUrl.publish_craft, null, asyncHttpResponseHandler);
    }

    public static  RequestHandle checkCode(String phone, String code, AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("code", code);
        return  doPost(RequestUrl.checkCode, params, asyncHttpResponseHandler);
    }

    /**
     * 3-14、获取银行列表
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle bankList(AsyncHttpResponseHandler
                                                     asyncHttpResponseHandler){
        return doGet(RequestUrl.bank_list, null, asyncHttpResponseHandler);
    }

    /**
     * 1-11、获取用户信息接口 get方式
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle userInformation(AsyncHttpResponseHandler
                                             asyncHttpResponseHandler){
        return doGet(RequestUrl.user_information, null, asyncHttpResponseHandler);
    }

    public static RequestHandle inviteWorkerList(String project_id, String craft_id, String phone,
                                                 AsyncHttpResponseHandler
                                                    asyncHttpResponseHandler){
        return doGet(RequestUrl.invite_worker_list, null, asyncHttpResponseHandler);
    }


    /**
     * 2-10.工人推荐工长  (扫码推荐)
     * @param id    工长的Id
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle workerRecommendHeadman(String id, AsyncHttpResponseHandler
                                           asyncHttpResponseHandler){
//        RequestParams params = new RequestParams();
//        params.put("id", id);
        return doGet(RequestUrl.worker_recommend_headman + id, null, asyncHttpResponseHandler);
    }



    /**
     * 1-12、修改用户头像  get方式
     */
    public static RequestHandle headImage(File imageFile, AsyncHttpResponseHandler
        asyncHttpResponseHandler) {

        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            T.show("网络异常,请检查网络连接");
            return null;
        }
        initAsyncHttpClient();
        RequestParams params = new RequestParams();
        try {
            params.put("image", imageFile, "image/png");
            params = addCommonParams(params);
            mClient.post(RequestUrl.head_image, params, asyncHttpResponseHandler);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            L.d(TAG, "headImage：文件不存在");
        }
        return null;
    }

    /**
     * 1-13、返回上一步重新认证修改认证
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle resetAuth(AsyncHttpResponseHandler
        asyncHttpResponseHandler){
        return doGet(RequestUrl.reset_auth, null, asyncHttpResponseHandler);
    }

    /**
     * 2-4 选择工头接口（开发商）
     * @param project_id
     * @param contend_id
     * @param asyncHttpResponseHandler
     * @return
     */

    public static RequestHandle selectHeadman(String project_id, String contend_id, AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("project_id",project_id);
        params.put("contend_id", contend_id);
        return doGet(RequestUrl.selectHeadman, params, asyncHttpResponseHandler);
    }
    /**
     *2-5.工头竞争工程接口
     * @param projectId 工程Id
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle headmanContendProject(String projectId, AsyncHttpResponseHandler
            asyncHttpResponseHandler){
        return doGet(RequestUrl.headman_contend_project + projectId, null, asyncHttpResponseHandler);
    }

    /**
     * 2-6.工人抢单动作 + projectId +craftId;
     * @param projectId
     * @param craftId
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle workerContendProject(String projectId, String craftId, AsyncHttpResponseHandler asyncHttpResponseHandler){
        return doGet(RequestUrl.worker_contend_project + projectId +"/+"+craftId, null, asyncHttpResponseHandler);
    }
    /**
     *2-11.首页工程列表
     * @param latitude 纬度
     * @param longitude 经度
     * @param page 页数
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle homeProjectInfo( String page, String latitude, String longitude,
                                                AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        return doGet(RequestUrl.home_project_info, params, asyncHttpResponseHandler);
    }

    /**
     *2-12.首页工程详细（开发商）
     * @param projectId 工程Id
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle projectInfoDetail(String projectId, AsyncHttpResponseHandler
                                                    asyncHttpResponseHandler){
        return doGet(RequestUrl.project_info_detail + projectId, null, asyncHttpResponseHandler);
    }

    /**
     * 2-23.首页工程详情（工长的工程）
     * @param projectId
     * @param asyncHttpResponseHandler
     * @return
     */

    public static RequestHandle projectInfoDetailInvistor(String projectId, AsyncHttpResponseHandler asyncHttpResponseHandler){

        return doGet(RequestUrl.project_info_detail_invistor + projectId, null, asyncHttpResponseHandler);
    }


    /**
     * 2-15.工长查看推荐过他的工人列表    get
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle recommendWorkers(AsyncHttpResponseHandler asyncHttpResponseHandler){
        return doGet(RequestUrl.recommended_workers, null, asyncHttpResponseHandler);
    }

    /**
     * 2-24.根据角色显示人员列表（个人中心）（开发商、个人、工人）
     * @param projectId 工程id
     * @param category  工长headman 劳务公司company
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle peopleCategory(String projectId, String
        category, AsyncHttpResponseHandler asyncHttpResponseHandler) {

        RequestParams params = new RequestParams();
        params.put("category", category);
        return doGet(RequestUrl.people_category + projectId, params, asyncHttpResponseHandler);
    }
    /**
     * 2-8.工头获取符合工种工人列表（邀请）
     * @param project_id
     * @param craft_id
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle friendWorkers(String project_id, String craft_id,AsyncHttpResponseHandler
                                              asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("project_id", project_id);
        params.put("craft_id", craft_id);
        return doGet(RequestUrl.friend_workers, params, asyncHttpResponseHandler);
    }

    /**
     * 2-9.工头邀请工人 加被邀请者的id
     * @param workerId
     * @param project_id
     * @param craft_id
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle inviteWorker(String workerId , String project_id, String craft_id,AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("project_id",project_id);
        params.put("craft_id", craft_id);
        return doGet(RequestUrl.headman_invite_worker+workerId, params, asyncHttpResponseHandler);
    }

    /**
     *  2-14.工人同意工长邀请参与工程
     *  @param contend_id 邀请表Id
     * @param type  agree(同意)、 refused（拒绝）
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle agreeInvite(String contend_id, String type, AsyncHttpResponseHandler
        asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("type",type);
        return doGet(RequestUrl.agree_invite + contend_id, params, asyncHttpResponseHandler);
    }

    /**
     * 2-16.工长解除和工人的推荐关系
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle removeRelation(String workerId, AsyncHttpResponseHandler
                                                       asyncHttpResponseHandler){
        return doGet(RequestUrl.remove_relation + workerId, null, asyncHttpResponseHandler);
    }

    /**
     * 2-17.发现
     * @param craft_id  工种  （非必填）
     * @param distance  距离  （非必填）
     * @param page  第几页
     * @param latitude   纬度
     * @param longitude   经度
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle founds(String craft_id,String distance,String page,String latitude,String longitude, AsyncHttpResponseHandler
        asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("craft_id", craft_id);
        params.put("distance", distance);
        params.put("page", page);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        return doGet(RequestUrl.founds, params, asyncHttpResponseHandler);
    }


    /**
     * 2-18 抢单 4种状态 抢单 待通过, 进行中, 已结束（工头）  get
     * @param page 分页
     * @param status wating_audit//待审核   sign_wating//待通过   execute//执行中 complete//完成
     * @param type  grab//抢单 （工长、劳务公司、工人）   publish//发布 （开发商、个人、工长）  invite//邀请我的（只有工人有）
     * @param invite  选填  agree//同意 invite//邀请中   根据type选择填写，如果type=invite,此字段必须填，如果type=grab,
     *                此字段传空字符串
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle grabStatus(String page, String status, String
        type,  String invite, AsyncHttpResponseHandler
        asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("status", status);
        params.put("type", type);
        params.put("invite", invite);
        return doGet(RequestUrl.grab_status, params, asyncHttpResponseHandler);
    }


    /**
     * 2-21.评价  post
     * @param project_id
     * @param content
     * @param anonymity
     * @param type
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle evaluate(String project_id,
                                         String content, String anonymity, String type,  AsyncHttpResponseHandler
        asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("project_id", project_id);
        params.put("content", content);
        params.put("anonymity", anonymity);
        params.put("type", type);

        return doPost(RequestUrl.evaluate, params, asyncHttpResponseHandler);
    }

    /**
     * 2-22.评价列表
     * @param page
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle evaluateList(String page,  AsyncHttpResponseHandler
                                             asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("page", page);

        return doGet(RequestUrl.evaluate_list, params, asyncHttpResponseHandler);
    }

    /**
     * 2-23.交易明细 get
     * @param page
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle tradeDetail(String page,  AsyncHttpResponseHandler
        asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("page", page);

        return doGet(RequestUrl.trade_detail, params, asyncHttpResponseHandler);
    }

    /**
     * 2-22.交易账单
     * @param page
     * @param search_time
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle account(String page, String search_time,  AsyncHttpResponseHandler
        asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("search_time", search_time);

        return doGet(RequestUrl.account, params, asyncHttpResponseHandler);
    }

    /**
     * 2-25  根据工程显示工种（个人中心）（工长、劳务公司）
     * @param projectId
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle showCraft(String projectId, AsyncHttpResponseHandler
        asyncHttpResponseHandler){
        return doGet(RequestUrl.show_craft + projectId, null, asyncHttpResponseHandler);
    }

    /**
     * 2-26  根据工种显示人员（个人中心）（工长、劳务公司）
     * @param projectId
     * @param workTypeId
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle showPeople(String projectId, String workTypeId,
                                           String type, AsyncHttpResponseHandler
        asyncHttpResponseHandler){
        RequestParams params = new RequestParams();
        params.put("type", type);
        return doGet(RequestUrl.show_people + projectId + "/" + workTypeId, params, asyncHttpResponseHandler);
    }

    /**
     * 1-14、积分列表
     * @param page
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle score(String page, AsyncHttpResponseHandler
        asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("page", page);

        return doGet(RequestUrl.score, params, asyncHttpResponseHandler);
    }


    /**
     * 4-1.发布社区活动接口 post
     * @param title
     * @param province
     * @param city
     * @param area_other    地址
     * @param number_people
     * @param start_time
     * @param end_time
     * @param theme
     * @param active_image
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle socialPublish(String title, String province, String city,
                                              String area_other, String number_people, String start_time,
                                              String end_time, String price, String theme, String
                                                  active_image,
                                              AsyncHttpResponseHandler asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("title", title);
        params.put("province", province);
        params.put("city", city);
        params.put("area_other", area_other);
        params.put("number_people", number_people);
        params.put("start_time", start_time);
        params.put("end_time", end_time);
        params.put("price", price);
        params.put("theme", theme);
        params.put("active_image", active_image);

        return doPost(RequestUrl.social_publish, params, asyncHttpResponseHandler);
    }

    /**
     * 4-2.社区活动首页展示接口
     * @param page
     * @param provinceId
     * @param cityId
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle socialShow(String page, String provinceId, String cityId,
                                              AsyncHttpResponseHandler asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("province", provinceId);
        params.put("area", cityId);

        return doGet(RequestUrl.social_show, params, asyncHttpResponseHandler);
    }

    /**
     * 4-3.社区活动首页点击查看详情接口   get
     * @param active_id
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle socialPartyDetail(String active_id,
                                              AsyncHttpResponseHandler asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("active_id", active_id);

        return doGet(RequestUrl.social_detail, params, asyncHttpResponseHandler);
    }


    /**
     * 4-4.社区活动报名
     * @param active_id
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle socialPartyApply(String active_id,
                                                  AsyncHttpResponseHandler asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("active_id", active_id);

        return doGet(RequestUrl.social_apply, params, asyncHttpResponseHandler);
    }

    /**
     * 4-4.社区活动我的列表
     * @param page
     * @param flag  flag:1代表进行中，2代表审核中，4代表已结束，3代表审核失败，不传代表全部
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle socialMyPartyList(String page, String flag,
                                                  AsyncHttpResponseHandler asyncHttpResponseHandler){

        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("flag", flag);

        return doGet(RequestUrl.social_my_list, params, asyncHttpResponseHandler);
    }


    public static RequestHandle doPost(String url, RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler) {

        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            T.show("网络异常");
            return null;
        }
        initAsyncHttpClient();
        if (params == null) {
            params = new RequestParams();
        }
        addCommonParams(params);
        L.d(TAG, "doPost: " + url + "?" + params.toString());
        return mClient.post(url, params, asyncHttpResponseHandler);
    }






    public static RequestHandle doGet(String url, RequestParams params, AsyncHttpResponseHandler
        asyncHttpResponseHandler) {

        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            T.show("网络异常");
            return null;
        }
        initAsyncHttpClient();
        if (params == null) {
            params = new RequestParams();
        }
        params = addCommonParams(params);
        L.d(TAG, "doGet Url : " + url + "?" + params.toString());
        return mClient.get(url, params, asyncHttpResponseHandler);

    }

    /**
     * 取消所有请求
     */
    public static void cancelAllRequests() {
        if (null != mClient)
            mClient.cancelAllRequests(true);
    }


    /**
     * 上传图片:Post方式
     */
    private static RequestHandle upLoadImage(String url, File imageFile, AsyncHttpResponseHandler
        asyncHttpResponseHandler) {

        if (!NetUtils.isConnected(MyApplication.getInstance())) {
            T.show("网络异常,请检查网络连接");
            return null;
        }
        initAsyncHttpClient();
        RequestParams params = new RequestParams();
        try {
            params.put("image", imageFile, "image/png");
            params = addCommonParams(params);
            mClient.post(url, params, asyncHttpResponseHandler);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            L.d(TAG, "upLoadImage：文件不存在");
        }
        return null;
    }

    private static RequestParams addCommonParams(RequestParams params) {
        User user = MyApplication.getInstance().getUser();
        if (null != user) {

            String id = user.getId();
            String app_token = user.getApp_token();
            params.put("id", id);
            params.put("app_token", app_token);
            params.put("os", "android");
        }

        return params;
    }

    /**
     * 获取包含有id和app_token的图片Url
     * @param url
     * @return
     */
    public static String getImageUrl(String url) {
        User user = MyApplication.getInstance().getUser();
        if (null != user) {
            String id = user.getId();
            String app_token = user.getApp_token();
            url = url + "?id=" + id + "&app_token=" + app_token;
            return url;
        }
        return url;
    }
}  
