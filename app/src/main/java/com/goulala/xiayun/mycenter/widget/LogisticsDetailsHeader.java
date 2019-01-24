package com.goulala.xiayun.mycenter.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author      : Z_B
 * date       : 2018/9/3
 * function  : 物流详情的头部布局
 */
public class LogisticsDetailsHeader extends FrameLayout {
    private CircleImageView civLogisticsCompanyLogo;
    private TextView tvLogisticsCompanyName;
    private TextView tvTheOfficialPhone;
    private TextView tvLogisticsSingleNumberTwo;
    private Context mContext;

    public LogisticsDetailsHeader(@NonNull Context context) {
        this(context, null);
    }

    public LogisticsDetailsHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LogisticsDetailsHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View headerView = View.inflate(context, R.layout.include_logistics_details_layout, this);
        initViews(headerView);
    }

    private void initViews(View headerView) {
        civLogisticsCompanyLogo = headerView.findViewById(R.id.civ_Logistics_company_logo);
        tvLogisticsCompanyName = headerView.findViewById(R.id.tv_Logistics_company_name);
        tvTheOfficialPhone = headerView.findViewById(R.id.tv_The_official_phone);
        tvLogisticsSingleNumberTwo = headerView.findViewById(R.id.tv_Logistics_single_number_two);

    }

    /**
     * 设置头部布局的数据
     */
    public void setHeaderViewDate(String LogisticsCompanyLogo, String LogisticsCompanyName, String TheOfficialPhone, String LogisticsSingleNumberTwo) {
        if (!TextUtils.isEmpty(LogisticsCompanyLogo)) {
            ImageLoaderUtils.displayGoods(LogisticsCompanyLogo, civLogisticsCompanyLogo);
        }
        if (!TextUtils.isEmpty(LogisticsCompanyName)) {
            tvLogisticsCompanyName.setText(LogisticsCompanyName);
        }
        if (!TextUtils.isEmpty(TheOfficialPhone)) {
            tvTheOfficialPhone.setText(mContext.getString(R.string.The_official_phone, TheOfficialPhone));
        }
        if (!TextUtils.isEmpty(LogisticsSingleNumberTwo)) {
            tvLogisticsSingleNumberTwo.setText(mContext.getString(R.string.Logistics_single_number_two, LogisticsSingleNumberTwo));
        }
    }


}
