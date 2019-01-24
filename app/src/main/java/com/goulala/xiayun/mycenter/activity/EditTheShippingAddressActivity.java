package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.pickerview.PickerViewCityUtils;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.ButtonClickUtils;
import com.goulala.xiayun.common.utils.InputMethodKeyBroadUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.PhoneUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;
import com.goulala.xiayun.shopcar.presenter.ShippingAddressPresenter;
import com.goulala.xiayun.shopcar.view.IShippingAddressView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑收货地址 和 添加收货地址公用
 */
public class EditTheShippingAddressActivity extends BaseMvpActivity<ShippingAddressPresenter> implements IShippingAddressView {

    private EditText editFillInTheConsigneeName;
    private EditText editEnterMobilePhoneNumber;
    private TextView tvSelectCity;
    private RelativeLayout rlSelectCity;
    private EditText editDetailedAddress;
    private CheckBox cbSetTheDefault;
    private LinearLayout llSetTheDefault;

    private int classType, addressId;
    private String userName, userPhoneNumber, userCityAddress, userAreaDetailsAddress;
    private int isDefaultAddress = 0;
    private int provinceId, cityId, areaId;
    private int isModifyTheMessage = 0; //是否修改了数据

    public static void start(Context context, int classType) {
        start(context, classType, 0, 0, 0, 0, 0,
                null, null, null, null);

    }

    public static void start(Context context,
                             int classType,
                             int addressId,
                             int isDefaultAddress,
                             int provinceId,
                             int cityId,
                             int areaId,
                             String userName,
                             String userPhoneNumber,
                             String userCityAddress,
                             String userAreaDetailsAddress) {
        Intent intent = new Intent(context, EditTheShippingAddressActivity.class);
        intent.putExtra(ConstantValue.INDEX, classType);
        intent.putExtra(ConstantValue.ID, addressId);
        intent.putExtra(ConstantValue.TYPE, isDefaultAddress);
        intent.putExtra(ApiParam.PROVINCE_ID, provinceId);
        intent.putExtra(ApiParam.CITY_ID, cityId);
        intent.putExtra(ApiParam.AREA_ID, areaId);
        intent.putExtra(ConstantValue.USER_NAME, userName);
        intent.putExtra(ConstantValue.PHONE_NUMBER, userPhoneNumber);
        intent.putExtra(ConstantValue.CITY, userCityAddress);
        intent.putExtra(ConstantValue.AREA, userAreaDetailsAddress);
        context.startActivity(intent);
    }


    @Override
    protected ShippingAddressPresenter createPresenter() {
        return new ShippingAddressPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        classType = getIntent().getIntExtra(ConstantValue.INDEX, -1);
        addressId = getIntent().getIntExtra(ConstantValue.ID, -1);
        isDefaultAddress = getIntent().getIntExtra(ConstantValue.TYPE, -1);

        provinceId = getIntent().getIntExtra(ApiParam.PROVINCE_ID, -1);
        cityId = getIntent().getIntExtra(ApiParam.CITY_ID, -1);
        areaId = getIntent().getIntExtra(ApiParam.AREA_ID, -1);

        userName = getIntent().getStringExtra(ConstantValue.USER_NAME);
        userPhoneNumber = getIntent().getStringExtra(ConstantValue.PHONE_NUMBER);
        userCityAddress = getIntent().getStringExtra(ConstantValue.CITY);
        userAreaDetailsAddress = getIntent().getStringExtra(ConstantValue.AREA);
    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_edit_the_shipping_address;
    }

    @Override
    public void bindViews(View contentView) {
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        editFillInTheConsigneeName = get(R.id.edit_Fill_in_the_consignee_name);
        editEnterMobilePhoneNumber = get(R.id.edit_Enter_mobile_phone_number);
        tvSelectCity = get(R.id.tv_Select_city);
        get(R.id.rl_Select_city).setOnClickListener(this);
        editDetailedAddress = get(R.id.edit_Detailed_address);
        cbSetTheDefault = get(R.id.cb_Set_the_default);
        cbSetTheDefault.setOnClickListener(this);
        llSetTheDefault = get(R.id.ll_Set_the_default);
        get(R.id.tv_save).setOnClickListener(this);

        if (ConstantValue.TYPE_OF_ADDRESS_CLASS == classType) {
            initTitle(mContext.getString(R.string.Add_new_shipping_address)).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            initTitle(mContext.getString(R.string.Edit_the_shipping_address)).setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            if (!TextUtils.isEmpty(userName)) {
                editFillInTheConsigneeName.setText(userName);
            }
            if (!TextUtils.isEmpty(userPhoneNumber)) {
                editEnterMobilePhoneNumber.setText(userPhoneNumber);
            }
            if (!TextUtils.isEmpty(userCityAddress)) {
                tvSelectCity.setText(userCityAddress);
            }
            if (!TextUtils.isEmpty(userAreaDetailsAddress)) {
                editDetailedAddress.setText(userAreaDetailsAddress);
            }
            if (1 == isDefaultAddress) {
                //说明已经是默认的收货地址,不可编辑
                cbSetTheDefault.setChecked(true);
                cbSetTheDefault.setEnabled(false);
            } else {
                //不是默认收货地址，可编辑
                cbSetTheDefault.setChecked(false);
            }
        }

    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

    }

