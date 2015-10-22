package com.ms.ebangw.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ms.ebangw.R;
import com.ms.ebangw.adapter.MessageAdapter;
import com.ms.ebangw.bean.Message;
import com.ms.ebangw.view.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageCenterActivit extends BaseActivity {
    private List<Message> datas;
    private MessageAdapter adapter;
    @Override
    public void initView() {
        initTitle(null, "返回", "消息中心", null, null);
    }

    @Override
    public void initData() {
        datas=new ArrayList<Message>();
        //暂时不添加数据
        Message message=new Message();
        message.setContent("临时内容");
        message.setTime("时间");
        message.setState("状态");
        message.setTitle("标题");
        datas.add(message);

        adapter=new MessageAdapter(datas,this);
        xListView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    @Bind(R.id.xl_xlistview)
    XListView xListView;


}
