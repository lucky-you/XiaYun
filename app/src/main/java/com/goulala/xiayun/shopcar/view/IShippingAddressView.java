package com.goulala.xiayun.shopcar.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :  添加和选择公用
 */
public interface IShippingAddressView extends IBaseView {

    int SET_DEFAULT_ADDRESS_TYPE = 1;// 设置默认
    int DELETE_ADDRESS_TYPE = 2;// 删除地址
    int THE_TYPE_OF_ADD_SHOPPING_ADDRESS = 3; //添加
    int THE_TYPE_OF_EDIT_SHOPPING_ADDRESS = 4; //修改

    //收货地址
    void getShoppingAddressListSuccess(List<ShoppingAddressList> shoppingAddressLists);

    /**
     * 设置默认的收货地址
     * 删除收货地址
     * 添加地址成功
     * 修改地址成功
     */
    void theOperationOfAddress(int requestType, String message);


}
