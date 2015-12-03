package com.ms.ebangw.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ReleasedPartyAdapter;
import com.ms.ebangw.bean.Party;
import com.ms.ebangw.bean.Province;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.social.MySocialListActivity;
import com.ms.ebangw.social.SocialPartyPublishActivity;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.ProvinceAndCityView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 社区
 *
 * @author wangkai
 */
public class CommunityFragment extends BaseFragment {
    @Bind(R.id.tv_release_party)
    TextView tvReleaseParty;
    private List<Province> provinces;
    private int currentPage = 1;
    private ReleasedPartyAdapter adapter;
    private String currentProvinceId, currentCityId;

    @Bind(R.id.pac)
    ProvinceAndCityView pac;
    @Bind(R.id.ptr)
    PullToRefreshListView ptr;
    private ViewGroup contentLayout;

    public CommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle(null, null, "社区", "我的列表", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, MySocialListActivity.class));
            }
        });
    }

    @Override
    public void initView() {

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
        pac.setOnAreaChangedListener(new ProvinceAndCityView.OnAreaChangedListener() {
            @Override
            public void onAreaChanged(String provinceId, String cityId) {
                currentProvinceId = provinceId;
                currentCityId = cityId;
                load();
            }
        });

        tvReleaseParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SocialPartyPublishActivity.class);
                startActivity(intent);
            }
        });

        adapter = new ReleasedPartyAdapter(mActivity, new ArrayList<Party>());

//        ListView listView = ptr.getRefreshableView();
//        ptr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Party party = (com.ms.ebangw.bean.Party) view.getTag(Constants.KEY_PARTY);
//                String partyId = party.getId();
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.KEY_PART_ID, partyId);
//                Intent intent = new Intent(mActivity, SocialPartyDetailActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//
//            }
//        });

        ptr.setAdapter(adapter);

        load();

    }

    private void load() {
        currentPage = 1;
        DataAccessUtil.socialShow(currentPage + "", currentProvinceId, currentCityId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<Party> list = DataParseUtil.socialShow(response);
                    if (adapter != null && list != null && list.size() > 0) {
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (null != ptr) {
                    ptr.onRefreshComplete();
                }
            }
        });
    }

    private void loadMore() {

        DataAccessUtil.socialShow(currentPage + "", currentProvinceId, currentCityId, new
            JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<Party> list = DataParseUtil.socialShow(response);
                    if (adapter != null && list != null && list.size() > 0) {
                        adapter.getList().addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                ptr.onRefreshComplete();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
