package com.goulala.xiayun.home.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.utils.ConstantValue;
import com.goulala.xiayun.home.activity.HomeGoodsDetailsActivity;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.home.model.HomeValueSellingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/7
 * function : 首页推荐商品的adapter
 */
public class HomeGoodsAdapter extends BaseQuickAdapter<HomeValueSellingBean, BaseViewHolder> {


    private OnViewToMoreClickListener onViewToMoreClickListener;

    public void setOnViewToMoreClickListener(OnViewToMoreClickListener onViewToMoreClickListener) {
        this.onViewToMoreClickListener = onViewToMoreClickListener;
    }

    public HomeGoodsAdapter(@Nullable List<HomeValueSellingBean> data) {
        super(R.layout.include_home_goods_item_view, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, HomeValueSellingBean item) {

        ImageLoaderUtils.displayBannerImage(item.getSmallimage(), (ImageView) helper.getView(R.id.iv_recommended_banner));
        RecyclerView homeGoodsRecyclerView = helper.getView(R.id.home_good_recommended_recyclerView);
        helper.addOnClickListener(R.id.iv_recommended_banner);
        List<GoodItemMessage> homeValueGoodItemList = item.getData();//原有的数据集合
        List<GoodItemMessage> newValueGoodItemList = new ArrayList<>(); //新建一个集合
        for (int i = 0; i < homeValueGoodItemList.size(); i++) {
            GoodItemMessage homeValueGoodMessage = homeValueGoodItemList.get(i);
            newValueGoodItemList.add(homeValueGoodMessage);
        }
        GoodItemMessage goodItemMessage = new GoodItemMessage(true); //最后再添加一个，查看更多
        newValueGoodItemList.add(goodItemMessage);
        if (newValueGoodItemList.size() > 0) {
            for (int i = 0; i < newValueGoodItemList.size(); i++) {
                if (!newValueGoodItemList.get(i).isViewToMore()) {
                    newValueGoodItemList.get(i).setItemType(GoodItemMessage.TYPE_ONE);
                } else {
                    newValueGoodItemList.get(i).setItemType(GoodItemMessage.TYPE_TWO);
                }
            }
            final HomeRecommendedGoodAdapter homeRecommendedGoodAdapter = new HomeRecommendedGoodAdapter(newValueGoodItemList);
            homeGoodsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
            homeGoodsRecyclerView.setAdapter(homeRecommendedGoodAdapter);
            homeRecommendedGoodAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    int goodId = homeRecommendedGoodAdapter.getItem(position).getId();
                    HomeGoodsDetailsActivity.start(mContext, goodId, ConstantValue.THE_TYPE_OF_OTHER);
                }
            });
            //查看更多
            homeRecommendedGoodAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.rl_to_sea_more_goods) {
                        if (onViewToMoreClickListener != null) {
                            onViewToMoreClickListener.onViewToMore(helper.getAdapterPosition());
                        }
                    }
                }
            });
        }
    }

    public interface OnViewToMoreClickListener {
        void onViewToMore(int position);
    }

}
