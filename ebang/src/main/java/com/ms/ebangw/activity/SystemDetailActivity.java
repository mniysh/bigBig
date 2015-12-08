package com.ms.ebangw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.SystemMessage;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 系统消息明细
 *
 * @author wangkai
 */
public class SystemDetailActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_time)
    TextView tvTime;
    private SystemMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_detail);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            message = extras.getParcelable(Constants.KEY_SYS_MESSAGE_STR);
        }

        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "系统消息", null, null);
        if (null != message) {
            tvTitle.setText("\u3000\u3000" + message.getTitle());
            tvContent.setText("\u3000\u3000" + message.getContent());
            tvTime.setText(message.getCreated_at());
        }
    }

    @Override
    public void initData() {
        load();
    }

    private void load() {
        DataAccessUtil.sysMsgAlready(message.getId(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean b = DataParseUtil.processDataResult(response);
                    if (b) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.KEY_SYS_MESSAGE_ID, message.getId());
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }
}
