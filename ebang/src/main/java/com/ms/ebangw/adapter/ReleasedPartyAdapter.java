package com.ms.ebangw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.Party;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.service.DataAccessUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 发布的活动Adapter
 * User: WangKai(123940232@qq.com)
 * 2015-11-12 09:09
 */
public class ReleasedPartyAdapter extends BaseAdapter {
    private Context context;
    private List<Party> list;

    public ReleasedPartyAdapter(Context context, List<Party> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Party> list) {
        this.list = list;
    }

    public List<Party> getList() {
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
        Party party = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.released_party_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String real_name = party.getReal_name();
        String head_image = party.getHead_image();
        String title = party.getTitle();
        String created_at = party.getCreated_at();
        String theme = party.getTheme();
        List<String> active_image = party.getActive_image();

        holder.tvStatus.setVisibility(View.GONE);
        holder.tvName.setText(real_name);
        holder.tvTitle.setText(title);
        holder.tvContent.setText(theme);
        holder.tvReleaseDate.setText(created_at);
        if (!TextUtils.isEmpty(head_image)) {
            Picasso.with(parent.getContext()).load(DataAccessUtil.getImageUrl(head_image)).
                placeholder(R.drawable.worker_avatar).into(holder.ivAvatar);
        } else {
            holder.ivAvatar.setImageResource(R.drawable.worker_avatar);
        }

        convertView.setTag(Constants.KEY_PARTY, party);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.rv)
        RecyclerView rv;
        @Bind(R.id.tv_release_date)
        TextView tvReleaseDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
