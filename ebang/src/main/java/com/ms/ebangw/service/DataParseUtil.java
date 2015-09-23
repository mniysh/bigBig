package com.ms.ebangw.service;

import android.text.TextUtils;

import com.facebook.internal.BoltsMeasurementEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ms.ebangw.bean.Area;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
     * 3-6. 获取全部省市区数据
     * @param jsonObject
     * @return
     * @throws ResponseException
     */
    public static TotalRegion provinceCityArea(JSONObject jsonObject)throws  ResponseException{

        JSONObject data = processData(jsonObject);
        Gson gson = new Gson();
        String provinceStr = data.optString("province", null);
//        String cityStr = data.optString("city", null);
//        String areaStr = data.optString("area", null);
        JSONObject citysObj = data.optJSONObject("city");
        JSONObject areaObj = data.optJSONObject("area");

        List<Province> provinces = gson.fromJson(provinceStr, new TypeToken<List<Province>>() {
        }.getType());

        Province province;
        for (int i = 0; i < provinces.size(); i++) {

           province = provinces.get(i);
            String id = province.getId();
            try {
                if (!citysObj.has(id)) {
                    continue;
                }
                String citysObjString = citysObj.getString(id);
                List<City> subCitys = gson.fromJson(citysObjString, new TypeToken<List<City>>() {
                }.getType());
                City city;
                for (int j = 0; j < subCitys.size(); j++) {
                    city = subCitys.get(j);
                    String cityId = city.getId();
                    if (!areaObj.has(cityId)) {
                        continue;
                    }
                    String areaObjString = areaObj.getString(cityId);
                    List<Area> subAreas = gson.fromJson(areaObjString, new TypeToken<List<Area>>() {
                    }.getType());
                    city.setAreas(subAreas);
                }

                province.setCitys(subCitys);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


//        List<City> citys = new ArrayList<>();
//        List<Area> areas = new ArrayList<>();
//
//        Iterator<String> cityKeys = citysObj.keys();
//        Iterator<String> areaKeys = areaObj.keys();
//
//        for (int i = 0; i < citysObj.length(); i++) {
//
//            if(cityKeys.hasNext()){
//                String next = cityKeys.next();
//                try {
//                    String arrayStr = citysObj.getString(next);
//                    List<City> subCitys = gson.fromJson(arrayStr, new TypeToken<List<City>>() {
//                        }.getType());
//                    citys.addAll(subCitys);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        for (int i = 0; i < areaObj.length(); i++) {
//
//            if(areaKeys.hasNext()){
//                String next = areaKeys.next();
//                try {
//                    String arrayStr = areaObj.getString(next);
//                    List<Area> subAreas = gson.fromJson(arrayStr, new TypeToken<List<Area>>() {
//                    }.getType());
//                    areas.addAll(subAreas);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        TotalRegion totalRegion = new TotalRegion();
        totalRegion.setProvince(provinces);
//        totalRegion.setCity(citys);
//        totalRegion.setArea(areas);
        return totalRegion;


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
            String message = jsonObject.getString("message");
            if (TextUtils.equals("200", code)) {        //数据正确
                return true;
            } else {
                throw new ResponseException(code, message);
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
                throw new ResponseException(code, message);
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
            } else {
                throw new ResponseException(code, message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static JSONArray processDataArray(JSONObject jsonObject) throws ResponseException {
        if (null == jsonObject) {
            L.d(TAG, "processDataArray: json对象为null");
            return null;
        }
        try {
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            if (TextUtils.equals("200", code)) {        //数据正确
                return jsonObject.getJSONArray("data");
            } else {
                throw new ResponseException(code, message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}  
