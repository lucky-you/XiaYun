package com.goulala.xiayun.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.home.model.MemberDiscountBean;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/7
 * @function : 会员优惠榜单的adapter
 */
public class MembershipListAdapter extends BaseQuickAdapter<MemberDiscountBean, BaseViewHolder> {


    public MembershipListAdapter(@Nullable List<MemberDiscountBean> data) {
        super(R.layout.include_membership_list_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberDiscountBean item) {
        helper.setText(R.id.tv_members_number, item.getMobile())
                .setText(R.id.tv_Accumulation_price, mContext.getString(R.string.total_money, item.getDiscount_price()));
    }

    @Nullable
    @Override
    public MemberDiscountBean getItem(int position) {
        int newPosition = position % getData().size();
        return getData().get(newPosition);
    }

    @Override
    public int getItemViewType(int position) {
        //刚开始进入包含该类的activity时,count为0。就会出现0%0的情况，这会抛出异常，所以我们要在下面做一下判断
        int count = getHeaderLayoutCount() + getData().size();
        if (count <= 0) {
            count = 1;
        }
        int newPosition = position % count;
        return super.getItemViewType(newPosition);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


}
