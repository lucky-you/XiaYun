package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.mycenter.model.VipCouponTicketList;

import java.util.List;


/**
 * author     : Z_B
 * date      : 2018/8/30
 * function : 会员中心的adapter
 */
public class DiscountTicketAdapter extends BaseQuickAdapter<VipCouponTicketList, BaseViewHolder> {


    public DiscountTicketAdapter(@Nullable List<VipCouponTicketList> data) {
        super(R.layout.include_the_discount_ticket_item_layout, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, VipCouponTicketList item) {
        String discount_amount = item.getDiscount_amount();
        String amountPrice = discount_amount.split("\\.")[0];//这么做的目的是为了去掉金额后面的.00，只使用单纯的数字
        helper.setText(R.id.tv_price, amountPrice)
                .setText(R.id.tv_brand, item.getBrand_desc())
                .setText(R.id.use_desc, item.getUse_desc())
                .setText(R.id.tv_time, item.getExpire_desc());
    }
}
