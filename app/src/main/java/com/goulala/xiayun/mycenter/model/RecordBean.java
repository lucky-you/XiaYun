package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/10/11
 * function  : 申请售后中的沟通记录
 */
public class RecordBean {

    /**
     * id : 522
     * service_no : 15077740500291507560
     * type : 40
     * account_id : 23
     * service_info : 245886857556868
     * express_company_id : 4
     * remark : 买家发货
     * images :
     * createtime : 1539314520
     * updatetime : 1539313520
     * express_company : 韵达速递
     */

    private int id;
    private String service_no;
    private int type;
    private int account_id;
    private String service_info;
    private int express_company_id;
    private String remark;
    private String images;
    private long createtime;
    private long updatetime;
    private String express_company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService_no() {
        return service_no;
    }

    public void setService_no(String service_no) {
        this.service_no = service_no;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getService_info() {
        return service_info;
    }

    public void setService_info(String service_info) {
        this.service_info = service_info;
    }

    public int getExpress_company_id() {
        return express_company_id;
    }

    public void setExpress_company_id(int express_company_id) {
        this.express_company_id = express_company_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }
}
