package com.ms.ebangw.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.bean.Trade;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.release.PayingActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-12 09:09
 */
public class AccountDetailAdapter extends BaseAdapter {
    private Context context;
    private List<Trade> list;

    public AccountDetailAdapter(Context context, List<Trade> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Trade> list) {
        this.list = list;
    }

    public List<Trade> getList() {
        return list;
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
        final Trade trade = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.trade_detail_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String title = trade.getTitle();
        String money = trade.getMoney();
        String describe = trade.getDescribe();
        String time = trade.getCreated_at();


        if (!TextUtils.isEmpty(describe)) {
            holder.tvDescription.setText(describe);
            holder.tvDescription.setVisibility(View.VISIBLE);
            holder.tvDescription.setOnClickListener(new View.OnClickListener() {    //跳转到支付
                @Override
                public void onClick(View v) {
                    ReleaseProject releaseProject = trade.getProject_info();
                    if (null != releaseProject) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constants.KEY_RELEASE_PROJECT, releaseProject);
                        Intent intent = new Intent(context, PayingActivity.class );
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });

        }else {
            holder.tvDescription.setVisibility(View.GONE);
        }
        holder.tvTitle.setText(title);
        holder.tvTime.setText(time);
        holder.tvMoney.setText(money);

        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'trade_detail_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.tv_description)
        TextView tvDescription;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
