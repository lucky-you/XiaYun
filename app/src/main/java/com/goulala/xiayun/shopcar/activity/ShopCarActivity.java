package com.goulala.xiayun.shopcar.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseActivity;

/**
 * 购物车的activity
 */
public class ShopCarActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShopCarActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_shop_car;
    }

    @Override
    public void bindViews(View contentView) {

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        Fragment fragment = getFragmentManager().findFragmentByTag("SHOP_CAR");

    }

    @Override
    public void setClickListener(View view) {

    }
}
