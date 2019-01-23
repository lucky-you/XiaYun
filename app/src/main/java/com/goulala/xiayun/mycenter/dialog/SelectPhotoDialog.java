package com.goulala.xiayun.mycenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.goulala.xiayun.R;

/**
 * Created by ZhouBin on 2017/8/24.
 * Effect: 选择图片的diaLog
 */

public class SelectPhotoDialog extends Dialog implements View.OnClickListener {


    private TextView tvPhotoAlbums;
    private TextView tvTakePictures;
    private TextView tvCancel;
    private OnItemSelectClickListener onItemSelectClickListener;

    public SelectPhotoDialog(Context context) {
        super(context);
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        View popupView = View.inflate(context, R.layout.include_select_item_photo_view, null);
        initViews(popupView);
        window.setContentView(popupView);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.windowAnimations = R.style.bottomInWindowAnim;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        SelectPhotoDialog.this.show();
    }

    private void initViews(View popupView) {
        tvPhotoAlbums = popupView.findViewById(R.id.tv_Photo_albums);
        tvTakePictures = popupView.findViewById(R.id.tv_Take_pictures);
        tvCancel = popupView.findViewById(R.id.tv_cancel);
        tvPhotoAlbums.setOnClickListener(this);
        tvTakePictures.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

    }


    /**
     * 设置选项一
     *
     * @param text
     */
    public void setPhotoAlbumsText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvPhotoAlbums.setText(text);
        }

    }

    /**
     * 设置选项二
     *
     * @param text
     */
    public void setSelectTakeText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvTakePictures.setText(text);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Photo_albums:
                if (onItemSelectClickListener != null) {
                    onItemSelectClickListener.itemSelectOne();
                }
                break;
            case R.id.tv_Take_pictures:
                if (onItemSelectClickListener != null) {
                    onItemSelectClickListener.itemSelectTwo();
                }
                break;
            case R.id.tv_cancel:
                SelectPhotoDialog.this.dismiss();
                break;
        }
    }


    public interface OnItemSelectClickListener {

        void itemSelectOne(); //选项一

        void itemSelectTwo();  //选项二

    }

    public void setOnItemSelectClickListener(OnItemSelectClickListener onItemSelectClickListener) {
        this.onItemSelectClickListener = onItemSelectClickListener;
    }
}
