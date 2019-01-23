package com.goulala.xiayun.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.goulala.xiayun.common.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by ZhouBin on 2017/7/26.
 * Effect: 微信支付的回调类
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;
    public static WeiXinPayUtils.OnWxPayResultListener wxPayResultListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, WeiXinPayUtils.APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);

    }

    public static void registerWxPayResultListener(WeiXinPayUtils.OnWxPayResultListener PayResultListener) {
        wxPayResultListener = PayResultListener;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_ADD_CARD_TO_EX_CARD_PACKAGE:
            case ConstantsAPI.COMMAND_CHOOSE_CARD_FROM_EX_CARD_PACKAGE:
                String wxOpenId = baseReq.openId;
                String wxTransaction = baseReq.transaction;
                ToastUtils.showToast("wxOpenId=" + wxOpenId + "\n" + "wxTransaction=" + wxTransaction);
                break;
        }

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.getType()) {
            case ConstantsAPI.COMMAND_PAY_BY_WX: //支付
            case ConstantsAPI.COMMAND_ADD_CARD_TO_EX_CARD_PACKAGE: //添加到卡包
                if (baseResp.errCode == 0) { //成功
                    if (wxPayResultListener != null) {
                        wxPayResultListener.onSuccess();
                    }
                } else if (baseResp.errCode == -2) { //取消
                    if (wxPayResultListener != null) {
                        wxPayResultListener.onCancel();
                    }
                } else if (baseResp.errCode == -1) { //失败
                    if (wxPayResultListener != null) {
                        wxPayResultListener.onFailed();
                    }
                }
                break;
        }
        Intent intent = new Intent();//返回App界面
        intent.putExtra("err_code", baseResp.errCode + "");
        sendBroadcast(intent);
        Log.e("TAG", "errCodes" + baseResp.errCode);
        finish();//这里重要，如果没有 finish()；将留在微信支付后的界面.

    }
}
