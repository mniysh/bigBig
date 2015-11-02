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
import com.ms.ebangw.commons.Constants;

public class PayingActivity extends BaseActivity {
    private ReleaseInfo releaseInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paying);
        initView();
        initData();



    }
    @Override
    public void initView() {
        Intent intent = getIntent();
        releaseInfo = intent.getExtras().getParcelable(Constants.KEY_RELEASE_INFO);

    }

    @Override
    public void initData() {

    }

}
