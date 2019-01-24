package com.goulala.xiayun.mycenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.mycenter.presenter.PlatformSuggestionBoxPresenter;
import com.goulala.xiayun.mycenter.view.IPlatformSuggestionBoxView;

import java.util.HashMap;
import java.util.Map;

/**
 * 平台意见箱
 */
public class PlatformSuggestionBoxActivity extends BaseMvpActivity<PlatformSuggestionBoxPresenter> implements IPlatformSuggestionBoxView {

    private EditText editContent;
    private TextView tvEditNumber;
    private int editMaxNumber = 200;

    public static void start(Context context) {
        Intent intent = new Intent(context, PlatformSuggestionBoxActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected PlatformSuggestionBoxPresenter createPresenter() {
        return new PlatformSuggestionBoxPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_platform_suggestion_box;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Platform_suggestion_box));
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.fake_status_bar));
        editContent = get(R.id.editContent);
        tvEditNumber = get(R.id.tvEditNumber);
        get(R.id.tvSubmit).setOnClickListener(this);
        tvEditNumber.setText("0/" + editMaxNumber);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String edit = charSequence.toString();
                if (0 < edit.length() && edit.length() < editMaxNumber) {
                    tvEditNumber.setText(edit.length() + "/" + editMaxNumber);
                } else if (edit.length() > editMaxNumber) {
                    tvEditNumber.setText(editMaxNumber + "/" + editMaxNumber);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setClickListener(View view) {
        if (view.getId() == R.id.tvSubmit) {
            String editContentText = editContent.getText().toString().trim();
            if (TextUtils.isEmpty(editContentText)) {
                showToast(mContext.getString(R.string.Content_cannot_be_empty));
                return;
            }
            submitPlatform(editContentText);
        }
    }

    /**
     * 提交意见
     */
    private void submitPlatform(String message) {
        Map<String, String> messageParam = new HashMap<>();
        messageParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.SUBMIT_PLATFORM_MESSAGE_URL);
        messageParam.put("content", message);
        messageParam.put("system", "Android");
        String messageParamJson = JsonUtils.toJson(messageParam);
        if (TextUtils.isEmpty(userToken)) return;
        mvpPresenter.submitPlatformMessage(userToken, messageParamJson);
        showDialog("");

    }

    @Override
    public void submitPlatformMessage(String message) {
        showToast(message);
        dismissDialog();
        finish();
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
        dismissDialog();
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);
        dismissDialog();
    }
}
