package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.home.model.GoodActivityBean;
import com.goulala.xiayun.mycenter.model.CollectionGoodList;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/18
 * function : 我的收藏和我的足迹公用的   (商品)   的adapter
 */
public class MyCollectionAndFootprintAdapter extends BaseQuickAdapter<CollectionGoodList, BaseViewHolder> {

    private int mClassType;
    private boolean isShowCheckBox;

    public MyCollectionAndFootprintAdapter(@Nullable List<CollectionGoodList> data, int classType) {
        super(R.layout.include_my_collention_and_footprint_item_view, data);
        this.mClassType = classType;
    }


    public void setShowCheckBox(boolean showCheckBox) {
        isShowCheckBox = showCheckBox;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CollectionGoodList item) {

        ImageLoaderUtils.displayGoods(item.getSmallimage(), (ImageView) helper.getView(R.id.iv_good_imageView));
        helper.setText(R.id.tv_good_name, item.getName())
                .setText(R.id.tv_good_describe, item.getSpecification());
        ImageView ivSaleOffOrFailure = helper.getView(R.id.ic_commodity_failed);
        List<GoodActivityBean> goodActivityBean = item.getActive();  //判断活动
        if (goodActivityBean != null && !goodActivityBean.isEmpty()) { //有活动
            if (goodActivityBean.get(0).getStock() > 0) {
                //没有售罄
                helper.setText(R.id.tv_good_price, mContext.getString(R.string.the_price, goodActivityBean.get(0).getPrice() + ""))
                        .setText(R.id.tv_The_member_price, mContext.getString(R.string.the_price, goodActivityBean.get(0).getMember_price() + ""));
                if (2 == item.getStatus()) { // 没有售罄失效了，显示失效
                    ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                    ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                } else {   // 没有售罄,也没有失效，都不显示
                    ivSaleOffOrFailure.setVisibility(View.GONE);
                }

            } else { //售罄了
                helper.setText(R.id.tv_good_price, mContext.getString(R.string.the_price, item.getPrice() + ""))
                        .setText(R.id.tv_The_member_price, mContext.getString(R.string.the_price, item.getMember_price() + ""));
                ivSaleOffOrFailure.setVisibility(View.VISIBLE);
                if (2 == item.getStatus()) { // 即售罄也失效了，显示失效
                    ivSaleOffOrFailure.setImageResource(R.drawable.ic_commodity_failed);
                } else {   //售罄了，没有失效，显示售罄
                    ivSaleOffOrFailure.setImageResource(R.drawable.ic_pic_commodity_so);
                }
            }
        } else {//没有活动
            helper.setText(R.id.tv_good_price, mContext.getString(R.string.the_price, item.getPrice() + ""))
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
        switch (mClassType) {
            case ConstantValue.THE_CLASS_OF_MY_FOOTPRINT_TYPE:
                //  我的足迹
                helper.setGone(R.id.cb_good_check, false);
                break;
            case ConstantValue.THE_CLASS_OF_MY_COLLECTION_TYPE:
                //我的收藏
                helper.setGone(R.id.cb_good_check, isShowCheckBox);
                helper.setChecked(R.id.cb_good_check, item.isSelect())
                        .addOnClickListener(R.id.cb_good_check);
                break;
        }
        int selectNumber = 0;
        String item_ids = null;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mData.size(); i++) {
            CollectionGoodList message = mData.get(i); //拿到对应的item
            int goodId = mData.get(i).getItem_id();
            if (message.isSelect()) {
                selectNumber++;   //被选中了
                buffer.append(goodId + ",");
            } else {
                selectNumber--; //没有被选中
            }
        }
        if (buffer.length() > 1) {
            item_ids = buffer.substring(0, buffer.length() - 1);
        }
        if (selectNumber == mData.size()) {
            //选中数量==集合的总数，表示全部被选中了，发消息
            EventBus.getDefault().post(new Notice(ConstantValue.IS_ALL_SELECT_GOOD_OF_DELETE, "clear"));
        } else {
            EventBus.getDefault().post(new Notice(ConstantValue.IS_NOT_ALL_SELECT_GOOD_OF_DELETE, item_ids));
        }

    }
}
