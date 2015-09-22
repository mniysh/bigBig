package com.ms.ebangw.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.commons.Constants;

import java.io.File;
import java.io.IOException;

/**
 * 类描述:裁剪图片工具类
 * 创建人: yancey
 * 创建时间: 2014-12-2 上午11:03:39
 * 修改人:
 * 修改时间:
 * 修改备注:
 * 版本:
 */
public class CropImageUtil {

    protected static final String TAG = "CropImageUtil";
    private static CropImageUtil cropImageUtil = null;
    private Context context;
    private String avatarUploadPath;
    /**
     * 土司提示信息
     */
    private String noticeStr;
    private String uploadImageURL;

    private CropImageUtil() {
        context = MyApplication.getInstance();
    }

    /**
     * @param
     * @return
     */
    public static CropImageUtil getInstance() {
        if (cropImageUtil == null)
            return cropImageUtil = new CropImageUtil();
        else
            return cropImageUtil;
    }



    /**
     * 图库
     */
    public void toGallery(Activity activity) {
        File dir = new File(Constants.LOGS_AND_IMGS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 选择图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, Constants.CHOOSE_GALLERY);
    }

    /**
     * 拍照
     */
    public void toPhotograph(Activity activity) {
        File dir = new File(Constants.LOGS_AND_IMGS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Constants.LOGS_AND_IMGS, "camera.jpg");
        Uri u = Uri.fromFile(f);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        activity.startActivityForResult(intent, Constants.CHOOSE_PHOTOGRAPH);
    }



    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

}
