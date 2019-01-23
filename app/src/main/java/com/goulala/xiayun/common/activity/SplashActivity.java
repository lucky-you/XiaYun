package com.goulala.xiayun.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseActivity;
import com.goulala.xiayun.common.utils.ConstantValue;
import com.goulala.xiayun.common.utils.SPUtils;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {

    private ImageView ivSplashImage;

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void bindViews(View contentView) {
        ivSplashImage = get(R.id.ivSplashImage);

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        /**需要判断用户是不是第一次安装（也就是是不是第一次进入到app）
         *如果是第一次安装，则加载引导页面，
         * 不是第一次，则加载启动页面
         */
        boolean starMain = (boolean) SPUtils.get(ConstantValue.START_MAIN, false);
        if (!starMain) {
            //引导页
            intentToActivity(GuidePageActivity.class);
            finish();
        } else {
            //获取权限，进入首页
            intentToActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    public void setClickListener(View view) {

    }
}
