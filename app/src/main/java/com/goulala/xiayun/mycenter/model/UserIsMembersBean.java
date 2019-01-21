package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/9/29
 * function  : 用户是不是plus的标识
 */
public class UserIsMembersBean {


    /**
     * id : 10
     * pay_no : 36023659800490189264
     * open_price : 200.00
     * status : 1
     * start_time : 1538134956
     * end_time : 1569670956
     * createtime : 1538134956
     * updatetime : 1538134956
     * user_id : 23
     * type : 1
     * start_time_text : 2018-09-28 19:42:36
     * end_time_text : 2019-09-28 19:42:36
     */

    private int id;
    private String pay_no;
    private String open_price;
    private int status;
    private long start_time;
    private long end_time;
    private long createtime;
    private long updatetime;
    private int user_id;
    private int type;
    private String start_time_text;
    private String end_time_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getOpen_price() {
        return open_price;
    }

    public void setOpen_price(String open_price) {
        this.open_price = open_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStart_time_text() {
        return start_time_text;
    }

    public void setStart_time_text(String start_time_text) {
        this.start_time_text = start_time_text;
    }

    public String getEnd_time_text() {
        return end_time_text;
    }

    public void setEnd_time_text(String end_time_text) {
        this.end_time_text = end_time_text;
    }
}
