package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.PaymentDetailsBean;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public interface IMyCommissionView extends IBaseView {

    //获取用余额
    void getAccountBalanceSuccess(AccountBalance balance);

    //收支明细
    void paymentDetailsSuccess(PaymentDetailsBean paymentDetailsBean);
}
