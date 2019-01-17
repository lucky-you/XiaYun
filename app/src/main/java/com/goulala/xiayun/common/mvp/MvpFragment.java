package com.goulala.xiayun.common.mvp;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.lib.LibFragment;
import com.goulala.xiayun.common.view.MProgressDialog;


public abstract class MvpFragment<P extends BasePresenter> extends LibFragment {
    protected P mvpPresenter;
    protected MProgressDialog progressDialog;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void firstLoad() {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
        super.firstLoad();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }


    /**
     * 显示对话框
     */
    public MProgressDialog showDialog(String hitMessage) {
        if (progressDialog == null) {
            progressDialog = new MProgressDialog(mContext);
            if (TextUtils.isEmpty(hitMessage)) {
                progressDialog = progressDialog.createLoadingDialog(mContext.getString(R.string.In_the_load));
            } else {
                progressDialog = progressDialog.createLoadingDialog(hitMessage);
            }
            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        return progressDialog;
    }

    /**
     * 关闭提示框
     */
    public void dismissDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }
}
