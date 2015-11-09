package com.ms.ebangw.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.RecommendedDeveoper;
import com.ms.ebangw.utils.ScreenUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-03 14:09
 */
public class RecommendedDeveloperAdapter extends RecyclerView.Adapter<RecommendedDeveloperAdapter
    .ViewHolder> implements View.OnClickListener{
    private Context context;
    private List<RecommendedDeveoper> list;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public RecommendedDeveloperAdapter(Context context, List<RecommendedDeveoper> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecommendedDeveloperAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.recommended_developer_item, null);
        int screenWidth = ScreenUtils.getScreenWidth(parent.getContext());
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(screenWidth / 3, ViewGroup
            .LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendedDeveloperAdapter.ViewHolder holder, int position) {
        RecommendedDeveoper recommendedDeveoper = list.get(position);
        String logoUrl = recommendedDeveoper.getLogo();
        if (!TextUtils.isEmpty(logoUrl)) {
            Picasso.with(context).load(Uri.parse(logoUrl)).into(holder.companyLogoIv);
        }

        holder.companyName.setText(recommendedDeveoper.getCompany_name());
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(recommendedDeveoper);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener != null) {
            onRecyclerViewItemClickListener.onItemClick(v, (RecommendedDeveoper)v.getTag());
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView companyLogoIv;
        private TextView companyName;


        public ViewHolder(View itemView) {
            super(itemView);
            companyLogoIv = (ImageView) itemView.findViewById(R.id.iv_company_logo);
            companyName = (TextView) itemView.findViewById(R.id.tv_company_name);
        }

    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , RecommendedDeveoper recommendedDeveoper);
    }

    public OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {
        return onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
