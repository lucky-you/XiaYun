package com.goulala.xiayun.mycenter.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.utils.PhoneUtils;
import com.goulala.xiayun.mycenter.callback.OnSetDefaultClickListener;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;

import java.util.List;

/**
 * author : Z_B
 * date : 2018/8/20
 * function : 收货地址
 */
public class ShippingAddressAdapter extends BaseQuickAdapter<ShoppingAddressList, BaseViewHolder> {

    private OnSetDefaultClickListener onSetDefaultClickListener;

    public void setOnSetDefaultClickListener(OnSetDefaultClickListener onSetDefaultClickListener) {
        this.onSetDefaultClickListener = onSetDefaultClickListener;
    }

    public ShippingAddressAdapter(@Nullable List<ShoppingAddressList> data) {
        super(R.layout.include_shipping_adress_item_view, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, ShoppingAddressList item) {
        helper.setText(R.id.tv_The_consignee_name, mContext.getString(R.string.The_consignee, item.getName()))
                .setText(R.id.tv_user_mobil, PhoneUtils.mobilNumber(item.getMobile()))
                .setGone(R.id.tv_delete_address, 1 != item.getSwitchX())
                .addOnClickListener(R.id.tv_edit_address)
                .addOnClickListener(R.id.tv_delete_address);
        TextView tvShoppingAddress = helper.getView(R.id.tv_Shipping_address);
        String address = item.getProvince() + item.getCity() + item.getArea() + item.getAddress();
        tvShoppingAddress.setText(mContext.getString(R.string.Shipping_address, address));
        final CheckBox cbSetDefault = helper.getView(R.id.cb_Set_to_the_default);
        TextView tvDefault = helper.getView(R.id.tv_is_default_address);
        if (item.getSwitchX() == 1) { //是否是默认
            cbSetDefault.setChecked(true);
            cbSetDefault.setEnabled(false);
            tvDefault.setVisibility(View.VISIBLE);
        } else {
            cbSetDefault.setChecked(false);
            tvDefault.setVisibility(View.INVISIBLE);
        }
        cbSetDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSelect) {
                int addressId = mData.get(helper.getAdapterPosition()).getId();
                if (isSelect) {
                    cbSetDefault.setChecked(true);
                    if (onSetDefaultClickListener != null) {
                        onSetDefaultClickListener.onSetDefaultClick(true, addressId);
                    }
                } else {
                    cbSetDefault.setChecked(false);
                    if (onSetDefaultClickListener != null) {
                        onSetDefaultClickListener.onSetDefaultClick(false, addressId);
                    }
                }

            }
        });
    }
}
