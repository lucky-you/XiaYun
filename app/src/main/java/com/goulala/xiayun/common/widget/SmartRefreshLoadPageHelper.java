package com.goulala.xiayun.common.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.goulala.xiayun.common.widget.EmptyDeal.size;

/**
 * author : Z_B
 * date : 2018/8/18
 * function : 数据分页上拉加载更多的帮助类 使用的是SmartRefreshLayout刷新
 */
public class SmartRefreshLoadPageHelper<T> {
    public int page = 1;//当前页
    SmartRefreshLoadPageHelper.DataProvider provider;//数据提供者,主要实现loadData 方法即可
    BaseQuickAdapter<T, ?> quickAdapter;//适配器
    SmartRefreshLayout refreshLayout;//下拉刷新控件
    AtomicBoolean isLoadMore = new AtomicBoolean(false);//是否处于加载状态
    int maxPageSize = 10;//每页最大值
    boolean isAttach = false;

    public SmartRefreshLoadPageHelper(SmartRefreshLoadPageHelper.DataProvider provider) {
        this.provider = provider;
    }

    /**
     * 附加列表信息,设置加载刷新信息
     */
    public void attachView(SmartRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter<T, ?> quickAdapter) {
        this.quickAdapter = quickAdapter;
        this.refreshLayout = refreshLayout;
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshPage();
            }
        });
        quickAdapter.setEnableLoadMore(true);
        quickAdapter.disableLoadMoreIfNotFullPage(recyclerView);
        quickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isLoadMore.set(true);
                page++;
                provider.loadMorePageDate(page);
            }
        }, recyclerView);
        provider.loadMorePageDate(page);
        isAttach = true;
    }

    /**
     * 刷新
     */
    public void refreshPage() {
        page = 1;
        isLoadMore.set(false);
        provider.loadMorePageDate(page);
    }

    /**
     * 设置数据,
     *
     * @param data
     */
    public void setData(List<T> data) {
        maxPageSize = Math.max(maxPageSize, size(data));
        if (isLoadMore.get() || page != 1) {
            if (EmptyDeal.Empty(data)) {
                page--;
                quickAdapter.setEnableLoadMore(false);
                quickAdapter.loadMoreEnd();
            } else {
                quickAdapter.addData(data);
            }
            loadComplete();
        } else {
            loadComplete();
            quickAdapter.setNewData(data);
            quickAdapter.setEnableLoadMore(size(data) >= maxPageSize);
        }
    }

    /**
     * 加载/刷新完成
     */
    public void loadComplete() {
        isLoadMore.set(false);
        refreshLayout.setEnableScrollContentWhenLoaded(true);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        quickAdapter.loadMoreComplete();
//        if (!quickAdapter.isLoadMoreEnable()) {
//            quickAdapter.loadMoreEnd(true);
//        } else {
//            quickAdapter.loadMoreEnd(false);
//        }
        if (!quickAdapter.isLoadMoreEnable()) {
//            if (quickAdapter.getFooterLayoutCount() == 0) {
            quickAdapter.addFooterView(View.inflate(BaseApplication.getInstance(), R.layout.include_recyclerview_foot_view, null));
//            }
        } else {
//            if (quickAdapter.getFooterLayoutCount() > 0) {
            quickAdapter.removeAllFooterView();
//            }
        }
    }

    /**
     * 数据提供者接口,用于进行加载动作
     */
    public interface DataProvider {
        void loadMorePageDate(int page);

    }


    public static void addData2List(List src, List data) {
        if (data != null) {
            Iterator var3 = data.iterator();
            while (true) {
                while (var3.hasNext()) {
                    Object object = var3.next();
                    int index = src.indexOf(object);
                    if (index != -1) {
                        src.set(index, object);
                    } else {
                        src.add(object);
                    }
                }
                return;
            }
        }
    }

    public boolean isAttach() {
        return isAttach;
    }

}
