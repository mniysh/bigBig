package com.ms.ebangw.release;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.ReleaseInfo;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;

public class PayingActivity extends BaseActivity {
    private ReleaseProject releaseProject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paying);
        initView();
        initData();



    }
    @Override
    public void initView() {
        initTitle(null,null, "结算", null, null);
        Intent intent = getIntent();
        releaseProject = intent.getExtras().getParcelable(Constants.RELEASE_WORKTYPE_KEY);

    }

    @Override
    public void initData() {

    }

}
