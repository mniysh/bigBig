package com.ms.ebangw.userAuthen.developers;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.fragment.CropEnableFragment;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.UserCenterUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 身份证照片上传
 * @author wangkai
 */
public class DevelopersIdentityCardFragment extends CropEnableFragment {
    private static final String CATEGORY = "category";

    private String frontImagePath;
    private String backImagePath;

    private View contentLayout;
    /**正面身份证选择图片*/
    @Bind(R.id.btn_select_front)
    Button uploadFrontBtn;
    /**背面身份证选择图片*/
    @Bind(R.id.btn_select_back)
    Button uploadBackBtn;
    /**正面身份证拍照*/
    @Bind(R.id.btn_photo_front)
    Button photoFrontBtn;
    /**反面身份证拍照*/
    @Bind(R.id.btn_photo_back)
    Button photoBackBtn;
    @Bind(R.id.iv_front)
    ImageView frontIv;
    @Bind(R.id.iv_back)
    ImageView backIv;
    @Bind(R.id.btn_next)
    Button nextBtn;
    private boolean isFrontUploaded, isBackUploaded;


    public static DevelopersIdentityCardFragment newInstance(String category) {
        DevelopersIdentityCardFragment fragment = new DevelopersIdentityCardFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
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


    @Override
    public void initView() {
        UserCenterUtil.setStarRed(contentLayout);
        if (!TextUtils.isEmpty(frontImagePath)&& new File(frontImagePath).exists()) {
            Bitmap bitmap = BitmapUtil.getImage(frontImagePath);
            frontIv.setImageBitmap(bitmap);
            isFrontUploaded = true;
        }
        if (!TextUtils.isEmpty(backImagePath)&& new File(backImagePath).exists()) {
            Bitmap bitmap = BitmapUtil.getImage(backImagePath);
            backIv.setImageBitmap(bitmap);
            isBackUploaded = true;
        }

    }

    @Override
    public void initData() {

    }

    /**
     * 选择正面或反面图片
     * @param view
     */
    @OnClick({R.id.btn_select_front, R.id.btn_select_back})
    public void selectGallery(View view) {
        selectPhoto(view, CropImageActivity.TYPE_PRIVATE);
    }

    /**
     * 选择正面或反面拍照
     * @param view
     */
    @OnClick({R.id.btn_photo_front, R.id.btn_photo_back})
    public void selectCamera(View view) {
        captureImageByCamera(view, CropImageActivity.TYPE_PRIVATE);
    }

    @OnClick(R.id.btn_next)
    public void goNext() {
        if (isIdentifyCardUploaded()) {
            ((DevelopersAuthenActivity) mActivity).goVerifyBank();
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
    public void onCropImageSuccess(View view, String cropedImagePath, UploadImageResult imageResult) {
        super.onCropImageSuccess(view, cropedImagePath, imageResult);

        String id = imageResult.getId();
        AuthInfo authInfo = ((DevelopersAuthenActivity) mActivity).getAuthInfo();
        Bitmap bitmap = BitmapUtil.getImage(cropedImagePath);
        switch (view.getId()) {
            case R.id.btn_photo_front:
            case R.id.btn_select_front:
                frontIv.setImageBitmap(bitmap);
                authInfo.setFrontImageId(id);
                isFrontUploaded = true;
                frontImagePath = cropedImagePath;
                break;

            case R.id.btn_photo_back:
            case R.id.btn_select_back:
                backIv.setImageBitmap(bitmap);
                authInfo.setBackImageId(id);
                isBackUploaded = true;
                backImagePath = cropedImagePath;
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putString(Constants.KEY_CURRENT_IMAGE_PATH, mCurrentPhotoPath);
//        outState.putString(Constants.KEY_WHICH_PHOTO, whichPhoto);
        outState.putString(Constants.KEY_FRONT_IMAGE_PATH, frontImagePath);
        outState.putString(Constants.KEY_BACK_IMAGE_PATH, backImagePath);
        L.d("Fragment onSaveInstanceState: " + outState);
        super.onSaveInstanceState(outState);
    }

}
