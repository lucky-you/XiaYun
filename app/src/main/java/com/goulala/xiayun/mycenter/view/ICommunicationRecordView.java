package com.goulala.xiayun.mycenter.view;

import com.goulala.xiayun.common.mvp.IBaseView;
import com.goulala.xiayun.mycenter.model.RecordBean;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/24
 * function  :
 */
public interface ICommunicationRecordView extends IBaseView {
    //获取沟通记录
    void getCommunicationRecordSuccess(List<RecordBean> message);
}
