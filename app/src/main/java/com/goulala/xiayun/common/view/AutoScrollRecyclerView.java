package com.goulala.xiayun.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AutoScrollRecyclerView extends RecyclerView {

    public AutoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //禁止手动滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
