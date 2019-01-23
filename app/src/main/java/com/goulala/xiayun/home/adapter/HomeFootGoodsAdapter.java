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
 * @author : Z_B
 * @date : 2018/8/7
 * @function : 首页底部品质优选  和  购物车猜你喜欢 公用的adapter
 */
public class HomeFootGoodsAdapter extends BaseQuickAdapter<GoodItemMessage, BaseViewHolder> {

    public static int HOME_FRAGMENT_ADAPTER_TYPE = 12; //首页的品质优选
    public static int SHOP_CAR_GUESS_YOU_LIKE_ADAPTER_TYPE = 13;//购物车猜你喜欢
    private int mLayoutType;

    public HomeFootGoodsAdapter(@Nullable List<GoodItemMessage> data, int type) {
        super(R.layout.include_home_foot_good_item_view, data);
        this.mLayoutType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodItemMessage item) {
        ImageLoaderUtils.displayGoods(item.getSmallimage(), (ImageView) helper.getView(R.id.iv_foot_good_imageView));
        helper.setText(R.id.tv_foot_good_name, item.getName());
        ImageView ivSaleOffOrFailure = helper.getView(R.id.iv_have_sale_off);
        if (mLayoutType == HOME_FRAGMENT_ADAPTER_TYPE) { //首页品质优选
            helper.setGone(R.id.ll_bottom_activity_layout, false)
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
        } else { //购物车猜你喜欢
            List<GoodActivityBean> goodActivityBean = item.getActive();
            if (goodActivityBean != null && goodActivityBean.size() > 0) { //有活动
                helper.setGone(R.id.ll_bottom_activity_layout, true)
                        .setText(R.id.tv_Limited_time_discount, goodActivityBean.get(0).getSell_title())
                        .setGone(R.id.tv_Full_reduction, !TextUtils.isEmpty(goodActivityBean.get(0).getSell_rule()))
                        .setText(R.id.tv_Full_reduction, goodActivityBean.get(0).getSell_type());
                if (goodActivityBean.get(0).getStock() > 0) {  //没有售罄
                    helper.setText(R.id.tv_The_normal_Price, mContext.getString(R.string.the_price, goodActivityBean.get(0).getPrice() + ""))
                            .setText(R.id.tv_The_member_price, mContext.getString(R.string.the_price, goodActivityBean.get(0).getMember_price() + ""));
                    if (2 == item.getStatus()) { // 没有售罄失效了，显示失效
                        ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                        ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                    } else {   // 没有售罄,也没有失效，都不显示
                        ivSaleOffOrFailure.setVisibility(View.GONE);
                    }
                } else { //售罄了
                    helper.setText(R.id.tv_The_normal_Price, mContext.getString(R.string.the_price, item.getPrice() + ""))
                            .setText(R.id.tv_The_member_price, mContext.getString(R.string.the_price, item.getMember_price() + ""));
                    ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                    if (2 == item.getStatus()) { // 即售罄也失效了，显示失效
                        ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                    } else {   //售罄了，没有失效，显示售罄
                        ivSaleOffOrFailure.setImageResource(R.drawable.ic_pic_commodity_so);
                    }
                }
            } else { //无活动
                helper.setGone(R.id.ll_bottom_activity_layout, false)
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
}
