package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.QinIuBean;
import com.goulala.xiayun.mycenter.model.RefundMoneyDate;
import com.goulala.xiayun.mycenter.model.RefundResultDate;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 退款--退货公用的view
 */
public interface IReturnGoodApplicationView extends IBaseView {
    /**
     * 获取申请售后数据成功
     */
    void getRefundGoodMessageSuccess(RefundMoneyDate refundMoneyDate);

    /**
     * 提交申请成功
     */
    void submitApplyRefundSuccess(RefundResultDate refundResultDate);

    /**
     * 获取七牛云的配置信息
     */
    void getQinIuSetMessageSuccess(QinIuBean message);
}
