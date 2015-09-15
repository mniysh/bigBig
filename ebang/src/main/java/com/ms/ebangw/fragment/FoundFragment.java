package com.ms.ebangw.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ms.ebangw.R;
import com.ms.ebangw.adapter.FoundFragmentAdapter;
import com.ms.ebangw.bean.FoundBean;
import com.ms.ebangw.view.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class FoundFragment extends BaseFragment implements XListView.IXListViewListener {
    private Spinner spi_type, spi_KM, spi_area;
    private ArrayAdapter<String> adapter01, adapter02, adapter03;
    private String[] data_type, data_KM, data_area;
    private XListView xlistview;
    private FoundFragmentAdapter adapter;
    private List<FoundBean> datas;
    private View mContentView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_found, null);
        ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("xxx", "fragment能不能进来");
        initDatas();
        initView();
        initViewOper();
    }


    private void initDatas() {
        datas = new ArrayList<FoundBean>();
        //数据要从服务器后台获取现在没有，暂时用临时数据代替
        FoundBean fb = new FoundBean("测试的url", "临时的title", "临时的距离", "临时的内容", "临时的钱数", "临时的抢单人数");
        datas.add(fb);
    }

    private void initViewOper() {
        //添加spinner的数据
        adapter01 = new ArrayAdapter<String>(mActivity, R.layout.sim_spinner_item);
        adapter02 = new ArrayAdapter<String>(mActivity, R.layout.sim_spinner_item);
        adapter03 = new ArrayAdapter<String>(mActivity, R.layout.sim_spinner_item);

        data_type = getResources().getStringArray(R.array.home_foundfragment_all_type);
        data_KM = getResources().getStringArray(R.array.home_foundfragment_juli);
        data_area = getResources().getStringArray(R.array.home_foundfragment_area);

        for (int i = 0; i < data_type.length; i++) {
            adapter01.add(data_type[i]);
        }
        for (int i = 0; i < data_KM.length; i++) {
            adapter02.add(data_KM[i]);
        }
        for (int i = 0; i < data_area.length; i++) {
            adapter03.add(data_area[i]);
        }
        Log.i("aaa", data_KM[1]);
        Log.i("aaa", data_area[1]);
        spi_type.setAdapter(adapter01);
        spi_KM.setAdapter(adapter02);
        spi_area.setAdapter(adapter03);
        xlistview.setPullLoadEnable(true);
        xlistview.setPullRefreshEnable(true);
        xlistview.setXListViewListener(this);

    }

    public void initView() {
        spi_type = (Spinner) mContentView.findViewById(R.id.frag_fount_spinner_type);
        spi_KM = (Spinner) mContentView.findViewById(R.id.frag_fount_spinner_KM);
        spi_area = (Spinner) mContentView.findViewById(R.id.frag_fount_spinner_area);
        xlistview = (XListView) getView().findViewById(R.id.act_frag_found_xlistview);
        adapter = new FoundFragmentAdapter(mActivity, datas);
        xlistview.setAdapter(adapter);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefresh() {

        //关闭xlistview的下拉刷新，现在还没有数据先设置停止。
        xlistview.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        //关闭xlistview的上拉加载更多
        xlistview.stopLoadMore();
    }

}

