package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 获取验证码，重置验证码公用
 */
public interface IResetCommissionPaymentView extends IBaseView {


    int GET_VERIFICATION_CODE = 3; //获取验证码

    int VALIDATION_CODE = 4;    //验证验证码

    int RESET_PASSWORD = 5; //重置密码


    /**
     * 获取验证码
     * 验证code--
     * 重置密码成功
     */
    void validationCodeOrResetPasswordSuccess(int requestType, boolean isSuccess, String message);

}
