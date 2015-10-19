package com.ms.ebangw.release;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProvinceAndCityView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布  ---- >填写信息
 *@author wangkai
 */
public class IncreaseDetailFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //经纬度
    private double longitude;
    private double latitude;

//    private String JPEG_FILE_PREFIX =




    private String mParam1;
    private String mParam2;
    private String staff;
    private ViewGroup contentLayout;
    private File imageFile;
    @Bind(R.id.pac)
    ProvinceAndCityView provinceAndCityView;
    @Bind(R.id.et_detail_address)
    EditText detailAddressEt;
    @Bind(R.id.et_title)
    EditText titleEt;
    @Bind(R.id.et_name)
    EditText nameEt;
    @Bind(R.id.et_phone)
    EditText phoneEt;
    @Bind(R.id.btn_pick)
    Button pickBtn;
    @Bind(R.id.btn_camera)
    Button cameraBtn;
    @Bind(R.id.rg_type)
    RadioGroup typeRg;



    public static IncreaseDetailFragment newInstance(String param1, String param2) {
        IncreaseDetailFragment fragment = new IncreaseDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public IncreaseDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            staff = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_increase_detail, container,
            false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();

        return contentLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitle("填写信息");
    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        HomeActivity homeActivity = (HomeActivity) mActivity;
        List<Province> provinces = getAreaFromAssets().getProvince();
        provinceAndCityView.setProvinces(provinces);

    }

    /*** 打开照相机     */
    @OnClick(R.id.btn_camera)
    public void openCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + "/Images");
        if(!file.exists()){
            file.mkdirs();
        }
        imageFile = new File(Environment.getExternalStorageDirectory() + "/Images/",
            "cameraImg" + String.valueOf(System.currentTimeMillis()) + ".png");

        Uri mUri = Uri.fromFile(imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        cameraIntent.putExtra("return-data", true);
        startActivityForResult(cameraIntent, Constants.REQUEST_CAMERA);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            return;
        }
        if(requestCode == Constants.REQUEST_CAMERA && requestCode == mActivity.RESULT_OK){

        }
//        L.d("onActivityResult");
//
//        if (requestCode == Constants.REQUEST_CAMERA && resultCode == mActivity.RESULT_OK) { //拍照返回
//            Uri uri;
//            if (null == data) {
//                uri = Uri.fromFile(imageFile);
//            }else {
//                uri = data.getData();
//            }
//
//
//            beginCrop(uri);
//
//        }else if (requestCode == Crop.REQUEST_PICK&& resultCode == mActivity.RESULT_OK) {
//            beginCrop(data.getData());
//
//        }else if (requestCode == Crop.REQUEST_CROP) {
//            handleCrop(resultCode, data);			//在Fragment中处理剪切后的图片
//        }
    }

    public void handleCrop(int resultCode, Intent result) {

    }

    private void beginCrop(Uri source) {

    }

    public void setDeveloperReleaseInfo() {

        //性别
        int checkId = typeRg.getCheckedRadioButtonId();
        String type = Constants.MONTH;
        if (checkId == R.id.rb_month) {
            type =  Constants.MONTH;
        }else {
            type =  Constants.DAY;
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
//                        AuthInfo authInfo = ((InvestorAuthenActivity) mActivity).getAuthInfo();
//                        if (type == TYPE_FRONT) {
//                            isFrontUploaded = true;
//                            authInfo.setFrontImageId(id);
//                        }
//
//                        if (type == TYPE_BACK) {
//                            isBackUploaded = true;
//                            authInfo.setBackImageId(id);
//                        }
                        T.show("上传图片成功");
                    } else {
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
     * 开发商发布
     */
    @OnClick(R.id.btn_release)
    public void developerRelease() {

    }

}
