package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.adapter.HomeViewPageAdapter;
import com.goulala.xiayun.common.base.BaseActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.SizeUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.mycenter.fragment.GoodsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部订单
 */
public class AllOrdersActivity extends BaseActivity {


    private SlidingTabLayout withdrawalSubsidiaryTabLayout;
    private View DivDeLine;
    private ViewPager withdrawalSubsidiaryViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private int tabLayoutIndex;

    public static void start(Context context, int index) {
        Intent intent = new Intent(context, AllOrdersActivity.class);
        intent.putExtra(ConstantValue.INDEX, index);
        context.startActivity(intent);
    }


    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_all_orders;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.all_the_orders));
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        withdrawalSubsidiaryTabLayout = get(R.id.withdrawal_subsidiary_tabLayout);
        DivDeLine = get(R.id.DivDe_Line);
        withdrawalSubsidiaryViewPager = get(R.id.withdrawal_subsidiary_viewPager);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        tabLayoutIndex = getIntent().getIntExtra(ConstantValue.INDEX, -1);
        String[] allOrderTitles = mContext.getResources().getStringArray(R.array.all_orders_titles);
        for (int i = 0; i < allOrderTitles.length; i++) {
            mFragments.add(GoodsListFragment.newInstance(i));
        }
        HomeViewPageAdapter homeSecondGoodViewPagerAdapter = new HomeViewPageAdapter(getSupportFragmentManager(), mFragments, allOrderTitles);
        withdrawalSubsidiaryViewPager.setAdapter(homeSecondGoodViewPagerAdapter);
        withdrawalSubsidiaryTabLayout.setViewPager(withdrawalSubsidiaryViewPager);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) DivDeLine.getLayoutParams();
        layoutParams.height = SizeUtils.dp2px( 12);
        DivDeLine.setLayoutParams(layoutParams);
        withdrawalSubsidiaryTabLayout.setCurrentTab(tabLayoutIndex);
    }

    @Override
    public void setClickListener(View view) {

    }
}
