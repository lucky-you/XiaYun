package com.goulala.xiayun.home.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.home.model.GoodActivityBean;
import com.goulala.xiayun.home.model.GoodItemMessage;

import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/7
 * function : 横向滚动商品的adapter
 */
public class HomeRecommendedGoodAdapter extends BaseMultiItemQuickAdapter<GoodItemMessage, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * <p>
     * param data A new list is created out of this one to avoid mutable list
     */
    public HomeRecommendedGoodAdapter(List<GoodItemMessage> data) {
        super(data);
        addItemType(GoodItemMessage.TYPE_ONE, R.layout.include_home_recommend_good_item_view);
        addItemType(GoodItemMessage.TYPE_TWO, R.layout.include_recommend_good_foot_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodItemMessage item) {
        switch (helper.getItemViewType()) {
            case GoodItemMessage.TYPE_ONE:
                ImageLoaderUtils.displayGoods(item.getSmallimage(), (ImageView) helper.getView(R.id.iv_home_good_icon));
                helper.setText(R.id.tv_value_seal_good_name, item.getName());
                ImageView ivSaleOffOrFailure = helper.getView(R.id.iv_have_sale_off);
                List<GoodActivityBean> homeValueSealGoodItem = item.getActive();
                if (homeValueSealGoodItem != null && homeValueSealGoodItem.size() > 0) {
                    //有活动
                    GoodActivityBean goodActivityBean = homeValueSealGoodItem.get(0);
                    helper.setText(R.id.tv_Limited_time_discount, goodActivityBean.getSell_title());

                    TextView tvSellRule = helper.getView(R.id.tv_Full_reduction);
                    tvSellRule.setText(goodActivityBean.getSell_type());
                    if (goodActivityBean.getStock() > 0) { //没有售罄
                        helper.setText(R.id.tv_The_normal_Price, mContext.getString(R.string.the_price, goodActivityBean.getPrice() + ""))
                                .setText(R.id.tv_The_member_price, mContext.getString(R.string.the_price, goodActivityBean.getMember_price() + ""));
                        if (2 == item.getStatus()) { // 没有售罄失效了，显示失效
                            ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                            ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                        } else {   // 没有售罄,也没有失效，都不显示
                            ivSaleOffOrFailure.setVisibility(View.GONE);
                        }
                    } else {//售罄了
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
                    //没有活动
                    helper.setGone(R.id.llActivityLayout, false)
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
                break;
            case GoodItemMessage.TYPE_TWO:
                helper.addOnClickListener(R.id.rl_to_sea_more_goods);
                break;
        }
    }
}
