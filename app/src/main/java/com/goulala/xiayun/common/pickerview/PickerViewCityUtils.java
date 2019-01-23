package com.goulala.xiayun.common.pickerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.goulala.xiayun.R;
import com.goulala.xiayun.mycenter.model.ProvinceCityBean;

import java.util.ArrayList;

/**
 * author : Z_B
 * date : 2018/8/28
 * function : 省市区三级选择的帮助类
 */
public class PickerViewCityUtils {

    private ArrayList<ProvinceCityBean> provinceList = new ArrayList<>(); //省份
    private ArrayList<ArrayList<ProvinceCityBean>> provinceCityList = new ArrayList<>();//省、市
    private ArrayList<ArrayList<ArrayList<ProvinceCityBean>>> provinceCityAreaList = new ArrayList<>();//省、市、区
    private static final int MSG_LOAD_DATA = 0x0001; // 开始解析数据
    private static final int MSG_LOAD_SUCCESS = 0x0002; // 解析数据成功
    private Activity mActivity;
    private OnCitySelectClickListener onCitySelectClickListener;

    public PickerViewCityUtils(Activity activity) {
        this.mActivity = activity;
        mHandler.sendEmptyMessage(MSG_LOAD_DATA); //发个消息，开始解析数据
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    new Thread() {
                        @Override
                        public void run() {
                            // 子线程中解析省市区数据
                            initJsonData();
                        }
                    }.start();
                    break;
                case MSG_LOAD_SUCCESS:
                    //获取数据成功,显示控件
                    showPickerView();
                    break;
            }
        }
    };

    public interface OnCitySelectClickListener {

        /**
         * @param selectName 选择的地址名称
         * @param provinceId 省份ID
         * @param cityId     城市ID
         * @param areaId     区县ID
         */
        void onSelectCityResult(String selectName, int provinceId, int cityId, int areaId);

    }

    public void setOnCitySelectClickListener(OnCitySelectClickListener onCitySelectClickListener) {
        this.onCitySelectClickListener = onCitySelectClickListener;
    }

    /**
     * 弹出选择器
     */
    private void showPickerView() {
        if (provinceList.size() < 1 || provinceCityAreaList.size() < 1) return;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String provinceName = provinceList.get(options1).getPickerViewText();
                int provinceId = provinceList.get(options1).getId();
                String cityName = provinceCityList.get(options1).get(options2).getPickerViewText();
                int cityId = provinceCityList.get(options1).get(options2).getId();
                String areaName = provinceCityAreaList.get(options1).get(options2).get(options3).getPickerViewText();
                int areaId = provinceCityAreaList.get(options1).get(options2).get(options3).getId();
                String selectName;
                if (cityName.contains(provinceName)) { //四大直辖市的省名称和市名称是一样的
                    selectName = cityName + areaName;
                } else {
                    selectName = provinceName + cityName + areaName;
                }
                if (onCitySelectClickListener != null) {
                    onCitySelectClickListener.onSelectCityResult(selectName, provinceId, cityId, areaId);
                }
            }
        })
                .setTitleText(mActivity.getString(R.string.select_city))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setCancelColor(mActivity.getResources().getColor(R.color.color_9f9f9f))
                .setSubmitColor(mActivity.getResources().getColor(R.color.color_e53d3d))
                .setDecorView((ViewGroup) mActivity.getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
//        pvOptions.setPicker(provinceList);//一级选择器
//        pvOptions.setPicker(provinceList, provinceCityList);//二级选择器
        pvOptions.setPicker(provinceList, provinceCityList, provinceCityAreaList);//三级选择器
        pvOptions.show();
    }

    /**
     * 解析数据
     */
    private void initJsonData() {
        String JsonData = new GetJsonDataUtil().getJson(mActivity, "province_id.json");//获取assets目录下的json文件数据
        ArrayList<ProvinceCityBean> jsonBean = new GetJsonDataUtil().parseData(JsonData);//用Gson 转成实体
        provinceList = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<ProvinceCityBean> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<ProvinceCityBean>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getChild().size(); c++) {//遍历该省份的所有城市
                ProvinceCityBean CityName = jsonBean.get(i).getChild().get(c);
                CityList.add(CityName);//添加城市
                ArrayList<ProvinceCityBean> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getChild().get(c).getChild() == null
                        || jsonBean.get(i).getChild().get(c).getChild().size() == 0) {
                    City_AreaList.add(new ProvinceCityBean());
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getChild().get(c).getChild());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            provinceCityList.add(CityList);
            provinceCityAreaList.add(Province_AreaList);
        }
        //发消息，解析数据完成
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
}
