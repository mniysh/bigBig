package com.ms.ebangw.dialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 抢单的dialog
 */
public class QiangDanDialog extends DialogFragment {
    private ViewGroup contentLayout;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String flag;
    private String mParam2;
    @Bind(R.id.iv_image)
    ImageView imageView;
    @Bind(R.id.tv_status)
    TextView statusTv;
    @Bind(R.id.tv_message)
    TextView messageTv;
    @Bind(R.id.but_button)
    Button button;
    public static QiangDanDialog newInstance(String param1, String param2) {
        QiangDanDialog fragment = new QiangDanDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public QiangDanDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flag = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_qiang_dan_dialog,container, false);
        ButterKnife.bind(this, contentLayout);
        initView();
        return contentLayout;
    }

    private void initView() {
        if(TextUtils.equals(flag,"succeed")){
            imageView.setImageResource(R.drawable.smile);
            statusTv.setText("抢单成功");
            messageTv.setText("成功");
            button.setText("确定");
        }
        if(TextUtils.equals(flag, "failed")){
            imageView.setImageResource(R.drawable.sad);
            statusTv.setText("抢单失败");
            messageTv.setText("失败");
            button.setText("继续抢单");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }



}
