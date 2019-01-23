package com.goulala.xiayun.common.pickerview;

/**
 * author      : Z_B
 * date       : 2018/10/12
 * function  : 条件选择器结果的回调
 */
public interface OnSelectConditionsClickListener {


    /**
     * 被选中的条件
     *
     * @param name 名称
     * @param id   id
     */
    void onConditionsSelect(String name, int id);
}
