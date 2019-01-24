package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.mycenter.model.ServiceCenterList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/16
 * function  : 客服中心的adapter
 */
public class CustomerServiceAdapter extends BaseQuickAdapter<ServiceCenterList, BaseViewHolder> {
    public CustomerServiceAdapter(@Nullable List<ServiceCenterList> data) {
        super(R.layout.include_service_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceCenterList item) {
        helper
                .setText(R.id.tvTitle, item.getTitle())
                .setText(R.id.tvContent, item.getContent());
        ImageLoaderUtils.displayGoods(item.getLogoimage(), (ImageView) helper.getView(R.id.ivLogo));

    }
}
