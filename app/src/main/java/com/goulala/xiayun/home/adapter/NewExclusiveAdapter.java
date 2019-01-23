package com.goulala.xiayun.home.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.home.model.CouponsList;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/14
 * @function : 新人专享的adapter
 */
public class NewExclusiveAdapter extends BaseQuickAdapter<CouponsList, BaseViewHolder> {
    public NewExclusiveAdapter(@Nullable List<CouponsList> data) {
        super(R.layout.include_new_exclusive_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponsList item) {

        String amount = item.getDiscount_amount();
        String amountPrice = amount.split("\\.")[0];//这么做的目的是为了去掉金额后面的.00，只使用单纯的数字
        helper.setText(R.id.tv_coupons_money, amountPrice + "")
                .setText(R.id.tv_Full_availability, item.getUse_desc())
                .setText(R.id.tv_brand,item.getBrand_desc())
                .setText(R.id.tv_Validity_delay_time,item.getExpire_desc());
        // .setVisible(R.id.iv_is_received, item.getGet_status() == 2);//[ get_status 1: 未领取 2:已经领取]
        TextView tv_Immediately_to_receive =  helper.getView(R.id.tv_Immediately_to_receive);
        if (item.getGet_status() == 2){
            helper.setText(R.id.tv_Immediately_to_receive, mContext.getString(R.string.Already_received));
            tv_Immediately_to_receive.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f));
        }else{
            switch (item.getGet_mode()) {//[get_mode 1:立即领取 2:下单返]
                case 1:
                    helper.setText(R.id.tv_Immediately_to_receive, mContext.getString(R.string.Immediately_to_receive));

                    break;
                case 2:
                    helper.setText(R.id.tv_Immediately_to_receive, mContext.getString(R.string.Place_the_order_or_return));
                    break;
            }
            tv_Immediately_to_receive.setTextColor(mContext.getResources().getColor(R.color.font_red));
        }

    }
}
