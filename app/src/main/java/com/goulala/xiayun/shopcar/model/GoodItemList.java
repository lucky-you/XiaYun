package com.goulala.xiayun.shopcar.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author      : Z_B
 * date       : 2018/9/11
 * function  : 单个商品的基本信息，获取订单信息，以及提交订单公用的数据结构
 * 0--------0 最终都需要转成Json传给服务器
 */
public class GoodItemList implements Parcelable {

    private int item_id; //商品id
    private int item_num; // 商品数量
    private int merchant_id; // 店铺id
    private String remark;   //用户留言
    private int cart_id;  //购物车的id


    public GoodItemList(int item_id, int item_num, int merchant_id, String remark) {
        this.item_id = item_id;
        this.item_num = item_num;
        this.merchant_id = merchant_id;
        this.remark = remark;
    }

    public GoodItemList(int item_id, int item_num, int merchant_id, int cart_id) {
        this.item_id = item_id;
        this.item_num = item_num;
        this.merchant_id = merchant_id;
        this.cart_id = cart_id;
    }

    protected GoodItemList(Parcel in) {
        item_id = in.readInt();
        item_num = in.readInt();
        merchant_id = in.readInt();
        remark = in.readString();
        cart_id = in.readInt();
    }

    public static final Creator<GoodItemList> CREATOR = new Creator<GoodItemList>() {
        @Override
        public GoodItemList createFromParcel(Parcel in) {
            return new GoodItemList(in);
        }

        @Override
        public GoodItemList[] newArray(int size) {
            return new GoodItemList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(item_id);
        parcel.writeInt(item_num);
        parcel.writeInt(merchant_id);
        parcel.writeString(remark);
        parcel.writeInt(cart_id);
    }


    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getItem_num() {
        return item_num;
    }

    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public static Creator<GoodItemList> getCREATOR() {
        return CREATOR;
    }
}
