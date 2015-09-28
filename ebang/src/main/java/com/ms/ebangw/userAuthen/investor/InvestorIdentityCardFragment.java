package com.ms.ebangw.userAuthen.investor;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.BaseFragment;
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
public class InvestorIdentityCardFragment extends BaseFragment {
    private static final String CATEGORY = "category";
    private String whichPhoto;
    private String category;
    private String mCurrentPhotoPath;
    private File imageFile;
    private final int TYPE_FRONT = 1;
    private final int TYPE_BACK = 2;

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
        ((InvestorAuthenActivity)mActivity).selectPhoto();
    }

    /**
     * 选择反面照片
     */
    @OnClick(R.id.btn_select_back)
    public void selectBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
        ((InvestorAuthenActivity)mActivity).selectPhoto();
    }

    /**
     * 拍正面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeFrontPhoto() {
        whichPhoto = Constants.PHOTO_FRONT;
        ((InvestorAuthenActivity)mActivity).openCamera();
    }

    /**
     * 拍背面身份证照
     */
    @OnClick(R.id.btn_photo_back)
    public void takeBackPhoto() {
        whichPhoto = Constants.PHOTO_BACK;
        ((InvestorAuthenActivity)mActivity).openCamera();
    }

    @Override
    public void initView() {
        setStarRed();
        nextBtn.setText("下一步");

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
            public void onStart() {
                super.onStart();
                showProgressDialog("图片上传中...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dismissLoadingDialog();


                try {
                    if (DataParseUtil.processDataResult(response)) {
                        UploadImageResult result = DataParseUtil.upLoadImage(response);
                        String id = result.getId();
                        AuthInfo authInfo = ((InvestorAuthenActivity) mActivity).getAuthInfo();
                        if (type == TYPE_FRONT) {
                            isFrontUploaded = true;
                            authInfo.setFrontImageId(id);
                        }

                        if (type == TYPE_BACK) {
                            isBackUploaded = true;
                            authInfo.setBackImageId(id);
                        }
                        T.show("上传图片成功");
                    }else {
                        T.show("上传图片失败,请重试");
                    }



                } catch (ResponseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                L.d(responseString);
                T.show("上传图片失败,请重试");
                dismissLoadingDialog();
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

}
