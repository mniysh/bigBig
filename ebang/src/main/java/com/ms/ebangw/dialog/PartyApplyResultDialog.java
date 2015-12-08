package com.ms.ebangw.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-12-08 13:55
 */
public class PartyApplyResultDialog extends DialogFragment {
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DESCRIPTION = "description";
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    /**
     * 确定还是查看其它活动
     */
    private View.OnClickListener listener;

    public static PartyApplyResultDialog newInstance(String content, String buttonDescription) {
        PartyApplyResultDialog dialog = new PartyApplyResultDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CONTENT, content);
        bundle.putString(KEY_DESCRIPTION, buttonDescription);
        dialog.setArguments(bundle);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.party_apply_result, null);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        if (null != arguments) {
            tvContent.setText(arguments.getString(KEY_CONTENT));
            tvConfirm.setText(arguments.getString(KEY_DESCRIPTION));
        }

        if (null != listener) {
            tvConfirm.setOnClickListener(listener);
        }
        return view;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
