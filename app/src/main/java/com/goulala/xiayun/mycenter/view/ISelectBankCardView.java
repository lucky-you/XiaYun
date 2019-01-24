package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.BankCardList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  : 选择银行
 */
public interface ISelectBankCardView extends IBaseView {

    void getBankCardListSuccess(List<BankCardList> bankCardLists);
}
