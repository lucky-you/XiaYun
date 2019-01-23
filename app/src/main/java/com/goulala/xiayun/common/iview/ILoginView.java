package com.goulala.xiayun.common.iview;

import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.IBaseView;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public interface ILoginView extends IBaseView {

    /**
     * 登录成功
     */
    void LoginSuccess(UserInfo userInfo, String message);

    /**
     * 登录失败
     */
    void LoginFailed(int code, String message);
}
