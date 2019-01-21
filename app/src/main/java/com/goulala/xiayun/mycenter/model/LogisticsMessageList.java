package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/11/1
 * function  :
 */
public class LogisticsMessageList {


    /**
     * id : 15
     * topic : 2
     * name : 订单已发货
     * memo : 订单35091966400262673744已发货 2018年10月31日
     * attachtype : 2
     * attachno : 35091966400262673744
     * publisher_id : 1
     * subscriber_id : 23
     * weigh : 15
     * createtime : 1540969857
     * updatetime : 1540969857
     * readtime : 0
     * attach : {"express_number":"3242343242","item_img":"http://xyfile.nacy.cc/uploads/20180828/Fl7ZSZRmGSEvn4LMUXaGllER-Lvn.png?imageView/2/w/100/h/100","name":"洪湖香十三香小龙香虾"}
     */

    private int id;
    private int topic;
    private String name;
    private String memo;
    private int attachtype;
    private String attachno;
    private int publisher_id;
    private String subscriber_id;
    private int weigh;
    private int createtime;
    private int updatetime;
    private int readtime;
    private AttachBean attach;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getAttachtype() {
        return attachtype;
    }

    public void setAttachtype(int attachtype) {
        this.attachtype = attachtype;
    }

    public String getAttachno() {
        return attachno;
    }

    public void setAttachno(String attachno) {
        this.attachno = attachno;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
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

    public int getReadtime() {
        return readtime;
    }

    public void setReadtime(int readtime) {
        this.readtime = readtime;
    }

    public AttachBean getAttach() {
        return attach;
    }

    public void setAttach(AttachBean attach) {
        this.attach = attach;
    }
}
