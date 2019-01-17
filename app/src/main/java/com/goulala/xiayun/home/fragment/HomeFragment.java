package com.goulala.xiayun.home.fragment;

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
public class HomeFragment extends BaseFragment {


    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_homefragment_layout;
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
