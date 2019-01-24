package com.goulala.xiayun.common.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cnsunrun.alipaylibrary.alipay.AliPayUtils;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.activity.LoginActivity;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.BasePresenter;
import com.goulala.xiayun.common.mvp.MvpActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.UserUtils;
import com.goulala.xiayun.common.view.TitleBuilder;
import com.goulala.xiayun.wxapi.WeiXinPayUtils;
import com.goulala.xiayun.wxapi.WxPayReqInfo;
import com.tencent.mm.opensdk.modelpay.PayReq;


public abstract class BaseMvpActivity<P extends BasePresenter> extends MvpActivity<P> {


    protected String userToken;
    protected UserInfo userInfo;
//    protected KfStartHelper helper;

    public TitleBuilder initTitle(Object obj) {
        if (obj instanceof String) {
            return new TitleBuilder(this).setTitleText((String) obj);
        } else {
            return new TitleBuilder(this).setTitleText((int) obj);
        }
    }

    public RecyclerView initCommonRecyclerView(BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        return initCommonRecyclerView(R.id.recyclerView, adapter, decoration);
    }

    public RecyclerView initCommonRecyclerView(@IdRes int id, BaseQuickAdapter adapter, RecyclerView.ItemDecoration decoration) {
        RecyclerView recyclerView = (RecyclerView) findViewById(id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userToken = UserUtils.userToken();
        BarUtils.setStatusBarLightMode(this, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCommonUserData();
//        helper = new KfStartHelper(this);
    }

    /**
     * 是否登录
     */
    public boolean checkLogin() {
        getCommonUserData();
        if (TextUtils.isEmpty(userToken)) {
            intentToActivity(LoginActivity.class);
            return false;
        }
        return true;
    }

    /**
     * 获取用户信息
     */
    public void getCommonUserData() {
        userToken = UserUtils.userToken();
        userInfo = BaseApplication.getInstance().getUserInfo();
    }


    /**
     * 使用微信支付
     */
    protected void userWeChatPaymentOrder(WxPayReqInfo wxPayResult, WeiXinPayUtils.OnWxPayResultListener wxPayResultListener) {
        if (wxPayResult != null) {
            PayReq request = new PayReq();
            request.appId = wxPayResult.getAppid();
            request.partnerId = wxPayResult.getPartnerid();
            request.prepayId = wxPayResult.getPrepayid();
            request.packageValue = wxPayResult.getPackageX();
            request.nonceStr = wxPayResult.getNoncestr();
            request.timeStamp = wxPayResult.getTimestamp();
            request.sign = wxPayResult.getSign();
            WeiXinPayUtils.useWeChatPay(mContext, request, wxPayResultListener);

        }
    }

    /**
     * 使用支付宝支付
     */
    protected void userAliPayPaymentOrder(String orderInfo, AliPayUtils.OnAlipayListener listener) {
        if (!TextUtils.isEmpty(orderInfo)) {
            AliPayUtils aliPayUtils = new AliPayUtils(this);
            aliPayUtils.requestPayFromServiceSide(orderInfo);
            aliPayUtils.setPayListener(listener);

        }

    }

}
