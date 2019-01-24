package com.goulala.xiayun.mycenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.utils.JsonUtils;
import com.goulala.view.LoadDialog;
import com.goulala.xiayun.Basemvp.BaseMVPActivity;
import com.goulala.xiayun.R;
import com.goulala.xiayun.base.ApiParam;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.home.utils.LogUtils;
import com.goulala.xiayun.mycenter.IPresenter.SelectBankCardPresenter;
import com.goulala.xiayun.mycenter.adapter.SelectBankCardAdapter;
import com.goulala.xiayun.mycenter.contact.SelectBankCardContact;
import com.goulala.xiayun.mycenter.model.BankCardList;
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
public class SelectBankCardActivity extends BaseMVPActivity<SelectBankCardContact.presenter> implements SelectBankCardContact.view {


    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private SelectBankCardAdapter selectBankCardAdapter;

    private List<BankCardList> bankCardLists = new ArrayList<>();

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_select_bank_card);
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);

    }

    @Override
    protected void bindViews() {
        initTitle(mContext.getString(R.string.Select_bank_card));
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getBankCardList();
        selectBankCardAdapter = new SelectBankCardAdapter(bankCardLists);
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        smartRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        smartRecyclerView.setAdapter(selectBankCardAdapter);
        EmptyViewUtils.bindEmptyView(mContext, selectBankCardAdapter, mContext.getString(R.string.Bank_card_is_not_available));


    }

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getBankCardList();
                refreshLayout.finishRefresh();
            }
        });

        selectBankCardAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
        });
    }

    /**
     * 获取
     */
    private void getBankCardList() {
        Map<String, String> bankCardListParam = new HashMap<>();
        bankCardListParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.BANK_CARD_LIST_URL);
        String bankCardListParamJson = JsonUtils.toJson(bankCardListParam);
        LogUtils.loggerDebug(userToken, bankCardListParamJson);
        if (!TextUtils.isEmpty(userToken)) {
            presenter.getBankCardList(mContext, userToken, bankCardListParamJson);
        }
    }


    @Override
    public SelectBankCardContact.presenter createPresenter() {
        return new SelectBankCardPresenter(this);
    }

    @Override
    public void getBankCardListSuccess(List<BankCardList> bankCardLists) {
        if (bankCardLists != null && bankCardLists.size() > 0) {
            this.bankCardLists = bankCardLists;
            selectBankCardAdapter.setNewData(bankCardLists);
        }
    }

    @Override
    public void showLoadingDialog(String message) {
        LoadDialog.show(mContext, message);
    }

    @Override
    public void dismissLoadingDialog() {
        LoadDialog.dismiss(mContext);
    }

    @Override
    public void onRequestFailure(String error) {
        showToast(error);

    }
}
