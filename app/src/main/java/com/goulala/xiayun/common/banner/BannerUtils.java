package com.goulala.xiayun.common.banner;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.imageloader.BannerImageLoader;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.utils.ConstantValue;
import com.goulala.xiayun.common.utils.SizeUtils;
import com.goulala.xiayun.home.model.HomeBannerDate;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/27
 * function : 加载banner和图片
 */
public class BannerUtils {


    /**
     * 加载首页的banner
     */
    public static void loadHomePageBanner(Banner mBanner, List<HomeBannerDate> bannerDate, String bannerType) {
        if (mBanner == null) return;
        if (bannerDate == null) return;
        List<String> imageViewList = new ArrayList<>();
        for (int i = 0; i < bannerDate.size(); i++) {
            imageViewList.add(bannerDate.get(i).getSmallimage());
        }
        //设置图片加载器
        mBanner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        mBanner.setImages(imageViewList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(ConstantValue.VP_TURN_TIME);
        switch (bannerType) {
            case ApiParam.TOP_BANNER:
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR); //	显示圆形指示器
                //设置指示器位置（当banner模式中有指示器时）
                mBanner.setIndicatorGravity(BannerConfig.CENTER);
                break;
            case ApiParam.RIGHT_BANNER_ONE:
                mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR); //不显示指示器
                break;
            case ApiParam.RIGHT_BANNER_TWO:
                mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR); //不显示指示器
                break;
            default:
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR); //	显示圆形指示器
                //设置指示器位置（当banner模式中有指示器时）
                mBanner.setIndicatorGravity(BannerConfig.CENTER);
                break;
        }
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

    }

    /**
     * 加载详情的banner
     */
    public static void loadGoodDetailsBanner(Banner mBanner, List<String> imageViewList) {
        if (mBanner == null) return;
        if (imageViewList == null) return;
        //设置图片加载器
        mBanner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        mBanner.setImages(imageViewList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(ConstantValue.VP_TURN_TIME);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

    }

    /**
     * 添加图片到商品详情页面的底部
     */
    public static void addBottomImageToGroup(Context context, LinearLayout llProductBottomImageView, List<String> imageViewList) {
        if (imageViewList == null) return;
        llProductBottomImageView.removeAllViews();
        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = LinearLayout.LayoutParams.MATCH_PARENT;
//            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = SizeUtils.dp2px(360);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoaderUtils.displayBannerImage(imageViewList.get(i), imageView);
            llProductBottomImageView.addView(imageView);
        }

    }


}
