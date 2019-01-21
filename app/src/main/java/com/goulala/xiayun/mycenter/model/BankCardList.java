package com.goulala.xiayun.mycenter.model;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * author      : Z_B
 * date       : 2018/9/29
 * function  : 选择银行的model 和 选择物流公司的model复用
 */
public class BankCardList  implements IPickerViewData {


    /**
     * id : 1
     * name : 工商银行
     * image : http://bpic.588ku.com/element_origin_min_pic/18/06/10/82d17e7657f03f5e2e4b42f06773f27e.jpg
     */

    private int id;
    private String name;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
