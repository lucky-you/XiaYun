package com.goulala.xiayun.mycenter.model;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/29
 * function  : 会员中心
 */
public class TheMemberCenterBean {

    public static final String OPEN_TYPE = "open";
    public static final String RENEW_TYPE = "renew";


    /**
     * type : renew  /open
     * title : 续费价格/开通价格
     * data : [{"id":12,"term":12,"open_price":"100.00","discount_open_price":"80.00","start_time":1538125826,"end_time":1540977029,"describe":"测试一下","vip_type":2,"createtime":0,"updatetime":0,"start_time_text":"2018-09-28 17:10:26","end_time_text":"2018-10-31 17:10:29"},{"id":13,"term":24,"open_price":"130.00","discount_open_price":"110.00","start_time":1538125852,"end_time":1543569053,"describe":"111","vip_type":2,"createtime":0,"updatetime":0,"start_time_text":"2018-09-28 17:10:52","end_time_text":"2018-11-30 17:10:53"},{"id":14,"term":36,"open_price":"300.00","discount_open_price":"80.00","start_time":1538125873,"end_time":1548925875,"describe":"测试测试","vip_type":2,"createtime":0,"updatetime":0,"start_time_text":"2018-09-28 17:11:13","end_time_text":"2019-01-31 17:11:15"}]
     */

    private String type;
    private String title;
    private List<TheMemberCenterList> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TheMemberCenterList> getData() {
        return data;
    }

    public void setData(List<TheMemberCenterList> data) {
        this.data = data;
    }
}
