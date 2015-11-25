package com.ms.ebangw.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.commons.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 开发商或个人确定雇佣工人  工人同意或拒绝邀请的对话框
 * @author wangkai
 */
public class ConfirmDialog extends DialogFragment {
    private static final String NAME = "name";
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    private String category, realName;
    private OnConfirmListener mListener;
    public ConfirmDialog() {
        // Required empty public constructor
    }

    public static ConfirmDialog newInstance(String category, String name) {
        ConfirmDialog fragment = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_CATEGORY, category);
        args.putString(NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(Constants.KEY_CATEGORY);
            realName = getArguments().getString(NAME);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        setStyle(STYLE_NO_FRAME, 0);
        return dialog;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirm_dialog, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        if (null == category) {
            return;
        }
        switch (category) {
            case Constants.WORKER:
                tvDescription.setText("确定跟着工长\\n" + realName + "来工作么？");
                break;
            case Constants.DEVELOPERS:
            case Constants.INVESTOR:
                tvDescription.setText("确定雇佣工长\\n" + realName + "吗？");
        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(false);
                    dismiss();
                }
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(true);
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    

    public interface OnConfirmListener {
        void onClick(boolean isAgree);
    }

    public void setListener(OnConfirmListener mListener) {
        this.mListener = mListener;
    }
}
