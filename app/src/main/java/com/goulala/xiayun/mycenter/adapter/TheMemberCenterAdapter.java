package com.goulala.xiayun.mycenter.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.mycenter.model.TheMemberCenterList;

import java.util.List;

/**
 * author     : Z_B
 * date      : 2018/8/30
 * function : 会员中心的adapter
 */
public class TheMemberCenterAdapter extends BaseQuickAdapter<TheMemberCenterList, BaseViewHolder> {

    private int mPosition;

    public TheMemberCenterAdapter(@Nullable List<TheMemberCenterList> data, int position) {
        super(R.layout.include_the_member_center_item_layout, data);
        this.mPosition = position;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, TheMemberCenterList item) {
        if (mPosition == helper.getAdapterPosition()) {
            helper.setBackgroundRes(R.id.rl_member_center_item_layout, R.drawable.shape_the_member_center_select_background);
        } else {
            helper.setBackgroundRes(R.id.rl_member_center_item_layout, R.drawable.shape_the_member_center_not_select_background);
        }
        helper.setText(R.id.tv_Member_of_the_time, item.getDescribe());
        TextView tvOldPrice = helper.getView(R.id.tv_The_original_price);
        TextView tvHalfOfTwoYear = helper.getView(R.id.tv_Half_price_from_the_second_year);
        TextView tvMemberOfTime = helper.getView(R.id.tv_Member_of_the_time);
        TextView tvTagOfTime = helper.getView(R.id.tv_Time_limit);
        switch (item.getDiscount_type()) {
            case 1:
                //不显示限时价格
                tvOldPrice.setVisibility(View.INVISIBLE);
                tvHalfOfTwoYear.setVisibility(View.INVISIBLE);
//                tvMemberOfTime.setText(item.getDescribe());
                tvTagOfTime.setVisibility(View.GONE);
                helper.setText(R.id.tv_Present_price, mContext.getString(R.string.the_price, item.getOpen_price()));
                break;
            case 2:
                //显示限时价格
                helper.setText(R.id.tv_Present_price, mContext.getString(R.string.the_price, item.getDiscount_open_price()));
                tvOldPrice.setVisibility(View.VISIBLE);
                tvHalfOfTwoYear.setVisibility(View.VISIBLE);
                tvHalfOfTwoYear.setText(item.getVip_title());
                tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvOldPrice.setText(mContext.getString(R.string.the_price, item.getOpen_price()));
//                tvMemberOfTime.setText(item.getDescribe());
                tvTagOfTime.setVisibility(View.VISIBLE);
                break;

        }
    }
}
