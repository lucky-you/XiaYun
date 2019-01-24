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
import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.common.widget.SmartRefreshLoadPageHelper;
import com.goulala.xiayun.home.adapter.WithdrawalSubsidiaryAdapter;
import com.goulala.xiayun.mycenter.model.PaymentDetailsBean;
import com.goulala.xiayun.mycenter.model.PaymentDetailsList;
import com.goulala.xiayun.mycenter.presenter.MyCommissionPresenter;
import com.goulala.xiayun.mycenter.view.IMyCommissionView;
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
 * 收支明细
 */
public class PaymentDetailsActivity extends BaseMvpActivity<MyCommissionPresenter> implements IMyCommissionView,
        SmartRefreshLoadPageHelper.DataProvider {

    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private WithdrawalSubsidiaryAdapter withdrawalSubsidiaryAdapter;
    private List<PaymentDetailsList> paymentDetailsLists = new ArrayList<>();
    private SmartRefreshLoadPageHelper<PaymentDetailsList> smartRefreshLoadPageHelper = new SmartRefreshLoadPageHelper<>(this);

    public static void start(Context context) {
        Intent intent = new Intent(context, PaymentDetailsActivity.class);
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
        return R.layout.activity_payment_details;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Payment_details));
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableLoadMore(false);

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        withdrawalSubsidiaryAdapter = new WithdrawalSubsidiaryAdapter(paymentDetailsLists, WithdrawalSubsidiaryAdapter.FRAGMENT_ADAPTER_TYPE_FOUR);
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        smartRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_ddd), 1));
        smartRecyclerView.setAdapter(withdrawalSubsidiaryAdapter);
        smartRefreshLoadPageHelper.attachView(refreshLayout, smartRecyclerView, withdrawalSubsidiaryAdapter);
        EmptyViewUtils.bindEmptyView(mContext, withdrawalSubsidiaryAdapter, mContext.getString(R.string.No_statement_of_income_and_expenditure));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLoadPageHelper.refreshPage();
                refreshLayout.finishRefresh();
            }
        });

    }

    /**
     * 收支明细
     */
    private void paymentDetailsList(int currentPage) {
        Map<String, String> paymentDetailsParam = new HashMap<>();
        paymentDetailsParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.PAYMENT_DETAILS_LIST_URL);
        paymentDetailsParam.put(ApiParam.PAGE_KEY, String.valueOf(currentPage));
        paymentDetailsParam.put(ApiParam.SIZE_KEY, ApiParam.SIZE_NUMBER_VALUE);
        String paymentDetailsParamJson = JsonUtils.toJson(paymentDetailsParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getPaymentDetails(userToken, paymentDetailsParamJson);
        }
    }

    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void loadMorePageDate(int page) {
        paymentDetailsList(page);
    }

    @Override
    public void getAccountBalanceSuccess(AccountBalance balance) {

    }

    @Override
    public void paymentDetailsSuccess(PaymentDetailsBean paymentDetailsBean) {
        if (paymentDetailsBean != null) {
            this.paymentDetailsLists = paymentDetailsBean.getData();
            smartRefreshLoadPageHelper.setData(paymentDetailsLists);
//            if (paymentDetailsLists.size() < 1) {
//                showToast(mContext.getString(R.string.All_content_is_displayed));
//            }
        }
    }

    @Override
    public void onNewWorkException(String message) {

    }

    @Override
    public void onRequestFailure(int resultCode, String message) {

    }
}
