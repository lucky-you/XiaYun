package com.goulala.xiayun.mycenter.model;

/**
 * author      : Z_B
 * date       : 2018/9/28
 * function  : 我的收藏和我的足迹记录
 */
public class CollectionAndHistoryBean {


    /**
     * favorite_count : 2
     * history_count : 2
     */

    private int favorite_count;
    private int history_count;

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getHistory_count() {
        return history_count;
    }

    public void setHistory_count(int history_count) {
        this.history_count = history_count;
    }
}
