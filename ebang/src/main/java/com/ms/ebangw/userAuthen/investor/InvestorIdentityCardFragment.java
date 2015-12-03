package com.ms.ebangw.userAuthen.investor;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.CropEnableFragment;
import com.ms.ebangw.userAuthen.developers.DevelopersAuthenActivity;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 身份证照片上传
 * @author wangkai
 */
public class InvestorIdentityCardFragment extends CropEnableFragment {
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


    public static InvestorIdentityCardFragment newInstance(String category) {
        InvestorIdentityCardFragment fragment = new InvestorIdentityCardFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.d("InvestorIdentityCardFragment onCreate" + savedInstanceState);
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            frontImagePath = savedInstanceState.getString(Constants.KEY_FRONT_IMAGE_PATH);
            backImagePath = savedInstanceState.getString(Constants.KEY_BACK_IMAGE_PATH);
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

    @Override
    public void initView() {
        setStarRed();
        nextBtn.setText("下一步");
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

    @OnClick(R.id.btn_next)
    public void goNext() {

        if (isIdentifyCardUploaded()) {
            ((InvestorAuthenActivity) mActivity).goVerifyBank();
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


    /**
     * 把*变成红色
     */
    public void setStarRed() {
        int[] resId = new int[]{R.id.tv_a, R.id.tv_b};
        for (int i = 0; i < resId.length; i++) {
            TextView a = (TextView) contentLayout.findViewById(resId[i]);
            String s = a.getText().toString();
            SpannableString spannableString = new SpannableString(s);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            a.setText(spannableString);
        }
    }

    @Override
    public void onCropImageSuccess(View view, String cropedImagePath, UploadImageResult imageResult) {
        super.onCropImageSuccess(view, cropedImagePath, imageResult);

        String id = imageResult.getId();
        AuthInfo authInfo = ((InvestorAuthenActivity) mActivity).getAuthInfo();
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
        outState.putString(Constants.KEY_FRONT_IMAGE_PATH, frontImagePath);
        outState.putString(Constants.KEY_BACK_IMAGE_PATH, backImagePath);
        L.d("Fragment onSaveInstanceState: " + outState);
        super.onSaveInstanceState(outState);
    }

}
