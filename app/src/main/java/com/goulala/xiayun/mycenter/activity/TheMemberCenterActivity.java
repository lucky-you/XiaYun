package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cnsunrun.alipaylibrary.alipay.AliPayUtils;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.activity.WebDetailsActivity;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.callback.CancelPaymentClickListener;
import com.goulala.xiayun.common.dialog.PasswordDialogUtils;
import com.goulala.xiayun.common.loading.LoadingController;
import com.goulala.xiayun.common.loading.LoadingUtils;
import com.goulala.xiayun.common.loading.OnNextClickListener;
import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.SizeUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.common.utils.StringUtils;
import com.goulala.xiayun.common.utils.UserUtils;
import com.goulala.xiayun.common.view.PasswordEditText;
import com.goulala.xiayun.common.widget.DividerGridItemDecoration;
import com.goulala.xiayun.common.widget.VipSpacesItemDecoration;
import com.goulala.xiayun.home.dialog.ThePaymentDialog;
import com.goulala.xiayun.mycenter.adapter.DiscountTicketAdapter;
import com.goulala.xiayun.mycenter.adapter.TheMemberCenterAdapter;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;
import com.goulala.xiayun.mycenter.model.TheMemberCenterBean;
import com.goulala.xiayun.mycenter.model.TheMemberCenterList;
import com.goulala.xiayun.mycenter.model.UserIsMembersBean;
import com.goulala.xiayun.mycenter.model.VipCouponTicketList;
import com.goulala.xiayun.shopcar.model.OrderMessage;
import com.goulala.xiayun.shopcar.presenter.MakeSureTheOrderPresenter;
import com.goulala.xiayun.shopcar.view.IMakeSureTheOrderView;
import com.goulala.xiayun.wxapi.WeiXinPayUtils;
import com.goulala.xiayun.wxapi.WxPayReqInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员中心
 */
