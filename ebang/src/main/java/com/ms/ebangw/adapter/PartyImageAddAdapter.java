package com.ms.ebangw.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ms.ebangw.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-12-06 22:53
 */
public class PartyImageAddAdapter extends BaseAdapter {
    private Activity activity;
    private List<String> list;

    public PartyImageAddAdapter(Activity activity, List<String> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = list.get(position);
        Context context = parent.getContext();
        ImageView imageView = new ImageView(context);
        WindowManager wm = activity.getWindowManager();
        int perWidth = wm.getDefaultDisplay().getWidth() / 3;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(perWidth, perWidth);
        imageView.setLayoutParams(params);
        if (TextUtils.equals("camera", s)) {
            imageView.setImageResource(R.drawable.camera_gray);
        }else {
            Picasso.with(context).load(Uri.parse(list.get(position))).into(imageView);

        }
        return imageView;
    }
}
