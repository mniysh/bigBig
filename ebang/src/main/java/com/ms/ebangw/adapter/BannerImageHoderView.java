package com.ms.ebangw.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.ms.ebangw.bean.BannerImage;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.utils.T;
import com.squareup.picasso.Picasso;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-04 11:03
 */
public class BannerImageHoderView  implements CBPageAdapter.Holder<BannerImage> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, BannerImage data) {
        String imageUrl = data.getImage_url();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(context).load(imageUrl).into(imageView);
        }
        imageView.setTag(Constants.KEY_BANNER_IMAGE, data);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.show("第 " + position + " 个图片被点击");

            }
        });
    }
}
