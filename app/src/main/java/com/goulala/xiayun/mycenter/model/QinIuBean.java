package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/10/10
 * function  : 七牛云的配置信息
 */
public class QinIuBean {


    /**
     * app_key : z3CAQe9vF3Me1F_VdIVzBcgPAgdlRz60eUTvF6Ix
     * secret_key : t0gwBDTvBC8VcHg8BzFGCv_568gJsy2ZZ1P0AsiN
     * bucket : goulala-xiayun-develop
     * uploadurl : https://upload.qiniup.com
     * cdnurl : http://xyfile.nacy.cc
     * notifyenabled : 0
     * notifyurl : http://www.yoursite.com/addons/qiniu/index/notify
     * savekey : /uploads/$(year)$(mon)$(day)/$(etag)$(ext)
     * expire : 600
     * maxsize : 10M
     * mimetype : png
     * multiple : 0
     * token : z3CAQe9vF3Me1F_VdIVzBcgPAgdlRz60eUTvF6Ix:yc_ep_HV3zR2J3dXg5hRQHg8i34=:eyJzY29wZSI6ImdvdWxhbGEteGlheXVuLWRldmVsb3AiLCJkZWFkbGluZSI6MTUzOTE0MzE5M30=
     */

    private String app_key;
    private String secret_key;
    private String bucket;
    private String uploadurl;
    private String cdnurl;
    private String notifyenabled;
    private String notifyurl;
    private String savekey;
    private String expire;
    private String maxsize;
    private String mimetype;
    private String multiple;
    private String token;

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUploadurl() {
        return uploadurl;
    }

    public void setUploadurl(String uploadurl) {
        this.uploadurl = uploadurl;
    }

    public String getCdnurl() {
        return cdnurl;
    }

    public void setCdnurl(String cdnurl) {
        this.cdnurl = cdnurl;
    }

    public String getNotifyenabled() {
        return notifyenabled;
    }

    public void setNotifyenabled(String notifyenabled) {
        this.notifyenabled = notifyenabled;
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    public String getSavekey() {
        return savekey;
    }

    public void setSavekey(String savekey) {
        this.savekey = savekey;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(String maxsize) {
        this.maxsize = maxsize;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
