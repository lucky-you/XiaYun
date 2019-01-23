package com.goulala.xiayun.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.utils.InputMethodKeyBroadUtils;
import com.goulala.utils.JsonUtils;
import com.goulala.view.LoadDialog;
import com.goulala.xiayun.Basemvp.BaseMVPActivity;
import com.goulala.xiayun.R;
import com.goulala.xiayun.base.ApiParam;
import com.goulala.xiayun.common.db.DBManager;
import com.goulala.xiayun.common.model.SearchHistory;
import com.goulala.xiayun.common.utils.ConstantValue;
import com.goulala.xiayun.common.utils.EmptyViewUtils;
import com.goulala.xiayun.common.widget.DivideLineItemDecoration;
import com.goulala.xiayun.home.adapter.HistoryAndHotSearchTagAdapter;
import com.goulala.xiayun.home.adapter.StartSearchAdapter;
import com.goulala.xiayun.home.contact.SearchContact;
import com.goulala.xiayun.home.presenter.SearchPresenter;
import com.orhanobut.logger.Logger;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索的activity
 */
public class SearchActivity extends BaseMVPActivity<SearchContact.presenter> implements SearchContact.view {


    private EditText editKey;
    private LinearLayout llHomeSearch;
    private TextView tvCancel;
    private TextView tvDelete;
    private TagFlowLayout flyHistoryList;
    private View historyDivide;
    private LinearLayout rlSearchHistory;
    private TagFlowLayout flyHotSearchList;
    private LinearLayout llHotSearchList;
    private LinearLayout llHaveSearchAndHotSearchList;
    private RecyclerView searchRecyclerView;
    private HistoryAndHotSearchTagAdapter historyAndHotSearchTagAdapter;
    private StartSearchAdapter startSearchAdapter;
    private String defaultSearchKeyWord;
    private static int HISTORY_SEARCH_TAG_CLICK_TYPE = 1;
    private static int HOT_SEARCH_TAG_CLICK_TYPE = 2;
    private List<SearchHistory> historyList = new ArrayList<>();//历史搜索的集合
    private List<SearchHistory> hotSearchList = new ArrayList<>();//热门搜索的集合
    private List<SearchHistory> lenovoSearchList = new ArrayList<>(); //关键词联想的集合

