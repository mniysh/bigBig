//package com.ms.ebangw.fragment;
//
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//
//import com.loopj.android.http.JsonHttpResponseHandler;
//import com.ms.ebangw.R;
//import com.ms.ebangw.activity.HomeActivity;
//import com.ms.ebangw.adapter.ServiceCraftAdapter;
//import com.ms.ebangw.adapter.ServicePagerAdapter;
//import com.ms.ebangw.bean.Craft;
//import com.ms.ebangw.exception.ResponseException;
//import com.ms.ebangw.service.DataAccessUtil;
//import com.ms.ebangw.service.DataParseUtil;
//import com.ms.ebangw.utils.L;
//import com.ms.ebangw.utils.T;
//
//import org.apache.http.Header;
//import org.json.JSONObject;
//
//import java.lang.reflect.Field;
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
///**
// * 主页的服务页面的一级页面
// */
//public class ServiceFragment extends BaseFragment {
//
//    private ServiceBuildFragment serviceBuildFragment;
//    private ServiceDecorateFragment serviceDecorateFragment;
//    private ServiceProjectManageFragment serviceProjectManageFragment;
//    private ServiceOtherFragment serviceOtherFragment;
//    private FragmentManager fragmentManager;
//    private List<Fragment> datas;
//    private ViewPager vPager;
//    private RadioGroup radioGroup;
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    private ServicePagerAdapter adapter;
//
//    private Activity activity;
//    private View view;
//    @Bind(R.id.rg_but)
//    RadioGroup rBut;
//    @Bind(R.id.ls_list)
//    ListView listView;
//    @Bind(R.id.rb_build)
//    RadioButton rBuilding;
//    private Object allWorkType;
//    private Craft craft;
//    private ServiceCraftAdapter serviceCraftAdapter;
//
//
//
//    /**
//     * 实例化方法
//     * @param param1
//     * @param param2
//     * @return
//     */
//    public static ServiceFragment newInstance(String param1, String param2) {
//        ServiceFragment fragment = new ServiceFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public ServiceFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//        activity = (HomeActivity)mActivity;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        view = inflater.inflate(R.layout.fragment_service, container, false);
//        ButterKnife.bind(this, view);
//        // Inflate the layout for this fragment
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ButterKnife.bind(mActivity);
//        initView();
//        initData();
//
//    }
//
//
//    @Override
//    public void initView() {
//
//        initTitle(null, null, "服务", null, null);
//        getAllWorkType();
////        serviceBuildFragment=new ServiceBuildFragment();
////        serviceDecorateFragment=new ServiceDecorateFragment();
////        serviceOtherFragment=new ServiceOtherFragment();
////        serviceProjectManageFragment=new ServiceProjectManageFragment();
////        datas=new ArrayList<Fragment>();
//        rBut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (serviceCraftAdapter == null || craft == null) {
//                    return;
//                }
//                switch (checkedId) {
//                    case R.id.rb_build:
//                        serviceCraftAdapter.setWorkType(craft.getBuilding(), (HomeActivity) mActivity);
//                        break;
//                    case R.id.rb_decorate:
//                        serviceCraftAdapter.setWorkType(craft.getFitment(), (HomeActivity) mActivity);
//                        break;
//                    case R.id.rb_projectManager:
//                        serviceCraftAdapter.setWorkType(craft.getProjectManage(), (HomeActivity) mActivity);
//                        break;
//                    case R.id.rb_other:
//                        serviceCraftAdapter.setWorkType(craft.getOther(), (HomeActivity)mActivity);
//
//                        break;
//                }
//                serviceCraftAdapter.notifyDataSetChanged();
//            }
//        });
//            rBut.getChildAt(0).setClickable(true);
//
//
//    }
//
//
//
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    @Override
//    public void initData() {
//
//
//    }
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        try {
//            Field childFragmentManager = Fragment.class
//                    .getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Craft getAllWorkType() {
////        craft = MyApplication.getInstance().getCraft();
//        if(craft == null){
//
//            DataAccessUtil.publishCraft(new JsonHttpResponseHandler(){
//                @Override
//                public void onStart() {
//                    super.onStart();
//                    showProgressDialog();
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    super.onSuccess(statusCode, headers, response);
//                    try {
//
//                        craft = DataParseUtil.publishCraft(response);
//                        if(craft == null){
//
//                        }
//
////                        MyApplication.getInstance().setCraft(craft);
//                        serviceCraftAdapter = new ServiceCraftAdapter(craft.getBuilding(), (HomeActivity)mActivity);
//                        listView.setAdapter(serviceCraftAdapter);
//                        rBuilding.toggle();
//                        dismissLoadingDialog();
////                        Utility.setlistview(listView);
//
//                    } catch (ResponseException e) {
//                        e.printStackTrace();
//                        T.show(e.getMessage());
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    super.onFailure(statusCode, headers, responseString, throwable);
//                    L.d(responseString);
//                    dismissLoadingDialog();
//                }
//            });
//        }else{
//            serviceCraftAdapter = new ServiceCraftAdapter(craft.getBuilding(), (HomeActivity)mActivity);
//            listView.setAdapter(serviceCraftAdapter);
//            rBuilding.toggle();
////            Utility.setlistview(listView);
//        }
//
//
//        return craft;
//    }
//
//}
