package com.ms.ebangw.release;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PayingActivity extends BaseActivity {
    private ReleaseProject releaseProject;
    private String title, imageUrl, content, projectMoney;
//    private TextView titleTv, countTv;
    @Bind(R.id.tv_title)
    TextView titleTv;
    @Bind(R.id.tv_content)
    TextView contentTv;
    @Bind(R.id.iv_pic)
    ImageView imageView;
    @Bind(R.id.tv_paying_money)
    TextView moneyTv;
    @Bind(R.id.but_phone)
    Button phoneBt;
    @Bind(R.id.bt_goCenter)
    Button goCenterBt;


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
        initTitle(null, null, "结算", null, null);
        Intent intent = getIntent();
        releaseProject = intent.getExtras().getParcelable(Constants.KEY_RELEASE_PROJECT);
        if(releaseProject != null){
            title = releaseProject.getTitle();
            imageUrl = releaseProject.getImage_par();
            content = releaseProject.getCount();
            projectMoney = releaseProject.getProject_money();
        }
        setReleaseInfo();
        phoneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse("tel:400 616 0066"));
                startActivity(intent);
            }
        });
        goCenterBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().setFlag_home(true);
                PayingActivity.this.finish();

            }
        });
    }


    private void setReleaseInfo() {
        titleTv.setText(title);
        contentTv.setText(content);
        moneyTv.setText(projectMoney+ "元");
        getImage();
    }



    public void getImage() {
        if(imageUrl != null){
            DataAccessUtil.LoadImage(imageUrl, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    showProgressDialog();
                }

                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {

                    if(i == 200){
                        BitmapFactory bitmapFactory = new BitmapFactory();
                        Bitmap bitmap = bitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
                        imageView.setImageBitmap(bitmap);
                        dismissLoadingDialog();
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    dismissLoadingDialog();

                }
            });
        }

    }


    @Override
    public void initData() {

    }

}
