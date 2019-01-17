package com.goulala.xiayun.common.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 图片加载的工具类
 */
public class ImageLoaderUtils {

    public static ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }


    public static PauseOnScrollListener getPauseOnScrollListener() {
        return new PauseOnScrollListener(getImageLoader(), false, true);
    }


    /**
     * banner图片
     *
     * @param uri
     * @param view
     */
    public static void displayBannerImage(String uri, ImageView view) {
        getImageLoader().displayImage(uri, view, ImageOptHelper.getBannerImgOptions());
    }

    /**
     * 商品图片
     *
     * @param uri
     * @param view
     */
    public static void displayGoods(String uri, ImageView view) {
        getImageLoader().displayImage(uri, view, ImageOptHelper.getGoodsOptions());
    }


    /**
     * 用户头像
     *
     * @param uri
     * @param view
     */
    public static void displayAvatar(String uri, ImageView view) {
        getImageLoader().displayImage(uri, view, ImageOptHelper.getAvatarOptions());
    }

    /**
     * 大图
     *
     * @param uri
     * @param view
     */
    public static void displayBigImage(String uri, ImageView view) {
        getImageLoader().displayImage(uri, view, ImageOptHelper.getBigImgOptions());
    }

    /**
     * 加载图片
     *
     * @param path
     * @param listener
     */
    public static void loadImage(String path, final LoadingListener listener) {
        getImageLoader().loadImage(path, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                listener.onLoadingComplete(loadedImage);
            }
        });
    }

    public interface LoadingListener {
        void onLoadingComplete(Bitmap loadedImage);
    }

    /**
     * 加载自定义尺寸的图片
     *
     * @param context
     * @param path
     * @param targetWidth
     * @param targetHeight
     * @param target
     */
    public static void displaySizeImage(Context context, String path, int targetWidth, int targetHeight, ImageView target) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(path, target, ImageOptHelper.getBannerImgOptions());

    }
}
