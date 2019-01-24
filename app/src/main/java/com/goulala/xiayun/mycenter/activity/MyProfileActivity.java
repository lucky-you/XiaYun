package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.ApiService;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.pickerview.OnSelectTimeClickListener;
import com.goulala.xiayun.common.pickerview.PickerViewCityUtils;
import com.goulala.xiayun.common.pickerview.PickerViewTimeUtils;
import com.goulala.xiayun.common.qiniu.QinIuUpLoadListener;
import com.goulala.xiayun.common.qiniu.QinIuUtils;
import com.goulala.xiayun.common.utils.AlertDialogUtils;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.ButtonClickUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.LogUtils;
import com.goulala.xiayun.common.utils.PictureSelectorUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;
import com.goulala.xiayun.mycenter.dialog.EditNickNameDialog;
import com.goulala.xiayun.mycenter.model.QinIuBean;
import com.goulala.xiayun.mycenter.presenter.MyProfilePresenter;
import com.goulala.xiayun.mycenter.view.IMyProfileView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心---我的资料
 */
public class MyProfileActivity extends BaseMvpActivity<MyProfilePresenter> implements IMyProfileView {


    private CircleImageView civUserImage;
    private TextView tvNickname;
    private RadioButton rbThatMan;
    private RadioButton rbThatWoman;
    private TextView tvLocalAddress;
    private TextView tvBirthday;
    private TextView tvTheSignature;
    private String memberSignature;//个性签名
    private String userImageUrl;
    private int gender = 0;
    private String qinIuToken; //七牛云的token和key
    private int isModifyTheMessage = 0; //是否修改了数据

