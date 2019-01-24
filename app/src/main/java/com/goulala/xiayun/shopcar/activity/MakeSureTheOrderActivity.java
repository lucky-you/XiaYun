package com.goulala.xiayun.shopcar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnsunrun.alipaylibrary.alipay.AliPayUtils;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.callback.CancelPaymentClickListener;
import com.goulala.xiayun.common.dialog.PasswordDialogUtils;
import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.ButtonClickUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.InputMethodKeyBroadUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.LogUtils;
import com.goulala.xiayun.common.utils.PhoneUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.common.utils.StringUtils;
import com.goulala.xiayun.common.view.PasswordEditText;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.home.dialog.ThePaymentDialog;
import com.goulala.xiayun.mycenter.activity.AllOrdersActivity;
import com.goulala.xiayun.mycenter.activity.ResetCommissionPaymentPasswordActivity;
import com.goulala.xiayun.mycenter.activity.TheCouponsActivity;
import com.goulala.xiayun.mycenter.activity.TheMemberCenterActivity;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;
import com.goulala.xiayun.mycenter.model.TheMemberCenterBean;
import com.goulala.xiayun.mycenter.model.UserIsMembersBean;
import com.goulala.xiayun.mycenter.model.VipCouponTicketList;
import com.goulala.xiayun.shopcar.adapter.MakeSureTheOrderAdapter;
import com.goulala.xiayun.shopcar.model.GoodItemList;
import com.goulala.xiayun.shopcar.model.MerchantMessage;
import com.goulala.xiayun.shopcar.model.OrderMessage;
import com.goulala.xiayun.shopcar.presenter.MakeSureTheOrderPresenter;
import com.goulala.xiayun.shopcar.view.IMakeSureTheOrderView;
import com.goulala.xiayun.wxapi.WeiXinPayUtils;
import com.goulala.xiayun.wxapi.WxPayReqInfo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 确认订单
 */
public class MakeSureTheOrderActivity extends BaseMvpActivity<MakeSureTheOrderPresenter> implements IMakeSureTheOrderView {


    private ImageView ivAddressIcon;
    private TextView tvTheConsigneeName;
    private TextView tvPhoneNumber;
    private TextView tvShippingAddress;
    private ImageView ivToMoreIcon;
    private RelativeLayout rlGoodHaveAddressLayout;
    private TextView tvNoSelectAddress;
    private RelativeLayout rlGoodNoHaveAddressLayout;
    private RecyclerView publicRecyclerView;
    private TextView tvTheTotalAmountOfGoods;
    private TextView tvTheFreightInTotal;
    private TextView tvPreferentialActivitiesOfPrice;
    private RelativeLayout rlPreferentialActivities;
    private View DividePreferentialActivities;
    private TextView tvFullReductionOfCoupons;
    private TextView tvOpenTheMemberToEnjoyTheDiscount;
    private RelativeLayout rlOpenTheMember;
    private TextView tvTheTotalMoney;
    private TextView tvSubmitOrders;
    private NestedScrollView nestedScrollView;

    private MakeSureTheOrderAdapter makeSureTheOrderAdapter;
    private int addressId; //收货地址
    private ArrayList<GoodItemList> goodItemListArrayList = new ArrayList<>(); //需要支付的商品的信息
    private String shareUserId, couponId;
    private List<MerchantMessage> merchantMessageList = new ArrayList<>(); //店铺的商品信息
    private String payMoney; // 需要支付的订单金额
    private String balanceMoney;//用户的余额
    private String payOrderNumber; //用户的支付订单

    public static void start(Context context, ArrayList<GoodItemList> goodItemLists, String shareUserId, String couponId) {
        Intent intent = new Intent(context, MakeSureTheOrderActivity.class);
        intent.putParcelableArrayListExtra(ApiParam.ARRAY_LIST, goodItemLists);
        intent.putExtra(ApiParam.SHARE_USER_ID, shareUserId);
        intent.putExtra(ApiParam.COUPON_ID, couponId);
        context.startActivity(intent);
    }


    @Override
    protected MakeSureTheOrderPresenter createPresenter() {
        return new MakeSureTheOrderPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        goodItemListArrayList = getIntent().getParcelableArrayListExtra(ApiParam.ARRAY_LIST);
        shareUserId = getIntent().getStringExtra(ApiParam.SHARE_USER_ID);
        couponId = getIntent().getStringExtra(ApiParam.COUPON_ID);
    }

