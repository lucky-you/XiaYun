package com.goulala.xiayun.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.InputMethodKeyBroadUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.KeyboardUtils;
import com.goulala.xiayun.common.utils.LogUtils;
import com.goulala.xiayun.common.widget.DividerGridItemDecoration;
import com.goulala.xiayun.common.widget.SmartRefreshLoadPageHelper;
import com.goulala.xiayun.home.adapter.SellLotsOfDetailsAdapter;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.home.model.GoodMessage;
import com.goulala.xiayun.home.presenter.SearchResultPresenter;
import com.goulala.xiayun.home.view.ISearchResultView;
import com.goulala.xiayun.shopcar.activity.ShopCarActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索结果
 */
public class SearchResultActivity extends BaseMvpActivity<SearchResultPresenter> implements ISearchResultView,
        SmartRefreshLoadPageHelper.DataProvider {

    private EditText editKey;
    private LinearLayout llHomeSearch;
    private TextView tvCancel;
    private TextView tvTheDefault;
    private TextView tvSalesNumber;
    private TextView tvThePrice;
    private ImageView ivPriceImageView;
    private LinearLayout llThePrice;
    private RelativeLayout rlThePrice;
    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private ImageView ivToTop;
    private TextView tvCurrentNumber;
    private TextView tvTotalNumberSize;
    private RelativeLayout rlCurrentItemSize;
    private TextView tvHaveSaveCarNumber;
    private RelativeLayout rlShopCarNumber;
    private LinearLayout llBottomRightLayout;
    private SellLotsOfDetailsAdapter sellLotsOfDetailsAdapter;
    private String keyWords, storeKey;
    private List<GoodItemMessage> goodItemMessageList = new ArrayList<>();
    private boolean priceIsDesc;  //价格排序
    private SmartRefreshLoadPageHelper<GoodItemMessage> smartRefreshLoadPageHelper = new SmartRefreshLoadPageHelper<>(this);


    public static void start(Context context, String keyWords) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(ConstantValue.TITLE, keyWords);
        context.startActivity(intent);
    }


    @Override
    public void initData(@Nullable Bundle bundle) {
        keyWords = bundle.getString(ConstantValue.TITLE); //从搜索界面传递过来的关键字
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    public void bindViews(View contentView) {
        get(R.id.rl_back).setOnClickListener(this);
        editKey = get(R.id.edit_key);
        llHomeSearch = get(R.id.ll_home_search);
        llHomeSearch.setOnClickListener(this);
        tvCancel = get(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
        tvTheDefault = get(R.id.tv_The_default);
        tvTheDefault.setOnClickListener(this);
        tvSalesNumber = get(R.id.tv_sales_number);
        tvSalesNumber.setOnClickListener(this);
        tvThePrice = get(R.id.tv_the_price);
        ivPriceImageView = get(R.id.iv_price_imageView);
        llThePrice = get(R.id.ll_the_price);
        llThePrice.setOnClickListener(this);
        rlThePrice = get(R.id.rl_the_price);
        rlThePrice.setOnClickListener(this);
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        ivToTop = get(R.id.iv_to_top);
        tvCurrentNumber = get(R.id.tv_current_number);
        tvTotalNumberSize = get(R.id.tv_total_number_size);
        rlCurrentItemSize = get(R.id.rl_current_item_size);
        tvHaveSaveCarNumber = get(R.id.tv_have_save_car_number);
        rlShopCarNumber = get(R.id.rl_shop_car_number);
        rlShopCarNumber.setOnClickListener(this);
        llBottomRightLayout = get(R.id.ll_bottom_right_Layout);
        KeyboardUtils.hideSoftInput(this);
        editKey.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editKey.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setEnableLoadMore(false);
        getTotalNumberOfShopCar();
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

        storeKey = ApiParam.ID_DESC_KEY;
        if (!TextUtils.isEmpty(keyWords)) {
            editKey.setText(keyWords);
        }
        sellLotsOfDetailsAdapter = new SellLotsOfDetailsAdapter(goodItemMessageList, SellLotsOfDetailsAdapter.SHOW_SALE_OFF_TYPE);
        smartRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        smartRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 7));
        smartRecyclerView.setAdapter(sellLotsOfDetailsAdapter);
        smartRefreshLoadPageHelper.attachView(refreshLayout, smartRecyclerView, sellLotsOfDetailsAdapter);
        EmptyViewUtils.bindEmptyView(mContext, sellLotsOfDetailsAdapter, mContext.getString(R.string.No_search_results));

        //商品详情
        sellLotsOfDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int goodId = sellLotsOfDetailsAdapter.getItem(position).getId();
                HomeGoodsDetailsActivity.start(mContext, goodId, ConstantValue.THE_TYPE_OF_OTHER);
            }
        });
        //搜索键盘
        editKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    editTextSearchListener();
                    return true;
                }
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                storeKey = ApiParam.ID_DESC_KEY;
                smartRefreshLoadPageHelper.refreshPage();
                tvTheDefault.setTextColor(mContext.getResources().getColor(R.color.color_e53d3d));
                tvSalesNumber.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
                tvThePrice.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
                refreshLayout.finishRefresh();
            }
        });

    }


    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.rl_shop_car_number:
                ShopCarActivity.start(mContext);
                break;
        }

    }

    private void getTotalNumberOfShopCar() {
        Map<String, String> totalParam = new HashMap<>();
        totalParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_THE_TOTAL_GOOD_ITEMS_OF_YOU_SHOP_CAR);
        String totalParamJson = JsonUtils.toJson(totalParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getTheTotalNumberOfShopCar(userToken, totalParamJson);
        }
    }

    //搜索键盘和右上角搜索的处理
    private void editTextSearchListener() {
        String keyWord = editKey.getText().toString().trim();
        if (TextUtils.isEmpty(keyWords)) {
            showToast(mContext.getString(R.string.please_edit_key_words));
            return;
        }
        keyWords = keyWord;
        storeKey = ApiParam.ID_DESC_KEY;
        smartRefreshLoadPageHelper.refreshPage();
        insertHistoryDao(keyWord);
        tvTheDefault.setTextColor(mContext.getResources().getColor(R.color.color_e53d3d));
        tvSalesNumber.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
        tvThePrice.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
        InputMethodKeyBroadUtils.hideKeyboard(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_home_search:
                InputMethodKeyBroadUtils.showKeyboard(this, editKey);
                break;
            case R.id.tv_cancel:
                //搜索
                editTextSearchListener();
                break;
            case R.id.tv_The_default:
                storeKey = ApiParam.ID_DESC_KEY;
                smartRefreshLoadPageHelper.refreshPage();
                tvTheDefault.setTextColor(mContext.getResources().getColor(R.color.color_e53d3d));
                tvSalesNumber.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
                tvThePrice.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
                break;
            case R.id.tv_sales_number:
                storeKey = ApiParam.SALES_DESC_KEY;
                smartRefreshLoadPageHelper.refreshPage();
                tvTheDefault.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
                tvSalesNumber.setTextColor(mContext.getResources().getColor(R.color.color_e53d3d));
                tvThePrice.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
                break;
            case R.id.ll_the_price:
            case R.id.rl_the_price:
                tvTheDefault.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
                tvSalesNumber.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
                tvThePrice.setTextColor(mContext.getResources().getColor(R.color.color_e53d3d));
                if (priceIsDesc) {
                    ivPriceImageView.setImageResource(R.drawable.icon_upwards);
                    priceIsDesc = !priceIsDesc;
                    storeKey = ApiParam.PRICE_ASC_KEY;
                    smartRefreshLoadPageHelper.refreshPage();
                } else {
                    ivPriceImageView.setImageResource(R.drawable.icon_down);
                    priceIsDesc = !priceIsDesc;
                    storeKey = ApiParam.PRICE_DESC_KEY;
                    smartRefreshLoadPageHelper.refreshPage();
                }
                break;
        }
    }


    @Override
    public void loadSearchResultList(GoodMessage goodMessage) {
        if (goodMessage != null) {
            goodItemMessageList = goodMessage.getData();
            smartRefreshLoadPageHelper.setData(goodItemMessageList);
//            if (goodItemMessageList.size() < 1) {
//                showToast(mContext.getString(R.string.All_content_is_displayed));
//            }
//            if (goodItemMessageList.size() > 0) {
//                rlCurrentItemSize.setVisibility(View.VISIBLE);
//                tvTotalNumberSize.setText(goodItemMessageList.size() + "");
//            } else {
//                rlCurrentItemSize.setVisibility(View.GONE);
//            }
        }
    }

    @Override
    public void getTheTotalNumberOfShopCar(int number) {
        if (number > 0) {
            tvHaveSaveCarNumber.setVisibility(View.VISIBLE);
            tvHaveSaveCarNumber.setText(number + "");
        } else {
            tvHaveSaveCarNumber.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadMorePageDate(int page) {
        startSearch(keyWords, storeKey, page);
    }

    /**
     * 发送请求
     */
    private void startSearch(String keyWords, String sortText, int mCurrentPage) {
        Map<String, String> searchResultParam = new HashMap<>();
        searchResultParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_SEARCH_RESULT_LIST_VALUE);
        searchResultParam.put(ApiParam.KEYWORD_KEY, keyWords);
        searchResultParam.put(ApiParam.SORT_KEY, sortText);
        searchResultParam.put(ApiParam.PAGE_KEY, String.valueOf(mCurrentPage));
        searchResultParam.put(ApiParam.SIZE_KEY, ApiParam.SIZE_NUMBER_VALUE);
        String searchResultJson = JsonUtils.toJson(searchResultParam);
        LogUtils.showLog(userToken, searchResultJson);
        mvpPresenter.getSearchResultList(userToken, searchResultJson);
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
    protected SearchResultPresenter createPresenter() {
        return new SearchResultPresenter(this);
    }
}
