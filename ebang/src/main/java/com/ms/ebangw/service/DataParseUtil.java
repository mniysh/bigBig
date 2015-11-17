package com.ms.ebangw.service;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.adapter.Evaluate;
import com.ms.ebangw.bean.Account;
import com.ms.ebangw.bean.Area;
import com.ms.ebangw.bean.Bank;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Craft;
import com.ms.ebangw.bean.HomeProjectInfo;
import com.ms.ebangw.bean.JiFen;
import com.ms.ebangw.bean.ProjectInfoDetail;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.Trade;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.bean.Worker;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.release.PayingActivity;
import com.ms.ebangw.setting.SettingAllActivity;
import com.ms.ebangw.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-09-14 09:45
 */
public class DataParseUtil {
    private static final String TAG = "DataParseUtil";


    /**
     * 注册接口
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static User register(JSONObject jsonObject) throws ResponseException {
        String  data = processDataStr(jsonObject);
        Gson gson = new Gson();
        User user = gson.fromJson(data, User.class);
        return user;
    };


    /**
     * 登录
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static User login(JSONObject jsonObject) throws ResponseException {
                String  data = processDataStr(jsonObject);
                Gson gson = new Gson();
                User user = gson.fromJson(data, User.class);
                return user;
    };

    /**
     * 获取用户的基本信息
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static User userInformation(JSONObject jsonObject) throws ResponseException {
        User user = MyApplication.getInstance().getUser();
        JSONObject data = processData(jsonObject);
        if (data == null){
            return null;
        }
        try {
            User baseUser = null;
            if (data.has("base_message")) {
                String base_message = data.getString("base_message");
                Gson gson = new Gson();
                baseUser = gson.fromJson(base_message, User.class);
                if (null != base_message) {
                    baseUser.setApp_token(user.getApp_token());
                }
            }

            if (data.has("real_message") && null != baseUser) {

                JSONObject realMessageObj = data.getJSONObject("real_message");
                String area = realMessageObj.getString("area");
                String real_name = realMessageObj.getString("real_name");
                String identity_card = realMessageObj.getString("identity_card");
                String card_image_front = realMessageObj.getString("card_image_front");
                String card_image_back = realMessageObj.getString("card_image_back");
                String craft = realMessageObj.getString("craft");

                baseUser.setArea(area);
                baseUser.setReal_name(real_name);
                baseUser.setIdentity_card(identity_card);
                baseUser.setCard_image_front(card_image_front);
                baseUser.setCard_image_back(card_image_back);
                baseUser.setCraft(craft);

            }
            return baseUser;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 登出接口
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static boolean exit(JSONObject jsonObject) throws ResponseException {
        return processDataResult(jsonObject);
    };

    /**
     * 短信接口
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static boolean messageCode(JSONObject jsonObject) throws ResponseException {
        return processDataResult(jsonObject);
    };


    /**
     * 修改昵称接口
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static boolean modifyName(JSONObject jsonObject)throws  ResponseException{
        return processDataResult(jsonObject);

    }

    /**
     * 上传图片的返回结果
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static UploadImageResult upLoadImage(JSONObject jsonObject)throws  ResponseException{
        String result = processDataStr(jsonObject);
        Gson gson = new Gson();
        UploadImageResult uploadImageResult = gson.fromJson(result, UploadImageResult.class);
        return uploadImageResult;

    }

    public static boolean modifyPassword(JSONObject jsonObject)throws  ResponseException{

        return processDataResult(jsonObject);
    }
    public static  boolean modifyPhone(JSONObject jsonObject)throws  ResponseException{

        return  processDataResult(jsonObject);
    }

    /**
     * 3-14、获取银行列表
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static  List<Bank> bankList(JSONObject jsonObject)throws  ResponseException{
        String dataStr = processDataStr(jsonObject);
        Gson gson = new Gson();
        List<Bank> banks = gson.fromJson(dataStr, new TypeToken<List<Bank>>(){}.getType());

        return  banks;
    }






    /**
     * 3-6. 获取全部省市区数据
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static TotalRegion provinceCityArea(JSONObject jsonObject)throws  ResponseException{
        String s = jsonObject.toString();
        System.out.print(s);
        JSONObject datas=processData(jsonObject);
        Gson gson=new Gson();
        String dataProvince=datas.optString("province");
        JSONObject cityobj=datas.optJSONObject("city");
        JSONObject areaObj=datas.optJSONObject("area");
        //通过goon工具转换为集合
        List<Province> provinces=gson.fromJson(dataProvince, new TypeToken<List<Province>>() {
        }.getType());

        Province province;
        for(int i=0; i<provinces.size(); i++){
            province=provinces.get(i);
            String id=province.getId();
            if(!cityobj.has(id)){
                continue;
            }
            try {
                String datacity = cityobj.getString(id);
                List<City> citys = gson.fromJson(datacity,new TypeToken<List<City>>(){}.getType());
                City city;
                for (int j=0; j < citys.size() ;j++){
                    city=citys.get(j);
                    String idCity = city.getId();
                    if(!areaObj.has(idCity)){
                        continue;
                    }
                    String dataArea = areaObj.getString(idCity);
                    List<Area> areas = gson.fromJson(dataArea, new TypeToken<List<Area>>(){}.getType());
                    city.setAreas(areas);

                }
                province.setCitys(citys);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        TotalRegion totalRegion = new TotalRegion();
        totalRegion.setProvince(provinces);

        return totalRegion;

    }

    /**
     * 3-7 发布选择的接口 (建筑， 装修， 工程管理)
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static Craft publishCraft(JSONObject jsonObject) throws  ResponseException{

        Craft craft = new Craft();
        JSONObject data = processData(jsonObject);
        if (data == null){
            return null;
        }
        Gson gson = new Gson();
        try {
            String first = data.getString("first");
            List<WorkType> mainWorkTypes = gson.fromJson(first, new TypeToken<List<WorkType>>() {
            }.getType());

            JSONObject secondObj = data.getJSONObject("second");
            JSONObject thirdObj = data.getJSONObject("third");

            for (int i = 0; i < mainWorkTypes.size(); i++) {
                WorkType type = mainWorkTypes.get(i);
                String  mainTypeId = type.getId();
                if (TextUtils.equals("1", mainTypeId)) {            //工程管理
                    craft.setProjectManage(type);
                    if (secondObj.has(mainTypeId)) {
                        String arrayStr = secondObj.getString(mainTypeId);
                        List<WorkType> secondWorkTypes = gson.fromJson(arrayStr, new TypeToken<List<WorkType>>() {
                        }.getType());
                        type.setWorkTypes(secondWorkTypes);
                    }

                }else if (TextUtils.equals("14", mainTypeId)) {     //建筑类
                    craft.setBuilding(type);
                    if (secondObj.has(mainTypeId)) {
                        String arrayStr = secondObj.getString(mainTypeId);
                        List<WorkType> secondWorkTypes = gson.fromJson(arrayStr, new TypeToken<List<WorkType>>() {
                        }.getType());
                        for (int j = 0; j < secondWorkTypes.size(); j++) {
                            WorkType secondType = secondWorkTypes.get(j);
                            String secondTypeId = secondType.getId();
                            if (thirdObj.has(secondTypeId)) {
                                String thirdArrayStr = thirdObj.getString(secondTypeId);
                                List<WorkType> thirdWorkTypes = gson.fromJson(thirdArrayStr, new TypeToken<List<WorkType>>() {
                                }.getType());
                                secondType.setWorkTypes(thirdWorkTypes);
                            }
                        }
                        type.setWorkTypes(secondWorkTypes);
                    }
                }else if (TextUtils.equals("68", mainTypeId)) {     //装修类
                    craft.setFitment(type);
                    if (secondObj.has(mainTypeId)) {
                        String arrayStr = secondObj.getString(mainTypeId);
                        List<WorkType> secondWorkTypes = gson.fromJson(arrayStr, new TypeToken<List<WorkType>>() {
                        }.getType());
                        for (int j = 0; j < secondWorkTypes.size(); j++) {
                            WorkType secondType = secondWorkTypes.get(j);
                            String secondTypeId = secondType.getId();
                            if (thirdObj.has(secondTypeId)) {
                                String thirdArrayStr = thirdObj.getString(secondTypeId);
                                List<WorkType> thirdWorkTypes = gson.fromJson(thirdArrayStr, new TypeToken<List<WorkType>>() {
                                }.getType());
                                secondType.setWorkTypes(thirdWorkTypes);
                            }
                        }
                        type.setWorkTypes(secondWorkTypes);
                    }
                }else if (TextUtils.equals("88", mainTypeId)) {     //其他
                    craft.setOther(type);
                    if (secondObj.has(mainTypeId)) {
                        String arrayStr = secondObj.getString(mainTypeId);
                        List<WorkType> secondWorkTypes = gson.fromJson(arrayStr, new TypeToken<List<WorkType>>() {
                        }.getType());
                        type.setWorkTypes(secondWorkTypes);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return craft;
    }




    /**
     * 2-11.首页工程列表
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static  HomeProjectInfo homeProjectInfo(JSONObject jsonObject)throws  ResponseException{
        String dataStr = processDataStr(jsonObject);
        Gson gson = new Gson();
        HomeProjectInfo info = gson.fromJson(dataStr, HomeProjectInfo.class);
        return  info;
    }

    /**
     * 2-12.首页工程详情
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static ProjectInfoDetail projectInfoDetail(JSONObject jsonObject)throws  ResponseException{
        String dataStr = processDataStr(jsonObject);
        Gson gson = new Gson();
        ProjectInfoDetail detail = gson.fromJson(dataStr, ProjectInfoDetail.class);
        return  detail;
    }


    /**
     * 2-15.工长查看推荐过他的工人列表
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static  List<Worker> recommendedWorkers(JSONObject jsonObject)throws  ResponseException{
        String dataStr = processDataStr(jsonObject);
        Gson gson = new Gson();
        List<Worker> workerList = gson.fromJson(dataStr, new TypeToken<List<Worker>>(){}.getType());

        return  workerList;
    }

    /**
     * 2-16.工长解除和工人的推荐关系
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static String removeRelation(JSONObject jsonObject) throws ResponseException {
        JSONObject data = processData(jsonObject);
        String recommend = data.optString("recommend", null);
        return recommend;
    }

    /**
     * 2-17.发现
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static List<ReleaseProject> founds(JSONObject jsonObject) throws ResponseException {
        JSONObject data = processData(jsonObject);
        String arrayStr = data.optString("project");

        Gson gson = new Gson();
        List<ReleaseProject> list = gson.fromJson(arrayStr, new TypeToken<List<ReleaseProject>>() {
        }.getType());

        return list;
    }

    /**
     * 2-18 2-19 2-20 已发布的工程
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static List<ReleaseProject> projectStatus(JSONObject jsonObject) throws ResponseException {
        JSONObject data = processData(jsonObject);
        String arrayStr = data.optString("project");

        Gson gson = new Gson();
        List<ReleaseProject> list = gson.fromJson(arrayStr, new TypeToken<List<ReleaseProject>>() {
        }.getType());

        return list;
    }

    /**
     * 2-22.评价列表
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static List<Evaluate> evaluateList(JSONObject jsonObject) throws ResponseException {
        JSONObject data = processData(jsonObject);
        String arrayStr = data.optString("evaluate");

        Gson gson = new Gson();
        List<Evaluate> list = gson.fromJson(arrayStr, new TypeToken<List<Evaluate>>() {
        }.getType());

        return list;
    }


    /**
     *  2-23.交易明细
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static List<Trade> tradeDetail(JSONObject jsonObject) throws ResponseException {
        JSONObject data = processData(jsonObject);
        String arrayStr = data.optString("trade");

        Gson gson = new Gson();
        List<Trade> list = gson.fromJson(arrayStr, new TypeToken<List<Trade>>() {
        }.getType());

        return list;
    }

    /**
     * 2-24.交易账单
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static Account account(JSONObject jsonObject) throws ResponseException {
        String dataStr = processDataStr(jsonObject);
        Gson gson = new Gson();
        Account account = gson.fromJson(dataStr, Account.class);
        return account;
    }

    /**
     * 1-14、积分列表
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static List<JiFen> score(JSONObject jsonObject) throws ResponseException {
        String dataStr = processDataStr(jsonObject);
        Gson gson = new Gson();
        List<JiFen> list = gson.fromJson(dataStr, new TypeToken<List<JiFen>>() {
        }.getType());

        return list;
    }


    /**
     * 2-1.发布接口（开发商）
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static ReleaseProject getProjectInfo(JSONObject jsonObject)throws ResponseException{
        ReleaseProject releaseProject = new ReleaseProject();
        JSONObject data = processData(jsonObject);
        try {
//            T.show("能进来");
            String id = data.getString("id");
            String no = data.getString("no");
            String province = data.getString("province");
            String city = data.getString("city");
            String area_other = data.getString("area_other");
            String account_staffs = data.getString("account_staffs");
            String total_money = data.getString("total_money");
            String project_money = data.getString("project_money");
            String image = data.getString("image");
            String title = data.getString("title");
            String count = data.getString("description");
            releaseProject.setCount(count);
            releaseProject.setTitle(title);
            releaseProject.setProject_money(project_money);
            releaseProject.setId(id);
            releaseProject.setNo(no);
            releaseProject.setProvince(province);
            releaseProject.setCity(city);
            releaseProject.setAccount_staff(account_staffs);
            releaseProject.setArea_other(area_other);
            releaseProject.setTotal_money(total_money);
            releaseProject.setProject_money(project_money);
            releaseProject.setImage_par(image);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return releaseProject;
    }




    /**
     * 通用解析方法， 判断请求是否成功
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static  boolean  processDataResult(JSONObject jsonObject) throws ResponseException {
        if (null == jsonObject) {
            L.d(TAG, "processDataResult: json对象为null");
            return false;
        }

        try {
            String code = jsonObject.getString("code");
            L.d("xxx",jsonObject.getString("code"));
            String message = jsonObject.getString("message");
            if (TextUtils.equals("200", code)) {        //数据正确
                return true;
            } else {
                processData(jsonObject);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static  String  processDataStr(JSONObject jsonObject) throws ResponseException {
        if (null == jsonObject) {
            L.d(TAG, "processData: json对象为null");
            return null;
        }

        try {
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            if (TextUtils.equals("200", code)) {        //数据正确
                return jsonObject.optString("data", "");
            } else {
                processData(jsonObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json数据解析  返回正确的JSONObject
     * @param jsonObject
     * @return
     * @throws ResponseException    抛出异常：code message
     */
    public static  JSONObject processData(JSONObject jsonObject) throws ResponseException {
        if (null == jsonObject) {
            L.d(TAG, "processData: json对象为null");
            return null;
        }

        try {
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            if (TextUtils.equals("200", code)) {        //数据正确
                return jsonObject.getJSONObject("data");
            }else if(TextUtils.equals("501", code)){
                return null;
            }else{
                String dataStr = jsonObject.optString("data", "");
                String errorMessage = "";
                if (!TextUtils.isEmpty(dataStr) && !TextUtils.equals("null", dataStr) && !TextUtils.equals("NULL", dataStr)) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    if (data != null) {
                        Iterator<String> keys = data.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            errorMessage = data.getString(next);
                        }
                    }
                }
                if (TextUtils.isEmpty(errorMessage)) {
                    throw new ResponseException(code, message);
                }else {
                    throw new ResponseException(code, errorMessage);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private static void processResponseException(JSONObject jsonObject) throws JSONException {
//
//        String dataStr = jsonObject.optString("data", "");
//        String errorMessage = "";
//        if (!TextUtils.isEmpty(dataStr) && !TextUtils.equals("null", dataStr) && !TextUtils.equals("NULL", dataStr)) {
//            JSONObject data = jsonObject.getJSONObject("data");
//            if (data != null) {
//                Iterator<String> keys = data.keys();
//                while (keys.hasNext()) {
//                    String next = keys.next();
//                    errorMessage = data.getString(next);
//                }
//            }
//        }
//        T.show("用户未登录");
//        if (TextUtils.isEmpty(errorMessage)) {
//            throw new ResponseException(code, message);
//        }else {
//            throw new ResponseException(code, errorMessage);
//        }
//
//    }
}
