package com.ms.ebangw.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.ShowedCraft;
import com.ms.ebangw.commons.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-10-30 13:44
 */
public class ShowCraftAdapter extends BaseAdapter {
    private List<ShowedCraft> list;

    public ShowCraftAdapter(List<ShowedCraft> list) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.show_scraft_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String firstChar = null;
        final ShowedCraft showedCraft = (ShowedCraft) getItem(position);
        if (position == 0) {
            firstChar = showedCraft.getPinyin().substring(0, 1);
        } else {
            String py = showedCraft.getPinyin().substring(0, 1);
            String spy = list.get(position - 1).getPinyin().substring(0, 1);
            if (!py.equals(spy)) {
                firstChar = showedCraft.getPinyin().substring(0, 1);
            }
        }
        if (firstChar == null) {
            holder.tvPy.setVisibility(View.GONE);
        } else {
            holder.tvPy.setVisibility(View.VISIBLE);
            holder.tvPy.setText(firstChar);
        }

        holder.tvCraftName.setText(showedCraft.getCtaft_name());
        String count = showedCraft.getCount();
        if (!TextUtils.isEmpty(count)) {
            try {
                int num = Integer.parseInt(count);
                holder.tvCount.setText("还差" + num + "人");
                holder.tvCount.setTextColor(parent.getResources().getColor(R.color.auther_red));
            } catch (Exception e) {
                holder.tvCount.setText("已满额");
                holder.tvCount.setCompoundDrawables(null, null, null, null);
                holder.tvCount.setTextColor(parent.getResources().getColor(R.color.black_overlay));
            }
        }else {
            holder.tvCount.setText("已满额");
            holder.tvCount.setCompoundDrawables(null, null, null, null);
            holder.tvCount.setTextColor(parent.getResources().getColor(R.color.black_overlay));
        }
        convertView.setTag(Constants.KEY_SHOW_CRAFT, showedCraft);
        return convertView;
    }


    public List<ShowedCraft> getList() {
        return list;
    }

    public void setList(List<ShowedCraft> list) {
        this.list = list;
    }

    static class ViewHolder {
        @Bind(R.id.tv_py)
        TextView tvPy;
        @Bind(R.id.tv_craft_name)
        TextView tvCraftName;
        @Bind(R.id.tv_count)
        TextView tvCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
