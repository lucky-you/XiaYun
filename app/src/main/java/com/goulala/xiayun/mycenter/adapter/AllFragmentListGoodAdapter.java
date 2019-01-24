package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.mycenter.activity.TheOrderDetailsActivity;
import com.goulala.xiayun.mycenter.callback.OnOrderListStatusClickListener;
import com.goulala.xiayun.mycenter.model.ApplyRefundDate;
import com.goulala.xiayun.mycenter.model.OrderAddressBean;
import com.goulala.xiayun.mycenter.model.OrderDateMessage;
import com.goulala.xiayun.mycenter.model.ShopDateMessage;
import com.goulala.xiayun.mycenter.model.ShopItemMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/11/19
 * function  : 商品的adapter
 */
public class AllFragmentListGoodAdapter extends BaseQuickAdapter<OrderDateMessage, BaseViewHolder> {

    private int dateType;
    public static final int THE_DATE_TYPE_ONE = 1; //合并的数据类型(不拆分数据,全部和待支付列表使用)
    public static final int THE_DATE_TYPE_TWO = 2; //拆分数据类型(待发货，待收货，退款/售后列表使用)
    private List<OrderDateMessage> orderDateMessageList;//总数据
    private OnOrderListStatusClickListener onOrderListStatusClickListener;
    private List<ShopItemMessage> totalShopItemList;// 不拆分数据的店铺信息
    private List<ShopItemMessage> totalShopItemMessageList; //拆分数据的店铺信息
    private String expressCompanyId, expressNumber;
    private int position;

    public AllFragmentListGoodAdapter(@Nullable List<OrderDateMessage> data) {
        super(R.layout.include_good_list_item_view, data);
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
        notifyDataSetChanged();
    }

