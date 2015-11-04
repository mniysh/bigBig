package com.ms.ebangw.release;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.ReleaseInfo;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.service.DataAccessUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PayingActivity extends BaseActivity {
    private ReleaseProject releaseProject;
    private String title, imageUrl, count, projectMoney;
//    private TextView titleTv, countTv;
    @Bind(R.id.tv_title)
    TextView titleTv;
    @Bind(R.id.tv_content)
    TextView contentTv;
    @Bind(R.id.iv_pic)
    ImageView imageView;
    @Bind(R.id.tv_paying_money)
    TextView moneyTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paying);
        ButterKnife.bind(this);
        initView();
        initData();



    }
    @Override
    public void initView() {
        initTitle(null,null, "结算", null, null);
        Intent intent = getIntent();
        releaseProject = intent.getExtras().getParcelable(Constants.RELEASE_WORKTYPE_KEY);
        if(releaseProject != null){
            title = releaseProject.getTitle();
            imageUrl = releaseProject.getImage_par();
            count = releaseProject.getCount();
            projectMoney = releaseProject.getProject_money();
        }
        getImage();


    }

    public void getImage() {
        if(imageUrl != null){
            DataAccessUtil.LoadImage(imageUrl, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }

    }


    @Override
    public void initData() {

    }

}
