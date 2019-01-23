package com.goulala.xiayun.home.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.home.model.GoodMessage;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public interface ISearchResultView extends IBaseView {

    //搜索的结果
    void loadSearchResultList(GoodMessage goodMessage);

    void getTheTotalNumberOfShopCar(int number);
}
