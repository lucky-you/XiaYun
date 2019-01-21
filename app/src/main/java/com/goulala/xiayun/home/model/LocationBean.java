package com.goulala.xiayun.home.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author      : Z_B
 * date       : 2018/10/19
 * function  : 定位的信息-->使用高德SDK定位成功之后，保存下来
 */
@Entity
public class LocationBean {

    @Id(autoincrement = true)
    private Long id;
    private double Longitude;
    private double Latitude;
    private String province;
    private String city;
    private String area;


    public LocationBean(double longitude, double latitude, String province, String city, String area) {
        Longitude = longitude;
        Latitude = latitude;
        this.province = province;
        this.city = city;
        this.area = area;
    }

    @Generated(hash = 1325748771)
    public LocationBean(Long id, double Longitude, double Latitude, String province,
                        String city, String area) {
        this.id = id;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.province = province;
        this.city = city;
        this.area = area;
    }

    @Generated(hash = 516751439)
    public LocationBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLongitude() {
        return this.Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
