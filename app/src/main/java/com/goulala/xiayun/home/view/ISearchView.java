package com.goulala.xiayun.home.view;

import com.goulala.xiayun.common.db.SearchHistory;
import com.goulala.xiayun.common.mvp.IBaseView;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2019/1/23
 * function  :
 */
public interface ISearchView extends IBaseView {

    /**
     * 热门搜索
     */
    void getHotSearch(List<SearchHistory> historyList);

    /**
     * 搜索关联
     */
    void getSearchRelevanceDate(List<SearchHistory> historyList);
}
