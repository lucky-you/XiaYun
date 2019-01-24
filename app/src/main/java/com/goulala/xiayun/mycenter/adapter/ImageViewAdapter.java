package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.utils.ImageLoaderUtils;
import com.goulala.xiayun.R;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/10/11
 * function  : 单纯的展示imageView的adapter
 */
public class ImageViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageViewAdapter(@Nullable List<String> data) {
        super(R.layout.include_image_view_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageLoaderUtils.displayGoods(item, (ImageView) helper.getView(R.id.iv_good_imageView));

    }
}
