package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/18
 * @function : 常用工具的adapter
 */
public class CommonlyUsedToolsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int[] imageList;

    public CommonlyUsedToolsAdapter(@Nullable List<String> data, int[] imageViews) {
        super(R.layout.include_commonly_used_tool_item_view, data);
        this.imageList = imageViews;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_bottom_text, item);
        helper.setImageResource(R.id.iv_top_image, imageList[helper.getAdapterPosition()]);
    }


}
