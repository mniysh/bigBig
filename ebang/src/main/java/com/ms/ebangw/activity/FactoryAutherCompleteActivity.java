package com.ms.ebangw.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ProvinceAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开发商完善页面
 */
public class FactoryAutherCompleteActivity extends BaseActivity {
    private LinearLayout layout;
    private ProvinceAdapter adapter;
    private String[] datas={"数据1","数据2","数据3","数据4","数据5","数据6"};
    @Bind(R.id.sp_type)
    Spinner sType;
    @Bind(R.id.sp_bankadd)
    Spinner sBandAdd;

    @Bind(R.id.bt_commit)
    Button nNext;
    @OnClick(R.id.bt_commit)
    void  next(){
        layout=(LinearLayout) getLayoutInflater().inflate(R.layout.popup_lay, null, false);
        Button bBack= (Button) layout.findViewById(R.id.popup_lay_iv_back);
        pWindow(layout,600,LinearLayout.LayoutParams.WRAP_CONTENT,bBack,null,null,null,nNext, Gravity.TOP,0,150);
    }

    @Override
    public void initView() {
        initTitle(null,"返回","企业认证",null,null);
        adapter=new ProvinceAdapter(datas);
        sType.setAdapter(adapter);
        sBandAdd.setAdapter(adapter);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_auther_complete);
        ButterKnife.bind(this);
        initView();
        initData();
    }


}
