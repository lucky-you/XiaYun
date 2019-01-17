package com.goulala.xiayun.common.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.lib.LibActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.view.TitleBuilder;


public abstract class BaseActivity extends LibActivity {


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
//        LinearLayout linearLayout = get(R.id.ll_root_layout);
//        BarUtils.addMarginTopEqualStatusBarHeight(linearLayout);
//        BarUtils.setStatusBarColor(this, BaseApplication.getInstance().getResources().getColor(R.color.title_bar_color), 0);
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


    @Override
    protected void onResume() {

        super.onResume();
    }
}
