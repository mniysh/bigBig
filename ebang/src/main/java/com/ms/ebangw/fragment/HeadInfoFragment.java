package com.ms.ebangw.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.bean.UploadImageResult;
import com.ms.ebangw.bean.User;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.crop.CropImageActivity;
import com.ms.ebangw.dialog.SelectPhotoDialog;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.scancode.MipcaActivityCapture;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.setting.SettingAllActivity;
import com.ms.ebangw.utils.BitmapUtil;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.T;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用户头部信息
 * @author wangkai
 */
public class HeadInfoFragment extends CropEnableFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private ViewGroup contentLayout;
    @Bind(R.id.tv_name)
    TextView nameTv;
    @Bind(R.id.tv_phone)
    TextView phoneTv;
    @Bind(R.id.tv_rank)
    TextView rankTv;
    @Bind(R.id.iv_head)
    ImageView headIv;
    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    public HeadInfoFragment() {
        // Required empty public constructor
    }

    public static HeadInfoFragment newInstance(String param1, String param2) {
        HeadInfoFragment fragment = new HeadInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == mActivity.RESULT_OK) {
            if (requestCode == SCANNIN_GREQUEST_CODE ) {
                Bundle bundle = data.getExtras();
                //显示扫描到的内容
                String result = bundle.getString("result");
                L.d("二维码扫描结果: " + result);
                workerRecommendHeadman(result);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_head_info, null);
        ButterKnife.bind(this, contentLayout);
        initView();
        initData();
        return contentLayout;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle(null, null, "我的信息", "设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置跳转
                Intent intent = new Intent(mActivity, SettingAllActivity.class);
                mActivity.startActivityForResult(intent, Constants.REQUEST_EXIT);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = getUser();

        if (TextUtils.equals(user.getCategory(), Constants.WORKER) && !TextUtils.equals("1", user
            .getIs_have_headman())) {
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setImageResource(R.drawable.scan_code);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, MipcaActivityCapture.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                }
            });
        }else {
            ivBack.setVisibility(View.GONE);
        }

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        initHeadInfo();
    }

    /**
     * 用户信息
     */
    public void initHeadInfo() {
        headIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPhotoDialog();
            }
        });
        loadAvatar();
        User user = getUser();
        if (null != user) {
            String rank = user.getRank();
            setNickName();
            rankTv.setText("等级：" + rank + " 级");
        }
    }

    private void setNickName() {
        L.d("xxx", "现在的用户昵称是" + getUser().getNick_name());
        User user = getUser();
        if (null != user) {
            String phone = user.getPhone();

//            /**
//             * 把*变成红色
//             */
//            public void setStarRed() {
//                int[] resId = new int[]{R.id.tv_a, R.id.tv_b, R.id.tv_c, R.id.tv_d, R.id.tv_e};
//                for (int i = 0; i < resId.length; i++) {
//                    TextView a = (TextView) contentLayout.findViewById(resId[i]);
//                    String s = a.getText().toString();
//                    SpannableString spannableString = new SpannableString(s);
//                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    a.setText(spannableString);
//                }
//            }
            SpannableString spannableString = new SpannableString(phone);
//            spannableString.setSpan(new String("*"),2, 5, Spanned.);


            phoneTv.setText(user.getPhone());
            nameTv.setText(user.getNick_name());
        }
    }

    public void showSelectPhotoDialog() {
        SelectPhotoDialog selectPhotoDialog = SelectPhotoDialog.newInstance("", "");
        selectPhotoDialog.setSelectListener(new SelectPhotoDialog.OnSelectListener() {
            @Override
            public void onCameraSelected(View view) {
                captureImageByCamera(view, CropImageActivity.TYPE_HEAD);
            }

            @Override
            public void onPhotoSelected(View view) {
                selectPhoto(view, CropImageActivity.TYPE_HEAD);
            }
        });

        selectPhotoDialog.show(getFragmentManager(), "SelectPhotoDialog");
    }

    public void loadAvatar() {
        User user = getUser();
        String head_image = user.getHead_img();
        if (!TextUtils.isEmpty(head_image)) {
            String imageUrl = DataAccessUtil.getImageUrl(head_image);
            Picasso.with(mActivity).load(Uri.parse(imageUrl)).into(headIv);
        }
    }

    /**
     * 工人扫码推荐工长
     * @param headmanId
     */
    private void workerRecommendHeadman(String headmanId) {
        DataAccessUtil.workerRecommendHeadman(headmanId, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    boolean b = DataParseUtil.processDataResult(response);
                    if(b){
                        T.show("成功推荐工长");
                    }

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

    @Override
    public void onCropImageSuccess(View view, String cropedImagePath, UploadImageResult imageResult) {
        super.onCropImageSuccess(view, cropedImagePath, imageResult);
        Bitmap bitmap = BitmapUtil.getImage(cropedImagePath);
        headIv.setImageBitmap(bitmap);
    }
}
