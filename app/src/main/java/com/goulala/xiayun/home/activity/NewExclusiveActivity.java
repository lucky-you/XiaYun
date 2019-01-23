package com.goulala.xiayun.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.utils.CommonUtil;
import com.goulala.utils.JsonUtils;
import com.goulala.view.LoadDialog;
import com.goulala.xiayun.Basemvp.BaseMVPActivity;
import com.goulala.xiayun.R;
import com.goulala.xiayun.base.ApiParam;
import com.goulala.xiayun.common.activity.LoginActivity;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.home.adapter.NewExclusiveAdapter;
import com.goulala.xiayun.home.contact.NewExclusiveCouponContact;
import com.goulala.xiayun.home.model.CoupleCouponsBean;
import com.goulala.xiayun.home.model.CouponsList;
import com.goulala.xiayun.home.presenter.NewExclusiveCouponPresenter;
import com.goulala.xiayun.mycenter.decoration.VipSpacesItemDecoration;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新人专享
 */
public class NewExclusiveActivity extends BaseMVPActivity<NewExclusiveCouponContact.presenter> implements NewExclusiveCouponContact.view {

    RecyclerView newExclusiveRecyclerView;
    private NewExclusiveAdapter newExclusiveAdapter;
    private List<CouponsList> couponList;

    public static void start(Context context) {
        Intent intent = new Intent(context, NewExclusiveActivity.class);
        context.startActivity(intent);

    }


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_new_exclusive);
        newExclusiveRecyclerView = get(R.id.new_Exclusive_RecyclerView);

    }

    @Override
    protected void bindViews() {
        initTitle(mContext.getString(R.string.New_exclusive))
                .setRightImage(R.drawable.ic_np_icon_refresh)
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCouponDate();
                    }
                });
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
        Logger.d("xy", couponParamJson + "token=" + userToken);
        presenter.getCouponDateList(mContext, userToken, couponParamJson);

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        newExclusiveRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        // newExclusiveRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, mContext.getResources().getColor(R.color.white), 7));
        newExclusiveRecyclerView.addItemDecoration(new VipSpacesItemDecoration(CommonUtil.dip2px(mContext,15),CommonUtil.dip2px(mContext,15),CommonUtil.dip2px(mContext,15),CommonUtil.dip2px(mContext,15)));
        newExclusiveAdapter = new NewExclusiveAdapter(couponList);
        newExclusiveRecyclerView.setAdapter(newExclusiveAdapter);
        EmptyViewUtils.bindEmptyView(mContext, newExclusiveAdapter, mContext.getString(R.string.No_coupons));

    }

    @Override
    protected void setListener() {
        newExclusiveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (1 == newExclusiveAdapter.getItem(position).getGet_status()) {
                    int coupon_id = newExclusiveAdapter.getItem(position).getId();
                    Map<String, String> couponParam = new HashMap<>();
                    couponParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_THE_COUPONS_URL);
                    couponParam.put(ApiParam.COUPON_ID, String.valueOf(coupon_id));
                    String couponParamJson = JsonUtils.toJson(couponParam);
                    Logger.d("xy", couponParamJson + "token=" + userToken);
                    if (TextUtils.isEmpty(userToken)) {
                        LoginActivity.start(mContext);
                    } else {
                        presenter.getTheCoupon(userToken, couponParamJson);
                    }
                } else {
                    showToast(mContext.getString(R.string.Coupons_received));
                }
            }
        });
    }

    @Override
    public NewExclusiveCouponContact.presenter createPresenter() {
        return new NewExclusiveCouponPresenter(this);
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
        getCouponDate();
    }

    @Override
    public void showLoadingDialog(String message) {
        LoadDialog.show(mContext, message);
    }

    @Override
    public void dismissLoadingDialog() {
        LoadDialog.dismiss(mContext);
    }

    @Override
    public void onRequestFailure(String error) {
        showToast(error);
    }
}
