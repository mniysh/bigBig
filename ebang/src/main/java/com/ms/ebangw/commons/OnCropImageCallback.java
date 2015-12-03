package com.ms.ebangw.commons;

import android.view.View;

import com.ms.ebangw.bean.UploadImageResult;

/**
 * Created by Administrator on 2015/12/3.
 */
public interface OnCropImageCallback {
    void onCropImageSuccess(View view, String cropedImagePath, UploadImageResult imageResult);
}
