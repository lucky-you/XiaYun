package com.goulala.xiayun.common.pickerview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.goulala.xiayun.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * author      : Z_B
 * date       : 2018/9/26
 * function  : 选择时间的帮助类
 */
public class PickerViewTimeUtils {

    /**
     * 选择时间
     * @param mContext
     * @param view
     * @param onSelectTimeClickListener Dialog 模式下，在底部弹出
     */
    public static void selectTimePickerView(Context mContext, View view, final OnSelectTimeClickListener onSelectTimeClickListener) {

        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (onSelectTimeClickListener != null) {
                    onSelectTimeClickListener.onDateTime(getDateTime(date));
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setCancelColor(mContext.getResources().getColor(R.color.color_9f9f9f))
                .setSubmitColor(mContext.getResources().getColor(R.color.color_e53d3d))
                .setDate(Calendar.getInstance())//系统当前时间
                .setRangDate(startCalendar(), endCalendar())//起始终止年月日设定
                .isCyclic(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        pvTime.show(view);
    }

    //获取当前时间
    private static Calendar startCalendar() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int year = calendar.get(Calendar.YEAR) - 60;    //获取年,当前年份-60年
        int month = calendar.get(Calendar.MONTH);   //获取月份，0表示1月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int hour = calendar.get(Calendar.HOUR_OF_DAY);       //获取当前小时
        int minute = calendar.get(Calendar.MINUTE);          //获取当前分钟
        calendar.set(year, month, day, hour, minute);
        return calendar;

    }

    //获取结束时间,
    private static Calendar endCalendar() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int year = calendar.get(Calendar.YEAR);    //获取年,当前年份
        int month = calendar.get(Calendar.MONTH);   //获取月份，0表示1月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int hour = calendar.get(Calendar.HOUR_OF_DAY);       //获取当前小时
        int minute = calendar.get(Calendar.MINUTE);          //获取当前分钟
        calendar.set(year, month, day, hour, minute);
        return calendar;
    }

    /**
     * 时间戳转字符串
     * 可根据需要自行截取数据显示
     */
    private static String getDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
