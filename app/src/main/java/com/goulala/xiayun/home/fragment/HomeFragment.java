package com.goulala.xiayun.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.adapter.HomeViewPageAdapter;
import com.goulala.xiayun.common.banner.BannerUtils;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseApplication;
import com.goulala.xiayun.common.base.BaseMvpFragment;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.base.ConstantValue;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.LogUtils;
import com.goulala.xiayun.common.utils.UserUtils;
import com.goulala.xiayun.common.view.AutoScrollRecyclerView;
import com.goulala.xiayun.common.view.CustomViewPager;
import com.goulala.xiayun.common.widget.DividerGridItemDecoration;
import com.goulala.xiayun.home.activity.HomeGoodsDetailsActivity;
import com.goulala.xiayun.home.activity.MemberSignInActivity;
import com.goulala.xiayun.home.activity.NewExclusiveActivity;
import com.goulala.xiayun.home.activity.SearchActivity;
import com.goulala.xiayun.home.activity.SellLotsOfDetailsActivity;
import com.goulala.xiayun.home.adapter.HomeFootGoodsAdapter;
import com.goulala.xiayun.home.adapter.HomeGoodsAdapter;
import com.goulala.xiayun.home.adapter.MembershipListAdapter;
import com.goulala.xiayun.home.helper.SecondsKillHelper;
import com.goulala.xiayun.home.model.DefaultSearchKeyWords;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.home.model.GoodMessage;
import com.goulala.xiayun.home.model.HomeBannerDate;
import com.goulala.xiayun.home.model.HomeValueSellingBean;
import com.goulala.xiayun.home.model.HotStyleSecondsKill;
import com.goulala.xiayun.home.model.MemberDiscountBean;
import com.goulala.xiayun.home.presenter.HomeFragmentPresenter;
import com.goulala.xiayun.home.view.IHomeFragmentView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author      : Z_B
 * date       : 2019/1/17
 * function  :
 */
public class HomeFragment extends BaseMvpFragment<HomeFragmentPresenter> implements IHomeFragmentView {

