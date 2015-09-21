package com.ms.ebangw.Photo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.ImageFloder;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.PhotoImageLoader;

import java.util.List;

class ImageDrisAdapter extends BaseAdapter {
    private List<ImageFloder> mImageFloders;
    private Context mContext;

    public ImageDrisAdapter(Context mContext, List<ImageFloder> mImageFloders) {
        this.mContext = mContext;
        this.mImageFloders = mImageFloders;
    }

    @Override
    public int getCount() {
        return mImageFloders.size();
    }

    @Override
    public Object getItem(int position) {
        return mImageFloders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.list_dir_item, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.id_dir_item_image);
            holder.dirName = (TextView) convertView.findViewById(R.id.id_dir_item_name);
            holder.photoCount = (TextView) convertView.findViewById(R.id.id_dir_item_count);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageFloder imageFloder = (ImageFloder) getItem(position);

        setImageByUrl(holder.iv, imageFloder.getFirstImagePath());
        holder.dirName.setText(imageFloder.getName());
        holder.photoCount.setText(imageFloder.getCount() + "张");

        convertView.setTag(holder);
        convertView.setTag(Constants.KEY_IMAGE_FLODER, imageFloder);
        return convertView;
    }

    /**
     * 为ImageView设置图片
     */
    public void setImageByUrl(ImageView imageView, String url)
    {
        PhotoImageLoader.getInstance(3, PhotoImageLoader.Type.LIFO).loadImage(url, imageView);

    }

    static class ViewHolder {

        private ImageView iv;
        private TextView dirName, photoCount;

    }
}