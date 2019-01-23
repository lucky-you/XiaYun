package com.goulala.xiayun.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/14
 * @function : 活动规则的adapter
 */
public class ActivityRulesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ActivityRulesAdapter(@Nullable List<String> data) {
        super(R.layout.include_activity_rules_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_rules_text, item);

    }
}
