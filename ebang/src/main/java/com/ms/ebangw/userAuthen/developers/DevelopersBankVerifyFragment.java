package com.ms.ebangw.userAuthen.developers;


import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.Bank;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.crop.FroyoAlbumDirFactory;
import com.ms.ebangw.crop.GetPathFromUri4kitkat;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.CropImageUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开发商银行信息验证
 * @author wangkai
 */
public class DevelopersBankVerifyFragment extends BaseFragment {
    private static final String CATEGORY = "category";
    private final int REQUEST_PICK = 4;
    private final int REQUEST_CAMERA = 6;
    private final int REQUEST_CROP = 8;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private com.ms.ebangw.crop.AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private String category;
    private ViewGroup contentLayout;
    private  List<Bank> banks;
    private String mCurrentPhotoPath;

    private List<Province> provinces, bankProvinces;
    private Province province, bankProvince;
    ArrayAdapter<Province> adapter01, bankProvinceAdapter;
    ArrayAdapter<City> adapter02, bankCityAdapter;
    /**
     * 组织机构代码证扫描件是否已上传
     */
    private boolean isImageUploaded = false;

    @Bind(R.id.sp_a)
    Spinner permitProvinceSp;
    @Bind(R.id.sp_b)
    Spinner permitCitySp;
    @Bind(R.id.et_often_address)
    EditText oftenAddressEt;

    @Bind(R.id.et_company_name)
    EditText companyNameEt;


    /**
     * 长期(值为1是长期, 2为非长期）
     */
    @Bind(R.id.et_business_age)
    EditText businessAgeEt;
    @Bind(R.id.cb_isLong)
    CheckBox isLongCb;
    @Bind(R.id.et_business_scope)
    EditText businessScopeEt;
    @Bind(R.id.et_company_number)
    EditText companyNumberEt;
    @Bind(R.id.et_stable_phone)
    EditText stablePhoneEt;
    @Bind(R.id.et_business_license_number)
    EditText businessLiceseNumberEt;
//    @Bind(R.id.et_company_linkman)
//    EditText linkmanEt;
//    @Bind(R.id.et_company_linkman_phone)
//    EditText linkmanPhoneEt;
    /**正面身份证选择图片*/
    @Bind(R.id.btn_select_front)
    Button uploadFrontBtn;
    /**正面身份证拍照*/
    @Bind(R.id.btn_photo_front)
    Button photoFrontBtn;
    @Bind(R.id.iv_front)
    ImageView frontIv;
    @Bind(R.id.et_public_account_name)
    EditText publicAccountNameEt;
    @Bind(R.id.et_public_account)
    EditText publicAccountEt;
    @Bind(R.id.et_public_account_two)
    EditText publicAccountTwoEt;
    @Bind(R.id.sp_bank)
    Spinner bankSp;
    @Bind(R.id.sp_bank_province)
    Spinner bankProvinceSp;
//    @Bind(R.id.sp_bank_city)
//    Spinner bankCitySp;


//
    public static DevelopersBankVerifyFragment newInstance(String category) {
        DevelopersBankVerifyFragment fragment = new DevelopersBankVerifyFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public DevelopersBankVerifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }

