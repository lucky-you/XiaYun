package com.goulala.xiayun.mycenter.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpFragment;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.common.widget.SmartRefreshLoadPageHelper;
import com.goulala.xiayun.home.activity.HomeGoodsDetailsActivity;
import com.goulala.xiayun.mycenter.adapter.MyCollectionAndFootprintAdapter;
import com.goulala.xiayun.mycenter.adapter.MyCollectionAndFootprintArticleAdapter;
import com.goulala.xiayun.mycenter.model.CollectGoodMessage;
import com.goulala.xiayun.mycenter.model.CollectionGoodList;
import com.goulala.xiayun.mycenter.presenter.MyCollectionOrMyFootprintPresenter;
import com.goulala.xiayun.mycenter.view.IMyCollectionOrMyFootprintView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author      : Z_B
 * date       : 2018/9/27
 * function  : 我的足迹
 */
public class MyFootprintFragment extends BaseMvpFragment<MyCollectionOrMyFootprintPresenter> implements IMyCollectionOrMyFootprintView,
        SmartRefreshLoadPageHelper.DataProvider,
        BaseQuickAdapter.OnItemClickListener {
    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private int tagIndex;
    private List<CollectionGoodList> collectionGoodLists = new ArrayList<>();
    private MyCollectionAndFootprintAdapter myCollectionAndFootprintAdapter;
    private MyCollectionAndFootprintArticleAdapter myCollectionAndFootprintArticleAdapter;
    private SmartRefreshLoadPageHelper<CollectionGoodList> smartRefreshLoadPageHelper = new SmartRefreshLoadPageHelper(this);

    public static MyFootprintFragment newInstance(int tagIndex) {
        MyFootprintFragment myCollectionAndFootprintFragment = new MyFootprintFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.INDEX, tagIndex);
        myCollectionAndFootprintFragment.setArguments(bundle);
        return myCollectionAndFootprintFragment;
    }


    @Override
    protected MyCollectionOrMyFootprintPresenter createPresenter() {
        return new MyCollectionOrMyFootprintPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_smartrefresh_layout;
    }

    @Override
    public void bindViews(View contentView) {
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        registerEvent();
        tagIndex = getArguments().getInt(ConstantValue.INDEX);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

        switch (tagIndex) {
            case 0://商品
                myCollectionAndFootprintAdapter = new MyCollectionAndFootprintAdapter(collectionGoodLists, ConstantValue.THE_CLASS_OF_MY_FOOTPRINT_TYPE);
                smartRecyclerView.setAdapter(myCollectionAndFootprintAdapter);
                smartRefreshLoadPageHelper.attachView(refreshLayout, smartRecyclerView, myCollectionAndFootprintAdapter);
                EmptyViewUtils.bindEmptyView(mContext, myCollectionAndFootprintAdapter, mContext.getString(R.string.No_tracks_of_mine));
                break;
            case 1: //虾记
                refreshLayout.setEnableLoadMore(false);
                myCollectionAndFootprintArticleAdapter = new MyCollectionAndFootprintArticleAdapter(null, ConstantValue.THE_CLASS_OF_MY_FOOTPRINT_TYPE);
                smartRecyclerView.setAdapter(myCollectionAndFootprintArticleAdapter);
                EmptyViewUtils.bindEmptyView(mContext, myCollectionAndFootprintArticleAdapter, mContext.getString(R.string.No_shrimp_records));
                break;
        }
        smartRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        myCollectionAndFootprintAdapter.setOnItemClickListener(this);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLoadPageHelper.refreshPage();
                refreshLayout.finishRefresh();
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Notice notice) {
        if (notice != null) {
            if (ConstantValue.EMPTY_THAT_FOOTPRINT_GOOD_TYPE == notice.type) {
                //清空足迹
                if (tagIndex == 0) {
                    clearFootprintGood();
                }
            }
        }

    }

    /**
     * 清空足迹
     */
    private void clearFootprintGood() {
        Map<String, String> clearParam = new HashMap<>();
        clearParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.EMPEY_THE_FOOTPRINT_URL);
        String clearParamJson = JsonUtils.toJson(clearParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.deleteThatGood(userToken, clearParamJson);
        }
    }


    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void loadMorePageDate(int page) {
        getMyFootprintList(page);
    }

    /**
     * 获取我的足迹
     */
    private void getMyFootprintList(int currentPage) {
        Map<String, String> collectionOrFootParam = new HashMap<>();
        collectionOrFootParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.THE_CLASS_OF_MY_FOOTPRINT_TYPE);
        collectionOrFootParam.put(ApiParam.PAGE_KEY, String.valueOf(currentPage));
        collectionOrFootParam.put(ApiParam.SIZE_KEY, ApiParam.SIZE_NUMBER_VALUE);
        String collectionOrFootParamJson = JsonUtils.toJson(collectionOrFootParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getGoodList(userToken, collectionOrFootParamJson);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        int goodStatus = myCollectionAndFootprintAdapter.getItem(position).getStatus();
        int goodId = myCollectionAndFootprintAdapter.getItem(position).getItem_id();
        HomeGoodsDetailsActivity.start(mContext, goodId, ConstantValue.THE_TYPE_OF_OTHER);
    }

    @Override
    public void getListSuccess(CollectGoodMessage goodMessages) {
        if (goodMessages != null) {
            this.collectionGoodLists = goodMessages.getData();
            smartRefreshLoadPageHelper.setData(collectionGoodLists);
//            if (collectionGoodLists.size() < 1) {
//                showToast(mContext.getString(R.string.All_content_is_displayed));
//            }
        }
    }

    @Override
    public void deleteGoodSuccess(String message) {
        showToast(message);
        smartRefreshLoadPageHelper.refreshPage();
    }

    @Override
    public void onNewWorkException(String message) {

    }

    @Override
    public void onRequestFailure(int resultCode, String message) {

    }
}
