package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.CollectGoodMessage;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 我的收藏和我的足迹公用的view
 */
public interface IMyCollectionOrMyFootprintView extends IBaseView {


    void getListSuccess(CollectGoodMessage goodMessages);

    void deleteGoodSuccess(String message);
}
