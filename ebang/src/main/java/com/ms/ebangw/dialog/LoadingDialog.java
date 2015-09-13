package com.ms.ebangw.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.ebangw.R;

/**
 * 正在加载中 的提示ProgressDialog
 * @author wangkai
 *
 */
public class LoadingDialog extends DialogFragment {
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    /**
     * ProgressDialog的标题和内容
     */
    private String title, message;


    public static LoadingDialog newInstance(String message) {
        LoadingDialog fragment = new LoadingDialog();
        Bundle args = new Bundle();
//        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            title = getArguments().getString(TITLE);
            message = getArguments().getString(MESSAGE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        TextView messageTv = (TextView) view.findViewById(R.id.txt_message);
        if (!TextUtils.isEmpty(message)) {
            messageTv.setText(message);
        }
        return view;
    }

}
