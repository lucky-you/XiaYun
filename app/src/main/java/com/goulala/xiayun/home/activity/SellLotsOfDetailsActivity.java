package com.goulala.xiayun.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.ConstantValue;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.LogUtils;
import com.goulala.xiayun.common.widget.DividerGridItemDecoration;
import com.goulala.xiayun.common.widget.GridSpacingItemDecoration;
import com.goulala.xiayun.common.widget.SmartRefreshLoadPageHelper;
import com.goulala.xiayun.home.adapter.SellLotsOfDetailsAdapter;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.home.model.HomeValueSellingBean;
import com.goulala.xiayun.home.presenter.SellLotsOfDetailsPresenter;
import com.goulala.xiayun.home.view.ISellLotsOfDetailsView;
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
 * 超值热卖详情的activity
 */
public class SellLotsOfDetailsActivity extends BaseMvpActivity<SellLotsOfDetailsPresenter> implements ISellLotsOfDetailsView,
        SmartRefreshLoadPageHelper.DataProvider {

    private ImageView ivDetailsImageView;
    private RecyclerView detailsRecyclerView;
    private NestedScrollView nestedScrollView;
    private ImageView ivHint;
    private TextView tvHint;
    private SmartRefreshLayout smartRefreshLayout;
    private ImageView ivToTop;
    private TextView tvCurrentNumber;
    private TextView tvTotalNumberSize;
    private RelativeLayout rlCurrentItemSize;
    private TextView tvHaveSaveCarNumber;
    private RelativeLayout rlShopCarNumber;
    private LinearLayout llBottomRightLayout;
    private RelativeLayout rlEmptyLayout;
    private SellLotsOfDetailsAdapter sellLotsOfDetailsAdapter;
    private String goodTitle; //活动的标题
    private String activityId;// 活动id
    private List<GoodItemMessage> goodItemMessageList = new ArrayList<>();
    private int classFormType;
    private SmartRefreshLoadPageHelper<GoodItemMessage> pageLimitDelegate = new SmartRefreshLoadPageHelper<>(this);

    public static void start(Context context, String title, String activityId, int classType) {
        Intent intent = new Intent(context, SellLotsOfDetailsActivity.class);
        intent.putExtra(ConstantValue.TITLE, title);
        intent.putExtra(ConstantValue.ACTIVE_ID, activityId);
        intent.putExtra(ConstantValue.INDEX, classType);
        context.startActivity(intent);
    }

    @Override
    protected SellLotsOfDetailsPresenter createPresenter() {
        return new SellLotsOfDetailsPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        goodTitle = getIntent().getStringExtra(ConstantValue.TITLE);
        activityId = getIntent().getStringExtra(ConstantValue.ACTIVE_ID);
        classFormType = getIntent().getIntExtra(ConstantValue.INDEX, -1);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_sell_lots_of_details;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        ivDetailsImageView = get(R.id.iv_details_imageView);
        detailsRecyclerView = get(R.id.details_RecyclerView);
        nestedScrollView = get(R.id.nestedScrollView);
        ivHint = get(R.id.ivHint);
        tvHint = get(R.id.tvHint);
        smartRefreshLayout = get(R.id.smartRefreshLayout);
        ivToTop = get(R.id.iv_to_top);
        tvCurrentNumber = get(R.id.tv_current_number);
        tvTotalNumberSize = get(R.id.tv_total_number_size);
        rlCurrentItemSize = get(R.id.rl_current_item_size);
        tvHaveSaveCarNumber = get(R.id.tv_have_save_car_number);
        rlShopCarNumber = get(R.id.rl_shop_car_number);
        rlShopCarNumber.setOnClickListener(this);
        llBottomRightLayout = get(R.id.ll_bottom_right_Layout);
        rlEmptyLayout = get(R.id.rl_empty_layout);

        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        smartRefreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setEnableLoadMore(false);

        if (!TextUtils.isEmpty(goodTitle)) {
            initTitle(goodTitle);
        }
        getTotalNumberOfShopCar();
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {


        sellLotsOfDetailsAdapter = new SellLotsOfDetailsAdapter(goodItemMessageList, SellLotsOfDetailsAdapter.HIDE_SALE_OFF_TYPE);
        detailsRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        detailsRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, mContext.getResources().getColor(R.color.white), 4));
        detailsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 3, false));
        detailsRecyclerView.setHasFixedSize(true);
        detailsRecyclerView.setNestedScrollingEnabled(false);
        detailsRecyclerView.setAdapter(sellLotsOfDetailsAdapter);
        pageLimitDelegate.attachView(smartRefreshLayout, detailsRecyclerView, sellLotsOfDetailsAdapter);
        EmptyViewUtils.bindEmptyView(mContext, sellLotsOfDetailsAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageLimitDelegate.refreshPage();
                refreshLayout.finishRefresh();
            }
        });
        sellLotsOfDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int goodId = sellLotsOfDetailsAdapter.getItem(position).getId();
                HomeGoodsDetailsActivity.start(mContext, goodId, ConstantValue.THE_TYPE_OF_OTHER);
            }
        });

    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.rl_shop_car_number:
                //购物车
                if (classFormType == ConstantValue.THAT_CLASS_TYPE_OF_SHOP_CAR) {
                    finish();
                } else {
//                    ShopCarActivity.start(mContext);
                }
                break;

        }
    }

    /**
     * 获取购物车中的商品数量
     */
    private void getTotalNumberOfShopCar() {
        Map<String, String> totalParam = new HashMap<>();
        totalParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_THE_TOTAL_GOOD_ITEMS_OF_YOU_SHOP_CAR);
        String totalParamJson = JsonUtils.toJson(totalParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getTheTotalNumberOfShopCar(userToken, totalParamJson);
        }
    }

    @Override
    public void loadMorePageDate(int page) {
        getActivityDateList(page);
    }

    /**
     * 获取活动的数据
     *
     * @param currentPage 当前页数
     */
    private void getActivityDateList(int currentPage) {
        Map<String, String> activityParam = new HashMap<>();
        activityParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.HOME_VALUE_SEAL_GOOD_LIST);
        activityParam.put(ApiParam.ACTIVE_ID, activityId);
        activityParam.put(ApiParam.PAGE_KEY, String.valueOf(currentPage));
        activityParam.put(ApiParam.SIZE_KEY, ApiParam.SIZE_NUMBER_VALUE);
        String activityParamJson = JsonUtils.toJson(activityParam);
        LogUtils.showLog(userToken, activityParamJson);
        mvpPresenter.getActivityDateList(mContext, userToken, activityParamJson);
    }

    @Override
    public void getActivityDateList(List<HomeValueSellingBean> activityDateList) {
        if (activityDateList != null && !activityDateList.isEmpty()) {
            rlEmptyLayout.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.VISIBLE);
            llBottomRightLayout.setVisibility(View.VISIBLE);
            ImageLoaderUtils.displayBannerImage(activityDateList.get(0).getSmallimage(), ivDetailsImageView);
            goodItemMessageList = activityDateList.get(0).getData();
            if (goodItemMessageList.size() > 0) {
                tvTotalNumberSize.setText(goodItemMessageList.size() + "");
//                sellLotsOfDetailsAdapter.setNewData(goodItemMessageList);
                pageLimitDelegate.setData(goodItemMessageList);
            }
        } else {
            rlEmptyLayout.setVisibility(View.VISIBLE);
            nestedScrollView.setVisibility(View.GONE);
            llBottomRightLayout.setVisibility(View.GONE);
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
    public void onNewWorkException(String message) {
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
    }
}
