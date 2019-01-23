package com.goulala.xiayun.mycenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.utils.ToastUtils;

/**
 * Created by : Z_B on 2017/12/7.
 * Effect : 修改用户昵称的DiaLog
 */

public class EditNickNameDialog extends Dialog implements View.OnClickListener {

    private TextView tvDialogTitle;
    private EditText editNickname;
    private TextView tvCancel;
    private TextView tvConfirm;
    private Context mContext;
    private Window window;
    private Display display;
    public OnConfirmClickListener onConfirmClickListener;

    public EditNickNameDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        View dialogView = View.inflate(context, R.layout.include_edit_nick_name_dialog_view, null);
        initViews(dialogView);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogView);
        window = getWindow();
        window.setWindowAnimations(android.R.style.Animation_InputMethod);
        window.getDecorView().setBackgroundResource(android.R.color.transparent);
        setWidth((int) (display.getWidth() * 0.9));
        EditNickNameDialog.this.show();

    }

    private void initViews(View dialogView) {
        tvDialogTitle = dialogView.findViewById(R.id.tv_dialog_title);
        editNickname = dialogView.findViewById(R.id.edit_nickname);
        tvCancel = dialogView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
        tvConfirm = dialogView.findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(this);
    }

    /**
     * 设置输入类型
     *
     * @param type
     */
    public void setInputType(int type) {
        editNickname.setInputType(type);
    }

    /**
     * 限制输入的最大长度
     *
     * @param maxLength
     */
    public void setFilteLength(int maxLength) {
        editNickname.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public interface OnConfirmClickListener {

        void onClick(String nickName);

    }

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTvDialogTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvDialogTitle.setText(title);
        }
    }


    /**
     * 设置显示信息
     *
     * @param editTextMes
     */
    public void setEditNickname(String editTextMes) {
        if (!TextUtils.isEmpty(editTextMes)) {
            editNickname.setText(editTextMes);
            editNickname.setSelection(editTextMes.length());
        }
    }

    /**
     * 设置提示信息
     *
     * @param message
     */
    public void setHitMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            editNickname.setHint(message);
        }
    }


    /**
     * 宽度
     *
     * @param width
     * @return
     */
    public EditNickNameDialog setWidth(int width) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width;
        window.setAttributes(lp);
        return this;

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                EditNickNameDialog.this.dismiss();
                break;
            case R.id.tv_confirm:
                confirmEditNickName();
                break;
        }
    }

    private void confirmEditNickName() {
        String nickName = editNickname.getText().toString().trim();
        if (!TextUtils.isEmpty(nickName)) {
            if (onConfirmClickListener != null) {
                onConfirmClickListener.onClick(nickName);
            }
        } else {
            ToastUtils.showToast(editNickname.getHint());
        }
    }
}
