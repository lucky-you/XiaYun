package com.goulala.xiayun.wxapi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by：Z_B on 2018/4/28 10:51
 * Effect： 微信支付的实体类
 */
public class WxPayReqInfo {


    /**
     * appid : wx28a1ed75f6e246a4
     * partnerid : 1512118311
     * prepayid : wx12155559658956293b52cf491117292539
     * timestamp : 1536738959
     * noncestr : 8OgaeKIgioq6Y8zz
     * package : Sign=WXPay
     * sign : 48441441F504B6E31CB87B243336B9D1
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
