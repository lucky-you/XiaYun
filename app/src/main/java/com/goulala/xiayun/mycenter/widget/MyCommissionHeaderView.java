package com.goulala.xiayun.mycenter.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.mycenter.activity.PaymentDetailsActivity;
import com.goulala.xiayun.mycenter.activity.TheWithdrawalActivity;

/**
 * @author : Z_B
 * @date : 2018/8/16
 * @function : 我的佣金的头部布局
 */
public class MyCommissionHeaderView extends FrameLayout implements View.OnClickListener {
    private TextView tvMyCommissionMoney;
    private TextView tvWithdrawal;
    private TextView tvCheckMoreItem;
    private Context mContext;

    public MyCommissionHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public MyCommissionHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCommissionHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View headerView = View.inflate(context, R.layout.include_my_commission_header_view, this);
        initViews(headerView);

    }

    private void initViews(View headerView) {
        tvMyCommissionMoney = headerView.findViewById(R.id.tv_my_commission_money);
        tvWithdrawal = headerView.findViewById(R.id.tv_withdrawal);
        tvWithdrawal.setOnClickListener(this);
        tvCheckMoreItem = headerView.findViewById(R.id.tv_check_more_item);
        tvCheckMoreItem.setOnClickListener(this);
    }


    /**
     * 设置余额
     *
     * @param balance
     */
    public void setBalance(String balance) {
        if (!TextUtils.isEmpty(balance)) {
            tvMyCommissionMoney.setText(mContext.getString(R.string.the_price, balance));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_withdrawal:
                //提现
                TheWithdrawalActivity.start(mContext);
                break;
            case R.id.tv_check_more_item:
                //更多收支明细
                PaymentDetailsActivity.start(mContext);
                break;
        }
    }
}
