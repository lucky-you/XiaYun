package com.goulala.xiayun.wxapi;

import android.content.Context;

import com.tencent.mm.opensdk.modelbiz.AddCardToWXCardPackage;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

/**
 * Created by : Z_B on 2017/12/26.
 * Effect :  微信支付的帮助类
 */

public class WeiXinPayUtils {

    public static final String APP_ID = "wx28a1ed75f6e246a4";

    public static final String AppSecret = "ca81c07c8881dfd9aca6fe76dcd7ad63";

    /**
     * 使用微信支付
     *
     * @param context
     * @param req
     * @param wxPayResultListener
     */
    public static void useWeChatPay(Context context, PayReq req, OnWxPayResultListener wxPayResultListener) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, APP_ID, false);
        api.registerApp(APP_ID);
        api.sendReq(req);
        WXPayEntryActivity.registerWxPayResultListener(wxPayResultListener);
    }

    /**
     * 微信卡券添加到卡包
     *
     * @param context
     * @param req
     * @param cardArray
     * @param wxPayResultListener
     */
    public static void AddCardToWXCardPackage(Context context, AddCardToWXCardPackage.Req req, List<AddCardToWXCardPackage.WXCardItem> cardArray, OnWxPayResultListener wxPayResultListener) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, APP_ID, false);
        api.registerApp(APP_ID);
        req.cardArrary = cardArray;
        api.sendReq(req);
        WXPayEntryActivity.registerWxPayResultListener(wxPayResultListener);

    }


    public static boolean isExist(Context context) {
        IWXAPI WxApi = WXAPIFactory.createWXAPI(context, APP_ID, false);
        return WxApi.isWXAppInstalled();
    }


    public static class OnWxPayResultListener {

        public OnWxPayResultListener() {
        }

        public void onSuccess() {

        }

        public void onCancel() {

        }

        public void onFailed() {
        }

    }


}