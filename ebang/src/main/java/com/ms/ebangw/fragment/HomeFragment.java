package com.ms.ebangw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.MyApplication;
import com.ms.ebangw.R;
import com.ms.ebangw.activity.MessageCenterActivit;
import com.ms.ebangw.activity.NextPageActivity;
import com.ms.ebangw.activity.ShowActivity;
import com.ms.ebangw.adapter.BannerImageHoderView;
import com.ms.ebangw.adapter.ProjectItemAdapter;
import com.ms.ebangw.bean.BannerImage;
import com.ms.ebangw.bean.HomeProjectInfo;
import com.ms.ebangw.bean.RecommendedDeveoper;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.MyListView;
import com.ms.ebangw.utils.T;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 主页的主页页面
 */
public class HomeFragment extends BaseFragment implements OnClickListener {
    private int[] images = {R.drawable.banner_aaa, R.drawable.banner_bb
    };// 滑动图片数据
    private int[] imgclass = {R.drawable.home_build, R.drawable.home_zxiu, R.drawable.home_life, R.drawable.home_business};
    private String[] txtclass = {"建筑", "装修", "生活", "商业"};
//    private List<FoundBean> datas;
    private View mContentLayout;
    public LocationClient mLocationClient = null;
    @Bind(R.id.lv_projects)
    MyListView listView;
    @Bind(R.id.home_search)
    EditText etSearch;
    @Bind(R.id.iv_building)
    ImageView buildingIv;
    @Bind(R.id.iv_decorater)
    ImageView decoratorIv;
    @Bind(R.id.iv_projectManage)
    ImageView projectManageIv;
    @Bind(R.id.iv_other)
    ImageView otherIv;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;//顶部广告栏控件
    @Bind(R.id.ptr)
    PullToRefreshScrollView ptr;
    private ProjectItemAdapter projectItemAdapter;

    /**
     * 消息按钮
     */
    @OnClick(R.id.tv_message)
    public void toastMessage() {
        Intent intent = new Intent(mActivity, MessageCenterActivit.class);

        startActivity(intent);
    }

    @OnClick(R.id.home_search)
    public void startSearch() {
        T.show("开始搜索");
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentLayout = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mContentLayout);
        initView();
        initData();
        return mContentLayout;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadHomeProjectInfo();
    }

    public void initView() {

        buildingIv.setOnClickListener(this);
        decoratorIv.setOnClickListener(this);
        projectManageIv.setOnClickListener(this);
        otherIv.setOnClickListener(this);
        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ptr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadHomeProjectInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadMoreHomeProjectInfo();
            }
        });
    }

    @Override
    public void initData() {
        this.mLocationClient = MyApplication.getInstance().mLocationClient;
        projectItemAdapter = new ProjectItemAdapter(new ArrayList<ReleaseProject>());
        projectItemAdapter.setOnGrabClickListener(new ProjectItemAdapter.OnGrabClickListener() {
            @Override
            public void onGrabClick(View view, ReleaseProject releaseProject) {
                Intent intent = new Intent(getActivity(), ShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.KEY_RELEASED_PROJECT_STR, releaseProject);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReleaseProject project = (ReleaseProject) view.getTag(Constants.KEY_RELEASED_PROJECT);

            }
        });

        listView.setAdapter(projectItemAdapter);
        initBanner();
    }

    public void initBanner() {
        convenientBanner.setPages(new CBViewHolderCreator<BannerImageHoderView>() {
                @Override
                public BannerImageHoderView createHolder() {
                    return new BannerImageHoderView();
                }
            }, getImages())
            //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
            .setPageIndicator(new int[]{R.drawable.point_normal, R.drawable.point_able})
                //设置翻页的效果，不需要翻页效果可用不设
            .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
    }

    private List<BannerImage> getImages() {
        List<BannerImage> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            BannerImage bannerImage = new BannerImage();
            bannerImage.setImgResId(getResId("ic_test_" + i, R.drawable.class));
            list.add(bannerImage);
        }
        return list;
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

        // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterknife.ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mActivity, NextPageActivity.class);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
