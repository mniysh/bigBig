package com.ms.ebangw.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.ms.ebangw.activity.InviteWithCashActivity;
import com.ms.ebangw.activity.LotteryActivity;
import com.ms.ebangw.bean.BannerImage;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.web.WebActivity;
import com.squareup.picasso.Picasso;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-04 11:03
 */
public class BannerImageHolderView implements CBPageAdapter.Holder<BannerImage> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, final int position, final BannerImage data) {
        String imageUrl = data.getImage_url();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(context).load(imageUrl).into(imageView);
        }
        imageView.setTag(Constants.KEY_BANNER_IMAGE, data);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null) {
                    Intent intent;
                    String type = data.getType();
                    if (!TextUtils.isEmpty(type)) {

                        switch (type) {     //0普通 1抽奖 2邀友返现
                            case "0":
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.KEY_WEB_TITLE, data.getTitle());
                                bundle.putString(Constants.KEY_WEB_URL, data.getArticle_url());
                                bundle.putString(Constants.KEY_WEB_TYPE, data.getType());
                                intent = new Intent(context, WebActivity.class);
                                intent.putExtras(bundle);
                                break;
                            case "1":
                                intent = new Intent(context, LotteryActivity.class);
                                break;
                            case "2":
                                intent = new Intent(context, InviteWithCashActivity.class);
                                break;
                            default:
                                intent = new Intent(context, WebActivity.class);
                                break;
                        }

                        context.startActivity(intent);
                    }
                }
            }
        });
    }
}
