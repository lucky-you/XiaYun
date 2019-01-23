package com.goulala.xiayun.common.pickerview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.goulala.xiayun.R;
import com.goulala.xiayun.mycenter.model.BankCardList;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/10/12
 * function  :  条件选择器的帮助类
 */
public class PickerViewConditionsUtils {

    /**
     * 条件选择器
     *
     * @param mActivity                       上下文
     * @param mDateList                       数据源
     * @param onSelectConditionsClickListener 选择的回调
     */
    public static void selectConditionsView(Activity mActivity, final List<BankCardList> mDateList, final OnSelectConditionsClickListener onSelectConditionsClickListener) {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (onSelectConditionsClickListener != null) {
                    onSelectConditionsClickListener.onConditionsSelect(mDateList.get(options1).getName(), mDateList.get(options1).getId());
                }
            }
        })
                .setCancelColor(mActivity.getResources().getColor(R.color.color_e53d3d))
                .setSubmitColor(mActivity.getResources().getColor(R.color.color_e53d3d))
                .setDecorView((ViewGroup) mActivity.getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        pvOptions.setPicker(mDateList);
        pvOptions.show();

    }
}