    /**
     * 添加或者修改地址
     *
     * @param apiUrl
     * @param requestType
     */
    private void addOrEditShopAddress(String apiUrl, int requestType) {
        String name = editFillInTheConsigneeName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast(mContext.getString(R.string.Fill_in_the_consignee_name));
            return;
        }
        String mobil = editEnterMobilePhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mobil)) {
            showToast(mContext.getString(R.string.Enter_mobile_phone_number));
            return;
        }
        if (!PhoneUtils.checkPhone(mobil, true)) {
            return;
        }
        if (0 == provinceId || 0 == areaId) {
            showToast(mContext.getString(R.string.Please_select_your_region));
            return;
        }
        String AreaDetailsAddress = editDetailedAddress.getText().toString().trim();
        if (TextUtils.isEmpty(AreaDetailsAddress)) {
            showToast(mContext.getString(R.string.please_edit_details_address));
            return;
        }
        Map<String, String> addShopAddressParam = new HashMap<>();
        addShopAddressParam.put(ApiParam.BASE_METHOD_KEY, apiUrl);
        addShopAddressParam.put(ApiParam.ADDRESS_ID, String.valueOf(addressId));
        addShopAddressParam.put(ApiParam.NAME, name);
        addShopAddressParam.put(ApiParam.MOBILE_KEY, mobil);
        addShopAddressParam.put(ApiParam.PROVINCE_ID, String.valueOf(provinceId));
        addShopAddressParam.put(ApiParam.CITY_ID, String.valueOf(cityId));
        addShopAddressParam.put(ApiParam.AREA_ID, String.valueOf(areaId));
        addShopAddressParam.put(ApiParam.ADDRESS, AreaDetailsAddress);
        addShopAddressParam.put(ApiParam.SWITCH, String.valueOf(isDefaultAddress));
        String addShopAddressParamJson = JsonUtils.toJson(addShopAddressParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.operationOnTheAddress(requestType, userToken, addShopAddressParamJson);
        }

    }

    @Override
    public void onBackPressed() {
        if (TextUtils.isEmpty(editFillInTheConsigneeName.getText().toString().trim())
                && TextUtils.isEmpty(editEnterMobilePhoneNumber.getText().toString().trim())
                && TextUtils.isEmpty(editDetailedAddress.getText().toString().trim())
                && isModifyTheMessage == 0
                ) {
            finish();
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        String hitTitle = mContext.getString(R.string.Information_not_saved_whether_still_quit);
        AlertDialogUtils.showCustomerDialog(mContext, hitTitle, new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {

            }

            @Override
            public void determineClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.rl_Select_city:
                //选择城市
                InputMethodKeyBroadUtils.hideKeyboard(this);
                if (!ButtonClickUtils.isFastClick()) {
                    selectCity();
                }
                break;
            case R.id.cb_Set_the_default:
                //设为默认
                if (cbSetTheDefault.isChecked()) {
                    isDefaultAddress = 1;
                } else {
                    isDefaultAddress = 0;
                }
                break;
            case R.id.tv_save:
                //保存
                if (ConstantValue.TYPE_OF_ADDRESS_CLASS == classType) {
                    //添加收货地址
                    addOrEditShopAddress(ApiParam.ADD_SHOP_ADDRESS_URL, IShippingAddressView.THE_TYPE_OF_ADD_SHOPPING_ADDRESS);
                } else {
                    //修改收货地址
                    addOrEditShopAddress(ApiParam.EDIT_SHOP_ADDRESS_URL, IShippingAddressView.THE_TYPE_OF_EDIT_SHOPPING_ADDRESS);
                }
                break;
        }
    }

    /**
     * 选择城市
     */
    private void selectCity() {
        PickerViewCityUtils pickerViewCityUtils = new PickerViewCityUtils(EditTheShippingAddressActivity.this);
        pickerViewCityUtils.setOnCitySelectClickListener(new PickerViewCityUtils.OnCitySelectClickListener() {
            @Override
            public void onSelectCityResult(String selectName, int province_Id, int city_Id, int area_Id) {
                tvSelectCity.setText(selectName);
                provinceId = province_Id;
                cityId = city_Id;
                areaId = area_Id;
                isModifyTheMessage = 999;
            }
        });
    }

    @Override
    public void getShoppingAddressListSuccess(List<ShoppingAddressList> shoppingAddressLists) {

    }

    @Override
    public void theOperationOfAddress(int requestType, String message) {
        showToast(message);
        finish();
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
