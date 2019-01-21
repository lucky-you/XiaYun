package com.goulala.xiayun.home.model;

/**
 * @author : Z_B
 * @date : 2018/8/28
 * @function : 默认搜索的关键字
 */
public class DefaultSearchKeyWords {


    /**
     * name : aaaa
     * hit : 4
     */

    private String name;
    private String hit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    @Override
    public String toString() {
        return "DefaultSearchKeyWords{" +
                "name='" + name + '\'' +
                ", hit='" + hit + '\'' +
                '}';
    }
}
