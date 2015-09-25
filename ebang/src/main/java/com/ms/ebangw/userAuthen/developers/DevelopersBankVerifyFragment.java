package com.ms.ebangw.userAuthen.developers;


import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.AuthInfo;
import com.ms.ebangw.bean.City;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.bean.TotalRegion;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.userAuthen.investor.InvestorAuthenActivity;
import com.ms.ebangw.utils.T;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户银行信息验证
 * @author wangkai
 */
public class DevelopersBankVerifyFragment extends BaseFragment {
    private static final String CATEGORY = "category";
    private String category;
    private ViewGroup contentLayout;
    private List<Province> provinces;
    private Province province;
    ArrayAdapter<Province> adapter01;
    ArrayAdapter<City> adapter02;

    @Bind(R.id.sp_a)
    Spinner provinceSp;
    @Bind(R.id.sp_b)
    Spinner citySp;
    @Bind(R.id.sp_bank)
    Spinner bankSp;
    @Bind(R.id.et_identify_card)
    EditText cardEt;
    @Bind(R.id.et_real_name)
    EditText reaNameEt;
    @Bind(R.id.btn_commit)
    Button commitBtn;

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
        int[] resId = new int[]{R.id.tv_a, R.id.tv_b, R.id.tv_c, R.id.tv_d};
        for (int i = 0; i < resId.length; i++) {
            TextView a = (TextView) contentLayout.findViewById(resId[i]);
            String s = a.getText().toString();
            SpannableString spannableString = new SpannableString(s);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            a.setText(spannableString);
        }
    }


    private boolean isInfoCorrect() {
        String realName = reaNameEt.getText().toString().trim();
        String cardId = cardEt.getText().toString().trim();
        if (TextUtils.isEmpty(realName)) {
            T.show("请输入真实姓名");
            return false;
        }

        if (TextUtils.isEmpty(cardId)) {
            T.show("请输入银行卡号");
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


        provinceSp.setAdapter(adapter01);

        adapter02 = new ArrayAdapter<>(mActivity, R.layout.layout_spinner_item, provinces
            .get(0).getCitys());
        citySp.setAdapter(adapter02);

        provinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                province = provinces.get(position);

                adapter02 = new ArrayAdapter<>(mActivity,
                    R.layout.layout_spinner_item, provinces.get(
                    position).getCitys());

                citySp.setAdapter(adapter02);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        provinceSp.setSelection(0, true);
        citySp.setSelection(0, true);


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
        String realName = reaNameEt.getText().toString().trim();
        String cardId = cardEt.getText().toString().trim();
        //获取开户行
        TextView provinceTv = (TextView) provinceSp.getSelectedView();
        TextView cityTv = (TextView) citySp.getSelectedView();

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

        String bankNameId = null;
        TextView bankTv = (TextView) bankSp.getSelectedView();
        if (null != bankTv) {
            bankNameId = bankTv.getText().toString().trim();
        }
        authInfo.setBankProvinceId(provinceId);
        authInfo.setBankCityId(cityId);
        authInfo.setBankId(bankNameId);
        authInfo.setRealName(realName);
        authInfo.setBankCard(cardId);
    }

    @Override
    public void initView() {
        setStarRed();
    }

    @Override
    public void initData() {
        initSpinner();
    }

    @OnClick(R.id.btn_commit)
    public void completeAuthentication() {
        if (isInfoCorrect()) {
            setAuthInfo();
            ((InvestorAuthenActivity) mActivity).commit();
        }
    }


}
