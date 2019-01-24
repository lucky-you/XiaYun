package com.goulala.xiayun.shopcar.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.ToastUtils;
import com.goulala.xiayun.home.activity.SellLotsOfDetailsActivity;
import com.goulala.xiayun.home.model.GoodActivityBean;
import com.goulala.xiayun.shopcar.callback.ShopCarGoodNumberListener;
import com.goulala.xiayun.shopcar.model.ShopCarBaseDate;

import java.util.List;


/**
 * author      : Z_B
 * date       : 2018/10/17
 * function  :  购物车的adapter  这种做法是一层来写
 */
public class ShopCarActivityOfStoreOfGoodAdapter extends BaseQuickAdapter<ShopCarBaseDate, BaseViewHolder> {

    private ShopCarGoodNumberListener shopCarGoodNumberListener;  //购买数量加减的回调
    private OnAdapterRefreshListener onAdapterRefreshListener;  //全选状态的回调

    public ShopCarActivityOfStoreOfGoodAdapter(@Nullable List<ShopCarBaseDate> data) {
        super(R.layout.include_shop_car_activity_store_good_item_view, data);
    }

    public void setShopCarGoodNumberListener(ShopCarGoodNumberListener shopCarGoodNumberListener) {
        this.shopCarGoodNumberListener = shopCarGoodNumberListener;
    }

