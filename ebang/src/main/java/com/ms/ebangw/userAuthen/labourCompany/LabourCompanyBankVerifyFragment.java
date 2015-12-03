package com.ms.ebangw.userAuthen.labourCompany;


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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.Bank;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.fragment.CropEnableFragment;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.utils.VerifyUtils;
import com.ms.ebangw.view.ProvinceAndCityView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开发商银行信息验证
 * @author wangkai
 */
public class LabourCompanyBankVerifyFragment extends CropEnableFragment {
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
    @Bind(R.id.pac_bank)
    ProvinceAndCityView pacBankPlace;
    @Bind(R.id.pac_permit)
    ProvinceAndCityView pacPermitPlace;



//
    public static LabourCompanyBankVerifyFragment newInstance(String category) {
        LabourCompanyBankVerifyFragment fragment = new LabourCompanyBankVerifyFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public LabourCompanyBankVerifyFragment() {
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
    public void selectFrontPhoto(View view) {
        selectPhoto(view, CropImageActivity.TYPE_PRIVATE);
    }

    /**
     * 拍正面身份证照
     */
    @OnClick(R.id.btn_photo_front)
    public void takeFrontPhoto(View view) {
        captureImageByCamera(view, CropImageActivity.TYPE_PRIVATE);
    }

    private boolean isInfoCorrect() {
        String businessYears = businessAgeEt.getText().toString().trim();
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




    private void setAuthInfo() {
        AuthInfo authInfo = ((LabourCompanyAuthenActivity) mActivity).getAuthInfo();


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
        String publicAccountName = publicAccountNameEt.getText().toString().trim();

        String publicAccountNoVerifyed = publicAccountEt.getText().toString().trim();

        String publicAccount = VerifyUtils.bankCard(publicAccountNoVerifyed);
        authInfo.setCompanyName(companyName);
        authInfo.setOftenAddress(oftenAddress);
        authInfo.setBusinessAge(businessYears);
        authInfo.setBusinessScope(businessScope);
        authInfo.setCompanyNumber(companyNumber);
        authInfo.setCompanyPhone(companyPhone);
        authInfo.setBusinessLicenseNumber(businessLiceseNumber);
        authInfo.setPublicAccountName(publicAccountName);
        authInfo.setPublicAccount(publicAccount);
        authInfo.setTimeState(isLong);

        //营业执照所在地
        String provinceId = pacPermitPlace.getProvinceId();
        String cityId = pacPermitPlace.getCityId();
        String  bankProvinceId = pacBankPlace.getProvinceId();
        String bankCityId = pacBankPlace.getCityId();
        String bankId = getBankId();

        authInfo.setPermitProvinceId(provinceId);
        authInfo.setPermitCityId(cityId);
        authInfo.setBankId(bankId);
        authInfo.setPublicAccountProvinceId(bankProvinceId);
        authInfo.setPublicAccountCityId(bankCityId);

    }

    /**
     * 获取选中银行的id
     * @return
     */
    private String getBankId() {
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
        return bankId;
    }

    @Override
    public void initView() {
        setStarRed();
        VerifyUtils.setBankCard(publicAccountEt);
        VerifyUtils.setBankCard(publicAccountTwoEt);
    }

    @Override
    public void initData() {
        provinces = getProvinces();
        if (null == provinces) {
            return;
        }
        pacPermitPlace.setProvinces(provinces);
        pacBankPlace.setProvinces(provinces);
        initBankSpinner();
    }

    @OnClick(R.id.btn_commit)
    public void completeAuthentication() {
        if (isInfoCorrect()) {
            setAuthInfo();
            ((LabourCompanyAuthenActivity) mActivity).commit();
        }
    }



    private void initBankSpinner() {
        banks = getBanks();
        ArrayAdapter<Bank> bankArrayAdapter = new ArrayAdapter<>(mActivity, R.layout.layout_spinner_item,
            banks);
        bankSp.setAdapter(bankArrayAdapter);
        bankSp.setSelection(0, true);

    }

    @Override
    public void onCropImageSuccess(View view, String cropedImagePath, UploadImageResult imageResult) {
        super.onCropImageSuccess(view, cropedImagePath, imageResult);

        String id = imageResult.getId();
        AuthInfo authInfo = ((LabourCompanyAuthenActivity) mActivity).getAuthInfo();
        Bitmap bitmap = BitmapUtil.getImage(cropedImagePath);
        frontIv.setImageBitmap(bitmap);
        authInfo.setOrganizationCertificate(id);
        isImageUploaded = true;
    }
}
