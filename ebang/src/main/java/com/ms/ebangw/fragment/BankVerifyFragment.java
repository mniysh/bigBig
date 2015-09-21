package com.ms.ebangw.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.ebangw.R;

/**
 * 用户银行信息验证
 * @author wangkai
 */
public class BankVerifyFragment extends Fragment {
    private static final String CATEGORY = "category";
    private String category;


    public static BankVerifyFragment newInstance(String category) {
        BankVerifyFragment fragment = new BankVerifyFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public BankVerifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bank_verify, container, false);
    }


}