    public void setOnAdapterRefreshListener(OnAdapterRefreshListener onAdapterRefreshListener) {
        this.onAdapterRefreshListener = onAdapterRefreshListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopCarBaseDate item) {
        switch (item.getCell()) {
            case 1: // 活动
                helper.setGone(R.id.ll_activity_layout, true)
                        .setGone(R.id.ll_storeMerchant_layout, false)
                        .setGone(R.id.ll_good_item_layout, false)
                        .setText(R.id.tv_activity_name, item.getSell_title())
                        .setText(R.id.tv_shop_car_good_hit_message, item.getSell_desc())
                        .setText(R.id.tv_Visit_again, item.getSell_link());
                if (item.isGoodSelect()) {
                    helper.setBackgroundRes(R.id.iv_activity_select, R.drawable.ic_but_select);
                } else {
                    helper.setBackgroundRes(R.id.iv_activity_select, R.drawable.ic_but_default);
                }
                helper.getView(R.id.iv_activity_select).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.setGoodSelect(!item.isGoodSelect());
                        if (item.isGoodSelect()) {
                            for (int i = 0; i < mData.size(); i++) {
                                if (mData.get(i).getCell() == 3 && mData.get(i).getActive().size() > 0) { //是否是商品且有活动
                                    if (mData.get(i).getActive().get(0).getId() == item.getActive_id()) {
                                        mData.get(i).setGoodSelect(true);
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < mData.size(); i++) {
                                if (mData.get(i).getCell() == 3 && mData.get(i).getActive().size() > 0) { //是否是商品且有活动
                                    if (mData.get(i).getActive().get(0).getId() == item.getActive_id()) {
                                        mData.get(i).setGoodSelect(false);
                                    }
                                }
                            }
                        }
                        notifyDataSetChanged();
                    }
                });
//                去逛逛
                helper.getView(R.id.tv_Visit_again).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = item.getName();
                        int activityId = item.getId();
                        SellLotsOfDetailsActivity.start(mContext, title, String.valueOf(activityId), ConstantValue.THAT_CLASS_TYPE_OF_SHOP_CAR);
                    }
                });
                break;
            case 2:  //供应商
                helper.setGone(R.id.ll_activity_layout, false)
                        .setGone(R.id.ll_storeMerchant_layout, true)
                        .setGone(R.id.ll_good_item_layout, false)
                        .setText(R.id.tv_storeMerchantName, item.getName());
                break;
            case 3://商品
                ImageLoaderUtils.displayGoods(item.getSmallimage(), (ImageView) helper.getView(R.id.iv_good_imageView));
                ImageView ivSaleOffOrFailure = helper.getView(R.id.iv_have_sale_off);
                helper.setGone(R.id.ll_activity_layout, false)
                        .setGone(R.id.ll_storeMerchant_layout, false)
                        .setGone(R.id.ll_good_item_layout, true)
                        .setText(R.id.tv_good_name, item.getName())
                        .setText(R.id.tv_good_describe, item.getSpecification())
                        .setText(R.id.tv_good_number, item.getItem_num() + "");
                List<GoodActivityBean> goodActivityList = item.getActive();
                if (goodActivityList.size() > 0) {  //有活动
                    if (goodActivityList.get(0).getStock() > 0) { //判断库存
                        helper.setText(R.id.tv_good_price, mContext.getString(R.string.the_price, goodActivityList.get(0).getPrice() + ""))
                                .setText(R.id.tv_good_plus_price, mContext.getString(R.string.the_price, goodActivityList.get(0).getMember_price() + ""));
                        if (2 == item.getStatus()) { // 没有售罄失效了，显示失效
                            ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                            ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                        } else {   // 没有售罄,也没有失效，都不显示
                            ivSaleOffOrFailure.setVisibility(View.GONE);
                        }

                    } else {
                        //没有库存了恢复活动价
                        helper.setText(R.id.tv_good_price, mContext.getString(R.string.the_price, item.getPrice() + ""))
                                .setText(R.id.tv_good_plus_price, mContext.getString(R.string.the_price, item.getMember_price() + ""));

                        ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                        if (2 == item.getStatus()) { // 即售罄也失效了，显示失效
                            ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                        } else {   //售罄了，没有失效，显示售罄
                            ivSaleOffOrFailure.setImageResource(R.drawable.ic_pic_commodity_so);
                        }

                    }

                } else { //没活动
                    helper.setText(R.id.tv_good_price, mContext.getString(R.string.the_price, item.getPrice() + ""))
                            .setText(R.id.tv_good_plus_price, mContext.getString(R.string.the_price, item.getMember_price() + ""));

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
                if (item.isGoodSelect()) {
                    helper.setBackgroundRes(R.id.iv_good_select, R.drawable.ic_but_select);
                } else {
                    helper.setBackgroundRes(R.id.iv_good_select, R.drawable.ic_but_default);
                }
                //商品选择
                helper.getView(R.id.iv_good_select).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.setGoodSelect(!item.isGoodSelect());
                        boolean isGoodAllSelect = true; //记录活动下面的商品是否全部被选中
                        if (item.isGoodSelect()) {

                            List<GoodActivityBean> goodActivityList = item.getActive(); //获取商品活动的数据
                            if (goodActivityList != null && goodActivityList.size() > 0) {
                                for (int i = 0; i < mData.size(); i++) {
                                    if (mData.get(i).getCell() == 3 && mData.get(i).getActive().size() > 0) { //商品，并且有活动
                                        if (mData.get(i).getId() != item.getId()) {
                                            //不是自己
                                            if (mData.get(i).getActive().get(0).getId() == item.getActive().get(0).getId()) { //活动的id是否和商品的id相等
                                                //是同一个活动
                                                if (!mData.get(i).isGoodSelect()) {
                                                    isGoodAllSelect = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                //没有活动的商品
                            }
                            if (isGoodAllSelect) {//所有商品被选中
                                for (int i = 0; i < mData.size(); i++) {
                                    if (mData.get(i).getCell() == 1) {
                                        if (item.getActive().size() > 0) { //说明是有活动的
                                            if (mData.get(i).getActive_id() == item.getActive().get(0).getId()) {
                                                mData.get(i).setGoodSelect(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            List<GoodActivityBean> goodActivityList = item.getActive(); //获取商品活动的数据
                            if (goodActivityList != null && goodActivityList.size() > 0) { //有活动
                                for (int i = 0; i < mData.size(); i++) {
                                    if (mData.get(i).getCell() == 1 && mData.get(i).getActive_id() == goodActivityList.get(0).getId()) {
                                        mData.get(i).setGoodSelect(false);
                                    }
                                }
                            }
                        }
                        notifyDataSetChanged();
                    }
                });
                final int merchant_id = item.getMerchant_id(); //店铺id
                final int item_id = item.getId();  //商品id
                //数量减少
                helper.getView(R.id.rl_good_number_to_reduce).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (2 == item.getStatus()) return;
                        if (item.getItem_num() > 1) {
                            if (shopCarGoodNumberListener != null) {
                                shopCarGoodNumberListener.reduceGoodItemFormShopCar(merchant_id, item_id, 1);
                            }
                        }
                    }
                });
                //数量增加
                helper.getView(R.id.rl_good_number_increase).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (2 == item.getStatus()) return;
                        if (item.getItem_num() < item.getStock()) {
                            if (shopCarGoodNumberListener != null) {
                                shopCarGoodNumberListener.addGoodItemToShopCar(merchant_id, item_id, 1);
                            }
                        } else {
                            ToastUtils.showToast(mContext.getString(R.string.Insufficient_inventory, item.getStock() + ""));
                        }
                    }
                });
                //点击item，去详情
                helper.getView(R.id.ll_good_item_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int goodId = item.getId();
                        if (shopCarGoodNumberListener != null) {
                            shopCarGoodNumberListener.onGoodItemClick(goodId);
                        }
                    }
                });
                break;
        }

        /**
         * 通过回调监听全选的状态
         */
        if (onAdapterRefreshListener != null) {
            boolean isSelect = false;
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i).getCell() == 1 || mData.get(i).getCell() == 3) { //是活动或者是商品才可以被选择
                    if (!mData.get(i).isGoodSelect()) {
                        isSelect = false;
                        break;
                    } else {
                        isSelect = true;
                    }
                }
            }
            onAdapterRefreshListener.onRefresh(isSelect);
        }
    }

    /**
     * "是否被全选
     */
    public interface OnAdapterRefreshListener {
        void onRefresh(boolean isSelect);
    }


}
