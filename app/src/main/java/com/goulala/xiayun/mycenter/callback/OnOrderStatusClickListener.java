package com.goulala.xiayun.mycenter.callback;

import android.widget.TextView;

/**
 * author      : Z_B
 * date       : 2018/9/17
 * function  : 订单状态的点击事件
 */
public interface OnOrderStatusClickListener {


    void onStatusOneClick(int orderStatus, int position, TextView tvStatusOne, String payMoney, String payOrderNumber);

    void onStatusTwoClick(int orderStatus, int position, TextView tvStatusTwo, String payMoney, String payOrderNumber);

    void onStatusThreeClick(int orderStatus, int position, TextView tvStatusThree, String payMoney, String payOrderNumber);


}
