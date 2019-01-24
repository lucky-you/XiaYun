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

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.home.adapter.WithdrawalSubsidiaryAdapter;
import com.goulala.xiayun.mycenter.model.PaymentDetailsBean;
import com.goulala.xiayun.mycenter.model.PaymentDetailsList;
import com.goulala.xiayun.mycenter.presenter.MyCommissionPresenter;
import com.goulala.xiayun.mycenter.view.IMyCommissionView;
import com.goulala.xiayun.mycenter.widget.MyCommissionHeaderView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的佣金 和收支明细公用
 */
public class MyCommissionActivity extends BaseMvpActivity<MyCommissionPresenter> implements IMyCommissionView {


    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private WithdrawalSubsidiaryAdapter withdrawalSubsidiaryAdapter;
    private MyCommissionHeaderView myCommissionHeaderView;
    private List<PaymentDetailsList> paymentDetailsLists = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, MyCommissionActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected MyCommissionPresenter createPresenter() {
        return new MyCommissionPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_my_commission;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableLoadMore(false);
        initTitle(mContext.getString(R.string.My_commission))
                .setRightText(mContext.getString(R.string.Directions_for_use))
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DirectionsForUseActivity.start(mContext, ConstantValue.COMMISSION_DESCRIPTION_TYPE);
                    }
                });
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        myCommissionHeaderView = new MyCommissionHeaderView(mContext);
        withdrawalSubsidiaryAdapter = new WithdrawalSubsidiaryAdapter(paymentDetailsLists, WithdrawalSubsidiaryAdapter.FRAGMENT_ADAPTER_TYPE_THREE);
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        smartRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_ddd), 1));
        smartRecyclerView.setAdapter(withdrawalSubsidiaryAdapter);
        withdrawalSubsidiaryAdapter.addHeaderView(myCommissionHeaderView);
        withdrawalSubsidiaryAdapter.setHeaderAndEmpty(true);
        EmptyViewUtils.bindEmptyView(mContext, withdrawalSubsidiaryAdapter, mContext.getString(R.string.No_statement_of_income_and_expenditure));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
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

    private void getDate() {
        getUserBalance();
        paymentDetailsList();
    }

    /**
     * 获取佣金
     */
    private void getUserBalance() {
        Map<String, String> balanceParam = new HashMap<>();
        balanceParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.ACCOUNT_BALANCE_URL);
        String balanceParamJson = JsonUtils.toJson(balanceParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getUserBalance(userToken, balanceParamJson);

    }

    /**
     * 收支明细
     */
    private void paymentDetailsList() {
        Map<String, String> paymentDetailsParam = new HashMap<>();
        paymentDetailsParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.PAYMENT_DETAILS_LIST_URL);
        paymentDetailsParam.put(ApiParam.PAGE_KEY, "1");
        paymentDetailsParam.put(ApiParam.SIZE_KEY, "3"); //只显示三条数据
        String paymentDetailsParamJson = JsonUtils.toJson(paymentDetailsParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getPaymentDetails(userToken, paymentDetailsParamJson);

    }

    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void getAccountBalanceSuccess(AccountBalance balance) {
        if (balance != null) {
            myCommissionHeaderView.setBalance(balance.getAmount());
        }
    }

    @Override
    public void paymentDetailsSuccess(PaymentDetailsBean paymentDetailsBean) {
        if (paymentDetailsBean != null) {
            this.paymentDetailsLists = paymentDetailsBean.getData();
            withdrawalSubsidiaryAdapter.setNewData(paymentDetailsLists);
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
