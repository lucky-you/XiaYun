package com.goulala.xiayun.common.model;

/**
 * 发送消息模板
 */
public class Notice {

    public int type;
    public Object contentOne;
    public Object contentTwo;
    public Object contentThree;

    public Notice(int type) {
        this.type = type;
    }

    public Notice(int type, Object contentOne) {
        this.type = type;
        this.contentOne = contentOne;
    }

    public Notice(int type, Object contentOne, Object contentTwo) {
        this.type = type;
        this.contentOne = contentOne;
        this.contentTwo = contentTwo;
    }

    public Notice(int type, Object contentOne, Object contentTwo, Object contentThree) {
        this.type = type;
        this.contentOne = contentOne;
        this.contentTwo = contentTwo;
        this.contentThree = contentThree;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "type=" + type +
                ", contentOne=" + contentOne +
                ", contentTwo=" + contentTwo +
                ", contentThree=" + contentThree +
                '}';
    }
}
