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
     * @param asyncHttpResponseHandler 请求
     * @return
     */
    public static RequestHandle register(String name,String phone,String email,String gender,
                                         String code, String password, AsyncHttpResponseHandler
        asyncHttpResponseHandler ) {
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("phone", phone);
        params.put("password", password);
        params.put("email", email);
        params.put("code", code);
        params.put("gender", gender);

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
     * 9.下载图片       RequestUrl.down_image + 图片名字
     * @param url
     * @param params
     * @param asyncHttpResponseHandler
     * @return
     */

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
     * @param card_expiration_time  身份证到期时间 (选填)
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle personIdentify(String real_name,String gender, String identity_card,
                                               String province,String city,
                                               String card_image_front,String card_image_back,
                                               String card_expiration_time,
                                                AsyncHttpResponseHandler
                                                    asyncHttpResponseHandler ) {

        RequestParams params = new RequestParams();
        params.put("real_name",real_name);
        params.put("gender",gender);
        params.put("identity_card",identity_card);
        params.put("province",province);
        params.put("city",city);
        params.put("card_image_front",card_image_front);
        params.put("card_image_back",card_image_back);
        params.put("card_expiration_time",card_expiration_time);


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
        params.put("bank_id",bank_id);

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
     *
     * @param linkman_name
     * @param identity_card
     * @param card_image_front
     * @param card_image_back
     * @param linkman_phone
     * @param linkman_province
     * @param linkman_city
     * @param company_name
     * @param business_province
     * @param business_city
     * @param address
     * @param business_years
     * @param time_state
     * @param company_number
     * @param company_phone
     * @param introduce
     * @param account_name
     * @param account_province
     * @param public_account
     * @param organization_certificate
     * @param business_license_number
     * @param business_scope
     * @param bank_id
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle developerIdentify(String linkman_name, String identity_card,
                                                  String card_image_front, String card_image_back,
                                                  String linkman_phone, String linkman_province,
                                                  String linkman_city, String company_name,
                                                  String business_province, String business_city,
                                                  String address, String business_years,
                                                  String time_state, String company_number,
                                                  String company_phone, String introduce,
                                                  String account_name, String account_province,
                                                  String public_account, String organization_certificate,
                                                  String business_license_number, String business_scope,
                                                  String bank_id,
                                               AsyncHttpResponseHandler
                                                   asyncHttpResponseHandler ) {

        RequestParams params = new RequestParams();
        params.put("linkman_name",linkman_name);
        params.put("identity_card",identity_card);
        params.put("card_image_front",card_image_front);
        params.put("card_image_back",card_image_back);
        params.put("linkman_phone",linkman_phone);
        params.put("linkman_province",linkman_province);
        params.put("linkman_city",linkman_city);
        params.put("company_name",company_name);
        params.put("business_province",business_province);
        params.put("business_city",business_city);
        params.put("address",address);
        params.put("business_years",business_years);
        params.put("time_state",time_state);
        params.put("company_number",company_number);
        params.put("company_phone",company_phone);
        params.put("introduce",introduce);
        params.put("account_name",account_name);
        params.put("account_province",account_province);
        params.put("public_account",public_account);
        params.put("organization_certificate",organization_certificate);
        params.put("business_license_number",business_license_number);
        params.put("business_scope",business_scope);
        params.put("bank_id",bank_id);


        return doPost(RequestUrl.developer_identify, params, asyncHttpResponseHandler);
    }

    /**
     * 13.修改昵称接口
     * @param nickname 修改后的昵称
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle modifyNickName(String nickname,AsyncHttpResponseHandler asyncHttpResponseHandler){
        RequestParams params=new RequestParams();
        params.put("nickname",nickname);
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

    /**
     * 3-14、获取银行列表
     * @param asyncHttpResponseHandler
     * @return
     */
    public static RequestHandle bankList(AsyncHttpResponseHandler
                                                     asyncHttpResponseHandler){
        return doGet(RequestUrl.bank_list, null, asyncHttpResponseHandler);
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
        L.d(TAG, "doPost: " + url + " : " + params.toString());
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
        addCommonParams(params);
        L.d(TAG, "doGet Url : " + url + "?"+ params.toString());
        return mClient.get(url, params, asyncHttpResponseHandler);

    }

    /**
     * 取消所有请求
     */
    public static void cancelAllRequests() {
        if (null != mClient)
            mClient.cancelAllRequests(true);
    }

//    /**
//     * 上传:Post方式
//     */
//    public static void upLoad(String url, File file) {
//
//        if (!NetUtils.isConnected(MyApplication.getInstance())) {
//            T.show("网络异常");
//            return ;
//        }
//        initAsyncHttpClient();
//        File myFile = new File("/path/to/file.png");
//        RequestParams params = new RequestParams();
//        try {
//            params.put("profile_picture", myFile);
//        } catch(FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

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
            mClient.post(RequestUrl.upload_image, params, asyncHttpResponseHandler);
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
