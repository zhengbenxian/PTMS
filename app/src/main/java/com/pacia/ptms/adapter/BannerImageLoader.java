package com.pacia.ptms.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pacia.ptms.R;
import com.youth.banner.loader.ImageLoader;

/**
 * banner 加载图片
 * Created by p on 2018/05/10.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.ic_img_failed)
                .centerCrop();
        Glide.with(context).load(path)
                .apply(options)
                .into(imageView);
    }
}