//            case R.id.fragment_home_lin_recommend01:
//            case R.id.fragment_home_lin_recommend02:
//            case R.id.fragment_home_lin_recommend03:
//                startActivity(new Intent(mActivity, RecommendActivity.class));
//                break;
//            case R.id.iv_building:
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.iv_decorater:
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.iv_projectManage:
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.iv_other:
//                intent.putExtras(bundle);
//                startActivity(intent);
//
//                break;
//            default:
//                break;
        }
    }

    private int currentPage = 0;
    public void loadHomeProjectInfo() {
        currentPage = 1;
        double latitude;
        double longitude;
        try {
            latitude = mLocationClient.getLastKnownLocation().getLatitude();
            longitude = mLocationClient.getLastKnownLocation().getLongitude();
        } catch (Exception e) {
            latitude = 0;
            longitude = 0;
        }
        DataAccessUtil.homeProjectInfo(currentPage + "", latitude+"", longitude + "", new
            JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissLoadingDialog();
                ptr.onRefreshComplete();
            }

                @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                currentPage ++ ;
                try {
                    HomeProjectInfo info = DataParseUtil.homeProjectInfo(response);
                    if (null != info) {
                        List<RecommendedDeveoper> deveoperList = info.getDevelopers();
                        List<ReleaseProject> projectList = info.getProject();
                        setRecommendedDeveploersInfo(deveoperList);
                        setRecommendedDevelopersProjects(projectList);
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }
        });
    }

    /**
     * 加载更多
     */
    private void loadMoreHomeProjectInfo() {
        double latitude;
        double longitude;
        try {
            latitude = mLocationClient.getLastKnownLocation().getLatitude();
            longitude = mLocationClient.getLastKnownLocation().getLongitude();
        } catch (Exception e) {
            latitude = 0;
            longitude = 0;
        }
        DataAccessUtil.homeProjectInfo(currentPage + "", latitude+"", longitude + "", new
            JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dismissLoadingDialog();
                ptr.onRefreshComplete();
            }

                @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    currentPage ++ ;
                    try {
                    HomeProjectInfo info = DataParseUtil.homeProjectInfo(response);
                    if (null != info) {
                        List<RecommendedDeveoper> deveoperList = info.getDevelopers();
                        List<ReleaseProject> projectList = info.getProject();
//                        setRecommendedDeveploersInfo(deveoperList);
                        projectItemAdapter.getList().addAll(projectList);
                        projectItemAdapter.notifyDataSetChanged();
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }
        });
    }

    private void setRecommendedDevelopersProjects(List<ReleaseProject> projectList) {
        if (projectItemAdapter != null && projectList != null && projectList.size() > 0) {
            projectItemAdapter.setList(projectList);
            projectItemAdapter.notifyDataSetChanged();
        }
    }

    private void setRecommendedDeveploersInfo(List<RecommendedDeveoper> deveoperList) {
        if (null != deveoperList && deveoperList.size() > 0 && null != mActivity) {
            LinearLayout companysLayout = (LinearLayout) mContentLayout.findViewById(R.id.ll_companys);
            int childCount = companysLayout.getChildCount();
            RecommendedDeveoper recommendedDeveoper;
            for (int i = 0; i < childCount; i++) {
                recommendedDeveoper = deveoperList.get(i);
                View childView = companysLayout.getChildAt(i);
                TextView companyNameTv = (TextView) childView.findViewById(R.id.tv_company_name);
                ImageView comanyLogoIv = (ImageView) childView.findViewById(R.id.iv_company_logo);
                companyNameTv.setText(recommendedDeveoper.getCompany_name());
                String logo = recommendedDeveoper.getLogo();
                if (!TextUtils.isEmpty(logo)) {
                    Picasso.with(mActivity).load(logo).placeholder(R
                        .drawable.yuan).into(comanyLogoIv);
                }
            }

//            RecommendedDeveloperAdapter developerAdapter = new RecommendedDeveloperAdapter(mActivity,deveoperList);
//            rvDevelopers.setAdapter(developerAdapter);

        }
    }



}
