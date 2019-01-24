package com.goulala.xiayun.common.base;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.activity.LoginActivity;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.mvp.MvpFragment;
import com.goulala.xiayun.common.utils.UserUtils;
import com.goulala.xiayun.common.view.MProgressDialog;
import com.goulala.xiayun.common.view.TitleBuilder;


public abstract class BaseMvpFragment<P extends BasePresenter> extends MvpFragment<P> {

    protected String userToken;
    protected UserInfo userInfo;
//    protected KfStartHelper helper;

    public TitleBuilder initTitle(Object obj) {
        if (obj instanceof String) {
            return new TitleBuilder(mRootView).setTitleText((String) obj);
        } else {
            return new TitleBuilder(mRootView).setTitleText((int) obj);
        }
    }

    public TitleBuilder initTitleNoBack(Object obj) {
        if (obj instanceof String) {
            return new TitleBuilder(mRootView).setTitleText((String) obj).noBack();
        } else {
            return new TitleBuilder(mRootView).setTitleText((int) obj).noBack();
        }
    }


    public RecyclerView initCommonRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    public RecyclerView initHorizontalRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        return initHorizontalRecyclerView(null, adapter, decoration);
    }

    public RecyclerView initHorizontalRecyclerView(RecyclerView recyclerView, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        return initHorizontalRecyclerView(recyclerView, adapter, decoration, false);
    }

    public RecyclerView initHorizontalRecyclerView(RecyclerView recyclerView, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, boolean reverseLayout) {
        if (recyclerView == null)
            recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, reverseLayout));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    public RecyclerView initGridRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, int spanCount) {
        return initGridRecyclerView((RecyclerView) mRootView.findViewById(R.id.recyclerView), adapter, decoration, spanCount);
    }

    public RecyclerView initGridRecyclerView(RecyclerView recyclerView, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, int spanCount) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    /**
     * 是否登录
     */
    public boolean checkLogin() {
        getCommonUserData();
        if (TextUtils.isEmpty(userToken)) {
            intentToActivity(LoginActivity.class);
            return false;
        }
        return true;
    }

    /**
     * 获取用户信息
     */
    public void getCommonUserData() {
        userToken = UserUtils.userToken();
        userInfo = BaseApplication.getInstance().getUserInfo();
    }

    @Override
    public void onResume() {
        getCommonUserData();
        super.onResume();
    }


    @Override
    protected void firstLoad() {
        getCommonUserData();
        super.firstLoad();
    }


}
