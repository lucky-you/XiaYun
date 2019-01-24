package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.mycenter.model.LogisticsDetailsList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/3
 * function  : 物流详情的adapter
 */
public class LogisticsDetailsAdapter extends BaseQuickAdapter<LogisticsDetailsList, BaseViewHolder> {
    public LogisticsDetailsAdapter(@Nullable List<LogisticsDetailsList> data) {
        super(R.layout.include_logisttics_details_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LogisticsDetailsList item) {

        helper.setText(R.id.tv_Logistics_information_message, item.getContext())
                .setText(R.id.tv_Logistics_information_time, item.getTime());
        if (helper.getAdapterPosition() == 1) {
            helper.setImageResource(R.id.iv_status_point, R.drawable.ic_logistics_transport_bright);
        } else if (helper.getAdapterPosition() == mData.size()) {
            helper.setImageResource(R.id.iv_status_point, R.drawable.ic_logistics_transport_grey);
        } else {
            helper.setImageResource(R.id.iv_status_point, R.drawable.ic_logistics_grey_cilcie);
        }
    }
}