    private Banner homeTitleBanner;
    private AutoScrollRecyclerView homeMarqueeView;
    private Banner homeRightBannerOne;
    private Banner homeRightBannerTwo;
    private TabLayout homeTimeRadioButtons;
    private CustomViewPager homeSecondKillGoodViewPager;
    private LinearLayout llHomeHotStyleKill;
    private RecyclerView hotValueSealRecyclerView;
    private RecyclerView homeFootGoodRecyclerView;
    private NestedScrollView nestedScrollView;
    private SmartRefreshLayout smartRefreshLayout;
    private TextView tvHomeLocalCityName;
    private ImageView ivHomeLocalImageView;
    private LinearLayout llHomeLocal;
    private TextView tvSearchKeyWords;
    private LinearLayout llHomeSearch;
    private LinearLayout llHomeSearchTitle;
    private LinearLayout llHomeTopTitle;
    private LinearLayout llValueSellingLayout;
    private String defaultKeyWords;
    private int currentPage = 1;//当前页码
    private int mScrollY = 0;
    //超值热卖的adapter
    private HomeGoodsAdapter homeGoodsAdapter;
    //超值热卖的数据
    private List<HomeValueSellingBean> homeValueSellingBeans = new ArrayList<>();
    //品质优选的adapter
    private HomeFootGoodsAdapter homeFootGoodsAdapter;
    //品质优选的数据
    private List<GoodItemMessage> homeFootGoodList = new ArrayList<>();
    //顶部banner的数据
    private List<HomeBannerDate> homeBannerDateList = new ArrayList<>();
    //右侧banner1
    private List<HomeBannerDate> homeBannerRightOneDateList = new ArrayList<>();
    //右侧 banner2
    private List<HomeBannerDate> homeBannerRightTwoDateList = new ArrayList<>();
    //会员优惠榜单
    private List<MemberDiscountBean> MembersList = new ArrayList<>();
    private MembershipListAdapter membershipListAdapter; //优惠榜单的adapter
    private LinearSmoothScroller mScroller;
    private Disposable mAutoTask;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_homefragment_layout;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.home_fake_status_bar));
        homeTitleBanner = get(R.id.home_title_banner);
        homeMarqueeView = get(R.id.home_MarqueeView);
        homeRightBannerOne = get(R.id.HomeMemberSignIn_banner);
        homeRightBannerTwo = get(R.id.home_new_envelopes_banner);
        homeTimeRadioButtons = get(R.id.home_time_radioButtons);
        homeSecondKillGoodViewPager = get(R.id.home_Second_kill_good_viewPager);
        llHomeHotStyleKill = get(R.id.ll_home_hot_style_kill);
        hotValueSealRecyclerView = get(R.id.hotValueSealRecyclerView);
        homeFootGoodRecyclerView = get(R.id.home_foot_good_RecyclerView);
        nestedScrollView = get(R.id.nestedScrollView);
        smartRefreshLayout = get(R.id.smartRefreshLayout);
        tvHomeLocalCityName = get(R.id.tv_home_local_city_name);
        ivHomeLocalImageView = get(R.id.iv_home_local_ImageView);
        llHomeLocal = get(R.id.ll_home_local);
        llHomeLocal.setOnClickListener(this);
        tvSearchKeyWords = get(R.id.tv_search_key_words);
        llHomeSearch = get(R.id.ll_home_search);
        llHomeSearch.setOnClickListener(this);
        llHomeSearchTitle = get(R.id.ll_home_search_title);
        llHomeTopTitle = get(R.id.ll_home_top_title);
        llValueSellingLayout = get(R.id.ll_Value_selling_layout);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        smartRefreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        ClassicsFooter classicsFooter = new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale);
        classicsFooter.REFRESH_FOOTER_FINISH = mContext.getString(R.string.All_content_is_displayed);
        smartRefreshLayout.setRefreshFooter(classicsFooter);
        hotValueSealRecyclerView.setNestedScrollingEnabled(false);
        homeFootGoodRecyclerView.setNestedScrollingEnabled(false);
        getHomeDate();
    }

    private void getHomeDate() {
        checkTokenIsOverdue(); //检查token是否过期
        searchDefaultKeyWords(); //获取搜索的默认关键字
        getHomeBannerDate(ApiParam.TOP_BANNER); //顶部banner
        getHomeBannerDate(ApiParam.RIGHT_BANNER_ONE);//右侧第一个banner
        getHomeBannerDate(ApiParam.RIGHT_BANNER_TWO);//右侧第二个banner
        getHotStyleKillGood();  // 爆款秒杀
        getValueSellingGood(); //超值热卖
        getMembershipList(); //会员优惠榜单
        getHomePageTheQualityOptimization();//品质优选
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {

        homeGoodsAdapter = new HomeGoodsAdapter(homeValueSellingBeans);
        hotValueSealRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        hotValueSealRecyclerView.setAdapter(homeGoodsAdapter);
        hotValueSealRecyclerView.setNestedScrollingEnabled(false);
        hotValueSealRecyclerView.setHasFixedSize(true);

        homeFootGoodsAdapter = new HomeFootGoodsAdapter(homeFootGoodList, HomeFootGoodsAdapter.HOME_FRAGMENT_ADAPTER_TYPE);
        homeFootGoodRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        homeFootGoodRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, mContext.getResources().getColor(R.color.color_f3f3f3), 7));
        homeFootGoodRecyclerView.setAdapter(homeFootGoodsAdapter);
        homeFootGoodRecyclerView.setNestedScrollingEnabled(false);
        homeFootGoodRecyclerView.setHasFixedSize(true);

        //会员优惠榜单的数据
        membershipListAdapter = new MembershipListAdapter(MembersList);
        homeMarqueeView.setLayoutManager(new LinearLayoutManager(mContext));
        homeMarqueeView.setAdapter(membershipListAdapter);


        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getHomeDate();
                refreshLayout.finishRefresh();
            }
        });
        //超值热卖的头部ImageView的点击事件
        homeGoodsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_recommended_banner) {
                    String title = homeGoodsAdapter.getItem(position).getTitle();
                    int activityId = homeGoodsAdapter.getItem(position).getActive_id();
                    SellLotsOfDetailsActivity.start(mContext, title, String.valueOf(activityId), ConstantValue.THAT_CLASS_TYPE_OF_OTHER);
                }
            }
        });
        //查看更多
        homeGoodsAdapter.setOnViewToMoreClickListener(new HomeGoodsAdapter.OnViewToMoreClickListener() {
            @Override
            public void onViewToMore(int position) {
                String title = homeGoodsAdapter.getItem(position).getTitle();
                int activityId = homeGoodsAdapter.getItem(position).getActive_id();
                SellLotsOfDetailsActivity.start(mContext, title, String.valueOf(activityId), ConstantValue.THAT_CLASS_TYPE_OF_OTHER);
            }
        });
        //品质优选的点击事件
        homeFootGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int goodId = homeFootGoodsAdapter.getItem(position).getId();
                HomeGoodsDetailsActivity.start(mContext, goodId, ConstantValue.THE_TYPE_OF_OTHER);
            }
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int lastScrollY = 0;
            int height = DensityUtil.dp2px(170);
            int color = ContextCompat.getColor(BaseApplication.getInstance(), R.color.white) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < height) {
                    scrollY = Math.min(height, scrollY);
                    mScrollY = scrollY > height ? height : scrollY;
                    llHomeSearchTitle.setBackgroundColor(((255 * mScrollY / height) << 24) | color);
                }
                if (scrollY == 0) {
                    tvHomeLocalCityName.setTextColor(mContext.getResources().getColor(R.color.white));
                    ivHomeLocalImageView.setImageResource(R.drawable.ic_home_local);
                    llHomeSearch.setBackground(mContext.getResources().getDrawable(R.drawable.shape_home_search_background));
                } else {
                    tvHomeLocalCityName.setTextColor(mContext.getResources().getColor(R.color.color_6f6f6f_black));
                    ivHomeLocalImageView.setImageResource(R.drawable.ic_home_local_black);
                    llHomeSearch.setBackground(mContext.getResources().getDrawable(R.drawable.shape_home_search_grey_background));
                }
                lastScrollY = scrollY;
            }
        });
        //头部的banner
        homeTitleBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String bannerScheme = homeBannerDateList.get(position).getScheme();
                String title = homeBannerDateList.get(position).getName();
                String url = homeBannerDateList.get(position).getDetail();
                String id = homeBannerDateList.get(position).getPath();
                bannerClickToActivity(bannerScheme, title, url, id);
            }
        });
        //右侧banner1
        homeRightBannerOne.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String bannerScheme = homeBannerRightOneDateList.get(position).getScheme();
                String title = homeBannerRightOneDateList.get(position).getName();
                String url = homeBannerRightOneDateList.get(position).getDetail();
                String id = homeBannerRightOneDateList.get(position).getPath();
                bannerClickToActivity(bannerScheme, title, url, id);
            }
        });
        //右侧banner2
        homeRightBannerTwo.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String bannerScheme = homeBannerRightTwoDateList.get(position).getScheme();
                String title = homeBannerRightTwoDateList.get(position).getName();
                String url = homeBannerRightTwoDateList.get(position).getDetail();
                String id = homeBannerRightTwoDateList.get(position).getPath();
                bannerClickToActivity(bannerScheme, title, url, id);
            }
        });
        mScroller = new LinearSmoothScroller(mContext) {

            //将移动的置顶显示
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            //控制速度，这里注意当速度过慢的时候可能会形成流式的效果，因为这里是代表移动像素的速度，
            // 当定时器中每隔的2秒之内正好或者还未移动一个item的高度的时候会出现，前一个还没移动完成又继续移动下一个了，就形成了流滚动的效果了
            // 这个问题后续可通过重写另外一个方法来进行控制，暂时就先这样了
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 3f / displayMetrics.density;
            }
        };

    }

    /**
     * banner 的点击跳转时间处理
     * @param scheme 定义的跳转协议,和后台约定好的字段
     */
    private void bannerClickToActivity(String scheme, String title, String url, String Id) {
        switch (scheme) {
            case ConstantValue.ITEM_DETAIL: //商品详情
                HomeGoodsDetailsActivity.start(mContext, Integer.parseInt(Id), ConstantValue.THE_TYPE_OF_OTHER);
                break;
            case ConstantValue.ADVER_DETAIL: //广告详情
//                WebDetailsActivity.start(mContext, title, url, ConstantValue.ADVER_DETAILS_TYPE);
                break;
            case ConstantValue.COUPON_NEWBIE: //新人优惠券
                NewExclusiveActivity.start(mContext);
                break;
            case ConstantValue.VIP_OPEN: //开通会员
                if (checkLogin())
//                    TheMemberCenterActivity.start(mContext);
                    break;
            case ConstantValue.ITEM_ACTIVE://活动专题
                SellLotsOfDetailsActivity.start(mContext, title, Id, ConstantValue.THAT_CLASS_TYPE_OF_OTHER);
                break;
            case ConstantValue.SCORE_SIGN://积分签到
                MemberSignInActivity.start(mContext);
                break;

        }

    }

    /**
     * 默认关键字
     */
    private void searchDefaultKeyWords() {
        Map<String, String> defaultParam = new HashMap<>();
        defaultParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_DEFAULT_SEARCH_KEYWORDS_VALUE);
        String defaultSearchJson = JsonUtils.toJson(defaultParam);
        LogUtils.showLog(userToken, defaultSearchJson);
        mvpPresenter.getDefaultSearch(userToken, defaultSearchJson);
    }

    /**
     * 获取banner的数据
     */
    private void getHomeBannerDate(String bannerType) {
        Map<String, String> bannerParam = new HashMap<>();
        bannerParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_HOME_BANNER_LIST);
        bannerParam.put(ApiParam.PAGE_NAME, bannerType);
        String bannerJson = JsonUtils.toJson(bannerParam);
        LogUtils.showLog(userToken, bannerJson);
        mvpPresenter.getHomeBannerDate(userToken, bannerJson, bannerType);
    }

    /**
     * 获取超值热卖的数据
     */
    private void getValueSellingGood() {
        Map<String, String> valueSealParam = new HashMap<>();
        valueSealParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.HOME_VALUE_SEAL_GOOD_LIST);
        String valueSealParamJson = JsonUtils.toJson(valueSealParam);
        LogUtils.showLog(userToken, valueSealParamJson);
        mvpPresenter.getHomeValueSellingGood(userToken, valueSealParamJson);

    }

    /**
     * 爆品秒杀的数据
     */
    private void getHotStyleKillGood() {
        Map<String, String> hotStyleGoodParam = new HashMap<>();
        hotStyleGoodParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.HOME_HOT_STYLE_KILL_GOOD_LIST);
        String hotStyleGoodParamJson = JsonUtils.toJson(hotStyleGoodParam);
        LogUtils.showLog(userToken, hotStyleGoodParamJson);
        mvpPresenter.getHotStyleSecondsKill(mContext, userToken, hotStyleGoodParamJson);

    }

    /**
     * 获取品质优选的数据
     */
    private void getHomePageTheQualityOptimization() {
        Map<String, String> qualityOptimizationParam = new HashMap<>();
        qualityOptimizationParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.THE_QUALITY_OF_THE_OPTIMIZATION_LIST);
        qualityOptimizationParam.put(ApiParam.PAGE_KEY, String.valueOf(currentPage));
        qualityOptimizationParam.put(ApiParam.SIZE_KEY, ApiParam.SIZE_NUMBER_VALUE);
        String homePageTheQualityOptimizationJson = JsonUtils.toJson(qualityOptimizationParam);
        LogUtils.showLog(userToken, homePageTheQualityOptimizationJson);
        mvpPresenter.getHomeQualityTheOptimization(mContext, userToken, homePageTheQualityOptimizationJson);

    }

    /**
     * 会员优惠榜单
     */
    private void getMembershipList() {
        Map<String, String> memberListParam = new HashMap<>();
        memberListParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.MEMBERSHIP_LIST_URL);
        String memberListParamJson = JsonUtils.toJson(memberListParam);
        mvpPresenter.getMembershipList(userToken, memberListParamJson);

    }

    /**
     * 检查token是否过期
     */
    private void checkTokenIsOverdue() {
        Map<String, String> tokenParam = new HashMap<>();
        tokenParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CHECK_TOKEN_IS_OVERDUE_VALUE);
        String tokenParamJson = JsonUtils.toJson(tokenParam);
        mvpPresenter.checkUserTokenIsOverdue(userToken, tokenParamJson);
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.ll_home_local:
                //定位
                break;
            case R.id.ll_home_search:
                //搜索
                SearchActivity.start(mContext, defaultKeyWords);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        startAuto();