    public static void start(Context context, String searchKeyWord) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(ConstantValue.TITLE, searchKeyWord);
        context.startActivity(intent);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_search);
        editKey = get(R.id.edit_key);
        llHomeSearch = get(R.id.ll_home_search);
        llHomeSearch.setOnClickListener(this);
        tvCancel = get(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);
        tvDelete = get(R.id.tv_delete);
        tvDelete.setOnClickListener(this);
        flyHistoryList = get(R.id.fly_History_list);
        historyDivide = get(R.id.history_Divide);
        rlSearchHistory = get(R.id.rl_search_history);
        flyHotSearchList = get(R.id.fly_Hot_Search_list);
        llHotSearchList = get(R.id.ll_hot_search_list);
        llHaveSearchAndHotSearchList = get(R.id.ll_have_search_and_hot_search_list);
        searchRecyclerView = get(R.id.search_recyclerView);
        get(R.id.rl_back).setOnClickListener(this);

    }

    @Override
    protected void bindViews() {
        editKey.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editKey.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        defaultSearchKeyWord = getIntent().getStringExtra(ConstantValue.TITLE);
        if (!TextUtils.isEmpty(defaultSearchKeyWord)) {
            editKey.setHint(defaultSearchKeyWord);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        historyList = getHistoryData();
        if (historyList.size() > 0) {
            rlSearchHistory.setVisibility(View.VISIBLE);
            historyDivide.setVisibility(View.VISIBLE);
            historyAndHotSearchTagAdapter = new HistoryAndHotSearchTagAdapter(getHistoryData(), mContext);
            flyHistoryList.setAdapter(historyAndHotSearchTagAdapter);
        } else {
            rlSearchHistory.setVisibility(View.GONE);
            historyDivide.setVisibility(View.GONE);
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        topSearch();
        //关键词联想的adapter
        startSearchAdapter = new StartSearchAdapter(lenovoSearchList);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        searchRecyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 1));
        searchRecyclerView.setAdapter(startSearchAdapter);

    }

    /**
     * 热门搜索
     */
    private void topSearch() {
        Map<String, String> hotSearchParam = new HashMap<>();
        hotSearchParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_HOT_SEARCH_GOOD_LIST_VALUE);
        String hotSearchParamJson = JsonUtils.toJson(hotSearchParam);
        Logger.d("xy", hotSearchParamJson + "token=" + userToken);
        presenter.getHotSearch(userToken, hotSearchParamJson);

    }

    @Override
    protected void setListener() {
        tagFlowLayoutAdapterClick(HISTORY_SEARCH_TAG_CLICK_TYPE, flyHistoryList);
        tagFlowLayoutAdapterClick(HOT_SEARCH_TAG_CLICK_TYPE, flyHotSearchList);
        //搜索键盘
        editKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    editTextSearchListener();
                    return true;
                }
                return false;
            }
        });
        //EditText的输入长度的变化
        editKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 输入的内容变化的监听
                String keyWords = charSequence.toString().trim();
                if (TextUtils.isEmpty(keyWords)) {
//                    showToast(mContext.getString(R.string.please_edit_key_words));
//                    return;
                    llHaveSearchAndHotSearchList.setVisibility(View.VISIBLE);
                    searchRecyclerView.setVisibility(View.GONE);
                    lenovoSearchList.clear();
                } else {
                    Map<String, String> keywordAssociation = new HashMap<>();
                    keywordAssociation.put(ApiParam.BASE_METHOD_KEY, ApiParam.THE_KEYWORD_ASSOCIATION_VALUE);
                    keywordAssociation.put(ApiParam.KEYWORD_KEY, keyWords);
                    String keywordAssociationJson = JsonUtils.toJson(keywordAssociation);
                    presenter.getSearchRelevanceDate(userToken, keywordAssociationJson);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //关键词联想的adapter的监听
        startSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String keyWords = startSearchAdapter.getItem(position).getName();
                SearchResultActivity.start(mContext, keyWords);
                insertHistoryDao(keyWords);
                editKey.getText().clear();
            }
        });
    }

    //搜索键盘和右上角搜索的处理
    private void editTextSearchListener() {
        String keyWords = editKey.getText().toString().trim();
        if (TextUtils.isEmpty(keyWords)) {
            String defaultKeyWord = editKey.getHint().toString().trim();
            insertHistoryDao(defaultKeyWord);
            InputMethodKeyBroadUtils.hideKeyboard(this);
            SearchResultActivity.start(mContext, defaultKeyWord);
            editKey.getText().clear();
        } else {
            insertHistoryDao(keyWords);
            InputMethodKeyBroadUtils.hideKeyboard(this);
            SearchResultActivity.start(mContext, keyWords);
            editKey.getText().clear();
        }
    }

    //标签的点击事件
    private void tagFlowLayoutAdapterClick(final int tagType, TagFlowLayout tagFlowLayout) {
        if (tagFlowLayout == null) return;
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (HISTORY_SEARCH_TAG_CLICK_TYPE == tagType) {
                    String keyWords = historyList.get(position).getName();
                    insertHistoryDao(keyWords);
                    SearchResultActivity.start(mContext, keyWords);
                } else {
                    String keyWords = hotSearchList.get(position).getName();
                    insertHistoryDao(keyWords);
                    SearchResultActivity.start(mContext, keyWords);
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_home_search:
                InputMethodKeyBroadUtils.showKeyboard(this, editKey);
                break;
            case R.id.tv_cancel:
                //右上角搜索
                editTextSearchListener();
                break;
            case R.id.tv_delete:
                //删除
                DBManager.getInstance().getHistoryDao().deleteAll();
                rlSearchHistory.setVisibility(View.GONE);
                historyDivide.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public SearchContact.presenter createPresenter() {
        return new SearchPresenter(this);
    }


    @Override
    public void getHotSearch(List<SearchHistory> historyList) {
        if (historyList != null && historyList.size() > 0) {
            llHotSearchList.setVisibility(View.VISIBLE);
            hotSearchList = historyList;
            historyAndHotSearchTagAdapter = new HistoryAndHotSearchTagAdapter(historyList, mContext);
            flyHotSearchList.setAdapter(historyAndHotSearchTagAdapter);
        } else {
            llHotSearchList.setVisibility(View.GONE);
        }

    }


    //关键字联想的数据
    @Override
    public void getSearchRelevanceDate(List<SearchHistory> historyList) {
        this.lenovoSearchList = historyList;
        if (lenovoSearchList == null || lenovoSearchList.size() > 0) {
            llHaveSearchAndHotSearchList.setVisibility(View.GONE);
            searchRecyclerView.setVisibility(View.VISIBLE);
            startSearchAdapter.setNewData(lenovoSearchList);

        } else {
            llHaveSearchAndHotSearchList.setVisibility(View.GONE);
            searchRecyclerView.setVisibility(View.VISIBLE);
            EmptyViewUtils.bindEmptyView(mContext, startSearchAdapter, mContext.getString(R.string.No_associative_keywords));
        }

    }

    @Override
    public void showLoadingDialog(String msg) {
        LoadDialog.show(mContext);

    }

    @Override
    public void dismissLoadingDialog() {
        LoadDialog.dismiss(mContext);
    }

    @Override
    public void onRequestFailure(String error) {
        showLoadingDialog(error);
    }

}
