package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.LogisticsDetailsBean;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public interface ILogisticsDetailsView extends IBaseView {

    void getLogisticsDetailsSuccess(LogisticsDetailsBean logisticsDetailsBean);
}
