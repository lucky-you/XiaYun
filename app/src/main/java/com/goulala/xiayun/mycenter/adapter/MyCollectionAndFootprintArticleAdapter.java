package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ConstantValue;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/30
 * @function :我的收藏和我的足迹公用的   (虾记文章)  的adapter
 */
public class MyCollectionAndFootprintArticleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int mClassType;

    public MyCollectionAndFootprintArticleAdapter(@Nullable List<String> data, int classType) {
        super(R.layout.include_mycollection_and_footer_article_layout, data);
        this.mClassType = classType;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (mClassType == ConstantValue.THE_CLASS_OF_MY_COLLECTION_TYPE) {
            //我的收藏
            helper.setVisible(R.id.cb_good_check, false)
                    .setText(R.id.tv_Room_clerk_name, item);
        } else {
            //  我的足迹
            helper.setVisible(R.id.cb_good_check, false)
                    .setText(R.id.tv_Room_clerk_name, item);

        }
    }
}
