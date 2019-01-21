package com.goulala.xiayun.mycenter.model;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/10/30
 * function  : 物流详情
 */
public class LogisticsDetailsBean {


    /**
     * message : ok
     * nu : 3380838656252
     * ischeck : 1
     * condition : F00
     * com : shentong
     * status : 200
     * state : 3
     * data : [{"time":"2018-10-21 17:19:57","ftime":"2018-10-21 17:19:57","context":"【上海市】已签收,签收人是草签.申行天下义当先,江湖好评是良缘.记得给申通小件员五星好评哦！，如有疑问请联系派件员七号片区716：18917829903/021-36014518 ，感谢使用申通快递，期待再次为您服务"},{"time":"2018-10-21 10:42:38","ftime":"2018-10-21 10:42:38","context":"【上海市】已签收,签收人是涂.申行天下义当先,江湖好评是良缘.记得给申通小件员五星好评哦！，如有疑问请联系派件员七号片区716：18917829903/021-36014518 ，感谢使用申通快递，期待再次为您服务"},{"time":"2018-10-21 08:08:19","ftime":"2018-10-21 08:08:19","context":"【上海罗泾公司】已收入"},{"time":"2018-10-21 08:08:19","ftime":"2018-10-21 08:08:19","context":"【上海市】上海罗泾公司派件员 七号片区716 18917829903/021-36014518正在为您派件"},{"time":"2018-10-21 06:30:11","ftime":"2018-10-21 06:30:11","context":"快件已到达【上海罗泾公司】 扫描员是【七号片区】"},{"time":"2018-10-21 01:44:57","ftime":"2018-10-21 01:44:57","context":"快件已到达【上海罗泾公司】 扫描员是【到件（6）】"},{"time":"2018-10-21 01:42:57","ftime":"2018-10-21 01:42:57","context":"由【上海中转部】发往【上海罗泾公司】"},{"time":"2018-10-21 01:13:31","ftime":"2018-10-21 01:13:31","context":"快件已到达【上海中转部】 扫描员是【自动分拣】"},{"time":"2018-10-20 22:18:18","ftime":"2018-10-20 22:18:18","context":"由【江苏南通转运中心】发往【上海中转部】"},{"time":"2018-10-20 19:33:56","ftime":"2018-10-20 19:33:56","context":"由【江苏如皋公司】发往【江苏南通转运中心】"},{"time":"2018-10-20 19:31:20","ftime":"2018-10-20 19:31:20","context":"由【江苏如皋公司】发往【江苏南通中转部】"},{"time":"2018-10-20 19:31:20","ftime":"2018-10-20 19:31:20","context":"【江苏如皋公司】正在进行【装袋】扫描"},{"time":"2018-10-20 18:17:20","ftime":"2018-10-20 18:17:20","context":"【江苏如皋公司】的收件员【总公司】已收件"}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private String logoimage;
    private String name;
    private String contactmobile;
    private List<LogisticsDetailsList> data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLogoimage() {
        return logoimage;
    }

    public void setLogoimage(String logoimage) {
        this.logoimage = logoimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactmobile() {
        return contactmobile;
    }

    public void setContactmobile(String contactmobile) {
        this.contactmobile = contactmobile;
    }

    public List<LogisticsDetailsList> getData() {
        return data;
    }

    public void setData(List<LogisticsDetailsList> data) {
        this.data = data;
    }
}
