package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.ToastUtils;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.mycenter.model.ApplyRefundDate;
import com.goulala.xiayun.mycenter.model.ShopItemMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/21
 * function : 订单详情的商品的adapter
 */
public class TheOrderDetailsGoodItemAdapter extends BaseQuickAdapter<ShopItemMessage, BaseViewHolder> {


    public TheOrderDetailsGoodItemAdapter(@Nullable List<ShopItemMessage> data) {
        super(R.layout.include_order_details_good_item_view, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ShopItemMessage item) {
        GoodItemMessage shopGoodMessage = item.getItem(); //店铺下面对应的店铺信息
        if (shopGoodMessage == null) return;
        ImageLoaderUtils.displayGoods(shopGoodMessage.getSmallimage(), (ImageView) helper.getView(R.id.iv_good_imageView));
        helper.setText(R.id.tv_good_name, shopGoodMessage.getName())
                .setText(R.id.tv_good_specifications, shopGoodMessage.getSpecification())
                .setText(R.id.tv_good_price, mContext.getString(R.string.the_price, item.getUnit_price() + ""))
                .setText(R.id.tv_good_number, "x" + item.getNum())
                .setText(R.id.tv_Commodity_prices, mContext.getString(R.string.the_price, item.getMoney() + ""))
                .setText(R.id.tv_Express_freight_two, mContext.getString(R.string.the_price, item.getFreight() + ""))
                .setText(R.id.tv_Share_activity_preferences_money, mContext.getString(R.string.the_price, item.getDiscount_money() + ""))
                .setText(R.id.tv_Share_coupons_price, mContext.getString(R.string.the_price, item.getCoupon_money() + ""))
                .setText(R.id.tv_The_payment_amount_money, mContext.getString(R.string.the_price, item.getPay_money() + ""))
                .setText(R.id.tv_Buyer_message_two, mContext.getString(R.string.Buyer_message_two, item.getRemark()));

        final List<ApplyRefundDate> applyRefundDateList = item.getApply(); //是否申请了售后
        if (applyRefundDateList != null && applyRefundDateList.size() > 0) {
            //申请了售后,此时点击按钮，进入对应的售后详情
            switch (item.getStatus()) { //订单状态
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID:
                    //待支付
                    helper.setGone(R.id.tv_Apply_for_after_sales, false);
                    break;
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD:
                    //待发货状态下，申请售后就是申请退款
                    helper.setGone(R.id.tv_Apply_for_after_sales, true)
                            .setText(R.id.tv_Apply_for_after_sales, applyRefundDateList.get(0).getLast_introduce())
                            .setTextColor(R.id.tv_Apply_for_after_sales, mContext.getResources().getColor(R.color.color_e53d3d))
                            .setBackgroundRes(R.id.tv_Apply_for_after_sales, R.drawable.shape_red_1dp_background)
                            .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //申请退款
                            if (0 == item.getAfter_status()) { //正常状态下，可以申请退款
                                EventBus.getDefault().post(new Notice(ConstantValue.APPLY_FOR_REFUND_MONEY_OF_NOT_SEND_GOOD_TYPE, applyRefundDateList.get(0).getService_no()));
                            } else if (1 == item.getAfter_status() && applyRefundDateList.get(0).getLast_status() == ApiParam.THAT_SHUT_DOWN_TYPE) {
                                ToastUtils.showToast(mContext.getString(R.string.The_order_is_currently_unavailable_for_a_refund));
                            }
                        }
                    });
                    break;
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD: //待收货状态下，申请售后分为退款退货两种情况
                    if (1 == item.getAfter_status() && applyRefundDateList.get(0).getLast_status() == ApiParam.THAT_SHUT_DOWN_TYPE) {
                        helper.setText(R.id.tv_Apply_for_after_sales, mContext.getString(R.string.Apply_for_after_sales));
                    } else {
                        helper.setText(R.id.tv_Apply_for_after_sales, applyRefundDateList.get(0).getLast_introduce());
                    }
                    helper.setGone(R.id.tv_Apply_for_after_sales, true)
                            .setTextColor(R.id.tv_Apply_for_after_sales, mContext.getResources().getColor(R.color.color_e53d3d))
                            .setBackgroundRes(R.id.tv_Apply_for_after_sales, R.drawable.shape_red_1dp_background)
                            .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (0 == item.getAfter_status()) { //正常状态下，可以申请售后
                                switch (applyRefundDateList.get(0).getType()) {
                                    case 1://退款
                                        EventBus.getDefault().post(new Notice(ConstantValue.APPLY_FOR_REFUND_MONEY_DETAILS_TYPE, applyRefundDateList.get(0).getService_no()));
                                        break;
                                    case 2: //退货
                                        EventBus.getDefault().post(new Notice(ConstantValue.APPLY_FOR_REFUND_GOOD_DETAILS_TYPE, applyRefundDateList.get(0).getService_no()));
                                        break;
                                }
                            } else if (1 == item.getAfter_status() && applyRefundDateList.get(0).getLast_status() == ApiParam.THAT_SHUT_DOWN_TYPE) {
                                ToastUtils.showToast(mContext.getString(R.string.This_order_cannot_be_applied_for_after_sale_at_present));
                            }
                        }
                    });
                    break;
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_DEAL:   //交易完成
                    if (1 == item.getAfter_status() && applyRefundDateList.get(0).getLast_status() == ApiParam.THAT_SHUT_DOWN_TYPE) {
                        helper.setText(R.id.tv_Apply_for_after_sales, mContext.getString(R.string.Apply_for_after_sales));
                    } else {
                        helper.setText(R.id.tv_Apply_for_after_sales, applyRefundDateList.get(0).getLast_introduce());
                    }
                    helper.setGone(R.id.tv_Apply_for_after_sales, true)
                            .setTextColor(R.id.tv_Apply_for_after_sales, mContext.getResources().getColor(R.color.color_e53d3d))
                            .setBackgroundRes(R.id.tv_Apply_for_after_sales, R.drawable.shape_red_1dp_background)
                            .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (0 == item.getAfter_status()) { //正常状态下
                                switch (applyRefundDateList.get(0).getType()) {
                                    case 1://退款
                                        EventBus.getDefault().post(new Notice(ConstantValue.APPLY_FOR_REFUND_MONEY_DETAILS_TYPE, applyRefundDateList.get(0).getService_no()));
                                        break;
                                    case 2: //退货
                                        EventBus.getDefault().post(new Notice(ConstantValue.APPLY_FOR_REFUND_GOOD_DETAILS_TYPE, applyRefundDateList.get(0).getService_no()));
                                        break;
                                }
                            } else if (1 == item.getAfter_status() && applyRefundDateList.get(0).getLast_status() == ApiParam.THAT_SHUT_DOWN_TYPE) {
                                ToastUtils.showToast(mContext.getString(R.string.This_order_cannot_be_applied_for_after_sale_at_present));
                            }
                        }
                    });
                    break;
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED:
                    //交易失败
                    helper.setGone(R.id.tv_Apply_for_after_sales, true)
                            .setText(R.id.tv_Apply_for_after_sales, applyRefundDateList.get(0).getLast_introduce())
                            .setTextColor(R.id.tv_Apply_for_after_sales, mContext.getResources().getColor(R.color.color_e53d3d))
                            .setBackgroundRes(R.id.tv_Apply_for_after_sales, R.drawable.shape_red_1dp_background)
                            .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (applyRefundDateList.get(0).getIs_sale()) { //是否售后
                                case 1: //不是售后--》待发货状态
                                    EventBus.getDefault().post(new Notice(ConstantValue.APPLY_FOR_REFUND_MONEY_OF_NOT_SEND_GOOD_TYPE, applyRefundDateList.get(0).getService_no()));
                                    break;
                                case 2: //是售后--》待收货状态
                                    switch (applyRefundDateList.get(0).getType()) {
                                        case 1://退款
                                            EventBus.getDefault().post(new Notice(ConstantValue.APPLY_FOR_REFUND_MONEY_DETAILS_TYPE, applyRefundDateList.get(0).getService_no()));
                                            break;
                                        case 2: //退货
                                            EventBus.getDefault().post(new Notice(ConstantValue.APPLY_FOR_REFUND_GOOD_DETAILS_TYPE, applyRefundDateList.get(0).getService_no()));
                                            break;
                                    }
                                    break;
                            }
                        }
                    });
                    break;
                case ApiParam.THE_ORDER_STATUS_TYPE_COMPLETED://交易完成-->彻底的完成
                    helper.setGone(R.id.tv_Apply_for_after_sales, true)
                            .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showToast(mContext.getText(R.string.This_order_cannot_be_applied_for_after_sales_service));
                        }
                    });
                    break;
            }
        } else {
            //没有申请售后
            switch (item.getStatus()) { //订单状态
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID:
                    //待支付
                    helper.setGone(R.id.tv_Apply_for_after_sales, false);
                    break;
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD:
                    //待发货状态下，申请售后就是申请退款
                    helper.setGone(R.id.tv_Apply_for_after_sales, false)
                            .setText(R.id.tv_Apply_for_after_sales, mContext.getString(R.string.Refund_application))
                            .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //申请退款
                            String shopOrderNumber = item.getShop_order();
                            EventBus.getDefault().post(new Notice(ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE, shopOrderNumber));
                        }
                    });
                    break;
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD:
                    //待收货状态下，申请售后分为退款退货两种情况
                    if (0 == item.getAfter_status()) { //正常状态下，可以申请售后
                        helper.setGone(R.id.tv_Apply_for_after_sales, true)
                                .setText(R.id.tv_Apply_for_after_sales, mContext.getString(R.string.Apply_for_after_sales))
                                .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //申请售后
                                String shopOrder = item.getShop_order();
                                String itemOrder = item.getItem_order();
                                String itemNumber = item.getNum() + "";

                                EventBus.getDefault().post(new Notice(
                                        ConstantValue.THAT_USE_APPLY_AFTER_SALES_TYPE,
                                        shopOrder,
                                        itemOrder,
                                        itemNumber
                                ));
                            }
                        });
                    } else if (1 == item.getAfter_status() && applyRefundDateList.get(0).getLast_status() == ApiParam.THAT_SHUT_DOWN_TYPE) {
                        //无法申请售后
                        helper.setGone(R.id.tv_Apply_for_after_sales, true)
                                .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtils.showToast(mContext.getString(R.string.This_order_cannot_be_applied_for_after_sale_at_present));
                            }
                        });
                    }
                    break;
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_DEAL://交易完成

                    helper.setGone(R.id.tv_Apply_for_after_sales, true)
                            .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //申请售后
                            String shopOrder = item.getShop_order();
                            String itemOrder = item.getItem_order();
                            String itemNumber = item.getNum() + "";
                            EventBus.getDefault().post(new Notice(
                                    ConstantValue.THAT_USE_APPLY_AFTER_SALES_TYPE,
                                    shopOrder,
                                    itemOrder,
                                    itemNumber
                            ));
                        }
                    });
                    break;
                case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED:
                    //交易失败
                    helper.setGone(R.id.tv_Apply_for_after_sales, false);
                    break;

                case ApiParam.THE_ORDER_STATUS_TYPE_COMPLETED://交易完成-->彻底的完成
                    helper.setGone(R.id.tv_Apply_for_after_sales, true)
                            .getView(R.id.tv_Apply_for_after_sales).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showToast(mContext.getText(R.string.This_order_cannot_be_applied_for_after_sales_service));
                        }
                    });
                    break;
            }
        }
    }
}
