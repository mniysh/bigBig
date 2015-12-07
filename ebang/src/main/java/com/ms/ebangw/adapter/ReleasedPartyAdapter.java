package com.ms.ebangw.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.ms.ebangw.social.SocialPartyDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        final Party party = list.get(position);
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
//        List<String> active_image = party.getActive_image();
        List<String> active_image = getTestImageUrls();
        if (null != active_image && active_image.size() > 0) {
            PartyImageAdapter imageAdapter = new PartyImageAdapter(active_image);
            LinearLayoutManager manager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false);
            holder.rv.setLayoutManager(manager);
            holder.rv.setAdapter(imageAdapter);
        }

        holder.tvStatus.setVisibility(View.GONE);
        holder.tvName.setText(real_name);
        holder.tvTitle.setText(title);
        holder.tvContent.setText(Html.fromHtml(theme));

        holder.tvReleaseDate.setText(created_at);
        if (!TextUtils.isEmpty(head_image)) {
            Picasso.with(parent.getContext()).load(DataAccessUtil.getImageUrl(head_image)).
                placeholder(R.drawable.worker_avatar).into(holder.ivAvatar);
        } else {
            holder.ivAvatar.setImageResource(R.drawable.worker_avatar);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Party party = (com.ms.ebangw.bean.Party) v.getTag(Constants.KEY_PARTY);
                String partyId = party.getActive_id();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_PART_ID, partyId);
                Intent intent = new Intent(parent.getContext(), SocialPartyDetailActivity.class);
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });
        convertView.setTag(Constants.KEY_PARTY, party);
        return convertView;
    }

    private List<String> getTestImageUrls() {
        List<String> images = new ArrayList<>();
        images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
        return images;
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
