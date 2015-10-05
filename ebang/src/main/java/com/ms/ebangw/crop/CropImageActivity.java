package com.ms.ebangw.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.CropImageView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-10-02 08:43
 */
public class CropImageActivity extends BaseActivity {
    @Bind(R.id.btn_cancel)
    Button cancelBtn;
    @Bind(R.id.btn_ok)
    Button okBtn;
    @Bind(R.id.cropImageView)
    CropImageView mImageView;
    private String filePath;
    private Bitmap mBitmap;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        MyApplication application = (MyApplication) getApplication();
        mBitmap = application.mBitmap;
        mImageView.setImageBitmap(mBitmap);


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

        DataAccessUtil.uploadImage(file, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                showProgressDialog("图片加载中...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    UploadImageResult imageResult = DataParseUtil.upLoadImage(response);
                    User user = getUser();
                    L.d(user.toString());
                    L.d(imageResult.toString());
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
            }


        });

    }

}

