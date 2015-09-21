package com.ms.ebangw.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ms.ebangw.Photo.SelectPhotosDirActivity;
import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 身份证照片上传
 * @author wangkai
 */
public class IdentityCardPhotoVerifyFragment extends BaseFragment {
    private static final String CATEGORY = "category";

    private String category;
    private View contentLayout;
    /**正面身份证上传*/
    @Bind(R.id.btn_upload_front)
    Button uploadFrontBtn;
    /**背面身份证上传*/
    @Bind(R.id.btn_upload_back)
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


    public static IdentityCardPhotoVerifyFragment newInstance(String category) {
        IdentityCardPhotoVerifyFragment fragment = new IdentityCardPhotoVerifyFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public IdentityCardPhotoVerifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
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
    @OnClick(R.id.btn_upload_front)
    public void selectFrontPhoto() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_PHOTO, Constants.PHOTO_FRONT);
        Intent intent = new Intent(mActivity, SelectPhotosDirActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    /**
     * 选择反面照片
     */
    @OnClick(R.id.btn_upload_back)
    public void selectBackPhoto() {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_PHOTO, Constants.PHOTO_BACK);
        Intent intent = new Intent(mActivity, SelectPhotosDirActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    /**
     * 拍正面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeFrontPhoto() {

    }

    /**
     * 拍背面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeBackPhoto() {

    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
