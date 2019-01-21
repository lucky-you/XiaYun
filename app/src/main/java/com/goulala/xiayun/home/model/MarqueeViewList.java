package com.goulala.xiayun.home.model;

/**
 * @author : Z_B
 * @date : 2018/8/14
 * @function : 垂直跑马灯的数据实体类
 */
public class MarqueeViewList {

    public String peopleImageUrl;
    public String peoplePhone;
    public String withdrawalMoney;

    public MarqueeViewList(String withdrawalMoney) {
        this.withdrawalMoney = withdrawalMoney;
    }
}
