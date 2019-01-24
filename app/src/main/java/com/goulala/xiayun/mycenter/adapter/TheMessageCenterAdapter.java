package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.mycenter.model.MessageCenterList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/10/24
 * function  : 消息中心
 */
public class TheMessageCenterAdapter extends BaseQuickAdapter<MessageCenterList, BaseViewHolder> {
    private int[] imageList;

    public TheMessageCenterAdapter(@Nullable List<MessageCenterList> data, int[] imageViews) {
        super(R.layout.include_message_center_item_view, data);
        this.imageList = imageViews;
    }


    @Override
    protected void convert(BaseViewHolder helper, MessageCenterList item) {
        helper.setText(R.id.tv_message_title, item.getName())
                .setGone(R.id.tv_message_count, item.getCount() > 0)
                .setText(R.id.tv_message_count, item.getCount() + "");
        helper.setImageResource(R.id.iv_message_image_view, imageList[helper.getAdapterPosition()]);
    }
}
