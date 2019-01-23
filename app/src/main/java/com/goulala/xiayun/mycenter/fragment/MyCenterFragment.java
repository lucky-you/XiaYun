package com.goulala.xiayun.mycenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseFragment;
import com.goulala.xiayun.common.utils.BarUtils;

/**
 * author      : Z_B
 * date       : 2019/1/17
 * function  :
 */
public class MyCenterFragment extends BaseFragment {

    public static MyCenterFragment newInstance() {
        MyCenterFragment myCenterFragment = new MyCenterFragment();
        Bundle bundle = new Bundle();
        myCenterFragment.setArguments(bundle);
        return myCenterFragment;
    }
    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_mycenterfragment_layout;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.home_fake_status_bar));

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void setClickListener(View view) {

    }
}
