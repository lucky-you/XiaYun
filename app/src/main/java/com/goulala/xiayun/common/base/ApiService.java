package com.goulala.xiayun.common.base;


import com.goulala.xiayun.common.db.SearchHistory;
import com.goulala.xiayun.common.model.AccountBalance;
import com.goulala.xiayun.common.model.UserInfo;
import com.goulala.xiayun.home.model.CoupleCouponsBean;
import com.goulala.xiayun.home.model.DefaultSearchKeyWords;
import com.goulala.xiayun.home.model.GoodItemMessage;
import com.goulala.xiayun.home.model.GoodMessage;
import com.goulala.xiayun.home.model.GoodsDetailsMessage;
import com.goulala.xiayun.home.model.HomeBannerDate;
import com.goulala.xiayun.home.model.HomeValueSellingBean;
import com.goulala.xiayun.home.model.HotStyleSecondsKill;
import com.goulala.xiayun.home.model.MemberDiscountBean;
import com.goulala.xiayun.mycenter.model.AllOrderListMessage;
import com.goulala.xiayun.mycenter.model.ApplyRefundDetailsDate;
import com.goulala.xiayun.mycenter.model.BankCardList;
import com.goulala.xiayun.mycenter.model.CollectGoodMessage;
import com.goulala.xiayun.mycenter.model.CollectionAndHistoryBean;
import com.goulala.xiayun.mycenter.model.CouponMessage;
import com.goulala.xiayun.mycenter.model.LogisticsDetailsBean;
import com.goulala.xiayun.mycenter.model.LogisticsMessageBean;
import com.goulala.xiayun.mycenter.model.MessageCenterList;
import com.goulala.xiayun.mycenter.model.OrderDetailsMessage;
import com.goulala.xiayun.mycenter.model.PaymentDetailsBean;
import com.goulala.xiayun.mycenter.model.QinIuBean;
import com.goulala.xiayun.mycenter.model.RecordBean;
import com.goulala.xiayun.mycenter.model.RefundMoneyDate;
import com.goulala.xiayun.mycenter.model.RefundResultDate;
import com.goulala.xiayun.mycenter.model.ServiceCenterList;
import com.goulala.xiayun.mycenter.model.ShoppingAddressList;
import com.goulala.xiayun.mycenter.model.TheMemberCenterBean;
import com.goulala.xiayun.mycenter.model.UserIsMembersBean;
import com.goulala.xiayun.mycenter.model.VipCouponTicketMessage;
import com.goulala.xiayun.shopcar.model.OrderMessage;
import com.goulala.xiayun.shopcar.model.ShopCarBaseDate;
import com.goulala.xiayun.wxapi.WxPayReqInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 网络请求
 */
public interface ApiService {


    //baseUrl
//    String API_SERVER_URL = "https://miyue.nacy.cc/";  //-->线下
    String API_SERVER_URL = "https://demo.nacy.cc/"; //-->线上
    String TOKEN = "token";
    String PARAM = "param";
    String HEADER_URL = "api/v1";
    String BASE_KEY = "method";
    String PAGE_SIZE_VALUE = "10";//每次加载10条数据


    //七牛云的服务器
    String QIN_IU_YUN_URL = "http://xyfile.nacy.cc/";

    //使用高德sdk通过ip定位
    String MAP_API_IP_ADDRESS = "https://restapi.amap.com/v3/ip?/";

    //高德web服务的key
    String WAB_SERVICE_MAP_KEY = "be04099c377ea26e1f8b0c792e3bf445";

    //7陌客服
    String QIMO_IM_ACCESSID = "9cce4250-18ae-11e9-be14-5dd98cef17aa";


