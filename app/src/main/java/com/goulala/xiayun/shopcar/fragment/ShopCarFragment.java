package com.goulala.xiayun.shopcar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.activity.LoginActivity;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpFragment;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.db.DaoManagerUtils;
import com.goulala.xiayun.common.db.TouristsGoodList;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.ButtonClickUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.view.TitleBuilder;
import com.goulala.xiayun.common.widget.DividerGridItemDecoration;
import com.goulala.xiayun.home.activity.HomeGoodsDetailsActivity;
import com.goulala.xiayun.home.adapter.HomeFootGoodsAdapter;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.home.model.GoodMessage;
import com.goulala.xiayun.shopcar.activity.MakeSureTheOrderActivity;
import com.goulala.xiayun.shopcar.adapter.ShopCarActivityOfStoreOfGoodAdapter;
import com.goulala.xiayun.shopcar.callback.ShopCarGoodNumberListener;
import com.goulala.xiayun.shopcar.model.GoodItemList;
import com.goulala.xiayun.shopcar.model.OrderMessage;
import com.goulala.xiayun.shopcar.model.ShopCarBaseDate;
import com.goulala.xiayun.shopcar.presenter.ShopCarPresenter;
import com.goulala.xiayun.shopcar.view.IShopCarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author      : Z_B
 * date       : 2019/1/17
 * function  :
 */
