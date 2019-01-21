package com.goulala.xiayun.shopcar.model;

/**
 * author      : Z_B
 * date       : 2018/10/18
 * function  : 存储购物车选择的商品的基本信息
 */
public class SaveShopCarSelectBean {


    private int id;
    private int active_id;
    private int cell;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive_id() {
        return active_id;
    }

    public void setActive_id(int active_id) {
        this.active_id = active_id;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

}
