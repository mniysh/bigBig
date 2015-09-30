package com.ms.ebangw.release;


import android.content.Intent;
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

import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.view.ProvinceAndCityView;

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

    private String mParam1;
    private String mParam2;
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
            mParam1 = getArguments().getString(ARG_PARAM1);
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
        List<Province> provinces = homeActivity.getTotalRegion().getProvince();
        provinceAndCityView.setProvinces(provinces);

    }

    /*** 打开照相机     */
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

    /**
     * 开发商发布
     */
    @OnClick(R.id.btn_release)
    public void developerRelease() {

    }

}
