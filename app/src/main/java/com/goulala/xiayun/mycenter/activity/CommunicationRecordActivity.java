package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.DateUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.mycenter.model.RecordBean;
import com.goulala.xiayun.mycenter.presenter.CommunicationRecordPresenter;
import com.goulala.xiayun.mycenter.view.ICommunicationRecordView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 沟通记录
 */
public class CommunicationRecordActivity extends BaseMvpActivity<CommunicationRecordPresenter> implements ICommunicationRecordView {

    private TextView tvTheMessageOfTheService;
    private TextView tvTvTheMessageOfTheServiceTime;
    private TextView tvProblemDescription;
    private TextView tvTvProblemDescriptionTime;
    private String applyRefundServiceOrder;

    public static void start(Context context, String applyOrder) {
        Intent intent = new Intent(context, CommunicationRecordActivity.class);
        intent.putExtra(ConstantValue.ORDER_NUMBER, applyOrder);
        context.startActivity(intent);
    }


    @Override
    protected CommunicationRecordPresenter createPresenter() {
        return new CommunicationRecordPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        applyRefundServiceOrder = getIntent().getStringExtra(ConstantValue.ORDER_NUMBER);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_communication_record;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Communication_record));
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        tvTheMessageOfTheService = get(R.id.tv_The_message_of_the_service);
        tvTvTheMessageOfTheServiceTime = get(R.id.tv_tv_The_message_of_the_service_time);
        tvProblemDescription = get(R.id.tv_Problem_description);
        tvTvProblemDescriptionTime = get(R.id.tv_tv_Problem_description_time);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        getCommunicationRecordDate();
    }

    /**
     * 获取沟通记录的数据
     */
    private void getCommunicationRecordDate() {
        Map<String, String> recordParam = new HashMap<>();
        recordParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.COMMUNICATION_RECORD_UTL);
        recordParam.put(ApiParam.SERVICE_NO, applyRefundServiceOrder);
        String recordParamJson = JsonUtils.toJson(recordParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getCommunicationRecord(userToken, recordParamJson);
        }
    }

    @Override
    public void setClickListener(View view) {

    }

    @Override
    public void getCommunicationRecordSuccess(List<RecordBean> message) {
        if (message != null && message.size() > 0) {
            switch (message.size()) {
//                case 1: //取集合的第一个为客服的审核留言
//                    tvTheMessageOfTheService.setText(message.get(0).getRemark());
//                    tvTvTheMessageOfTheServiceTime.setText(DateUtils.getStrTime(message.get(0).getCreatetime()));
//                    break;
                case 2:
                    //取集合的第一个为客服的审核留言
                    // 取第二个是用户的问题描述
                    tvTheMessageOfTheService.setText(message.get(0).getRemark());
                    tvTvTheMessageOfTheServiceTime.setText(DateUtils.getStrTime(message.get(0).getCreatetime()));
                    if (TextUtils.isEmpty(message.get(1).getRemark())) {
                        tvProblemDescription.setText(mContext.getString(R.string.User_did_not_fill_in));
                    } else {
                        tvProblemDescription.setText(message.get(1).getRemark());
                    }
                    tvTvProblemDescriptionTime.setText(DateUtils.getStrTime(message.get(1).getCreatetime()));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
    }
}