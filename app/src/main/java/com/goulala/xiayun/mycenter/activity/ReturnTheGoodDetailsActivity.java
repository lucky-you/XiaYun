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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.pickerview.OnSelectConditionsClickListener;
import com.goulala.xiayun.common.pickerview.PickerViewConditionsUtils;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.mycenter.adapter.ImageViewAdapter;
import com.goulala.xiayun.mycenter.adapter.ShopListMessageAdapter;
import com.goulala.xiayun.mycenter.callback.OnRefundGoodOrMoneyClickListener;
import com.goulala.xiayun.mycenter.dialog.OperatingResultDialog;
import com.goulala.xiayun.mycenter.model.ApplyRefundDetailsDate;
import com.goulala.xiayun.mycenter.model.BankCardList;
import com.goulala.xiayun.mycenter.model.RecordBean;
import com.goulala.xiayun.mycenter.model.ShopItemMessage;
import com.goulala.xiayun.mycenter.presenter.TheGoodDetailsRefundPresenter;
import com.goulala.xiayun.mycenter.view.ITheGoodDetailsRefundView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 申请售后- 退货详情
 * 申请售后- 退款详情
 * 在发货之后使用
 * 公用的activity
 */
public class ReturnTheGoodDetailsActivity extends BaseMvpActivity<TheGoodDetailsRefundPresenter> implements ITheGoodDetailsRefundView {


    private TextView tvRefundStatus;
    private ImageView ivSubmitAnApplication;
    private TextView tvSubmitAnApplication;
    private ImageView ivTheReviewOfTheService;
    private TextView tvTheReviewOfTheService;
    private ImageView ivFastMoney;
    private TextView tvFastMoney;
    private ImageView ivRefundToComplete;
    private TextView tvRefundToComplete;
    private LinearLayout llRefundMoneyStepViewLayout;
    private TextView tvRefundStatusOfGood;
    private ImageView ivSubmitAnApplicationOfGood;
    private TextView tvSubmitAnApplicationOfGood;
    private ImageView ivTheReviewOfTheServiceOfGood;
    private TextView tvTheReviewOfTheServiceOfGood;
    private ImageView ivReturnGoodsOfGood;
    private TextView tvReturnGoodsOfGood;
    private ImageView ivCommodityInspectionOfGood;
    private TextView tvCommodityInspectionOfGood;
    private ImageView ivFastMoneyOfGood;
    private TextView tvFastMoneyOfGood;
    private ImageView ivRefundToCompleteOfGood;
    private TextView tvRefundToCompleteOfGood;
    private LinearLayout llRefundGoodStepViewLayout;
    private RecyclerView publicRecyclerView;
    private TextView tvNumberOfApplications;
    private TextView tvTheRefundAmount;
    private TextView tvTheReturnReason;
    private TextView tvToApplyForTime;
    private RecyclerView bgaSortablePhotoLayout;
    private LinearLayout llRefundGoodPhotoLayout;
    private TextView tvReviewTheMessage;
    private LinearLayout llCommunicationRecordLayout;
    private TextView tvPleaseSelectLogisticsCompany;
    private ImageView ivPleaseSelectLogisticsCompany;
    private RelativeLayout rlPleaseSelectLogisticsCompany;
    private EditText editPleaseFillInTheLogisticsNumber;
    private LinearLayout llReturnGoodsLayout;
    private View DivideBottomLine;
    private TextView tvOrderStatusOne;
    private TextView tvOrderStatusTwo;
    private TextView tvOrderStatusThree;
    private LinearLayout llBottomStatusLayout;
    private SmartRefreshLayout smartRefreshLayout;
    private int applyRefundType; //申请的类型，是退款还是退货
    private String applyRefundServiceOrder; //申请售后的订单编号
    private int applyRefundStatus; //申请售后的状态
    private ShopListMessageAdapter shopListMessageAdapter;
    private List<ShopItemMessage> shopItemMessageList = new ArrayList<>();
    private RecordBean recordBean; //沟通记录的信息
    private String ReviewMessage; //审核留言
    private String shopOrderNumber;//店铺的订单编号
    private ArrayList<String> mSelectImagePath = new ArrayList<>(); //图片的集合
    private ImageViewAdapter imageAdapter; //展示商品图片的adapter
    private List<BankCardList> companyList = new ArrayList<>(); //物流公司的集合
    private int logisticsCompanyId = 0; //物流公司的id
    private String itemOrder, itemNumber; //商品的订单编号和商品的数量
    private String expressNumber;//物流单号
    private OperatingResultDialog operatingResultDialog;
    private List<LocalMedia> selectList = new ArrayList<>();

