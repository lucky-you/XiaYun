package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.mycenter.adapter.ShopListMessageAdapter;
import com.goulala.xiayun.mycenter.dialog.OperatingResultDialog;
import com.goulala.xiayun.mycenter.model.ApplyRefundDetailsDate;
import com.goulala.xiayun.mycenter.model.BankCardList;
import com.goulala.xiayun.mycenter.model.RecordBean;
import com.goulala.xiayun.mycenter.model.ShopItemMessage;
import com.goulala.xiayun.mycenter.presenter.TheGoodDetailsRefundPresenter;
import com.goulala.xiayun.mycenter.view.ITheGoodDetailsRefundView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退款售后详情-->只在待发货的情况下申请退款的使用
 */
public class TheGoodDetailsRefundActivity extends BaseMvpActivity<TheGoodDetailsRefundPresenter> implements ITheGoodDetailsRefundView {

    private TextView tvRefundStatus;
    private RecyclerView publicRecyclerView;
    private TextView tvTheRefundAmount;
    private TextView tvWithdrawalsReason;
    private TextView tvToApplyForTime;
    private TextView tvReviewTheMessage;
    private View DivideBottomLine;
    private TextView tvOrderStatusOne;
    private TextView tvOrderStatusTwo;
    private TextView tvOrderStatusThree;
    private LinearLayout llBottomStatusLayout;
    private LinearLayout llCommunicationRecordLayout;
    private ImageView ivSubmitAnApplication;
    private TextView tvSubmitAnApplication;
    private ImageView ivTheReviewOfTheService;
    private TextView tvTheReviewOfTheService;
    private ImageView ivFastMoney;
    private TextView tvFastMoney;
    private ImageView ivRefundToComplete;
    private TextView tvRefundToComplete;
    private SmartRefreshLayout smartRefreshLayout;
    private String applyRefundServiceOrder;
    private int applyRefundStatus; //申请退款的状态
    private ShopListMessageAdapter shopListMessageAdapter;
    private List<ShopItemMessage> shopItemMessageList;
    private String ReviewMessage; //审核留言
    private String shopOrderNumber;//店铺的订单编号
    private OperatingResultDialog operatingResultDialog;

    public static void start(Context context, String applyOrder) {
        Intent intent = new Intent(context, TheGoodDetailsRefundActivity.class);
        intent.putExtra(ConstantValue.ORDER_NUMBER, applyOrder);
        context.startActivity(intent);
    }

