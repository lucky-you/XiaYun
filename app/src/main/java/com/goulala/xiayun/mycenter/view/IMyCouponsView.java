package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.CouponMessage;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public interface IMyCouponsView extends IBaseView {

    //获取优惠券
    void getCouponMessageSuccess(CouponMessage couponMessage);
}
