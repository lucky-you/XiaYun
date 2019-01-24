package com.goulala.xiayun.shopcar.view;

import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.home.model.GoodMessage;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;
import com.goulala.xiayun.shopcar.model.OrderMessage;
import com.goulala.xiayun.shopcar.model.ShopCarBaseDate;
import com.goulala.xiayun.wxapi.WxPayReqInfo;

import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/16
 * function : 确认订单的view
 */
public interface IMakeSureTheOrderView extends IBaseView {






    //获取收货地址成功
    void getShoppingAddressSuccess(List<ShoppingAddressList> shoppingAddressLists);

    //获取订单信息成功
    void getOrderMessageOrSubmitOrderSuccess(int requestType, OrderMessage orderMessage);

    //余额支付--支付宝支付
    void useBalanceOrAliPayPaymentSuccess(int requestType, String orderMessage);

    //余额支付失败--支付宝支付失败
    void useBalanceOrAliPayPaymentFailed(int requestType, String message);

    //微信支付
    void useWeChatPaymentSuccess(WxPayReqInfo wxPayReqInfo);

    //微信支付失败
    void useWeChatPaymentFailed(String message);

    //是否设置了用户密码
    void checkPasswordExist(boolean isExist);

    //获取用余额
    void getAccountBalanceSuccess(AccountBalance balance);
}
