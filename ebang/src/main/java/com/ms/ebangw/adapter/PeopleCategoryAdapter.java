package com.ms.ebangw.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.People;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-10-30 13:44
 */
public class PeopleCategoryAdapter extends BaseAdapter {
    private String category;
    private List<People> list;
    private OnAgreeListener onAgreeListener;

    public PeopleCategoryAdapter(String category, List<People> list, OnAgreeListener
        onAgreeListener) {
        this.category = category;
        this.list = list;
        this.onAgreeListener = onAgreeListener;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.people_category_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String firstChar = null;
        final People people = (People) getItem(position);
        if (position == 0) {
            firstChar = people.getPinyin().substring(0, 1);
        } else {
            String py = people.getPinyin().substring(0, 1);
            String spy = list.get(position - 1).getPinyin().substring(0, 1);
            if (!py.equals(spy)) {
                firstChar = people.getPinyin().substring(0, 1);
            }
        }
        if (firstChar == null) {
            holder.tvPy.setVisibility(View.GONE);
        } else {
            holder.tvPy.setVisibility(View.VISIBLE);
            holder.tvPy.setText(firstChar);
        }

        if (TextUtils.equals(category, Constants.WORKER)) {
            holder.tvAgree.setText("同意");
        }else {
            holder.tvAgree.setText("雇佣");
        }

        holder.tvName.setText(people.getReal_name());
        String head_image = people.getHead_image();
        if (!TextUtils.isEmpty(head_image)) {
            Picasso.with(parent.getContext()).load(DataAccessUtil.getImageUrl(head_image)).
                placeholder(R.drawable.worker_avatar).into(holder.ivAvatar);
        }else {
            holder.ivAvatar.setImageResource(R.drawable.worker_avatar);
        }
        holder.tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onAgreeListener) {
                    onAgreeListener.onAgree(people);

                }
            }
        });
        convertView.setTag(Constants.KEY_PEOPLE, people);
        return convertView;
    }

    public interface OnAgreeListener {
        void onAgree(People people);

    }

    public List<People> getList() {
        return list;
    }

    public void setList(List<People> list) {
        this.list = list;
    }


    static class ViewHolder {
        @Bind(R.id.tv_py)
        TextView tvPy;
        @Bind(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_agree)
        TextView tvAgree;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
