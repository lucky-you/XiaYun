package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.mycenter.IPresenter.TheMessageCenterPresenter;
import com.goulala.xiayun.mycenter.adapter.TheMessageCenterAdapter;
import com.goulala.xiayun.mycenter.contact.TheMessageCenterContact;
import com.goulala.xiayun.mycenter.model.MessageCenterList;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 消息中心
 */
public class TheMessageCenterActivity extends BaseMVPActivity<TheMessageCenterContact.presenter> implements TheMessageCenterContact.view {


    private RecyclerView publicRecyclerView;
    private TheMessageCenterAdapter theMessageCenterAdapter;
    private List<MessageCenterList> messageCenterLists = new ArrayList<>();
    private int[] imageArrayList = {
            R.drawable.ic_news_block_system,
            R.drawable.ic_news_block_logistics,
            R.drawable.ic_news_block_as
//            R.drawable.ic_news_block_cs,
    };

    public static void start(Context context) {
        Intent intent = new Intent(context, TheMessageCenterActivity.class);
        context.startActivity(intent);

    }


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_the_message_center);
        publicRecyclerView = get(R.id.public_RecyclerView);
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
    }

    @Override
    protected void bindViews() {
        initTitle(mContext.getString(R.string.The_message_center));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessageList();
    }

    /**
     * 消息中心
     */
    private void getMessageList() {
        Map<String, String> messageListParam = new HashMap<>();
        messageListParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.THE_MESSAGE_CENTER_URL);
        String messageListParamJson = JsonUtils.toJson(messageListParam);
        Logger.d("xy", messageListParamJson + "token=" + userToken);
        if (!TextUtils.isEmpty(userToken)) {
            presenter.getMessageList(mContext, userToken, messageListParamJson);
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        theMessageCenterAdapter = new TheMessageCenterAdapter(messageCenterLists, imageArrayList);
        publicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        publicRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        publicRecyclerView.setAdapter(theMessageCenterAdapter);

    }

    @Override
    protected void setListener() {
        theMessageCenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int categoryId = theMessageCenterAdapter.getItem(position).getId();
                String messageTitle = theMessageCenterAdapter.getItem(position).getName();
                switch (position) {
                    case 0: //系统消息
                        MessageDetailsActivity.start(mContext, ConstantValue.THE_CLASS_TYPE_OF_SYSTEM_NEWS, messageTitle, categoryId);
                        break;
                    case 1: //物流消息
                        MessageDetailsActivity.start(mContext, ConstantValue.THE_CLASS_TYPE_OF_LOGISTICS_NEWS, messageTitle, categoryId);
                        break;
                    case 2:  //售后消息
                        MessageDetailsActivity.start(mContext, ConstantValue.THE_CLASS_TYPE_OF_AFTER_SELL_NEWS, messageTitle, categoryId);
                        break;
                    case 3://客服消息
                        break;
                }
            }
        });
    }

    @Override
    public TheMessageCenterContact.presenter createPresenter() {
        return new TheMessageCenterPresenter(this);
    }

    @Override
    public void getMessageListSuccess(List<MessageCenterList> messageLists) {
        if (messageLists.size() > 0) {
            this.messageCenterLists = messageLists;
            theMessageCenterAdapter.setNewData(messageCenterLists);
        } else {
            EmptyViewUtils.bindEmptyView(mContext, theMessageCenterAdapter);
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
