package com.goulala.xiayun.home.helper;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.home.model.HotStyleSecondsKill;
import com.goulala.xiayun.home.model.TimeAndStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/7
 * function  : 首页爆品秒杀的帮助类
 */
public class SecondsKillHelper {

    /**
     * 获取到时间和状态，用于tabLayout显示标题
     */
    public static List<TimeAndStatus> getTimeList(List<HotStyleSecondsKill> hotStyleSecondsKillList) {
        List<TimeAndStatus> mDateList = new ArrayList<>();
        for (int i = 0; i < hotStyleSecondsKillList.size(); i++) {
            mDateList.add(new TimeAndStatus(hotStyleSecondsKillList.get(i).getTime(), hotStyleSecondsKillList.get(i).getDesc()));
        }
        return mDateList;
    }

    /**
     * 显示tabLayout的标题
     */
    public static void setTabLayoutViews(final Context mContext, TabLayout homeGoodKillTabLayout, List<TimeAndStatus> mDateList) {
        for (int i = 0; i < mDateList.size(); i++) {
            View goodTabView = View.inflate(mContext, R.layout.include_second_kill_good_title, null);
            TextView tvSecondKillGoodTime = goodTabView.findViewById(R.id.tv_second_kill_good_time);
            TextView tvSecondKillGoodStatus = goodTabView.findViewById(R.id.tv_second_kill_good_status);
            ImageView ivSecondKillGoodImageView = goodTabView.findViewById(R.id.tv_second_kill_good_imageView);
            tvSecondKillGoodTime.setText(mDateList.get(i).time);
            tvSecondKillGoodStatus.setText(mDateList.get(i).status);
            if (i == 0) {
                tvSecondKillGoodTime.setTextColor(mContext.getResources().getColor(R.color.color_3f3f3f_black));
                tvSecondKillGoodStatus.setTextColor(mContext.getResources().getColor(R.color.color_3f3f3f_black));
                ivSecondKillGoodImageView.setVisibility(View.VISIBLE);
            } else {
                tvSecondKillGoodTime.setTextColor(mContext.getResources().getColor(R.color.color_9f9f9f_black));
                tvSecondKillGoodStatus.setTextColor(mContext.getResources().getColor(R.color.color_9f9f9f_black));
                ivSecondKillGoodImageView.setVisibility(View.INVISIBLE);
            }
            homeGoodKillTabLayout.getTabAt(i).setCustomView(goodTabView);
        }
        if (homeGoodKillTabLayout == null) return;
        homeGoodKillTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View tabView = tab.getCustomView();
                if (tabView == null) return; //之所以增加这一行，是因为在下拉刷新的时候，会出现空指针异常
                TextView tvSecondKillGoodTime = tabView.findViewById(R.id.tv_second_kill_good_time);
                TextView tvSecondKillGoodStatus = tabView.findViewById(R.id.tv_second_kill_good_status);
                ImageView ivSecondKillGoodImageView = tabView.findViewById(R.id.tv_second_kill_good_imageView);
                tvSecondKillGoodTime.setTextColor(mContext.getResources().getColor(R.color.color_3f3f3f_black));
                tvSecondKillGoodStatus.setTextColor(mContext.getResources().getColor(R.color.color_3f3f3f_black));
                ivSecondKillGoodImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View tabView = tab.getCustomView();
                if (tabView == null) return;
                TextView tvSecondKillGoodTime = tabView.findViewById(R.id.tv_second_kill_good_time);
                TextView tvSecondKillGoodStatus = tabView.findViewById(R.id.tv_second_kill_good_status);
                ImageView ivSecondKillGoodImageView = tabView.findViewById(R.id.tv_second_kill_good_imageView);
                tvSecondKillGoodTime.setTextColor(mContext.getResources().getColor(R.color.color_9f9f9f_black));
                tvSecondKillGoodStatus.setTextColor(mContext.getResources().getColor(R.color.color_9f9f9f_black));
                ivSecondKillGoodImageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
