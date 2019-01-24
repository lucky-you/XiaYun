package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.LogisticsMessageBean;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public interface IMessageDetailsView extends IBaseView {

    void getMessageDetails(LogisticsMessageBean logisticsMessageBean);

    void readMessageSuccess(String isReadCode);
}
