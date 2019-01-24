package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.mycenter.model.AttachBean;
import com.goulala.xiayun.mycenter.model.LogisticsMessageList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/1
 * function  : 消息详情的adapter 物流和售后公用的
 */
public class MessageDetailsAdapter extends BaseQuickAdapter<LogisticsMessageList, BaseViewHolder> {

    private int mClassType;

    public MessageDetailsAdapter(@Nullable List<LogisticsMessageList> data, int type) {
        super(R.layout.include_message_details_item_view, data);
        this.mClassType = type;
    }


    @Override
    protected void convert(BaseViewHolder helper, LogisticsMessageList item) {
        helper.setText(R.id.tv_order_create_time, DateUtils.getStrTime(item.getCreatetime()))
                .setText(R.id.tv_order_status, item.getName())
                .setGone(R.id.iv_is_read, item.getReadtime() == 0);
        AttachBean attachBean = item.getAttach();
        if (attachBean != null) {
            ImageLoaderUtils.displayGoods(attachBean.getItem_img(), (ImageView) helper.getView(R.id.civ_good_photo));
            switch (mClassType) {
                case ConstantValue.THE_CLASS_TYPE_OF_LOGISTICS_NEWS: //物流消息
                    helper.setText(R.id.tv_good_name, attachBean.getName())
                            .setText(R.id.tv_The_order_no_of_message, mContext.getString(R.string.The_order_no, attachBean.getExpress_number()));
                    break;
                case ConstantValue.THE_CLASS_TYPE_OF_AFTER_SELL_NEWS: //售后消息
                    helper.setText(R.id.tv_good_name, attachBean.getName())
                            .setText(R.id.tv_The_order_no_of_message, mContext.getString(R.string.The_order_no, attachBean.getShop_order()));
                    break;
            }
        }
    }
}
