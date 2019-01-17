package com.goulala.xiayun.shopcar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseFragment;

/**
 * author      : Z_B
 * date       : 2019/1/17
 * function  :
 */
public class ShopCarFragment extends BaseFragment {


    public static ShopCarFragment newInstance() {
        ShopCarFragment shopCarFragment = new ShopCarFragment();
        Bundle bundle = new Bundle();
        shopCarFragment.setArguments(bundle);
        return shopCarFragment;
    }
    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_shopcarfragment_layout;
    }

    @Override
    public void bindViews(View contentView) {

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void setClickListener(View view) {

    }
}
