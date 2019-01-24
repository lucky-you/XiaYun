package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.mycenter.model.AttachBean;
import com.goulala.xiayun.mycenter.model.LogisticsMessageList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/1
 * function  : 系统消息的adapter
 */
public class SystemMessageAdapter extends BaseQuickAdapter<LogisticsMessageList, BaseViewHolder> {
    public SystemMessageAdapter(@Nullable List<LogisticsMessageList> data) {
        super(R.layout.include_system_message_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogisticsMessageList item) {
        helper.setText(R.id.tv_order_create_time, DateUtils.getStrTime(item.getCreatetime()))
                .setText(R.id.tv_order_status, item.getName())
                .setText(R.id.tv_The_message_content, item.getMemo())
                .setGone(R.id.iv_is_read, item.getReadtime() == 0);
        AttachBean attachBean = item.getAttach();
        if (attachBean != null) {
            helper.setText(R.id.tv_Withdrawal_amount_money, mContext.getString(R.string.Withdrawal_amount_two, attachBean.getMoney()));
        }
    }
}
