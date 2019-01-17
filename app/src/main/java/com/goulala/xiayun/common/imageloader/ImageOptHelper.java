package com.goulala.xiayun.common.imageloader;

import android.graphics.Bitmap;

import com.goulala.xiayun.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * ImageLoader加载图片的配置类
 */
public class ImageOptHelper {

    public static DisplayImageOptions.Builder getBaseBuilder() {
        return new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565);
    }


    /**
     * banner图片
     * @return
     */
    public static DisplayImageOptions getBannerImgOptions() {
        return getBaseBuilder()
                .showImageOnLoading(R.drawable.ic_banner_default_image)
                .showImageForEmptyUri(R.drawable.ic_banner_default_image)
                .build();
    }

    /**
     * 商品图片
     *
     * @return
     */
    public static DisplayImageOptions getGoodsOptions() {
        return getBaseBuilder()
                .showImageOnLoading(R.drawable.ic_good_default_image)
                .showImageForEmptyUri(R.drawable.ic_good_default_image)
                .showImageOnFail(R.drawable.ic_good_default_image)
                .build();
    }

    /**
     * 大图
     *
     * @return
     */
    public static DisplayImageOptions getBigImgOptions() {
        return getBaseBuilder().build();
    }

    /**
     * 用户图像
     */
    public static DisplayImageOptions getAvatarOptions() {
        return getBaseBuilder()
                .showImageOnLoading(R.drawable.ic_user_default_header)
                .showImageForEmptyUri(R.drawable.ic_user_default_header)
                .showImageOnFail(R.drawable.ic_user_default_header)
                .build();
    }

    public static DisplayImageOptions getCornerOptions(int cornerRadiusPixels) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.ic_good_default_image)
                .showImageForEmptyUri(R.drawable.ic_good_default_image)
                .showImageOnFail(R.drawable.ic_good_default_image)
                .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels)).build();
        return options;
    }
}
