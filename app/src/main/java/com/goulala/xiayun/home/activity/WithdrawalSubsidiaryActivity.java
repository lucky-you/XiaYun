package com.goulala.xiayun.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.goulala.xiayun.R;
import com.goulala.xiayun.base.BaseActivity;
import com.goulala.xiayun.common.adapter.HomeViewPageAdapter;
import com.goulala.xiayun.home.fragment.WithdrawalSubsidiaryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现明细
 */
public class WithdrawalSubsidiaryActivity extends BaseActivity {

    private SlidingTabLayout withdrawalSubsidiaryTabLayout;
    private ViewPager withdrawalSubsidiaryViewPager;

    public static void start(Context context) {
        Intent intent = new Intent(context, WithdrawalSubsidiaryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_withdrawal_subsidiary);
        withdrawalSubsidiaryTabLayout = get(R.id.withdrawal_subsidiary_tabLayout);
        withdrawalSubsidiaryViewPager = get(R.id.withdrawal_subsidiary_viewPager);
    }

    @Override
    protected void bindViews() {
        initTitle(mContext.getString(R.string.The_withdrawal_subsidiary));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        String[] titles = mContext.getResources().getStringArray(R.array.withdrawal_subsidiary_titles);
        List<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mFragments.add(WithdrawalSubsidiaryFragment.newInstance(i));
        }
        HomeViewPageAdapter homeSecondGoodViewPagerAdapter = new HomeViewPageAdapter(getSupportFragmentManager(), mFragments, titles);
        withdrawalSubsidiaryViewPager.setAdapter(homeSecondGoodViewPagerAdapter);
        withdrawalSubsidiaryTabLayout.setViewPager(withdrawalSubsidiaryViewPager);


    }

    @Override
    protected void setListener() {

    }

}
