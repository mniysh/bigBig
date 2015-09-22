package com.ms.ebangw.fragment;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.UserAuthenActivity;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.soundcloud.android.crop.Crop;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 身份证照片上传
 * @author wangkai
 */
public class IdentityCardPhotoVerifyFragment extends BaseFragment {
    private static final String CATEGORY = "category";
    private String whichPhoto;
    private String category;
    private String mCurrentPhotoPath;
    private File imageFile;
    private final int TYPE_FRONT = 1;
    private final int TYPE_BACK = 2;

    private View contentLayout;
    /**正面身份证上传*/
    @Bind(R.id.btn_select_front)
    Button uploadFrontBtn;
    /**背面身份证上传*/
    @Bind(R.id.btn_select_back)
    Button uploadBackBtn;
    /**正面身份证拍照*/
    @Bind(R.id.btn_photo_front)
    Button photoFrontBtn;
    /**正面身份证拍照*/
    @Bind(R.id.btn_photo_back)
    Button photoBackBtn;
    @Bind(R.id.iv_front)
    ImageView frontIv;
    @Bind(R.id.iv_back)
    ImageView backIv;
    @Bind(R.id.btn_next)
    Button nextBtn;
    private boolean isFrontUploaded, isBackUploaded;


    public static IdentityCardPhotoVerifyFragment newInstance(String category) {
        IdentityCardPhotoVerifyFragment fragment = new IdentityCardPhotoVerifyFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(Constants.KEY_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = inflater.inflate(R.layout.fragment_card_verify, container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }


    /**
     * 选择正面照片
     */
    @OnClick(R.id.btn_select_front)
    public void selectFrontPhoto() {
        whichPhoto = Constants.PHOTO_FRONT;
        ((UserAuthenActivity)mActivity).selectPhoto();
    }

    /**
     * 选择反面照片
     */
    @OnClick(R.id.btn_select_back)
    public void selectBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
        ((UserAuthenActivity)mActivity).selectPhoto();
    }

    /**
     * 拍正面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeFrontPhoto() {
        whichPhoto = Constants.PHOTO_FRONT;
        ((UserAuthenActivity)mActivity).openCamera();
    }

    /**
     * 拍背面身份证照
     */
    @OnClick(R.id.btn_photo_back)
    public void takeBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
        ((UserAuthenActivity)mActivity).openCamera();
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.btn_next)
    public void goNext() {

        switch (category) {
            case Constants.HEADMAN:
            case Constants.WORKER:
                if (isIdentifyCardUploaded()) {
                    ((UserAuthenActivity) mActivity).goVerifyBank();
                }

                break;
        }
    }

    private boolean isIdentifyCardUploaded() {
        if (!isFrontUploaded) {
            T.show("请上传身份证正面照");
            return false;
        }else if(!isBackUploaded){
            T.show("请上传身份证反面照");
            return false;
        }
        return true;
    }

    @Override
    public void initData() {

    }

    public void handleCrop(int resultCode, Intent result) {
        if (resultCode == mActivity.RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            L.d("Uri: " + uri);
            if (whichPhoto == Constants.PHOTO_FRONT) {
                frontIv.setImageURI(Crop.getOutput(result));
                uploadImage(uri, TYPE_FRONT);
            }else {
                backIv.setImageURI(Crop.getOutput(result));
                uploadImage(uri, TYPE_BACK);
            }



        } else if (resultCode == Crop.RESULT_ERROR) {
            T.show("选取图片失败");
        }
    }

    private void uploadImage(Uri uri, final int type ) {
        File file = uriToFile(uri);

        DataAccessUtil.uploadImage(file, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    UploadImageResult result = DataParseUtil.upLoadImage(response);
                    if (type == TYPE_FRONT ) {
                        isFrontUploaded = true;
                    }

                    if (type == TYPE_BACK ) {
                        isBackUploaded = true;
                    }

                } catch (ResponseException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

        });
    }

    public File uriToFile(Uri uri) {

        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = mActivity.getContentResolver().query( uri, new String[] { MediaStore.Images
                .ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        File file = new File(data);
        return file;
    }

}