    public void setOnOrderListStatusClickListener(OnOrderListStatusClickListener onOrderListStatusClickListener) {
        this.onOrderListStatusClickListener = onOrderListStatusClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDateMessage item) {
        RecyclerView goodRecyclerView = helper.getView(R.id.public_RecyclerView);
        TextView tvOrderCreateTime = helper.getView(R.id.tv_order_number);
        TextView tvTheOrderStatus = helper.getView(R.id.tv_order_status);
        TextView tvTheTotalNumber = helper.getView(R.id.tv_total_good_number);
        TextView tvTheTotalMoney = helper.getView(R.id.tv_total_order_good_money);
        TextView tvStatusOne = helper.getView(R.id.tv_order_status_one);
        TextView tvStatusTwo = helper.getView(R.id.tv_order_status_two);
        TextView tvStatusThree = helper.getView(R.id.tv_order_status_three);
        position = helper.getAdapterPosition();
        orderDateMessageList = mData;
        switch (dateType) {
            case THE_DATE_TYPE_ONE: //不拆分数据
                totalShopItemList = notBreakUpDate(position);
                final ShopListMessageAdapter shopListMessageAdapter = new ShopListMessageAdapter(totalShopItemList);
                goodRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                goodRecyclerView.setAdapter(shopListMessageAdapter);
                tvOrderCreateTime.setText(DateUtils.getStrTime(orderDateMessageList.get(position).getCreatetime()));
                tvTheTotalNumber.setText(mContext.getString(R.string.Total_number_of_goods, orderDateMessageList.get(position).getSum_pay_num()));
                tvTheTotalMoney.setText(mContext.getString(R.string.the_total_money, orderDateMessageList.get(position).getSum_pay_money() + ""));
                int orderStatus = orderDateMessageList.get(position).getStatus(); //订单状态
                int failStatus = orderDateMessageList.get(position).getFail_status(); //在
                setBottomStatusOfOrder(THE_DATE_TYPE_ONE, orderStatus, failStatus, tvTheOrderStatus, tvStatusOne, tvStatusTwo, tvStatusThree);

                //点击商品进入详情页面
                String payNoOne = orderDateMessageList.get(position).getPay_no();
                String shopOrderOne = orderDateMessageList.get(position).getShop_order();
                String payMoney = orderDateMessageList.get(position).getSum_pay_money() + "";
                OrderAddressBean addressBean = orderDateMessageList.get(position).getShop().get(0).getAddress();
                if (addressBean != null) {
                    expressCompanyId = String.valueOf(addressBean.getExpress_company_id());//物流公司的id
                    expressNumber = addressBean.getExpress_number();//物流编号
                }
                shopGoodItemClick(shopListMessageAdapter, orderStatus, payNoOne, shopOrderOne);
                //底部按钮的监听
                setOrderStatusClickOfView(orderStatus, position,
                        tvStatusOne, tvStatusTwo,
                        tvStatusThree, payMoney, payNoOne, shopOrderOne,
                        expressCompanyId, expressNumber
                );
                break;
            case THE_DATE_TYPE_TWO://拆分数据
                final List<ShopDateMessage> totalShopItemListTwo = BreakUpDate();
                totalShopItemMessageList = totalShopItemListTwo.get(position).getList();
                final ShopListMessageAdapter shopListMessageAdapterTwo = new ShopListMessageAdapter(totalShopItemMessageList);
                goodRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                goodRecyclerView.setAdapter(shopListMessageAdapterTwo);
                tvOrderCreateTime.setText(DateUtils.getStrTime(orderDateMessageList.get(position).getCreatetime()));
                tvTheTotalNumber.setText(mContext.getString(R.string.Total_number_of_goods, totalShopItemListTwo.get(position).getSum_pay_num() + ""));
                tvTheTotalMoney.setText(mContext.getString(R.string.the_total_money, totalShopItemListTwo.get(position).getSum_pay_money() + ""));
                int orderStatusTwo = totalShopItemListTwo.get(position).getStatus(); //订单状态
                int failStatusTwo = totalShopItemListTwo.get(position).getFail_status();
                setBottomStatusOfOrder(THE_DATE_TYPE_TWO, orderStatusTwo, failStatusTwo, tvTheOrderStatus, tvStatusOne, tvStatusTwo, tvStatusThree);

                //点击商品进入详情页面
                String payNoTwo = totalShopItemListTwo.get(position).getPay_no();
                String shopOrderTwo = totalShopItemListTwo.get(position).getShop_order();
                String payMoneyTwo = totalShopItemListTwo.get(position).getSum_pay_money() + "";
                int expressCompanyIdTwo = totalShopItemListTwo.get(position).getAddress().getExpress_company_id();//物流公司的id
                String expressNumberTwo = totalShopItemListTwo.get(position).getAddress().getExpress_number();//物流编号

                shopGoodItemClick(shopListMessageAdapterTwo, orderStatusTwo, payNoTwo, shopOrderTwo);

                //底部按钮的监听
                setOrderStatusClickOfView(orderStatusTwo, position,
                        tvStatusOne, tvStatusTwo,
                        tvStatusThree, payMoneyTwo, payNoTwo, shopOrderTwo,
                        String.valueOf(expressCompanyIdTwo), expressNumberTwo
                );
                break;

        }
    }

