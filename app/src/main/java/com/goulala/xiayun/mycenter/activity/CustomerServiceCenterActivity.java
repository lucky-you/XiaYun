package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.activity.WebDetailsActivity;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.mycenter.adapter.CustomerServiceAdapter;
import com.goulala.xiayun.mycenter.model.ServiceCenterList;
import com.goulala.xiayun.mycenter.presenter.CustomerServiceCenterPresenter;
import com.goulala.xiayun.mycenter.view.ICustomerServiceCenterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客服中心
 */
public class CustomerServiceCenterActivity extends BaseMvpActivity<CustomerServiceCenterPresenter> implements ICustomerServiceCenterView {

    private RecyclerView ServiceRecyclerView;
    private CustomerServiceAdapter customerServiceAdapter;
    private List<ServiceCenterList> serviceCenterLists = new ArrayList<>();
//    private KfStartHelper helper;

    public static void start(Context context) {
        Intent intent = new Intent(context, CustomerServiceCenterActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected CustomerServiceCenterPresenter createPresenter() {
        return new CustomerServiceCenterPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_customer_service_center;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        get(R.id.flBack).setOnClickListener(this);
        get(R.id.rlPreSaleService).setOnClickListener(this);
        get(R.id.rlAfterSalesService).setOnClickListener(this);
        get(R.id.rlDescriptionOfPlatformRules).setOnClickListener(this);
        get(R.id.rlFrequentlyAskedQuestions).setOnClickListener(this);
        get(R.id.rlPlatformSuggestionBox).setOnClickListener(this);
        ServiceRecyclerView = get(R.id.ServiceRecyclerView);
        getServiceList();

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        customerServiceAdapter = new CustomerServiceAdapter(serviceCenterLists);
        ServiceRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ServiceRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_ddd), 1));
        ServiceRecyclerView.setAdapter(customerServiceAdapter);
    }

    /**
     * 获取客服群组
     */
    private void getServiceList() {
        Map<String, String> serviceParam = new HashMap<>();
        serviceParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_SERVICE_LIST_URL);
        String serviceParamJson = JsonUtils.toJson(serviceParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getServiceList(userToken, serviceParamJson);
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.flBack:
                finish();
                break;
            case R.id.rlPreSaleService:
                //售前客服
//                helper.initSdkChat(ApiService.QIMO_IM_ACCESSID, UserUtils.getUserName(), UserUtils.getUserID(), "10043315");
                break;
            case R.id.rlAfterSalesService:
                //售后客服
//                helper.initSdkChat(ApiService.QIMO_IM_ACCESSID, UserUtils.getUserName(), UserUtils.getUserID(), "10043484");
                break;

            case R.id.rlDescriptionOfPlatformRules:
                //平台规则说明
                WebDetailsActivity.start(mContext, ConstantValue.RULES_OF_THE_PLATFORM_URL);
                break;

            case R.id.rlFrequentlyAskedQuestions:
                //常用说明
                WebDetailsActivity.start(mContext, ConstantValue.COMMON_PROBLEMS_URL);
                break;
            case R.id.rlPlatformSuggestionBox:
                //平台意见箱
                PlatformSuggestionBoxActivity.start(mContext);
                break;

        }
    }

    @Override
    public void getServiceListSuccess(List<ServiceCenterList> serviceCenterLists) {
        if (!serviceCenterLists.isEmpty()) {
            customerServiceAdapter.setNewData(serviceCenterLists);
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
