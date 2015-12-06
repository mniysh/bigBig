package com.ms.ebangw.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ms.ebangw.R;
import com.ms.ebangw.service.DataAccessUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-12-06 10:07
 */
public class PartyImageAdapter extends RecyclerView.Adapter<PartyImageAdapter.ViewHolder> {
    private List<String> mImageUrls;

    public PartyImageAdapter(List<String> mImageUrls) {
        this.mImageUrls = mImageUrls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.party_image_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri uri = Uri.parse(DataAccessUtil.getImageUrl(mImageUrls.get(position)));

        Picasso.with(holder.mImageView.getContext()).load(uri).centerCrop().resizeDimen(R.dimen.social_party_image_width, R.dimen.social_party_image_height).into(holder.mImageView);
//        holder.mSDV.setImageURI(uri);
    }


    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

}
