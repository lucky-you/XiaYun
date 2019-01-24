package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.QinIuBean;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public interface IMyProfileView extends IBaseView {

    /**
     * 获取用户信息
     */
    void getUserInfoMessage(UserInfo userInfo);

    /**
     * 修改信息成功
     */
    void modifySuccess(String message);
    /**
     * 获取七牛云的配置信息
     */
    void getQinIuSetMessageSuccess(QinIuBean message);
}