public class TheMemberCenterActivity extends BaseMvpActivity<MakeSureTheOrderPresenter> implements IMakeSureTheOrderView,
        BaseQuickAdapter.OnItemClickListener {

    private TextView tvUserNameOfNotMember;
    private TextView tvOpenMemberHitMessage;
    private LinearLayout llNotMemberHitMessage;
    private TextView tvHaveMemberUserName;
    private LinearLayout llIsVipBackground;
    private TextView tvOpeningMemberMessage;
    private RecyclerView memberRecyclerView;
    private TextView tvImmediatelyOpened;
    private TextView tvUserAgreement;
    private TheMemberCenterAdapter theMemberCenterAdapter;
    private List<TheMemberCenterList> theMemberCenterLists = new ArrayList<>();
    private String userNickName, theMemberStatus, userMemberEndTime;
    private int vipRuleId; //开通会员id
    private String payOrderMoney; //需要支付的金额
    private String balanceMoney;//用户的账户余额
    private int openType = 1; //	开通类型 1:开通 2:续费
    private LoadingController loadingController;
    private RecyclerView ticketRecyclerView;
    private RelativeLayout rlTicket;
    private TextView tvPrice;
    private TextView overduePrice;
    private int mVipRuleId;
    private List<VipCouponTicketList> vipCouponTicketMessageList = new ArrayList<>();
    private DiscountTicketAdapter discountTicketAdapter;
    private LinearLayout llCouponLayout;

    public static void start(Context context) {
        Intent intent = new Intent(context, TheMemberCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected MakeSureTheOrderPresenter createPresenter() {
        return new MakeSureTheOrderPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_the_member_center;
    }

    @Override
    public void bindViews(View contentView) {
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        get(R.id.iv_member_back).setOnClickListener(this);
        tvUserNameOfNotMember = get(R.id.tv_user_name_of_not_member);
        tvOpenMemberHitMessage = get(R.id.tv_open_member_hit_message);
        llNotMemberHitMessage = get(R.id.ll_not_member_hit_message);
        tvHaveMemberUserName = get(R.id.tv_have_member_user_name);
        llIsVipBackground = get(R.id.ll_is_vip_background);
        tvOpeningMemberMessage = get(R.id.tv_Opening_member_message);
        memberRecyclerView = get(R.id.member_recyclerView);
        tvImmediatelyOpened = get(R.id.tv_Immediately_opened);
        tvImmediatelyOpened.setOnClickListener(this);
        tvUserAgreement = get(R.id.tv_User_agreement);
        tvUserAgreement.setOnClickListener(this);
        ticketRecyclerView = get(R.id.ticket_recyclerView);
        rlTicket = get(R.id.rl_ticket);
        tvPrice = get(R.id.tv_price);
        overduePrice = get(R.id.Overdue_price);
        llCouponLayout = get(R.id.llCouponLayout);
        if (userInfo != null) {
            userNickName = UserUtils.getNickName();
        }
        loadingController = LoadingUtils.showLoadingView(mContext, get(R.id.ll_root_layout), new OnNextClickListener() {
            @Override
            public void onNextClick() {
                getDate();
            }
        });
        getDate();
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        theMemberCenterAdapter = new TheMemberCenterAdapter(theMemberCenterLists, 0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        memberRecyclerView.setLayoutManager(linearLayoutManager);
        memberRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, mContext.getResources().getColor(R.color.white), 5));
        memberRecyclerView.setAdapter(theMemberCenterAdapter);
        theMemberCenterAdapter.setOnItemClickListener(this);

        discountTicketAdapter = new DiscountTicketAdapter(vipCouponTicketMessageList);
        ticketRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ticketRecyclerView.addItemDecoration(new VipSpacesItemDecoration(SizeUtils.dp2px(15), SizeUtils.dp2px(15), SizeUtils.dp2px(15), SizeUtils.dp2px(15)));
        ticketRecyclerView.setAdapter(discountTicketAdapter);
    }

    private void getDate() {
        //是否是会员
        checkUserIsMember();
        //获取数据
        getMemberDate();
        //获取用户账户的余额
        getAccountBalanceOrCheckPasswordExist(ApiParam.ACCOUNT_BALANCE_URL, ApiParam.GET_ACCOUNT_BALANCE_TYPE);
    }


    /**
     * 判断是否是plus会员
     */
    private void checkUserIsMember() {
        Map<String, String> checkParam = new HashMap<>();
        checkParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CHECK_USER_IS_MEMBER_URL);
        String checkParamJson = JsonUtils.toJson(checkParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.useIsMembers(userToken, checkParamJson);
        }
    }

    /**
     * 获取开通会员或者续费会员的规则
     */
    private void getMemberDate() {
        Map<String, String> memberParam = new HashMap<>();
        memberParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.OPEN_OR_RENEWAL_MEMBER_URL);
        String memberParamJson = JsonUtils.toJson(memberParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getMemberCenterDate(userToken, memberParamJson);
        }
    }

    /**
     * 获取优惠券列表
     */
    private void getCouponList() {
        HashMap<String, String> CouponHashMap = new HashMap<>();
        CouponHashMap.put(ApiParam.BASE_METHOD_KEY, ApiParam.COUPON_URL);
        CouponHashMap.put("rule_id", String.valueOf(mVipRuleId));
        CouponHashMap.put("page", "1");
        CouponHashMap.put("size", "999");
        String CouponHashMapParam = JsonUtils.toJson(CouponHashMap);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getCouponList(userToken, CouponHashMapParam);
        }
    }

    /**
     * 展示支付界面的dialog
     */
    private void showPaymentDialog() {
        AlertDialogUtils.showPaymentDialog(mContext, payOrderMoney, balanceMoney, new ThePaymentDialog.OnPayResultListener() {
            @Override
            public void chooseTypeToPay(int type) {
                switch (type) {
                    case ThePaymentDialog.PAY_OF_BALANCE_TYPE:
                        if (TextUtils.isEmpty(balanceMoney)) {
                            showToast(mContext.getString(R.string.not_payment_of_order));
                            return;
                        }
                        //余额支付,需要判断余额够不够支付订单金额，同时还要判断，有没有设置支付密码。
                        if (Double.valueOf(payOrderMoney) <= Double.valueOf(balanceMoney)) {
                            //支付的金额小于余额，继续支付，走检查是否设置密码的流程
                            getAccountBalanceOrCheckPasswordExist(ApiParam.DOES_PASSWORD_EXIST_URL, ApiParam.CHECK_PASSWORD_EXIST_TYPE);
                        } else {
                            //支付的金额大于余额
                            showToast(mContext.getString(R.string.Lack_of_balance));
                        }
                        break;
                    case ThePaymentDialog.PAY_OF_ALIPAY_TYPE:
                        //支付宝支付
                        selectPayTypeToPaymentOrder("", ConstantValue.PAY_OF_ALIPAY_TYPE);
                        break;
                    case ThePaymentDialog.PAY_OF_WECHAT_TYPE:
                        //微信支付
                        selectPayTypeToPaymentOrder("", ConstantValue.PAY_OF_WECHAT_TYPE);
                        break;
                }
            }

            @Override
            public void closePayDialog() {

            }
        });

    }

    /**
     * 获取用户的余额,和,检查是否设置了支付密码公用
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

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_member_back:
                finish();
                break;
            case R.id.tv_Immediately_opened:
                //开通或者续费
                showPaymentDialog();
                break;
            case R.id.tv_User_agreement:
                //服务协议
                WebDetailsActivity.start(mContext, ConstantValue.MEMBER_SERVICE_AGREEMENT_URL);
                break;
        }
    }

    @Override
    public void getShoppingAddressSuccess(List<ShoppingAddressList> shoppingAddressLists) {

    }

    @Override
    public void getOrderMessageOrSubmitOrderSuccess(int requestType, OrderMessage orderMessage) {

    }

    @Override
    public void useBalanceOrAliPayPaymentSuccess(int requestType, String orderMessage) {
        switch (requestType) {
            case ConstantValue.PAY_OF_BALANCE_TYPE:
                //余额支付成功
                switch (theMemberStatus) {
                    case TheMemberCenterBean.OPEN_TYPE:
                        showToast(mContext.getString(R.string.vip_Opened_successfully));
                        break;
                    case TheMemberCenterBean.RENEW_TYPE:
                        showToast(mContext.getString(R.string.vip_Continue_FeiChengGong));
                        break;
                }
                break;
            case ConstantValue.PAY_OF_ALIPAY_TYPE:
                //支付宝支付成功
                if (!TextUtils.isEmpty(orderMessage)) {
                    userAliPayPaymentOrder(orderMessage, new AliPayUtils.OnAlipayListener() {
                        @Override
                        public void onSuccess() {
                            switch (theMemberStatus) {
                                case TheMemberCenterBean.OPEN_TYPE:
                                    showToast(mContext.getString(R.string.vip_Opened_successfully));
                                    break;
                                case TheMemberCenterBean.RENEW_TYPE:
                                    showToast(mContext.getString(R.string.vip_Continue_FeiChengGong));
                                    break;
                            }
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
    public void useBalanceOrAliPayPaymentFailed(int requestType, String message) {
        switch (requestType) {
            case ConstantValue.PAY_OF_BALANCE_TYPE:
                //余额支付失败
                showToast(message);
                break;
            case ConstantValue.PAY_OF_ALIPAY_TYPE:
                //支付宝支付失败
                showToast(message);
                break;
        }
    }

    @Override
    public void useWeChatPaymentSuccess(WxPayReqInfo wxPayReqInfo) {
        //微信支付成功
        if (wxPayReqInfo != null) {
            userWeChatPaymentOrder(wxPayReqInfo, new WeiXinPayUtils.OnWxPayResultListener() {
                @Override
                public void onSuccess() {
                    switch (theMemberStatus) {
                        case TheMemberCenterBean.OPEN_TYPE:
                            showToast(mContext.getString(R.string.vip_Opened_successfully));
                            break;
                        case TheMemberCenterBean.RENEW_TYPE:
                            showToast(mContext.getString(R.string.vip_Continue_FeiChengGong));
                            break;
                    }
                    finish();
                }

                @Override
                public void onCancel() {
                    showToast(mContext.getString(R.string.Cancel_the_payment));
                }
            });

        }
    }

    @Override
    public void useWeChatPaymentFailed(String message) {
        showToast(message);
    }

    @Override
    public void checkPasswordExist(boolean isExist) {
        loadingController.dismissLoading();
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
        loadingController.dismissLoading();
        if (balance != null) {
            //获取到了余额
            balanceMoney = balance.getAmount();
        }
    }

    @Override
    public void useIsMembers(UserIsMembersBean userIsMembersBean) {
        loadingController.dismissLoading();
        if (userIsMembersBean != null) {
            long memberEndTime = userIsMembersBean.getEnd_time();
            if (memberEndTime > 0) {
                //是plus会员，此时间为会员到期时间
                userMemberEndTime = DateUtils.getStrTime(memberEndTime);
            } else {
                //不是会员

            }
        }
    }

    @Override
    public void getMemberCenterDate(TheMemberCenterBean theMemberCenterBean) {
        loadingController.dismissLoading();
        if (theMemberCenterBean != null) {
            this.theMemberStatus = theMemberCenterBean.getType();
            switch (theMemberStatus) {
                case TheMemberCenterBean.OPEN_TYPE:
                    //不是plus会员，需要开通会员
                    openType = 1;
                    llIsVipBackground.setBackground(mContext.getResources().getDrawable(R.drawable.ic_member_card_view_background));
                    llNotMemberHitMessage.setVisibility(View.VISIBLE);
                    tvHaveMemberUserName.setVisibility(View.GONE);
                    tvUserNameOfNotMember.setText(mContext.getString(R.string.Is_not_a_member, userNickName));
                    tvOpeningMemberMessage.setText(theMemberCenterBean.getTitle());
                    tvImmediatelyOpened.setText(mContext.getString(R.string.Immediately_opened));
                    String textOne = mContext.getString(R.string.User_agreement_one);
                    SpannableString textStringOne = StringUtils.getTheCpecifiedTextColor(textOne, 6, textOne.length(), mContext.getResources().getColor(R.color.color_ad7a2b));
                    tvUserAgreement.setText(textStringOne);
                    break;
                case TheMemberCenterBean.RENEW_TYPE:
                    rlTicket.setVisibility(View.GONE);
                    tvOpenMemberHitMessage.setVisibility(View.GONE);
                    llCouponLayout.setVisibility(View.GONE);
                    //已经是plus会员，界面展示续费
                    openType = 2;
                    llIsVipBackground.setBackground(mContext.getResources().getDrawable(R.drawable.ic_member_card_view_vip_background));
                    llNotMemberHitMessage.setVisibility(View.GONE);
                    tvHaveMemberUserName.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(userMemberEndTime)) {
                        tvHaveMemberUserName.setText(mContext.getString(R.string.Already_member, userNickName, userMemberEndTime));
                    }
                    tvOpeningMemberMessage.setText(theMemberCenterBean.getTitle());
                    tvImmediatelyOpened.setText(mContext.getString(R.string.Immediately_renewal));
                    tvImmediatelyOpened.setTextColor(mContext.getResources().getColor(R.color.white));
                    String textTwo = mContext.getString(R.string.User_agreement_two);
                    SpannableString textStringTwo = StringUtils.getTheCpecifiedTextColor(textTwo, 6, textTwo.length(), mContext.getResources().getColor(R.color.color_ad7a2b));
                    tvUserAgreement.setText(textStringTwo);
                    break;
            }
            this.theMemberCenterLists = theMemberCenterBean.getData();
            if (theMemberCenterLists.size() > 0) {
                vipRuleId = theMemberCenterLists.get(0).getId();
                theMemberCenterAdapter.setNewData(theMemberCenterLists);
                if (1 == theMemberCenterLists.get(0).getDiscount_type()) {
                    payOrderMoney = theMemberCenterLists.get(0).getOpen_price();
                } else {
                    payOrderMoney = theMemberCenterLists.get(0).getDiscount_open_price();

                }
            }
            //设置布局下边的信息 ，默认为第一个
            List<TheMemberCenterList> data = theMemberCenterBean.getData();
            if (data.size() > 0) {
                TheMemberCenterList theMemberCenterList = data.get(0);
                if (theMemberCenterList.getDiscount_type() == 1) {   //1不在优惠价  2在优惠期间
                    tvPrice.setText(mContext.getString(R.string.the_money, theMemberCenterList.getOpen_price()));
                    overduePrice.setVisibility(View.GONE);
                } else {
                    tvPrice.setText(mContext.getString(R.string.the_money, theMemberCenterList.getDiscount_open_price()));
                    overduePrice.setVisibility(View.VISIBLE);
                    overduePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    overduePrice.setText(theMemberCenterList.getOpen_price());
                }
                mVipRuleId = theMemberCenterList.getId();
                getCouponList();
            }

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
                        showPaymentDialog();
                    }
                },
                new PasswordEditText.PasswordFullListener() {
                    @Override
                    public void passwordFull(String password) {
                        selectPayTypeToPaymentOrder(password, ConstantValue.PAY_OF_BALANCE_TYPE);
                    }
                });

    }

    /**
     * 选择支付方式开用会员或者续费会员
     */
    private void selectPayTypeToPaymentOrder(String password, int payType) {
        Map<String, String> paymentOrderParam = new HashMap<>();
        paymentOrderParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.OPEN_OR_RENEWAL_MEMBER_CLICK_URL);
        paymentOrderParam.put(ApiParam.VIP_RULE_ID, String.valueOf(vipRuleId));
        paymentOrderParam.put(ApiParam.TYPE, String.valueOf(payType));
        paymentOrderParam.put(ApiParam.OPEN_TYPE, String.valueOf(openType));
        paymentOrderParam.put(ApiParam.PAYPASS, password);
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
     * 去设置支付密码
     */
    private void setCommissionPaymentPassword() {
        String dialogTitle = mContext.getString(R.string.You_have_not_set_the_commission_payment_password);
        String dialogLeft = mContext.getString(R.string.Temporarily_not_set);
        String dialogRight = mContext.getString(R.string.Immediately_set);
        AlertDialogUtils.showCustomerDialog(mContext, dialogTitle, dialogLeft, dialogRight, new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {
                showPaymentDialog();
            }

            @Override
            public void determineClick(View view) {
                ResetCommissionPaymentPasswordActivity.start(mContext, ConstantValue.SET_THE_COMMISSION_PAYMENT_PASSWORD);
            }
        });
    }

    @Override
    public void getCouponSuccess(List<VipCouponTicketList> vipCouponTicketLists) {
        if (vipCouponTicketLists.isEmpty()) return;
        discountTicketAdapter.setNewData(vipCouponTicketLists);
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
        loadingController.showError();
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        theMemberCenterAdapter.setPosition(position);
        vipRuleId = theMemberCenterAdapter.getItem(position).getId();
        mVipRuleId = vipRuleId;
        payOrderMoney = theMemberCenterAdapter.getItem(position).getDiscount_open_price();
        //设置布局下边的信息
        TheMemberCenterList theMemberCenterList = theMemberCenterAdapter.getItem(position);
        if (theMemberCenterList != null) {
            if (theMemberCenterList.getDiscount_type() == 1) {   //1不在优惠价  2在优惠期间
                tvPrice.setText(mContext.getString(R.string.the_money, theMemberCenterList.getOpen_price()));
                overduePrice.setVisibility(View.GONE);
            } else {
                tvPrice.setText(mContext.getString(R.string.the_money, theMemberCenterList.getDiscount_open_price()));
                overduePrice.setVisibility(View.VISIBLE);
                overduePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                overduePrice.setText(theMemberCenterList.getOpen_price());
            }
        }
        getCouponList();
    }
}
