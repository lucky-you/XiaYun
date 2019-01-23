package com.goulala.xiayun.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.home.model.DetailsDescriptionList;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/15
 * @function : 商品详情的adapter
 */
public class ProductDetailsMessageAdapter extends BaseQuickAdapter<DetailsDescriptionList, BaseViewHolder> {
    public ProductDetailsMessageAdapter(@Nullable List<DetailsDescriptionList> data) {
        super(R.layout.include_good_details_message_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailsDescriptionList item) {
        helper.setText(R.id.tv_message_text, item.messageDescribe)
                .setText(R.id.tv_message_title, item.messageTitle)
                .setVisible(R.id.dotted_line_view, helper.getAdapterPosition() != mData.size() - 1);

    }
}
