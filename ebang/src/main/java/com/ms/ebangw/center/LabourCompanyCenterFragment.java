package com.ms.ebangw.center;


import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.AccountActivity;
import com.ms.ebangw.activity.EvaluateListActivity;
import com.ms.ebangw.activity.InviteFriendsActivity;
import com.ms.ebangw.activity.JiFenActivity;
import com.ms.ebangw.activity.PeopleManageActivity;
import com.ms.ebangw.activity.ProjectStatusActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.fragment.BaseFragment;
import com.ms.ebangw.fragment.HeadInfoFragment;
import com.ms.ebangw.release.ReleaseActivity;
import com.ms.ebangw.utils.DensityUtils;
import com.ms.ebangw.utils.QRCodeUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 劳务公司中心
 *
 * @author wangkai
 */
public class LabourCompanyCenterFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.tv_invite_code)
    TextView tvInviteCode;
    @Bind(R.id.iv_eweima)
    ImageView ivEweima;
    @Bind(R.id.ll_eweima)
    LinearLayout eweimaLayout;
    @Bind(R.id.fl_head_info)
    FrameLayout flHeadInfo;
    @Bind(R.id.tv_published)
    TextView tvGrab;
    @Bind(R.id.tv_trade)
    TextView tvTrade;
    @Bind(R.id.tv_show)
    TextView tvEvaluate;
    @Bind(R.id.tv_people_manage)
    TextView tvPeopleManage;
    @Bind(R.id.tv_invite_friend)
    TextView tvInviteFriend;
    @Bind(R.id.ll_verItems)
    LinearLayout llVerItems;
    @Bind(R.id.tv_jifen)
    TextView tvJifen;
    @Bind(R.id.tv_publish)
    TextView tvPublish;


    private String mParam1;
    private String mParam2;
    private ViewGroup contentLayout;
    private FragmentManager fm;
    private User user;

    public static LabourCompanyCenterFragment newInstance(String param1, String param2) {
        LabourCompanyCenterFragment fragment = new LabourCompanyCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LabourCompanyCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_labour_company_center, null);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }

    @Override
    public void initView() {
        fm.beginTransaction().replace(R.id.fl_head_info, HeadInfoFragment.newInstance("", ""))
            .commit();


        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ReleaseActivity.class);
                startActivity(intent);
            }
        });

        tvGrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ProjectStatusActivity.class);
                startActivity(intent);
            }
        });

        tvTrade.setOnClickListener(new View.OnClickListener() {      //交易
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AccountActivity.class);
                startActivity(intent);
            }
        });

        tvEvaluate.setOnClickListener(new View.OnClickListener() {      //收到的评价列表
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, EvaluateListActivity.class);
                startActivity(intent);
            }
        });

        tvJifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, JiFenActivity.class);
                startActivity(intent);
            }
        });

        tvPeopleManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PeopleManageActivity.class);
                startActivity(intent);
            }
        });

        tvInviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, InviteFriendsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        user = getUser();

        if (user != null) {
            String recommend = user.getRecommend();
            if (TextUtils.equals("0", recommend)) {
                setNoEnoughWorkerView();
            } else {
                setEnoughWorkerView();
            }
        }


    }

    /**
     * 推荐人数足够
     */
    private void setEnoughWorkerView() {
        llVerItems.setVisibility(View.VISIBLE);
        eweimaLayout.setVisibility(View.GONE);

    }

    /**
     * 推荐人数不够时， 显示二维码
     */
    private void setNoEnoughWorkerView() {
        llVerItems.setVisibility(View.VISIBLE);
        tvGrab.setVisibility(View.GONE);
        tvTrade.setVisibility(View.GONE);
        tvEvaluate.setVisibility(View.GONE);
        tvJifen.setVisibility(View.GONE);
        tvPeopleManage.setVisibility(View.GONE);
        tvInviteFriend.setVisibility(View.GONE);
        eweimaLayout.setVisibility(View.VISIBLE);
        tvInviteCode.setText("邀请码：" + user.getInvite_code());

        initInviteQR();
    }

    /**
     * 初始化二维码图片
     */
    private void initInviteQR() {
//        eweimaLayout.setVisibility(View.VISIBLE);
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File directory = Environment.getExternalStorageDirectory();
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File eweima = new File(directory, "eweima.jpg");
            final String path = eweima.getAbsolutePath();
            ViewTreeObserver viewTreeObserver = ivEweima.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ivEweima.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int width = ivEweima.getWidth();
                    int height = ivEweima.getHeight();
                    Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_144);
                    if (width <= 0) {
                        width = DensityUtils.dp2px(mActivity, 200);
                    }
                    boolean b = QRCodeUtil.createQRImage(getUser().getId(), width, width, logoBitmap, path);
                    if (b) {
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        ivEweima.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
