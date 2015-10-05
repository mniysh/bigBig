package com.ms.ebangw.crop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.view.CropImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-10-02 08:43
 */
public class CropImageActivity extends AppCompatActivity {
    @Bind(R.id.btn_cancel)
    Button cancelBtn;
    @Bind(R.id.btn_ok)
    Button okBtn;
    @Bind(R.id.cropImageView)
    CropImageView mImageView;
    private String filePath;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        MyApplication application = (MyApplication) getApplication();
        mBitmap = application.mBitmap;
        mImageView.setImageBitmap(mBitmap);
    }



}