    @Override
    public int loadViewLayout() {
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_make_sure_the_order;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Make_sure_the_order));
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        registerEvent();
        ivAddressIcon = get(R.id.iv_address_icon);
        tvTheConsigneeName = get(R.id.tv_The_consignee_name);
        tvPhoneNumber = get(R.id.tv_phone_number);
        tvShippingAddress = get(R.id.tv_Shipping_address);
        ivToMoreIcon = get(R.id.iv_to_more_icon);
        rlGoodHaveAddressLayout = get(R.id.rl_good_have_address_layout);
        rlGoodHaveAddressLayout.setOnClickListener(this);
        tvNoSelectAddress = get(R.id.tv_no_select_address);
        rlGoodNoHaveAddressLayout = get(R.id.rl_good_no_have_address_layout);
        rlGoodNoHaveAddressLayout.setOnClickListener(this);
        publicRecyclerView = get(R.id.public_RecyclerView);
        tvTheTotalAmountOfGoods = get(R.id.tv_The_total_amount_of_goods);
        tvTheFreightInTotal = get(R.id.tv_The_freight_in_total);
        tvPreferentialActivitiesOfPrice = get(R.id.tv_Preferential_activities_of_price);
        rlPreferentialActivities = get(R.id.rl_Preferential_activities);
        rlPreferentialActivities.setOnClickListener(this);
        DividePreferentialActivities = get(R.id.Divide_Preferential_activities);
        tvFullReductionOfCoupons = get(R.id.tv_Full_reduction_of_coupons);
        tvFullReductionOfCoupons.setOnClickListener(this);
        tvOpenTheMemberToEnjoyTheDiscount = get(R.id.tv_Open_the_member_to_enjoy_the_discount);
        tvOpenTheMemberToEnjoyTheDiscount.setOnClickListener(this);
        rlOpenTheMember = get(R.id.rl_Open_the_member);
        rlOpenTheMember.setOnClickListener(this);
        tvTheTotalMoney = get(R.id.tv_the_total_money);
        tvSubmitOrders = get(R.id.tv_Submit_orders);
        tvSubmitOrders.setOnClickListener(this);
        nestedScrollView = get(R.id.nestedScrollView);
        InputMethodKeyBroadUtils.hideKeyboard(this);
        tvFullReductionOfCoupons.setText("");
        nestedScrollView.smoothScrollTo(0, 20);
        publicRecyclerView.setFocusable(false);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        getShoppingAddressList();
        makeSureTheOrderAdapter = new MakeSureTheOrderAdapter(merchantMessageList);
        publicRecyclerView.setAdapter(makeSureTheOrderAdapter);
        publicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        publicRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 12));
        publicRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDate();
    }


    private void getDate() {
        //获取订单信息
        getOrderMessageOrSubmitOrders(ConstantValue.GET_ORDER_MESSAGE_CODE, ApiParam.GET_ORDER_MESSAGE_URL, JsonUtils.toJson(goodItemListArrayList));
        //获取用户账户的余额
        getAccountBalanceOrCheckPasswordExist(ApiParam.ACCOUNT_BALANCE_URL, ApiParam.GET_ACCOUNT_BALANCE_TYPE);
    }

    /**
     * 获取收货地址的列表
     */
    private void getShoppingAddressList() {
        Map<String, String> shoppingListParam = new HashMap<>();
        shoppingListParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_THE_ADDRESS_LIST_URL);
        String shoppingListParamJson = JsonUtils.toJson(shoppingListParam);
        LogUtils.showLog(userToken, shoppingListParamJson);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getShoppingAddress(userToken, shoppingListParamJson);


    }

    /**
     * 获取订单信息,提交订单公用
     */
    private void getOrderMessageOrSubmitOrders(int requestType, String apiUrl, String itemInfoMessage) {
        Map<String, String> orderMessageParam = new HashMap<>();
        orderMessageParam.put(ApiParam.BASE_METHOD_KEY, apiUrl);
        orderMessageParam.put(ApiParam.ITEM_INFO, itemInfoMessage);
        orderMessageParam.put(ApiParam.SHARE_USER_ID, shareUserId);
        orderMessageParam.put(ApiParam.COUPON_ID, couponId);
        orderMessageParam.put(ApiParam.ADDRESS_ID, String.valueOf(addressId));
        String orderMessageParamJson = JsonUtils.toJson(orderMessageParam);
        if (TextUtils.isEmpty(userToken)) return;
        if (requestType == ConstantValue.GET_ORDER_MESSAGE_CODE) {
            // 获取订单信息
            mvpPresenter.getOrderMessageOrSubmitOrder(ConstantValue.GET_ORDER_MESSAGE_CODE, userToken, orderMessageParamJson);
        } else {
            //提交订单
            mvpPresenter.getOrderMessageOrSubmitOrder(ConstantValue.SUBMIT_ORDER_MESSAGE_CODE, userToken, orderMessageParamJson);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Notice notice) {
        if (notice != null) {
            if (notice.type == ConstantValue.THE_USER_MESSAGE_TYPE) {
                String message = (String) notice.contentOne;
                int goodID = (int) notice.contentTwo;
                if (!TextUtils.isEmpty(message)) {
                    //对比商品的id,确定到底是那件商品的留言
                    for (int i = 0; i < goodItemListArrayList.size(); i++) {
                        if (goodID == goodItemListArrayList.get(i).getItem_id()) {
                            goodItemListArrayList.get(i).setRemark(message);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.rl_good_have_address_layout:
//                break;
            case R.id.rl_good_no_have_address_layout:
                if (!ButtonClickUtils.isFastClick()) {
                    Intent intent = new Intent(mContext, ShippingAddressActivity.class);
                    intent.putExtra(ConstantValue.TYPE, ConstantValue.GET_SHOPPING_ADDRESS_RETURN_TO_ORDER_ACTIVITY);
                    startActivityForResult(intent, ConstantValue.GET_SHOPPING_ADDRESS_RETURN_TO_ORDER_ACTIVITY);
                }

                break;
            case R.id.rl_Preferential_activities:
                //活动特惠
                break;
            case R.id.tv_Full_reduction_of_coupons:
                //优惠券
                if (!ButtonClickUtils.isFastClick()) {
                    Intent intentOne = new Intent(mContext, TheCouponsActivity.class);
                    intentOne.putExtra(ConstantValue.TYPE, ConstantValue.CHOOSE_COUPONS_RETURN_TO_ORDER_ACTIVITY);
                    intentOne.putExtra(ConstantValue.MONEY, payMoney);
                    startActivityForResult(intentOne, ConstantValue.CHOOSE_COUPONS_RETURN_TO_ORDER_ACTIVITY);
                }
                break;
            case R.id.rl_Open_the_member:
            case R.id.tv_Open_the_member_to_enjoy_the_discount:
                //开通plus会员
                if (!ButtonClickUtils.isFastClick()) {
                    TheMemberCenterActivity.start(mContext);
                }
                break;
            case R.id.tv_Submit_orders:
                if (!ButtonClickUtils.isFastClick()) {
                    if (addressId == 0) {
                        showToast(mContext.getString(R.string.Please_fill_in_the_shipping_address));
                        return;
                    }
                    //提交订单
                    getOrderMessageOrSubmitOrders(ConstantValue.SUBMIT_ORDER_MESSAGE_CODE, ApiParam.SUBMIT_ORDER_URL, JsonUtils.toJson(goodItemListArrayList));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantValue.GET_SHOPPING_ADDRESS_RETURN_TO_ORDER_ACTIVITY:
                //收货地址
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        rlGoodHaveAddressLayout.setVisibility(View.VISIBLE);
                        rlGoodNoHaveAddressLayout.setVisibility(View.GONE);
                        addressId = data.getIntExtra(ApiParam.ADDRESS_ID, -1);
                        String userName = data.getStringExtra(ApiParam.NAME);
                        String userMobil = data.getStringExtra(ApiParam.MOBILE_KEY);
                        String userAddressDetails = data.getStringExtra(ApiParam.ADDRESS);
                        setAddressToView(userName, userMobil, userAddressDetails);
                        getDate();
                    }
                }
                break;
            case ConstantValue.CHOOSE_COUPONS_RETURN_TO_ORDER_ACTIVITY:
                //优惠券
                if (resultCode == ConstantValue.CHOOSE_COUPONS_RETURN_TO_ORDER_ACTIVITY) {
                    if (data != null) {
                        couponId = data.getStringExtra(ConstantValue.ID);
                        String FullPriceReduction = data.getStringExtra(ConstantValue.FULL_PRICE);
                        String PreferentialPrice = data.getStringExtra(ConstantValue.PREFERENTIAL_PRICE);
                        tvFullReductionOfCoupons.setText(mContext.getString(R.string.Full_reduction, FullPriceReduction, PreferentialPrice));
                        getDate();
                    }
                }
                break;
            default:
                break;

        }
    }

    /**
     * 显示收货地址
     */
    private void setAddressToView(String userName, String userMobil, String addressDetails) {
        tvTheConsigneeName.setText(mContext.getString(R.string.The_consignee, userName));
        tvPhoneNumber.setText(PhoneUtils.mobilNumber(userMobil));
        tvShippingAddress.setText(mContext.getString(R.string.Shipping_address, addressDetails));
    }

    @Override
    public void getShoppingAddressSuccess(List<ShoppingAddressList> shoppingAddressLists) {
        if (shoppingAddressLists != null && !shoppingAddressLists.isEmpty()) {
            rlGoodHaveAddressLayout.setVisibility(View.VISIBLE);
            rlGoodNoHaveAddressLayout.setVisibility(View.GONE);
            addressId = shoppingAddressLists.get(0).getId();
            String userName = shoppingAddressLists.get(0).getName();
            String userMobil = shoppingAddressLists.get(0).getMobile();
            String userAddressDetails = shoppingAddressLists.get(0).getProvince() + shoppingAddressLists.get(0).getCity()
                    + shoppingAddressLists.get(0).getArea() + shoppingAddressLists.get(0).getAddress();
            setAddressToView(userName, userMobil, userAddressDetails);
        } else {
            rlGoodHaveAddressLayout.setVisibility(View.GONE);
            rlGoodNoHaveAddressLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getOrderMessageOrSubmitOrderSuccess(int requestType, OrderMessage orderMessage) {
        if (orderMessage != null) {
            //获取订单信息
            if (requestType == ConstantValue.GET_ORDER_MESSAGE_CODE) {
                merchantMessageList = orderMessage.getList();
                if (merchantMessageList != null && merchantMessageList.size() > 0) {
                    makeSureTheOrderAdapter.setNewData(merchantMessageList);
                }
                String plusText = orderMessage.getPlus_str(); //是否是会员的描述
                SpannableString textStringThree = StringUtils.getTheCpecifiedTextColor(plusText, 2, 8, mContext.getResources().getColor(R.color.color_e53d3d));
                tvOpenTheMemberToEnjoyTheDiscount.setText(textStringThree);

                payMoney = String.valueOf(orderMessage.getPay_price());
                tvTheTotalAmountOfGoods.setText(mContext.getString(R.string.the_price, orderMessage.getTotal_price() + "")); //商品总额
                tvTheFreightInTotal.setText(mContext.getString(R.string.the_price, orderMessage.getTotal_freight() + ""));  //运费总计
                tvTheTotalMoney.setText(mContext.getString(R.string.the_total_money, payMoney)); //合计金额
                //活动特惠
                if (orderMessage.getDiscount_member_price() > 0) {
                    rlPreferentialActivities.setVisibility(View.VISIBLE);
                    DividePreferentialActivities.setVisibility(View.VISIBLE);
                    tvPreferentialActivitiesOfPrice.setText(mContext.getString(R.string.the_price_two, orderMessage.getDiscount_member_price() + ""));
                } else {
                    rlPreferentialActivities.setVisibility(View.GONE);
                    DividePreferentialActivities.setVisibility(View.GONE);
                }
            } else {
                //提交订单
                payMoney = String.valueOf(orderMessage.getPay_price());
                payOrderNumber = orderMessage.getPay_no();
                showPaymentDialog(payOrderNumber, payMoney, balanceMoney);
            }
        }
    }

    @Override
    public void useBalanceOrAliPayPaymentSuccess(int requestType, String orderMessage) {


        switch (requestType) {
            case ConstantValue.PAY_OF_BALANCE_TYPE:
                //余额支付成功
                if (!TextUtils.isEmpty(orderMessage)) {
                    //需要 判断是否设置了支付密码
                    showToast(orderMessage);
                    cancelPayOrPayFailedToJump(2);
                }
                break;
            case ConstantValue.PAY_OF_ALIPAY_TYPE:
                //支付宝支付成功
                if (!TextUtils.isEmpty(orderMessage)) {
                    userAliPayPaymentOrder(orderMessage, new AliPayUtils.OnAlipayListener() {
                        @Override
                        public void onSuccess() {
                            showToast(mContext.getString(R.string.Pay_for_success));
                            cancelPayOrPayFailedToJump(2);
                        }

                        @Override
                        public void onCancel() {
                            showToast(mContext.getString(R.string.Cancel_the_payment));
                            cancelPayOrPayFailedToJump(1);
                        }
                    });

                }
                break;
        }

    }

    @Override
    public void useBalanceOrAliPayPaymentFailed(int requestType, String message) {
        showToast(message);
        cancelPayOrPayFailedToJump(1);
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
                showPaymentDialog(payOrderNumber, payMoney, balanceMoney);
            }

            @Override
            public void determineClick(View view) {
                ResetCommissionPaymentPasswordActivity.start(mContext, ConstantValue.SET_THE_COMMISSION_PAYMENT_PASSWORD);
            }
        });
    }

    /**
     * 取消支付，或者支付失败的跳转
     */
    private void cancelPayOrPayFailedToJump(int jumpType) {
        AllOrdersActivity.start(mContext, jumpType);
        finish(); //销毁当前界面
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
                        showPaymentDialog(payOrderNumber, payMoney, balanceMoney);
                    }
                },
                new PasswordEditText.PasswordFullListener() {
                    @Override
                    public void passwordFull(String password) {
                        selectPayTypeToPaymentOrder(payOrderNumber, payMoney, password, 1);
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
            mvpPresenter.checkPasswordExist(userToken, checkParamJson);
        }

    }

    /**
     * 选择支付
     */
    private void showPaymentDialog(final String payOrderNumber, final String payMoney, final String balanceMoney) {
        String balance;
        //余额不能为空，且购买的金额小于余额
        if (!TextUtils.isEmpty(balanceMoney) && Double.valueOf(payMoney) <= Double.valueOf(balanceMoney)) {
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
                        selectPayTypeToPaymentOrder(payOrderNumber, payMoney, "", 2);
                        break;
                    case ThePaymentDialog.PAY_OF_WECHAT_TYPE:
                        //微信支付
                        selectPayTypeToPaymentOrder(payOrderNumber, payMoney, "", 3);
                        break;
                }
            }

            @Override
            public void closePayDialog() {
                cancelPayOrPayFailedToJump(1);
            }
        });
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
            case 1:
                mvpPresenter.useBalanceOrAliPayPayment(ConstantValue.PAY_OF_BALANCE_TYPE, userToken, paymentOrderParamJson);
                break;
            case 2:
                mvpPresenter.useBalanceOrAliPayPayment(ConstantValue.PAY_OF_ALIPAY_TYPE, userToken, paymentOrderParamJson);
                break;
            case 3:
                mvpPresenter.useWeChatPayment(userToken, paymentOrderParamJson);
                break;
        }
    }

    @Override
    public void useWeChatPaymentSuccess(WxPayReqInfo wxPayReqInfo) {
        if (wxPayReqInfo != null) {
            userWeChatPaymentOrder(wxPayReqInfo, new WeiXinPayUtils.OnWxPayResultListener() {
                @Override
                public void onSuccess() {
                    showToast(mContext.getString(R.string.Pay_for_success));
                    cancelPayOrPayFailedToJump(2);
                }

                @Override
                public void onCancel() {
                    showToast(mContext.getString(R.string.Cancel_the_payment));
                    cancelPayOrPayFailedToJump(1);
                }
            });
        }
    }

    @Override
    public void useWeChatPaymentFailed(String message) {
        showToast(message);
        cancelPayOrPayFailedToJump(1);
    }

    @Override
    public void checkPasswordExist(boolean isExist) {
        if (isExist) {
            //设置了密码，直接输密码，支付
            editPayPassword();
        } else {
            //没有设置密码，去设置界面，设置支付密码
            setCommissionPaymentPassword();
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
    public void useIsMembers(UserIsMembersBean userIsMembersBean) {

    }

    @Override
    public void getMemberCenterDate(TheMemberCenterBean theMemberCenterBean) {

    }

    @Override
    public void getCouponSuccess(List<VipCouponTicketList> vipCouponTicketLists) {

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
