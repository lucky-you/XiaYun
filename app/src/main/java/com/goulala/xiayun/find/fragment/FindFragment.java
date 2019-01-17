package com.goulala.xiayun.find.fragment;

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
public class FindFragment extends BaseFragment {

    public static FindFragment newInstance() {
        FindFragment findFragment = new FindFragment();
        Bundle bundle = new Bundle();
        findFragment.setArguments(bundle);
        return findFragment;
    }
    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_findfragment_layout;
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
