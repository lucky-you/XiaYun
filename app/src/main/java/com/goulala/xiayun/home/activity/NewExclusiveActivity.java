package com.goulala.xiayun.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.LogUtils;
import com.goulala.xiayun.common.utils.SizeUtils;
import com.goulala.xiayun.common.widget.DividerGridItemDecoration;
import com.goulala.xiayun.common.widget.VipSpacesItemDecoration;
import com.goulala.xiayun.home.adapter.NewExclusiveAdapter;
import com.goulala.xiayun.home.model.CoupleCouponsBean;
import com.goulala.xiayun.home.model.CouponsList;
import com.goulala.xiayun.home.presenter.NewExclusivePresenter;
import com.goulala.xiayun.home.view.INewExclusiveView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新人专享
 */
public class NewExclusiveActivity extends BaseMvpActivity<NewExclusivePresenter> implements INewExclusiveView {

    RecyclerView newExclusiveRecyclerView;
    private NewExclusiveAdapter newExclusiveAdapter;
    private List<CouponsList> couponList;

    public static void start(Context context) {
        Intent intent = new Intent(context, NewExclusiveActivity.class);
        context.startActivity(intent);

    }


    @Override
    protected NewExclusivePresenter createPresenter() {
        return new NewExclusivePresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_new_exclusive;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.New_exclusive))
                .setRightImage(R.drawable.ic_np_icon_refresh)
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCouponDate();
                    }
                });
        newExclusiveRecyclerView = get(R.id.new_Exclusive_RecyclerView);
        getCouponDate();

    }

    /**
     * 获取优惠券的列表
     */
    private void getCouponDate() {
        Map<String, String> couponParam = new HashMap<>();
        couponParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.NEW_COUPLE_COUPONS_LIST_URL);
        couponParam.put(ApiParam.PAGE_KEY, "1");
        couponParam.put(ApiParam.SIZE_KEY, "999");
        String couponParamJson = JsonUtils.toJson(couponParam);
        LogUtils.showLog(userToken, couponParamJson);
        mvpPresenter.getCouponDateList(userToken, couponParamJson);

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        newExclusiveRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        newExclusiveRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, mContext.getResources().getColor(R.color.white), 7));
        newExclusiveRecyclerView.addItemDecoration(new VipSpacesItemDecoration(SizeUtils.dp2px(15), SizeUtils.dp2px(15), SizeUtils.dp2px(15), SizeUtils.dp2px(15)));
        newExclusiveAdapter = new NewExclusiveAdapter(couponList);
        newExclusiveRecyclerView.setAdapter(newExclusiveAdapter);
        EmptyViewUtils.bindEmptyView(mContext, newExclusiveAdapter, mContext.getString(R.string.No_coupons));

        newExclusiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (1 == newExclusiveAdapter.getItem(position).getGet_status()) {
                    int coupon_id = newExclusiveAdapter.getItem(position).getId();
                    Map<String, String> couponParam = new HashMap<>();
                    couponParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_THE_COUPONS_URL);
                    couponParam.put(ApiParam.COUPON_ID, String.valueOf(coupon_id));
                    String couponParamJson = JsonUtils.toJson(couponParam);
                    LogUtils.showLog(userToken, couponParamJson);
                    if (TextUtils.isEmpty(userToken)) {
//                        LoginActivity.start(mContext);
                    } else {
                        mvpPresenter.getTheCoupon(userToken, couponParamJson);
                    }
                } else {
                    showToast(mContext.getString(R.string.Coupons_received));
                }
            }
        });
    }

    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void getCouponDateListSuccess(CoupleCouponsBean coupleCouponsBean) {
        if (coupleCouponsBean != null) {
            couponList = coupleCouponsBean.getData();
            if (couponList != null && couponList.size() > 0) {
                newExclusiveAdapter.setNewData(couponList);
            }
        }
    }

    @Override
    public void getTheCouponSuccess(String message) {
        showToast(message);
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
    }
}
