package com.ms.ebangw.Photo;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ms.ebangw.R;
import com.ms.ebangw.utils.L;
import com.ms.ebangw.utils.PhotoImageLoader;

import java.util.LinkedList;
import java.util.List;


class MyPhotoAdapter extends BaseAdapter {
    private String TAG = getClass().getSimpleName();
    public List<String> mSelectedImage = new LinkedList<>();
    private String mDirPath;
    private Context mContext;
    private List<String> mDatas;

    public MyPhotoAdapter(Context context, List<String> datas, String dirPath) {
        this.mDirPath = dirPath;
        this.mContext = context;
        this.mDatas = datas;


    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.my_beautiful_photo_item, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.id_item_image);
            holder.ib = (ImageButton) convertView.findViewById(R.id.id_item_select);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ImageView mImageView = holder.iv;
        final ImageView mSelect = holder.ib;
        mImageView.setImageResource(R.drawable.pictures_no);  //设置未加载前的图片
        mSelect.setImageResource(R.drawable.picture_unselected); //设置未选中
        final String item = (String) getItem(position);
        setImageByUrl(mImageView, mDirPath + "/" + item);
        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mImageView.setOnClickListener(new View.OnClickListener()
        {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v)
            {
                // 已经选择过该图片
                if (mSelectedImage.contains(mDirPath + "/" + item))
                {
                    mSelectedImage.remove(mDirPath + "/" + item);
                    mSelect.setImageResource(R.drawable.picture_unselected);
                    mImageView.setColorFilter(null);
                } else
                // 未选择该图片
                {
                    mSelectedImage.add(mDirPath + "/" + item);
                    mSelect.setImageResource(R.drawable.pictures_selected);
                    mImageView.setColorFilter(Color.parseColor("#77000000"));
                }

                L.d(TAG, mDirPath + "/" + item);
            }
        });

        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mSelectedImage.contains(mDirPath + "/" + item))
        {
            mSelect.setImageResource(R.drawable.pictures_selected);
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        }

        convertView.setTag(holder);
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
        private ImageButton ib;

    }
}