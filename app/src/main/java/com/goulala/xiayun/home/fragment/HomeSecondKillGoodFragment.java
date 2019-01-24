package com.goulala.xiayun.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseFragment;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.home.activity.HomeGoodsDetailsActivity;
import com.goulala.xiayun.home.adapter.HomeSecondKillGoodAdapter;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.home.model.HotStyleSecondsKill;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/7
 * function : 秒杀商品的fragment
 */
public class HomeSecondKillGoodFragment extends BaseFragment {

    private RecyclerView secondKillGoodRecyclerView;
    private HomeSecondKillGoodAdapter homeSecondKillGoodAdapter;
    private int typeIndex;
    private ArrayList<HotStyleSecondsKill> hotStyleSecondsKillList = new ArrayList<>();
    private List<GoodItemMessage> goodItemMessageList = new ArrayList<>();

    public static HomeSecondKillGoodFragment newInstance(int index, ArrayList<HotStyleSecondsKill> hotStyleSecondsKillList) {
        HomeSecondKillGoodFragment homeSecondKillGoodFragment = new HomeSecondKillGoodFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.INDEX, index);
        bundle.putParcelableArrayList(ConstantValue.LIST, hotStyleSecondsKillList);
        homeSecondKillGoodFragment.setArguments(bundle);
        return homeSecondKillGoodFragment;
    }


    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_recyclerview_layout;
    }

    @Override
    public void bindViews(View contentView) {
        secondKillGoodRecyclerView = get(R.id.public_RecyclerView);
        typeIndex = getArguments().getInt(ConstantValue.INDEX);
        hotStyleSecondsKillList = getArguments().getParcelableArrayList(ConstantValue.LIST);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        goodItemMessageList = hotStyleSecondsKillList.get(typeIndex).getData();
        homeSecondKillGoodAdapter = new HomeSecondKillGoodAdapter(goodItemMessageList);
        secondKillGoodRecyclerView.setAdapter(homeSecondKillGoodAdapter);
        secondKillGoodRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        secondKillGoodRecyclerView.setNestedScrollingEnabled(false);
        secondKillGoodRecyclerView.setHasFixedSize(true);
        homeSecondKillGoodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int goodId = homeSecondKillGoodAdapter.getItem(position).getId();
                HomeGoodsDetailsActivity.start(mContext, goodId, ConstantValue.THE_TYPE_OF_OTHER);

            }
        });
    }

    @Override
    public void setClickListener(View view) {

    }


}
