package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.mycenter.model.ApplyRefundDate;
import com.goulala.xiayun.mycenter.model.ShopItemMessage;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/19
 * function  : 店铺的数据信息，包含商品的信息
 */
public class ShopListMessageAdapter extends BaseQuickAdapter<ShopItemMessage, BaseViewHolder> {
    public ShopListMessageAdapter(@Nullable List<ShopItemMessage> data) {
        super(R.layout.include_shop_list_message_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopItemMessage item) {

        GoodItemMessage goodItemMessage = item.getItem();
        if (goodItemMessage == null) return;
        ImageLoaderUtils.displayGoods(goodItemMessage.getSmallimage(), (ImageView) helper.getView(R.id.civ_good_photo));
        helper.setText(R.id.tv_good_name, goodItemMessage.getName())
                .setText(R.id.tv_good_specifications, goodItemMessage.getSpecification())
                .setText(R.id.tv_good_price, mContext.getString(R.string.the_price, item.getUnit_price()+""))
                .setText(R.id.tv_good_number, "x" + item.getNum());
        List<ApplyRefundDate> applyRefundDate = item.getApply(); //是否申请了售后
        if (applyRefundDate != null && applyRefundDate.size() > 0) {
            helper.setVisible(R.id.tv_Refund_status, true)
                    .setText(R.id.tv_Refund_status, applyRefundDate.get(0).getLast_introduce());
        } else {
            helper.setVisible(R.id.tv_Refund_status, false);
        }
    }
}
