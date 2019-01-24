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
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.mycenter.adapter.LogisticsDetailsAdapter;
import com.goulala.xiayun.mycenter.model.LogisticsDetailsBean;
import com.goulala.xiayun.mycenter.model.LogisticsDetailsList;
import com.goulala.xiayun.mycenter.presenter.LogisticsDetailsPresenter;
import com.goulala.xiayun.mycenter.view.ILogisticsDetailsView;
import com.goulala.xiayun.mycenter.widget.LogisticsDetailsHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物流详情
 */
public class LogisticsDetailsActivity extends BaseMvpActivity<LogisticsDetailsPresenter> implements ILogisticsDetailsView {

    RecyclerView smartRecyclerView;
    SmartRefreshLayout refreshLayout;
    private LogisticsDetailsAdapter logisticsDetailsAdapter;
    private LogisticsDetailsHeader logisticsDetailsHeader;
    private String expressCompanyId, expressNumber;//物流公司id,物流单号
    private List<LogisticsDetailsList> logisticsDetailsLists = new ArrayList<>();

    public static void start(Context context, String expressCompanyId, String expressNumber) {
        Intent intent = new Intent(context, LogisticsDetailsActivity.class);
        intent.putExtra(ApiParam.EXPRESS_COMPANY_ID, expressCompanyId);
        intent.putExtra(ApiParam.EXPRESS_NUMBER, expressNumber);
        context.startActivity(intent);

    }


    @Override
    protected LogisticsDetailsPresenter createPresenter() {
        return new LogisticsDetailsPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        expressCompanyId = getIntent().getStringExtra(ApiParam.EXPRESS_COMPANY_ID);
        expressNumber = getIntent().getStringExtra(ApiParam.EXPRESS_NUMBER);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_logistics_details;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Logistics_details));
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        smartRecyclerView.setAdapter(logisticsDetailsAdapter);
        logisticsDetailsAdapter.addHeaderView(logisticsDetailsHeader);
        logisticsDetailsAdapter.setHeaderAndEmpty(true);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDate();
                refreshLayout.finishRefresh();
            }
        });
    }

    /**
     * 获取数据
     */
    private void getDate() {
        Map<String, String> logisticParam = new HashMap<>();
        logisticParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.ACCESS_TO_LOGISTICS_MESSAGE_URL);
        logisticParam.put(ApiParam.EXPRESS_COMPANY_ID, expressCompanyId);
        logisticParam.put(ApiParam.EXPRESS_NUMBER, expressNumber);
        String logisticParamJson = JsonUtils.toJson(logisticParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getLogisticsDetails(userToken, logisticParamJson);
        showDialog("");
    }

    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void getLogisticsDetailsSuccess(LogisticsDetailsBean logisticsDetailsBean) {
        dismissDialog();
        if (logisticsDetailsBean != null) {
            logisticsDetailsHeader.setHeaderViewDate(
                    logisticsDetailsBean.getLogoimage(),
                    logisticsDetailsBean.getName(),
                    logisticsDetailsBean.getContactmobile(),
                    logisticsDetailsBean.getNu());
            logisticsDetailsLists = logisticsDetailsBean.getData();
            if (logisticsDetailsLists != null && logisticsDetailsLists.size() > 0) {
                //获取到的物流信息
                logisticsDetailsAdapter.setNewData(logisticsDetailsLists);
            } else {
                //没有物流信息
                EmptyViewUtils.bindEmptyView(mContext, logisticsDetailsAdapter, mContext.getString(R.string.No_logistics_information));
            }
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
