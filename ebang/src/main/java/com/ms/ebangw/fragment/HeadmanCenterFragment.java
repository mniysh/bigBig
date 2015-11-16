package com.ms.ebangw.fragment;


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
import com.ms.ebangw.activity.JiFenActivity;
import com.ms.ebangw.activity.PublishedProjectActivity;
import com.ms.ebangw.activity.RecommendedWorksActivity;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.utils.QRCodeUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工长中心
 *
 * @author wangkai
 */
public class HeadmanCenterFragment extends BaseFragment {
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
    @Bind(R.id.tv_grab)
    TextView tvGrab;
    @Bind(R.id.tv_trade)
    TextView tvTrade;
    @Bind(R.id.tv_evaluate)
    TextView tvEvaluate;
    @Bind(R.id.tv_jifen)
    TextView tvFen;
    @Bind(R.id.tv_people_manage)
    TextView tvPeopleManage;
    @Bind(R.id.tv_invite_friend)
    TextView tvInviteFriend;
    @Bind(R.id.ll_verItems)
    LinearLayout llVerItems;


    private String mParam1;
    private String mParam2;
    private ViewGroup contentLayout;
    private FragmentManager fm;

    public static HeadmanCenterFragment newInstance(String param1, String param2) {
        HeadmanCenterFragment fragment = new HeadmanCenterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HeadmanCenterFragment() {
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
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_headman_center, null);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;
    }

    @Override
    public void initView() {
        fm.beginTransaction().replace(R.id.fl_head_info, HeadInfoFragment.newInstance("", ""))
            .commit();

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

        tvFen.setOnClickListener(new View.OnClickListener() {      //收到的评价列表
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, JiFenActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        User user = getUser();

        if (user != null) {
            String recommend = user.getRecommend();
            if (TextUtils.equals("0", recommend)) {
                setNoEnoughWorkerView();
            } else {
                setEnoughWorkerView();
            }
        }

        tvGrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PublishedProjectActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 推荐人数足够
     */
    private void setEnoughWorkerView() {
        llVerItems.setVisibility(View.VISIBLE);
        eweimaLayout.setVisibility(View.GONE);
        tvPeopleManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, RecommendedWorksActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 推荐人数不够时， 显示二维码
     */
    private void setNoEnoughWorkerView() {
        llVerItems.setVisibility(View.GONE);
        eweimaLayout.setVisibility(View.VISIBLE);
        initInviteQR();
    }

    /**
     * 初始化二维码图片
     */
    private void initInviteQR() {
        eweimaLayout.setVisibility(View.VISIBLE);
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
                    Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ms_logo_144);
                    boolean b = QRCodeUtil.createQRImage(getUser().getId(), width, height, logoBitmap, path);
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
