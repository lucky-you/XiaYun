package com.goulala.xiayun.home.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.utils.ImageLoaderUtils;
import com.goulala.xiayun.R;
import com.goulala.xiayun.home.model.GoodActivityBean;
import com.goulala.xiayun.home.model.GoodItemMessage;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/7
 * @function : 秒杀商品的adapter
 */
public class HomeSecondKillGoodAdapter extends BaseQuickAdapter<GoodItemMessage, BaseViewHolder> {
    public HomeSecondKillGoodAdapter(@Nullable List<GoodItemMessage> data) {
        super(R.layout.include_second_kill_good_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodItemMessage item) {
        ImageLoaderUtils.displayGoods(item.getSmallimage(), (ImageView) helper.getView(R.id.tv_good_imageView));
        TextView tvOriginalPrice = helper.getView(R.id.tv_original_price);
        tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvOriginalPrice.setText(mContext.getString(R.string.the_price, item.getPrice() + ""));
        List<GoodActivityBean> goodActivityBean = item.getActive();
        if (goodActivityBean != null && goodActivityBean.size() > 0) {
            helper.setText(R.id.tv_good_name, item.getName())
                    .setText(R.id.tv_have_sold_number, mContext.getString(R.string.have_sold, String.valueOf(goodActivityBean.get(0).getSales())))
                    .setGone(R.id.iv_have_sale_off, 0 == goodActivityBean.get(0).getStock())
                    .setGone(R.id.Divide_line, helper.getAdapterPosition() != mData.size() - 1);
            if (goodActivityBean.get(0).getStock() > 0) {
                helper.setText(R.id.tv_Present_price, mContext.getString(R.string.the_price, goodActivityBean.get(0).getPrice() + ""))
                        .setText(R.id.tv_member_price, mContext.getString(R.string.the_price, goodActivityBean.get(0).getMember_price() + ""));
            } else {
                helper.setText(R.id.tv_Present_price, mContext.getString(R.string.the_price, item.getPrice() + ""))
                        .setText(R.id.tv_member_price, mContext.getString(R.string.the_price, item.getMember_price() + ""));
            }

        }
    }
}
