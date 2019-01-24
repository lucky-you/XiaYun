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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.common.widget.SmartRefreshLoadPageHelper;
import com.goulala.xiayun.mycenter.adapter.MessageDetailsAdapter;
import com.goulala.xiayun.mycenter.adapter.SystemMessageAdapter;
import com.goulala.xiayun.mycenter.model.AttachBean;
import com.goulala.xiayun.mycenter.model.LogisticsMessageBean;
import com.goulala.xiayun.mycenter.model.LogisticsMessageList;
import com.goulala.xiayun.mycenter.presenter.MessageDetailsPresenter;
import com.goulala.xiayun.mycenter.view.IMessageDetailsView;
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
 * 消息详情--公用的界面
 */
public class MessageDetailsActivity extends BaseMvpActivity<MessageDetailsPresenter> implements IMessageDetailsView,
        BaseQuickAdapter.OnItemClickListener,
        SmartRefreshLoadPageHelper.DataProvider {

    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private ImageView ivHint;
    private TextView tvHint;
    private RelativeLayout rlEmptyLayout;
    private int mClassType;
    //物流和售后的adapter
    private MessageDetailsAdapter messageDetailsAdapter;
    //系统消息的adapter
    private SystemMessageAdapter systemMessageAdapter;
    private int categoryId;  //信息的分类id
    private List<LogisticsMessageList> logisticsMessageList = new ArrayList<>();
    private String messageTitle;
    private SmartRefreshLoadPageHelper<LogisticsMessageList> smartRefreshLoadPageHelper = new SmartRefreshLoadPageHelper<>(this);


    public static void start(Context context, int classType, String title, int categoryId) {
        Intent intent = new Intent(context, MessageDetailsActivity.class);
        intent.putExtra(ConstantValue.TYPE, classType);
        intent.putExtra(ConstantValue.TITLE, title);
        intent.putExtra(ConstantValue.CATEGORY, categoryId);
        context.startActivity(intent);

    }


    @Override
    protected MessageDetailsPresenter createPresenter() {
        return new MessageDetailsPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mClassType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
        categoryId = getIntent().getIntExtra(ConstantValue.CATEGORY, -1);
        messageTitle = getIntent().getStringExtra(ConstantValue.TITLE);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_message_details;
    }

    @Override
    public void bindViews(View contentView) {
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        ivHint = get(R.id.ivHint);
        tvHint = get(R.id.tvHint);
        rlEmptyLayout = get(R.id.rl_empty_layout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableLoadMore(false);
        initTitle(messageTitle);
        switch (mClassType) {
            case ConstantValue.THE_CLASS_TYPE_OF_LOGISTICS_NEWS: //物流消息
                messageDetailsAdapter = new MessageDetailsAdapter(logisticsMessageList, ConstantValue.THE_CLASS_TYPE_OF_LOGISTICS_NEWS);
                messageDetailsAdapter.setOnItemClickListener(this);
                smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                smartRecyclerView.setAdapter(messageDetailsAdapter);
                smartRefreshLoadPageHelper.attachView(refreshLayout, smartRecyclerView, messageDetailsAdapter);
                EmptyViewUtils.bindEmptyView(mContext, messageDetailsAdapter, mContext.getString(R.string.No_message));
                break;
            case ConstantValue.THE_CLASS_TYPE_OF_AFTER_SELL_NEWS://售后消息
                messageDetailsAdapter = new MessageDetailsAdapter(logisticsMessageList, ConstantValue.THE_CLASS_TYPE_OF_AFTER_SELL_NEWS);
                messageDetailsAdapter.setOnItemClickListener(this);
                smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                smartRecyclerView.setAdapter(messageDetailsAdapter);
                smartRefreshLoadPageHelper.attachView(refreshLayout, smartRecyclerView, messageDetailsAdapter);
                EmptyViewUtils.bindEmptyView(mContext, messageDetailsAdapter, mContext.getString(R.string.No_message));
                break;
            case ConstantValue.THE_CLASS_TYPE_OF_SYSTEM_NEWS://系统消息
                systemMessageAdapter = new SystemMessageAdapter(logisticsMessageList);
                systemMessageAdapter.setOnItemClickListener(this);
                smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                smartRecyclerView.setAdapter(systemMessageAdapter);
                smartRefreshLoadPageHelper.attachView(refreshLayout, smartRecyclerView, systemMessageAdapter);
                EmptyViewUtils.bindEmptyView(mContext, systemMessageAdapter, mContext.getString(R.string.No_message));
                break;
        }
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLoadPageHelper.refreshPage();
                refreshLayout.finishRefresh();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        smartRefreshLoadPageHelper.refreshPage();
    }

    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void loadMorePageDate(int page) {
        getDate(categoryId, page);
    }

    /**
     * 获取数据
     */
    private void getDate(int category, int currentPage) {
        Map<String, String> categoryMessageParam = new HashMap<>();
        categoryMessageParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_CATEGORY_MESSAGE_URL);
        categoryMessageParam.put(ApiParam.CATEGORY, String.valueOf(category));
        categoryMessageParam.put(ApiParam.SIZE_KEY, ApiParam.SIZE_NUMBER_VALUE);
        categoryMessageParam.put(ApiParam.PAGE_KEY, String.valueOf(currentPage));
        String categoryMessageParamJson = JsonUtils.toJson(categoryMessageParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getMessageDetails(userToken, categoryMessageParamJson);

    }

    /**
     * 阅读消息
     */
    private void readMessage(int message_id) {
        Map<String, String> readMessageParam = new HashMap<>();
        readMessageParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.READ_STATION_LETTERS_URL);
        readMessageParam.put(ApiParam.MESSAGE_ID, String.valueOf(message_id));
        String readMessageParamJson = JsonUtils.toJson(readMessageParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.readMessage(userToken, readMessageParamJson);

    }

    @Override
    public void getMessageDetails(LogisticsMessageBean logisticsMessageBean) {
        if (logisticsMessageBean != null) {
            logisticsMessageList = logisticsMessageBean.getData();
            smartRefreshLoadPageHelper.setData(logisticsMessageList);
        }
    }

    @Override
    public void readMessageSuccess(String isReadCode) {
        smartRefreshLoadPageHelper.refreshPage();
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AttachBean attachBean;
        switch (mClassType) {
            case ConstantValue.THE_CLASS_TYPE_OF_SYSTEM_NEWS://系统消息
                int messageId = systemMessageAdapter.getItem(position).getId();
                PaymentDetailsActivity.start(mContext);
                readMessage(messageId);
                break;

            case ConstantValue.THE_CLASS_TYPE_OF_LOGISTICS_NEWS: //物流消息
                attachBean = messageDetailsAdapter.getItem(position).getAttach();
                String express_company_id = attachBean.getExpress_company_id();
                String express_number = attachBean.getExpress_number();
                int messageIdTwo = messageDetailsAdapter.getItem(position).getId();
                LogisticsDetailsActivity.start(mContext, express_company_id, express_number);
                readMessage(messageIdTwo);
                break;

            case ConstantValue.THE_CLASS_TYPE_OF_AFTER_SELL_NEWS://售后消息
                attachBean = messageDetailsAdapter.getItem(position).getAttach();
                String shop_order = attachBean.getShop_order();
                String pay_no = attachBean.getPay_no();
                int messageIdThree = messageDetailsAdapter.getItem(position).getId();
                TheOrderDetailsActivity.start(mContext, pay_no, shop_order);
                readMessage(messageIdThree);
                break;

        }
    }
}
