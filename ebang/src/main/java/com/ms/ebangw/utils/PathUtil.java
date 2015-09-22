package com.ms.ebangw.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class PathUtil {
    /**
     * @param @param  context
     * @param @return
     * @return String
     * @throws
     * @Title: getLogPath
     * @Description: 获取Log路径
     * @author gaoshunsheng
     */
    public static String getLogPath(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (null != externalCacheDir) {
            return externalCacheDir.getAbsolutePath();
        }

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 根据Uri获取文件的路径
     *
     * @param uri
     * @return String
     * @Title: getUriString
     * @date 2012-11-28 下午1:19:31
     */
    public static String getUri2Path(Uri uri, ContentResolver cr) {
        String imgPath = null;
        if (uri != null) {
            String uriString = uri.toString();
            // 小米手机的适配问题，小米手机的uri以file开头，其他的手机都以content开头
            // 以content开头的uri表明图片插入数据库中了，而以file开头表示没有插入数据库
            // 所以就不能通过query来查询，否则获取的cursor会为null。
            if (uriString.startsWith("file")) {
                // uri的格式为file:///mnt....,将前七个过滤掉获取路径
                imgPath = uriString.substring(7, uriString.length());
                return imgPath;
            }
            Cursor cursor = cr.query(uri, null, null, null, null);
            cursor.moveToFirst();
            imgPath = cursor.getString(1); // 图片文件路径

        }
        return imgPath;
    }


}
