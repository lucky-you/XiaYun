package com.goulala.xiayun.shopcar.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.model.Notice;
import com.goulala.xiayun.common.utils.ConstantValue;
import com.goulala.xiayun.home.model.GoodActivityBean;
import com.goulala.xiayun.home.model.GoodItemMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * author     : Z_B
 * date      : 2018/8/17
 * function : 确认订单的商品的adapter
 */
public class MakeSureTheOrderGoodAdapter extends BaseQuickAdapter<GoodItemMessage, BaseViewHolder> {

    public MakeSureTheOrderGoodAdapter(@Nullable List<GoodItemMessage> data) {
        super(R.layout.include_make_sure_order_good_item_view, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, GoodItemMessage item) {
        ImageLoaderUtils.displayGoods(item.getSmallimage(), (ImageView) helper.getView(R.id.civ_good_photo));
        helper.setText(R.id.tv_good_name, item.getName())
                .setText(R.id.tv_good_specifications, item.getSpecification())
                .setText(R.id.tv_good_number, "x" + item.getItem_num())
                .setText(R.id.tv_In_distribution_type, item.getExpress())
                .setVisible(R.id.Divide_line, helper.getAdapterPosition() != mData.size() - 1);
        List<GoodActivityBean> goodItemList = item.getActive();
        if (goodItemList != null && goodItemList.size() > 0) {
            //有活动
            helper.setText(R.id.tv_good_price, mContext.getString(R.string.the_price, goodItemList.get(0).getPrice() + ""))
                    .setText(R.id.tv_good_plus_price, mContext.getString(R.string.the_price, goodItemList.get(0).getMember_price() + ""));
        } else {
            //没有活动
            helper.setText(R.id.tv_good_price, mContext.getString(R.string.the_price, item.getPrice() + ""))
                    .setText(R.id.tv_good_plus_price, mContext.getString(R.string.the_price, item.getMember_price() + ""));
        }
        EditText editLeaveMessage = helper.getView(R.id.edit_Message_prompt);
        editLeaveMessage.setFocusableInTouchMode(true);
        editLeaveMessage.requestFocus();
        editLeaveMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                EventBus.getDefault().post(new Notice(ConstantValue.THE_USER_MESSAGE_TYPE, editable.toString(), mData.get(helper.getAdapterPosition()).getId()));
            }
        });
    }

}
