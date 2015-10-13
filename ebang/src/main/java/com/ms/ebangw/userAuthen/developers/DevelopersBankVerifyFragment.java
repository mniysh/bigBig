package com.ms.ebangw.userAuthen.developers;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
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

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.Bank;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;
import com.soundcloud.android.crop.Crop;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
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
    private String category;
    private ViewGroup contentLayout;
    private  List<Bank> banks;

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
        int[] resId = new int[]{R.id.tv_a, R.id.tv_b, R.id.tv_c, R.id.tv_d, R.id.tv_e, R.id.tv_f,
                R.id.tv_g, R.id.tv_h, R.id.tv_i, R.id.tv_j, R.id.tv_k, R.id.tv_l, R.id.tv_m, R.id.tv_n, R.id.tv_o, R.id.tv_v};
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
        ((DevelopersAuthenActivity)mActivity).selectPhoto();
    }

    /**
     * 拍正面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeFrontPhoto() {
        ((DevelopersAuthenActivity)mActivity).openCamera();
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
//        String linkman = linkmanEt.getText().toString().trim();
//        String linkmanPhone = linkmanPhoneEt.getText().toString().trim();
        String publicName = publicAccountNameEt.getText().toString().trim();
        String a = publicAccountEt.getText().toString().trim();
        String b = publicAccountTwoEt.getText().toString().trim();
        String publicAccount = VerifyUtils.bankCard(a);
        String publicAccount2 = VerifyUtils.bankCard(b);
        if (TextUtils.isEmpty(companyName)) {
            T.show("请填写企业名称");
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

        if (TextUtils.isEmpty(businessLiceseNumber)) {
            T.show("请填写营业执照注册号");
            return false;
        }

        if (TextUtils.isEmpty(businessLiceseNumber)) {
            T.show("请填写营业执照注册号");
            return false;
        }

//        if (TextUtils.isEmpty(linkman)) {
//            T.show("请填写企业联系人");
//            return false;
//        }
//
//        if (TextUtils.isEmpty(linkmanPhone)) {
//            T.show("请填写企业联系人电话");
//            return false;
//        }

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

        if (!isImageUploaded) {
            T.show("请上传组织机构代码证扫描件");
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
        String account = publicAccountEt.getText().toString().trim();
        String publicAccount = VerifyUtils.bankCard(account);
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
        setEt();
    }



    @Override
    public void initData() {
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

    public void handleCrop(int resultCode, Intent result) {
        if (resultCode == mActivity.RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            L.d("Uri: " + uri);
            frontIv.setImageURI(Crop.getOutput(result));
            uploadImage(uri);
        } else if (resultCode == Crop.RESULT_ERROR) {
            T.show("选取图片失败");
        }
    }

    private void initBankSpinner() {
        banks = ((DevelopersAuthenActivity)mActivity).getBanks();
        ArrayAdapter<Bank> bankArrayAdapter = new ArrayAdapter<>(mActivity, R.layout.layout_spinner_item,
            banks);
        bankSp.setAdapter(bankArrayAdapter);
        bankSp.setSelection(0, true);

    }

    private void uploadImage(Uri uri) {
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
                        AuthInfo authInfo = ((DevelopersAuthenActivity) mActivity).getAuthInfo();
                        authInfo.setOrganizationCertificate(id);
                        isImageUploaded = true;
                        T.show("上传图片成功");
                    } else {
                        T.show("上传图片失败,请重试");
                        isImageUploaded = false;
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
    private void setEt() {

        VerifyUtils.setBankCard(publicAccountTwoEt);
        VerifyUtils.setBankCard(publicAccountEt);

    }




}
