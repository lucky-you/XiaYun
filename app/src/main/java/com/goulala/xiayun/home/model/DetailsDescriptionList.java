package com.goulala.xiayun.home.model;

/**
 * @author : Z_B
 * @date : 2018/8/27
 * @function : 商品详情页面的详情描述（品牌，告知其，产地，规格....）
 */
public class DetailsDescriptionList {


    public String messageTitle; //标题
    public String messageDescribe; //描述

    public DetailsDescriptionList(String messageTitle, String messageDescribe) {
        this.messageTitle = messageTitle;
        this.messageDescribe = messageDescribe;
    }


}
