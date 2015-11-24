package com.ms.ebangw.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ProjectStatusPagerAdapter;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.ProjectStatusFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已发布工程的不同状态的 工程 ：待审核，待通过  进行中  已结束
 *
 * @author wangkai
 */
public class ProjectStatusActivity extends BaseActivity {
    /**
     * 抢单（工长、劳务公司、工人)
     */
    public static final String TYPE_GRAB = "grab";
    /**
     * 发布（开发商、个人、工长）
     */
    public static final String TYPE_PUBLISH = "publish";
    /**
     * 邀请我的（只有工人有)
     */
    public static final String TYPE_INVITE = "invite";
    /**
     *
     * 根据type选择填写，如果type=invite,此字段必须填，如果type=grab,此字段传空字符串
     */
    /**
     * 已接受邀请
     */
    public static final String INVITE_TYPE_AGREE = "agree";
    /**
     * 待接受邀请
     */
    public static final String INVITE_TYPE_INVITE = "invite";

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.btn_waiting)
    RadioButton btnWaiting;
    @Bind(R.id.rb_execute)
    RadioButton rbExecute;
    @Bind(R.id.btn_complete)
    RadioButton btnComplete;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.btn_waiting_audit)
    RadioButton btnWaitingAudit;
    @Bind(R.id.tl_tab)
    TabLayout tlTab;

    private String currentType;
    private String currentInviteType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_status);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            currentType = extras.getString(Constants.KEY_PROJECT_TYPE, TYPE_GRAB);
            currentInviteType = extras.getString(Constants.KEY_PROJECT_TYPE_INVITE);
        }
        initView();
        initData();
    }

    @StringDef({TYPE_GRAB, TYPE_PUBLISH, TYPE_INVITE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectedType {
    }

    @Override
    public void initView() {
        if (isPublished()) {
            initTitle(null, "返回", "已发布", null, null);
        }else {  //抢单,没有待审核
            initTitle(null, "返回", "抢单", null, null);
            radioGroup.removeViewAt(0);
            btnWaiting.setBackgroundResource(R.drawable.segment_left_round_selector);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
    }

    /**
     * 判断是已发布 还是 抢单
     * @return
     */
    public boolean isPublished() {
        if (TextUtils.equals(currentType, TYPE_PUBLISH)) {  //已发布
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void initData() {
        List<Fragment> list = new ArrayList<>();
        ProjectStatusFragment auditFragment;
        if (isPublished()) {  //已发布， 有待审核状态
            auditFragment = ProjectStatusFragment.newInstance(ProjectStatusFragment.AUDIT, currentType, currentInviteType);
            list.add(auditFragment);
        }

        ProjectStatusFragment waitingFragment = ProjectStatusFragment.newInstance( ProjectStatusFragment.WAITING, currentType,currentInviteType);
        ProjectStatusFragment executeFragment = ProjectStatusFragment.newInstance(ProjectStatusFragment.EXECUTE, currentType, currentInviteType);
        ProjectStatusFragment completeFragment = ProjectStatusFragment.newInstance(ProjectStatusFragment.COMPLETE, currentType,  currentInviteType);

        list.add(waitingFragment);
        list.add(executeFragment);
        list.add(completeFragment);

        ProjectStatusPagerAdapter adapter = new ProjectStatusPagerAdapter(getFragmentManager(), list);
        viewPager.setAdapter(adapter);
        tlTab.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.toggle();
            }
        });
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.toggle();
    }

}
