package com.ms.ebangw.release;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.BaseActivity;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.event.OnCheckedWorkTypeEvent;
import com.ms.ebangw.event.WorkTypeEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class ReleaseActivity extends BaseActivity {
    private FragmentManager fm ;
    private String categroy;
    private List<WorkType> selectWorkType;
    private List<WorkType> data;
    private ReleaseWorkTypeFragment releaseWorkTypeFragment;

    @Override
    public void initView() {
        fm = getFragmentManager();
        if(selectWorkType == null){
            selectWorkType = new ArrayList<WorkType>();

        }
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        categroy = intent.getExtras().getString(Constants.RELEASE_WORKTYPE_KEY);
        fm.beginTransaction().replace(R.id.fl_release, SelectCraftFragment.newInstance(categroy, "")).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        if(savedInstanceState != null){

        }else{

            initView();
            initData();
        }

    }
    public void onEvent(WorkTypeEvent event) {
        WorkType workType = event.getWorkType();
        boolean isAdd = event.isAdd();
        if (workType != null && isAdd) {
            selectWorkType.add(workType);
        } else {
            selectWorkType.remove(workType);
        }
    }
    public List<WorkType> getSelectWorkType() {
        return selectWorkType;
    }
    public void goDeveloperRelease(String staff, String cate) {

        IncreaseDetailFragment increaseDetailFragment = IncreaseDetailFragment.newInstance(staff, cate);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_release, increaseDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(OnCheckedWorkTypeEvent event) {
        if (event == null) {
            return;
        }
        WorkType workType = event.getWorkType();
        boolean b = event.isSelected();
        if (event != null && b) {

            data.add(workType);
            releaseWorkTypeFragment = ReleaseWorkTypeFragment.newInstance(workType);
        } else if (event != null && !b) {
            data.remove(workType);
        }
    }

}
