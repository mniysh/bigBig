package com.ms.ebangw.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.ebangw.R;

/**
 * 身份证照片上传
 * @author wangkai
 */
public class CardVerifyFragment extends Fragment {
    private static final String CATEGORY = "category";

    private String category;

    public static CardVerifyFragment newInstance(String category) {
        CardVerifyFragment fragment = new CardVerifyFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public CardVerifyFragment() {
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
        return inflater.inflate(R.layout.fragment_card_verify, container, false);
    }


}
