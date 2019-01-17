package com.goulala.xiayun.common.activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseActivity;
import com.goulala.xiayun.common.fragment.FragmentController;
import com.goulala.xiayun.common.lib.ActivityManager;
import com.goulala.xiayun.common.utils.StatusBarUtil;

public class MainActivity extends BaseActivity {


    private FragmentController controller;
    private long exitTime;
    private LinearLayout llBottom;
    private TextView tvMessageNumber;
    private View lastSelected;
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void bindViews(View contentView) {
        llBottom = get(R.id.llBottom);
        tvMessageNumber = get(R.id.tvMessageNumber);
        StatusBarUtil.setStatusBar(this, false, false);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        instance = this;
        controller = FragmentController.getInstance(this, R.id.container, true);
        initBottomTab();
    }

    private void initBottomTab() {
        for (int i = 0; i < llBottom.getChildCount(); i++) {
            View tab = llBottom.getChildAt(i);
            if (i == 0) {
                setSelect(tab, 0);
            }
            tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelect(v, llBottom.indexOfChild(v));
                }
            });
        }
    }

    private void setSelect(View tab, int position) {
        if (lastSelected != null)
            lastSelected.setSelected(false);
        tab.setSelected(true);
        lastSelected = tab;
        controller.showFragment(position);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(mContext.getString(R.string.Press_exit_procedure_again));
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManager.getAppInstance().finishAllActivity();
        }
    }

    @Override
    public void setClickListener(View view) {

    }
}
