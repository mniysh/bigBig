package com.ms.ebangw.utils;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class JsonUtil {

    private static String TAG = "JsonUtil";

    // Constant values
    public static final int ERROR_INTEGER = 0;
    public static final double ERROR_DOUBLE = 0f;
    public static final boolean ERROR_BOOLEAN = false;

    private JsonUtil() {
    }


    /**
     * Load
     *
     * @param json JSON string who contains JSON text
     * @return
     */
    public static JSONObject loadJSON(String jsonString) {
        try {
            if (jsonString != null && jsonString.length() != 0) {
                return new JSONObject(jsonString);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "parse json string to object failed! jsonString:" + jsonString, e);
        }
        return null;
    }


    /**
     * Load
     *
     * @param json JSON string who contains JSON text
     * @return
     */
    public static JSONArray loadJsonArray(String jsonArrayString) {
        try {
            if (jsonArrayString != null && jsonArrayString.length() != 0) {

//            	CLog.e(TAG,  "data---->" + jsonArrayString);
                return new JSONArray(jsonArrayString);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "parse json array to object failed! jsonString:" + jsonArrayString, e);
        }
        return null;
    }


    /**
     * Get integer from JSON object
     *
     * @param aJoObj
     * @param aName
     * @return
     */
    public static int getInt(JSONObject jsonObject, String aName) {
        try {
            if (jsonObject != null && jsonObject.has(aName)) {
                return jsonObject.getInt(aName);
            }
            return 0;
        } catch (JSONException e) {
            Logger.e(TAG, "get int from json object failed! jsonObject:" + jsonObject + "\tname:" + aName, e);
        }
        return ERROR_INTEGER;
    }

    /**
     * Get long from JSON object
     *
     * @param aJoObj
     * @param aName
     * @return
     */
    public static long getLong(JSONObject aJoObj, String aName) {
        try {
            if (aJoObj != null && aJoObj.has(aName)) {
                return aJoObj.getLong(aName);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get long from json object failed! jsonObject:" + aJoObj + "\tname:" + aName, e);
        }
        return ERROR_INTEGER;
    }

    /**
     * Get string from JSON object
     *
     * @param aJoObj
     * @param aName
     * @return
     */
    public static String getString(JSONObject aJoObj, String aName) {
        try {
            if (aJoObj != null && aJoObj.has(aName)) {
                String value = aJoObj.getString(aName);
                if (!value.equals(JSONObject.NULL)) {
                    return value;
                }
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get string from json object failed! jsonObject:" + aJoObj + "\tname:" + aName, e);
        }
        return null;
    }

    /**
     * Get boolean from JSON object
     *
     * @param aJoObj
     * @param aName
     * @return
     */
    public static boolean getBoolean(JSONObject aJoObj, String aName) {
        try {
            if (aJoObj != null && aJoObj.has(aName)) {
                return aJoObj.getBoolean(aName);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get boolean from json object failed! jsonObject:" + aJoObj + "\tname:" + aName, e);
        }
        return ERROR_BOOLEAN;
    }


    /**
     * Get double from JSON object
     *
     * @param aJoObj
     * @param aName
     * @return
     */
    public static double getDouble(JSONObject aJoObj, String aName) {
        try {
            if (aJoObj != null && aJoObj.has(aName)) {
                return aJoObj.getDouble(aName);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get double from json object failed! jsonObject:" + aJoObj + "\tname:" + aName, e);
        }
        return ERROR_DOUBLE;
    }

    /**
     * Get sub object from JSON object
     *
     * @param aJoObj
     * @param aName
     * @return
     */
    public static JSONObject getJSONObject(JSONObject aJoObj, String aName) {
        try {
            if (aJoObj != null && aJoObj.has(aName)) {
                return aJoObj.getJSONObject(aName);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get jsonObject from json object failed! jsonObject:" + aJoObj + "\tname:" + aName, e);
        }
        return null;
    }

    /**
     * Get sub JSON array from JSON object
     *
     * @param aJoObj
     * @param aName
     * @return
     */
    public static JSONArray getJSONArray(JSONObject aJoObj, String aName) {
        try {
            if (aJoObj != null && aJoObj.has(aName)) {
                return aJoObj.getJSONArray(aName);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get json array from json object failed! jsonObject:" + aJoObj + "\tname:" + aName, e);
        }
        return null;
    }

    /**
     * Get the length of a JSON array
     *
     * @param aJoArray
     * @return
     */
    public static int getArrayLength(JSONArray aJoArray) {
        if (aJoArray != null) {
            return aJoArray.length();
        }
        return 0;
    }

    /**
     * Get JSON object from JSON array
     *
     * @param aJoArray
     * @param aIndex
     * @return
     */
    public static JSONObject getJSONObject(JSONArray aJoArray, int aIndex) {
        try {
            if (aJoArray != null && !aJoArray.isNull(aIndex)) {
                return aJoArray.getJSONObject(aIndex);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get string from json object failed! jsonArray:" + aJoArray.toString() + "\taIndex:" + aIndex, e);
        }
        return null;
    }

    /**
     * Get string from JSON array
     *
     * @param aJoArray
     * @param aIndex
     * @return
     */
    public static String getStringFromArray(JSONArray aJoArray, int aIndex) {
        try {
            if (aJoArray != null && !aJoArray.isNull(aIndex)) {
                String value = aJoArray.getString(aIndex);
                if (!value.equals(JSONObject.NULL)) {
                    return value;
                }
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get string from json Array failed! jsonArray:" + aJoArray.toString() + "\taIndex:" + aIndex, e);
        }
        return null;
    }

    /**
     * Get integer from JSON array
     *
     * @param aJoArray
     * @param aIndex
     * @return
     */
    public static int getIntFromArray(JSONArray aJoArray, int aIndex) {
        try {
            if (aJoArray != null && !aJoArray.isNull(aIndex)) {
                return aJoArray.getInt(aIndex);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get int from json array failed! jsonArray:" + aJoArray.toString() + "\taIndex:" + aIndex, e);
        }
        return ERROR_INTEGER;
    }

    /**
     * Get boolean from JSON array
     *
     * @param aJoArray
     * @param aIndex
     * @return
     */
    public static boolean getBoolFromArray(JSONArray aJoArray, int aIndex) {
        try {
            if (aJoArray != null && !aJoArray.isNull(aIndex)) {
                return aJoArray.getBoolean(aIndex);
            }
        } catch (JSONException e) {
            Logger.e(TAG, "get boolean from json array failed! jsonArray:" + aJoArray.toString() + "\taIndex:" + aIndex, e);
        }
        return ERROR_BOOLEAN;
    }

    /**yancey添加以以下***************************************************************************************/

    /**
     * Object生成Json
     *
     * @param object
     * @return
     */
    public static String createGsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    /**
     * Json解析成Bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(gsonString, cls);
        return t;
    }

    /**
     * Json解析成List
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    /**
     * Json解析成List<Map<String,T>>
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
        }.getType());
        return list;
    }

    /**
     * Json解析成Map<String,T>
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        Gson gson = new Gson();
        map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }

    /**
     * json解析成class
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T Gson2Class(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    /**
     * json解析成任意的类型,List<Bean>等所有类型
     *
     * @param json
     * @param type
     * @return
     */
    public static <T> T Gson2Type(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }


    /**
     * 把 bundle 转换成 json 对象, 只取用 String, Boolean, Integer, Long, Double
     * @param bundle
     * @return
     * @throws JSONException
     */
    public static JSONObject bundleToJSON(Bundle bundle) throws JSONException {
        JSONObject json = new JSONObject();
        if(bundle == null || bundle.isEmpty()){
            return json;
        }
        Set<String> keySet = bundle.keySet();
        for (String key : keySet) {
            Object object = bundle.get(key);
            if (object instanceof String || object instanceof Boolean || object instanceof Integer
                    || object instanceof Long || object instanceof Double){
                json.put(key, object);
            }
        }
        return json;
    }

    /**
     * 把 bundle 转换成 json 字符串, 只取用 String, Boolean, Integer, Long, Double
     * @param bundle
     * @return
     * @throws JSONException
     */
    public static String bundleToJSONString(Bundle bundle) throws JSONException {
        JSONObject json = bundleToJSON(bundle);
        return json.toString();
    }
}
