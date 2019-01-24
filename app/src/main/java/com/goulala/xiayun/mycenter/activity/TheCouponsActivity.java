package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.goulala.bean.Notice;
import com.goulala.xiayun.R;
import com.goulala.xiayun.base.BaseActivity;
import com.goulala.xiayun.common.adapter.HomeViewPageAdapter;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.mycenter.fragment.MyCouponsFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * 优惠券的activity
 */
public class TheCouponsActivity extends BaseActivity {

    private SlidingTabLayout withdrawalSubsidiaryTabLayout;
    private ViewPager withdrawalSubsidiaryViewPager;
    private TextView titleLeft, titleRight;
    private int classFormType;
    private String payMoney;

    public static void start(Context context, String amount) {
        Intent intent = new Intent(context, TheCouponsActivity.class);
        intent.putExtra(ConstantValue.AMOUNT, amount);
        context.startActivity(intent);
    }


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_the_coupons);
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        registerEvent();
        withdrawalSubsidiaryTabLayout = get(R.id.withdrawal_subsidiary_tabLayout);
        withdrawalSubsidiaryViewPager = get(R.id.withdrawal_subsidiary_viewPager);
    }


    @Override
    protected void bindViews() {
        initTitle(mContext.getString(R.string.My_coupons))
                .setRightText(mContext.getString(R.string.Directions_for_use))
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DirectionsForUseActivity.start(mContext, ConstantValue.COUPON_DESCRIPTION_TYPE);
                    }
                });
    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String[] titles = mContext.getResources().getStringArray(R.array.My_coupon_title);
        classFormType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
        payMoney = getIntent().getStringExtra(ConstantValue.MONEY);
        List<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mFragments.add(MyCouponsFragment.newInstance(i, classFormType, payMoney));
        }
        HomeViewPageAdapter homeSecondGoodViewPagerAdapter = new HomeViewPageAdapter(getSupportFragmentManager(), mFragments, titles);
        withdrawalSubsidiaryViewPager.setAdapter(homeSecondGoodViewPagerAdapter);
        withdrawalSubsidiaryTabLayout.setViewPager(withdrawalSubsidiaryViewPager);
        withdrawalSubsidiaryTabLayout.setIndicatorWidth(43);
        titleLeft = withdrawalSubsidiaryTabLayout.getTitleView(0);
        titleRight = withdrawalSubsidiaryTabLayout.getTitleView(1);

    }

    @Subscribe
    public void onEvent(Notice notice) {
        if (notice != null) {
            switch (notice.type) {
                case ConstantValue.COUPONS_AVAILABLE_TYPE:
                    //可用优惠券
                    int couponsSize = (int) notice.contentOne;
                    titleLeft.setText(mContext.getString(R.string.Coupons_available, couponsSize + ""));
                    break;
                case ConstantValue.NOT_COUPONS_AVAILABLE_TYPE:
                    //不可用优惠券
                    int couponsSizeTwo = (int) notice.contentOne;
                    titleRight.setText(mContext.getString(R.string.Not_available_coupons, couponsSizeTwo + ""));
                    break;

            }

        }
    }


    @Override
    protected void setListener() {

    }

}