    public static void start(Context context) {
        Intent intent = new Intent(context, MyProfileActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected MyProfilePresenter createPresenter() {
        return new MyProfilePresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_my_profile;
    }

    @Override
    public void bindViews(View contentView) {
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        civUserImage = get(R.id.civ_user_image);
        get(R.id.ll_user_photo_image).setOnClickListener(this);
        tvNickname = get(R.id.tv_nickname);
        tvNickname.setOnClickListener(this);
        rbThatMan = get(R.id.rb_that_man);
        rbThatMan.setOnClickListener(this);
        rbThatWoman = get(R.id.rb_that_woman);
        rbThatWoman.setOnClickListener(this);
        tvLocalAddress = get(R.id.tv_local_address);
        tvLocalAddress.setOnClickListener(this);
        tvBirthday = get(R.id.tv_birthday);
        tvBirthday.setOnClickListener(this);
        tvTheSignature = get(R.id.tv_The_signature);
        tvTheSignature.setOnClickListener(this);
        initTitle(mContext.getString(R.string.My_profile))
                .setRightText(mContext.getString(R.string.save))
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveMemberMessage();
                    }
                }).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        //获取用户信息
        getUserInfoMessage();
        //获取七牛云的信息
        getQinIuSet();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfoMessage() {
        Map<String, String> baseUserInfoParam = new HashMap<>();
        baseUserInfoParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_USER_BASE_INFO_URL);
        String baseUserInfoParamJson = JsonUtils.toJson(baseUserInfoParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getUserInfoMessage(userToken, baseUserInfoParamJson);
        }
    }

    /**
     * 获取七牛云的信息
     */
    private void getQinIuSet() {
        Map<String, String> qinIuMapParam = new HashMap<>();
        qinIuMapParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_QIN_IU_SET_UP_URL);
        String qinIuMapParamJson = JsonUtils.toJson(qinIuMapParam);
        LogUtils.showLog(userToken, qinIuMapParamJson);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.getQinIuSetMessage(userToken, qinIuMapParamJson);

    }

    private void showBackDialog() {
        AlertDialogUtils.showCustomerDialog(mContext, mContext.getString(R.string.Do_you_save_changes_before_exiting), new CancelOrDetermineClickListener() {
            @Override
            public void cancelClick() {
                finish();
            }

            @Override
            public void determineClick(View view) {
                saveMemberMessage();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (0 == isModifyTheMessage) { //说明什么都没有修改
            finish();
        } else {
            showBackDialog();
        }
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.ll_user_photo_image:
                //头像
                PictureSelectorUtils.selectImageOfOne(this, PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.tv_nickname:
                //昵称
                String nickName = tvNickname.getText().toString().trim();
                AlertDialogUtils.showNickNameDialog(mContext, nickName, new EditNickNameDialog.OnConfirmClickListener() {
                    @Override
                    public void onClick(String nickName) {
                        tvNickname.setText(nickName);
                        isModifyTheMessage = 2;
                    }
                });
                break;
            case R.id.rb_that_man:
                if (rbThatMan.isChecked()) {
                    gender = 1; //男
                }
                isModifyTheMessage = 3;
                break;
            case R.id.rb_that_woman:
                if (rbThatWoman.isChecked()) {
                    gender = 2; //女
                }
                isModifyTheMessage = 4;
                break;
            case R.id.tv_local_address:
                //常住地
                if (!ButtonClickUtils.isFastClick()) {
                    selectLocalAddress();
                }
                break;
            case R.id.tv_birthday:
                //生日
                if (!ButtonClickUtils.isFastClick()) {
                    initTimePicker(view);
                }
                break;
            case R.id.tv_The_signature:
                Intent intent = new Intent(mContext, IndividualitySignatureActivity.class);
                startActivityForResult(intent, ConstantValue.THE_MEMBER_SIGNATURE);
                break;
        }
    }

    /**
     * 保存用户的信息
     */
    private void saveMemberMessage() {
        String nickName = tvNickname.getText().toString().trim();
        String address = tvLocalAddress.getText().toString().trim();
        String birthday = tvBirthday.getText().toString().trim();
        String signName = tvTheSignature.getText().toString().trim();
        Map<String, String> saveMemberMessageParam = new HashMap<>();
        saveMemberMessageParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.EDIT_MEMBER_MESSAGE_URL);
        saveMemberMessageParam.put(ApiParam.NICK_NAME, nickName);
        saveMemberMessageParam.put(ApiParam.AVATAR, userImageUrl);
        saveMemberMessageParam.put(ApiParam.GENDER, String.valueOf(gender));
        saveMemberMessageParam.put(ApiParam.ADDRESS, address);
        saveMemberMessageParam.put(ApiParam.BIRTHDAY, birthday);
        saveMemberMessageParam.put(ApiParam.BIO, signName);
        String saveMemberMessageParamJson = JsonUtils.toJson(saveMemberMessageParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.modifyMemberInformation(userToken, saveMemberMessageParamJson);
            isModifyTheMessage = 1;
        }

    }

    /**
     * 选择省市区
     */
    private void selectLocalAddress() {
        PickerViewCityUtils pickerViewCityUtils = new PickerViewCityUtils(MyProfileActivity.this);
        pickerViewCityUtils.setOnCitySelectClickListener(new PickerViewCityUtils.OnCitySelectClickListener() {
            @Override
            public void onSelectCityResult(String selectName, int province_Id, int city_Id, int area_Id) {
                tvLocalAddress.setText(selectName);
                isModifyTheMessage = 5;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantValue.THE_MEMBER_SIGNATURE: //个性签名
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        memberSignature = data.getStringExtra(ApiParam.BIO);
                        tvTheSignature.setText(memberSignature);
                        isModifyTheMessage = 6;
                    }
                }
                break;
            case PictureConfig.CHOOSE_REQUEST:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.isEmpty()) return;
                userImageUrl = selectList.get(0).getPath();
                qinIuUpLoad(userImageUrl);
                break;

        }
    }

    /**
     * 将图片上传到七牛云
     */
    private void qinIuUpLoad(String ImageUrl) {
        String qinKey = "xiayun/uploads/" + QinIuUtils.getStringDate() + "/" + System.currentTimeMillis();
        QinIuUtils.qinIuUpLoad(ImageUrl, qinKey, qinIuToken, new QinIuUpLoadListener() {
            @Override
            public void upLoadSuccess(String path) {
                userImageUrl = "/" + path;
                ImageLoaderUtils.displayAvatar(ApiService.QIN_IU_YUN_URL + path, civUserImage);
            }

            @Override
            public void upLoadFail(String errorMessage) {
                showToast(mContext.getString(R.string.Upload_failed, errorMessage));
            }
        });
    }


    /**
     * 选择生日
     */
    private void initTimePicker(View view) {
        PickerViewTimeUtils.selectTimePickerView(mContext, view, new OnSelectTimeClickListener() {
            @Override
            public void onDateTime(String dateTime) {
                tvBirthday.setText(dateTime);
                isModifyTheMessage = 8;
            }
        });
    }

    @Override
    public void getUserInfoMessage(UserInfo userInfo) {
        if (userInfo != null) {
            ImageLoaderUtils.displayAvatar(userInfo.getAvatar(), civUserImage);
            tvNickname.setText(userInfo.getNickname());
            tvLocalAddress.setText(userInfo.getAddress());
            tvTheSignature.setText(userInfo.getBio());
            tvBirthday.setText(userInfo.getBirthday());
            int gender = userInfo.getGender(); // 0=未知,1=男,2=女
            switch (gender) {
                case 0:
                    break;
                case 1:
                    rbThatMan.setChecked(true);
                    break;
                case 2:
                    rbThatWoman.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void modifySuccess(String message) {
        showToast(message);
        finish();
    }

    @Override
    public void getQinIuSetMessageSuccess(QinIuBean message) {
        if (message != null) {
            qinIuToken = message.getToken();
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
