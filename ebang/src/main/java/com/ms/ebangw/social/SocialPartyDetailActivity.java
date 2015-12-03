package com.ms.ebangw.social;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 社区：发布的活动详情
 *
 * @author wangkai
 */
public class SocialPartyDetailActivity extends BaseActivity {

    @Bind(R.id.tv_area)
    TextView tvArea;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_people_num)
    TextView tvPeopleNum;
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.tv_theme)
    TextView tvTheme;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_added_num)
    TextView tvAddedNum;

    private String partyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_party_detail);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            partyId = extras.getString(Constants.KEY_PART_ID);
        }

        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "预览", "报名", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply();
            }
        });
    }

    @Override
    public void initData() {
        load();
    }

    private void load() {
        DataAccessUtil.socialPartyDetail(partyId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Party party = DataParseUtil.socialPartyDetail(response);
                    if (null != party) {
                        setPartyDetail(party);
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }
        });
    }

    private void apply() {
        DataAccessUtil.socialPartyApply(partyId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean b = DataParseUtil.processDataResult(response);
                    if (b) {
                        T.show("报名成功");
                        finish();
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }
        });


    }

    private void setPartyDetail(Party party) {
        if (null == party) return;

        tvTitle.setText(party.getTitle());
        tvArea.setText(party.getProvince() + " " + party.getCity());
        tvAddress.setText(party.getArea_other());
        tvPeopleNum.setText(party.getNumber_people() + "人");
        tvStartTime.setText(party.getStart_time());
        tvEndTime.setText(party.getEnd_time());
        tvTheme.setText(party.getTheme());

        String flag = party.getFlag();
        if (!TextUtils.isEmpty(flag)) {
            switch (flag) {
                case "1":
                    tvAddedNum.setText(party.getApply_count());

                    break;

                  case "2":
                      tvAddedNum.setText("审核中");
                    break;

                  case "3":
                      tvAddedNum.setText("审核失败");
                    break;

                  case "4":
                      tvAddedNum.setText("已结束");
                    break;
            }
        }
    }
}
