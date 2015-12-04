package com.ms.ebangw.social;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.Party;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.setting.SocialPartyUtil;
import com.ms.ebangw.utils.JsonUtil;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SocialPartyPreviewActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_area)
    TextView tvArea;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_people_num)
    TextView tvPeopleNum;
    @Bind(R.id.tv_added_num)
    TextView tvAddedNum;
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.tv_theme)
    TextView tvTheme;
    @Bind(R.id.tv_commit)
    TextView tvCommit;


    private Party party;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_party_preview);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            party = extras.getParcelable(Constants.KEY_PARTY_STR);
        }

        initView();
        initData();

    }

    @Override
    public void initView() {
        initTitle(null, "返回", "预览", null, null);
        if (null != party) {
            tvTitle.setText(party.getTitle());
            tvArea.setText(party.getProvince() + " " + party.getCity());
            tvAddress.setText(party.getArea_other());
            tvPeopleNum.setText(party.getNumber_people() + "人");
            tvStartTime.setText(party.getStart_time());
            tvEndTime.setText(party.getEnd_time());
            tvTheme.setText(party.getTheme());

        }

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishParty(party);
            }
        });
    }

    @Override
    public void initData() {

    }

    /**
     * 发布
     */
    public void publishParty(Party party) {

        if (SocialPartyUtil.isRight(party)) {
            String title = party.getTitle();
            String address = party.getArea_other();
            String num = party.getNumber_people();

            String startTime = party.getStart_time();
            String endTime = party.getEnd_time();
            String theme = party.getTheme();
            String imagesJson = JsonUtil.createGsonString(party.getActive_image());
            String price = party.getPrice();

            DataAccessUtil.socialPublish(title, party.getProvinceId(), party.getCityId(), address, num,
                startTime, endTime, price, theme, imagesJson, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            boolean b = DataParseUtil.processDataResult(response);
                            if (b) {
                                finish();
                            }
                        } catch (ResponseException e) {
                            e.printStackTrace();
                            T.show(e.getMessage());
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        showProgressDialog();
                    }
                });
        }
    }
}
