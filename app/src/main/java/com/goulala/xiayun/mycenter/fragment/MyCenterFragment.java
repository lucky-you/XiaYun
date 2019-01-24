package com.goulala.xiayun.mycenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.goulala.xiayun.R;
import com.goulala.xiayun.common.base.ApiParam;
import com.goulala.xiayun.common.base.BaseMvpFragment;
import com.goulala.xiayun.common.imageloader.ImageLoaderUtils;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.common.utils.BarUtils;
import com.goulala.xiayun.common.utils.JsonUtils;
import com.goulala.xiayun.common.utils.UserUtils;
import com.goulala.xiayun.mycenter.activity.AllOrdersActivity;
import com.goulala.xiayun.mycenter.activity.CustomerServiceCenterActivity;
import com.goulala.xiayun.mycenter.activity.MyCollectionActivity;
import com.goulala.xiayun.mycenter.activity.MyCommissionActivity;
import com.goulala.xiayun.mycenter.activity.MyFootprintActivity;
import com.goulala.xiayun.mycenter.activity.MyProfileActivity;
import com.goulala.xiayun.mycenter.activity.SetUpTheActivity;
import com.goulala.xiayun.mycenter.activity.TheCouponsActivity;
import com.goulala.xiayun.mycenter.activity.TheMemberCenterActivity;
import com.goulala.xiayun.mycenter.activity.TheMessageCenterActivity;
import com.goulala.xiayun.mycenter.adapter.CommonlyUsedToolsAdapter;
import com.goulala.xiayun.mycenter.model.CollectionAndHistoryBean;
import com.goulala.xiayun.mycenter.model.UserIsMembersBean;
import com.goulala.xiayun.mycenter.presenter.MyCenterPresenter;
import com.goulala.xiayun.mycenter.view.IMyCenterView;
import com.goulala.xiayun.shopcar.activity.ShippingAddressActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author      : Z_B
 * date       : 2019/1/17
 * function  :
 */
