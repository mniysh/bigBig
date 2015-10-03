package com.ms.ebangw.userAuthen.worker;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.adapter.CraftAdapter;
import com.ms.ebangw.adapter.CraftGridViewAdapter;
import com.ms.ebangw.bean.Craft;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.ms.ebangw.view.MyGridView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工人认证----->工种选择
 *
 * @author wangkai
 */
public class WorkTypeActivity extends BaseActivity {

    @Bind(R.id.rg_select_type)
    RadioGroup selectTypeRg;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.btn_ok)
    Button okBtn;
    @Bind(R.id.rb_build)
    RadioButton buildingRb;
    private CraftAdapter craftAdapter;
    private Craft craft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_type);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    @Override
    public void initView() {
        selectTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (craftAdapter == null || craft == null) {
                    return;
                }
                switch (checkedId) {
                    case R.id.rb_build:
                        craftAdapter.setWorkType(craft.getBuilding());

                        break;
                    case R.id.rb_fitment:
                        craftAdapter.setWorkType(craft.getFitment());
                        break;
                    case R.id.rb_projectManager:
                        craftAdapter.setWorkType(craft.getProjectManage());
                        break;
                }
                craftAdapter.notifyDataSetChanged();
            }
        });
//        listView  = (MyListView) findViewById(R.id.listView);
    }

    @Override
    public void initData() {
        loadWorkType();
    }

    public void loadWorkType() {

        DataAccessUtil.publishCraft(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    craft = DataParseUtil.publishCraft(response);
//                    setWorkTypeAdapter(craft.getProjectManage());
                    craftAdapter = new CraftAdapter(craft.getBuilding());
                    listView.setAdapter(craftAdapter);
                    buildingRb.toggle();
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                L.d(responseString);
            }
        });
    }

    public ArrayList<WorkType> getSelectedWorkTypes() {
        ArrayList<WorkType> selectTypes = new ArrayList<>();
        int childCount = listView.getChildCount();
        ViewGroup listItemLayout;
        for (int i = 0; i < childCount; i++) {
            listItemLayout = (ViewGroup) listView.getChildAt(i);
            MyGridView myGridView = (MyGridView) listItemLayout.findViewById(R.id.gridView);
            CraftGridViewAdapter craftGridViewAdapter = (CraftGridViewAdapter) myGridView.getAdapter();
            List<WorkType> selectedGridTypes = craftGridViewAdapter.getSelectedWorkTypes();
            if (selectedGridTypes != null && selectedGridTypes.size() > 0) {
                selectTypes.addAll(selectedGridTypes);
            }
        }
       return selectTypes;
    }

    @OnClick(R.id.btn_ok)
    public void commitSelectedTypes() {
        if(getSelectedWorkTypes()!=null){
            if(getSelectedWorkTypes().size() > 5 || getSelectedWorkTypes().size() < 1){
                T.show("最多选择5项,最少选择1项");
            }else{
                Bundle bundle = new Bundle();

                bundle.putParcelableArrayList(Constants.KEY_SELECTED_WORKTYPES,getSelectedWorkTypes());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(22, intent);
                finish();
            }
        }

    }
}

