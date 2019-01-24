package com.goulala.xiayun.mycenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.mycenter.adapter.SelectBankCardAdapter;
import com.goulala.xiayun.mycenter.model.BankCardList;
import com.goulala.xiayun.mycenter.presenter.SelectBankCardPresenter;
import com.goulala.xiayun.mycenter.view.ISelectBankCardView;
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
 * 选择银行卡
 */
public class SelectBankCardActivity extends BaseMvpActivity<SelectBankCardPresenter> implements ISelectBankCardView,
        BaseQuickAdapter.OnItemClickListener {


    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private SelectBankCardAdapter selectBankCardAdapter;

    private List<BankCardList> bankCardLists = new ArrayList<>();

    @Override
    protected SelectBankCardPresenter createPresenter() {
        return null;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_select_bank_card;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Select_bank_card));
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
        getBankCardList();
        selectBankCardAdapter = new SelectBankCardAdapter(bankCardLists);
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        smartRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        smartRecyclerView.setAdapter(selectBankCardAdapter);
        EmptyViewUtils.bindEmptyView(mContext, selectBankCardAdapter, mContext.getString(R.string.Bank_card_is_not_available));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getBankCardList();
                refreshLayout.finishRefresh();
            }
        });
    }

    /**
     * 获取银行
     */
    private void getBankCardList() {
        Map<String, String> bankCardListParam = new HashMap<>();
        bankCardListParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.BANK_CARD_LIST_URL);
        String bankCardListParamJson = JsonUtils.toJson(bankCardListParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getBankCardList(userToken, bankCardListParamJson);
        showDialog("");
    }

    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void getBankCardListSuccess(List<BankCardList> bankCardLists) {
        if (bankCardLists != null && bankCardLists.size() > 0) {
            this.bankCardLists = bankCardLists;
            selectBankCardAdapter.setNewData(bankCardLists);
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String bankCardName = selectBankCardAdapter.getItem(position).getName();
        int bankCardId = selectBankCardAdapter.getItem(position).getId();
        Intent intent = new Intent();
        intent.putExtra(ApiParam.BANK_NAME, bankCardName);
        intent.putExtra(ApiParam.BANK_ID, bankCardId);
        setResult(RESULT_OK, intent);
        finish();
    }


}