        if (savedInstanceState != null) {
            mCurrentPhotoPath = savedInstanceState.getString(Constants.KEY_CURRENT_IMAGE_PATH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_developers_bank_verify, container,
            false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();

        return contentLayout;
    }

    /**
     * 把*变成红色
     */
    public void setStarRed() {
        int[] resId = new int[]{R.id.tv_a, R.id.tv_b, R.id.tv_c, R.id.tv_d,R.id.tv_e,R.id.tv_f,R.id.tv_g,R.id.tv_h,R.id.tv_k};
        for (int i = 0; i < resId.length; i++) {
            TextView a = (TextView) contentLayout.findViewById(resId[i]);
            String s = a.getText().toString();
            SpannableString spannableString = new SpannableString(s);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            a.setText(spannableString);
        }
    }

    /**
     * 选择正面照片
     */
    @OnClick(R.id.btn_select_front)
    public void selectFrontPhoto() {
        selectPhoto();
    }

    /**
     * 拍正面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeFrontPhoto() {

        captureImageByCamera();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != mActivity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CAMERA ) { //拍照返回
            handleBigCameraPhoto();

        }else if (requestCode == REQUEST_PICK) {
            Uri uri = data.getData();
            Log.d("way", "uri: " + uri);

            try {
                String path = GetPathFromUri4kitkat.getPath(mActivity, uri);
                Bitmap bitmap = BitmapUtil.getImage(path);
                int bitmapDegree = CropImageUtil.getBitmapDegree(path);
                if (bitmapDegree != 0) {
                    bitmap = CropImageUtil.rotateBitmapByDegree(bitmap, bitmapDegree);
                }
                MyApplication myApplication = (MyApplication) mActivity.getApplication();
                myApplication.mBitmap = bitmap;
                goCropActivity();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (requestCode == REQUEST_CROP) {        //剪切后返回
            handleCropBitmap(data);
        }
    }



    private boolean isInfoCorrect() {
        String businessYears = businessAgeEt.getText().toString().trim();
        boolean checked = isLongCb.isChecked();
        String isLong = "2";
        if (checked) {
            isLong = "1";
        }

        String companyName = companyNameEt.getText().toString().trim();
        String oftenAddress = oftenAddressEt.getText().toString().trim();
        String businessScope = businessScopeEt.getText().toString().trim();
        String companyNumber = companyNumberEt.getText().toString().trim();
        String companyPhone = stablePhoneEt.getText().toString().trim();
        String businessLiceseNumber = businessLiceseNumberEt.getText().toString().trim();
        String publicName = publicAccountNameEt.getText().toString().trim();
        String aa = publicAccountEt.getText().toString().trim();
        String cc = publicAccountTwoEt.getText().toString().trim();


        String publicAccount = VerifyUtils.bankCard(aa);
        String publicAccount2 = VerifyUtils.bankCard(cc);
        if (TextUtils.isEmpty(companyName)) {
            T.show("请填写企业名称");
            return false;
        }

        if (TextUtils.isEmpty(businessLiceseNumber)) {
            T.show("请填写营业执照注册号");
            return false;
        }

        if (TextUtils.isEmpty(oftenAddress)) {
            T.show("请填写常住地址");
            return false;
        }

        if (TextUtils.isEmpty(businessYears)) {
            T.show("请输入营业年限");
            return false;
        }

        if (TextUtils.isEmpty(businessScope)) {
            T.show("请填写经营范围");
            return false;
        }

        if (TextUtils.isEmpty(companyNumber)) {
            T.show("请填写组织机构代码证号");
            return false;
        }

        if (TextUtils.isEmpty(companyPhone)) {
            T.show("请填写公司座机");
            return false;
        }

        if (!isImageUploaded) {
            T.show("请上传组织机构代码证扫描件");
            return false;
        }


        if (TextUtils.isEmpty(publicName)) {
            T.show("请填写对公帐户户名");
            return false;
        }


        if (TextUtils.isEmpty(publicAccount)) {
            T.show("请填写对公帐户");
            return false;
        }

        if (!TextUtils.equals(publicAccount, publicAccount2)) {
            T.show("对公帐户两次输入不一致");
            return false;
        }



        return true;
    }

    public void initSpinner() {
        provinces = getProvinces();
        if (null == provinces) {
            return;
        }

        adapter01 = new ArrayAdapter<>(mActivity,
            R.layout.layout_spinner_item, provinces);


        permitProvinceSp.setAdapter(adapter01);

        adapter02 = new ArrayAdapter<>(mActivity, R.layout.layout_spinner_item, provinces
            .get(0).getCitys());
        permitCitySp.setAdapter(adapter02);

        permitProvinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                province = provinces.get(position);

                adapter02 = new ArrayAdapter<>(mActivity,
                    R.layout.layout_spinner_item, provinces.get(
                    position).getCitys());

                permitCitySp.setAdapter(adapter02);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        permitProvinceSp.setSelection(0, true);
        permitCitySp.setSelection(0, true);
    }

    public void initPublicAccountAddressSpinner() {
        bankProvinces = getProvinces();
        if (null == bankProvinces) {
            return;
        }

        bankProvinceAdapter = new ArrayAdapter<>(mActivity,
            R.layout.layout_spinner_item, bankProvinces);


        bankProvinceSp.setAdapter(bankProvinceAdapter);

        bankCityAdapter = new ArrayAdapter<>(mActivity, R.layout.layout_spinner_item, provinces
            .get(0).getCitys());
//        bankCitySp.setAdapter(bankCityAdapter);

        bankProvinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                bankProvince = provinces.get(position);

                bankCityAdapter = new ArrayAdapter<>(mActivity,
                    R.layout.layout_spinner_item, bankProvinces.get(
                    position).getCitys());

                permitCitySp.setAdapter(adapter02);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bankProvinceSp.setSelection(0, true);
//        bankCitySp.setSelection(0, true);
    }

    public List<Province> getProvinces() {
        TotalRegion totalRegion = ((DevelopersAuthenActivity) mActivity).getTotalRegion();
        if (totalRegion == null) {
            return null;
        }else {
            return totalRegion.getProvince();
        }
    }

    private void setAuthInfo() {
        AuthInfo authInfo = ((DevelopersAuthenActivity) mActivity).getAuthInfo();


        String businessYears = businessAgeEt.getText().toString().trim();
        boolean checked = isLongCb.isChecked();
        String isLong = "2";
        if (checked) {
            isLong = "1";
        }
        String companyName = companyNameEt.getText().toString().trim();
        String oftenAddress = oftenAddressEt.getText().toString().trim();
        String businessScope = businessScopeEt.getText().toString().trim();
        String companyNumber = companyNumberEt.getText().toString().trim();
        String companyPhone = stablePhoneEt.getText().toString().trim();
        String businessLiceseNumber = businessLiceseNumberEt.getText().toString().trim();
//        String linkman = linkmanEt.getText().toString().trim();
//        String linkmanPhone = linkmanPhoneEt.getText().toString().trim();
        String publicAccountName = publicAccountNameEt.getText().toString().trim();

        String aa = publicAccountEt.getText().toString().trim();

        String publicAccount = VerifyUtils.bankCard(aa);
//        String publicAccount2 = publicAccountTwoEt.getText().toString().trim();
        authInfo.setCompanyName(companyName);
        authInfo.setOftenAddress(oftenAddress);
        authInfo.setBusinessAge(businessYears);
        authInfo.setBusinessScope(businessScope);
        authInfo.setCompanyNumber(companyNumber);
        authInfo.setCompanyPhone(companyPhone);
        authInfo.setBusinessLicenseNumber(businessLiceseNumber);
//        authInfo.setLinkman(linkman);
//        authInfo.setLinkmanPhone(linkmanPhone);
        authInfo.setPublicAccountName(publicAccountName);
        authInfo.setPublicAccount(publicAccount);
        authInfo.setTimeState(isLong);

        //营业执照所在地
        TextView provinceTv = (TextView) permitProvinceSp.getSelectedView();
        TextView cityTv = (TextView) permitCitySp.getSelectedView();

        String province = null;
        String city = null;
        if (provinceTv != null && cityTv != null) {

            province = provinceTv.getText().toString().trim();
            city = cityTv.getText().toString().trim();
        }
        String  provinceId = null;
        String cityId = null;

        List<Province> provinces = getProvinces();
        for (int i = 0; i < provinces.size(); i++) {
            Province p = provinces.get(i);
            if(TextUtils.equals(p.getName(), province)){
                provinceId = p.getId();
                List<City> citys = p.getCitys();
                for (int j = 0; j < citys.size(); j++) {
                    City c = citys.get(j);
                    if(TextUtils.equals(c.getName(), city)){
                        cityId = c.getId();
                        break;
                    }
                }
                break;
            }
        }

        //开户银行地点
        TextView bankProvinceTv = (TextView) bankProvinceSp.getSelectedView();
//        TextView banCityTv = (TextView) bankCitySp.getSelectedView();

        String bankProvince = null;
        String banCity = null;
        if (bankProvinceTv != null ) {

            bankProvince = provinceTv.getText().toString().trim();
        }
        String  bankProvinceId = null;
        String bankCityId = null;

//        List<Province> provinces = getProvinces();
        for (int i = 0; i < provinces.size(); i++) {
            Province p = provinces.get(i);
            if(TextUtils.equals(p.getName(), bankProvince)){
                bankProvinceId = p.getId();

                break;
            }
        }

        String bankId = null;
        String bankName = null;
        TextView bankTv = (TextView) bankSp.getSelectedView();
        if (null != bankTv) {
            bankName = bankTv.getText().toString().trim();
        }
        for (int i = 0; i < banks.size(); i++) {
            Bank bank = banks.get(i);
            if(TextUtils.equals(bank.getBank_name(), bankName)){
                bankId = bank.getId();
                break;
            }
        }

        authInfo.setPermitProvinceId(provinceId);
        authInfo.setPermitCityId(cityId);
        authInfo.setBankId(bankId);

        authInfo.setPublicAccountProvinceId(bankProvinceId);
        authInfo.setPublicAccountCityId(bankCityId);


    }

    @Override
    public void initView() {
        setStarRed();
        VerifyUtils.setBankCard(publicAccountEt);
        VerifyUtils.setBankCard(publicAccountTwoEt);
    }

    @Override
    public void initData() {
        mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        initSpinner();
        initPublicAccountAddressSpinner();
        initBankSpinner();
    }

    @OnClick(R.id.btn_commit)
    public void completeAuthentication() {
        if (isInfoCorrect()) {
            setAuthInfo();
            ((DevelopersAuthenActivity) mActivity).commit();
        }
    }



    private void initBankSpinner() {
        banks = MyApplication.getInstance().getBanks();
        ArrayAdapter<Bank> bankArrayAdapter = new ArrayAdapter<>(mActivity, R.layout.layout_spinner_item,
            banks);
        bankSp.setAdapter(bankArrayAdapter);
        bankSp.setSelection(0, true);

    }




    /*图片剪切==================*/
    public void handleCropBitmap(Intent intent) {
        if (intent == null) {
            return;
        }
        UploadImageResult imageResult = intent.getParcelableExtra(Constants.KEY_UPLOAD_IMAGE_RESULT);
        MyApplication myApplication = (MyApplication) mActivity.getApplication();
        Bitmap bitmap = myApplication.mBitmap;

        String id = imageResult.getId();
        AuthInfo authInfo = ((DevelopersAuthenActivity) mActivity).getAuthInfo();
        frontIv.setImageBitmap(bitmap);
        authInfo.setOrganizationCertificate(id);
        isImageUploaded = true;
    }

    public void goCropActivity() {

        Intent intent = new Intent(mActivity, CropImageActivity.class);
        startActivityForResult(intent, REQUEST_CROP);

    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic(mCurrentPhotoPath , 400, 800);
            galleryAddPic();
            mCurrentPhotoPath = null;
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mActivity.sendBroadcast(mediaScanIntent);
    }


    private void setPic(String path, int targetW, int targetH) {

        Bitmap bitmap = BitmapUtil.getImage(path);
        int bitmapDegree = CropImageUtil.getBitmapDegree(path);
        if (bitmapDegree != 0) {
            bitmap = CropImageUtil.rotateBitmapByDegree(bitmap, bitmapDegree);
        }

        MyApplication application = (MyApplication) mActivity.getApplication();
        application.mBitmap = bitmap;

        Intent intent = new Intent(mActivity, CropImageActivity.class);
        startActivityForResult(intent, REQUEST_CROP);

    }


    //拍照与选择图片剪切相关

    public void selectPhoto() {
        // 选择图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK);
    }


    /**
     * 拍照
     */
    public void captureImageByCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f;

        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }

        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }


    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private String getAlbumName() {
        return "crop";
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.KEY_CURRENT_IMAGE_PATH, mCurrentPhotoPath);
        L.d("onSaveInstanceState: " + mCurrentPhotoPath);
        super.onSaveInstanceState(outState);
    }
}
