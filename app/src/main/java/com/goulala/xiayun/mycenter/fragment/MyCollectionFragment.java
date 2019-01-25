package com.goulala.xiayun.mycenter.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpFragment;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.LogUtils;
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
 * function  : 我的收藏的fragment
 */
public class MyCollectionFragment extends BaseMvpFragment<MyCollectionOrMyFootprintPresenter> implements IMyCollectionOrMyFootprintView,
        SmartRefreshLoadPageHelper.DataProvider,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener {
    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private CheckBox cbDeleteSelect;
    private TextView tvDeleteTheGoods;
    private LinearLayout llShopCarBottomDeleteLayout;
    private int tagIndex;
    private List<CollectionGoodList> collectionGoodLists = new ArrayList<>();
    private MyCollectionAndFootprintAdapter myCollectionAndFootprintAdapter;
    private MyCollectionAndFootprintArticleAdapter myCollectionAndFootprintArticleAdapter;
    private String item_ids;
    private SmartRefreshLoadPageHelper<CollectionGoodList> smartRefreshLoadPageHelper = new SmartRefreshLoadPageHelper<>(this);

    public static MyCollectionFragment newInstance(int index) {
        MyCollectionFragment myCollectionFragment = new MyCollectionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.INDEX, index);
        myCollectionFragment.setArguments(bundle);
        return myCollectionFragment;
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
        return R.layout.include_my_collection_layout;
    }

    @Override
    public void bindViews(View contentView) {
        registerEvent();
        tagIndex = getArguments().getInt(ConstantValue.INDEX);
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        cbDeleteSelect = get(R.id.cb_delete_select);
        cbDeleteSelect.setOnClickListener(this);
        tvDeleteTheGoods = get(R.id.tv_Delete_the_goods);
        tvDeleteTheGoods.setOnClickListener(this);
        llShopCarBottomDeleteLayout = get(R.id.ll_shop_car_bottom_delete_layout);
        tvDeleteTheGoods.setBackgroundColor(mContext.getResources().getColor(R.color.color_e53d3d));
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        switch (tagIndex) {
            case 0://商品
                tvDeleteTheGoods.setText(mContext.getString(R.string.Delete_the_goods));
                myCollectionAndFootprintAdapter = new MyCollectionAndFootprintAdapter(collectionGoodLists, ConstantValue.THE_CLASS_OF_MY_COLLECTION_TYPE);
                smartRecyclerView.setAdapter(myCollectionAndFootprintAdapter);
                smartRefreshLoadPageHelper.attachView(refreshLayout, smartRecyclerView, myCollectionAndFootprintAdapter);
                myCollectionAndFootprintAdapter.setOnItemClickListener(this);
                myCollectionAndFootprintAdapter.setOnItemChildClickListener(this);
                EmptyViewUtils.bindEmptyView(mContext, myCollectionAndFootprintAdapter, mContext.getString(R.string.No_goods_have_been_collected));
                break;
            case 1: //虾记
                tvDeleteTheGoods.setText(mContext.getString(R.string.Delete_the_shrimp_remember));
                refreshLayout.setEnableLoadMore(false);
                myCollectionAndFootprintArticleAdapter = new MyCollectionAndFootprintArticleAdapter(null, ConstantValue.THE_CLASS_OF_MY_COLLECTION_TYPE);
                smartRecyclerView.setAdapter(myCollectionAndFootprintArticleAdapter);
//                myCollectionAndFootprintArticleAdapter.setOnItemClickListener(this);
//                myCollectionAndFootprintArticleAdapter.setOnItemChildClickListener(this);
                EmptyViewUtils.bindEmptyView(mContext, myCollectionAndFootprintArticleAdapter, mContext.getString(R.string.No_shrimp_records));
                break;
        }
        smartRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
            switch (notice.type) {
                case ConstantValue.IS_SHOW_DELETE_BUTTON:
                    //是否显示左侧的checkBox
                    boolean isShowDeleteButton = (boolean) notice.contentOne;
                    if (myCollectionAndFootprintAdapter != null) {
                        myCollectionAndFootprintAdapter.setShowCheckBox(isShowDeleteButton);
                        myCollectionAndFootprintAdapter.notifyDataSetChanged();
                    }
                    if (isShowDeleteButton) {
                        llShopCarBottomDeleteLayout.setVisibility(View.VISIBLE);
                    } else {
                        llShopCarBottomDeleteLayout.setVisibility(View.GONE);
                    }
                    break;
                case ConstantValue.IS_ALL_SELECT_GOOD_OF_DELETE:
                    item_ids = (String) notice.contentOne;
                    cbDeleteSelect.setChecked(true);
                    break;
                case ConstantValue.IS_NOT_ALL_SELECT_GOOD_OF_DELETE:
                    item_ids = (String) notice.contentOne;
                    cbDeleteSelect.setChecked(false);
                    break;
            }
        }
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.cb_delete_select:
                //全部选择
                if (collectionGoodLists != null && collectionGoodLists.size() > 0) {
                    for (int i = 0; i < collectionGoodLists.size(); i++) {
                        collectionGoodLists.get(i).setSelect(cbDeleteSelect.isChecked());
                    }
                }
                myCollectionAndFootprintAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_Delete_the_goods:
                //删除商品
                if (TextUtils.isEmpty(item_ids)) {
                    showToast(mContext.getString(R.string.Please_select_the_item_to_delete));
                    return;
                }
                deleteCollectionGood();
                break;
        }
    }

    /**
     * 获取我的收藏的列表
     */
    private void getCollectionList(int currentPage) {
        Map<String, String> collectionOrFootParam = new HashMap<>();
        collectionOrFootParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.THE_CLASS_OF_MY_COLLECTION_TYPE);
        collectionOrFootParam.put(ApiParam.PAGE_KEY, String.valueOf(currentPage));
        collectionOrFootParam.put(ApiParam.SIZE_KEY, ApiParam.SIZE_NUMBER_VALUE);
        String collectionOrFootParamJson = JsonUtils.toJson(collectionOrFootParam);
        LogUtils.showLog(userToken, collectionOrFootParamJson);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getGoodList(userToken, collectionOrFootParamJson);
        showDialog("");

    }

    /**
     * 删除商品
     */
    private void deleteCollectionGood() {
        Map<String, String> deleteGoodParam = new HashMap<>();
        deleteGoodParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CANCEL_COLLECTION_THAT_GOOD_VALUE);
        deleteGoodParam.put(ApiParam.ITEM_IDS_KEY, item_ids);
        String deleteGoodParamJson = JsonUtils.toJson(deleteGoodParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.deleteThatGood(userToken, deleteGoodParamJson);
        showDialog("");
    }

    @Override
    public void loadMorePageDate(int page) {
        getCollectionList(page);
    }

    @Override
    public void getListSuccess(CollectGoodMessage goodMessages) {
        dismissDialog();
        if (goodMessages != null) {
            this.collectionGoodLists = goodMessages.getData();
            smartRefreshLoadPageHelper.setData(collectionGoodLists);
        }
    }

    @Override
    public void deleteGoodSuccess(String message) {
        showToast(message);
        dismissDialog();
        smartRefreshLoadPageHelper.refreshPage();
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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.cb_good_check) {
            CheckBox cbGoodSelect = view.findViewById(R.id.cb_good_check);
            if (cbGoodSelect.isChecked()) {
                myCollectionAndFootprintAdapter.getItem(position).setSelect(true);
            } else {
                myCollectionAndFootprintAdapter.getItem(position).setSelect(false);
            }
            myCollectionAndFootprintAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        int goodStatus = myCollectionAndFootprintAdapter.getItem(position).getStatus();
        int goodId = myCollectionAndFootprintAdapter.getItem(position).getItem_id();
        HomeGoodsDetailsActivity.start(mContext, goodId, ConstantValue.THE_TYPE_OF_OTHER);
    }
}
