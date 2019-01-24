package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.mycenter.model.ShopDateMessage;
import com.goulala.xiayun.mycenter.model.ShopItemMessage;

import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/21
 * function :  订单详情的adapter
 */
public class TheOrderDetailsAdapter extends BaseQuickAdapter<ShopDateMessage, BaseViewHolder> {
    public TheOrderDetailsAdapter(@Nullable List<ShopDateMessage> data) {
        super(R.layout.include_the_order_details_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDateMessage item) {
        helper.setText(R.id.tv_the_shop_name, item.getMerchant());
        RecyclerView goodRecyclerView = helper.getView(R.id.public_RecyclerView);
        List<ShopItemMessage> shopItemList = item.getList();
        if (shopItemList != null && shopItemList.size() > 0) {
            TheOrderDetailsGoodItemAdapter theOrderDetailsGoodItemAdapter = new TheOrderDetailsGoodItemAdapter(shopItemList);
            goodRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            goodRecyclerView.setAdapter(theOrderDetailsGoodItemAdapter);
        }

    }
}
