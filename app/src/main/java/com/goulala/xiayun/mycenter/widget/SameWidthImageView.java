package com.goulala.xiayun.mycenter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 显示九宫格图片
 */
public class SameWidthImageView extends android.support.v7.widget.AppCompatImageView {
    public SameWidthImageView(Context context) {
        super(context);
    }

    public SameWidthImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SameWidthImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }
}
