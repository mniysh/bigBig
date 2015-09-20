package com.ms.ebangw.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.ms.ebangw.R;
import com.ms.ebangw.adapter.FoundFragmentAdapter;
import com.ms.ebangw.adapter.GridAdapter;
import com.ms.ebangw.bean.FoundBean;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主页跳转的2级页面
 */
public class NextPageActivity extends BaseActivity {
    private GridAdapter adapter;
    private FoundFragmentAdapter fFAdapter;
    private List<String> datas;
    private List<FoundBean> datas_lv;
    @Bind(R.id.gr_gridview)
    GridView gItem;
    @Bind(R.id.lv_listview)
    ListView lItem;



    @Override
    public void initView() {
        datas=new ArrayList<String>();
        datas_lv=new ArrayList<FoundBean>();



    }

    @Override
    public void initData() {
        for (int i = 0; i <15 ; i++) {
            datas.add("数据"+i);
        }
        adapter=new GridAdapter(datas,this);
        FoundBean fb = new FoundBean("测试的url", "临时的title", "临时的距离", "临时的内容", "临时的钱数", "临时的抢单人数");
        datas_lv.add(fb);
        fFAdapter=new FoundFragmentAdapter(this,datas_lv);
        gItem.setAdapter(adapter);
        lItem.setAdapter(fFAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page);
        ButterKnife.bind(this);
        initView();
        initData();

    }


}
