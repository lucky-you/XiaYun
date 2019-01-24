package com.goulala.xiayun.mycenter.activity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnsunrun.alipaylibrary.alipay.AliPayUtils;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.callback.CancelPaymentClickListener;
import com.goulala.xiayun.common.dialog.PasswordDialogUtils;
import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.PhoneUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.common.view.PasswordEditText;
import com.goulala.xiayun.home.dialog.ThePaymentDialog;
import com.goulala.xiayun.mycenter.adapter.TheOrderDetailsAdapter;
import com.goulala.xiayun.mycenter.callback.OnRefundGoodOrMoneyClickListener;
import com.goulala.xiayun.mycenter.model.AllOrderListMessage;
import com.goulala.xiayun.mycenter.model.OrderAddressBean;
import com.goulala.xiayun.mycenter.model.OrderDetailsMessage;
import com.goulala.xiayun.mycenter.model.OrderPayInfo;
import com.goulala.xiayun.mycenter.model.ShopDateMessage;
import com.goulala.xiayun.mycenter.model.ShopItemMessage;
import com.goulala.xiayun.mycenter.presenter.GoodListPresenter;
import com.goulala.xiayun.mycenter.view.IGoodListView;
import com.goulala.xiayun.wxapi.WeiXinPayUtils;
import com.goulala.xiayun.wxapi.WxPayReqInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情
 */
public class TheOrderDetailsActivity extends BaseMvpActivity<GoodListPresenter> implements IGoodListView {

    private TextView tvStatusName;
    private TextView tvStatusDescription;
    private LinearLayout llOrderStatusBackgroundLayout;
    private TextView tvTheConsigneeName;
    private TextView tvTvTheConsigneePhoneNumber;
    private TextView tvShippingAddress;
    private RecyclerView publicRecyclerView;
    private TextView tvAmountOfRealPayMoney;
    private TextView tvTheOrderNo;
    private TextView tvCopyTheOrderNo;
    private RelativeLayout rlThatOrderNumber;
    private TextView tvAlipayTransactionNumber;
    private TextView tvCreationTime;
    private TextView tvTimeOfPayment;
    private TextView tvTheDeliveryTime;
    private TextView tvClinchDealTheTime;
    private View DivideBottomLine;
    private TextView tvOrderStatusOne;
    private TextView tvOrderStatusTwo;
    private TextView tvOrderStatusThree;
    private SmartRefreshLayout smartRefreshLayout;
    private LinearLayout llBottomStatusLayout;
    private String payNumber, shopOrder;
    private TheOrderDetailsAdapter theOrderDetailsAdapter;
    private List<ShopDateMessage> shopDateMessageList = new ArrayList<>(); //店铺的数据
    private int thatOrderStatus; //订单状态
    private OrderDetailsMessage orderDetailsMessage; //订单的总信息
    private OrderPayInfo orderPayInfo; //订单的支付信息
    private String shopOrderNumber;//总的订单编号
    private String payOrderMoney; // 需要支付的订单金额,也就是实际的支付金额，没付款之前取最外层订单的sum_pay_money，付款之后取供应商的sum_pay_money
    private String payOrderMoneyTwo; // 需要支付的订单金额,也就是实际的支付金额，没付款之前取最外层订单的sum_pay_money，付款之后取供应商的sum_pay_money
    private String balanceMoney;//用户的余额
    private String payOrderNumber; //用户的支付订单编号
    private String aliPayOrderNumber; //支付宝交易单号
    private long payCallBackTime;//支付回调时间
    private long theDeliveryTime;//发货时间
    private int theOrderOfPayType;//订单支付方式
    private List<ShopItemMessage> goodDateListOfShop; //店铺下面的商品的集合(可能存在多商品的情况)
    private long completeTime;// 成交时间
    private String itemOrder, itemNumber; //商品的订单编号和商品的数量
    private String expressCompanyId, expressNumber;//物流公司id,物流单号

    public static void start(Context context, String pay_no, String shop_order) {
        Intent intent = new Intent(context, TheOrderDetailsActivity.class);
        intent.putExtra(ApiParam.PAY_NO, pay_no);
        intent.putExtra(ApiParam.SHOP_ORDER, shop_order);
        context.startActivity(intent);
    }


