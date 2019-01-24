package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.mycenter.adapter.ShopListMessageAdapter;
import com.goulala.xiayun.mycenter.model.QinIuBean;
import com.goulala.xiayun.mycenter.model.RefundMoneyDate;
import com.goulala.xiayun.mycenter.model.RefundResultDate;
import com.goulala.xiayun.mycenter.model.ShopItemMessage;
import com.goulala.xiayun.mycenter.presenter.ReturnGoodApplicationPresenter;
import com.goulala.xiayun.mycenter.view.IReturnGoodApplicationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 申请退款（单纯的退款） 在待发货之前使用
 */
public class RefundApplicationActivity extends BaseMvpActivity<ReturnGoodApplicationPresenter> implements IReturnGoodApplicationView {

    private RecyclerView publicRecyclerView;
    private RadioButton rbTakeTheWrongGoods;
    private RadioButton rbDonWantToBuyThe;
    private RadioButton rbGoodsOutOfStock;
    private RadioButton rbOtherReasons;
    private EditText editProblemDescription;
    private TextView tvTheRefundAmount;
    private TextView tvSubmit;
    private String shopOrderNumber, itemOrderNumber;
    private int itemNumber, orderStatus;
    private List<ShopItemMessage> goodItemMessageList = new ArrayList<>();
    private ShopListMessageAdapter shopListMessageAdapter;
    private String payOrderNumber, refundMoney, refundNumber, refundReason;

    public static void start(Context context, String shopOrder, String itemOrder, int itemNum) {
        Intent intent = new Intent(context, RefundApplicationActivity.class);
        intent.putExtra(ApiParam.SHOP_ORDER, shopOrder);
        intent.putExtra(ApiParam.ITEM_ORDER, itemOrder);
        intent.putExtra(ApiParam.ITEM_NUM, itemNum);
        context.startActivity(intent);
    }

    @Override
    protected ReturnGoodApplicationPresenter createPresenter() {
        return new ReturnGoodApplicationPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        shopOrderNumber = getIntent().getStringExtra(ApiParam.SHOP_ORDER);
        itemOrderNumber = getIntent().getStringExtra(ApiParam.ITEM_ORDER);
        itemNumber = getIntent().getIntExtra(ApiParam.ITEM_NUM, -1);
    }

    @Override
    public int loadViewLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_refund_application;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Refund_application));
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        publicRecyclerView = get(R.id.public_RecyclerView);
        rbTakeTheWrongGoods = get(R.id.rb_Take_the_wrong_goods);
        rbDonWantToBuyThe = get(R.id.rb_Don_want_to_buy_the);
        rbGoodsOutOfStock = get(R.id.rb_Goods_out_of_stock);
        rbOtherReasons = get(R.id.rb_Other_reasons);
        rbTakeTheWrongGoods.setOnClickListener(this);
        rbDonWantToBuyThe.setOnClickListener(this);
        rbGoodsOutOfStock.setOnClickListener(this);
        rbOtherReasons.setOnClickListener(this);
        editProblemDescription = get(R.id.edit_Problem_description);
        tvTheRefundAmount = get(R.id.tv_The_refund_amount);
        tvSubmit = get(R.id.tv_submit);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        shopListMessageAdapter = new ShopListMessageAdapter(goodItemMessageList);
        publicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        publicRecyclerView.setAdapter(shopListMessageAdapter);
        publicRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        publicRecyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refundThatGoodMoney();
    }

    /**
     * 获取退款的信息
     */
    private void refundThatGoodMoney() {
        Map<String, String> refundGoodMonetParam = new HashMap<>();
        refundGoodMonetParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.REFUND_AFTER_ORDER_MESSAGE_URL);
        refundGoodMonetParam.put(ApiParam.SHOP_ORDER, shopOrderNumber);
        refundGoodMonetParam.put(ApiParam.ITEM_ORDER, itemOrderNumber);
        refundGoodMonetParam.put(ApiParam.ITEM_NUM, String.valueOf(itemNumber));
        String refundGoodMonetParamJson = JsonUtils.toJson(refundGoodMonetParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getRefundGoodMessage(userToken, refundGoodMonetParamJson);
        showDialog("");

    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.rb_Take_the_wrong_goods:
                getRefundReason(rbTakeTheWrongGoods);
                break;
            case R.id.rb_Don_want_to_buy_the:
                getRefundReason(rbDonWantToBuyThe);
                break;
            case R.id.rb_Goods_out_of_stock:
                getRefundReason(rbGoodsOutOfStock);
                break;
            case R.id.rb_Other_reasons:
                getRefundReason(rbOtherReasons);
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(refundReason)) {
                    showToast(mContext.getString(R.string.Please_select_refund_money_reason));
                    return;
                }
                String remarkText = editProblemDescription.getText().toString().trim(); //描述（选填）
                submitApplyRefundDate(remarkText);
                break;
        }
    }

    /**
     * 退款原因
     */
    private void getRefundReason(RadioButton radioButton) {
        if (radioButton.isChecked()) {
            tvSubmit.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
            refundReason = radioButton.getText().toString();
        } else {
            tvSubmit.setBackgroundColor(mContext.getResources().getColor(R.color.color_ddd));
        }
    }

    /**
     * 提交申请退款的数据
     */
    private void submitApplyRefundDate(String remarkText) {
        Map<String, String> submitParam = new HashMap<>();
        submitParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.APPLY_FOR_AFTER_SALES_URL);
        submitParam.put(ApiParam.PAY_NO, payOrderNumber);
        submitParam.put(ApiParam.SHOP_ORDER, shopOrderNumber);
        submitParam.put(ApiParam.ITEM_ORDER, "0"); //在待发货状态下，全部传0
        submitParam.put(ApiParam.MONEY, refundMoney);
        submitParam.put(ApiParam.NUM, refundNumber);
        submitParam.put(ApiParam.TYPE, "1");//退款
        submitParam.put(ApiParam.REASON, refundReason);
        submitParam.put(ApiParam.REMARK, remarkText);
        submitParam.put(ApiParam.IMAGES, "");
        submitParam.put(ApiParam.STATUS, String.valueOf(orderStatus));
        String submitParamJson = JsonUtils.toJson(submitParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.submitApplyRefund(userToken, submitParamJson);
        }

    }

    @Override
    public void getRefundGoodMessageSuccess(RefundMoneyDate refundMoneyDate) {
        dismissDialog();
        if (refundMoneyDate != null) {
            goodItemMessageList = refundMoneyDate.getOrder();
            if (goodItemMessageList != null && goodItemMessageList.size() > 0) {
                shopListMessageAdapter.setNewData(goodItemMessageList);
                payOrderNumber = goodItemMessageList.get(0).getPay_no();
                shopOrderNumber = goodItemMessageList.get(0).getShop_order();
                orderStatus = goodItemMessageList.get(0).getStatus(); //订单状态
            }
            refundMoney = refundMoneyDate.getMoney() + "";
            refundNumber = refundMoneyDate.getNum() + "";
            tvTheRefundAmount.setText(mContext.getString(R.string.the_price, refundMoney));
        }
    }

    @Override
    public void submitApplyRefundSuccess(RefundResultDate refundResultDate) {
        if (refundResultDate != null) {
            String applyRefundOrder = refundResultDate.getService_no();
            TheGoodDetailsRefundActivity.start(mContext, applyRefundOrder);
        }
        finish();
    }

    @Override
    public void getQinIuSetMessageSuccess(QinIuBean message) {

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
