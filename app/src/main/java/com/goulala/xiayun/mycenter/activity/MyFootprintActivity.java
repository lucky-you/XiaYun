package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.adapter.HomeViewPageAdapter;
import com.goulala.xiayun.common.base.BaseActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.mycenter.fragment.MyFootprintFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的足迹
 */
public class MyFootprintActivity extends BaseActivity {

    private SlidingTabLayout withdrawalSubsidiaryTabLayout;
    private ViewPager withdrawalSubsidiaryViewPager;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyFootprintActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_my_footprint;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        withdrawalSubsidiaryTabLayout = get(R.id.withdrawal_subsidiary_tabLayout);
        withdrawalSubsidiaryViewPager = get(R.id.withdrawal_subsidiary_viewPager);
        initTitle(mContext.getString(R.string.My_footprint))
                .setRightText(mContext.getString(R.string.the_empty))
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String hitMessage = mContext.getString(R.string.Are_you_sure_you_want_to_clear_all_records);
                        showDeleteDialog(hitMessage);

                    }
                });
    }

    /**
     * 清空足迹
     */
    private void showDeleteDialog(String message) {
        AlertDialogUtils.showCustomerDialog(mContext, message, new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {
            }

            @Override
            public void determineClick(View view) {
                EventBus.getDefault().post(new Notice(ConstantValue.EMPTY_THAT_FOOTPRINT_GOOD_TYPE));
            }
        });

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        String[] titles = mContext.getResources().getStringArray(R.array.my_collection_titles);
        List<Fragment> mFragmentList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mFragmentList.add(MyFootprintFragment.newInstance(i));
        }
        HomeViewPageAdapter homeSecondGoodViewPagerAdapter = new HomeViewPageAdapter(getSupportFragmentManager(), mFragmentList, titles);
        withdrawalSubsidiaryViewPager.setAdapter(homeSecondGoodViewPagerAdapter);
        withdrawalSubsidiaryTabLayout.setViewPager(withdrawalSubsidiaryViewPager);
    }

    @Override
    public void setClickListener(View view) {

    }
}
