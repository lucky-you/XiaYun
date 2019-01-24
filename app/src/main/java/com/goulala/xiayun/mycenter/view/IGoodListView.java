package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.AllOrderListMessage;
import com.goulala.xiayun.wxapi.WxPayReqInfo;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 商品列表的view
 */
public interface IGoodListView extends IBaseView {

    //获取订单列表
    void getOrderListSuccess(AllOrderListMessage orderMessage);

    //是否设置了用户密码
    void checkPasswordExist(boolean isExist);

    //获取用余额
    void getAccountBalanceSuccess(AccountBalance balance);

    //微信支付
    void useWeChatPaymentSuccess(WxPayReqInfo wxPayReqInfo);

    //余额支付/支付宝支付
    void useBalanceOrAliPayPaymentSuccess(int requestType, String orderMessage);


    //支付失败
    void userPaymentFailed(int request, String message);

    /**
     * 取消订单，删除订单，确认收货
     */
    void cancelOrDeleteOrderSuccess(int requestType, String message);

}
