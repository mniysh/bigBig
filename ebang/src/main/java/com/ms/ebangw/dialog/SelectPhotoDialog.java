package com.ms.ebangw.dialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 选择照片的dialog
 *@author wangkai
 */
public class SelectPhotoDialog extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ViewGroup contentLayout;
    @Bind(R.id.tv_camera)
    TextView cameraTv;
    @Bind(R.id.tv_photo)
    TextView photoTv;

    public static SelectPhotoDialog newInstance(String param1, String param2) {
        SelectPhotoDialog fragment = new SelectPhotoDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SelectPhotoDialog() {
        // Required empty public constructor
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

        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_select_photo_dialog, container,
            false);
        ButterKnife.bind(this, contentLayout);
        initData();
        return contentLayout;
    }

    public void initData() {
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null) {
                    selectListener.onCameraSelected();
                    dismiss();
                }
            }
        });

        photoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null) {
                    selectListener.onPhotoSelected();
                    dismiss();
                }
            }
        });
    }

    public interface OnSelectListener{
        void onCameraSelected();
        void onPhotoSelected();

    }

    private OnSelectListener selectListener;

    public void setSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
    }

}
