package com.ms.ebangw.release;


import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.SelectMapLocActivity;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.ReleaseInfo;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.dialog.DatePickerFragment;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.CropEnableFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.ImageLoaderutils;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;
import com.ms.ebangw.view.ProvinceAndCityView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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
public class IncreaseDetailFragment extends CropEnableFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_PICK = 4;
    private static final int REAQUEST_CROP = 8;
    private static final int MAP_LOCATION = 11;
    private ImageLoader loader;
    private DisplayImageOptions options;

    private String whickPhoto ;
    private int a;
    private ArrayList<String> dataUrl;

    //经纬度
    private float longitude;
    private float latitude;
    private CountDownTimer countDownTimer;
    private Handler mHandler;

//    private String JPEG_FILE_PREFIX =

    private ReleaseInfo releaseInfo;

    private ArrayList<String> imageNames;
    private File file;


    private String mParam1;
    private String mParam2;
    private String staff;
    private String pay_type = "月结";
    private String image_ary;
    private ViewGroup contentLayout;
    private File imageFile;
    private List<ImageView> picList;
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
    //总金额
    @Bind(R.id.et_totalMoney)
    EditText totalMoneyEt;

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
    @Bind(R.id.tv_address)
    TextView selectAddTv;
    @Bind(R.id.et_verifyCode)
    EditText etVerifyCode;
    @Bind(R.id.tv_verify_code)
    TextView tvVerifyCode;



    private String province, city , area, detailAddress, title, link_name, link_phone, verifyCode,
        description, startTime, totalMoney, endTime, selectMapAdd;
    private String provinceId, cityId, areaId;
    private ReleaseProject releaseProject;
    private String categroy;
    private static  final String KEY_CATEGROY = "key_categroy";
    private static final String  KEY_MONEY = "key_money";
    private ArrayList<Bitmap> dataBit ;
    private ArrayList<String> dataFilePath;
    private long money = 0;




    public static IncreaseDetailFragment newInstance(String param1, String categroy,long money) {
        IncreaseDetailFragment fragment = new IncreaseDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(KEY_CATEGROY, categroy);
        args.putLong(KEY_MONEY, money);
        fragment.setArguments(args);
        return fragment;
    }

    public IncreaseDetailFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            staff = getArguments().getString(ARG_PARAM1);
            categroy = getArguments().getString(KEY_CATEGROY);
            money = getArguments().getLong(KEY_MONEY);
        }
        options = ImageLoaderutils.getOpt();
        loader = ImageLoaderutils.getInstance(mActivity);

        if (null != savedInstanceState) {
            releaseInfo = savedInstanceState.getParcelable(Constants.KEY_RELEASE_INFO);
            dataFilePath = savedInstanceState.getStringArrayList("duang");
            imageNames = savedInstanceState.getStringArrayList(Constants.KEY_PROJECT_IMAGES);

            if(dataUrl != null){
                T.show("长度是"+dataUrl.size());
            }

            if (null == picList) {
                picList = new ArrayList<>();
                picList.add(picture01Iv);
                picList.add(picture02Iv);
                picList.add(picture03Iv);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    //地图获取信息
    @OnClick({R.id.tv_address})
    public void getMap(){

        Intent intent = new Intent(mActivity, SelectMapLocActivity.class);
        startActivityForResult(intent, MAP_LOCATION);
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
                startTimeTv.setTag(calendar.getTimeInMillis());
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
                endTimeTv.setTag(calendar.getTimeInMillis());
            }
        });
        datePickerFragment.show(getFragmentManager(), "date");
    }

    /**
     * 设置星号
     */
    public void setStartRed(){
        int[] arr = {R.id.tv_a,R.id.tv_b,R.id.tv_c,
                R.id.tv_d,R.id.tv_e,R.id.tv_f,
                R.id.tv_g,R.id.tv_h,
                R.id.tv_i,R.id.tv_j,R.id.tv_k,R.id.tv_l, R.id.tv_m};
        for (int i = 0; i < arr.length ; i++) {
            TextView a = (TextView) contentLayout.findViewById(arr[i]);
            if(a != null){
                String s = a.getText().toString().trim();
                SpannableString  spa = new SpannableString(s);
                spa.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                a.setText(spa);
            }

        }
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

        link_phone = phoneEt.getText().toString().trim();
        verifyCode = etVerifyCode.getText().toString().trim();
        description = introduceEt.getText().toString().trim();
        provinceId = provinceAndCityView.getProvinceId();
        cityId = provinceAndCityView.getCityId();
//        areaId = provinceAndCityView.getAreaId();
        startTime = startTimeTv.getText().toString().trim();
        endTime = endTimeTv.getText().toString().trim();
        totalMoney = totalMoneyEt.getText().toString().trim();
        if(releaseInfo == null){
            releaseInfo = new ReleaseInfo();
        }


        releaseInfo.setTitle(title);
        releaseInfo.setDescription(description);
        releaseInfo.setLink_man(link_name);
        releaseInfo.setLink_phone(link_phone);
        releaseInfo.setVerifyCode(verifyCode);
        releaseInfo.setProvince(provinceId);
        releaseInfo.setCity(cityId);
        releaseInfo.setPoint_dimention(latitude);
        releaseInfo.setPoint_longitude(longitude);
        releaseInfo.setImage_ary(image_ary);
        releaseInfo.setStart_time(startTime);
        releaseInfo.setEnd_time(endTime);
        releaseInfo.setProject_money(totalMoney);
        releaseInfo.setStaff(staff);
    }

    /**
     *
     * @param data
     * @return
     */
    public String disposeImage(List<String> data){
        Gson gson = new Gson();
        return gson.toJson(data);
    }


    public boolean isRight(){
        if(dataFilePath == null || dataFilePath.size() == 0){
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
         if(TextUtils.isEmpty(verifyCode)){
            T.show("请输入验证码");
            return  false;
        }

        if(TextUtils.isEmpty(description)){
            T.show("简介不可为空");
            return  false;
        }
        if(TextUtils.isEmpty(selectMapAdd) || (longitude == 0 && latitude == 0)){
            T.show("请地图选点");
            return false;
        }
        if(TextUtils.isEmpty(totalMoney) ){
            T.show("工程总额不能为空");
            return false;
        }
        if(Integer.valueOf(totalMoney) < money){
            T.show("工程总额不能低于" + money + "元");
            return false;
        }


        if (TextUtils.isEmpty(startTimeTv.getText().toString())) {
            T.show("请输入上门时间");
            return false;
        }

        if (TextUtils.isEmpty(endTimeTv.getText().toString())) {
            T.show("请输入结束时间");
            return false;
        }

        long startTime = (long) startTimeTv.getTag();
        long endTime = (long) endTimeTv.getTag();
        if (startTime >= endTime) {
            T.show("结束时间应该在开始时间之后");
            return false;
        }
        return true;
    }



    @Override
    public void initView() {
        if(imageNames == null){
            imageNames = new ArrayList<String>();
        }

        if(dataFilePath == null){

            dataFilePath = new ArrayList<String>();
        }
        setStartRed();
    }

    @Override
    public void initData() {
        picList = new ArrayList<>();
        picList.add(picture01Iv);
        picList.add(picture02Iv);
        picList.add(picture03Iv);

        List<Province> provinces = getAreaFromAssets().getProvince();
        provinceAndCityView.setProvinces(provinces);
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int what = msg.what;
                if (what == 0) {
                    tvVerifyCode.setPressed(false);
                    tvVerifyCode.setClickable(true);
                    tvVerifyCode.setText("获取验证码");
                }else {
                    tvVerifyCode.setText(what + " 秒");
                }

                return false;
            }
        });
    }

    /**
     * 图片
     * @param view
     */
    @OnClick(R.id.btn_pick)
    public void selectGallery(View view) {
        selectPhoto(view, CropImageActivity.TYPE_PUBLIC);
    }

    /**
     * 拍照
     * @param view
     */
    @OnClick(R.id.btn_camera)
    public void selectCamera(View view) {
        captureImageByCamera(view, CropImageActivity.TYPE_PUBLIC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mActivity.RESULT_OK) {
            if (requestCode == MAP_LOCATION) {//地图选点结果
                if (null != data) {
                    Bundle extras = data.getExtras();
                    PoiInfo poiInfo = extras.getParcelable(Constants.KEY_POIINFO_STR);
                    selectMapAdd = poiInfo.address;
                    LatLng location = poiInfo.location;
                    latitude = (float) location.latitude;
                    longitude = (float) location.longitude;
                    selectAddTv.setText(selectMapAdd);
                    L.d("latitude: " + latitude + " , longitude: " + longitude);
                }
            }
        }
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
         file = uriToFile(uri);

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
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if ( index > -1 ) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        File file = new File(data);
        return file;
    }

    @OnClick(R.id.btn_release)
    public void releaseProject() {
        User user = getUser();
        if(user == null){
            return;
        }
        getData();

        if(isRight()){
            DataAccessUtil.releaseProject(title, description, link_name, link_phone, verifyCode,
                    provinceId, cityId, detailAddress,
                    longitude, latitude, image_ary, startTime, endTime, totalMoney, staff,
                    new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                releaseProject = new ReleaseProject();
                                boolean b = DataParseUtil.processDataResult(response);
                                if (b) {
                                    T.show("开发商发布成功");
                                    releaseProject = DataParseUtil.getProjectInfo(response);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable(Constants.KEY_RELEASE_PROJECT, releaseProject);
                                    Intent intent = new Intent(mActivity, PayingActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    mActivity.finish();
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
                    });
        }

    }

    @Override
    public void onCropImageSuccess(View view, String cropedImagePath, UploadImageResult imageResult) {
        super.onCropImageSuccess(view, cropedImagePath, imageResult);

        int size = dataFilePath.size();
        if (size == 3) {        //只有三张图片，如果大于三张，删除第一张
            dataFilePath.remove(0);
            imageNames.remove(0);
        }
        dataFilePath.add(cropedImagePath);
        imageNames.add(imageResult.getName());
        if(dataFilePath != null){
            for (int i = 0; i <dataFilePath.size() ; i++) {
                picList.get(i).setImageBitmap(BitmapUtil.getImage(dataFilePath.get(i)));
            }
        }
    }

    @OnClick(R.id.tv_verify_code)
    public void goRegister2(View view) {
        String phone = phoneEt.getText().toString().trim();
        if (VerifyUtils.isPhone(phone)) {

            DataAccessUtil.messageCode(phone, new JsonHttpResponseHandler(){
                @Override
                public void onStart() {
                    executeCountDown();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        boolean b = DataParseUtil.messageCode(response);
                        L.d("xxx",b+"b的值");
                        if (b) {
                            T.show("验证码已发送，请注意查收");
                        }

                    } catch (ResponseException e) {
                        e.printStackTrace();
                        T.show(e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }else {
            T.show("请输入正确的手机号");

        }
    }

    private void executeCountDown() {
        tvVerifyCode.setPressed(true);
        tvVerifyCode.setClickable(false);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mHandler.sendEmptyMessage((int)(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mHandler.sendEmptyMessage(0);
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.KEY_RELEASE_INFO, releaseInfo);
        outState.putStringArrayList(Constants.KEY_PROJECT_IMAGES, imageNames);
        outState.putStringArrayList("duang", dataFilePath);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        ButterKnife.unbind(this);
    }
}