    @Override
    protected TheGoodDetailsRefundPresenter createPresenter() {
        return new TheGoodDetailsRefundPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_details_of_the_refund;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Details_of_the_refund));
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        tvRefundStatus = get(R.id.tv_refund_status);
        publicRecyclerView = get(R.id.public_RecyclerView);
        tvTheRefundAmount = get(R.id.tv_The_refund_amount);
        tvWithdrawalsReason = get(R.id.tv_Withdrawals_reason);
        tvToApplyForTime = get(R.id.tv_To_apply_for_time);
        get(R.id.rl_Communication_record).setOnClickListener(this);
        tvReviewTheMessage = get(R.id.tv_Review_the_message);
        DivideBottomLine = get(R.id.Divide_Bottom_Line);
        tvOrderStatusOne = get(R.id.tv_order_status_one);
        tvOrderStatusOne.setOnClickListener(this);
        tvOrderStatusTwo = get(R.id.tv_order_status_two);
        tvOrderStatusTwo.setOnClickListener(this);
        tvOrderStatusThree = get(R.id.tv_order_status_three);
        tvOrderStatusThree.setOnClickListener(this);
        llBottomStatusLayout = get(R.id.ll_bottom_status_layout);
        llCommunicationRecordLayout = get(R.id.ll_Communication_record_layout);
        ivSubmitAnApplication = get(R.id.iv_Submit_an_application);
        tvSubmitAnApplication = get(R.id.tv_Submit_an_application);
        ivTheReviewOfTheService = get(R.id.iv_The_review_of_the_service);
        tvTheReviewOfTheService = get(R.id.tv_The_review_of_the_service);
        ivFastMoney = get(R.id.iv_Fast_money);
        tvFastMoney = get(R.id.tv_Fast_money);
        ivRefundToComplete = get(R.id.iv_Refund_to_complete);
        tvRefundToComplete = get(R.id.tv_Refund_to_complete);
        smartRefreshLayout = get(R.id.smartRefreshLayout);

        //底部布局是复用的，所以一开始只显示按钮二
        tvOrderStatusOne.setVisibility(View.GONE);
        tvOrderStatusTwo.setVisibility(View.VISIBLE);
        tvOrderStatusThree.setVisibility(View.GONE);
        applyRefundServiceOrder = getIntent().getStringExtra(ConstantValue.ORDER_NUMBER);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        smartRefreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        smartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        getApplyRefundMoneyDate();
        shopListMessageAdapter = new ShopListMessageAdapter(shopItemMessageList);
        publicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        publicRecyclerView.setAdapter(shopListMessageAdapter);
        publicRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        publicRecyclerView.setNestedScrollingEnabled(false);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getApplyRefundMoneyDate();
                refreshLayout.finishRefresh();
            }
        });
    }

    /**
     * 获取退款详情的数据
     */
    private void getApplyRefundMoneyDate() {
        Map<String, String> applyRefundParam = new HashMap<>();
        applyRefundParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.AFTER_THE_DETAILS_URL);
        applyRefundParam.put(ApiParam.SERVICE_NO, applyRefundServiceOrder);
        String applyRefundParamJson = JsonUtils.toJson(applyRefundParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getApplyRefundDateDetails(userToken, applyRefundParamJson);
        }
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.rl_Communication_record:
                //沟通记录
                CommunicationRecordActivity.start(mContext, applyRefundServiceOrder);
                break;
            case R.id.tv_order_status_one:
                //按钮一
                bottomButtonOneClick();
                break;
            case R.id.tv_order_status_two:
                //按钮二
                bottomButtonTwoClick();
                break;
            case R.id.tv_order_status_three:
                //按钮三
                break;
        }
    }

    @Override
    public void getApplyRefundDetailsSuccess(ApplyRefundDetailsDate applyRefundDetailsDate) {
        if (applyRefundDetailsDate != null) {
            shopOrderNumber = applyRefundDetailsDate.getShop_order();
            applyRefundStatus = applyRefundDetailsDate.getLast_status();
            shopItemMessageList = applyRefundDetailsDate.getList();
            tvTheRefundAmount.setText(mContext.getString(R.string.the_price, applyRefundDetailsDate.getMoney() + ""));
            tvWithdrawalsReason.setText(applyRefundDetailsDate.getReason());
            tvToApplyForTime.setText(DateUtils.getStrTime(applyRefundDetailsDate.getCreatetime()));
            if (shopItemMessageList != null && shopItemMessageList.size() > 0) {
                shopListMessageAdapter.setNewData(shopItemMessageList);
            }
            //沟通记录
            RecordBean recordBean = applyRefundDetailsDate.getRecord();
            if (recordBean != null) {
                ReviewMessage = recordBean.getRemark(); //审核留言
            }
            //根据状态显示控件
            showViewOfStatus(applyRefundStatus);
        }
    }

    /**
     * 根据状态显示控件
     *
     * @param applyRefundStatus 订单的状态
     */
    private void showViewOfStatus(int applyRefundStatus) {
        switch (applyRefundStatus) {
            case ApiParam.TO_APPLY_FOR_TYPE: //提交申请
                //隐藏沟通记录的布局
                tvRefundStatus.setText(mContext.getString(R.string.The_review_of_the_service));

                setAfterSalesStatus(false, true,
                        false, "",
                        true, mContext.getString(R.string.Cancel_the_application));

                break;
            case ApiParam.CUSTOMER_SERVICE_REVIEW_REJECTION: //客服审核拒绝
                //显示沟通记录的布局
                tvRefundStatus.setText(mContext.getString(R.string.Audit_refused_to));
                ivTheReviewOfTheService.setImageResource(R.drawable.ic_refund_shut_down_background);
                tvTheReviewOfTheService.setText(mContext.getString(R.string.Audit_refused_to));

                setAfterSalesStatus(true, true,
                        true, mContext.getString(R.string.To_resubmit),
                        true, mContext.getString(R.string.Cancel_the_application));
                break;
            case ApiParam.CUSTOMER_SERVICE_APPROVAL: //客服审核通过
                //显示沟通记录的布局
                tvRefundStatus.setText(mContext.getString(R.string.Fast_money));
                ivTheReviewOfTheService.setImageResource(R.drawable.ic_refund_complete_background);
                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");
                break;

            case ApiParam.FINANCIAL_AUDIT_DID_PASS: //财务审核不通过
                tvRefundStatus.setText(mContext.getString(R.string.Fast_money));
                ivTheReviewOfTheService.setImageResource(R.drawable.ic_refund_complete_background);
                ivFastMoney.setImageResource(R.drawable.ic_refund_shut_down_background);
                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");
                break;

            case ApiParam.FINANCIAL_AUDIT_THROUGH_THE_RAPID_PATMENT: //财务审核通过急速打款
                tvRefundStatus.setText(mContext.getString(R.string.Fast_money));
                ivFastMoney.setImageResource(R.drawable.ic_refund_complete_background);
                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");
                break;
            case ApiParam.THAT_COMPLETE_TYPE: //完成
                tvRefundStatus.setText(mContext.getString(R.string.Refund_to_complete));

                ivTheReviewOfTheService.setImageResource(R.drawable.ic_refund_complete_background);
                ivFastMoney.setImageResource(R.drawable.ic_refund_complete_background);
                ivRefundToComplete.setImageResource(R.drawable.ic_refund_complete_background);

                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");
                break;

        }

    }

    /**
     * 根据状态判断是否显示沟通布局和底部的按钮布局
     *
     * @param isShowCommunicationLayout 是否显示沟通记录的布局
     * @param isShowBottomButtonLayout  是否显示底部布局
     * @param isShowBottomButtonOne     是否显示按钮一
     * @param tvButtonOneTitle          按钮一显示的文本
     * @param isShowBottomButtonTwo     是否显示按钮二
     * @param tvButtonTwoTitle          按钮二显示的文本
     */
    private void setAfterSalesStatus(
            boolean isShowCommunicationLayout,
            boolean isShowBottomButtonLayout,
            boolean isShowBottomButtonOne,
            String tvButtonOneTitle,
            boolean isShowBottomButtonTwo,
            String tvButtonTwoTitle

    ) {
        if (isShowCommunicationLayout) {
            llCommunicationRecordLayout.setVisibility(View.VISIBLE);
            tvReviewTheMessage.setText(ReviewMessage);
        } else {
            llCommunicationRecordLayout.setVisibility(View.GONE);
        }
        if (isShowBottomButtonLayout) {
            llBottomStatusLayout.setVisibility(View.VISIBLE);
        } else {
            llBottomStatusLayout.setVisibility(View.GONE);
        }
        if (isShowBottomButtonOne) {
            tvOrderStatusOne.setVisibility(View.VISIBLE);
            tvOrderStatusOne.setText(tvButtonOneTitle);
        } else {
            tvOrderStatusOne.setVisibility(View.GONE);
        }
        if (isShowBottomButtonTwo) {
            tvOrderStatusTwo.setVisibility(View.VISIBLE);
            tvOrderStatusTwo.setText(tvButtonTwoTitle);
        } else {
            tvOrderStatusTwo.setVisibility(View.GONE);
        }
    }


    /**
     * 底部按钮一的监听
     */
    private void bottomButtonOneClick() {
        switch (applyRefundStatus) {
            case ApiParam.CUSTOMER_SERVICE_REVIEW_REJECTION: //客服审核拒绝，此时可以重新提交
                RefundApplicationActivity.start(mContext, shopOrderNumber, "0", 0);
                break;

        }
    }

    /**
     * 按钮二的监听
     */
    private void bottomButtonTwoClick() {
        switch (applyRefundStatus) {
            case ApiParam.TO_APPLY_FOR_TYPE: //已申请，此时可以撤销申请
            case ApiParam.CUSTOMER_SERVICE_REVIEW_REJECTION: //客服审核拒绝,可以撤销申请
            case ApiParam.CUSTOMER_SERVICE_APPROVAL: //客服审核通过,可以撤销申请
                cancelTheApplication();
                break;

        }
    }

    /**
     * 撤销申请
     */
    private void cancelTheApplication() {
        AlertDialogUtils.showCustomerDialog(mContext, mContext.getString(R.string.Are_you_sure_you_want_to_withdraw_your_application), new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {

            }

            @Override
            public void determineClick(View view) {
                Map<String, String> cancelParam = new HashMap<>();
                cancelParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CANCEL_THE_APPLICATION_URL);
                cancelParam.put(ApiParam.SERVICE_NO, applyRefundServiceOrder);
                String cancelParamJson = JsonUtils.toJson(cancelParam);
                if (!TextUtils.isEmpty(userToken)) {
                    mvpPresenter.cancelTheApplicationOrSubmit(ConstantValue.CANCEL_THE_APPLICATION_TYPE, userToken, cancelParamJson);
                }
            }
        });

    }

    @Override
    public void cancelTheApplicationOrToSubmitSuccess(int requestType, String message) {
        switch (requestType) {
            case ConstantValue.CANCEL_THE_APPLICATION_TYPE:
                operatingResultDialog = AlertDialogUtils.showOperatingResultDialog(mContext, message);
                finish();
                break;
        }

    }

    @Override
    public void accessLogisticsCompanySuccess(List<BankCardList> companyList) {

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
