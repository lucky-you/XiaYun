package com.goulala.xiayun.home.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.activity.LoginActivity;
import com.goulala.xiayun.common.banner.BannerUtils;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.pickerview.PickerViewCityUtils;
import com.goulala.xiayun.common.share.ShareCallBackListener;
import com.goulala.xiayun.common.share.ShareSDKShareUtils;
import com.goulala.xiayun.common.utils.ButtonClickUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.LogUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.home.adapter.ProductDetailsMessageAdapter;
import com.goulala.xiayun.home.dialog.ShareCommissionDialog;
import com.goulala.xiayun.home.model.DetailsDescriptionList;
import com.goulala.xiayun.home.model.GoodActivityBean;
import com.goulala.xiayun.home.model.GoodsDetailsMessage;
import com.goulala.xiayun.home.presenter.HomeGoodsDetailsPresenter;
import com.goulala.xiayun.home.view.IHomeGoodsDetailsView;
import com.goulala.xiayun.mycenter.activity.TheMemberCenterActivity;
import com.goulala.xiayun.shopcar.activity.MakeSureTheOrderActivity;
import com.goulala.xiayun.shopcar.activity.ShopCarActivity;
import com.goulala.xiayun.shopcar.model.GoodItemList;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;

/**
 * 首页的商品详情
 */
public class HomeGoodsDetailsActivity extends BaseMvpActivity<HomeGoodsDetailsPresenter> implements IHomeGoodsDetailsView {


    private Banner homeGoodDetailBanner;
    private TextView tvHaveSold;
    private TextView tvNoActivityGoodPrice;
    private LinearLayout llHaveNoActivityPrice;
    private TextView tvNowPrice;
    private TextView tvActivityName;
    private TextView tvTheOriginalPrice;
    private LinearLayout llActivityPriceAndTitle;
    private TextView tvActivityCountDown;
    private CountdownView tvDistanceEndTime;
    private LinearLayout llHaveActivityPrice;
    private TextView tvMemberPrice;
    private TextView tvOpenMembershipNow;
    private TextView tvSharingProductGetCommission;
    private TextView tvCheckTheDetails;
    private TextView tvGoodName;
    private TextView tvGoodDetailsMessage;
    private TextView tvSalesPromotionFullReductionMoney;
    private RelativeLayout rlSalesPromotion;
    private View rlSalesPromotionDivideLine;
    private TextView tvIsFailure;
    private LinearLayout llNotFailureLayout;
    private TextView tvTheDeliveryAddress;
    private TextView tvShippingAddress;
    private TextView tvIsPackageMail;
    private RelativeLayout rlSelectDistributionAddress;
    private RelativeLayout rlGoodNumberToReduce;
    private TextView tvGoodNumber;
    private RelativeLayout rlGoodNumberIncrease;
    private TextView tvTheQuantityInventory;
    private TextView tvShowTitle;
    private RecyclerView productDetailsRecyclerView;
    private LinearLayout llProductBottomImageView;
    private NestedScrollView goodDetailsScrollView;
    private SmartRefreshLayout smartRefreshLayout;
    private ImageView icDetailsNbReturn;
    private ImageView icDetailsNbFollow;
    private ImageView icDetailsNbShare;
    private LinearLayout rlDetailsTopTitle;
    private LinearLayout llCustomerService;
    private TextView tvShopCarNumber;
    private LinearLayout llShoppingBags;
    private TextView tvAddTheShoppingBag;
    private TextView tvBuyNow;
    private LinearLayout llGoodDetailsBottomLayout;
    private ImageView ivHint;
    private TextView tvHint;
    private RelativeLayout rlEmptyLayout;
    private ProductDetailsMessageAdapter productDetailsMessageAdapter;
    private List<DetailsDescriptionList> goodsDetailsMessages = new ArrayList<>(); //详情描述的数据
    private int goodId; //商品的ID
    private boolean thatGoodIsCollect; //商品是否收藏
    private int goodNumber = 1;  //商品的数量
    private int goodInventoryNumber; //商品的总库存
    private int merchant_id; //店铺的id
    private String goodTitleName, goodDescription, goodImageView, goodShareUrl; //商品的名称，描述，图片,分享链接
    private String defaultDeliveryAddress; //默认的发货地址
    private String defaultShippingAddress;//默认的收货地址
    private String goodActivityTitle;//活动的标题
    private int goodActivityId; //商品活动的id
    private double shareFeeMix, shareFeeMax;
    //    private LocationMapUtils locationMapUtils;
    private boolean goodStatus;//商品状态--》失效为false，正常为true
    private int classType;


