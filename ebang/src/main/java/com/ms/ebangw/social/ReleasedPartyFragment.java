package com.ms.ebangw.social;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.view.ProvinceAndCityView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 社区首页
 *
 * @author wangkai
 */
public class ReleasedPartyFragment extends BaseFragment {
    private List<Province> provinces;
    private int currentPage = 1;
    private ReleasedPartyFragment adapter;
    @Bind(R.id.pac)
    ProvinceAndCityView pac;
    @Bind(R.id.ptr)
    PullToRefreshListView ptr;
    private ViewGroup contentLayout;

    public ReleasedPartyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_released_party, container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "积分", null, null);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ptr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                load();
            }


            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });
    }

    @Override
    public void initData() {
        provinces = getProvinces();
        pac.setProvinces(provinces);

//        adapter = new ReleasedPartyAdapter(this, new ArrayList<JiFen>());
//        ptr.setAdapter(adapter);
        load();

    }

    private void load() {
        currentPage = 1;
//        DataAccessUtil.score(currentPage + "", new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                currentPage++;
//                try {
//                    List<JiFen> list = DataParseUtil.score(response);
//                    if (adapter != null && list != null && list.size() > 0) {
//                        adapter.setList(list);
//                        adapter.notifyDataSetChanged();
//                    }
//                } catch (ResponseException e) {
//                    e.printStackTrace();
//                    T.show(e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                if (null != ptr) {
//                    ptr.onRefreshComplete();
//                }
//            }
//        });
    }

    private void loadMore() {

//        DataAccessUtil.score(currentPage + "", new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                currentPage++;
//                try {
//                    List<JiFen> list = DataParseUtil.score(response);
//                    if (adapter != null && list != null && list.size() > 0) {
//                        adapter.getList().addAll(list);
//                        adapter.notifyDataSetChanged();
//                    }
//                } catch (ResponseException e) {
//                    e.printStackTrace();
//                    T.show(e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                ptr.onRefreshComplete();
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
