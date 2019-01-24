package com.goulala.xiayun.mycenter.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnsunrun.alipaylibrary.alipay.AliPayUtils;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpFragment;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.callback.CancelPaymentClickListener;
import com.goulala.xiayun.common.dialog.PasswordDialogUtils;
import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.view.PasswordEditText;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.common.widget.SmartRefreshLoadPageHelper;
import com.goulala.xiayun.home.dialog.ThePaymentDialog;
import com.goulala.xiayun.mycenter.activity.LogisticsDetailsActivity;
import com.goulala.xiayun.mycenter.activity.RefundApplicationActivity;
import com.goulala.xiayun.mycenter.activity.ResetCommissionPaymentPasswordActivity;
import com.goulala.xiayun.mycenter.adapter.AllFragmentListGoodAdapter;
import com.goulala.xiayun.mycenter.callback.OnOrderListStatusClickListener;
import com.goulala.xiayun.mycenter.model.AllOrderListMessage;
import com.goulala.xiayun.mycenter.model.OrderDateMessage;
import com.goulala.xiayun.mycenter.model.OrderDetailsMessage;
import com.goulala.xiayun.mycenter.presenter.GoodListPresenter;
import com.goulala.xiayun.mycenter.view.IGoodListView;
import com.goulala.xiayun.wxapi.WeiXinPayUtils;
import com.goulala.xiayun.wxapi.WxPayReqInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : Z_B
 * date : 2018/8/20
 * function : 商品的fragment
 */
