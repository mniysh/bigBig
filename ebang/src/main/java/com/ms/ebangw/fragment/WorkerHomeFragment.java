package com.ms.ebangw.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ms.ebangw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工友之家
 */
public class WorkerHomeFragment extends BaseFragment {
    private static final String RES_ID = "resId";
    private static final String TYPE = "type";
    private View mContentView;
    private int  resId, type;
    @Bind(R.id.iv)
    ImageView iv;

    /**
     *
     * @param resId
     * @param type  1:电子商城  2：工友之家
     * @return
     */
    public static WorkerHomeFragment newInstance(int resId, int type) {
        WorkerHomeFragment fragment = new WorkerHomeFragment();
        Bundle args = new Bundle();
        args.putInt(RES_ID, resId);
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public WorkerHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resId = getArguments().getInt(RES_ID);
            type = getArguments().getInt(TYPE);

        }
    }

    @Override
    public void initView() {
        iv.post(new Runnable() {
            @Override
            public void run() {
                if (!isAdded()) {
                    return;
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                BitmapFactory.decodeResource(getResources(), resId, options);
                int photoW = options.outWidth;
                int photoH = options.outHeight;

                int screenWidth = iv.getWidth();
                int scaleFactor = 1;
                if (screenWidth > 0) {
                    scaleFactor = photoW / screenWidth;
                }
                options.inSampleSize = scaleFactor;
                options.inJustDecodeBounds = false;
                options.inPurgeable = true;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId, options);
                iv.setImageBitmap(bitmap);

            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_worker_home, container, false);
        ButterKnife.bind(this, mContentView);
        initView();
        initData();
        return mContentView;
    }


}
