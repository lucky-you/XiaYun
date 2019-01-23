package com.goulala.xiayun.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.db.SearchHistory;

import java.util.List;


/**
 * @author : Z_B
 * @date : 2018/8/13
 * @function : 搜索过程中显示搜索信息的adapter
 */
public class StartSearchAdapter extends BaseQuickAdapter<SearchHistory, BaseViewHolder> {
    public StartSearchAdapter(@Nullable List<SearchHistory> data) {
        super(R.layout.include_start_search_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistory item) {
        helper.setText(R.id.tv_search_name, item.getName());
    }
}
