package com.goulala.xiayun.common.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.goulala.xiayun.find.fragment.FindFragment;
import com.goulala.xiayun.home.fragment.HomeFragment;
import com.goulala.xiayun.mycenter.fragment.MyCenterFragment;
import com.goulala.xiayun.shopcar.fragment.ShopCarFragment;

import java.util.ArrayList;


/**
 * 主界面Fragment控制器
 */
public class FragmentController {

    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;
    private int position;

    private static FragmentController controller;
    private static boolean isReload;

    public static FragmentController getInstance() {
        return controller;
    }

    public static FragmentController getInstance(FragmentActivity activity, int containerId, boolean isReload) {
        FragmentController.isReload = isReload;
        if (controller == null) {
            controller = new FragmentController(activity, containerId);
        }
        return controller;
    }

    public static void onDestroy() {
        controller = null;
    }

    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        if (isReload) {
            fragments.add(HomeFragment.newInstance());
            fragments.add(FindFragment.newInstance());
            fragments.add(ShopCarFragment.newInstance());
            fragments.add(MyCenterFragment.newInstance());
            FragmentTransaction ft = fm.beginTransaction();
            for (int i = 0; i < fragments.size(); i++) {
                ft.add(containerId, fragments.get(i), "" + i);
            }
            ft.commit();
        } else {
            for (int i = 0; i < 4; i++) {
                fragments.add(fm.findFragmentByTag(i + ""));
            }
        }
    }

    public void showFragment(int position) {
        this.position = position;
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    public int getCurrentPosition() {
        return this.position;
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }
}