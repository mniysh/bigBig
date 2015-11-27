package com.ms.ebangw.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.T;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * 评论
 *
 * @author wangkai
 */
public class EvaluateActivity extends BaseActivity {

    @Bind(R.id.head)
    ImageView head;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.rb_good)
    RadioButton rbGood;
    @Bind(R.id.rb_middle)
    RadioButton rbMiddle;
    @Bind(R.id.rb_bad)
    RadioButton rbBad;
    @Bind(R.id.cb_is_anonymity)
    CheckBox cbIsAnonymity;
    @Bind(R.id.btn_publish)
    Button btnPublish;
    @Bind(R.id.tv_grab_description)
    TextView tvGrabNum;
    @Bind(R.id.tv_show)
    TextView tvEvaluate;
    @Bind(R.id.et_content)
    TextView etContent;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;

    private ReleaseProject project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            project = extras.getParcelable(Constants.KEY_RELEASED_PROJECT_STR);
        }
        initView();
        initData();
    }

    @Override
    public void initView() {
        rbGood.setChecked(true);
    }

    @Override
    public void initData() {
        setProjectInfo();

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }

    private void commit() {
        String id = project.getId();

        String content = etContent.getText().toString().trim();
        String evaluateType = getEvaluateType();
        if (TextUtils.equals(evaluateType, "bad")) {   //差评不能少于五字
            if (TextUtils.isEmpty(content) && content.length() < 5) {
                T.show("差评不能少于五字");
                return;
            }
        }

        String isAnonymity = "0";
        if (cbIsAnonymity.isChecked()) {
            isAnonymity = "1";
        }

        DataAccessUtil.evaluate(id, content, isAnonymity, evaluateType, new JsonHttpResponseHandler() {
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
        });
    }

    private String getEvaluateType() {
        String type;
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        switch (radioButtonId) {
            case R.id.rb_good:
                type = "good";
                break;

            case R.id.rb_middle:
                type = "middle";
                break;

            case R.id.rb_bad:
                type = "bad";
                break;
            default:
                type = "good";

        }

        return type;
    }



    public void setProjectInfo() {
        String imageUrl = project.getImages();
        String title = project.getTitle();
        String description = project.getDescription();
        String project_money = project.getProject_money();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(this).load(DataAccessUtil.getImageUrl(imageUrl))
                .placeholder(R.drawable.head).into(head);
        } else {
            head.setImageResource(R.drawable.head);
        }

        tvEvaluate.setVisibility(View.GONE);
        tvGrabNum.setVisibility(View.GONE);

        tvTitle.setText(title);
        tvDescription.setText(description);
        tvMoney.setText("总工资:" + project_money + " 元");

    }
}
