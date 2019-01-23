package com.goulala.xiayun.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseActivity;
import com.goulala.xiayun.common.view.NoticeMarqueeView;
import com.goulala.xiayun.home.adapter.ActivityRulesAdapter;
import com.goulala.xiayun.home.model.MarqueeViewList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 会员签到
 */
public class MemberSignInActivity extends BaseActivity {

    private NoticeMarqueeView signInMarqueeView;
    private TextView tvWithdrawalMoney;
    private TextView tvHaveSignForCash;
    private TextView tvMoreWaysToMakeMoneyWillOpenUp;
    private RecyclerView ActivityRulesRecyclerView;
    //签到的dialog
    private MemberSignInDialog memberSignInDialog;
    private ActivityRulesAdapter activityRulesAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MemberSignInActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_member_sign_in);

        signInMarqueeView = get(R.id.sign_in_marqueeView);
        tvWithdrawalMoney = get(R.id.tv_Withdrawal_money);
        tvHaveSignForCash = get(R.id.tv_have_Sign_for_cash);
        tvHaveSignForCash.setOnClickListener(this);
        tvMoreWaysToMakeMoneyWillOpenUp = get(R.id.tv_More_ways_to_make_money_will_open_up);
        ActivityRulesRecyclerView = get(R.id.Activity_Rules_RecyclerView);
        get(R.id.tv_Withdrawal_way).setOnClickListener(this);


    }

    @Override
    protected void bindViews() {
        initTitle(mContext.getString(R.string.Sign_in_for_cash));

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        List<MarqueeViewList> marqueeViewLists = new ArrayList<>();
        marqueeViewLists.add(new MarqueeViewList("5"));
        marqueeViewLists.add(new MarqueeViewList("15"));
        marqueeViewLists.add(new MarqueeViewList("25"));
        marqueeViewLists.add(new MarqueeViewList("35"));
        marqueeViewLists.add(new MarqueeViewList("45"));

        signInMarqueeView.addNotice(marqueeViewLists);
        signInMarqueeView.startFlipping();
        List<String> mRulesList = Arrays.asList(
                "1.每天签到可领取签到金",
                "2.连续3日签到可获得额外的签到奖励",
                "3.签到金可提现可兑换等额的优惠券",
                "4.提现兑换发起后，存在审核环节"
        );

        activityRulesAdapter = new ActivityRulesAdapter(mRulesList);
        ActivityRulesRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        ActivityRulesRecyclerView.setAdapter(activityRulesAdapter);


    }

    @Override
    protected void setListener() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Withdrawal_way:
                WithdrawalZoneActivity.start(mContext);
                break;
            case R.id.tv_have_Sign_for_cash:
                showSignInDialog();
                break;
        }
    }

    private void showSignInDialog() {
        memberSignInDialog = new MemberSignInDialog(mContext);
    }
}
