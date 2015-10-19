package com.ms.ebangw.release;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.HomeActivity;
import com.ms.ebangw.bean.Craft;
import com.ms.ebangw.bean.Staff;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工种展示
 */
public class ReleaseWorkTypeFragment extends BaseFragment {
    private static final String WORK_TYPE = "work_type";

    private WorkType workType;
    private ViewGroup contentLayout;
    private ReleaseCraftAdapter craftAdapter;
//    @Bind(R.id.rg_but)
//    RadioGroup radioGroup;
    @Bind(R.id.listView)
    ListView listView;
//    @Bind(R.id.rb_build)
//    RadioButton rBuilding;
//    @Bind(R.id.bt_next)
//    Button bNext;
//    @Bind(R.id.tv_money)

//    TextView tMoney;
    private static String money;

    private Craft craft;

    public static ReleaseWorkTypeFragment newInstance(WorkType workType) {
        ReleaseWorkTypeFragment fragment = new ReleaseWorkTypeFragment();
        Bundle args = new Bundle();

        args.putParcelable(WORK_TYPE, workType);
        fragment.setArguments(args);
        return fragment;
    }

    public ReleaseWorkTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            workType = getArguments().getParcelable(WORK_TYPE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_work_type, container,
            false);
        ButterKnife.bind(this, contentLayout);

        initView();
        initData();
        return contentLayout;
    }


    @Override
    public void initView() {
        craft = getAllWorkType();

    }

    @Override
    public void initData() {
        if(workType != null){
            craftAdapter = new ReleaseCraftAdapter(((HomeActivity)mActivity).getFragmentManager(), workType);
            listView.setAdapter(craftAdapter);
        }


    }
    public Craft getAllWorkType(){
        craft = MyApplication.getInstance().getCraft();
        if(craft == null){
            DataAccessUtil.publishCraft(new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        craft = DataParseUtil.publishCraft(response);
                        MyApplication.getInstance().setCraft(craft);
                        craftAdapter = new ReleaseCraftAdapter(((HomeActivity)mActivity).getFragmentManager(), craft.getBuilding());
                        listView.setAdapter(craftAdapter);
//                        rBuilding.toggle();
                    } catch (ResponseException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    L.d(responseString);
                }
            });

        }else{
            craftAdapter = new ReleaseCraftAdapter(((HomeActivity)mActivity).getFragmentManager(), craft.getBuilding());
            listView.setAdapter(craftAdapter);
        }
        return craft;
    }
}
