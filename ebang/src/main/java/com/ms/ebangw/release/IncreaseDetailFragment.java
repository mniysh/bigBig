package com.ms.ebangw.release;


import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.crop.GetPathFromUri4kitkat;
import com.ms.ebangw.dialog.DatePickerFragment;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProvinceAndCityView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_PICK = 4;
    private static final int REAQUEST_CROP = 8;
    private String whickPhoto ;
    private int a;

    //经纬度
    private double longitude;
    private double latitude;

//    private String JPEG_FILE_PREFIX =


    private List<String> imageNames;


    private String mParam1;
    private String mParam2;
    private String staff;
    private String pay_type = "月结";
    private String image_ary;
    private ViewGroup contentLayout;
    private File imageFile;
    @Bind(R.id.pac)
    ProvinceAndCityView provinceAndCityView;
    //详细地址
    @Bind(R.id.et_detail_address)
    EditText detailAddressEt;
    //title
    @Bind(R.id.et_title)
    EditText titleEt;
    //联系人和电话
    @Bind(R.id.et_name)
    EditText nameEt;
    @Bind(R.id.et_phone)
    EditText phoneEt;
    //内容
    @Bind(R.id.et_introduce)
    EditText introduceEt;

    @Bind(R.id.btn_pick)
    Button pickBtn;
    @Bind(R.id.btn_camera)
    Button cameraBtn;
    @Bind(R.id.rg_type)
    RadioGroup typeRg;
    @Bind(R.id.iv_picture01)
    ImageView picture01Iv;
    @Bind(R.id.iv_picture02)
    ImageView picture02Iv;
    @Bind(R.id.iv_picture03)
    ImageView picture03Iv;
    //工期开始结束时间
    @Bind(R.id.tv_start_time)
    TextView startTimeTv;
    @Bind(R.id.tv_end_time)
    TextView endTimeTv;




    private String province, city , area, detailAddress, title, link_name, link_phone, count, startTime, endTime;
    private String provinceId, cityId, areaId;
    private String mCurrentPhotoPuth;
    private MyApplication myApplication;




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
    //地图获取信息
    @OnClick(R.id.et_address)
    public void getMap(){
        HomeActivity homeActivity = (HomeActivity)mActivity;
        homeActivity.goMapAdd();

    }
    @OnClick(R.id.tv_start_time)
    public void projectStartTime(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = simpleDateFormat.format(calendar.getTime());
                startTimeTv.setText(dateStr);
            }

        });
        datePickerFragment.show(getFragmentManager(), "date");
    }
    @OnClick(R.id.tv_end_time)
    public void projectEndTime(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = simpleDateFormat.format(calendar.getTime());
                endTimeTv.setText(dateStr);
            }
        });
        datePickerFragment.show(getFragmentManager(), "date");
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

    /**
     * 获取数据
     */
    public void getData(){

        if(imageNames == null){
            return;
        }


        image_ary = disposeImage(imageNames);
        detailAddress = detailAddressEt.getText().toString().trim();
        title = titleEt.getText().toString().trim();
        link_name = nameEt.getText().toString().trim();
        latitude = 0.5;
        longitude = 0.5;
        link_phone = phoneEt.getText().toString().trim();
        count = introduceEt.getText().toString().trim();
        provinceId = provinceAndCityView.getProvinceId();
        cityId = provinceAndCityView.getCityId();
        areaId = provinceAndCityView.getAreaId();
        startTime = startTimeTv.getText().toString().trim();
        endTime = endTimeTv.getText().toString().trim();

    }
    public String disposeImage(List<String> data){
        Gson gson = new Gson();
        return gson.toJson(data);
    }
    public boolean isRight(){
        if(imageNames == null){
            T.show("请至少上传一张图片");
            return false;
        }
        if(TextUtils.isEmpty(detailAddress)){
            T.show("详细地址不可为空");
            return  false;
        }
        if(TextUtils.isEmpty(title)){
            T.show("标题不可为空");
            return  false;
        }
        if(TextUtils.isEmpty(link_name)){
            T.show("姓名不可为空");
            return  false;
        }
        if(TextUtils.isEmpty(link_phone)){
            T.show("电话不可为空");
            return  false;
        }
        if(TextUtils.isEmpty(count)){
            T.show("简介不可为空");
            return  false;
        }
        if(latitude == 0 || longitude == 0){
            T.show("请地图选点");
            return false;
        }

        return true;
    }


    @Override
    public void initView() {
        imageNames = new ArrayList<String>();
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

    /**
     * 手机选照片
     */
    @OnClick(R.id.btn_pick)
    public void selectImageFromPhone(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Constants.REQUEST_PICK);

    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( mActivity.RESULT_OK != -1){
            return;
        }
        if(requestCode == Constants.REQUEST_CAMERA ){
            //照相上传
            Uri uri ;
            if(data == null){
                uri = Uri.fromFile(imageFile);
            }else{
                uri = data.getData();
            }
            beginCrop(uri);


        }else if(requestCode == Constants.REQUEST_PICK){
            //手机内部选图处理
            Uri uri = data.getData();
            String path = GetPathFromUri4kitkat.getPath(mActivity,uri);
            myApplication = (MyApplication) mActivity.getApplication();
            myApplication.imagePath = path;
            Intent intent = new Intent(mActivity, CropImageActivity.class);
            startActivityForResult(intent, Constants.REQUEST_CROP);
        }else if(requestCode == Constants.REQUEST_CROP){
            //剪切后的处理
            settingImage(data);


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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCurrentPhotoPuth = imageFile.getAbsolutePath();
        myApplication = (MyApplication) mActivity.getApplication();
        myApplication.imagePath = mCurrentPhotoPuth;
        Intent intent = new Intent(mActivity, CropImageActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CROP);


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

    public void getAreaId(){



    }

    /**
     * 开发商发布
     */
    @OnClick(R.id.btn_release)
    public void developerRelease() {
       // private String province, city , area, detailAddress, title, link_name, link_phone, count;
        User user = getUser();
        if(user == null){
            return;
        }
        getData();

        if(isRight()){
            DataAccessUtil.developerRelease(title,detailAddress,
                    link_name, link_phone, provinceId, cityId, areaId, count,
                    longitude, latitude, "月结", image_ary, staff,
                    new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                boolean b = DataParseUtil.processDataResult(response);
                                if(b){
                                    T.show("请求成功");
                                }
                            } catch (ResponseException e) {
                                e.printStackTrace();
                                T.show(e.getMessage());
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            L.d(responseString);
                        }
                    } );
        }






    }

    public void settingImage(Intent intent) {
        if(intent == null){
            return;
        }

        UploadImageResult upLoadImageResult = intent.getParcelableExtra(Constants.KEY_UPLOAD_IMAGE_RESULT);
        String id = upLoadImageResult.getId();
        String name = upLoadImageResult.getName();
        imageNames.add(name);
        String imagePuth = myApplication.imagePath;
        Bitmap bitmap = BitmapUtil.getImage(imagePuth);
        if(bitmap != null){
            a++;
        }
        if(a % 3 == 1){
            picture01Iv.setImageBitmap(bitmap);
        }else if(a % 3 == 2){
            picture02Iv.setImageBitmap(bitmap);
        }else if(a %3 == 0){
            picture03Iv.setImageBitmap(bitmap);
        }


    }
}























