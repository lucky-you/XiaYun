package com.goulala.xiayun.mycenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseActivity;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.StatusBarUtil;


/**
 * 个性签名
 */
public class IndividualitySignatureActivity extends BaseActivity {

    private EditText editPersonalizedSignatureText;
    private TextView tvSignatureNumber;
    private int nowNum = 0; //当前输入的字符数
    private CharSequence temp;//接收当前输入的字符
    private int maxNum = 200; // 最大的字符数量

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.activity_individuality_signature;
    }

    @Override
    public void bindViews(View contentView) {
        initTitle(mContext.getString(R.string.Individuality_signature));
        StatusBarUtil.setStatusBar(this, false, false);
        View fakeStatusBar = get(R.id.fake_status_bar);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fakeStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        editPersonalizedSignatureText = get(R.id.edit_Personalized_signature_text);
        tvSignatureNumber = get(R.id.tv_Signature_number);
        get(R.id.tv_save).setOnClickListener(this);
        tvSignatureNumber.setText(nowNum + "/" + maxNum);


    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        editPersonalizedSignatureText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                nowNum = editable.length();
                tvSignatureNumber.setText(nowNum + "/" + maxNum);
                int selectionStart = editPersonalizedSignatureText.getSelectionStart();
                int selectionEnd = editPersonalizedSignatureText.getSelectionEnd();
                if (temp.length() > maxNum) {
                    //截取字符串
                    editable.delete(selectionStart - 1, selectionEnd);
                    editPersonalizedSignatureText.setText(editable.toString());
                    int selection = editable.length();
                    //光标回到末尾
                    editPersonalizedSignatureText.setSelection(selection);
                }
            }
        });
    }

    @Override
    public void setClickListener(View view) {
        if (view.getId() == R.id.tv_save) {
            String IndividualitySignature = editPersonalizedSignatureText.getText().toString().trim();
            if (TextUtils.isEmpty(IndividualitySignature)) {
                showToast(mContext.getString(R.string.Please_enter_your_signature));
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(ApiParam.BIO, IndividualitySignature);
            setResult(RESULT_OK, intent);
            finish();

        }
    }
}
