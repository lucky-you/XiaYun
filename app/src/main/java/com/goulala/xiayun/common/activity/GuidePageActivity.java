package com.goulala.xiayun.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.adapter.GuidePageAdapter;
import com.goulala.xiayun.common.base.BaseActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.db.DaoManagerUtils;
import com.goulala.xiayun.common.db.LocationBean;
import com.goulala.xiayun.common.location.LocationMapUtils;
import com.goulala.xiayun.common.permission.AndPermissionListener;
import com.goulala.xiayun.common.permission.AndPermissionUtils;
import com.goulala.xiayun.common.utils.SPUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 */
public class GuidePageActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        AMapLocationListener {

    private ViewPager viewPager;
    private TextView btStartMain;
    private List<ImageView> imageViews = new ArrayList<>();
    private GuidePageAdapter guidePageAdapter;
    private LocationMapUtils locationMapUtils;

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_guide_page;
    }

    @Override
    public void bindViews(View contentView) {
        StatusBarUtil.setStatusBar(this, false, false);
        viewPager = get(R.id.viewPager);
        btStartMain = get(R.id.bt_start_main);
        btStartMain.setOnClickListener(this);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        int[] imagePages = new int[]{
                R.drawable.ic_welcome_page_01,
                R.drawable.ic_welcome_page_02,
                R.drawable.ic_welcome_page_03,
                R.drawable.ic_welcome_page_04,
        };
        for (int i = 0; i < imagePages.length; i++) {
            ImageView imageview = new ImageView(mContext);
            imageview.setBackgroundResource(imagePages[i]);
            imageViews.add(imageview);
        }
        guidePageAdapter = new GuidePageAdapter(imageViews, mContext);
        viewPager.setAdapter(guidePageAdapter);
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    public void setClickListener(View view) {
        if (view.getId() == R.id.bt_start_main) {
            SPUtils.set(ConstantValue.START_MAIN, true);
            requestPermission(
                    Permission.ACCESS_FINE_LOCATION,
                    Permission.ACCESS_COARSE_LOCATION,
                    Permission.READ_EXTERNAL_STORAGE,
                    Permission.WRITE_EXTERNAL_STORAGE
            );

        }
    }

    /**
     * 申请权限
     */
    private void requestPermission(String... permissions) {
        AndPermissionUtils.requestPermission(mContext, new AndPermissionListener() {
            @Override
            public void PermissionSuccess(List<String> permissions) {
                localCurrentAddress();
                intentToActivity(MainActivity.class);
                finish();
            }

            @Override
            public void PermissionFailure(List<String> permissions) {

            }
        }, permissions);


    }

    //定位当前城市
    public void localCurrentAddress() {
        locationMapUtils = new LocationMapUtils(mContext, this);
        locationMapUtils.initOnLocationMap();
    }

    @Override
    public void onPageScrolled(int position, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        //在最后一张图片中显示按钮
        if (position == imageViews.size() - 1) {
            btStartMain.setVisibility(View.VISIBLE);
            viewPager.setEnabled(true);
        } else {
            btStartMain.setVisibility(View.GONE);
            viewPager.setEnabled(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int position) {

    }


    @Override
    protected void onDestroy() {
        locationMapUtils.stopLocation();
        locationMapUtils.destroyLocation();
        super.onDestroy();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && 0 == aMapLocation.getErrorCode()) { //定位成功
            double Longitude = aMapLocation.getLongitude();
            double Latitude = aMapLocation.getLatitude();
            String provinceName = aMapLocation.getProvince();
            String cityName = aMapLocation.getCity();
            String areaName = aMapLocation.getDistrict();//城区信息,定位到县区
            LocationBean locationBean = new LocationBean(Longitude, Latitude, provinceName, cityName, areaName);
            DaoManagerUtils.insertLocationBeanData(locationBean);
            Log.e("xy", "地址存储成功:" + provinceName + cityName + areaName);
        } else {
            showToast(mContext.getString(R.string.To_locate_failure) + aMapLocation.getErrorCode() + aMapLocation.getErrorInfo());
        }
    }
}
