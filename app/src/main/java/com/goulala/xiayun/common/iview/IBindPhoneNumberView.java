package com.goulala.xiayun.common.iview;

import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.IBaseView;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public interface IBindPhoneNumberView extends IBaseView {

    /**
     * 获取验证码成功
     */
    void getCodeSuccess(String message);

    /**
     * 绑定手机号码成功
     */
    void loginOrBindPhoneNumberSuccess(int requestType, UserInfo userInfo, String message);


}
