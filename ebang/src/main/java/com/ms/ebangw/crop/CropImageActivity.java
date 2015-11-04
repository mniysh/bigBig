package com.ms.ebangw.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.CropImageView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-10-02 08:43
 */
public class CropImageActivity extends BaseActivity {
    MyApplication application;

    @Bind(R.id.btn_cancel)
    Button cancelBtn;
    @Bind(R.id.btn_ok)
    Button okBtn;
    @Bind(R.id.cropImageView)
    CropImageView mImageView;
    private String filePath;
    private Bitmap mBitmap;
    private boolean isHeadImage = false;
    private RequestHandle handle;
    private String headImageStr;
//    private List<String> imagenNames;

    @Override
    public void initView() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        imagenNames = new ArrayList<>();
    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            isHeadImage = extras.getBoolean(Constants.KEY_HEAD_IMAGE, false);
            headImageStr = extras.getString(Constants.KEY_HEAD_IMAGE_STR,"1");
        }

        if (isHeadImage && TextUtils.equals(headImageStr,"headImage")) {
            mImageView.setCropMode(CropImageView.CropMode.RATIO_1_1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        initView();
        initData();

        application = (MyApplication) getApplication();
        String path = application.imagePath;
        Bitmap bitmap = BitmapUtil.getImage(path);
        mImageView.setImageBitmap(bitmap);
    }

    /** 保存方法 */
    public File saveBitmap(Bitmap bitmap) {


        L.d("保存图片");
        File cacheDir = getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();

        }
        File f = new File(cacheDir, "crop.png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            L.d("已经保存");

            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return f;
    }


    @OnClick(R.id.btn_ok)
    public void uploadImage (){
        Bitmap croppedBitmap = mImageView.getCroppedBitmap();
        application.mBitmap = croppedBitmap;
        File file = saveBitmap(croppedBitmap);
        if (isHeadImage && TextUtils.equals(headImageStr,"headImage")) {
            uploadAvatarImage(file);
        }else if(isHeadImage && TextUtils.equals(headImageStr, "publicImage")){
            uploadPublicImage(file);
        }
        else{
            uploadCommonImage(file);
        }

    }

    /**
     * 通用图片上传方式
     */
    public void uploadCommonImage(File file) {

        handle = DataAccessUtil.uploadImage(file, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                showProgressDialog("图片加载中...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    UploadImageResult imageResult = DataParseUtil.upLoadImage(response);
                    String name = imageResult.getName();

//                    User user = getUser();
//                    L.locationpois_item(user.toString());
//                    L.locationpois_item(imageResult.toString());
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_UPLOAD_IMAGE_RESULT, imageResult);
                    setResult(RESULT_OK, intent);
                    finish();
                    T.show("图片上传成功");
                } catch (ResponseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                T.show("图片上传失败，请重试");
                dismissLoadingDialog();
            }
        });


    }
    public void uploadPublicImage(File file) {

        handle = DataAccessUtil.uploadPublicImage(file, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                showProgressDialog("图片加载中...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    UploadImageResult imageResult = DataParseUtil.upLoadImage(response);
                    String name = imageResult.getName();

//                    User user = getUser();
//                    L.locationpois_item(user.toString());
//                    L.locationpois_item(imageResult.toString());
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_UPLOAD_IMAGE_RESULT, imageResult);
                    setResult(RESULT_OK, intent);
                    finish();
                    T.show("图片上传成功");
                } catch (ResponseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                T.show("图片上传失败，请重试");
                dismissLoadingDialog();
            }
        });


    }

    /**
     * 上传头像
     */
    public void uploadAvatarImage(File file) {

        handle = DataAccessUtil.headImage(file, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                showProgressDialog("头像上传中...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    UploadImageResult imageResult = DataParseUtil.upLoadImage(response);
//                    L.locationpois_item(imageResult.toString());
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_UPLOAD_IMAGE_RESULT, imageResult);
                    setResult(RESULT_OK, intent);
                    finish();
                    T.show("头像上传成功");
                } catch (ResponseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                T.show("头像上传失败，请重试");
                dismissLoadingDialog();
            }
        });
    }
}

