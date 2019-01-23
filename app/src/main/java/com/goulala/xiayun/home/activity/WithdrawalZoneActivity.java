package com.goulala.xiayun.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.view.NoticeMarqueeView;
import com.goulala.xiayun.common.widget.DividerGridItemDecoration;
import com.goulala.xiayun.home.adapter.WithdrawalZoneAdapter;
import com.goulala.xiayun.home.model.MarqueeViewList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 提现专区的activity
 */
public class WithdrawalZoneActivity extends BaseActivity {

    private NoticeMarqueeView signInMarqueeView;
    private CircleImageView civWithdrawalPhoto;
    private TextView tvTvTheWithdrawalPeopleName;
    private TextView tvTvTheWithdrawalBalance;
    private TextView tvTheWithdrawalSubsidiary;
    private RecyclerView withdrawalZoneRecyclerView;
    private WithdrawalZoneAdapter withdrawalZoneAdapter;


    public static void start(Context context) {
        Intent intent = new Intent(context, WithdrawalZoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_withdrawal_zone;
    }


    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Withdrawal_zone));
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        signInMarqueeView = get(R.id.sign_in_marqueeView);
        civWithdrawalPhoto = get(R.id.civ_withdrawal_photo);
        tvTvTheWithdrawalPeopleName = get(R.id.tv_tv_The_withdrawal_people_name);
        tvTvTheWithdrawalBalance = get(R.id.tv_tv_The_withdrawal_balance);
        tvTheWithdrawalSubsidiary = get(R.id.tv_The_withdrawal_subsidiary);
        tvTheWithdrawalSubsidiary.setOnClickListener(this);
        withdrawalZoneRecyclerView = get(R.id.withdrawal_zone_recyclerView);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        List<String> mDateList = Arrays.asList("20", "50", "100", "500");
        withdrawalZoneAdapter = new WithdrawalZoneAdapter(mDateList);
        withdrawalZoneRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        withdrawalZoneRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, mContext.getResources().getColor(R.color.white), 7));
        withdrawalZoneRecyclerView.setAdapter(withdrawalZoneAdapter);

        List<MarqueeViewList> marqueeViewLists = new ArrayList<>();
        marqueeViewLists.add(new MarqueeViewList("5"));
        marqueeViewLists.add(new MarqueeViewList("15"));
        marqueeViewLists.add(new MarqueeViewList("25"));
        marqueeViewLists.add(new MarqueeViewList("35"));
        marqueeViewLists.add(new MarqueeViewList("45"));

        signInMarqueeView.addNotice(marqueeViewLists);
        signInMarqueeView.startFlipping();
    }


    @Override
    public void setClickListener(View view) {
        if (view.getId() == R.id.tv_The_withdrawal_subsidiary) {
            WithdrawalSubsidiaryActivity.start(mContext);
        }
    }


}
