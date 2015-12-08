package com.ms.ebangw.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.SystemDetailActivity;
import com.ms.ebangw.adapter.SystemMessageAdapter;
import com.ms.ebangw.bean.SystemMessage;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 系统消息
 *
 * @author wangkai.
 */
public class SystemMsgFragment extends Fragment {
    private static final int ALREADY_READ = 111;

    @Bind(R.id.ptr)
    PullToRefreshListView ptr;

    private int currentPage = 1;
    private ArrayList<SystemMessage> messages;
    private SystemMessageAdapter adapter;

    public SystemMsgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_system_msg, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    public void initData() {
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

        messages = new ArrayList<>();
        adapter = new SystemMessageAdapter(messages);
        ptr.setAdapter(adapter);

        ptr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SystemMessage message = (SystemMessage) view.getTag(Constants.KEY_SYS_MESSAGE);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_SYS_MESSAGE_STR, message);

                Intent intent = new Intent(getActivity(), SystemDetailActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, ALREADY_READ);
            }
        });

        load();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == ALREADY_READ) {
            Bundle extras = data.getExtras();
            if (null != extras) {
                String msgId =  extras.getString(Constants.KEY_SYS_MESSAGE_ID, "-100");
                updateMessageStatus(msgId);
            }

        }
    }

    private void updateMessageStatus(String messageId) {
        int size = adapter.getList().size();
        SystemMessage msg;
        for (int i = 0; i < size; i++) {
            msg = adapter.getList().get(i);
            if (TextUtils.equals(msg.getId(), messageId)) {
                msg.setIs_read("1");
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private void load() {
        currentPage = 1;
        DataAccessUtil.systemMessage(currentPage + "", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<SystemMessage> list = DataParseUtil.systemMessage(response);
                    if (adapter != null && list != null) {
                        adapter.setList(list);
                    } else {
                        adapter.getList().clear();
                    }
                    adapter.notifyDataSetChanged();

                } catch (ResponseException e) {
                    e.printStackTrace();
                    L.d(e.getMessage());
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
        DataAccessUtil.systemMessage(currentPage + "", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    List<SystemMessage> list = DataParseUtil.systemMessage(response);
                    if (adapter != null && list != null && list.size() > 0) {
                        adapter.getList().addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    L.d(e.getMessage());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
