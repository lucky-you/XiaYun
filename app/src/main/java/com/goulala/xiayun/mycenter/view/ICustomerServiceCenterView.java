package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.ServiceCenterList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public interface ICustomerServiceCenterView extends IBaseView {

    void getServiceListSuccess(List<ServiceCenterList> serviceCenterLists);
}
