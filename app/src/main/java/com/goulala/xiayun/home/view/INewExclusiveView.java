package com.goulala.xiayun.home.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.home.model.CoupleCouponsBean;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public interface INewExclusiveView extends IBaseView {

    //获取新人优惠券
    void getCouponDateListSuccess(CoupleCouponsBean coupleCouponsBean);

    //领取优惠券
    void getTheCouponSuccess(String message);

}
