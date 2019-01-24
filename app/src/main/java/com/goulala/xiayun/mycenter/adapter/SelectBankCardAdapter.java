package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.mycenter.model.BankCardList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/8/31
 * function  : 选择银行卡的adapter
 */
public class SelectBankCardAdapter extends BaseQuickAdapter<BankCardList, BaseViewHolder> {
    public SelectBankCardAdapter(@Nullable List<BankCardList> data) {
        super(R.layout.include_select_bank_card_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCardList item) {
        ImageLoaderUtils.displayGoods(item.getImage(), (ImageView) helper.getView(R.id.civ_bank_logo));
        helper.setText(R.id.tv_bank_name, item.getName());
    }
}
