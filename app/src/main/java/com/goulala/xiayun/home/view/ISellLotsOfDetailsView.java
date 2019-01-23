package com.goulala.xiayun.home.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.home.model.HomeValueSellingBean;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public interface ISellLotsOfDetailsView extends IBaseView {


    void getActivityDateList(List<HomeValueSellingBean> activityDateList);

    void getTheTotalNumberOfShopCar(int number);
}
