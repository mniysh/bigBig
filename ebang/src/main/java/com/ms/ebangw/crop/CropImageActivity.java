package com.ms.ebangw.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-10-02 08:43
 */
public class CropImageActivity extends BaseActivity {
    public static final String TYPE_PRIVATE = "private";
    public static final String TYPE_PUBLIC = "public";
    public static final String TYPE_HEAD = "headImage";

    @Bind(R.id.btn_cancel)
    Button cancelBtn;
    @Bind(R.id.btn_ok)
    Button okBtn;
    @Bind(R.id.cropImageView)
    CropImageView mImageView;
    private RequestHandle handle;
    private String imageType;
    private String savedImagePath;

    @Override
    public void initView() {

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 要上传的图片的类型， 公共的，私有的， 头像
     */
    @StringDef({TYPE_PRIVATE, TYPE_PUBLIC, TYPE_HEAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageType{}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        initView();
        initData();


    }

    /** 保存方法 */
    public File saveBitmap(Bitmap bitmap) {


        L.d("保存图片");
        File cacheDir = getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();

        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HHMMss");
        String s = format.format(new Date());
        File f = new File(cacheDir, s + "_crop.png");
//        if (f.exists()) {
//            f.delete();
//        }
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
        File file = saveBitmap(croppedBitmap);
        if (file.exists()) {
            savedImagePath = file.getAbsolutePath();
            if (!TextUtils.isEmpty(imageType)) {
                switch (imageType) {
                    case TYPE_HEAD:
                        uploadAvatarImage(file);
                        break;
                    case TYPE_PUBLIC:
                        uploadPublicImage(file);
                        break;
                    case TYPE_PRIVATE:
                        uploadPrivateImage(file);
                        break;
                }
            }
        }
    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            imageType = extras.getString(Constants.KEY_UPLOAD_IMAGE_TYPE);
            String originImagePath = extras.getString(Constants.KEY_ORIGIN_IMAGE_PATH);;
            if (TextUtils.equals(imageType, TYPE_HEAD)) {
                mImageView.setCropMode(CropImageView.CropMode.RATIO_1_1);
            }

            Bitmap bitmap = BitmapUtil.getImage(originImagePath);
            mImageView.setImageBitmap(bitmap);
        }

    }


    /**
     * 私有图片上传方式
     */
    public void uploadPrivateImage(File file) {

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
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_UPLOAD_IMAGE_RESULT, imageResult);
                    intent.putExtra(Constants.KEY_CROP_IMAGE_PATH, savedImagePath);
                    setResult(RESULT_OK, intent);
                    finish();
                    T.show("图片上传成功");
                } catch (ResponseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissLoadingDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                T.show("图片上传失败，请重试");
            }
        });
    }

    /**
     * 公开的图片
     * @param file
     */
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
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_UPLOAD_IMAGE_RESULT, imageResult);
                    intent.putExtra(Constants.KEY_CROP_IMAGE_PATH, savedImagePath);
                    setResult(RESULT_OK, intent);
                    finish();
                    T.show("发布图片上传成功");
                } catch (ResponseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissLoadingDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                T.show("图片上传失败，请重试");
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
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_UPLOAD_IMAGE_RESULT, imageResult);
                    intent.putExtra(Constants.KEY_CROP_IMAGE_PATH, savedImagePath);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != handle && !handle.isCancelled()) {
            handle.cancel(true);
        }
    }
}

