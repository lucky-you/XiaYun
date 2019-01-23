package com.goulala.xiayun.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.home.model.MarqueeViewList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : Z_B
 * @date : 2018/8/14
 * @function : 垂直跑马灯
 */
public class NoticeMarqueeView extends ViewFlipper implements View.OnClickListener {
    private Context mContext;
    private List<MarqueeViewList> mNotices;

    public NoticeMarqueeView(Context context) {
        super(context);
    }

    public NoticeMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        // 轮播间隔时间
        setFlipInterval(3000);
        // 内边距5dp
        setPadding(dp2px(5f), dp2px(5f), dp2px(5f), dp2px(5f));
        // 设置enter和leave动画
        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.notify_in));
        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.notify_out));
    }

    /**
     * 添加需要轮播展示的公告
     *
     * @param notices
     */
    public void addNotice(List<MarqueeViewList> notices) {
        removeAllViews();
        mNotices = notices;
        for (int i = 0; i < notices.size(); i++) {
            View noticeView = View.inflate(mContext, R.layout.include_marque_view_layout, null);
            CircleImageView civPeopleImage = noticeView.findViewById(R.id.civ_people_imageView);
            TextView tvPeopleMessage = noticeView.findViewById(R.id.tv_who_get_money_of_date);
            MarqueeViewList marqueeViewList = notices.get(i);
            tvPeopleMessage.setText(mContext.getString(R.string.who_get_money_of_date, "136****1234", "3分钟", marqueeViewList.withdrawalMoney));
            ImageLoaderUtils.displayAvatar(marqueeViewList.peopleImageUrl,civPeopleImage);
            noticeView.setTag(i);
            noticeView.setOnClickListener(this);
            NoticeMarqueeView.this.addView(noticeView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        MarqueeViewList notice = mNotices.get(position);
        if (mOnNoticeClickListener != null) {
            mOnNoticeClickListener.onNoticeClick(position, notice);
        }
    }

    /**
     * 通知点击监听接口
     */
    public interface OnNoticeClickListener {
        void onNoticeClick(int position, MarqueeViewList notice);
    }

    private OnNoticeClickListener mOnNoticeClickListener;

    /**
     * 设置通知点击监听器
     *
     * @param onNoticeClickListener 通知点击监听器
     */
    public void setOnNoticeClickListener(OnNoticeClickListener onNoticeClickListener) {
        mOnNoticeClickListener = onNoticeClickListener;
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                mContext.getResources().getDisplayMetrics());
    }

}
