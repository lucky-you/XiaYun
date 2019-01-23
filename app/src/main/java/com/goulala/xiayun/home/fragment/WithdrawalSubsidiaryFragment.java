package com.goulala.xiayun.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.BaseFragment;
import com.goulala.xiayun.common.utils.ConstantValue;
import com.goulala.xiayun.home.adapter.WithdrawalSubsidiaryAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * author : Z_B
 * date : 2018/8/13
 * function : 签到明细和提现明细的fragment
 */
public class WithdrawalSubsidiaryFragment extends BaseFragment {

    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private WithdrawalSubsidiaryAdapter withdrawalSubsidiaryAdapter;
    private int fragmentType;

    public static WithdrawalSubsidiaryFragment newInstance(int classType) {
        WithdrawalSubsidiaryFragment fragment = new WithdrawalSubsidiaryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.INDEX, classType);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_smartrefresh_layout;
    }

    @Override
    public void bindViews(View contentView) {
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));

//        fragmentType = getArguments().getInt(ConstantValue.INDEX);
//        List<String> mDateList = Arrays.asList("5.5", "7.8", "8.3", "2.6", "9.7", "5.8", "8.3", "2.6", "9.7", "5.8");
//        if (fragmentType == 0) {
//            withdrawalSubsidiaryAdapter = new WithdrawalSubsidiaryAdapter(mDateList, WithdrawalSubsidiaryAdapter.FRAGMENT_ADAPTER_TYPE_ONE);
//        } else {
//            withdrawalSubsidiaryAdapter = new WithdrawalSubsidiaryAdapter(mDateList, WithdrawalSubsidiaryAdapter.FRAGMENT_ADAPTER_TYPE_TWO);
//        }
//        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        smartRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getColors(R.color.color_ddd), 1));
//        smartRecyclerView.setAdapter(withdrawalSubsidiaryAdapter);
    }


    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void setClickListener(View view) {

    }


}
