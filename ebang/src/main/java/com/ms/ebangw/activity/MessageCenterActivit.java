package com.ms.ebangw.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.easemob.chat.EMConversation;
import com.easemob.easeui.ui.EaseConversationListFragment;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.CommonV4FragmentPagerAdapter;
import com.ms.ebangw.adapter.MessageAdapter;
import com.ms.ebangw.utils.ChartUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageCenterActivit extends BaseActivity {
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private MessageAdapter adapter;


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
    }

    @Override
    public void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                ChartUtil.chatBySingle(MessageCenterActivit.this, conversation.getUserName());
            }
        });

        fragments.add(conversationListFragment);



        CommonV4FragmentPagerAdapter adapter = new CommonV4FragmentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }



}
