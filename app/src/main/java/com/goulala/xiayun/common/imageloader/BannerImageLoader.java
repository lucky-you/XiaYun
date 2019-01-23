package com.goulala.xiayun.common.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * author : Z_B
 * date : 2018/8/27
 * function : 加载banner
 */
public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ImageLoaderUtils.displayBannerImage((String) path,imageView);

    }
}
