package com.goulala.xiayun.mycenter.callback;

import android.widget.TextView;

/**
 * author      : Z_B
 * date       : 2018/9/26
 * function  : 订单列表中订单状态的监听
 */
public interface OnOrderListStatusClickListener {

    void onStatusOneClick(int orderStatus, int position, TextView tvStatusOne,
                          String payMoney, String payOrderNumber, String shopOrderNumber,
                          String expressCompanyId, String expressNumber
    );

    void onStatusTwoClick(int orderStatus, int position, TextView tvStatusTwo,
                          String payMoney, String payOrderNumber, String shopOrderNumber,
                          String expressCompanyId, String expressNumber);

    void onStatusThreeClick(int orderStatus, int position, TextView tvStatusThree,
                            String payMoney, String payOrderNumber, String shopOrderNumber,
                            String expressCompanyId, String expressNumber);


}