public class MyCenterFragment extends BaseMvpFragment<MyCenterPresenter> implements IMyCenterView,
        BaseQuickAdapter.OnItemClickListener {

    private CircleImageView civUserImage;
    private TextView tvUserName;
    private TextView tvRegisteredMembers;
    private TextView tvMyCollection;
    private TextView tvMyFootprint;
    private LinearLayout llForThePayment;
    private LinearLayout llToSendTheGoods;
    private LinearLayout llForTheGoods;
    private LinearLayout llTheRefund;
    private RecyclerView CommonlyUsedToolsRecyclerView;
    private CommonlyUsedToolsAdapter commonlyUsedToolsAdapter;
    private final Class<?>[] mClasses = {
            TheCouponsActivity.class, //优惠券
            ShippingAddressActivity.class,//收货地址
            CustomerServiceCenterActivity.class,//联系客服
            MyCommissionActivity.class, //我的佣金
//            MemberSignInActivity.class,//我的签到
            TheMemberCenterActivity.class //会员中心
    };


    public static MyCenterFragment newInstance() {
        MyCenterFragment myCenterFragment = new MyCenterFragment();
        Bundle bundle = new Bundle();
        myCenterFragment.setArguments(bundle);
        return myCenterFragment;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int loadViewLayout() {
        return R.layout.include_mycenterfragment_layout;
    }

    @Override
    public void bindViews(View contentView) {
        BarUtils.addMarginTopEqualStatusBarHeight(get(R.id.home_fake_status_bar));
        get(R.id.iv_ic_my_nb_but_su).setOnClickListener(this);
        get(R.id.iv_ic_my_nb_but_news).setOnClickListener(this);
        civUserImage = get(R.id.civ_user_image);
        civUserImage.setOnClickListener(this);
        tvUserName = get(R.id.tv_user_name);
        tvRegisteredMembers = get(R.id.tv_Registered_members);
        tvRegisteredMembers.setOnClickListener(this);
        tvMyCollection = get(R.id.tv_My_collection);
        get(R.id.ll_My_collection).setOnClickListener(this);
        tvMyFootprint = get(R.id.tv_My_footprint);
        get(R.id.ll_My_footprint).setOnClickListener(this);
        get(R.id.tv_all_the_orders).setOnClickListener(this);
        llForThePayment = get(R.id.ll_For_the_payment);
        llForThePayment.setOnClickListener(this);
        llToSendTheGoods = get(R.id.ll_To_send_the_goods);
        llToSendTheGoods.setOnClickListener(this);
        llForTheGoods = get(R.id.ll_For_the_goods);
        llForTheGoods.setOnClickListener(this);
        llTheRefund = get(R.id.ll_the_refund);
        llTheRefund.setOnClickListener(this);
        CommonlyUsedToolsRecyclerView = get(R.id.Commonly_used_tools_RecyclerView);
    }

    @Override
    public void processLogic(Bundle savedInstanceState) {
        List<String> titlesList = Arrays.asList(mContext.getResources().getStringArray(R.array.commonly_tool_titles));
        int[] imageArrayList = {
                R.drawable.ic_my_tool_icon_coupon,
                R.drawable.ic_my_tool_icon_address,
                R.drawable.ic_my_tool_icon_sc,
                R.drawable.ic_my_tool_icon_commission,
//                R.drawable.ic_my_tool_icon_sign,
                R.drawable.ic_my_tool_icon_vip
        };
        commonlyUsedToolsAdapter = new CommonlyUsedToolsAdapter(titlesList, imageArrayList);
        CommonlyUsedToolsRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        CommonlyUsedToolsRecyclerView.setAdapter(commonlyUsedToolsAdapter);
        commonlyUsedToolsAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDate();
    }

    private void getDate() {
        //获取用户的基本信息
        getUserInfoMessage();
        //用户是否是plus
        checkUserIsMember();
        //获取收藏和足迹数量
        getCollectionAndHistoryDate(IMyCenterView.COLLECTION_TYPE, ApiParam.MY_CENTER_COLLECTION_URL);
        getCollectionAndHistoryDate(IMyCenterView.MY_FOOTPRINT_TYPE, ApiParam.MY_CENTER_HISTORY_URL);
    }

    /**
     * 获取用户信息
     */
    private void getUserInfoMessage() {
        Map<String, String> baseUserInfoParam = new HashMap<>();
        baseUserInfoParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.GET_USER_BASE_INFO_URL);
        String baseUserInfoParamJson = JsonUtils.toJson(baseUserInfoParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getUserInfoMessage(userToken, baseUserInfoParamJson);
        }
    }

    /**
     * 获取我的收藏和我的足迹
     */
    private void getCollectionAndHistoryDate(int requestType, String requestUrl) {
        Map<String, String> collectionParam = new HashMap<>();
        collectionParam.put(ApiParam.BASE_METHOD_KEY, requestUrl);
        String collectionParamJson = JsonUtils.toJson(collectionParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.getCollectionAndHistoryDate(requestType, userToken, collectionParamJson);
        }
    }

    /**
     * 判断是否是plus会员
     */
    private void checkUserIsMember() {
        Map<String, String> checkParam = new HashMap<>();
        checkParam.put(ApiParam.BASE_METHOD_KEY, ApiParam.CHECK_USER_IS_MEMBER_URL);
        String checkParamJson = JsonUtils.toJson(checkParam);
        if (!TextUtils.isEmpty(userToken)) {
            mvpPresenter.usersAreNotMembers(userToken, checkParamJson);
        }
    }

    @Override
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_ic_my_nb_but_su:
                //设置
                if (checkLogin())
                    SetUpTheActivity.start(mContext);
                break;
            case R.id.iv_ic_my_nb_but_news:
                //消息中心
                if (checkLogin())
                    TheMessageCenterActivity.start(mContext);
                break;
            case R.id.civ_user_image:
                //个人中心
                if (checkLogin())
                    MyProfileActivity.start(mContext);
                break;
            case R.id.tv_Registered_members:
                break;
            case R.id.ll_My_collection:
                //我的收藏
                if (checkLogin())
                    MyCollectionActivity.start(mContext);
                break;
            case R.id.ll_My_footprint:
                //我的足迹
                if (checkLogin())
                    MyFootprintActivity.start(mContext);
                break;
            case R.id.tv_all_the_orders:
                if (checkLogin())
                    AllOrdersActivity.start(mContext, 0);
                break;
            case R.id.ll_For_the_payment:
                if (checkLogin())
                    AllOrdersActivity.start(mContext, 1);
                break;
            case R.id.ll_To_send_the_goods:
                if (checkLogin())
                    AllOrdersActivity.start(mContext, 2);
                break;
            case R.id.ll_For_the_goods:
                if (checkLogin())
                    AllOrdersActivity.start(mContext, 3);
                break;
            case R.id.ll_the_refund:
                if (checkLogin())
                    AllOrdersActivity.start(mContext, 4);
                break;
        }
    }

    @Override
    protected MyCenterPresenter createPresenter() {
        return new MyCenterPresenter(this);
    }

    @Override
    public void getCollectionAndHistoryDate(int requestType, CollectionAndHistoryBean collectionAndHistoryBean) {
        if (collectionAndHistoryBean != null) {
            if (IMyCenterView.COLLECTION_TYPE == requestType) {
                tvMyCollection.setText(collectionAndHistoryBean.getFavorite_count() + "");
            } else {
                tvMyFootprint.setText(collectionAndHistoryBean.getHistory_count() + "");
            }
        }
    }

    @Override
    public void getUserInfoMessage(UserInfo userInfo) {
        if (userInfo != null) {
            UserUtils.loginIn(userInfo);
            ImageLoaderUtils.displayAvatar(userInfo.getAvatar(), civUserImage);
            tvUserName.setText(userInfo.getNickname());
        }
    }

    @Override
    public void usersAreNotMembers(UserIsMembersBean userIsMembersBean) {
        if (userIsMembersBean != null) {
            long memberEndTime = userIsMembersBean.getEnd_time();//此时间为会员到期时间
            if (memberEndTime > 0) {
                //是plus会员
                tvRegisteredMembers.setText(mContext.getString(R.string.Plus_member));
            } else {
                //不是会员
                tvRegisteredMembers.setText(mContext.getString(R.string.Registered_members));
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (checkLogin()) {
            Intent intent = new Intent(mContext, mClasses[position]);
            startActivity(intent);
        }
    }
}
