package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.ApplyRefundDetailsDate;
import com.goulala.xiayun.mycenter.model.BankCardList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 退款--退货公用
 */
public interface IDetailsOfTheRefundView extends IBaseView {

    //获取售后详情
    void getApplyRefundDetailsSuccess(ApplyRefundDetailsDate applyRefundDetailsDate);

    //撤销申请--继续提交成功
    void cancelTheApplicationOrToSubmitSuccess(int requestType, String message);

    //获取物流公司
    void accessLogisticsCompanySuccess(List<BankCardList> companyList);

}
