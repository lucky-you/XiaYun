package com.goulala.xiayun.mycenter.model;

import com.google.gson.annotations.SerializedName;

/**
 * author      : Z_B
 * date       : 2018/9/11
 * function  : 收货地址的model
 */
public class ShoppingAddressList {


    /**
     * id : 7
     * user_id : 23
     * name : 周彬
     * mobile : 13677197786
     * province_id : 19
     * city_id : 19
     * area_id : 20
     * address : 光谷天地一号楼1208室
     * unit :
     * postcode : 0000000
     * switch : 1
     * createtime : 0
     * updatetime : 1536635267
     * province : 天津
     * city : 天津
     * area : 天津市
     */

    private int id;
    private int user_id;
    private String name;
    private String mobile;
    private int province_id;
    private int city_id;
    private int area_id;
    private String address;
    private String unit;
    private String postcode;
    @SerializedName("switch")
    private int switchX;
    private int createtime;
    private int updatetime;
    private String province;
    private String city;
    private String area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getSwitchX() {
        return switchX;
    }

    public void setSwitchX(int switchX) {
        this.switchX = switchX;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