    public static void start(Context context, int goodId, int type) {
        Intent intent = new Intent(context, HomeGoodsDetailsActivity.class);
        intent.putExtra(ConstantValue.ID, goodId);
        intent.putExtra(ConstantValue.TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected HomeGoodsDetailsPresenter createPresenter() {
        return new HomeGoodsDetailsPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        goodId = getIntent().getIntExtra(ConstantValue.ID, -1);
        classType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_home_goods_details;
    }

    @Override
    public void bindViews(View contentView) {
//        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        StatusBarUtil.setStatusBar(this, false, false);
        homeGoodDetailBanner = get(R.id.homeGoodDetailBanner);
        tvHaveSold = get(R.id.tv_have_sold);
        tvNoActivityGoodPrice = get(R.id.tv_no_activity_good_price);
        llHaveNoActivityPrice = get(R.id.ll_have_no_activity_price);
        tvNowPrice = get(R.id.tv_now_price);
        tvActivityName = get(R.id.tv_activity_name);
        tvTheOriginalPrice = get(R.id.tv_The_original_price);
        llActivityPriceAndTitle = get(R.id.ll_activity_price_and_title);
        tvActivityCountDown = get(R.id.tv_activity_CountDown);
        tvDistanceEndTime = get(R.id.tv_Distance_end_time);
        llHaveActivityPrice = get(R.id.ll_have_activity_price);
        tvMemberPrice = get(R.id.tv_member_price);
        tvOpenMembershipNow = get(R.id.tv_Open_membership_now);
        tvOpenMembershipNow.setOnClickListener(this);
        tvSharingProductGetCommission = get(R.id.tv_sharing_product_get_commission);
        tvCheckTheDetails = get(R.id.tv_Check_the_details);
        tvCheckTheDetails.setOnClickListener(this);
        tvGoodName = get(R.id.tv_good_name);
        tvGoodDetailsMessage = get(R.id.tv_good_details_message);
        tvSalesPromotionFullReductionMoney = get(R.id.tv_Sales_promotion_Full_reduction_money);
        rlSalesPromotion = get(R.id.rl_Sales_promotion);
        rlSalesPromotion.setOnClickListener(this);
        rlSalesPromotionDivideLine = get(R.id.rl_Sales_promotion_Divide_line);
        tvTheDeliveryAddress = get(R.id.tv_The_delivery_address);
        tvShippingAddress = get(R.id.tv_Shipping_address);
        tvIsPackageMail = get(R.id.tv_is_Package_mail);
        rlSelectDistributionAddress = get(R.id.rl_select_distribution_address);
        rlSelectDistributionAddress.setOnClickListener(this);
        rlGoodNumberToReduce = get(R.id.rl_good_number_to_reduce);
        rlGoodNumberToReduce.setOnClickListener(this);
        tvGoodNumber = get(R.id.tv_good_number);
        rlGoodNumberIncrease = get(R.id.rl_good_number_increase);
        rlGoodNumberIncrease.setOnClickListener(this);
        tvTheQuantityInventory = get(R.id.tv_The_quantity_inventory);
        tvShowTitle = get(R.id.tv_show_title);
        productDetailsRecyclerView = get(R.id.productDetailsRecyclerView);
        llProductBottomImageView = get(R.id.ll_product_bottom_imageView);
        goodDetailsScrollView = get(R.id.good_detailsScrollView);
        smartRefreshLayout = get(R.id.smartRefreshLayout);
        icDetailsNbReturn = get(R.id.ic_details_nb_return);
        icDetailsNbReturn.setOnClickListener(this);
        icDetailsNbFollow = get(R.id.ic_details_nb_follow);
        icDetailsNbFollow.setOnClickListener(this);
        icDetailsNbShare = get(R.id.ic_details_nb_share);
        icDetailsNbShare.setOnClickListener(this);
        rlDetailsTopTitle = get(R.id.rl_details_top_title);
        llCustomerService = get(R.id.ll_Customer_service);
        llCustomerService.setOnClickListener(this);
        tvShopCarNumber = get(R.id.tv_shop_car_number);
        llShoppingBags = get(R.id.ll_Shopping_bags);
        llShoppingBags.setOnClickListener(this);
        tvAddTheShoppingBag = get(R.id.tv_Add_the_shopping_bag);
        tvAddTheShoppingBag.setOnClickListener(this);
        tvBuyNow = get(R.id.tv_Buy_now);
        tvBuyNow.setOnClickListener(this);
        llGoodDetailsBottomLayout = get(R.id.ll_good_details_bottom_layout);
        ivHint = get(R.id.ivHint);
        tvHint = get(R.id.tvHint);
        rlEmptyLayout = get(R.id.rl_empty_layout);
        tvIsFailure = get(R.id.tvIsFailure);
        llNotFailureLayout = get(R.id.llNotFailureLayout);

        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        smartRefreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        smartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        tvGoodNumber.setText(goodNumber + "");

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void processLogic(Bundle savedInstanceState) {

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDate();
                refreshLayout.finishRefresh();

            }
        });
        goodDetailsScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int headHeight = homeGoodDetailBanner.getHeight() / 4;//高度是头部布局高度的1/4
                if (scrollY < headHeight) {
                    rlDetailsTopTitle.setBackgroundColor(getResources().getColor(R.color.white));
                    int progress = (int) (new Float(scrollY) / new Float(headHeight) * 200);//255
                    rlDetailsTopTitle.getBackground().mutate().setAlpha(progress);
                } else {
                    rlDetailsTopTitle.setBackgroundColor(getResources().getColor(R.color.white));
                    rlDetailsTopTitle.getBackground().setAlpha(255 - 55);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDate();
    }

    private void getDate() {
        if (goodsDetailsMessages.size() > 0) goodsDetailsMessages.clear();
//        List<TouristsGoodList> touristsGoodLists = DaoManagerUtils.queryAll();
//        if (touristsGoodLists != null && touristsGoodLists.size() > 0) {
//            tvShopCarNumber.setVisibility(View.VISIBLE);
//            tvShopCarNumber.setText(String.valueOf(touristsGoodLists.size()));
//        }
        //商品详情
        getGoodDetails();
        //检查token是否过期
        checkTokenIsOverdue();
        //获取商品分享的url链接
        getGoodShareUrl();
        //获取购物车中的商品数量
        getTotalNumberOfShopCar();
    }

    /**
     * 获取商品分享的url链接
     */
    private void getGoodShareUrl() {
        Map<String, String> shareParam = new HashMap<>();
        shareParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_GOOD_SHARE_URL);
        shareParam.put(ApiParam.ITEM_ID_KEY, String.valueOf(goodId));
        String shareParamJson = JsonUtils.toJson(shareParam);
        LogUtils.showLog(userToken, shareParamJson);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getGoodShareUrl(userToken, shareParamJson);
        }
    }

    /**
     * 商品详情
     */
    private void getGoodDetails() {
        Map<String, String> goodDetailsParam = new HashMap<>();
        goodDetailsParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_GOOD_DETAILS_VALUE);
        goodDetailsParam.put(ApiParam.ITEM_ID_KEY, String.valueOf(goodId));
        String goodDetailsJson = JsonUtils.toJson(goodDetailsParam);
        LogUtils.showLog(userToken, goodDetailsJson);
        mvpPresenter.getHomeGoodDetailsMessage(userToken, goodDetailsJson);
    }

    /**
     * 获取购物车中的商品数量
     */
    private void getTotalNumberOfShopCar() {
        Map<String, String> totalParam = new HashMap<>();
        totalParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_THE_TOTAL_GOOD_ITEMS_OF_YOU_SHOP_CAR);
        String totalParamJson = JsonUtils.toJson(totalParam);
        LogUtils.showLog(userToken, totalParamJson);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getTheTotalNumberOfShopCar(userToken, totalParamJson);
        }
    }

    /**
     * 检查token是否过期
     */
    private void checkTokenIsOverdue() {
        Map<String, String> tokenParam = new HashMap<>();
        tokenParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CHECK_TOKEN_IS_OVERDUE_VALUE);
        String tokenParamJson = JsonUtils.toJson(tokenParam);
        mvpPresenter.checkUserTokenIsOverdue(userToken, tokenParamJson);
    }


    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.ic_details_nb_return:
                finish();
                break;
            case R.id.ic_details_nb_follow:
                //收藏
                if (TextUtils.isEmpty(userToken)) {
                    LoginActivity.start(mContext);
                } else {
                    if (thatGoodIsCollect) {
                        collectionOrCancelThatGood(ApiParam.CANCEL_COLLECTION_THAT_GOOD_VALUE, ConstantValue.CANCEL_COLLECTION_THAT_GOOD_TYPE);
                        thatGoodIsCollect = !thatGoodIsCollect;
                    } else {
                        collectionOrCancelThatGood(ApiParam.COLLECTION_THAT_GOOD_VALUE, ConstantValue.COLLECTION_THAT_GOOD_TYPE);
                        thatGoodIsCollect = !thatGoodIsCollect;
                    }
                }
                break;
            case R.id.ic_details_nb_share:
                //分享
                if (TextUtils.isEmpty(userToken)) {
                    LoginActivity.start(mContext);
                } else {
                    startShareGood();
                }
                break;
            case R.id.rl_Open_membership_now:
            case R.id.tv_Open_membership_now:
                //立即开通会员
                if (TextUtils.isEmpty(userToken)) {
                    LoginActivity.start(mContext);
                } else {
                    TheMemberCenterActivity.start(mContext);
                }
                break;
            case R.id.tv_Check_the_details:
                //查看佣金详情
                ShareCommissionDialog shareCommissionDialog = new ShareCommissionDialog(mContext);
                shareCommissionDialog.setShareFeePrice(shareFeeMix, shareFeeMax);
                break;
            case R.id.rl_Sales_promotion:
                //促销
                SellLotsOfDetailsActivity.start(mContext, goodActivityTitle, String.valueOf(goodActivityId), ConstantValue.THAT_CLASS_TYPE_OF_OTHER);
                break;
            case R.id.rl_select_distribution_address:
                if (!ButtonClickUtils.isFastClick()) {
                    showSelectAddressDialog();
                }
                break;
            case R.id.ll_Customer_service:
                //客服
                if (TextUtils.isEmpty(userToken)) {
                    LoginActivity.start(mContext);
                } else {
//                    helper.initSdkChat(ApiService.QIMO_IM_ACCESSID, UserUtils.getUserName(), UserUtils.getUserID(), "10043315");
                }
                break;
            case R.id.ll_Shopping_bags:
                //购物袋
                if (classType == ConstantValue.THE_TYPE_OF_SHOP_CAR) {
                    //从购物车进入商品详情，在商品详情点购物车时，直接返回购物车。
                    finish();
                } else {
                    if (TextUtils.isEmpty(userToken)) {
                        LoginActivity.start(mContext);
                    } else {
                        ShopCarActivity.start(mContext);
                    }
                }
                break;
            case R.id.tv_Add_the_shopping_bag:
                //加入购物车
                if (!goodStatus) return;
                if (TextUtils.isEmpty(userToken)) {
                    //游客登录,先把信息存储到本地，登陆之后，再合并到购物车
//                    if (goodId != 0 && merchant_id != 0) {
//                        TouristsGoodList touristsGoodList = new TouristsGoodList();
//                        touristsGoodList.setItem_id((long) goodId);
//                        touristsGoodList.setItem_num(goodNumber);
//                        touristsGoodList.setMerchant_id(merchant_id);
//                        DaoManagerUtils.saveTouristsGoodListDate(touristsGoodList);
//                        showToast(mContext.getString(R.string.Add_shopping_cart_successfully));
//                        getDate();
//                    }
                } else {
                    if (goodInventoryNumber > 0) {
                        addThatGoodToShopCar();
                    } else {
                        showToast(mContext.getString(R.string.Insufficient_inventory_two));
                    }
                }
                break;
            case R.id.tv_Buy_now:
                //立即购买
                if (!goodStatus) return;
                if (TextUtils.isEmpty(userToken)) {
                    LoginActivity.start(mContext);
                } else {
                    //还需要判断库存
                    if (goodInventoryNumber > 0) {
                        ArrayList<GoodItemList> goodItemLists = new ArrayList<>();
                        goodItemLists.add(new GoodItemList(goodId, goodNumber, merchant_id, ""));
                        MakeSureTheOrderActivity.start(mContext, goodItemLists, "", "");
                    } else {
                        showToast(mContext.getString(R.string.Insufficient_inventory_two));
                    }
                }
                break;
            case R.id.rl_good_number_to_reduce:
                //数量减少
                if (goodNumber > 1) {
                    goodNumber--;
                    tvGoodNumber.setText(goodNumber + "");
                }
                break;
            case R.id.rl_good_number_increase:
                //数量增加
                if (goodNumber < goodInventoryNumber) {
                    goodNumber++;
                    tvGoodNumber.setText(goodNumber + "");
                } else {
                    showToast(mContext.getString(R.string.The_maximum_number_of_purchases_has_been_exceeded));
                }
                break;
        }

    }

    /**
     * 分享商品
     */
    protected void startShareGood() {
        ShareSDKShareUtils.showShare(mContext,
                goodTitleName,
                goodDescription,
                goodShareUrl,
                goodImageView,
                new ShareCallBackListener() {
                    @Override
                    public void onSuccess() {
                        showToast(mContext.getString(R.string.share_success));
                    }

                    @Override
                    public void onFailed() {
                        showToast(mContext.getString(R.string.share_failed));
                    }

                    @Override
                    public void onCancel() {
                        showToast(mContext.getString(R.string.share_cancel));
                    }
                }
        );
    }

    /**
     * 选择城市
     */
    private void showSelectAddressDialog() {
        PickerViewCityUtils pickerViewCityUtils = new PickerViewCityUtils(HomeGoodsDetailsActivity.this);
        pickerViewCityUtils.setOnCitySelectClickListener(new PickerViewCityUtils.OnCitySelectClickListener() {
            @Override
            public void onSelectCityResult(String selectName, int province_Id, int city_Id, int area_Id) {
                setShippingAddress(defaultDeliveryAddress, selectName);
            }
        });
    }

    /**
     * 添加商品到购物车
     */
    private void addThatGoodToShopCar() {
        Map<String, String> addToShopCarParam = new HashMap<>();
        addToShopCarParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.ADD_GOOD_TO_SHOP_CAR_VALUE);
        addToShopCarParam.put(ApiParam.ITEM_ID_KEY, String.valueOf(goodId));
        addToShopCarParam.put(ApiParam.ITEM_NUMBER_KEY, String.valueOf(goodNumber));
        addToShopCarParam.put(ApiParam.MERCHANT_ID_KEY, String.valueOf(merchant_id));
        String addShopCarJson = JsonUtils.toJson(addToShopCarParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.commodityOperatingThatGood(ConstantValue.ADD_THAT_GOOD_TO_SHOP_CAR_TYPE, userToken, addShopCarJson);
        }
    }

    /**
     * 商品的收藏或者取消收藏
     */
    private void collectionOrCancelThatGood(String requestUrl, int requestType) {
        Map<String, String> cancelCollectionGood = new HashMap<>();
        cancelCollectionGood.put(ApiParam.BASE_METHOD_KEY, requestUrl);
        if (requestType == ConstantValue.COLLECTION_THAT_GOOD_TYPE) {
            cancelCollectionGood.put(ApiParam.ITEM_ID_KEY, String.valueOf(goodId));
        } else {
            cancelCollectionGood.put(ApiParam.ITEM_IDS_KEY, String.valueOf(goodId));
        }
        String cancelCollectionGoodJson = JsonUtils.toJson(cancelCollectionGood);
        if (!TextUtils.isEmpty(userToken)) {
            switch (requestType) {
                case ConstantValue.COLLECTION_THAT_GOOD_TYPE:
                    //收藏
                    mvpPresenter.commodityOperatingThatGood(ConstantValue.COLLECTION_THAT_GOOD_TYPE, userToken, cancelCollectionGoodJson);
                    break;
                case ConstantValue.CANCEL_COLLECTION_THAT_GOOD_TYPE:
                    //取消收藏
                    mvpPresenter.commodityOperatingThatGood(ConstantValue.CANCEL_COLLECTION_THAT_GOOD_TYPE, userToken, cancelCollectionGoodJson);
                    break;

            }

        }
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void loadHomeGoodDetailsMessage(GoodsDetailsMessage goodsDetailsMessage) {
        if (goodsDetailsMessage != null) {
            List<String> topBannerImageList = goodsDetailsMessage.getBroadcast_images();
            List<String> bottomDetailsImageList = goodsDetailsMessage.getDetail_images();
            //顶部的banner
            BannerUtils.loadGoodDetailsBanner(homeGoodDetailBanner, topBannerImageList);
            //商品的信息
            merchant_id = goodsDetailsMessage.getMerchant_id(); //获取店铺的id
            goodTitleName = goodsDetailsMessage.getName();   //商品名称
            goodDescription = goodsDetailsMessage.getDescription(); //商品描述
            goodImageView = goodsDetailsMessage.getSmallimage(); //商品图片
            tvGoodName.setText(goodTitleName);
            tvGoodDetailsMessage.setText(goodDescription);
            // 商品是否收藏
            thatGoodIsCollect = goodsDetailsMessage.isFavorite();
            goodIsCollection();
            if (1 == goodsDetailsMessage.getStatus()) {
                //没有失效
                goodStatus = true;
                tvIsFailure.setVisibility(View.GONE);
                llNotFailureLayout.setVisibility(View.VISIBLE);

            } else {
                //失效了
                goodStatus = false;
                tvIsFailure.setVisibility(View.VISIBLE);
                llNotFailureLayout.setVisibility(View.GONE);
            }
            defaultDeliveryAddress = goodsDetailsMessage.getProvince() + goodsDetailsMessage.getCity(); //发货地址
            setShippingAddress(defaultDeliveryAddress, defaultShippingAddress);//收货配送地址
            String Merchant_delivery_address = mContext.getString(R.string.Merchant_delivery, goodsDetailsMessage.getProvince() + goodsDetailsMessage.getCity());
            //底部的imageView
            BannerUtils.addBottomImageToGroup(mContext, llProductBottomImageView, bottomDetailsImageList);

            // 商品详情
            setGoodDetailsDate(
                    goodsDetailsMessage.getBrand(),
                    goodsDetailsMessage.getShelf_life(),
                    goodsDetailsMessage.getOrigin(),
                    goodsDetailsMessage.getSpecification(),
                    goodsDetailsMessage.getExpress_str(),
                    Merchant_delivery_address,
                    goodsDetailsMessage.getTaste(),
                    goodsDetailsMessage.getStorage()

            );

            //分销
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            shareFeeMix = goodsDetailsMessage.getShare_fee_mix();
            shareFeeMax = goodsDetailsMessage.getShare_fee_max();
            tvSharingProductGetCommission.setText(mContext.getString(R.string.the_price, numberFormat.format(shareFeeMix) + "-" + numberFormat.format(shareFeeMax)));
            //是否是会员
            if (goodsDetailsMessage.isVip()) {
                tvOpenMembershipNow.setVisibility(View.VISIBLE);
                tvOpenMembershipNow.setText("");
            } else {
                tvOpenMembershipNow.setVisibility(View.VISIBLE);
            }
            if (goodsDetailsMessage.getFreight() > 0) { //固定邮费
                tvIsPackageMail.setText(mContext.getString(R.string.The_postage_money, goodsDetailsMessage.getFreight() + ""));
            } else if (goodsDetailsMessage.getFreight() == 0) { //包邮
                tvIsPackageMail.setText(mContext.getString(R.string.Package_mail));
            }

            //活动相关
            List<GoodActivityBean> goodActivityBeanList = goodsDetailsMessage.getActive();
            if (goodActivityBeanList != null && goodActivityBeanList.size() > 0) {
                //有活动
                llHaveActivityPrice.setVisibility(View.VISIBLE);
                llHaveNoActivityPrice.setVisibility(View.GONE);
                GoodActivityBean goodActivityBean = goodActivityBeanList.get(0);
                if (goodActivityBean != null) {
                    goodActivityId = goodActivityBean.getId();// 活动的id
                    goodActivityTitle = goodActivityBean.getName();//活动的标题
                    switch (goodActivityBean.getActive_category_id()) {
                        case 1: //限时秒杀活动--》不显示促销的布局
                            rlSalesPromotion.setVisibility(View.GONE);
                            rlSalesPromotionDivideLine.setVisibility(View.GONE);
                            break;
                        case 2: //限时促销活动---》限时促销的布局
                            rlSalesPromotion.setVisibility(View.VISIBLE);
                            rlSalesPromotionDivideLine.setVisibility(View.VISIBLE);
                            tvSalesPromotionFullReductionMoney.setText(goodActivityTitle);
                            break;
                        default:
                            break;
                    }
                    long time = 0;
                    long currentTime = (System.currentTimeMillis() / 1000); //当前时间
                    long startTime = goodActivityBean.getStarttime(); //开始时间
                    long endTime = goodActivityBean.getEndtime();  //结束时间
                    if (goodActivityBean.getStock() > 0) {
                        //有活动，且库存大于0，需要判断时间
                        goodInventoryNumber = goodActivityBean.getStock(); //商品的库存
                        tvTheQuantityInventory.setText(mContext.getString(R.string.The_quantity_inventory, goodInventoryNumber + ""));
                        if (currentTime >= endTime) {
                            //活动结束,按照无活动处理
                            llHaveActivityPrice.setVisibility(View.GONE);
                            llHaveNoActivityPrice.setVisibility(View.VISIBLE);
                            tvNoActivityGoodPrice.setText(mContext.getString(R.string.the_price, goodsDetailsMessage.getPrice() + ""));
                            tvMemberPrice.setText(mContext.getString(R.string.the_price, goodsDetailsMessage.getMember_price() + ""));
                            goodInventoryNumber = goodsDetailsMessage.getStock(); //商品的库存
                            tvTheQuantityInventory.setText(mContext.getString(R.string.The_quantity_inventory, goodInventoryNumber + ""));
                            //已售份数
                            tvHaveSold.setText(mContext.getString(R.string.have_sold, goodsDetailsMessage.getSales() + ""));
                        } else if (currentTime < startTime) {
                            //活动即将开始，显示距离开始的倒计时
                            tvActivityName.setVisibility(View.GONE); //活动名称
                            llActivityPriceAndTitle.setVisibility(View.GONE); //原价
                            tvActivityCountDown.setText(mContext.getString(R.string.Distance_start_time, goodActivityBean.getSell_title()));
                            tvNowPrice.setText(mContext.getString(R.string.the_price, goodsDetailsMessage.getPrice() + ""));
                            tvMemberPrice.setText(mContext.getString(R.string.the_price, goodsDetailsMessage.getMember_price() + ""));
                            time = startTime - currentTime;
                            tvDistanceEndTime.start(time * 1000);
                            goodInventoryNumber = goodsDetailsMessage.getStock(); //商品的库存
                            tvTheQuantityInventory.setText(mContext.getString(R.string.The_quantity_inventory, goodInventoryNumber + ""));
                            //已售份数
                            tvHaveSold.setText(mContext.getString(R.string.have_sold, goodsDetailsMessage.getSales() + ""));
                        } else if (currentTime > startTime && currentTime < endTime) {
                            //活动即将结束，显示距离结束的倒计时--》活动中
                            tvActivityName.setVisibility(View.VISIBLE);
                            llActivityPriceAndTitle.setVisibility(View.VISIBLE); //原价
                            tvActivityName.setText(goodActivityBean.getSell_title());
                            tvNowPrice.setText(mContext.getString(R.string.the_price, goodActivityBean.getPrice() + ""));
                            tvTheOriginalPrice.setText(mContext.getString(R.string.the_price, goodsDetailsMessage.getPrice() + ""));
                            tvTheOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            tvMemberPrice.setText(mContext.getString(R.string.the_price, goodActivityBean.getMember_price() + ""));
                            tvActivityCountDown.setText(mContext.getString(R.string.Distance_end_time, goodActivityBean.getSell_title()));
                            time = endTime - currentTime;
                            tvDistanceEndTime.start(time * 1000);
                            goodInventoryNumber = goodActivityBean.getStock(); //商品的库存
                            tvTheQuantityInventory.setText(mContext.getString(R.string.The_quantity_inventory, goodInventoryNumber + ""));
                            //已售份数
                            tvHaveSold.setText(mContext.getString(R.string.have_sold, goodActivityBean.getSales() + ""));
                        }
                    } else {
                        // 库存为0，恢复原价（相当于活动结束了）--》展示非活动商品的数据
                        llHaveActivityPrice.setVisibility(View.GONE);
                        llHaveNoActivityPrice.setVisibility(View.VISIBLE);
                        rlSalesPromotion.setVisibility(View.GONE);
                        rlSalesPromotionDivideLine.setVisibility(View.GONE);
                        tvNoActivityGoodPrice.setText(mContext.getString(R.string.the_price, goodsDetailsMessage.getPrice() + ""));
                        tvMemberPrice.setText(mContext.getString(R.string.the_price, goodsDetailsMessage.getMember_price() + ""));
                        goodInventoryNumber = goodsDetailsMessage.getStock(); //商品的库存
                        tvTheQuantityInventory.setText(mContext.getString(R.string.The_quantity_inventory, goodInventoryNumber + ""));
                        //已售份数
                        tvHaveSold.setText(mContext.getString(R.string.have_sold, goodsDetailsMessage.getSales() + ""));
                    }
                }
            } else {
                //无活动
                llHaveActivityPrice.setVisibility(View.GONE);
                llHaveNoActivityPrice.setVisibility(View.VISIBLE);
                rlSalesPromotion.setVisibility(View.GONE);
                rlSalesPromotionDivideLine.setVisibility(View.GONE);
                tvNoActivityGoodPrice.setText(mContext.getString(R.string.the_price, goodsDetailsMessage.getPrice() + ""));
                tvMemberPrice.setText(mContext.getString(R.string.the_price, goodsDetailsMessage.getMember_price() + ""));
                goodInventoryNumber = goodsDetailsMessage.getStock(); //商品的库存
                tvTheQuantityInventory.setText(mContext.getString(R.string.The_quantity_inventory, goodInventoryNumber + ""));
                //已售份数
                tvHaveSold.setText(mContext.getString(R.string.have_sold, goodsDetailsMessage.getSales() + ""));
            }
        }
    }

    /**
     * 商品详情
     */
    private void setGoodDetailsDate(
            String brand,  //品牌
            String shelf_life, //	保质期
            String origin,  //产地
            String specification, //规格
            String The_service_information,//快递信息
            String Storage_conditions,//服务信息
            String storage, // 口味
            String taste //	 储存条件
    ) {
        goodsDetailsMessages.add(new DetailsDescriptionList(mContext.getString(R.string.brand_name), brand));
        goodsDetailsMessages.add(new DetailsDescriptionList(mContext.getString(R.string.Shelf_life), shelf_life));
        goodsDetailsMessages.add(new DetailsDescriptionList(mContext.getString(R.string.origin_name), origin));
        goodsDetailsMessages.add(new DetailsDescriptionList(mContext.getString(R.string.specifications), specification));
        goodsDetailsMessages.add(new DetailsDescriptionList(mContext.getString(R.string.Delivery_information), The_service_information));
        goodsDetailsMessages.add(new DetailsDescriptionList(mContext.getString(R.string.The_service_information), Storage_conditions));
        goodsDetailsMessages.add(new DetailsDescriptionList(mContext.getString(R.string.taste), storage));
        goodsDetailsMessages.add(new DetailsDescriptionList(mContext.getString(R.string.Storage_conditions), taste));
        productDetailsMessageAdapter = new ProductDetailsMessageAdapter(goodsDetailsMessages);
        productDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        productDetailsRecyclerView.setHasFixedSize(true);
        productDetailsRecyclerView.setNestedScrollingEnabled(false);
        productDetailsRecyclerView.setAdapter(productDetailsMessageAdapter);

    }

    /**
     * @param deliveryAddress 发货地址
     * @param shippingAddress 收货地址
     */
    private void setShippingAddress(String deliveryAddress, String shippingAddress) {
        tvTheDeliveryAddress.setText(deliveryAddress);
        tvShippingAddress.setText(shippingAddress);
    }

    private void goodIsCollection() {
        if (thatGoodIsCollect) {
            icDetailsNbFollow.setImageResource(R.drawable.ic_details_nb_follow_p);
        } else {
            icDetailsNbFollow.setImageResource(R.drawable.ic_details_nb_follow);
        }
    }

    @Override
    public void loadHomeGoodDetailsMessageFailed(String message) {
        //获取详情失败，加载失败布局
        rlEmptyLayout.setVisibility(View.VISIBLE);
        smartRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        tvDistanceEndTime.stop();
//        locationMapUtils.stopLocation();
//        locationMapUtils.destroyLocation();
        super.onDestroy();
    }

    @Override
    public void collectTheGoodSuccess(String message) {

    }

    @Override
    public void addGoodToShopCarSuccess(String message) {

    }

    @Override
    public void cancelCollectTheGoodSuccess(String message) {
        showToast(message);
        icDetailsNbFollow.setImageResource(R.drawable.ic_details_nb_follow);
    }

    @Override
    public void thatGoodIsCollect(Boolean isCollect) {
        thatGoodIsCollect = isCollect;
        goodIsCollection();
    }

    @Override
    public void checkUserTokenIsOverdue(UserInfo userToken) {

    }

    @Override
    public void getGoodShareUrlAddressSuccess(String url) {
        if (!TextUtils.isEmpty(url)) {
            this.goodShareUrl = url;
        }
    }

    @Override
    public void formIpLoadCityBeanSuccess(String locationBean) {
        if (!TextUtils.isEmpty(locationBean)) {
            setShippingAddress(defaultDeliveryAddress, locationBean);
        }
    }

    @Override
    public void formIpLoadCityBeanFailed(String error) {
//        List<LocationBean> locationList = DaoManagerUtils.queryAllLocationList();
//        if (locationList != null && locationList.size() > 0) {
//            String provinceName = locationList.get(locationList.size() - 1).getProvince();
//            String cityName = locationList.get(locationList.size() - 1).getCity();
//            String areaName = locationList.get(locationList.size() - 1).getArea();
//            if (!TextUtils.isEmpty(cityName)) {
//                defaultShippingAddress = provinceName + cityName + areaName;
//                setShippingAddress(defaultDeliveryAddress, defaultShippingAddress);//收货配送地址
//            }
//        }
    }

    @Override
    public void getTheTotalNumberOfShopCar(int number) {
        if (number > 0) {
            tvShopCarNumber.setVisibility(View.VISIBLE);
            tvShopCarNumber.setText(String.valueOf(number));
        } else {
            tvShopCarNumber.setVisibility(View.GONE);
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
