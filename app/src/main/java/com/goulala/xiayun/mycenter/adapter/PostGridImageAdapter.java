package com.goulala.xiayun.mycenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.utils.SizeUtils;
import com.goulala.xiayun.mycenter.widget.WrapHeightGridView;

import java.util.ArrayList;

/**
 * author      : Z_B
 * date       : 2018/8/31
 * function  : 显示九宫格图片的adapter
 */
public class PostGridImageAdapter extends BaseAdapter {

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    //最大图片显示数量
    private int maxNum = 3;
    //是否显示图片添加按钮
    private boolean isShow = true;
    private Context mContext;
    private ArrayList<String> mDates;

    private onRemoveImageClickListener onRemoveImageClickListener;

    public void setOnRemoveImageClickListener(PostGridImageAdapter.onRemoveImageClickListener onRemoveImageClickListener) {
        this.onRemoveImageClickListener = onRemoveImageClickListener;
    }

    public PostGridImageAdapter(Context context, ArrayList<String> datas) {
        this.mContext = context;
        this.mDates = datas;
    }

    @Override
    public int getCount() {
        if (mDates.size() == maxNum) {
            isShow = false;
            return mDates.size();
        }
        isShow = true;
        return mDates.size() + 1;
    }

    @Override
    public String getItem(int position) {
        return mDates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.include_nine_image_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        if (((WrapHeightGridView) parent).isOnMeasure) {
            return convertView;
        }
        holder = (ViewHolder) convertView.getTag();
        GridView gv = (GridView) parent;
        int horizontalSpacing = SizeUtils.dp2px(8);
        int numColumns = gv.getNumColumns();
        int itemWidth = (gv.getWidth() - (numColumns - 1) * horizontalSpacing
                - gv.getPaddingLeft() - gv.getPaddingRight()) / numColumns;
        ViewGroup.LayoutParams lp = holder.img.getLayoutParams();
        lp.width = itemWidth;
        lp.height = itemWidth;
        holder.img.setLayoutParams(lp);
        if (isShow && position == getCount() - 1) {
            holder.img.setImageResource(R.drawable.ic_camera_bg);
            holder.ivDelete.setVisibility(View.GONE);
        } else {
            ImageLoaderUtils.displaySizeImage(mContext, "file://" + getItem(position), itemWidth, itemWidth, holder.img);
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRemoveImageClickListener != null) {
                        onRemoveImageClickListener.removeClick(position);
                    }
                    mDates.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }

    private static class ViewHolder {
        private ImageView img;
        private ImageView ivDelete;

        public ViewHolder(View view) {
            this.img = (ImageView) view.findViewById(R.id.img);
            this.ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        }
    }


    /**
     * 点击右上角删除图片的监听
     */
    public interface onRemoveImageClickListener {

        void removeClick(int position);

    }


}
