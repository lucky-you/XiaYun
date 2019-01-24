package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.qiniu.QinIuUpLoadListener;
import com.goulala.xiayun.common.qiniu.QinIuUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.ButtonClickUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.PictureSelectorUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.mycenter.adapter.PostGridImageAdapter;
import com.goulala.xiayun.mycenter.adapter.ShopListMessageAdapter;
import com.goulala.xiayun.mycenter.model.QinIuBean;
import com.goulala.xiayun.mycenter.model.RefundMoneyDate;
import com.goulala.xiayun.mycenter.model.RefundResultDate;
import com.goulala.xiayun.mycenter.model.ShopItemMessage;
import com.goulala.xiayun.mycenter.presenter.ReturnGoodApplicationPresenter;
import com.goulala.xiayun.mycenter.view.IReturnGoodApplicationView;
import com.goulala.xiayun.mycenter.widget.WrapHeightGridView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 申请售后-退货
 * 申请售后-退款
 * 在发货之后使用
 * 公用的activity
 */
public class ReturnTheGoodActivity extends BaseMvpActivity<ReturnGoodApplicationPresenter> implements IReturnGoodApplicationView,
        AdapterView.OnItemClickListener {


    private RecyclerView publicRecyclerView;
    private TextView tvChooseTheReturnReasons;
    private RadioButton rbTakeTheWrongGoods;
    private RadioButton rbDonWantToBuyThe;
    private RadioButton rbGoodsOutOfStock;
    private RadioButton rbOtherReasons;
    private EditText editProblemDescription;
    private TextView tvNumberOfApplicationsNumber;
    private LinearLayout llRefundNumberLayout;
    private TextView tvTheRefundAmount;
    private WrapHeightGridView bgaSortablePhotoLayout;
    private TextView tvSubmit;
    //图片集合
    private ArrayList<String> mSelectImagePath = new ArrayList<>();
    private PostGridImageAdapter imageAdapter;
    private int applyRefundType; //售后类型，是退款还是退货
    private String[] refundReasonList;
    private int goodNumber = 1;  //商品的数量
    private String shopOrderNumber, itemNumber;
    private List<ShopItemMessage> goodItemMessageList = new ArrayList<>();
    private ShopListMessageAdapter shopListMessageAdapter;
    private String payOrderNumber, itemOrder, refundMoney, refundReason;
    private int refundNumber, orderStatus;//	数量
    private String imagePaths = "";//图片路径
    private String qinIuToken; //七牛云的token
    private List<String> qinIuImages = new ArrayList<>(); //保存从七牛云返回的图片路径的集合
    //选择图片最大数目
    public static final int MAX_NUM = 3;
    private String remarkText;

    public static void start(Context context, int type, String shop_order, String item_order, String item_num) {
        Intent intent = new Intent(context, ReturnTheGoodActivity.class);
        intent.putExtra(ConstantValue.TYPE, type);
        intent.putExtra(ApiParam.SHOP_ORDER, shop_order);
        intent.putExtra(ApiParam.ITEM_ORDER, item_order);
        intent.putExtra(ApiParam.ITEM_NUM, item_num);
        context.startActivity(intent);
    }

    @Override
    protected ReturnGoodApplicationPresenter createPresenter() {
        return new ReturnGoodApplicationPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        applyRefundType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
        shopOrderNumber = getIntent().getStringExtra(ApiParam.SHOP_ORDER);
        payOrderNumber = getIntent().getStringExtra(ApiParam.ITEM_ORDER);
        itemNumber = getIntent().getStringExtra(ApiParam.ITEM_NUM);
    }

    @Override
    public int loadViewLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_return_the_good;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        publicRecyclerView = get(R.id.public_RecyclerView);
        tvChooseTheReturnReasons = get(R.id.tv_choose_the_return_reasons);
        rbTakeTheWrongGoods = get(R.id.rb_Take_the_wrong_goods);
        rbDonWantToBuyThe = get(R.id.rb_Don_want_to_buy_the);
        rbGoodsOutOfStock = get(R.id.rb_Goods_out_of_stock);
        rbOtherReasons = get(R.id.rb_Other_reasons);
        rbTakeTheWrongGoods.setOnClickListener(this);
        rbDonWantToBuyThe.setOnClickListener(this);
        rbGoodsOutOfStock.setOnClickListener(this);
        rbOtherReasons.setOnClickListener(this);
        editProblemDescription = get(R.id.edit_Problem_description);
        tvNumberOfApplicationsNumber = get(R.id.tv_Number_of_applications_number);
        llRefundNumberLayout = get(R.id.ll_refund_number_layout);
        tvTheRefundAmount = get(R.id.tv_The_refund_amount);
        bgaSortablePhotoLayout = get(R.id.bgaSortablePhotoLayout);
        tvSubmit = get(R.id.tv_submit);
        tvSubmit.setOnClickListener(this);
        llRefundNumberLayout.setVisibility(View.VISIBLE); //显示申请数量
        switch (applyRefundType) {
            case ConstantValue.APPLY_FOR_REFUND_GOOD_TYPE:
                initTitle(mContext.getString(R.string.To_apply_for_refund_good));
                tvChooseTheReturnReasons.setText(mContext.getString(R.string.Reasons_for_choosing_refund_good));
                refundReasonList = mContext.getResources().getStringArray(R.array.apply_after_refund_good);
                setReasonToView(refundReasonList);
                break;
            case ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE:
                initTitle(mContext.getString(R.string.To_apply_for_refund_money));
                tvChooseTheReturnReasons.setText(mContext.getString(R.string.Reasons_for_choosing_refund));
                refundReasonList = mContext.getResources().getStringArray(R.array.apply_after_refund_money);
                setReasonToView(refundReasonList);
                break;
        }
        imageAdapter = new PostGridImageAdapter(mContext, mSelectImagePath);
        imageAdapter.setMaxNum(MAX_NUM);
        bgaSortablePhotoLayout.setAdapter(imageAdapter);
        bgaSortablePhotoLayout.setOnItemClickListener(this);
    }

    /**
     * 设置退款或者退货的原因
     */
    private void setReasonToView(String[] refundReason) {
        if (refundReason.length > 0) {
            rbTakeTheWrongGoods.setText(refundReason[0]);
            rbDonWantToBuyThe.setText(refundReason[1]);
            rbGoodsOutOfStock.setText(refundReason[2]);
            rbOtherReasons.setText(refundReason[3]);
        }
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        shopListMessageAdapter = new ShopListMessageAdapter(goodItemMessageList);
        publicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        publicRecyclerView.setAdapter(shopListMessageAdapter);
        publicRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        publicRecyclerView.setNestedScrollingEnabled(false);
        getDate();
    }

    private void getDate() {
        //获取订单信息
        getOrderMessage();
        //获取七牛云的配置信息
        getQinIuSet();

    }

    /**
     * 获取七牛云的配置
     */
    private void getQinIuSet() {
        Map<String, String> qinIuMapParam = new HashMap<>();
        qinIuMapParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_QIN_IU_SET_UP_URL);
        String qinIuMapParamJson = JsonUtils.toJson(qinIuMapParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getQinIuSetMessage(userToken, qinIuMapParamJson);
        }
    }

    /**
     * 获取申请售后的商品信息
     */
    private void getOrderMessage() {
        Map<String, String> refundGoodMonetParam = new HashMap<>();
        refundGoodMonetParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.REFUND_AFTER_ORDER_MESSAGE_URL);
        refundGoodMonetParam.put(ApiParam.SHOP_ORDER, shopOrderNumber);
        refundGoodMonetParam.put(ApiParam.ITEM_ORDER, payOrderNumber);
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
                radioButtonIsSelect(rbTakeTheWrongGoods);
                break;
            case R.id.rb_Don_want_to_buy_the:
                radioButtonIsSelect(rbDonWantToBuyThe);
                break;
            case R.id.rb_Goods_out_of_stock:
                radioButtonIsSelect(rbGoodsOutOfStock);
                break;
            case R.id.rb_Other_reasons:
                radioButtonIsSelect(rbOtherReasons);
                break;
            case R.id.tv_submit:
                //提交
                if (!ButtonClickUtils.isFastClick()) {
                    remarkText = editProblemDescription.getText().toString().trim(); //描述（选填）
                    switch (applyRefundType) {
                        case ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE: //退款
                            if (mSelectImagePath.size() > 0) {//有照片
                                for (int i = 0; i < mSelectImagePath.size(); i++) {
                                    qinIuUpLoad(mSelectImagePath.get(i), i, ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE);
                                }
                            } else { //没有照片
                                submitApplyRefundDate("1", remarkText); //退款
                            }
                            break;

                        case ConstantValue.APPLY_FOR_REFUND_GOOD_TYPE: //退货
                            if (mSelectImagePath.size() < 1) {
                                showToast(mContext.getString(R.string.Choose_at_least_one_picture));
                                return;
                            }
                            for (int i = 0; i < mSelectImagePath.size(); i++) {
                                qinIuUpLoad(mSelectImagePath.get(i), i, ConstantValue.APPLY_FOR_REFUND_GOOD_TYPE);
                            }
                            break;
                    }
                }
                break;
        }
    }

    //上传图片到七牛云
    private void qinIuUpLoad(String imagePath, int index, final int requestType) {
        String qinKey = "xiayun/uploads/" + QinIuUtils.getStringDate() + "/" + System.currentTimeMillis();
        QinIuUtils.qinIuUpLoad(imagePath, qinKey + "/" + index, qinIuToken, new QinIuUpLoadListener() {
            @Override
            public void upLoadSuccess(String path) {
                String imageUrl = "/" + path;
                Log.e("xy", "imageUrl=" + imageUrl);
                qinIuImages.add(imageUrl);
                //先要把图片上传到七牛云,然后在提交
                if (qinIuImages.size() == mSelectImagePath.size()) { //集合数量相等，说明，选择的图片全部上传了
                    if (requestType == ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE) {
                        submitApplyRefundDate("1", remarkText); //退款
                    } else {
                        submitApplyRefundDate("2", remarkText);//退货
                    }
                }
            }

            @Override
            public void upLoadFail(String errorMessage) {
                showToast(mContext.getString(R.string.Upload_failed, errorMessage));
            }
        });

    }

    /**
     * 提交售后申请
     *
     * @param remarkText
     */
    private void submitApplyRefundDate(String refundType, String remarkText) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < qinIuImages.size(); i++) {
            stringBuffer.append(qinIuImages.get(i) + ",");
        }
        if (stringBuffer != null && stringBuffer.length() > 0) {
            imagePaths = stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        Map<String, String> submitParam = new HashMap<>();
        submitParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.APPLY_FOR_AFTER_SALES_URL);
        submitParam.put(ApiParam.PAY_NO, payOrderNumber);
        submitParam.put(ApiParam.SHOP_ORDER, shopOrderNumber);
        submitParam.put(ApiParam.ITEM_ORDER, itemOrder);
        submitParam.put(ApiParam.MONEY, refundMoney);
        submitParam.put(ApiParam.NUM, String.valueOf(refundNumber));
        submitParam.put(ApiParam.TYPE, refundType);//1退款 2退货
        submitParam.put(ApiParam.REASON, refundReason);
        submitParam.put(ApiParam.REMARK, remarkText);
        submitParam.put(ApiParam.IMAGES, imagePaths);
        submitParam.put(ApiParam.STATUS, String.valueOf(orderStatus));
        String submitParamJson = JsonUtils.toJson(submitParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.submitApplyRefund(userToken, submitParamJson);
        showDialog("");

    }

    /**
     * radioButton有没有被选中
     */
    private void radioButtonIsSelect(RadioButton radioButton) {
        if (radioButton.isChecked()) {
            tvSubmit.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
            refundReason = radioButton.getText().toString(); //退款、退货的原因
        } else {
            tvSubmit.setBackgroundColor(mContext.getResources().getColor(R.color.color_ddd));
        }
    }

    @Override
    public void getRefundGoodMessageSuccess(RefundMoneyDate refundMoneyDate) {
        if (refundMoneyDate != null) {
            goodItemMessageList = refundMoneyDate.getOrder();
            tvNumberOfApplicationsNumber.setText(String.valueOf(refundMoneyDate.getMax_num()));
            if (goodItemMessageList != null && goodItemMessageList.size() > 0) {
                shopListMessageAdapter.setNewData(goodItemMessageList);
                shopOrderNumber = goodItemMessageList.get(0).getShop_order(); //店铺的编号
                payOrderNumber = goodItemMessageList.get(0).getPay_no(); //支付订单编号
                orderStatus = goodItemMessageList.get(0).getStatus(); //订单状态
                if (goodItemMessageList.size() > 1) { //商品订单号 多个订单时传0
                    itemOrder = "0";
                } else {
                    itemOrder = goodItemMessageList.get(0).getItem_order(); //单个商品
                }
            }
            refundMoney = refundMoneyDate.getMoney() + ""; //金额
            refundNumber = refundMoneyDate.getMax_num(); //数量
            tvTheRefundAmount.setText(mContext.getString(R.string.the_price, refundMoney));
        }
    }

    @Override
    public void submitApplyRefundSuccess(RefundResultDate refundResultDate) {
        if (refundResultDate != null) {
            String applyRefundOrder = refundResultDate.getService_no();
            ReturnTheGoodDetailsActivity.start(mContext, applyRefundType, applyRefundOrder);
        }
        finish();
    }

    @Override
    public void getQinIuSetMessageSuccess(QinIuBean message) {
        if (message != null) {
            qinIuToken = message.getToken();
        }
    }

    @Override
    public void onNewWorkException(String message) {
        dismissDialog();
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        dismissDialog();
        showToast(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.isEmpty()) return;
                for (int i = 0; i < selectList.size(); i++) {
                    mSelectImagePath.add(selectList.get(i).getPath());
                }
                updateImageViews();
        }
    }

    /**
     * 更新选择的图片
     */
    private void updateImageViews() {
        if (mSelectImagePath.size() > 0) {
            bgaSortablePhotoLayout.setVisibility(View.VISIBLE);
            imageAdapter.notifyDataSetChanged();
        } else {
            bgaSortablePhotoLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == imageAdapter.getCount() - 1 && imageAdapter.isShow()) {
            if (mSelectImagePath.size() == MAX_NUM) {
                showToast("最多上传" + MAX_NUM + "张图片");
            } else {
                if (qinIuImages.size() > 0) qinIuImages.clear();
                PictureSelectorUtils.selectImageOfMore(this, 3);
            }
        }
    }
}
