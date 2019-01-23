package com.goulala.xiayun.home.view;

import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.home.model.DefaultSearchKeyWords;
import com.goulala.xiayun.home.model.GoodMessage;
import com.goulala.xiayun.home.model.HomeBannerDate;
import com.goulala.xiayun.home.model.HomeValueSellingBean;
import com.goulala.xiayun.home.model.HotStyleSecondsKill;
import com.goulala.xiayun.home.model.MemberDiscountBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public interface IHomeFragmentView extends IBaseView {
    //默认关键字
    void getDefaultSearch(DefaultSearchKeyWords historyList);

    //首页banner的数据
    void getHomeBannerSuccess(List<HomeBannerDate> bannerDate, String bannerType);

    //超值热卖
    void getHomeValueSellingGood(List<HomeValueSellingBean> homeValueSellingBeanList);

    //爆品秒杀
    void getHotStyleSecondsKillSuccess(ArrayList<HotStyleSecondsKill> hotStyleSecondsKills);

    //品质优选
    void getHomeQualityTheOptimization(GoodMessage homeValueSellingBean);

    //会员优惠榜单
    void getMembershipList(List<MemberDiscountBean> memberDiscountBeanList);

    //检查token是否过期
    void checkUserTokenIsOverdue(UserInfo userToken);
}