    @Override
    protected GoodListPresenter createPresenter() {
        return new GoodListPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        payNumber = getIntent().getStringExtra(ApiParam.PAY_NO);
        shopOrder = getIntent().getStringExtra(ApiParam.SHOP_ORDER);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_the_order_details;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.The_order_details));
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        tvStatusName = get(R.id.tv_status_name);
        tvStatusDescription = get(R.id.tv_status_description);
        llOrderStatusBackgroundLayout = get(R.id.ll_order_status_background_layout);
        tvTheConsigneeName = get(R.id.tv_The_consignee_name);
        tvTvTheConsigneePhoneNumber = get(R.id.tv_tv_The_consignee_phone_number);
        tvShippingAddress = get(R.id.tv_Shipping_address);
        publicRecyclerView = get(R.id.public_RecyclerView);
        tvAmountOfRealPayMoney = get(R.id.tv_Amount_of_real_pay_money);
        tvTheOrderNo = get(R.id.tv_The_order_no);
        tvCopyTheOrderNo = get(R.id.tv_copy_The_order_no);
        tvCopyTheOrderNo.setOnClickListener(this);
        rlThatOrderNumber = get(R.id.rl_that_order_number);
        tvAlipayTransactionNumber = get(R.id.tv_Alipay_transaction_number);
        tvCreationTime = get(R.id.tv_Creation_time);
        tvTimeOfPayment = get(R.id.tv_Time_of_payment);
        tvTheDeliveryTime = get(R.id.tv_The_delivery_time);
        tvClinchDealTheTime = get(R.id.tv_Clinch_deal_the_time);
        DivideBottomLine = get(R.id.Divide_Bottom_Line);
        tvOrderStatusOne = get(R.id.tv_order_status_one);
        tvOrderStatusOne.setOnClickListener(this);
        tvOrderStatusTwo = get(R.id.tv_order_status_two);
        tvOrderStatusTwo.setOnClickListener(this);
        tvOrderStatusThree = get(R.id.tv_order_status_three);
        tvOrderStatusThree.setOnClickListener(this);
        smartRefreshLayout = get(R.id.smartRefreshLayout);
        llBottomStatusLayout = get(R.id.ll_bottom_status_layout);
        registerEvent();
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        smartRefreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        smartRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        theOrderDetailsAdapter = new TheOrderDetailsAdapter(shopDateMessageList);
        publicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        publicRecyclerView.setAdapter(theOrderDetailsAdapter);
        publicRecyclerView.setNestedScrollingEnabled(false);
        publicRecyclerView.setFocusable(false);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDate();
                refreshLayout.finishRefresh();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDate();
    }

    /**
     * 获取数据
     */
    private void getDate() {
        //获取订单详情的信息
        getOrderDetails();
        //获取用户账户的余额
        getAccountBalanceOrCheckPasswordExist(ApiParam.ACCOUNT_BALANCE_URL, ApiParam.GET_ACCOUNT_BALANCE_TYPE);
    }

    /**
     * 获取订单信息
     */
    private void getOrderDetails() {
        Map<String, String> orderDetailsParam = new HashMap<>();
        orderDetailsParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.THAT_ORDER_DETAILS_URL);
        orderDetailsParam.put(ApiParam.PAY_NO, payNumber);
        orderDetailsParam.put(ApiParam.SHOP_ORDER, shopOrder);
        String orderDetailsParamJson = JsonUtils.toJson(orderDetailsParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getOrderDetails(userToken, orderDetailsParamJson);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Notice notice) {
        if (notice != null) {
            switch (notice.type) {
                case ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE: //申请退款
                    //待发货状态下，申请售后就是申请退款
                    String shopOrderNumber = (String) notice.contentOne;
                    RefundApplicationActivity.start(mContext, shopOrderNumber, "0", 0);
                    break;
                case ConstantValue.THAT_USE_APPLY_AFTER_SALES_TYPE: //申请售后
                    //待收货状态下，申请售后分为退款退货两种情况
                    final String shopOrder = (String) notice.contentOne;
                    final String itemOrder = (String) notice.contentTwo;
                    final String itemNumber = (String) notice.contentThree;
                    AlertDialogUtils.refundGoodOrMoneyDialog(mContext, new OnRefundGoodOrMoneyClickListener() {
                        @Override
                        public void onRefundGoodClick() {
                            ReturnTheGoodActivity.start(mContext, ConstantValue.APPLY_FOR_REFUND_GOOD_TYPE, shopOrder, itemOrder, itemNumber);
                        }

                        @Override
                        public void onRefundMoneyClick() {
                            ReturnTheGoodActivity.start(mContext, ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE, shopOrder, itemOrder, itemNumber);
                        }
                    });
                    break;
                case ConstantValue.APPLY_FOR_REFUND_MONEY_OF_NOT_SEND_GOOD_TYPE: //待发货状态下，点击已申请退款按钮，进入退款详情界面
                    String applyRefundOrder = (String) notice.contentOne;
                    TheGoodDetailsRefundActivity.start(mContext, applyRefundOrder);
                    break;
                case ConstantValue.APPLY_FOR_REFUND_MONEY_DETAILS_TYPE: //待收货状态下，点击已申请退款按钮，进入退款详情界面
                    String applyRefundOrderOne = (String) notice.contentOne;
                    ReturnTheGoodDetailsActivity.start(mContext, ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE, applyRefundOrderOne);
                    break;
                case ConstantValue.APPLY_FOR_REFUND_GOOD_DETAILS_TYPE:  //待收货状态下，点击已申请退货按钮，进入退货详情界面
                    String applyRefundOrderTwo = (String) notice.contentOne;
                    ReturnTheGoodDetailsActivity.start(mContext, ConstantValue.APPLY_FOR_REFUND_GOOD_TYPE, applyRefundOrderTwo);
                    break;
            }
        }
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.tv_copy_The_order_no:
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                clipboardManager.setPrimaryClip(ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN, shopOrderNumber));
                showToast(mContext.getString(R.string.Copy_success));
                break;
            case R.id.tv_order_status_one:
                onButtonStatusOneClick();
                break;
            case R.id.tv_order_status_two:
                onButtonStatusTwoClick();
                break;
            case R.id.tv_order_status_three:
                onButtonStatusThreeClick();
                break;
        }
    }

    //按钮一的监听
    private void onButtonStatusOneClick() {
        switch (thatOrderStatus) {
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID:
//                helper.initSdkChat(ApiService.QIMO_IM_ACCESSID, UserUtils.getUserName(), UserUtils.getUserID(), "10043315");
                //联系客服
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD:
//                helper.initSdkChat(ApiService.QIMO_IM_ACCESSID, UserUtils.getUserName(), UserUtils.getUserID(), "10043315");
                //联系客服
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD:
//                helper.initSdkChat(ApiService.QIMO_IM_ACCESSID, UserUtils.getUserName(), UserUtils.getUserID(), "10043315");
                //联系客服
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_DEAL:
                //删除订单
//                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED:
            case ApiParam.THE_ORDER_STATUS_TYPE_COMPLETED:
                //删除订单
                showCancelOrDeleteOrderDialog(mContext.getString(R.string.Are_you_sure_you_want_to_delete_the_order),
                        ApiParam.DELETE_THAT_ORDER_TYPE, payOrderNumber, shopOrderNumber);
                break;
        }
    }

    //按钮二的监听
    private void onButtonStatusTwoClick() {
        switch (thatOrderStatus) {
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID:
                //取消订单
                showCancelOrDeleteOrderDialog(mContext.getString(R.string.Are_you_sure_you_want_to_cancel_the_order),
                        ApiParam.CANCEL_THAT_ORDER_TYPE, payOrderNumber, shopOrderNumber);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD:
                //申请退款
                RefundApplicationActivity.start(mContext, shopOrderNumber, "0", 0);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD:
                //查看物流
                LogisticsDetailsActivity.start(mContext, expressCompanyId, expressNumber);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_DEAL:
            case ApiParam.THE_ORDER_STATUS_TYPE_COMPLETED:
                //查看物流
                LogisticsDetailsActivity.start(mContext, expressCompanyId, expressNumber);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED:
                //在此状态下不显示
                break;
        }
    }

    //按钮三的监听
    private void onButtonStatusThreeClick() {
        switch (thatOrderStatus) {
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID:
                //立即付款
                showPaymentDialog(payOrderNumber, payOrderMoney, balanceMoney);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD:
                //在此状态下不显示
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD:
                //确认收货
                showCancelOrDeleteOrderDialog(mContext.getString(R.string.Have_you_confirmed_receipt_of_the_goods),
                        ApiParam.CONFIRM_THAT_GOOD_TYPE,
                        payOrderNumber,
                        shopOrderNumber);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_DEAL:
                //在此状态下不显示
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED:
                //在此状态下不显示
                break;
        }
    }

    @Override
    public void getOrderDetailsSuccess(OrderDetailsMessage orderDetailsMessage) {
        if (orderDetailsMessage != null) {
            thatOrderStatus = orderDetailsMessage.getStatus();
            this.orderDetailsMessage = orderDetailsMessage;
            payOrderNumber = orderDetailsMessage.getPay_no(); //支付的订单编号
            payOrderMoney = orderDetailsMessage.getSum_pay_money(); //订单没有支付，取最外层的订单价格
            List<ShopDateMessage> shopItemMessage = orderDetailsMessage.getShop();   //获取到店铺额信息
            if (shopItemMessage != null && shopItemMessage.size() > 0) {
                thatOrderStatus = shopItemMessage.get(0).getStatus(); //店铺的状态
                this.shopDateMessageList = shopItemMessage;
                theOrderDetailsAdapter.setNewData(shopDateMessageList);

                shopOrderNumber = shopItemMessage.get(0).getShop_order(); //订单编号
                payOrderMoneyTwo = shopItemMessage.get(0).getSum_pay_money() + ""; //已经支付了的订单，就取供应商下面的总价
                goodDateListOfShop = shopItemMessage.get(0).getList();  //获取店铺下面的商品的数据集合
                completeTime = shopItemMessage.get(0).getCompletetime(); //订单的成交时间
                itemNumber = shopItemMessage.get(0).getSum_pay_num() + "";  // 商品的个数

                if (goodDateListOfShop != null && goodDateListOfShop.size() > 0) {
                    if (goodDateListOfShop.size() > 1) {
                        itemOrder = "0";  //商品订单号 多个订单时传0
                    } else {
                        itemOrder = goodDateListOfShop.get(0).getItem_order();
                    }
                }
            }
            //获取到收货地址的信息
            OrderAddressBean shopAddressBean = orderDetailsMessage.getAddress();
            if (shopAddressBean != null) {
                setAddressDateToView(shopAddressBean);
                theDeliveryTime = shopAddressBean.getDeliverytime();//商品的发货时间
                expressCompanyId = String.valueOf(shopAddressBean.getExpress_company_id());
                expressNumber = shopAddressBean.getExpress_number();
            }
            //订单的支付信息
            orderPayInfo = orderDetailsMessage.getPay_info();
            if (orderPayInfo != null) {
                aliPayOrderNumber = orderPayInfo.getPay_no(); //交易编号
                payCallBackTime = orderPayInfo.getCallbacktime(); //支付时间
                theOrderOfPayType = orderPayInfo.getSource();  //订单的支付方式 //2是余额支付，3是支付宝支付，4是微信支付
            }
            //根据状态显示界面，加载数据
            setDateToTopViewOfStatus(thatOrderStatus);
        }
    }

    /**
     * 根据状态显示头部的布局
     */
    private void setDateToTopViewOfStatus(int orderStatus) {
        switch (orderStatus) {
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID:
                //待支付
                llOrderStatusBackgroundLayout.setBackground(mContext.getResources().getDrawable(R.drawable.ic_order_status_waiting_bj));
                tvStatusName.setText(mContext.getString(R.string.Waiting_for_payment));
                tvStatusDescription.setText("剩余21小时29分自动关闭");
                //实际付款金额
                tvAmountOfRealPayMoney.setText(mContext.getString(R.string.the_total_money, payOrderMoney));
                setOrderPaymentStatusToView(
                        false,
                        false,
                        true,
                        false,
                        false,
                        false);
                tvOrderStatusOne.setText(mContext.getString(R.string.Contact_customer_service)); //联系客服
                tvOrderStatusTwo.setText(mContext.getString(R.string.Cancel_the_order)); //取消订单
                tvOrderStatusThree.setText(mContext.getString(R.string.Immediate_payment)); //立即付款
                break;

            case ApiParam.THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD:
                //待发货
                llOrderStatusBackgroundLayout.setBackground(mContext.getResources().getDrawable(R.drawable.ic_order_status_waiting_bj));
                tvStatusName.setText(mContext.getString(R.string.Waiting_for_the_seller_to_deliver_the_goods));
                tvStatusDescription.setVisibility(View.GONE);
                //实际付款金额
                tvAmountOfRealPayMoney.setText(mContext.getString(R.string.the_total_money, payOrderMoneyTwo));
                setOrderPaymentStatusToView(
                        true,
                        true,
                        true,
                        true,
                        false,
                        false);

                tvOrderStatusOne.setText(mContext.getString(R.string.Contact_customer_service)); //联系客服
                tvOrderStatusThree.setVisibility(View.GONE);
                if (goodDateListOfShop != null && goodDateListOfShop.size() > 0) {
                    for (int i = 0; i < goodDateListOfShop.size(); i++) {
                        /**
                         * 在待发货状态下，申请售后，只能申请退款
                         * 在待发货状态下，存在着同一个店铺，包含多个商品的情况，
                         * 只要有一个商品申请了售后，整个店铺都视为申请了售后，此时隐藏申请退款按钮
                         */
                        if (goodDateListOfShop.get(i).getApply() != null && goodDateListOfShop.get(i).getApply().size() > 0) {
                            //申请了售后
                            tvOrderStatusTwo.setVisibility(View.GONE);
                        } else {
                            //没有申请售后
                            tvOrderStatusTwo.setVisibility(View.VISIBLE);
                            tvOrderStatusTwo.setText(mContext.getString(R.string.To_apply_for_refund)); //申请退款
                        }
                    }
                }
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD:
                //待收货
                llOrderStatusBackgroundLayout.setBackground(mContext.getResources().getDrawable(R.drawable.ic_order_status_waiting_bj));
                tvStatusName.setText(mContext.getString(R.string.Wait_for_the_seller_to_receive_the_goods));

                //TODO：10天之后自动收货
                long theGoodsTime = theDeliveryTime + 10 * 24 * 60 * 60; //发货时间+10天(10天之后自动确认收货)的时间(单位是秒)
                /**
                 * 10天之后的时间减去当前的时间，就是距离自动确认收货的倒计时时间
                 */
                long currentTime = (System.currentTimeMillis() / 1000);
                long theCountdownTime = theGoodsTime - currentTime; //倒计时时间

                tvStatusDescription.setText("剩余2天14小时28分自动确认收货");
                //实际付款金额
                tvAmountOfRealPayMoney.setText(mContext.getString(R.string.the_total_money, payOrderMoneyTwo));
                setOrderPaymentStatusToView(
                        true,
                        true,
                        true,
                        true,
                        true,
                        false);
                tvOrderStatusOne.setText(mContext.getString(R.string.Contact_customer_service)); //联系客服
                tvOrderStatusTwo.setText(mContext.getString(R.string.Check_the_logistics)); //查看物流
                tvOrderStatusThree.setText(mContext.getString(R.string.Confirm_the_goods)); //确认收货
                break;

            case ApiParam.THE_ORDER_STATUS_TYPE_OF_DEAL:
                //确认收货
                llOrderStatusBackgroundLayout.setBackground(mContext.getResources().getDrawable(R.drawable.ic_order_status_success_bj));
                tvStatusName.setText(mContext.getString(R.string.A_successful_deal));
                tvStatusDescription.setVisibility(View.GONE);
                //实际付款金额
                tvAmountOfRealPayMoney.setText(mContext.getString(R.string.the_total_money, payOrderMoneyTwo));
                setOrderPaymentStatusToView(
                        true,
                        true,
                        true,
                        true,
                        true,
                        true);

                tvOrderStatusOne.setVisibility(View.GONE);//删除订单
                //默认不显示删除订单
                tvOrderStatusTwo.setText(mContext.getString(R.string.Check_the_logistics)); //查看物流
                tvOrderStatusThree.setVisibility(View.GONE);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED:
                llOrderStatusBackgroundLayout.setBackground(mContext.getResources().getDrawable(R.drawable.ic_order_state_close_bj));
                tvStatusName.setText(mContext.getString(R.string.Trading_closed));

                //实际付款金额
                tvAmountOfRealPayMoney.setText(mContext.getString(R.string.the_total_money, payOrderMoneyTwo));
                /**交易失败,包含了交易关闭，主动取消，超时关闭等几种情况
                 * 判断fail_status字段 1超时关闭 2交易关闭 3主动取消
                 */
                switch (orderDetailsMessage.getFail_status()) {
                    case 1: //超时关闭
                        setOrderPaymentStatusToView(
                                true,
                                false,
                                true,
                                false,
                                false,
                                false);
                        tvStatusDescription.setVisibility(View.VISIBLE);
                        tvStatusDescription.setText(mContext.getString(R.string.Timeout_closure));
                        break;
                    case 2: //交易关闭
                        setOrderPaymentStatusToView(
                                true,
                                true,
                                true,
                                true,
                                true,
                                false);
                        tvStatusDescription.setVisibility(View.GONE);
//                        tvStatusDescription.setText(mContext.getString(R.string.Trading_closed));
                        break;
                    case 3: //主动取消
                        setOrderPaymentStatusToView(
                                true,
                                false,
                                true,
                                false,
                                false,
                                false);
                        tvStatusDescription.setVisibility(View.VISIBLE);
                        tvStatusDescription.setText(mContext.getString(R.string.Take_the_initiative_to_cancel));
                        break;
                }
                tvOrderStatusOne.setText(mContext.getString(R.string.Delete_the_order)); //删除订单
                tvOrderStatusTwo.setVisibility(View.GONE);
                tvOrderStatusThree.setVisibility(View.GONE);
                break;

            case ApiParam.THE_ORDER_STATUS_TYPE_COMPLETED:
                // 此时的交易完成，才是真正的完成，默认达到10天之后的完成
                llOrderStatusBackgroundLayout.setBackground(mContext.getResources().getDrawable(R.drawable.ic_order_status_success_bj));
                tvStatusName.setText(mContext.getString(R.string.A_successful_deal));
                tvStatusDescription.setVisibility(View.GONE);
                //实际付款金额
                tvAmountOfRealPayMoney.setText(mContext.getString(R.string.the_total_money, payOrderMoneyTwo));
                setOrderPaymentStatusToView(
                        true,
                        true,
                        true,
                        true,
                        true,
                        true);
                tvOrderStatusOne.setText(mContext.getString(R.string.Delete_the_order));//删除订单
                //超过7天之后才显示删除订单按钮
                tvOrderStatusTwo.setText(mContext.getString(R.string.Check_the_logistics)); //查看物流
                tvOrderStatusThree.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * @param isShowOrderNumberLayout 订单编号的布局
     * @param isShowTheOrderPayNumber 订单的交易号
     * @param isShowCreateTime        创建时间
     * @param isShowPayTime           付款时间
     * @param isShowTimeOfPayment     发货时间
     * @param isShowTheDeliveryTime   成交时间
     */
    private void setOrderPaymentStatusToView(boolean isShowOrderNumberLayout,
                                             boolean isShowTheOrderPayNumber,
                                             boolean isShowCreateTime,
                                             boolean isShowPayTime,
                                             boolean isShowTimeOfPayment,
                                             boolean isShowTheDeliveryTime
    ) {
        if (isShowOrderNumberLayout) {
            rlThatOrderNumber.setVisibility(View.VISIBLE); //订单编号的布局
            tvTheOrderNo.setVisibility(View.VISIBLE);//订单编号
            tvTheOrderNo.setText(mContext.getString(R.string.The_order_no, shopOrderNumber));//订单编号
        } else {
            rlThatOrderNumber.setVisibility(View.GONE);
            tvTheOrderNo.setVisibility(View.GONE);
        }
        if (isShowTheOrderPayNumber) {
            tvAlipayTransactionNumber.setVisibility(View.VISIBLE); //支付交易号
            switch (theOrderOfPayType) {
                case OrderPayInfo.USE_BALANCE_PAYMENT_TYPE:
                    tvAlipayTransactionNumber.setText(mContext.getString(R.string.balance_transaction_number, aliPayOrderNumber));//余额交易号
                    break;
                case OrderPayInfo.USE_ALIPAY_PAYMENT_TYPE:
                    tvAlipayTransactionNumber.setText(mContext.getString(R.string.Alipay_transaction_number, aliPayOrderNumber));//支付宝交易号
                    break;
                case OrderPayInfo.USE_WECHAT_PAYMENT_TYPE:
                    tvAlipayTransactionNumber.setText(mContext.getString(R.string.wecahat_transaction_number, aliPayOrderNumber));//微信交易号
                    break;
            }
        } else {
            tvAlipayTransactionNumber.setVisibility(View.GONE);
        }
        if (isShowCreateTime) {
            tvCreationTime.setVisibility(View.VISIBLE); //创建时间
            tvCreationTime.setText(mContext.getString(R.string.Creation_time, DateUtils.getStrTime(orderDetailsMessage.getCreatetime()))); //创建时间
        } else {
            tvCreationTime.setVisibility(View.GONE);
        }
        if (isShowPayTime) {
            tvTimeOfPayment.setVisibility(View.VISIBLE); //付款时间
            tvTimeOfPayment.setText(mContext.getString(R.string.Time_of_payment, DateUtils.getStrTime(payCallBackTime)));//付款时间
        } else {
            tvTimeOfPayment.setVisibility(View.GONE);
        }
        if (isShowTimeOfPayment) {
            if (theDeliveryTime > 0) { //发货时间>0说明是待收货状态下--》售后完成，显示发货时间
                tvTheDeliveryTime.setVisibility(View.VISIBLE); //发货时间
                tvTheDeliveryTime.setText(mContext.getString(R.string.The_delivery_time, DateUtils.getStrTime(theDeliveryTime))); //发货时间
            } else {
                //说明是在待发货状态下--》完成了退款，不显示发货时间
                tvTheDeliveryTime.setVisibility(View.GONE);
            }
        } else {
            tvTheDeliveryTime.setVisibility(View.GONE);
        }
        if (isShowTheDeliveryTime) {
            tvClinchDealTheTime.setVisibility(View.VISIBLE); //成交时间
            tvClinchDealTheTime.setText(mContext.getString(R.string.Clinch_deal_the_time, DateUtils.getStrTime(completeTime))); //发货时间
        } else {
            tvClinchDealTheTime.setVisibility(View.GONE);
        }
    }

    /**
     * 填充收货地址的信息
     */
    private void setAddressDateToView(OrderAddressBean shopAddressBean) {
        tvTheConsigneeName.setText(mContext.getString(R.string.The_consignee_three, shopAddressBean.getConsignee_name()));
        tvTvTheConsigneePhoneNumber.setText(PhoneUtils.mobilNumber(shopAddressBean.getConsignee_mobile()));
        tvShippingAddress.setText(mContext.getString(R.string.Shipping_address, shopAddressBean.getConsignee_address()));
    }

    @Override
    public void getOrderListSuccess(AllOrderListMessage orderMessage) {

    }

    @Override
    public void checkPasswordExistOrOperationOrder(int requestType, boolean isExist, String message) {
        switch (requestType) {
            case ApiParam.CHECK_PASSWORD_EXIST_TYPE:
                if (isExist) {
                    //设置了密码，直接输密码，支付
                    editPayPassword();
                } else {
                    //没有设置密码，去设置界面，设置支付密码
                    setCommissionPaymentPassword();
                }
                break;
            case ApiParam.CANCEL_THAT_ORDER_TYPE://取消订单:
            case ApiParam.DELETE_THAT_ORDER_TYPE: //删除订单
            case ApiParam.CONFIRM_THAT_GOOD_TYPE://确认收货
                showToast(message);
                finish();
//                onResume();
                break;


        }
    }

    /**
     * 输入支付密码
     */
    private void editPayPassword() {
        PasswordDialogUtils.passwordDialog(mContext,
                mContext.getString(R.string.Please_enter_the_payment_password),
                new CancelPaymentClickListener() {
                    @Override
                    public void cancelPayment() {
                        showToast(mContext.getString(R.string.Cancel_the_payment));
                        showPaymentDialog(payOrderNumber, payOrderMoney, balanceMoney);
                    }
                },
                new PasswordEditText.PasswordFullListener() {
                    @Override
                    public void passwordFull(String password) {
                        selectPayTypeToPaymentOrder(payOrderNumber, payOrderMoney, password, ConstantValue.PAY_OF_BALANCE_TYPE);
                    }
                });

    }

    /**
     * 去设置支付密码
     */
    private void setCommissionPaymentPassword() {
        String dialogTitle = mContext.getString(R.string.You_have_not_set_the_commission_payment_password);
        String dialogLeft = mContext.getString(R.string.Temporarily_not_set);
        String dialogRight = mContext.getString(R.string.Immediately_set);
        AlertDialogUtils.showCustomerDialog(mContext, dialogTitle, dialogLeft, dialogRight, new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {
                showPaymentDialog(payOrderNumber, payOrderMoney, balanceMoney);
            }

            @Override
            public void determineClick(View view) {
                ResetCommissionPaymentPasswordActivity.start(mContext, ConstantValue.SET_THE_COMMISSION_PAYMENT_PASSWORD);
            }
        });
    }

    /**
     * 立即支付的dialog
     */
    private void showPaymentDialog(final String payOrderNumber, final String payMoney, final String balanceMoney) {

        String balance;
        //余额不能为空，且购买的金额小于余额
        if (Double.valueOf(payMoney) <= Double.valueOf(balanceMoney) && !TextUtils.isEmpty(balanceMoney)) {
            balance = balanceMoney;
        } else {
            balance = "";
        }
        AlertDialogUtils.showPaymentDialog(mContext, payMoney, balance, new ThePaymentDialog.OnPayResultListener() {
            @Override
            public void chooseTypeToPay(int type) {
                switch (type) {
                    case ThePaymentDialog.PAY_OF_BALANCE_TYPE:
                        getAccountBalanceOrCheckPasswordExist(ApiParam.DOES_PASSWORD_EXIST_URL, ApiParam.CHECK_PASSWORD_EXIST_TYPE);
                        break;
                    case ThePaymentDialog.PAY_OF_ALIPAY_TYPE:
                        //支付宝支付
                        selectPayTypeToPaymentOrder(payOrderNumber, payMoney, "", ConstantValue.PAY_OF_ALIPAY_TYPE);
                        break;
                    case ThePaymentDialog.PAY_OF_WECHAT_TYPE:
                        //微信支付
                        selectPayTypeToPaymentOrder(payOrderNumber, payMoney, "", ConstantValue.PAY_OF_WECHAT_TYPE);
                        break;
                }
            }

            @Override
            public void closePayDialog() {

            }
        });
    }

    /**
     * 获取用户的余额,和,检查是否设置了支付密码公用的,我就想少写两行代码
     */
    private void getAccountBalanceOrCheckPasswordExist(String requestUrl, int requestType) {
        Map<String, String> checkParam = new HashMap<>();
        checkParam.put(ApiParam.BASE_METHOD_KEY, requestUrl);
        String checkParamJson = JsonUtils.toJson(checkParam);
        if (ApiParam.GET_ACCOUNT_BALANCE_TYPE == requestType) {
            mvpPresenter.getAccountBalance(userToken, checkParamJson);
        } else {
            mvpPresenter.checkPasswordExistOrDeleteOrder(ApiParam.CHECK_PASSWORD_EXIST_TYPE, userToken, checkParamJson);
        }

    }

    /**
     * 选择支付方式支付订单
     */
    private void selectPayTypeToPaymentOrder(String payOrderNumber, String payMoney, String password, int payType) {
        Map<String, String> paymentOrderParam = new HashMap<>();
        paymentOrderParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.PAYMENT_THAT_ORDER_URL);
        paymentOrderParam.put(ApiParam.PAY_NO, payOrderNumber);
        paymentOrderParam.put(ApiParam.MONEY, payMoney);
        paymentOrderParam.put(ApiParam.PAYPASS, password);
        paymentOrderParam.put(ApiParam.TYPE, String.valueOf(payType));
        String paymentOrderParamJson = JsonUtils.toJson(paymentOrderParam);
        switch (payType) {
            //支付类型 1:余额支付 2:支付宝支付 3:微信支付
            case ConstantValue.PAY_OF_BALANCE_TYPE:
                mvpPresenter.useBalanceOrAliPayPayment(ConstantValue.PAY_OF_BALANCE_TYPE, userToken, paymentOrderParamJson);
                break;
            case ConstantValue.PAY_OF_ALIPAY_TYPE:
                mvpPresenter.useBalanceOrAliPayPayment(ConstantValue.PAY_OF_ALIPAY_TYPE, userToken, paymentOrderParamJson);
                break;
            case ConstantValue.PAY_OF_WECHAT_TYPE:
                mvpPresenter.useWeChatPayment(userToken, paymentOrderParamJson);
                break;
        }
    }

    /**
     * 取消，删除，确认收货的dialog
     */
    private void showCancelOrDeleteOrderDialog(String showTitle, final int requestType, final String payOrderNumber, final String shopOrderNumber) {
        AlertDialogUtils.showCustomerDialog(mContext, showTitle, new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {

            }

            @Override
            public void determineClick(View view) {
                cancelOrDeleteOrder(payOrderNumber, shopOrderNumber, requestType);
            }
        });

    }

    //取消订单,删除订单
    private void cancelOrDeleteOrder(String payOrderNumber, String shopOrderNumber, int requestType) {
        Map<String, String> cancelOrderParam = new HashMap<>();
        switch (requestType) {
            case ApiParam.CANCEL_THAT_ORDER_TYPE://取消订单
                cancelOrderParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CANCEL_THAT_ORDER_URL);
                cancelOrderParam.put(ApiParam.PAY_NO, payOrderNumber);
                break;
            case ApiParam.DELETE_THAT_ORDER_TYPE: //删除订单
                cancelOrderParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.DELETE_THAT_ORDER_URL);
                cancelOrderParam.put(ApiParam.PAY_NO, payOrderNumber);
                cancelOrderParam.put(ApiParam.SHOP_ORDER, shopOrderNumber);
                break;
            case ApiParam.CONFIRM_THAT_GOOD_TYPE://确认收货
                cancelOrderParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CONFIRM_THAT_GOOD);
                cancelOrderParam.put(ApiParam.SHOP_ORDER, shopOrderNumber);
                break;
        }
        String cancelParamJson = JsonUtils.toJson(cancelOrderParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.checkPasswordExistOrDeleteOrder(requestType, userToken, cancelParamJson);
        }
    }

    @Override
    public void getAccountBalanceSuccess(AccountBalance balance) {
        if (balance != null) {
            //获取到了余额
            balanceMoney = balance.getAmount();
        }
    }

    @Override
    public void useWeChatPaymentSuccess(WxPayReqInfo wxPayResult) {
        //微信支付成功
        if (wxPayResult != null) {
            userWeChatPaymentOrder(wxPayResult, new WeiXinPayUtils.OnWxPayResultListener() {
                @Override
                public void onSuccess() {
                    showToast(mContext.getString(R.string.Pay_for_success));
                    finish();
                    super.onSuccess();
                }

                @Override
                public void onCancel() {
                    showToast(mContext.getString(R.string.Cancel_the_payment));
                    super.onCancel();
                }
            });
        }
    }

    @Override
    public void useBalanceOrAliPayPaymentSuccess(int requestType, String orderMessage) {
        switch (requestType) {
            case ConstantValue.PAY_OF_BALANCE_TYPE:
                //余额支付成功
                if (!TextUtils.isEmpty(orderMessage)) {
                    showToast(orderMessage);
                    finish();
                }
                break;
            case ConstantValue.PAY_OF_ALIPAY_TYPE:
                //支付宝支付成功
                if (!TextUtils.isEmpty(orderMessage)) {
                    userAliPayPaymentOrder(orderMessage, new AliPayUtils.OnAlipayListener() {
                        @Override
                        public void onSuccess() {
                            showToast(mContext.getString(R.string.Pay_for_success));
                            finish();
                        }

                        @Override
                        public void onCancel() {
                            showToast(mContext.getString(R.string.Cancel_the_payment));
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void userPaymentFailed(int requestType, String message) {
        switch (requestType) {
            case ConstantValue.PAY_OF_BALANCE_TYPE:
                //余额支付失败
                showToast(message);
                break;
            case ConstantValue.PAY_OF_ALIPAY_TYPE:
                //支付宝支付失败
                showToast(message);
                break;
            case ConstantValue.PAY_OF_WECHAT_TYPE:
                //微信支付失败
                showToast(message);
                break;
        }
    }


    @Override
    public void onNewWorkException(String message) {
        showToast(message);
        dismissDialog();
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
        dismissDialog();
    }
}