public class ShopCarFragment extends BaseMvpFragment<ShopCarPresenter> implements IShopCarView,
        ShopCarActivityOfStoreOfGoodAdapter.OnAdapterRefreshListener,
        BaseQuickAdapter.OnItemClickListener,
        ShopCarGoodNumberListener {

    private RecyclerView smartRecyclerView;
    private ImageView ivHint;
    private TextView tvHint;
    private RelativeLayout rlEmptyLayout;
    private TextView tvShowTitle;
    private RecyclerView GuessYouLikeRecyclerView;
    private LinearLayout llGuessYouLikeRootLayout;
    private SmartRefreshLayout refreshLayout;
    private CheckBox cbSelect;
    private LinearLayout llAllSelect;
    private TextView tvCombinedMoney;
    private LinearLayout llShopCarTotalPriceLayout;
    private TextView tvToSettleAccounts;
    private LinearLayout llShopCarBottomLayout;
    private LinearLayout llShopCarRootLayout;
    private NestedScrollView nestedScrollView;
    private TitleBuilder shopCarTitle;
    //购物车商品的adapter
    private ShopCarActivityOfStoreOfGoodAdapter shopCarGoodAdapter;
    //猜你喜欢的adapter
    private HomeFootGoodsAdapter shopCarGuessLikeGoodAdapter;
    //购物车的商品是否全部选中
    private boolean isShopCarGoodAllSelect;
    //购物车的数据集合
    private List<ShopCarBaseDate> shopCarAllGoodMessageList = new ArrayList<>();
    private List<ShopCarBaseDate> goodMessageTotalList = new ArrayList<>();//自定义的商品数据集合--》cell==3，自己手动添加汇总
    //猜你喜欢的数据
    private List<GoodItemMessage> guessYouLikeList = new ArrayList<>();
    private int shopCarBottomLayoutStatus = SHOP_CAR_STATUS_TO_SETTLE_ACCOUNT; //默认去结算
    private static final int SHOP_CAR_STATUS_TO_SETTLE_ACCOUNT = 1; // 去结算商品
    private static final int SHOP_CAR_STATUS_OF_DELETE_THE_GOOD = 2;//删除商品
    private static final int STATUS_OF_DELETE_ALL_THE_GOOD_OF_SHOP_CAR = 3;//删除了全部商品
    private String itemCardIds; //商品的购物车id
    //需要去结算的商品的信息集合
    private ArrayList<GoodItemList> totalGoodItemLists = new ArrayList<>();


    public static ShopCarFragment newInstance() {
        ShopCarFragment shopCarFragment = new ShopCarFragment();
        Bundle bundle = new Bundle();
        shopCarFragment.setArguments(bundle);
        return shopCarFragment;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_shopcarfragment_layout;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.home_fake_status_bar));
        smartRecyclerView = get(R.id.smart_RecyclerView);
        ivHint = get(R.id.ivHint);
        tvHint = get(R.id.tvHint);
        rlEmptyLayout = get(R.id.rl_empty_layout);
        tvShowTitle = get(R.id.tv_show_title);
        GuessYouLikeRecyclerView = get(R.id.Guess_you_like_RecyclerView);
        llGuessYouLikeRootLayout = get(R.id.ll_Guess_you_like_root_layout);
        refreshLayout = get(R.id.refreshLayout);
        cbSelect = get(R.id.cb_select);
        cbSelect.setOnClickListener(this);
        llAllSelect = get(R.id.ll_all_select);
        tvCombinedMoney = get(R.id.tv_combined_money);
        llShopCarTotalPriceLayout = get(R.id.ll_shop_car_total_price_layout);
        tvToSettleAccounts = get(R.id.tv_To_settle_accounts);
        tvToSettleAccounts.setOnClickListener(this);
        llShopCarBottomLayout = get(R.id.ll_shop_car_bottom_layout);
        llShopCarRootLayout = get(R.id.ll_shop_car_root_layout);
        nestedScrollView = get(R.id.nestedScrollView);
        tvShowTitle.setText(mContext.getString(R.string.Guess_you_like));
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableLoadMore(false);
        tvHint.setText(mContext.getString(R.string.Shopping_bags_are_empty));
        shopCarTitle = initTitle(mContext.getString(R.string.shop_car_bag))
                .noBack()
                .setRightText(mContext.getString(R.string.The_editor))
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRightTopEditorView();
                    }
                });


    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        shopCarGoodAdapter = new ShopCarActivityOfStoreOfGoodAdapter(shopCarAllGoodMessageList);
        shopCarGoodAdapter.setShopCarGoodNumberListener(this);
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        smartRecyclerView.setAdapter(shopCarGoodAdapter);
        smartRecyclerView.setNestedScrollingEnabled(false);
        smartRecyclerView.setHasFixedSize(true);

        shopCarGuessLikeGoodAdapter = new HomeFootGoodsAdapter(guessYouLikeList, HomeFootGoodsAdapter.SHOP_CAR_GUESS_YOU_LIKE_ADAPTER_TYPE);
        GuessYouLikeRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        GuessYouLikeRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, mContext.getResources().getColor(R.color.white), 4));
        GuessYouLikeRecyclerView.setAdapter(shopCarGuessLikeGoodAdapter);
        GuessYouLikeRecyclerView.setNestedScrollingEnabled(false);
        GuessYouLikeRecyclerView.setHasFixedSize(true);

        shopCarGuessLikeGoodAdapter.setOnItemClickListener(this);
        shopCarGoodAdapter.setOnAdapterRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDate();
    }

    private void getDate() {
        if (shopCarAllGoodMessageList.size() > 0) shopCarAllGoodMessageList.clear();
        if (goodMessageTotalList.size() > 0) goodMessageTotalList.clear();
        if (guessYouLikeList.size() > 0) guessYouLikeList.clear();
        if (totalGoodItemLists.size() > 0) totalGoodItemLists.clear();
        // 购物车数据
        getShopCarDateList();
        //猜你喜欢
        GuessYouLikeData();
    }

    /**
     * 获取购物车的数据
     */
    private void getShopCarDateList() {
        if (TextUtils.isEmpty(userToken)) {
            //没有token，表示没有登录，查询本地的数据传给服务器
            List<TouristsGoodList> touristsGoodLists = DaoManagerUtils.queryAll();
            if (touristsGoodLists != null && touristsGoodLists.size() > 0) {
                String touristsGoodListsJson = JsonUtils.toJson(touristsGoodLists);
                Map<String, String> touristsGoodParam = new HashMap<>();
                touristsGoodParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.THE_TOURIST_SHOP_LIST);
                touristsGoodParam.put(ApiParam.CART_KEY, touristsGoodListsJson);
                touristsGoodParam.put(ApiParam.DEVICE_KEY, ApiParam.DEVICE_VALUE);
                String touristsGoodParamJson = JsonUtils.toJson(touristsGoodParam);
                mvpPresenter.getShopCarGoodList(IShopCarView.THE_TYPE_OF_GET_TOURIST_GOOD_LIST, "", touristsGoodParamJson);
            }
        } else {
            //有token，表示登录了，获取服务器的数据
            Map<String, String> shopCarParam = new HashMap<>();
            shopCarParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_CHOP_CAR_DATE_LIST_VALUE);
            shopCarParam.put(ApiParam.DEVICE_KEY, ApiParam.DEVICE_VALUE);
            String shopCarParamJson = JsonUtils.toJson(shopCarParam);
            mvpPresenter.getShopCarGoodList(IShopCarView.THE_TYPE_OF_GET_SHOP_CAR_GOOD_LIST, userToken, shopCarParamJson);
        }
    }

    /**
     * 猜你喜欢的数据
     */
    private void GuessYouLikeData() {
        Map<String, String> guessLikeParam = new HashMap<>();
        guessLikeParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GUESS_YOU_LIKE_URL);
        String guessYouLikeJson = JsonUtils.toJson(guessLikeParam);
        mvpPresenter.getGuessYouLikeGoodList(userToken, guessYouLikeJson);
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.cb_select:
                //全选，当勾选按钮的时候，整个购物车的商品全部被选中
                if (cbSelect.isChecked()) {
                    //全部选中
                    isShopCarGoodAllSelect = !isShopCarGoodAllSelect;
                    for (int i = 0; i < shopCarAllGoodMessageList.size(); i++) {
                        shopCarAllGoodMessageList.get(i).setGoodSelect(true);
                    }
                } else {
                    //全部不选
                    isShopCarGoodAllSelect = !isShopCarGoodAllSelect;
                    for (int i = 0; i < shopCarAllGoodMessageList.size(); i++) {
                        shopCarAllGoodMessageList.get(i).setGoodSelect(false);
                    }
                    tvCombinedMoney.setText("0");
                    totalGoodItemLists.clear();
                }
                shopCarGoodAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_To_settle_accounts:
                if (TextUtils.isEmpty(userToken)) {
                    LoginActivity.start(mContext);
                    return;
                }
                switch (shopCarBottomLayoutStatus) {
                    case SHOP_CAR_STATUS_TO_SETTLE_ACCOUNT: //去结算
                        for (int i = 0; i < shopCarAllGoodMessageList.size(); i++) {
                            if (shopCarAllGoodMessageList.get(i).getCell() == 3 && shopCarAllGoodMessageList.get(i).isGoodSelect()) {//商品
                                int merchant_id = shopCarAllGoodMessageList.get(i).getMerchant_id(); //店铺id
                                int item_id = shopCarAllGoodMessageList.get(i).getId();  //商品id
                                int item_num = shopCarAllGoodMessageList.get(i).getItem_num(); //商品数量
                                int cart_id = shopCarAllGoodMessageList.get(i).getCart_id(); //购物车的id
                                totalGoodItemLists.add(new GoodItemList(item_id, item_num, merchant_id, cart_id));
                            }
                        }
                        if (totalGoodItemLists.size() < 1) {
                            showToast(mContext.getString(R.string.Please_select_the_commodity_to_be_settled));
                            return;
                        }
                        MakeSureTheOrderActivity.start(mContext, totalGoodItemLists, "", "");
                        totalGoodItemLists.clear();
                        break;
                    case SHOP_CAR_STATUS_OF_DELETE_THE_GOOD://删除商品
                        for (int i = 0; i < shopCarAllGoodMessageList.size(); i++) {
                            if (shopCarAllGoodMessageList.get(i).getCell() == 3 && shopCarAllGoodMessageList.get(i).isGoodSelect()) {//商品
                                int merchant_id = shopCarAllGoodMessageList.get(i).getMerchant_id(); //店铺id
                                int item_id = shopCarAllGoodMessageList.get(i).getId();  //商品id
                                int item_num = shopCarAllGoodMessageList.get(i).getItem_num(); //商品数量
                                int cart_id = shopCarAllGoodMessageList.get(i).getCart_id(); //购物车的id
                                totalGoodItemLists.add(new GoodItemList(item_id, item_num, merchant_id, cart_id));
                            }
                        }
                        if (totalGoodItemLists.size() < 1) {
                            showToast(mContext.getString(R.string.Please_select_the_item_to_delete));
                            return;
                        }
                        shoDeleteDialogOfShopCar();
                        break;
                    default:

                        break;
                }
                break;
        }


    }

    /**
     * 删除商品的对话框
     */
    private void shoDeleteDialogOfShopCar() {
        String dialogTitle = mContext.getString(R.string.Are_you_sure_about_deleting);
        AlertDialogUtils.showCustomerDialog(mContext, dialogTitle, new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {

            }

            @Override
            public void determineClick(View view) {
                if (totalGoodItemLists.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < totalGoodItemLists.size(); i++) {
                        int goodId = totalGoodItemLists.get(i).getCart_id();
                        stringBuffer.append(goodId + ",");
                    }
                    if (stringBuffer.length() > 1) {//需要删除的商品的购物车id
                        itemCardIds = stringBuffer.substring(0, stringBuffer.length() - 1);
                    }
                }
                Map<String, String> deleteShopCarGoodParam = new HashMap<>();
                deleteShopCarGoodParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.DELETE_GOOD_ITEM_FORM_YOU_SHOP_CAR);
                deleteShopCarGoodParam.put(ApiParam.CART_IDS, itemCardIds);
                String deleteShopCarGoodParamJson = JsonUtils.toJson(deleteShopCarGoodParam);
                if (TextUtils.isEmpty(userToken)) return;
                mvpPresenter.addOrDeleteThatGoodFromShopCar(IShopCarView.THE_TYPE_OF_DELETE_ITEM_FROM_SHOP_CAR, userToken, deleteShopCarGoodParamJson);
                if (totalGoodItemLists.size() == goodMessageTotalList.size()) { //全部删除了
                    shopCarBottomLayoutStatus = STATUS_OF_DELETE_ALL_THE_GOOD_OF_SHOP_CAR;
                }
                totalGoodItemLists.clear();

            }
        });

    }


    /**
     * 右上角编辑按钮状态的改变
     */
    private void setRightTopEditorView() {
        switch (shopCarBottomLayoutStatus) {
            case SHOP_CAR_STATUS_TO_SETTLE_ACCOUNT: //结算
                shopCarTitle.setRightText(mContext.getResources().getString(R.string.complete));
                llShopCarTotalPriceLayout.setVisibility(View.GONE);
                tvToSettleAccounts.setText(mContext.getString(R.string.Delete_the_goods));
                tvToSettleAccounts.setBackgroundColor(mContext.getResources().getColor(R.color.color_f3f3f3));
                llGuessYouLikeRootLayout.setVisibility(View.GONE);
                shopCarBottomLayoutStatus = SHOP_CAR_STATUS_OF_DELETE_THE_GOOD;
                break;

            case SHOP_CAR_STATUS_OF_DELETE_THE_GOOD://删除
                shopCarTitle.setRightText(mContext.getResources().getString(R.string.The_editor));
                llShopCarTotalPriceLayout.setVisibility(View.VISIBLE);
                tvToSettleAccounts.setText(mContext.getString(R.string.To_settle_accounts));
                tvToSettleAccounts.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
                llGuessYouLikeRootLayout.setVisibility(View.VISIBLE);
                shopCarBottomLayoutStatus = SHOP_CAR_STATUS_TO_SETTLE_ACCOUNT;
                break;

            default:
                break;

        }

    }

    @Override
    protected ShopCarPresenter createPresenter() {
        return new ShopCarPresenter(this);
    }

    @Override
    public void getShopCarGoodListSuccess(List<ShopCarBaseDate> shopCarMessageList) {
        setDateToShopCarView(shopCarMessageList);
    }


    @Override
    public void getTouristShopGoodListSuccess(List<ShopCarBaseDate> touristShopGood) {
        setDateToShopCarView(touristShopGood);
    }

    /**
     * 显示购物带的布局
     *
     * @param shopCarMessageList
     */
    private void setDateToShopCarView(List<ShopCarBaseDate> shopCarMessageList) {
        if (shopCarMessageList != null && shopCarMessageList.size() > 0) {
            this.shopCarAllGoodMessageList = shopCarMessageList;
            smartRecyclerView.setVisibility(View.VISIBLE);
            rlEmptyLayout.setVisibility(View.GONE);
            shopCarGoodAdapter.setNewData(shopCarMessageList);
            shopCarTitle.setRightText(mContext.getString(R.string.The_editor));
            for (int i = 0; i < shopCarAllGoodMessageList.size(); i++) {
                if (3 == shopCarAllGoodMessageList.get(i).getCell()) {
                    goodMessageTotalList.add(shopCarAllGoodMessageList.get(i));
                }
            }
        } else {
            smartRecyclerView.setVisibility(View.GONE);
            rlEmptyLayout.setVisibility(View.VISIBLE);
            tvHint.setText(mContext.getString(R.string.Shopping_bags_are_empty));
            cbSelect.setChecked(false);
            switch (shopCarBottomLayoutStatus) {
                case SHOP_CAR_STATUS_TO_SETTLE_ACCOUNT:
                    shopCarTitle.setRightText("");
                    break;
                case SHOP_CAR_STATUS_OF_DELETE_THE_GOOD:

                    break;
                case STATUS_OF_DELETE_ALL_THE_GOOD_OF_SHOP_CAR: //全部删除
                    shopCarTitle.setRightText("");
                    llShopCarTotalPriceLayout.setVisibility(View.VISIBLE);
                    tvToSettleAccounts.setText(mContext.getString(R.string.To_settle_accounts));
                    tvToSettleAccounts.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
                    llGuessYouLikeRootLayout.setVisibility(View.VISIBLE);
                    shopCarBottomLayoutStatus = SHOP_CAR_STATUS_TO_SETTLE_ACCOUNT;
                    break;
            }
        }
        shopCarGoodAdapter.notifyDataSetChanged();
    }

    @Override
    public void getGuessYouLikeGoodListSuccess(List<GoodItemMessage> goodMessage) {
        if (goodMessage != null && !goodMessage.isEmpty()) {
            this.guessYouLikeList = goodMessage;
            shopCarGuessLikeGoodAdapter.setNewData(guessYouLikeList);
        } else {
            EmptyViewUtils.bindEmptyView(mContext, shopCarGuessLikeGoodAdapter);
        }
    }

    @Override
    public void addGoodItemsToYouShopCarSuccess(String message) {
        showToast(message);
        getDate();
    }

    @Override
    public void reduceGoodItemFormYouShopCar(String message) {
        showToast(message);
        getDate();
    }

    @Override
    public void deleteThatGoodFromShopCarSuccess(String message) {
        showToast(message);
        getDate();
    }

    @Override
    public void countsTheTotalGoodItemsOfYouShopCar(GoodMessage goodMessage) {

    }

    @Override
    public void getShopCarTotalPriceSuccess(OrderMessage orderMessage) {
        if (orderMessage != null) {
            double totalMoney = orderMessage.getPay_price() - orderMessage.getTotal_freight();
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            tvCombinedMoney.setText(nf.format(totalMoney));
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        int goodId = shopCarGuessLikeGoodAdapter.getItem(position).getId();
        HomeGoodsDetailsActivity.start(mContext, goodId, ConstantValue.THE_TYPE_OF_SHOP_CAR);
    }

    @Override
    public void onRefresh(boolean isSelect) {
        switch (shopCarBottomLayoutStatus) {
            case SHOP_CAR_STATUS_TO_SETTLE_ACCOUNT:   //去结算
                if (isSelect) {
                    cbSelect.setChecked(true);
                } else {
                    cbSelect.setChecked(false);
                }
                List<GoodItemList> shopCarPriceList = new ArrayList<>();
                for (int i = 0; i < shopCarAllGoodMessageList.size(); i++) {
                    if (shopCarAllGoodMessageList.get(i).isGoodSelect()) {
                        if (shopCarAllGoodMessageList.get(i).getCell() == 3) { //3是商品
                            int merchant_id = shopCarAllGoodMessageList.get(i).getMerchant_id(); //店铺id
                            int item_id = shopCarAllGoodMessageList.get(i).getId();  //商品id
                            int item_num = shopCarAllGoodMessageList.get(i).getItem_num(); //商品数量
                            int cart_id = shopCarAllGoodMessageList.get(i).getCart_id(); //购物车的id
                            shopCarPriceList.add(new GoodItemList(item_id, item_num, merchant_id, cart_id));
                        }
                    } else {
                        tvCombinedMoney.setText("0");
                    }
                }
                if (!ButtonClickUtils.isFastClick()) {
                    calculateTotalPrice(shopCarPriceList);
                }
                break;
            case SHOP_CAR_STATUS_OF_DELETE_THE_GOOD:  //删除
                if (isSelect) {
                    cbSelect.setChecked(true);
                    tvToSettleAccounts.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
                } else {
                    cbSelect.setChecked(false);
                    tvToSettleAccounts.setBackgroundColor(mContext.getResources().getColor(R.color.color_f3f3f3));
                }
                for (int i = 0; i < shopCarAllGoodMessageList.size(); i++) {
                    if (shopCarAllGoodMessageList.get(i).isGoodSelect()) {
                        if (shopCarAllGoodMessageList.get(i).getCell() == 3) {
                            if (shopCarAllGoodMessageList.get(i).isGoodSelect()) {
                                tvToSettleAccounts.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
                            } else {
                                tvToSettleAccounts.setBackgroundColor(mContext.getResources().getColor(R.color.color_f3f3f3));
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 计算勾选商品的价格
     */
    private void calculateTotalPrice(List<GoodItemList> goodItemListArrayList) {
        if (goodItemListArrayList.size() > 0) {
            String goodItemInfoJson = JsonUtils.toJson(goodItemListArrayList);
            Map<String, String> orderMessageParam = new HashMap<>();
            orderMessageParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_ORDER_MESSAGE_URL);
            orderMessageParam.put(ApiParam.ITEM_INFO, goodItemInfoJson);
            orderMessageParam.put(ApiParam.SHARE_USER_ID, "");
            orderMessageParam.put(ApiParam.COUPON_ID, "");
            orderMessageParam.put(ApiParam.ADDRESS_ID, "");
            String orderMessageParamJson = JsonUtils.toJson(orderMessageParam);
            mvpPresenter.getShopCarTotalPrice(userToken, orderMessageParamJson);
        }

    }

    @Override
    public void addGoodItemToShopCar(int merchant_id, int item_id, int item_num) {
        if (TextUtils.isEmpty(userToken)) {
            //没有登录，操作本地数据库
        } else {
            //登录了，操作服务器数据
            //数量增加
            addOrReduceGoodItem(IShopCarView.THE_TYPE_OF_ADD_ITEM_TO_SHOP_CAR, merchant_id, item_id, item_num);
        }
    }

    @Override
    public void reduceGoodItemFormShopCar(int merchant_id, int item_id, int item_num) {
        if (TextUtils.isEmpty(userToken)) {
            //没有登录，操作本地数据库
        } else {
            //登录了，操作服务器数据
            //数量减少
            addOrReduceGoodItem(IShopCarView.THE_TYPE_OF_REDUCE_ITEM_FROM_SHOP_CAR, merchant_id, item_id, item_num);
        }
    }

    @Override
    public void onGoodItemClick(int goodId) {
        if (shopCarBottomLayoutStatus == SHOP_CAR_STATUS_TO_SETTLE_ACCOUNT) {
            HomeGoodsDetailsActivity.start(mContext, goodId, ConstantValue.THE_TYPE_OF_SHOP_CAR);
        }
    }

    /**
     * 购物车数量增加或者减少
     */
    private void addOrReduceGoodItem(int requestType, int merchant_id, int item_id, int item_num) {
        Map<String, String> addOrReduceGoodItem = new HashMap<>();
        switch (requestType) {
            case IShopCarView.THE_TYPE_OF_ADD_ITEM_TO_SHOP_CAR:
                //数量增加
                addOrReduceGoodItem.put(ApiParam.BASE_METHOD_KEY, ApiParam.ADD_GOOD_ITEM_TO_YOU_SHOP_CAR);
                break;
            case IShopCarView.THE_TYPE_OF_REDUCE_ITEM_FROM_SHOP_CAR:
                //数量减少
                addOrReduceGoodItem.put(ApiParam.BASE_METHOD_KEY, ApiParam.REDUCE_GOOD_ITEM_FORM_YOU_SHOP_CAR);
                break;
        }
        addOrReduceGoodItem.put(ApiParam.MERCHANT_ID_KEY, String.valueOf(merchant_id));
        addOrReduceGoodItem.put(ApiParam.ITEM_ID_KEY, String.valueOf(item_id));
        addOrReduceGoodItem.put(ApiParam.ITEM_NUMBER_KEY, String.valueOf(item_num));
        String addOrReduceGoodItemParam = JsonUtils.toJson(addOrReduceGoodItem);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.addOrDeleteThatGoodFromShopCar(requestType, userToken, addOrReduceGoodItemParam);
        }
    }
}
