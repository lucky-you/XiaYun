package com.goulala.xiayun.shopcar.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.shopcar.model.MerchantMessage;

import java.util.List;

/**
 * author     : Z_B
 * date      : 2018/8/17
 * function : 确认订单
 */
public class MakeSureTheOrderAdapter extends BaseQuickAdapter<MerchantMessage, BaseViewHolder> {
    public MakeSureTheOrderAdapter(@Nullable List<MerchantMessage> data) {
        super(R.layout.include_make_sure_order_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MerchantMessage item) {
        helper.setText(R.id.tv_shop_name, item.getMerchant());
        RecyclerView publicRecyclerView = helper.getView(R.id.public_RecyclerView);
        List<GoodItemMessage> goodItemMessageList = item.getItem();
        if (goodItemMessageList != null && goodItemMessageList.size() > 0) {
            MakeSureTheOrderGoodAdapter makeSureTheOrderGoodAdapter = new MakeSureTheOrderGoodAdapter(goodItemMessageList);
            publicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            publicRecyclerView.setAdapter(makeSureTheOrderGoodAdapter);
        }
    }
}
