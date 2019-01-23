package com.goulala.xiayun.home.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.mycenter.model.PaymentDetailsList;

import java.text.NumberFormat;
import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/13
 * @function : 提现明细的adapter
 */
public class WithdrawalSubsidiaryAdapter extends BaseQuickAdapter<PaymentDetailsList, BaseViewHolder> {
    public static final int FRAGMENT_ADAPTER_TYPE_ONE = 0; //签到金明细
    public static final int FRAGMENT_ADAPTER_TYPE_TWO = 1; //提现明细
    public static final int FRAGMENT_ADAPTER_TYPE_THREE = 2; //我的佣金
    public static final int FRAGMENT_ADAPTER_TYPE_FOUR = 3; //收支明细
    private int adapter_type;

    public WithdrawalSubsidiaryAdapter(@Nullable List<PaymentDetailsList> data, int type) {
        super(R.layout.include_withdrawal_subsidiary_item_view, data);
        this.adapter_type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, PaymentDetailsList item) {

        helper.setText(R.id.tv_withdrawal_name, item.getRemark())
                .setText(R.id.tv_withdrawal_time, DateUtils.getStrTime(item.getCreatetime()));
        switch (adapter_type) {
            case FRAGMENT_ADAPTER_TYPE_ONE: //签到金明细
                helper.setText(R.id.tv_Sign_in_gold, mContext.getString(R.string.Sign_in_gold, item.getRemark()));
                break;

            case FRAGMENT_ADAPTER_TYPE_TWO: // 提现明细:
                break;
            case FRAGMENT_ADAPTER_TYPE_THREE: // 我的佣金
            case FRAGMENT_ADAPTER_TYPE_FOUR: //收支明细
                double money = item.getMoney();
                NumberFormat numberFormat = NumberFormat.getInstance();
                switch (item.getType()) {
                    case 1:
                        helper.setText(R.id.tv_Sign_in_gold, mContext.getString(R.string.Sign_in_gold, numberFormat.format(money) + ""))
                                .setTextColor(R.id.tv_Sign_in_gold, Color.parseColor("#0caf33"));
                        break;
                    case 2:
                    case 3:
                        helper.setText(R.id.tv_Sign_in_gold, mContext.getString(R.string.Sign_in_gold_two, numberFormat.format(money) + ""))
                                .setTextColor(R.id.tv_Sign_in_gold, Color.parseColor("#e53d3d"));
                        break;
                }
                break;
        }
    }
}
