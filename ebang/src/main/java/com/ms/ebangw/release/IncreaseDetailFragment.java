package com.ms.ebangw.release;


import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.activity.SelectMapLocActivity;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.ReleaseInfo;
import com.ms.ebangw.bean.ReleaseProject;
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
import com.ms.ebangw.utils.ImageLoaderutils;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;
import com.ms.ebangw.view.ProvinceAndCityView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

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
    private static final int MAP_LOCATION = 11;
    private ImageLoader loader;
    private DisplayImageOptions options;

    private String whickPhoto ;
    private int a;
    private ArrayList<String> dataUrl;

    //经纬度
    private float longitude;
    private float latitude;

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
    EditText startTimeTv;
    @Bind(R.id.tv_end_time)
    EditText endTimeTv;
    @Bind(R.id.tv_selectMapAdd)
    TextView selectAdd;
    @Bind(R.id.et_address)
    EditText selectAddEt;




    private String province, city , area, detailAddress, title, link_name, link_phone,
        description, startTime, totalMoney, endTime, selectMapAdd;
    private String provinceId, cityId, areaId;
    private String mCurrentPhotoPath;
    private MyApplication myApplication;
    private ReleaseProject releaseProject;
    private int  startYear,  startMonth,  startDay, endYear, endMonth, endDay;
    private String categroy;
    private static  final String KEY_CATEGROY = "key_categroy";
    private ArrayList<Bitmap> dataBit ;




    public static IncreaseDetailFragment newInstance(String param1, String categroy) {
        IncreaseDetailFragment fragment = new IncreaseDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(KEY_CATEGROY, categroy);
        fragment.setArguments(args);
        return fragment;
    }

    public IncreaseDetailFragment() {
        // Required empty public constructor
    }
    //地图获取信息
    @OnClick({R.id.et_address, R.id.tv_selectMapAdd})
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
                startYear = year;
                startMonth = monthOfYear;
                startDay = dayOfMonth;
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
                endYear = year;
                endMonth = monthOfYear;
                endDay = dayOfMonth;
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
            categroy = getArguments().getString(KEY_CATEGROY);
        }
        options = ImageLoaderutils.getOpt();
        loader = ImageLoaderutils.getInstance(mActivity);

        if (null != savedInstanceState) {
            mCurrentPhotoPath = savedInstanceState.getString(Constants.KEY_CURRENT_IMAGE_PATH);
            releaseInfo = savedInstanceState.getParcelable(Constants.KEY_RELEASE_INFO);
            imageNames = savedInstanceState.getStringArrayList(Constants.KEY_PROJECT_IMAGES);
            dataUrl = savedInstanceState.getStringArrayList(Constants.KEY_PROJECT_IMAGE_URL);
            dataBit = savedInstanceState.getParcelableArrayList("ceshi");
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
     * 设置星号
     */
    public void setStartRed(){
        int[] arr = {R.id.tv_a,R.id.tv_b,R.id.tv_c,
                R.id.tv_d,R.id.tv_e,R.id.tv_f,
                R.id.tv_g,R.id.tv_h,
                R.id.tv_i,R.id.tv_j,R.id.tv_k,R.id.tv_l};
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
     *  RequestParams params = new RequestParams();
     params.put("title",title);
     params.put("description",description);
     params.put("link_man",link_man);
     params.put("link_phone",link_phone);
     params.put("province",province);
     params.put("city",city);
     params.put("area_other",area_other);
     params.put("point_longitude",point_longitude);
     params.put("point_dimension",point_dimension);
     params.put("start_time",start_time);
     params.put("end_time", end_time);
     params.put("project_money",project_money);
     params.put("image_ary",image_ary);
     params.put("staffs", staffs);
     *
     * @param data
     * @return
     */
    public String disposeImage(List<String> data){
        Gson gson = new Gson();
        return gson.toJson(data);
    }


    public boolean isRight(){
        if(dataBit == null || dataBit.size() == 0){
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
        if(!VerifyUtils.isRightTime(startYear, startMonth, startDay,endYear, endMonth, endDay)){
            T.show("时间不正确");
            return false;
        }
        if(!VerifyUtils.isRight(startYear, startMonth, startDay)){
            T.show("时间不正确");
            return false;
        }

        return true;
    }



    @Override
    public void initView() {
        imageNames = new ArrayList<String>();
//        dataUrl = new ArrayList<String>();
        dataBit = new ArrayList<Bitmap>();
        setStartRed();
        startTimeTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!v.hasFocus()) {
//                    String a = startTimeTv.getText().toString().trim();
                    if (!VerifyUtils.isRight(startYear, startMonth, startDay)) {
                        T.show("时间不正确");
                        startTimeTv.setText("");
                        startTimeTv.setHint("请重新选择");
                    }

                }
            }
        });
        endTimeTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!v.hasFocus()) {
                    if (!VerifyUtils.isRight(endYear, endMonth, endDay)) {
                        T.show("时间不正确");
                        endTimeTv.setText("");
                        endTimeTv.setHint("请重新选择");
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        picList = new ArrayList<>();
        picList.add(picture01Iv);
        picList.add(picture02Iv);
        picList.add(picture03Iv);

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

        mCurrentPhotoPath = imageFile.getAbsolutePath();
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
        startActivityForResult(intent,Constants.REQUEST_PICK);

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
//                uri = Uri.fromFile(imageFile);
                uri = Uri.fromFile(new File(mCurrentPhotoPath));
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
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_HEAD_IMAGE_STR,"publicImage");
            bundle.putBoolean(Constants.KEY_HEAD_IMAGE,true);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constants.REQUEST_CROP);
        }else if(requestCode == Constants.REQUEST_CROP){
            //剪切后的处理
            settingImage(data);
        }else if(requestCode == MAP_LOCATION ) {//地图选点结果
            if (null != data) {
                Bundle extras = data.getExtras();
                PoiInfo poiInfo = extras.getParcelable(Constants.KEY_POIINFO_STR);
                selectMapAdd = poiInfo.address;
                LatLng location = poiInfo.location;
                latitude = (float)location.latitude;
                longitude = (float)location.longitude;
                selectAddEt.setVisibility(View.GONE);
                selectAdd.setVisibility(View.VISIBLE);
                selectAdd.setText(selectMapAdd);
                L.d("latitude: " + latitude + " , longitude: " + longitude);
            }
        }
    }

    public void handleCrop(int resultCode, Intent result) {

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void beginCrop(Uri source) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        mCurrentPhotoPath = imageFile.getAbsolutePath();
        myApplication = (MyApplication) mActivity.getApplication();
        myApplication.imagePath = mCurrentPhotoPath;
        Intent intent = new Intent(mActivity, CropImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HEAD_IMAGE_STR,"publicImage");
        bundle.putBoolean(Constants.KEY_HEAD_IMAGE,true);
        intent.putExtras(bundle);
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
       // private String province, city , area, detailAddress, title, link_name, link_phone, description;
        User user = getUser();
        if(user == null){
            return;
        }
        getData();

        if(isRight() && TextUtils.equals(categroy, "developer")){
            DataAccessUtil.developerRelease(title, description,link_name,link_phone,
                     provinceId, cityId,detailAddress,
                    longitude, latitude, image_ary,startTime, endTime,totalMoney, staff,
                    new JsonHttpResponseHandler(){

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                releaseProject = new ReleaseProject();
                                boolean b = DataParseUtil.processDataResult(response);
                                if(b){
                                    T.show("开发商发布成功");
                                    releaseProject = DataParseUtil.getProjectInfo(response);
                                    Bundle  bundle = new Bundle();
                                    bundle.putParcelable(Constants.KEY_RELEASE_PROJECT,releaseProject);
                                    Intent intent = new Intent((HomeActivity)mActivity,PayingActivity.class );
                                    intent.putExtras(bundle);
                                    startActivity(intent);
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
        if(isRight() && TextUtils.equals(categroy, "investor")){
            DataAccessUtil.investorRelease(title, description,link_name,link_phone,
                    provinceId, cityId,detailAddress,
                    longitude, latitude, image_ary,startTime, endTime,totalMoney, staff,
                    new JsonHttpResponseHandler(){
                        @Override
                        public void onStart() {
                            super.onStart();
                            showProgressDialog("请稍后");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                boolean b = DataParseUtil.processDataResult(response);
                                if (b){
                                    T.show("个人发布成功");
                                    dismissLoadingDialog();
                                }
                            } catch (ResponseException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            L.d(responseString);
                            dismissLoadingDialog();
                        }
                    });
        }
    }

    public void settingImage(Intent intent) {
        if(intent == null){
            return;
        }

        UploadImageResult upLoadImageResult = intent.getParcelableExtra(Constants.KEY_UPLOAD_IMAGE_RESULT);
        String id = upLoadImageResult.getId();
        String name = upLoadImageResult.getName();
        String imagePath = myApplication.imagePath;
        String url = upLoadImageResult.getUrl();
        Bitmap bitmap = BitmapUtil.getImage(imagePath);


        int size = dataBit.size();
        if (size == 3) {        //只有三张图片，如果大于三张，删除第一张
            dataBit.remove(0);
        }
            dataBit.add(bitmap);
        if(dataBit != null){
            for (int i = 0; i <dataBit.size() ; i++) {
                picList.get(i).setImageBitmap(dataBit.get(i));
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.KEY_CURRENT_IMAGE_PATH, mCurrentPhotoPath);
        outState.putParcelable(Constants.KEY_RELEASE_INFO, releaseInfo);
        outState.putStringArrayList(Constants.KEY_PROJECT_IMAGES, imageNames);
        outState.putStringArrayList(Constants.KEY_PROJECT_IMAGE_URL, dataUrl);
        T.show("保存长度"+dataBit.size());
        outState.putParcelableArrayList("ceshi", dataBit);
        super.onSaveInstanceState(outState);
    }
}