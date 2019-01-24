package com.goulala.xiayun.mycenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpFragment;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.mycenter.adapter.MyCouponsAdapter;
import com.goulala.xiayun.mycenter.model.CouponList;
import com.goulala.xiayun.mycenter.model.CouponMessage;
import com.goulala.xiayun.mycenter.presenter.MyCouponsPresenter;
import com.goulala.xiayun.mycenter.view.IMyCouponsView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : Z_B
 * date : 2018/8/18
 * function : 我的优惠券
 */
public class MyCouponsFragment extends BaseMvpFragment<MyCouponsPresenter> implements IMyCouponsView,
        BaseQuickAdapter.OnItemClickListener {

    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private MyCouponsAdapter myCouponsAdapter;
    private List<CouponList> couponLists = new ArrayList<>();
    private int fragmentType; //根据下标来区分是可用优惠券界面，还是不可用优惠券界面
    private int classFormType; //根据classType来区分是从个人中心点击进来的，还是从确认下单点击进来的
    private String payMoney;

    public static Fragment newInstance(int classType, int classFormType, String money) {
        MyCouponsFragment fragment = new MyCouponsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.INDEX, classType);
        bundle.putInt(ConstantValue.TYPE, classFormType);
        bundle.putString(ConstantValue.MONEY, money);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected MyCouponsPresenter createPresenter() {
        return new MyCouponsPresenter(this);
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
        fragmentType = getArguments().getInt(ConstantValue.INDEX);
        classFormType = getArguments().getInt(ConstantValue.TYPE);
        payMoney = getArguments().getString(ConstantValue.MONEY);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        getDate();
        if (fragmentType == 0) {
            myCouponsAdapter = new MyCouponsAdapter(couponLists, ConstantValue.COUPONS_AVAILABLE_TYPE);
        } else {
            myCouponsAdapter = new MyCouponsAdapter(couponLists, ConstantValue.NOT_COUPONS_AVAILABLE_TYPE);
        }
        myCouponsAdapter.setOnItemClickListener(this);
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        smartRecyclerView.setAdapter(myCouponsAdapter);
        EmptyViewUtils.bindEmptyView(mContext, myCouponsAdapter, mContext.getString(R.string.No_coupons));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDate();
                refreshLayout.finishRefresh();
            }
        });
    }

    private void getDate() {
        getCouponList(fragmentType + 1, payMoney);
    }


    /**
     * 获取优惠券的信息
     */
    private void getCouponList(int type, String amount) {
        Map<String, String> couponListParam = new HashMap<>();
        couponListParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_COUPON_LIST_URL);
        couponListParam.put(ApiParam.CATEGORY, String.valueOf(type));
        couponListParam.put(ApiParam.AMOUNT, amount);
        String couponListParamJson = JsonUtils.toJson(couponListParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getCouponMessage(userToken, couponListParamJson);
        }
    }

    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void getCouponMessageSuccess(CouponMessage couponMessage) {
        if (couponMessage != null) {
            int couponSize = couponMessage.getTotal();
            couponLists = couponMessage.getData();
            if (couponLists != null && couponLists.size() > 0) {
                myCouponsAdapter.setNewData(couponLists);
            }
            if (fragmentType == 0) {
                EventBus.getDefault().post(new Notice(ConstantValue.COUPONS_AVAILABLE_TYPE, couponSize));
            } else {
                EventBus.getDefault().post(new Notice(ConstantValue.NOT_COUPONS_AVAILABLE_TYPE, couponSize));
            }
        }
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        int couponId = myCouponsAdapter.getItem(position).getId();
        String FullPriceReduction = myCouponsAdapter.getItem(position).getUse_trade_amount(); //达到满减的金额
        String PreferentialPrice = myCouponsAdapter.getItem(position).getDiscount_amount(); //满减的金额
        if (ConstantValue.CHOOSE_COUPONS_RETURN_TO_ORDER_ACTIVITY == classFormType) {
            //是从确认订单进来的，然后还要判断优惠券可用不可用，可用的才finish
            if (Double.parseDouble(payMoney) >= Double.parseDouble(FullPriceReduction)) {
                //支付金额达到了优惠的金额
                Intent intent = new Intent();
                intent.putExtra(ConstantValue.ID, String.valueOf(couponId));
                intent.putExtra(ConstantValue.FULL_PRICE, FullPriceReduction);
                intent.putExtra(ConstantValue.PREFERENTIAL_PRICE, PreferentialPrice);
                getActivity().setResult(ConstantValue.CHOOSE_COUPONS_RETURN_TO_ORDER_ACTIVITY, intent);
                getActivity().finish();
            } else {
                showToast(mContext.getString(R.string.The_amount_did_not_meet_the_coupon_conditions));
            }

        }
    }
}