    public static void start(Context context, int type, String applyOrder) {
        Intent intent = new Intent(context, ReturnTheGoodDetailsActivity.class);
        intent.putExtra(ConstantValue.TYPE, type);
        intent.putExtra(ConstantValue.ORDER_NUMBER, applyOrder);
        context.startActivity(intent);
    }


    @Override
    protected TheGoodDetailsRefundPresenter createPresenter() {
        return new TheGoodDetailsRefundPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        applyRefundType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
        applyRefundServiceOrder = getIntent().getStringExtra(ConstantValue.ORDER_NUMBER);
    }

    @Override
    public int loadViewLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_return_the_good_details;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        tvRefundStatus = get(R.id.tv_refund_status);
        ivSubmitAnApplication = get(R.id.iv_Submit_an_application);
        tvSubmitAnApplication = get(R.id.tv_Submit_an_application);
        ivTheReviewOfTheService = get(R.id.iv_The_review_of_the_service);
        tvTheReviewOfTheService = get(R.id.tv_The_review_of_the_service);
        ivFastMoney = get(R.id.iv_Fast_money);
        tvFastMoney = get(R.id.tv_Fast_money);
        ivRefundToComplete = get(R.id.iv_Refund_to_complete);
        tvRefundToComplete = get(R.id.tv_Refund_to_complete);
        llRefundMoneyStepViewLayout = get(R.id.ll_refund_money_step_view_layout);
        tvRefundStatusOfGood = get(R.id.tv_refund_status_of_good);
        ivSubmitAnApplicationOfGood = get(R.id.iv_Submit_an_application_of_good);
        tvSubmitAnApplicationOfGood = get(R.id.tv_Submit_an_application_of_good);
        ivTheReviewOfTheServiceOfGood = get(R.id.iv_The_review_of_the_service_of_good);
        tvTheReviewOfTheServiceOfGood = get(R.id.tv_The_review_of_the_service_of_good);
        ivReturnGoodsOfGood = get(R.id.iv_Return_goods_of_good);
        tvReturnGoodsOfGood = get(R.id.tv_Return_goods_of_good);
        ivCommodityInspectionOfGood = get(R.id.iv_Commodity_inspection_of_good);
        tvCommodityInspectionOfGood = get(R.id.tv_Commodity_inspection_of_good);
        ivFastMoneyOfGood = get(R.id.iv_Fast_money_of_good);
        tvFastMoneyOfGood = get(R.id.tv_Fast_money_of_good);
        ivRefundToCompleteOfGood = get(R.id.iv_Refund_to_complete_of_good);
        tvRefundToCompleteOfGood = get(R.id.tv_Refund_to_complete_of_good);
        llRefundGoodStepViewLayout = get(R.id.ll_refund_good_step_view_layout);
        publicRecyclerView = get(R.id.public_RecyclerView);
        tvNumberOfApplications = get(R.id.tv_Number_of_applications);
        tvTheRefundAmount = get(R.id.tv_The_refund_amount);
        tvTheReturnReason = get(R.id.tv_The_return_reason);
        tvToApplyForTime = get(R.id.tv_To_apply_for_time);
        bgaSortablePhotoLayout = get(R.id.bgaSortablePhotoLayout);
        llRefundGoodPhotoLayout = get(R.id.ll_refund_good_photo_layout);
        tvReviewTheMessage = get(R.id.tv_Review_the_message);
        llCommunicationRecordLayout = get(R.id.ll_Communication_record_layout);
        llCommunicationRecordLayout.setOnClickListener(this);
        tvPleaseSelectLogisticsCompany = get(R.id.tv_Please_select_logistics_company);
        ivPleaseSelectLogisticsCompany = get(R.id.iv_Please_select_logistics_company);
        rlPleaseSelectLogisticsCompany = get(R.id.rl_Please_select_logistics_company);
        rlPleaseSelectLogisticsCompany.setOnClickListener(this);
        editPleaseFillInTheLogisticsNumber = get(R.id.edit_Please_fill_in_the_logistics_number);
        llReturnGoodsLayout = get(R.id.ll_Return_goods_layout);
        DivideBottomLine = get(R.id.Divide_Bottom_Line);
        tvOrderStatusOne = get(R.id.tv_order_status_one);
        tvOrderStatusOne.setOnClickListener(this);
        tvOrderStatusTwo = get(R.id.tv_order_status_two);
        tvOrderStatusTwo.setOnClickListener(this);
        tvOrderStatusThree = get(R.id.tv_order_status_three);
        tvOrderStatusThree.setOnClickListener(this);
        llBottomStatusLayout = get(R.id.ll_bottom_status_layout);
        smartRefreshLayout = get(R.id.smartRefreshLayout);
        //底部布局是复用的，所以一开始只显示按钮二
        tvOrderStatusOne.setVisibility(View.GONE);
        tvOrderStatusTwo.setVisibility(View.VISIBLE);
        tvOrderStatusThree.setVisibility(View.GONE);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        smartRefreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        smartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        switch (applyRefundType) {
            case ConstantValue.APPLY_FOR_REFUND_GOOD_TYPE: //退货
                initTitle(mContext.getString(R.string.Apply_for_after_sales_The_return_details));
                llRefundGoodStepViewLayout.setVisibility(View.VISIBLE);
                llRefundMoneyStepViewLayout.setVisibility(View.GONE);
                break;

            case ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE: //退款
                initTitle(mContext.getString(R.string.Apply_for_after_sales_The_return_money_details));
                llRefundMoneyStepViewLayout.setVisibility(View.VISIBLE);
                llRefundGoodStepViewLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        getDate();
        shopListMessageAdapter = new ShopListMessageAdapter(shopItemMessageList);
        publicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        publicRecyclerView.setAdapter(shopListMessageAdapter);
        publicRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        publicRecyclerView.setNestedScrollingEnabled(false);

        imageAdapter = new ImageViewAdapter(mSelectImagePath);
        bgaSortablePhotoLayout.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        bgaSortablePhotoLayout.setAdapter(imageAdapter);
        bgaSortablePhotoLayout.setNestedScrollingEnabled(false);

        //图片查看大图
        imageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PictureSelector.create(ReturnTheGoodDetailsActivity.this)
                        .themeStyle(R.style.picture_white_style)
                        .openExternalPreview(position, selectList);
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDate();
                refreshLayout.finishRefresh();
            }
        });
    }

    private void getDate() {
        //获取退款详情的数据
        getApplyRefundMoneyDate();
        //获取物流公司的信息
        selectLogisticsCompany();

    }

    /**
     * 获取退款详情的数据
     */
    private void getApplyRefundMoneyDate() {
        Map<String, String> applyRefundParam = new HashMap<>();
        applyRefundParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.AFTER_THE_DETAILS_URL);
        applyRefundParam.put(ApiParam.SERVICE_NO, applyRefundServiceOrder);
        String applyRefundParamJson = JsonUtils.toJson(applyRefundParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getApplyRefundDateDetails(userToken, applyRefundParamJson);

    }

    /**
     * 获取物流公司的信息
     */
    private void selectLogisticsCompany() {
        Map<String, String> LogisticsParam = new HashMap<>();
        LogisticsParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.SELECT_LOGISTICS_COMPANY_URL);
        String LogisticsParamJson = JsonUtils.toJson(LogisticsParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.accessLogisticsCompany(userToken, LogisticsParamJson);

    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.ll_Communication_record_layout:
                //沟通记录
                CommunicationRecordActivity.start(mContext, applyRefundServiceOrder);
                break;
            case R.id.rl_Please_select_logistics_company:
                //选择物流公司
                if (applyRefundStatus == ApiParam.CUSTOMER_SERVICE_APPROVAL) {
                    chooseLogisticsCompany();
                }
                break;
            case R.id.tv_order_status_one:
                //按钮一
                bottomButtonOneClick();
                break;
            case R.id.tv_order_status_two:
                //按钮二
                bottomButtonTwoClick();
                break;
            case R.id.tv_order_status_three:
                //按钮三
                break;
        }
    }

    @Override
    public void getApplyRefundDetailsSuccess(ApplyRefundDetailsDate applyRefundDetailsDate) {
        if (applyRefundDetailsDate != null) {
            shopOrderNumber = applyRefundDetailsDate.getShop_order();
            itemOrder = applyRefundDetailsDate.getItem_order();
            applyRefundStatus = applyRefundDetailsDate.getLast_status();
            shopItemMessageList = applyRefundDetailsDate.getList();
            itemNumber = applyRefundDetailsDate.getNum() + "";
            tvNumberOfApplications.setText(itemNumber);
            tvTheRefundAmount.setText(mContext.getString(R.string.the_price, applyRefundDetailsDate.getMoney() + ""));
            tvTheReturnReason.setText(applyRefundDetailsDate.getReason());
            tvToApplyForTime.setText(DateUtils.getStrTime(applyRefundDetailsDate.getCreatetime()));
            if (shopItemMessageList != null && shopItemMessageList.size() > 0) {
                shopListMessageAdapter.setNewData(shopItemMessageList);
            }
            //沟通记录
            recordBean = applyRefundDetailsDate.getRecord();
            if (recordBean != null) {
                ReviewMessage = recordBean.getRemark(); //审核留言
                logisticsCompanyId = recordBean.getExpress_company_id(); // 物流公司id
                expressNumber = recordBean.getService_info();  // 物流单号
            }
            //根据状态显示控件
            showViewOfStatus(applyRefundStatus);
            // 商品的图片
            mSelectImagePath = (ArrayList<String>) applyRefundDetailsDate.getImages();
            if (mSelectImagePath != null && mSelectImagePath.size() > 0) {
                llRefundGoodPhotoLayout.setVisibility(View.VISIBLE);
                imageAdapter.setNewData(mSelectImagePath);
                if (selectList.size() > 0) selectList.clear();
                for (int i = 0; i < mSelectImagePath.size(); i++) {
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(mSelectImagePath.get(i));
                    selectList.add(localMedia);
                }
            }
        }
    }

    /**
     * 更具状态显示数据
     *
     * @param applyRefundStatus
     */
    private void showViewOfStatus(int applyRefundStatus) {
        switch (applyRefundType) {
            case ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE:
                //退款
                applyRefundMoney(applyRefundStatus);
                break;
            case ConstantValue.APPLY_FOR_REFUND_GOOD_TYPE:
                //退货
                applyRefundGood(applyRefundStatus);
                break;
        }
    }

    /**
     * @param applyRefundStatus 退款
     */
    private void applyRefundMoney(int applyRefundStatus) {
        switch (applyRefundStatus) {
            case ApiParam.TO_APPLY_FOR_TYPE: //提交申请
                //隐藏沟通记录的布局
                tvRefundStatus.setText(mContext.getString(R.string.The_review_of_the_service));
                setAfterSalesStatus(false, true,
                        false, "",
                        true, mContext.getString(R.string.Cancel_the_application));

                break;
            case ApiParam.CUSTOMER_SERVICE_REVIEW_REJECTION: //客服审核拒绝
                //显示沟通记录的布局
                tvRefundStatus.setText(mContext.getString(R.string.Audit_refused_to));
                ivTheReviewOfTheService.setImageResource(R.drawable.ic_refund_shut_down_background);
                tvTheReviewOfTheService.setText(mContext.getString(R.string.Audit_refused_to));
                setAfterSalesStatus(true, true,
                        true, mContext.getString(R.string.To_resubmit),
                        true, mContext.getString(R.string.Cancel_the_application));

                break;
            case ApiParam.CUSTOMER_SERVICE_APPROVAL: //客服审核通过
                //显示沟通记录的布局
                tvRefundStatus.setText(mContext.getString(R.string.Fast_money));
                ivTheReviewOfTheService.setImageResource(R.drawable.ic_refund_complete_background);
                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");
                break;

            case ApiParam.FINANCIAL_AUDIT_DID_PASS: //财务审核不通过
                tvRefundStatus.setText(mContext.getString(R.string.Fast_money));
                ivFastMoney.setImageResource(R.drawable.ic_refund_shut_down_background);
                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");
                break;

            case ApiParam.FINANCIAL_AUDIT_THROUGH_THE_RAPID_PATMENT: //财务审核通过急速打款
                tvRefundStatus.setText(mContext.getString(R.string.Fast_money));
                ivFastMoney.setImageResource(R.drawable.ic_refund_complete_background);
                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");
                break;

            case ApiParam.THAT_COMPLETE_TYPE: //完成
                tvRefundStatus.setText(mContext.getString(R.string.Refund_to_complete));

                ivTheReviewOfTheService.setImageResource(R.drawable.ic_refund_complete_background);
                ivFastMoney.setImageResource(R.drawable.ic_refund_complete_background);
                ivRefundToComplete.setImageResource(R.drawable.ic_refund_complete_background);

                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");
                break;

        }
    }

    /**
     * 根据状态判断是否显示沟通布局和底部的按钮布局
     *
     * @param isShowCommunicationLayout 是否显示沟通记录的布局
     * @param isShowBottomButtonLayout  是否显示底部布局
     * @param isShowBottomButtonOne     是否显示按钮一
     * @param tvButtonOneTitle          按钮一显示的文本
     * @param isShowBottomButtonTwo     是否显示按钮二
     * @param tvButtonTwoTitle          按钮二显示的文本
     */
    private void setAfterSalesStatus(
            boolean isShowCommunicationLayout,
            boolean isShowBottomButtonLayout,
            boolean isShowBottomButtonOne,
            String tvButtonOneTitle,
            boolean isShowBottomButtonTwo,
            String tvButtonTwoTitle

    ) {
        if (isShowCommunicationLayout) {
            llCommunicationRecordLayout.setVisibility(View.VISIBLE);
            tvReviewTheMessage.setText(ReviewMessage);
        } else {
            llCommunicationRecordLayout.setVisibility(View.GONE);
        }
        if (isShowBottomButtonLayout) {
            llBottomStatusLayout.setVisibility(View.VISIBLE);
        } else {
            llBottomStatusLayout.setVisibility(View.GONE);
        }
        if (isShowBottomButtonOne) {
            tvOrderStatusOne.setVisibility(View.VISIBLE);
            tvOrderStatusOne.setText(tvButtonOneTitle);
        } else {
            tvOrderStatusOne.setVisibility(View.GONE);
        }
        if (isShowBottomButtonTwo) {
            tvOrderStatusTwo.setVisibility(View.VISIBLE);
            tvOrderStatusTwo.setText(tvButtonTwoTitle);
        } else {
            tvOrderStatusTwo.setVisibility(View.GONE);
        }
    }


    /**
     * @param applyRefundStatus 退货
     */
    private void applyRefundGood(int applyRefundStatus) {
        switch (applyRefundStatus) {
            case ApiParam.TO_APPLY_FOR_TYPE: //提交申请
                //隐藏沟通记录的布局
                tvRefundStatusOfGood.setText(mContext.getString(R.string.The_review_of_the_service));

                setAfterSalesStatus(false, true,
                        false, "",
                        true, mContext.getString(R.string.Cancel_the_application));

                break;
            case ApiParam.CUSTOMER_SERVICE_REVIEW_REJECTION: //客服审核拒绝
                //显示沟通记录的布局
                tvRefundStatusOfGood.setText(mContext.getString(R.string.Audit_refused_to));

                ivTheReviewOfTheServiceOfGood.setImageResource(R.drawable.ic_refund_shut_down_background);
                tvTheReviewOfTheService.setText(mContext.getString(R.string.Audit_refused_to));

                setAfterSalesStatus(true, true,
                        true, mContext.getString(R.string.To_resubmit),
                        true, mContext.getString(R.string.Cancel_the_application));

                break;
            case ApiParam.CUSTOMER_SERVICE_APPROVAL: //客服审核通过
                //显示沟通记录的布局
                tvRefundStatusOfGood.setText(mContext.getString(R.string.Return_goods));
                ivTheReviewOfTheServiceOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                //显示物流的布局
                llReturnGoodsLayout.setVisibility(View.VISIBLE);

                setAfterSalesStatus(true, true,
                        false, "",
                        true, mContext.getString(R.string.Continue_submit));
                break;

            case ApiParam.THE_BUYER_TO_DELIVER_GOODS: //买家发货-->商品寄回
                //显示沟通记录的布局
                tvRefundStatusOfGood.setText(mContext.getString(R.string.Commodity_inspection));
                ivTheReviewOfTheServiceOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivReturnGoodsOfGood.setImageResource(R.drawable.ic_refund_complete_background);

                setAfterSalesStatus(true, true,
                        false, "",
                        true, mContext.getString(R.string.Check_the_logistics));

                //显示物流的布局
                llReturnGoodsLayout.setVisibility(View.VISIBLE);
                llReturnGoodsLayout.setEnabled(false); //只显示数据，不可点击操作
                ivPleaseSelectLogisticsCompany.setVisibility(View.GONE);
                editPleaseFillInTheLogisticsNumber.setFocusable(false);
                editPleaseFillInTheLogisticsNumber.setFocusableInTouchMode(false);
                editPleaseFillInTheLogisticsNumber.setText(recordBean.getService_info());
                tvPleaseSelectLogisticsCompany.setText(recordBean.getExpress_company());
                tvPleaseSelectLogisticsCompany.setTextColor(mContext.getResources().getColor(R.color.color_3f3f3f_black));
                break;

            case ApiParam.FAILURE_OF_COMMODITY_AUDIT://商品审核失败
                //显示沟通记录的布局
                tvRefundStatusOfGood.setText(mContext.getString(R.string.Commodity_inspection));
                ivTheReviewOfTheServiceOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivReturnGoodsOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivCommodityInspectionOfGood.setImageResource(R.drawable.ic_refund_shut_down_background);

                setAfterSalesStatus(true, true,
                        true, mContext.getString(R.string.To_resubmit),
                        true, mContext.getString(R.string.Cancel_the_application));

                break;

            case ApiParam.APPROVAL_OF_GOODS: //商品审核通过

                tvRefundStatusOfGood.setText(mContext.getString(R.string.Fast_money));
                ivTheReviewOfTheServiceOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivReturnGoodsOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivCommodityInspectionOfGood.setImageResource(R.drawable.ic_refund_complete_background);

                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");

                break;
            case ApiParam.FINANCIAL_AUDIT_DID_PASS: //财务审核不通过
                tvRefundStatusOfGood.setText(mContext.getString(R.string.Fast_money));
                ivTheReviewOfTheServiceOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivReturnGoodsOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivCommodityInspectionOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivFastMoneyOfGood.setImageResource(R.drawable.ic_refund_shut_down_background);


                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");

                break;

            case ApiParam.FINANCIAL_AUDIT_THROUGH_THE_RAPID_PATMENT: //财务审核通过-->急速打款
                tvRefundStatusOfGood.setText(mContext.getString(R.string.Fast_money));
                ivTheReviewOfTheServiceOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivReturnGoodsOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivCommodityInspectionOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivFastMoneyOfGood.setImageResource(R.drawable.ic_refund_complete_background);


                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");
                break;

            case ApiParam.THAT_COMPLETE_TYPE:  //申请售后 退货完成
                tvRefundStatusOfGood.setText(mContext.getString(R.string.Return_to_complete));

                ivTheReviewOfTheServiceOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivReturnGoodsOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivCommodityInspectionOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivFastMoneyOfGood.setImageResource(R.drawable.ic_refund_complete_background);
                ivRefundToCompleteOfGood.setImageResource(R.drawable.ic_refund_complete_background);

                setAfterSalesStatus(true, false,
                        false, "",
                        false, "");

                break;

            case ApiParam.THAT_SHUT_DOWN_TYPE: //申请售后 关闭

                break;


        }

    }


    /**
     * 底部按钮一的监听
     */
    private void bottomButtonOneClick() {
        switch (applyRefundStatus) {
            case ApiParam.CUSTOMER_SERVICE_REVIEW_REJECTION: //客服审核拒绝，此时可以重新提交-->  退款退货都存在的状态
            case ApiParam.FAILURE_OF_COMMODITY_AUDIT://商品审核失败,此时可以重新提交 -->退货时候的状态
                //重新提交-->重新申请退款或者退货
                AlertDialogUtils.refundGoodOrMoneyDialog(mContext, new OnRefundGoodOrMoneyClickListener() {
                    @Override
                    public void onRefundGoodClick() {
                        ReturnTheGoodActivity.start(mContext, ConstantValue.APPLY_FOR_REFUND_GOOD_TYPE, shopOrderNumber, itemOrder, itemNumber);
                        finish();
                    }

                    @Override
                    public void onRefundMoneyClick() {
                        ReturnTheGoodActivity.start(mContext, ConstantValue.APPLY_FOR_REFUND_MONEY_TYPE, shopOrderNumber, itemOrder, itemNumber);
                        finish();
                    }
                });
                break;
        }
    }

    /**
     * 按钮二的监听
     */
    private void bottomButtonTwoClick() {
        switch (applyRefundStatus) {
            case ApiParam.TO_APPLY_FOR_TYPE: //已申请，此时可以撤销申请 ---》退款退货都存在的状态
            case ApiParam.CUSTOMER_SERVICE_REVIEW_REJECTION: //客服审核解决--》撤销申请
            case ApiParam.FAILURE_OF_COMMODITY_AUDIT://商品审核失败 ---》退货时的状态
                cancelTheApplication();
                break;
            case ApiParam.CUSTOMER_SERVICE_APPROVAL: //客服审核通过,需要寄回商品，选择物流信息之后，继续提交--》退货时的状态
                if (0 == logisticsCompanyId) {
                    showToast(mContext.getString(R.string.Please_select_logistics_company));
                    return;
                }
                String logisticsNumber = editPleaseFillInTheLogisticsNumber.getText().toString().trim();
                if (TextUtils.isEmpty(logisticsNumber)) {
                    showToast(mContext.getString(R.string.Please_fill_in_the_logistics_number));
                    return;
                }
                continueToSubmit(logisticsNumber);
                break;
            case ApiParam.THE_BUYER_TO_DELIVER_GOODS: //买家发货-->商品寄回 --》此时为查看物流--》退货时的状态
                LogisticsDetailsActivity.start(mContext, String.valueOf(logisticsCompanyId), expressNumber);
                break;

        }

    }

    /**
     * 继续提交
     */
    private void continueToSubmit(String LogisticsNumber) {
        Map<String, String> continueParam = new HashMap<>();
        continueParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CONTINUE_TO_SUBMIT_URL);
        continueParam.put(ApiParam.SERVICE_NO, applyRefundServiceOrder);
        continueParam.put(ApiParam.SERVICE_INFO, LogisticsNumber);
        continueParam.put(ApiParam.EXPRESS_COMPANY_ID, String.valueOf(logisticsCompanyId));
        String continueParamJson = JsonUtils.toJson(continueParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.cancelTheApplicationOrSubmit(ConstantValue.CONTIUNE_TO_SUBMIT_TYPE, userToken, continueParamJson);
        showDialog("");

    }


    /**
     * 撤销申请
     */
    private void cancelTheApplication() {
        String cancelTitle;
        if (applyRefundType == ConstantValue.APPLY_FOR_REFUND_GOOD_TYPE) {
            //退货
            cancelTitle = mContext.getString(R.string.Are_you_sure_you_want_to_withdraw_your_application_of_good);
        } else {
            //退款
            cancelTitle = mContext.getString(R.string.Are_you_sure_you_want_to_withdraw_your_application);
        }
        AlertDialogUtils.showCustomerDialog(mContext, cancelTitle, new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {

            }

            @Override
            public void determineClick(View view) {
                Map<String, String> cancelParam = new HashMap<>();
                cancelParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CANCEL_THE_APPLICATION_URL);
                cancelParam.put(ApiParam.SERVICE_NO, applyRefundServiceOrder);
                String cancelParamJson = JsonUtils.toJson(cancelParam);
                if (!TextUtils.isEmpty(userToken)) {
                    mvpPresenter.cancelTheApplicationOrSubmit(ConstantValue.CANCEL_THE_APPLICATION_TYPE, userToken, cancelParamJson);
                }
            }
        });

    }

    /**
     * 选择物流公司
     */
    private void chooseLogisticsCompany() {
        PickerViewConditionsUtils.selectConditionsView(this, companyList, new OnSelectConditionsClickListener() {
            @Override
            public void onConditionsSelect(String name, int id) {
                tvPleaseSelectLogisticsCompany.setText(name);
                tvPleaseSelectLogisticsCompany.setTextColor(mContext.getResources().getColor(R.color.color_3f3f3f_black));
                logisticsCompanyId = id;//物流公司的id
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (operatingResultDialog != null) {
            operatingResultDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void cancelTheApplicationOrToSubmitSuccess(int requestType, String message) {
        dismissDialog();
        switch (requestType) {
            case ConstantValue.CANCEL_THE_APPLICATION_TYPE://撤销申请
                operatingResultDialog = AlertDialogUtils.showOperatingResultDialog(mContext, message);
                finish();
                break;
            case ConstantValue.CONTIUNE_TO_SUBMIT_TYPE://继续提交
                showToast(message);
                //获取退款详情的数据
                getApplyRefundMoneyDate();
                break;
        }

    }

    @Override
    public void accessLogisticsCompanySuccess(List<BankCardList> companyList) {
        if (companyList != null) {
            this.companyList = companyList;
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
}
