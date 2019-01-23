package com.goulala.xiayun.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/13
 * @function : 提现专区的adapter
 */
public class WithdrawalZoneAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public WithdrawalZoneAdapter(@Nullable List<String> data) {
        super(R.layout.include_withdrawal_zone_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_Full_availability, mContext.getString(R.string.Full_availability, item))
                .setText(R.id.tv_coupons_money, mContext.getString(R.string.total_money, item))
                .setText(R.id.tv_Sign_in_gold_money, mContext.getString(R.string.Sign_in_gold_money, item));

    }
}
