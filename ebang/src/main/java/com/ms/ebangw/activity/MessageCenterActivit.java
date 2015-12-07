package com.ms.ebangw.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.easemob.chat.EMConversation;
import com.easemob.easeui.ui.EaseConversationListFragment;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.CommonV4FragmentPagerAdapter;
import com.ms.ebangw.adapter.MessageAdapter;
import com.ms.ebangw.bean.Message;
import com.ms.ebangw.utils.ChartUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageCenterActivit extends BaseActivity {
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private List<Message> datas;
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
        datas = new ArrayList<Message>();
        //暂时不添加数据
        final Message message = new Message();
//        message.setContent("临时内容");
//        message.setTime("时间");
//        message.setState("状态");
//        message.setTitle("标题");
//        datas.add(message);
//
//        adapter = new MessageAdapter(datas, this);
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
