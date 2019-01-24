package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.CollectionAndHistoryBean;
import com.goulala.xiayun.mycenter.model.UserIsMembersBean;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public interface IMyCenterView extends IBaseView {

    int COLLECTION_TYPE = 12; //我的收藏
    int MY_FOOTPRINT_TYPE = 13; //我的足迹

    //我的收藏和我的足迹统计
    void getCollectionAndHistoryDate(int requestType, CollectionAndHistoryBean collectionAndHistoryBean);

    //获取用户信息
    void getUserInfoMessage(UserInfo userInfo);

    //是否是plus会员
    void usersAreNotMembers(UserIsMembersBean userIsMembersBean);
}
