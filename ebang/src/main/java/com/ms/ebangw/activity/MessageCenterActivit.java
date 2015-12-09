package com.ms.ebangw.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.easemob.chat.EMConversation;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.ui.EaseConversationListFragment;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.CommonV4FragmentPagerAdapter;
import com.ms.ebangw.fragment.SystemMsgFragment;
import com.ms.ebangw.utils.ChartUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageCenterActivit extends BaseActivity {
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.btn_user_msg)
    RadioButton btnUserMsg;
    @Bind(R.id.btn_sys_msg)
    RadioButton btnSysMsg;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "消息中心", null, null);
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

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.toggle();
            }
        });
    }

    @Override
    public void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {

                String userName = conversation.getUserName();
                ChartUtil.chatTo(MessageCenterActivit.this, userName, EaseConstant.CHATTYPE_SINGLE);
            }
        });

        fragments.add(conversationListFragment);

        SystemMsgFragment systemMsgFragment = new SystemMsgFragment();
        fragments.add(systemMsgFragment);

        CommonV4FragmentPagerAdapter adapter = new CommonV4FragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        btnUserMsg.toggle();
    }


}
