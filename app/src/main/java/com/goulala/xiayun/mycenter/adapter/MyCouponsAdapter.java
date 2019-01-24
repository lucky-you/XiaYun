package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.mycenter.model.CouponList;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/18
 * @function : 我的优惠券的adapter
 */
public class MyCouponsAdapter extends BaseQuickAdapter<CouponList, BaseViewHolder> {

    public int mCouponsType;

    public MyCouponsAdapter(@Nullable List<CouponList> data, int couponsType) {
        super(R.layout.include_my_coupons_item_view, data);
        this.mCouponsType = couponsType;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponList item) {
        String startTime = DateUtils.getStrTimeTwo(item.getStarttime());
        String endTime = DateUtils.getStrTimeTwo(item.getExpiretime());
        String couponTime = startTime + "-" + endTime;
        String use_trade_amount = item.getUse_trade_amount();
        String discount_amount = item.getDiscount_amount();
        String FullPriceReduction = use_trade_amount.split("\\.")[0];
        String PreferentialPrice = discount_amount.split("\\.")[0];//这么做的目的是为了去掉金额后面的.00，只是用单纯的数字

        helper.setText(R.id.tv_Full_availability, mContext.getString(R.string.Full_availability, FullPriceReduction))
                .setText(R.id.tv_coupons_money, PreferentialPrice)
                .setText(R.id.tv_The_period_of_validity, mContext.getString(R.string.The_period_of_validity, couponTime));

        if (mCouponsType == ConstantValue.COUPONS_AVAILABLE_TYPE) {
            //可用优惠券
            helper.setVisible(R.id.tv_Coupons_unavailable, false);
            if (Double.parseDouble(discount_amount) > 50.00) {
                helper.setBackgroundRes(R.id.ll_coupons_background_layout, R.drawable.ic_coupon_available_red_background);
            } else {
                helper.setBackgroundRes(R.id.ll_coupons_background_layout, R.drawable.ic_coupon_available_blue_background);
            }
        } else {
            //不可用优惠券
            helper.setBackgroundRes(R.id.ll_coupons_background_layout, R.drawable.ic_coupon_unavailable_greybackground)
                    .setVisible(R.id.tv_Coupons_unavailable, true)
                    .setText(R.id.tv_Coupons_unavailable, item.getExpire_desc());
        }
    }
}