    /**
     * 检查token 是否过期
     * 绑定手机号
     * 手机号码登录
     * 使用微信登录
     * 微信账号绑定
     * 获取用户信息
     * 修改用户信息
     * 返回数据为UserInfo 全局通用
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<UserInfo>> publicResultOfUserInfoDate(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * data返回为boolean值数据的通用请求,全局通用
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<Boolean>> publicResultOfBooleanDate(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 使用余额支付、支付宝支付
     * 全局返回数据为String类型的通用
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<String>> publicResultOfStringDate(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * Integer 返回类型的通用
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<Integer>> publicResultOfIntegerDate(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 热门搜索
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<SearchHistory>>> getHotSearchResult(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 获取搜索关键字
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<DefaultSearchKeyWords>> getDefaultSearchResult(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 搜索的关键字联想
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<SearchHistory>>> getSearchRelevance(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 搜索商品
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<GoodMessage>> searchResultRequest(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 商品详情
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<GoodsDetailsMessage>> getHomeGoodDetails(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 收藏列表
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<CollectGoodMessage>> getCollectThatGoodDateList(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 获取购物车的商品
     * 和 游客购物车商品公用
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<ShopCarBaseDate>>> getShopCarGoodDateList(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 购物车猜你喜欢的商品
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<GoodItemMessage>>> getGuessYouLikeGoodList(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 首页banner的数据
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<HomeBannerDate>>> getHomeBannerDate(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 爆品秒杀
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<ArrayList<HotStyleSecondsKill>>> HotStyleKillProducts(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 超值热卖
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<HomeValueSellingBean>>> HomeValueSellingGood(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 品质优选
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<GoodMessage>> HomeQualityTheOptimization(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 获取收货地址列表
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<ShoppingAddressList>>> getShoppingAddressList(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 确认订单，获取订单信息
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<OrderMessage>> getMakeSureTheOrderMessage(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 微信支付
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<WxPayReqInfo>> useWeChatPayment(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 获取账户余额，全局通用
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<AccountBalance>> getAccountBalance(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 获取优惠券列表
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<CouponMessage>> getCouponMessage(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 订单列表
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<AllOrderListMessage>> getAllOrderList(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 订单详情
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<OrderDetailsMessage>> getOrderDetailsMessage(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 获取退款申请数据
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<RefundMoneyDate>> getRefundMoneyDate(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 提交退款申请
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<RefundResultDate>> submitApplyRefundDate(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 退款详情
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<ApplyRefundDetailsDate>> applyRefundDetailsDate(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 获取我的收藏和足迹的列表
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<CollectionAndHistoryBean>> getCollectionAndHistory(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 收支明细
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<PaymentDetailsBean>> getPaymentDetailsList(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 获取银行卡列表 和 获取物流公司公用的
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<BankCardList>>> getBankCardList(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 开通会员、续费会员的规则
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<TheMemberCenterBean>> getOpenOrRenewalMemberRules(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 判断是否是plus会员
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<UserIsMembersBean>> checkUserIsMember(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 获取七牛云的配置信息，全局通用
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<QinIuBean>> getQinIuSetMessage(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 售后获取沟通记录
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<RecordBean>>> getCommunicationRecord(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 新人专享优惠券
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<CoupleCouponsBean>> getCouponDateList(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 使用高德SDK,通过ip定位
     */
    @GET(MAP_API_IP_ADDRESS)
    Observable<ResponseBody> formIpLoadCityLocationBean(@Query("ip") String ipAddress, @Query("output") String outputJson, @Query("key") String mapKey);

    /**
     * 获取会员优惠榜单
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<MemberDiscountBean>>> getMembershipList(@Field(TOKEN) String userToken, @Field(PARAM) String param);


    /**
     * 消息中心--》消息列表
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<MessageCenterList>>> getMessageCenterList(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 物流详情
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<LogisticsDetailsBean>> getLogisticsDetails(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 消息中心---》物流消息
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<LogisticsMessageBean>> getTheLogisticsMessage(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 阅读消息
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<Object>> publicResultOfObjectData(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 客服中心
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<List<ServiceCenterList>>> getServiceCenterList(@Field(TOKEN) String userToken, @Field(PARAM) String param);

    /**
     * 会员优惠券
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ResultResponse<VipCouponTicketMessage>> getVipCoupon(@Field(TOKEN) String userToken, @Field(PARAM) String param);


}
