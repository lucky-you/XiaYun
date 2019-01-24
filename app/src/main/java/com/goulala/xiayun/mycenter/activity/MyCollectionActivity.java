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
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.view.TitleBuilder;
import com.goulala.xiayun.mycenter.fragment.MyCollectionFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 */
public class MyCollectionActivity extends BaseActivity {

    private SlidingTabLayout withdrawalSubsidiaryTabLayout;
    private ViewPager withdrawalSubsidiaryViewPager;
    private TitleBuilder titleBarLayout;
    private boolean isShowEditButton; //是佛显示右上角的编辑按钮

    public static void start(Context context) {
        Intent intent = new Intent(context, MyCollectionActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_my_collection_and_footprint;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        withdrawalSubsidiaryTabLayout = get(R.id.withdrawal_subsidiary_tabLayout);
        withdrawalSubsidiaryViewPager = get(R.id.withdrawal_subsidiary_viewPager);
        titleBarLayout = initTitle(mContext.getString(R.string.My_collection))
                .setRightText(mContext.getString(R.string.The_editor))
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isShowEditButton) {
                            titleBarLayout.setRightText(mContext.getString(R.string.complete));
                            isShowEditButton = !isShowEditButton;
                            //显示删除的checkBox,需要告诉fragment中的adapter
                            EventBus.getDefault().post(new Notice(ConstantValue.IS_SHOW_DELETE_BUTTON, true));
                        } else {
                            titleBarLayout.setRightText(mContext.getString(R.string.The_editor));
                            isShowEditButton = !isShowEditButton;
                            EventBus.getDefault().post(new Notice(ConstantValue.IS_SHOW_DELETE_BUTTON, false));
                        }
                    }
                });

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        String[] titles = mContext.getResources().getStringArray(R.array.my_collection_titles);
        List<Fragment> mFragmentList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mFragmentList.add(MyCollectionFragment.newInstance(i));
        }
        HomeViewPageAdapter homeSecondGoodViewPagerAdapter = new HomeViewPageAdapter(getSupportFragmentManager(), mFragmentList, titles);
        withdrawalSubsidiaryViewPager.setAdapter(homeSecondGoodViewPagerAdapter);
        withdrawalSubsidiaryTabLayout.setViewPager(withdrawalSubsidiaryViewPager);
    }

    @Override
    public void setClickListener(View view) {

    }
}