    /**
     * 商品的监听---点击之后进入详情界面
     */
    private void shopGoodItemClick(BaseQuickAdapter baseQuickAdapter, final int orderStatus, final String payNo, final String shopOrder) {
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TheOrderDetailsActivity.start(mContext, payNo, shopOrder);
            }
        });
    }

    /**
     * 根据状态显示底部按钮
     */
    private void setBottomStatusOfOrder(int dateType, int orderStatus, int failStatus, TextView tvTheOrderStatus, TextView tvStatusOne, TextView tvStatusTwo, TextView tvStatusThree) {
        switch (orderStatus) {
            /**
             * 订单状态 -1 删除 0 待付款 1待发货（退款中）  2 待收货 （退款退货） 3 确认收货 （退款退货）4 交易失败 (交易取消  超时关闭  主动取消) 5 交易完成
             */
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID: //待支付
                tvTheOrderStatus.setText(mContext.getString(R.string.To_be_paid));
                tvStatusOne.setText(mContext.getString(R.string.Cancel_the_order));
                tvStatusTwo.setText(mContext.getString(R.string.Contact_customer_service));
                tvStatusThree.setText(mContext.getString(R.string.Immediate_payment));
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD://待发货
                tvTheOrderStatus.setText(mContext.getString(R.string.To_send_the_goods));
                switch (dateType) {
                    case THE_DATE_TYPE_ONE:
                        //不拆分数据
                        if (totalShopItemList != null && totalShopItemList.size() > 0) {
                            for (int i = 0; i < totalShopItemList.size(); i++) {
                                List<ApplyRefundDate> applyRefundList = totalShopItemList.get(i).getApply();
                                if (applyRefundList != null && applyRefundList.size() > 0) {
                                    //申请了售后
                                    tvStatusOne.setText(mContext.getString(R.string.Contact_customer_service));
                                    tvStatusTwo.setVisibility(View.GONE);
                                    tvStatusThree.setVisibility(View.GONE);
                                } else {
                                    //没有申请售后
                                    tvStatusOne.setText(mContext.getString(R.string.Contact_customer_service));
                                    tvStatusTwo.setVisibility(View.VISIBLE);
                                    tvStatusTwo.setText(mContext.getString(R.string.To_apply_for_refund));
                                    tvStatusThree.setVisibility(View.GONE);
                                }
                            }
                        }

                        break;
                    case THE_DATE_TYPE_TWO:
                        //拆分数据
                        if (totalShopItemMessageList != null && totalShopItemMessageList.size() > 0) {
                            for (int i = 0; i < totalShopItemMessageList.size(); i++) { //判断是或否申请了售后
                                List<ApplyRefundDate> applyRefundDates = totalShopItemMessageList.get(i).getApply();
                                if (applyRefundDates != null && applyRefundDates.size() > 0) {
                                    //申请了售后
                                    tvStatusOne.setText(mContext.getString(R.string.Contact_customer_service));
                                    tvStatusTwo.setVisibility(View.GONE);
                                    tvStatusThree.setVisibility(View.GONE);
                                } else {
                                    //没有申请售后
                                    tvStatusOne.setText(mContext.getString(R.string.Contact_customer_service));
                                    tvStatusTwo.setVisibility(View.VISIBLE);
                                    tvStatusTwo.setText(mContext.getString(R.string.To_apply_for_refund));
                                    tvStatusThree.setVisibility(View.GONE);
                                }
                            }
                        }
                        break;
                }
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD: //待收货
                tvTheOrderStatus.setText(mContext.getString(R.string.For_the_goods));
                tvStatusOne.setText(mContext.getString(R.string.Contact_customer_service));
                tvStatusTwo.setText(mContext.getString(R.string.Check_the_logistics));
                tvStatusThree.setText(mContext.getString(R.string.Confirm_the_goods));
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_DEAL://交易完成
                tvTheOrderStatus.setText(mContext.getString(R.string.The_deal));
                tvStatusOne.setText(mContext.getString(R.string.Check_the_logistics));
//                tvStatusTwo.setText(mContext.getString(R.string.Check_the_logistics));
                tvStatusTwo.setVisibility(View.GONE);
                tvStatusThree.setVisibility(View.GONE);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED://交易失败
                /**交易失败,包含了交易关闭，主动取消，超时关闭等几种情况
                 * 判断fail_status字段 1超时关闭 2交易关闭 3主动取消
                 */
                switch (failStatus) {
                    case 1:
                        tvTheOrderStatus.setText(mContext.getString(R.string.Timeout_closure));
                        break;
                    case 2:
                        tvTheOrderStatus.setText(mContext.getString(R.string.Trading_closed));
                        break;
                    case 3:
                        tvTheOrderStatus.setText(mContext.getString(R.string.Take_the_initiative_to_cancel));
                        break;
                }
                tvStatusOne.setText(mContext.getString(R.string.Delete_the_order));
                tvStatusTwo.setVisibility(View.GONE);
                tvStatusThree.setVisibility(View.GONE);
                break;

            case ApiParam.THE_ORDER_STATUS_TYPE_COMPLETED:
                tvTheOrderStatus.setText(mContext.getString(R.string.The_deal));
                tvStatusOne.setText(mContext.getString(R.string.Delete_the_order));
                tvStatusTwo.setText(mContext.getString(R.string.Check_the_logistics));
                tvStatusThree.setVisibility(View.GONE);

                break;

        }
    }

    /**
     * 底部按钮的显示和监听
     */
    private void setOrderStatusClickOfView(final int orderStatus, final int position, final TextView tvStatusOne,
                                           final TextView tvStatusTwo, final TextView tvStatusThree,
                                           final String payMoney, final String payOrderNumber, final String shopOrderNumber,
                                           final String expressCompanyId, final String expressNumber
    ) {
        /**
         * 按钮一
         */
        tvStatusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOrderListStatusClickListener != null) {
                    onOrderListStatusClickListener.onStatusOneClick(orderStatus, position,
                            tvStatusOne, payMoney,
                            payOrderNumber, shopOrderNumber,
                            expressCompanyId, expressNumber

                    );
                }
            }
        });
        /**
         * 按钮二
         */
        tvStatusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOrderListStatusClickListener != null) {
                    onOrderListStatusClickListener.onStatusTwoClick(orderStatus, position,
                            tvStatusTwo, payMoney,
                            payOrderNumber, shopOrderNumber,
                            expressCompanyId, expressNumber
                    );
                }
            }
        });

        /**
         * 按钮三
         */
        tvStatusThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onOrderListStatusClickListener != null) {
                    onOrderListStatusClickListener.onStatusThreeClick(orderStatus, position,
                            tvStatusThree, payMoney,
                            payOrderNumber, shopOrderNumber,
                            expressCompanyId, expressNumber
                    );
                }
            }
        });
    }


    /**
     * 不拆分数据
     */
    private List<ShopItemMessage> notBreakUpDate(int position) {
        List<ShopItemMessage> totalShopItemMessageList = new ArrayList<>(); //以店铺为单位，获取到所有的店铺信息
        if (orderDateMessageList.size() > 0) {
            List<ShopDateMessage> shopDateMessageList = orderDateMessageList.get(position).getShop(); //获取到对应的店铺数据
            if (shopDateMessageList != null && shopDateMessageList.size() > 0) {
                for (int i = 0; i < shopDateMessageList.size(); i++) {
                    for (int j = 0; j < shopDateMessageList.get(i).getList().size(); j++) {
                        ShopItemMessage shopItemMessage = shopDateMessageList.get(i).getList().get(j);
                        totalShopItemMessageList.add(shopItemMessage);
                    }
                }
            }
        }
        return totalShopItemMessageList;
    }

    /**
     * 拆分数据
     */
    private List<ShopDateMessage> BreakUpDate() {
        List<ShopDateMessage> totalShopDateList = new ArrayList<>();  // 以订单为单位，获取对应订单下面的所有店铺的商品
        if (orderDateMessageList.size() > 0) {
            for (int i = 0; i < orderDateMessageList.size(); i++) {
                List<ShopDateMessage> shopDateMessageList = orderDateMessageList.get(i).getShop(); //获取到对应的店铺数据
                if (shopDateMessageList != null && shopDateMessageList.size() > 0) {
                    for (int j = 0; j < shopDateMessageList.size(); j++) {
                        ShopDateMessage shopDateMessage = shopDateMessageList.get(j);
                        totalShopDateList.add(shopDateMessage); //将单个店铺添加到总的店铺之中
                    }
                }
            }
        }
        return totalShopDateList;
    }


}