public class GoodsListFragment extends BaseMvpFragment<GoodListPresenter> implements IGoodListView,
        OnOrderListStatusClickListener, SmartRefreshLoadPageHelper.DataProvider {

    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private ImageView ivHint;
    private TextView tvHint;
    private RelativeLayout rlEmptyLayout;
    private int mFragmentType;
    private String payOrderMoney; // 需要支付的订单金额
    private String balanceMoney;//用户的余额
    private String payOrderNumber; //用户的支付订单
    private List<OrderDateMessage> orderDateList = new ArrayList<>(); //没有拆分数据的adapter
    private AllFragmentListGoodAdapter goodListFragmentAdapter;
    private int pageSize = 10;//每页展示的数据条数
    private SmartRefreshLoadPageHelper<OrderDateMessage> smartRefreshLoadPageHelper = new SmartRefreshLoadPageHelper<>(this);

    public static GoodsListFragment newInstance(int type) {
        GoodsListFragment fragment = new GoodsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected GoodListPresenter createPresenter() {
        return new GoodListPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_good_list_fragment_layout;
    }

    @Override
    public void bindViews(View contentView) {
        mFragmentType = getArguments().getInt(ConstantValue.TYPE, -5);
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        ivHint = get(R.id.ivHint);
        tvHint = get(R.id.tvHint);
        rlEmptyLayout = get(R.id.rl_empty_layout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

        goodListFragmentAdapter = new AllFragmentListGoodAdapter(orderDateList);
        goodListFragmentAdapter.setOnOrderListStatusClickListener(this);
        smartRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 10));
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        smartRecyclerView.setAdapter(goodListFragmentAdapter);
        smartRefreshLoadPageHelper.attachView(refreshLayout, smartRecyclerView, goodListFragmentAdapter);
        EmptyViewUtils.bindEmptyView(mContext, goodListFragmentAdapter, mContext.getString(R.string.No_order_information));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                orderDateList.clear();
                smartRefreshLoadPageHelper.refreshPage();
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDate();
    }

    /**
     * 获取数据
     */
    private void getDate() {
        //获取用户账户的余额
        getAccountBalanceOrCheckPasswordExist(ApiParam.ACCOUNT_BALANCE_URL, ApiParam.GET_ACCOUNT_BALANCE_TYPE);
    }

    /**
     * 获取订单列表
     */
    private void getOrderListDate(int type, int currentPage) {
        Map<String, String> orderListParam = new HashMap<>();
        orderListParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.THAT_ORDER_LIST_URL);
        orderListParam.put(ApiParam.TYPE, String.valueOf(type));
        orderListParam.put(ApiParam.PAGE_KEY, String.valueOf(currentPage));
        orderListParam.put(ApiParam.SIZE_KEY, ApiParam.SIZE_NUMBER_VALUE);
        String orderListParamJson = JsonUtils.toJson(orderListParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getOrderList(userToken, orderListParamJson);
        }
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
        switch (requestType) {
            case ApiParam.GET_ACCOUNT_BALANCE_TYPE:
                mvpPresenter.getAccountBalance(userToken, checkParamJson);
                break;
            case ApiParam.CHECK_PASSWORD_EXIST_TYPE:
                mvpPresenter.checkPasswordExistOrDeleteOrder(ApiParam.CHECK_PASSWORD_EXIST_TYPE, userToken, checkParamJson);
                break;
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
    public void setClickListener(View view) {

    }

    @Override
    public void loadMorePageDate(int page) {
        getOrderListDate(mFragmentType - 1, page);
    }

    @Override
    public void onStatusOneClick(int orderStatus, int position, TextView tvStatusOne, String payMoney, String payOrderNumber, String shopOrderNumber, String expressCompanyId, String expressNumber) {
        this.payOrderMoney = payMoney;
        this.payOrderNumber = payOrderNumber;
        switch (orderStatus) {
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID:
                //取消订单
                showCancelOrDeleteOrderDialog(mContext.getString(R.string.Are_you_sure_you_want_to_cancel_the_order),
                        ApiParam.CANCEL_THAT_ORDER_TYPE, payOrderNumber, shopOrderNumber);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD:
                //联系客服
//                helper.initSdkChat(ApiService.QIMO_IM_ACCESSID, UserUtils.getUserName(), UserUtils.getUserID(), "10043315");
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD:
                //联系客服
//                helper.initSdkChat(ApiService.QIMO_IM_ACCESSID, UserUtils.getUserName(), UserUtils.getUserID(), "10043315");
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_DEAL:
                //交易完成
                LogisticsDetailsActivity.start(mContext, expressCompanyId, expressNumber);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED:
            case ApiParam.THE_ORDER_STATUS_TYPE_COMPLETED:
                //删除订单
                showCancelOrDeleteOrderDialog(mContext.getString(R.string.Are_you_sure_you_want_to_delete_the_order),
                        ApiParam.DELETE_THAT_ORDER_TYPE, payOrderNumber, shopOrderNumber);
                break;
        }
    }

    @Override
    public void onStatusTwoClick(int orderStatus, int position, TextView tvStatusTwo, String payMoney, String payOrderNumber, String shopOrderNumber, String expressCompanyId, String expressNumber) {
        this.payOrderMoney = payMoney;
        this.payOrderNumber = payOrderNumber;
        switch (orderStatus) {
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID:
                //联系客服
//                helper.initSdkChat(ApiService.QIMO_IM_ACCESSID, UserUtils.getUserName(), UserUtils.getUserID(), "10043315");
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
                //查看物流
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED:
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_COMPLETED:
                LogisticsDetailsActivity.start(mContext, expressCompanyId, expressNumber);
                break;
        }
    }

    @Override
    public void onStatusThreeClick(int orderStatus, int position, TextView tvStatusThree, String payMoney, String payOrderNumber, String shopOrderNumber, String expressCompanyId, String expressNumber) {
        this.payOrderMoney = payMoney;
        this.payOrderNumber = payOrderNumber;
        switch (orderStatus) {
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TO_PAID:
                //立即支付
                showPaymentDialog(payOrderNumber, payMoney, balanceMoney);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD:
                //在此状态被隐藏了
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD:
                //确认收货
                showCancelOrDeleteOrderDialog(mContext.getString(R.string.Have_you_confirmed_receipt_of_the_goods),
                        ApiParam.CONFIRM_THAT_GOOD_TYPE,
                        payOrderNumber,
                        shopOrderNumber);
                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_DEAL:

                break;
            case ApiParam.THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED:
                break;

            case ApiParam.THE_ORDER_STATUS_TYPE_COMPLETED:
                break;
        }
    }

    @Override
    public void getOrderDetailsSuccess(OrderDetailsMessage orderDetailsMessage) {

    }

    @Override
    public void getOrderListSuccess(AllOrderListMessage orderMessage) {
        if (orderMessage != null) {
            orderDateList = orderMessage.getData();
            if (orderDateList != null && !orderDateList.isEmpty()) {
                refreshLayout.setVisibility(View.VISIBLE);
                rlEmptyLayout.setVisibility(View.GONE);
                switch (mFragmentType) { //fragment的下标
                    case 0: //全部订单，需要考虑拆分和不拆分两种情况
                        for (int i = 0; i < orderDateList.size(); i++) {
                            if (1 == orderDateList.get(i).getIs_dismantle()) {//拆分数据
                                goodListFragmentAdapter.setDateType(AllFragmentListGoodAdapter.THE_DATE_TYPE_TWO);
                            } else {//不拆数据
                                goodListFragmentAdapter.setDateType(AllFragmentListGoodAdapter.THE_DATE_TYPE_ONE);
                            }
                        }
                        smartRefreshLoadPageHelper.setData(orderDateList);
                        break;
                    case 1: //待支付
                        //不需要拆分数据
                        goodListFragmentAdapter.setDateType(AllFragmentListGoodAdapter.THE_DATE_TYPE_ONE);
                        smartRefreshLoadPageHelper.setData(orderDateList);
                        break;
                    case 2: //待收货
                    case 3: //待收货
                    case 4: //退款/售后
                        //需要拆分数据
                        goodListFragmentAdapter.setDateType(AllFragmentListGoodAdapter.THE_DATE_TYPE_TWO);
                        smartRefreshLoadPageHelper.setData(orderDateList);
                        break;
                }
            }
        }
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
            case ApiParam.CANCEL_THAT_ORDER_TYPE://取消订单
            case ApiParam.DELETE_THAT_ORDER_TYPE: //删除订单
            case ApiParam.CONFIRM_THAT_GOOD_TYPE://确认收货
                showToast(message);
                smartRefreshLoadPageHelper.refreshPage();
                break;


        }
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
            PayReq request = new PayReq();
            request.appId = wxPayResult.getAppid();
            request.partnerId = wxPayResult.getPartnerid();
            request.prepayId = wxPayResult.getPrepayid();
            request.packageValue = wxPayResult.getPackageX();
            request.nonceStr = wxPayResult.getNoncestr();
            request.timeStamp = wxPayResult.getTimestamp();
            request.sign = wxPayResult.getSign();
            WeiXinPayUtils.useWeChatPay(mContext, request, new WeiXinPayUtils.OnWxPayResultListener() {
                @Override
                public void onSuccess() {
                    showToast(mContext.getString(R.string.Pay_for_success));
                    smartRefreshLoadPageHelper.refreshPage();
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
                    smartRefreshLoadPageHelper.refreshPage();
                }
                break;
            case ConstantValue.PAY_OF_ALIPAY_TYPE:
                //支付宝支付成功
                if (!TextUtils.isEmpty(orderMessage)) {
                    AliPayUtils aliPayUtils = new AliPayUtils(getActivity());
                    aliPayUtils.requestPayFromServiceSide(orderMessage);
                    aliPayUtils.setPayListener(new AliPayUtils.OnAlipayListener() {
                        @Override
                        public void onSuccess() {
                            showToast(mContext.getString(R.string.Pay_for_success));
                            smartRefreshLoadPageHelper.refreshPage();
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
    public void userPaymentFailed(int request, String message) {
        switch (request) {
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
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
    }
}
