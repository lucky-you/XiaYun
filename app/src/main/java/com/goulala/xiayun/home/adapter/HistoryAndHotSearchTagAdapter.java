package com.goulala.xiayun.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.goulala.xiayun.R;
import com.goulala.xiayun.common.model.SearchHistory;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * @author : Z_B
 * @date : 2018/8/10
 * @function : 搜索的tag Adapter
 */
public class HistoryAndHotSearchTagAdapter extends TagAdapter<SearchHistory> {

    private Context mContext;

    public HistoryAndHotSearchTagAdapter(List<SearchHistory> datas, Context context) {
        super(datas);
        this.mContext = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, SearchHistory item) {
        TextView tvSearchKey = (TextView) View.inflate(mContext, R.layout.include_search_tag_view, null);
        tvSearchKey.setText(item.getName());
        return tvSearchKey;
    }
}