//        homeMarqueeView.scrollToPosition(0);
    }

    @Override
    public void onDestroy() {
        stopAuto();
        super.onDestroy();
    }

    public void startAuto() {
        if (mAutoTask != null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
        }
        homeMarqueeView.scrollToPosition(0);
        mAutoTask = Observable.interval(1, 2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //自动滚动
                        mScroller.setTargetPosition(aLong.intValue());
                        homeMarqueeView.getLayoutManager().startSmoothScroll(mScroller);
                    }
                });
    }

    private void stopAuto() {
        if (mAutoTask != null && !mAutoTask.isDisposed()) {
            mAutoTask.dispose();
            mAutoTask = null;
        }
    }

    @Override
    protected HomeFragmentPresenter createPresenter() {
        return new HomeFragmentPresenter(this);
    }

    @Override
    public void getDefaultSearch(DefaultSearchKeyWords historyList) {
        if (historyList != null) {
            defaultKeyWords = historyList.getName();
            if (!TextUtils.isEmpty(defaultKeyWords)) {
                tvSearchKeyWords.setText(defaultKeyWords);
            }
        }
    }

    @Override
    public void getHomeBannerSuccess(List<HomeBannerDate> bannerDate, String bannerType) {
        if (bannerDate != null && bannerDate.size() > 0) {
            switch (bannerType) {
                case ApiParam.TOP_BANNER:
                    this.homeBannerDateList = bannerDate;
                    BannerUtils.loadHomePageBanner(homeTitleBanner, homeBannerDateList, ApiParam.TOP_BANNER);
                    break;
                case ApiParam.RIGHT_BANNER_ONE:
                    this.homeBannerRightOneDateList = bannerDate;
                    BannerUtils.loadHomePageBanner(homeRightBannerOne, homeBannerRightOneDateList, ApiParam.RIGHT_BANNER_ONE);
                    break;
                case ApiParam.RIGHT_BANNER_TWO:
                    this.homeBannerRightTwoDateList = bannerDate;
                    BannerUtils.loadHomePageBanner(homeRightBannerTwo, homeBannerRightTwoDateList, ApiParam.RIGHT_BANNER_TWO);
                    break;
            }
        } else {
            switch (bannerType) {
                case ApiParam.TOP_BANNER:
                    homeTitleBanner.setBackground(mContext.getResources().getDrawable(R.drawable.ic_banner_default_image));
                    break;
                case ApiParam.RIGHT_BANNER_ONE:
                    homeRightBannerOne.setBackground(mContext.getResources().getDrawable(R.drawable.ic_banner_default_image));
                    break;
                case ApiParam.RIGHT_BANNER_TWO:
                    homeRightBannerTwo.setBackground(mContext.getResources().getDrawable(R.drawable.ic_banner_default_image));
                    break;
            }
        }
    }

    @Override
    public void getHomeValueSellingGood(List<HomeValueSellingBean> homeValueSellingBeanList) {
        this.homeValueSellingBeans = homeValueSellingBeanList;
        if (homeValueSellingBeanList != null && homeValueSellingBeanList.size() > 0) {
            llValueSellingLayout.setVisibility(View.VISIBLE);
            homeGoodsAdapter.setNewData(homeValueSellingBeans);
        } else {
            llValueSellingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void getHotStyleSecondsKillSuccess(ArrayList<HotStyleSecondsKill> hotStyleSecondsKills) {
        if (hotStyleSecondsKills != null && hotStyleSecondsKills.size() > 0) {
            llHomeHotStyleKill.setVisibility(View.VISIBLE);
            List<Fragment> mFragments = new ArrayList<>();
            for (int i = 0; i < hotStyleSecondsKills.size(); i++) {
                mFragments.add(HomeSecondKillGoodFragment.newInstance(i, hotStyleSecondsKills));
            }
            HomeViewPageAdapter homeSecondGoodViewPagerAdapter = new HomeViewPageAdapter(getChildFragmentManager(), mFragments);
            homeSecondKillGoodViewPager.setAdapter(homeSecondGoodViewPagerAdapter);
            homeTimeRadioButtons.setupWithViewPager(homeSecondKillGoodViewPager);
            SecondsKillHelper.setTabLayoutViews(mContext, homeTimeRadioButtons, SecondsKillHelper.getTimeList(hotStyleSecondsKills));
        } else {
            llHomeHotStyleKill.setVisibility(View.GONE);
        }
    }

    @Override
    public void getHomeQualityTheOptimization(GoodMessage homeValueSellingBean) {
        if (homeValueSellingBean != null) {
            List<GoodItemMessage> HomeQualityTheOptList = homeValueSellingBean.getData();
            if (HomeQualityTheOptList != null && HomeQualityTheOptList.size() > 0) {
                this.homeFootGoodList = HomeQualityTheOptList;
                homeFootGoodsAdapter.setNewData(homeFootGoodList);
            }
        }
    }

    @Override
    public void getMembershipList(List<MemberDiscountBean> memberDiscountBeanList) {
        if (memberDiscountBeanList != null && memberDiscountBeanList.size() > 0) {
            MembersList = memberDiscountBeanList;
            membershipListAdapter.setNewData(MembersList);
        } else {
//            FrameLayout frameLayout=new FrameLayout(mContext);
//            frameLayout.setBackground(mContext.getResources().getDrawable(R.drawable.ic_banner_default_image));
//            membershipListAdapter.setEmptyView(frameLayout);
        }
    }

    @Override
    public void checkUserTokenIsOverdue(UserInfo userToken) {
        if (userToken != null) {
            if (TextUtils.isEmpty(userToken.getToken())) {
                // token过期了
                showToast(mContext.getString(R.string.Token_expired_please_login_again));
                UserUtils.loginOut(mContext);
//                LoginActivity.start(mContext);
            } else {
                //token没有过期
            }
        }
    }

    @Override
    public void onNewWorkException(String message) {
        showToast(message);
    }

    @Override
    public void onRequestFailure(int resultCode, String message) {
        showToast(message);

    }
}
