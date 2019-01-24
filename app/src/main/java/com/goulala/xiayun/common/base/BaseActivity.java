package com.goulala.xiayun.common.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.activity.LoginActivity;
import com.goulala.xiayun.common.lib.LibActivity;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.utils.UserUtils;
import com.goulala.xiayun.common.view.TitleBuilder;


public abstract class BaseActivity extends LibActivity {

    protected String userToken;
    protected UserInfo userInfo;

    public TitleBuilder initTitle(Object obj) {
        if (obj instanceof String) {
            return new TitleBuilder(this).setTitleText((String) obj);
        } else {
            return new TitleBuilder(this).setTitleText((int) obj);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCommonUserData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCommonUserData();
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

    public RecyclerView initCommonRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        return initCommonRecyclerView(R.id.recyclerView, adapter, decoration);
    }

    public RecyclerView initCommonRecyclerView(@IdRes int id, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        RecyclerView recyclerView = (RecyclerView) findViewById(id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    public RecyclerView initGridRecyclerView(@IdRes int id, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, int spanCount) {
        RecyclerView recyclerView = (RecyclerView) findViewById(id);
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    public RecyclerView initGridRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration, int spanCount) {
        return initGridRecyclerView(R.id.recyclerView, adapter, decoration, spanCount);
    }


}
