package com.goulala.xiayun.home.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.home.model.GoodActivityBean;
import com.goulala.xiayun.home.model.GoodItemMessage;

import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/9
 * function : 超值热卖(查看更多)的adapter 和 搜索结果显示公用的adapter
 */
public class SellLotsOfDetailsAdapter extends BaseQuickAdapter<GoodItemMessage, BaseViewHolder> {

    public static final int HIDE_SALE_OFF_TYPE = 1; //不显示已售罄 热卖详情中使用
    public static final int SHOW_SALE_OFF_TYPE = 2; //显示已售罄 在搜索中显示
    private int isShowType;

    public SellLotsOfDetailsAdapter(@Nullable List<GoodItemMessage> data, int type) {
        super(R.layout.include_sell_lots_details_item_view, data);
        this.isShowType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodItemMessage item) {
        ImageLoaderUtils.displayGoods(item.getSmallimage(), (ImageView) helper.getView(R.id.iv_foot_good_imageView));
        ImageView ivSaleOffOrFailure = helper.getView(R.id.iv_have_sale_off);
        helper.setText(R.id.tv_foot_good_name, item.getName());
        //活动
        List<GoodActivityBean> goodActivityBeanList = item.getActive();
        if (goodActivityBeanList != null && goodActivityBeanList.size() > 0) {
            //有活动
            GoodActivityBean goodActivityBean = goodActivityBeanList.get(0);
            helper.setGone(R.id.ll_activity_layout, true)
                    .setGone(R.id.tv_Limited_time_discount, !TextUtils.isEmpty(goodActivityBean.getSell_title()))
                    .setText(R.id.tv_Limited_time_discount, goodActivityBean.getSell_title())
                    .setGone(R.id.tv_Full_reduction, !TextUtils.isEmpty(goodActivityBean.getSell_type()))
                    .setText(R.id.tv_Full_reduction, goodActivityBean.getSell_type());
            if (goodActivityBean.getStock() > 0) {
                //只要库存为0，都需要显示已售罄的ImageView
                helper.setText(R.id.tv_The_normal_Price, mContext.getString(R.string.the_price, goodActivityBean.getPrice() + ""))
                        .setText(R.id.tv_The_member_price, mContext.getString(R.string.the_price, goodActivityBean.getMember_price() + ""));
                if (2 == item.getStatus()) { // 没有售罄失效了，显示失效
                    ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                    ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                } else {   // 没有售罄,也没有失效，都不显示
                    ivSaleOffOrFailure.setVisibility(View.GONE);
                }
            } else {
                helper.setText(R.id.tv_The_normal_Price, mContext.getString(R.string.the_price, item.getPrice() + ""))
                        .setText(R.id.tv_The_member_price, mContext.getString(R.string.the_price, item.getMember_price() + ""));
                ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                if (2 == item.getStatus()) { // 即售罄也失效了，显示失效
                    ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                } else {   //售罄了，没有失效，显示售罄
                    ivSaleOffOrFailure.setImageResource(R.drawable.ic_pic_commodity_so);
                }
            }
        } else {
            //没活动
            helper.setGone(R.id.ll_activity_layout, false)
                    .setText(R.id.tv_The_normal_Price, mContext.getString(R.string.the_price, item.getPrice() + ""))
                    .setText(R.id.tv_The_member_price, mContext.getString(R.string.the_price, item.getMember_price() + ""));
            if (0 == item.getStock()) {  //售罄了--》判断有没有失效
                ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                if (2 == item.getStatus()) { // 即售罄也失效了，显示失效
                    ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                } else {   //售罄了，没有失效，显示售罄
                    ivSaleOffOrFailure.setImageResource(R.drawable.ic_pic_commodity_so);
                }
            } else {  //没有售罄--》判断有没有失效
                if (2 == item.getStatus()) { // 没有售罄失效了，显示失效
                    ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                    ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                } else {   // 没有售罄,也没有失效，都不显示
                    ivSaleOffOrFailure.setVisibility(View.GONE);
                }
            }


        }
    }
}
