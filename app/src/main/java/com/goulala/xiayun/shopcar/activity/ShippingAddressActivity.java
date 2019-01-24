package com.goulala.xiayun.shopcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.mycenter.callback.OnSetDefaultClickListener;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;
import com.goulala.xiayun.shopcar.adapter.ShippingAddressAdapter;
import com.goulala.xiayun.shopcar.presenter.ShippingAddressPresenter;
import com.goulala.xiayun.shopcar.view.IShippingAddressView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收货地址
 */
public class ShippingAddressActivity extends BaseMvpActivity<ShippingAddressPresenter> implements IShippingAddressView,
        OnSetDefaultClickListener,
        BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemClickListener {

    private RecyclerView smartRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private TextView tvTheNewAddress;
    private List<ShoppingAddressList> shoppingAddressLists = new ArrayList<>();
    private ShippingAddressAdapter shippingAddressAdapter;
    private int classFormType;


    @Override
    protected ShippingAddressPresenter createPresenter() {
        return new ShippingAddressPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        classFormType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_shipping_address;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.my_Shipping_address));
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        smartRecyclerView = get(R.id.smart_RecyclerView);
        refreshLayout = get(R.id.refreshLayout);
        tvTheNewAddress = get(R.id.tv_The_new_address);
        tvTheNewAddress.setOnClickListener(this);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableLoadMore(false);

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        shippingAddressAdapter = new ShippingAddressAdapter(shoppingAddressLists);
        shippingAddressAdapter.setOnSetDefaultClickListener(this);
        shippingAddressAdapter.setOnItemChildClickListener(this);
        shippingAddressAdapter.setOnItemClickListener(this);
        smartRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        smartRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 12));
        smartRecyclerView.setAdapter(shippingAddressAdapter);
        EmptyViewUtils.bindEmptyView(mContext, shippingAddressAdapter, mContext.getString(R.string.No_receiving_address));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                getShoppingAddressList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShoppingAddressList();
    }

    /**
     * 获取收货地址的列表
     */
    private void getShoppingAddressList() {
        Map<String, String> shoppingListParam = new HashMap<>();
        shoppingListParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_THE_ADDRESS_LIST_URL);
        String shoppingListParamJson = JsonUtils.toJson(shoppingListParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getShoppingAddressList(userToken, shoppingListParamJson);
        }
    }

    @Override
    public void setClickListener(View view) {
        if (view.getId() == R.id.tv_The_new_address) {
//            EditTheShippingAddressActivity.start(mContext, ConstantValue.TYPE_OF_ADDRESS_CLASS);
        }
    }

    @Override
    public void getShoppingAddressListSuccess(List<ShoppingAddressList> shoppingAddressLists) {
        if (shoppingAddressLists != null && shoppingAddressLists.size() > 0) {
            this.shoppingAddressLists = shoppingAddressLists;
            shippingAddressAdapter.setNewData(shoppingAddressLists);
        }
    }

    @Override
    public void theOperationOfAddress(int requestType, String message) {
        showToast(message);
        getShoppingAddressList();
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int addressId = shippingAddressAdapter.getItem(position).getId();
        switch (view.getId()) {
            case R.id.tv_edit_address:
                //修改
                int isDefaultAddress = shippingAddressAdapter.getItem(position).getSwitchX();
                String userName = shippingAddressAdapter.getItem(position).getName();
                String userPhoneNumber = shippingAddressAdapter.getItem(position).getMobile();
                int provinceId = shippingAddressAdapter.getItem(position).getProvince_id();
                int cityId = shippingAddressAdapter.getItem(position).getCity_id();
                int areaId = shippingAddressAdapter.getItem(position).getArea_id();
                String userCityAddress = shippingAddressAdapter.getItem(position).getProvince() + shippingAddressAdapter.getItem(position).getCity()
                        + shippingAddressAdapter.getItem(position).getArea();
                String userAreaDetailsAddress = shippingAddressAdapter.getItem(position).getAddress();
//                EditTheShippingAddressActivity.start(mContext,
//                        ConstantValue.TYPE_OF_EDITTEXT_CLASS,
//                        addressId,
//                        isDefaultAddress,
//                        provinceId,
//                        cityId,
//                        areaId,
//                        userName,
//                        userPhoneNumber,
//                        userCityAddress,
//                        userAreaDetailsAddress
//                );
                break;
            case R.id.tv_delete_address:
                //删除
                DeleteDialog(addressId);
                break;
        }
    }

    /**
     * 删除或者设置默认的操作
     */
    private void setDefaultOrDeleteAddress(String apiUrl, int addressId, int requestType) {
        Map<String, String> setDefaultParam = new HashMap<>();
        setDefaultParam.put(ApiParam.BASE_METHOD_KEY, apiUrl);
        setDefaultParam.put(ApiParam.ADDRESS_ID, String.valueOf(addressId));
        String setDefaultParamJson = JsonUtils.toJson(setDefaultParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.operationOnTheAddress(requestType, userToken, setDefaultParamJson);
        }
    }

    /**
     * 删除收货地址
     */
    private void DeleteDialog(final int addressId) {
        AlertDialogUtils.showCustomerDialog(mContext, mContext.getString(R.string.Are_you_sure_about_deleting), new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {

            }

            @Override
            public void determineClick(View view) {
                setDefaultOrDeleteAddress(ApiParam.DELETE_THAT_ADDRESS_URL, addressId, IShippingAddressView.DELETE_ADDRESS_TYPE);
            }
        });
    }

    @Override
    public void onSetDefaultClick(boolean isSelect, int addressId) {
        if (isSelect) {
            //设置为默认收货地址
            setDefaultOrDeleteAddress(ApiParam.SET_THE_DEFAULT_ADDRESS_URL, addressId, IShippingAddressView.SET_DEFAULT_ADDRESS_TYPE);
        } else {

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (ConstantValue.GET_SHOPPING_ADDRESS_RETURN_TO_ORDER_ACTIVITY == classFormType) {
            int addressId = shippingAddressAdapter.getItem(position).getId();
            String userName = shippingAddressAdapter.getItem(position).getName();
            String userPhoneNumber = shippingAddressAdapter.getItem(position).getMobile();
            String userAreaDetailsAddress = shippingAddressAdapter.getItem(position).getProvince() + shippingAddressAdapter.getItem(position).getCity()
                    + shippingAddressAdapter.getItem(position).getArea() + shippingAddressAdapter.getItem(position).getAddress();
            Intent intent = new Intent();
            intent.putExtra(ApiParam.ADDRESS_ID, addressId);
            intent.putExtra(ApiParam.NAME, userName);
            intent.putExtra(ApiParam.MOBILE_KEY, userPhoneNumber);
            intent.putExtra(ApiParam.ADDRESS, userAreaDetailsAddress);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
