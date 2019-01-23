package com.goulala.xiayun.common.utils;

import android.content.Context;
import android.text.TextUtils;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.callback.CancelOrDetermineClickListener;
import com.goulala.xiayun.common.view.CustomDialog;
import com.goulala.xiayun.home.dialog.ThePaymentDialog;
import com.goulala.xiayun.mycenter.callback.OnRefundGoodOrMoneyClickListener;
import com.goulala.xiayun.mycenter.dialog.ApplyForAfterSalesDialog;
import com.goulala.xiayun.mycenter.dialog.EditNickNameDialog;
import com.goulala.xiayun.mycenter.dialog.OperatingResultDialog;

/**
 * @author : Z_B
 * @date : 2018/8/20
 * @function : 对话框的帮助类
 */
public final class AlertDialogUtils {


    /**
     * 自定义dialog
     */
    public static void showCustomerDialog(Context context, String showTitle, final CancelOrDetermineClickListener onClickListener) {
        showCustomerDialog(context, showTitle, "", "", onClickListener);

    }

    /**
     * 自定义dialog
     */
    public static void showCustomerDialog(Context context, String showTitle, String leftTitle, String rightTitle, final CancelOrDetermineClickListener onClickListener) {
        CustomDialog customDialog = new CustomDialog(context);
        customDialog.setContent(showTitle);
        if (TextUtils.isEmpty(leftTitle)) {
            customDialog.setLeftCancel(context.getString(R.string.cancel));
        } else {
            customDialog.setLeftCancel(leftTitle);
        }
        if (TextUtils.isEmpty(rightTitle)) {
            customDialog.setRightSure(context.getString(R.string.determine));
        } else {
            customDialog.setRightSure(rightTitle);
        }
        customDialog.setCancelOrDetermineClickListener(onClickListener);

    }


    /**
     * 支付的dialog
     */
    public static void showPaymentDialog(Context context, String totalMoney, String balance, final ThePaymentDialog.OnPayResultListener onPayResultListener) {
        final ThePaymentDialog paymentDialog = new ThePaymentDialog(context);
        paymentDialog.setTvTotalMoney(totalMoney);
        if (TextUtils.isEmpty(balance)) {
            paymentDialog.setLackOfBalance(); //余额不足
        } else {
            paymentDialog.setBalancePayMoney(balance);//可以使用余额支付
        }
        paymentDialog.setOnPayResultListener(new ThePaymentDialog.OnPayResultListener() {
            @Override
            public void chooseTypeToPay(int type) {
                if (onPayResultListener != null) {
                    onPayResultListener.chooseTypeToPay(type);
                }
                paymentDialog.dismiss();
            }

            @Override
            public void closePayDialog() {
                if (onPayResultListener != null) {
                    onPayResultListener.closePayDialog();
                }
                paymentDialog.dismiss();
            }
        });


    }

    /**
     * 处理结果返回的dialog
     */
    public static OperatingResultDialog showOperatingResultDialog(Context context, String resultTitle) {

        return showOperatingResultDialog(context, 0, resultTitle, 0);
    }


    /**
     * 处理结果返回的dialog
     */
    public static OperatingResultDialog showOperatingResultDialog(Context context, int imageViewId, String resultTitle, int width) {
        final OperatingResultDialog operatingResultDialog = new OperatingResultDialog(context);
        operatingResultDialog.setResultTitle(resultTitle);
        if (0 != imageViewId) {
            operatingResultDialog.setResultImageView(imageViewId);
        }
        if (0 != width) {
            operatingResultDialog.setWidth(SizeUtils.dp2px(width));
        }
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                timer.cancel();
//                operatingResultDialog.dismiss();
//            }
//        }, 1000);

        return operatingResultDialog;

    }

    /**
     * 修改昵称的dialog
     *
     * @param context
     * @param hitName
     * @param onConfirmClickListener
     */
    public static void showNickNameDialog(Context context, String hitName, final EditNickNameDialog.OnConfirmClickListener onConfirmClickListener) {
        final EditNickNameDialog editNickNameDialog = new EditNickNameDialog(context);
        editNickNameDialog.setEditNickname(hitName);
        editNickNameDialog.setOnConfirmClickListener(new EditNickNameDialog.OnConfirmClickListener() {
            @Override
            public void onClick(String nickName) {
                if (onConfirmClickListener != null) {
                    onConfirmClickListener.onClick(nickName);
                }
                editNickNameDialog.dismiss();
            }
        });
    }

    /**
     * 申请售后 退款或者退货的dialog
     *
     * @param context
     * @param onRefundGoodOrMoneyClickListener
     */
    public static void refundGoodOrMoneyDialog(Context context, final OnRefundGoodOrMoneyClickListener onRefundGoodOrMoneyClickListener) {
        final ApplyForAfterSalesDialog applyForAfterSalesDialog = new ApplyForAfterSalesDialog(context);
        applyForAfterSalesDialog.setOnRefundGoodOrMoneyClickListener(new OnRefundGoodOrMoneyClickListener() {
            @Override
            public void onRefundGoodClick() {
                if (onRefundGoodOrMoneyClickListener != null) {
                    onRefundGoodOrMoneyClickListener.onRefundGoodClick();
                }
                applyForAfterSalesDialog.dismiss();
            }

            @Override
            public void onRefundMoneyClick() {
                if (onRefundGoodOrMoneyClickListener != null) {
                    onRefundGoodOrMoneyClickListener.onRefundMoneyClick();
                }
                applyForAfterSalesDialog.dismiss();
            }
        });

    }


}
