package com.goulala.xiayun.common.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.mvp.MvpActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.view.TitleBuilder;


public abstract class BaseMvpActivity<P extends BasePresenter> extends MvpActivity<P> {


    protected String userToken;

    public TitleBuilder initTitle(Object obj) {
        if (obj instanceof String) {
            return new TitleBuilder(this).setTitleText((String) obj);
        } else {
            return new TitleBuilder(this).setTitleText((int) obj);
        }
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        userToken = UserUtils.getUserToken();
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarLightMode(this,true);
    }


}
