package com.goulala.xiayun.common.base;

/**
 * author      : Z_B
 * date       : 2018/11/25
 * function  : 常量值
 */
public interface ConstantValue {

    String POSITION = "position";
    String START_MAIN = "start_main";
    String SP_TOKEN = "token";
    String ADDRESS_TYPE = "addressType";
    String DATA = "data";
    String TYPE = "type";
    String PRICE = "price";
    String INDEX = "index";
    String TITLE = "title";
    String URL = "url";
    String ID = "id";
    String USER_NAME = "user_name";
    String USER_ID = "user_id";
    String PHONE_NUMBER = "phone_number";
    String PROVINCES = "provinces";
    String CITY = "city";
    String AREA = "area";
    String LIST = "list";
    String USER_IMAGE_VIEW = "user_imageView";
    String USER_GENDER = "user_gender";
    String MOBIL = "mobil";
    String CAPTCHA = "captcha";
    String AMOUNT = "amount";
    String ORDER_NUMBER = "service_no";
    String IMAGE_LIST = "image_list";
    String FULL_PRICE = "FullPriceReduction";
    String PREFERENTIAL_PRICE = "PreferentialPrice";
    String MONEY = "money";
    String ACTIVE_ID = "active_id";
    String CATEGORY = "category";
    String NICK_NAME = "nick_name";


    String ITEM_DETAIL = "item_detail";//商品详情
    String ADVER_DETAIL = "adver_detail"; //广告详情
    String COUPON_NEWBIE = "coupon_newbie";//新人优惠券
    String VIP_OPEN = "vip_open"; //开通会员
    String ITEM_ACTIVE = "item_active"; //活动专题
    String SCORE_SIGN = "score_sign"; //积分签到


    //轮播切换的时间
    int VP_TURN_TIME = 5000;
    //倒计时总时间
    int TOTAL_COUNT_DOWN_TIME = 60050;
    //倒计时间隔时间
    int COUNT_DOWN_INTERVAL_TIME = 1000;


    int THE_CLASS_OF_MY_COLLECTION_TYPE = 1; // 我的收藏
    int THE_CLASS_OF_MY_FOOTPRINT_TYPE = 2; //我的足迹
    int TYPE_DEFAULT_SEARCH_KEY_WORDS = 3; //默认搜索关键字
    int THE_CLASS_OF_BIND_PHONE_NUMBER_TYPE = 4; //绑定手机号码
    int THE_CLASS_OF_VERIFY_NUMBER_LOGIN_TYPE = 5; //验证号码登录

    int THE_CLASS_TYPE_OF_LOGISTICS_NEWS = 6;//物流消息
    int THE_CLASS_TYPE_OF_AFTER_SELL_NEWS = 7;//售后消息
    int THE_CLASS_TYPE_OF_SYSTEM_NEWS = 8;//系统消息
    int THE_CLASS_TYPE_OF_SERVICE_NEWS = 9;//客服消息

    int TYPE_OF_ADDRESS_CLASS = 10;//添加收货地址
    int TYPE_OF_EDITTEXT_CLASS = 11;// 修改收货地址

    int GET_SHOPPING_ADDRESS_RETURN_TO_ORDER_ACTIVITY = 12; //选择收货地址之后，返回的确认订单界面


    int SET_THE_COMMISSION_PAYMENT_PASSWORD = 13;//设置佣金支付密码

    int RESET_THE_COMMISSION_PAYMENT_PASSWORD = 14;// 重置佣金支付密码

    int THAT_CLASS_TYPE_OF_SHOP_CAR = 15; //从购物袋进来的
    int THAT_CLASS_TYPE_OF_OTHER = 16;// 从其他界面进来的

    int THAT_USE_APPLY_AFTER_SALES_TYPE = 17;//用户点击了申请售后

    int APPLY_FOR_REFUND_GOOD_TYPE = 18; //申请退货
    int APPLY_FOR_REFUND_MONEY_TYPE = 19;//申请退款

    int THE_MEMBER_SIGNATURE = 20;//个性签名

    int THE_TYPE_OF_SHOP_CAR = 21; //购物车--进入商品详情
    int THE_TYPE_OF_OTHER = 22;//其他界面进入商品详情

    int ITEM_SELECT_STATUS_IS_CHECK = 23;//商品选中状态

    int IS_SHOW_DELETE_BUTTON = 24;//是否显示删除按钮

    int IS_ALL_SELECT_GOOD_OF_DELETE = 25; //全选

    int IS_NOT_ALL_SELECT_GOOD_OF_DELETE = 26; //没有全选

    int EMPTY_THAT_FOOTPRINT_GOOD_TYPE = 27; //清空足迹

    int APPLY_FOR_REFUND_GOOD_DETAILS_TYPE = 28;  // 待收货状态下退货详情
    int APPLY_FOR_REFUND_MONEY_DETAILS_TYPE = 29; //待收货状态下退款详情

    int APPLY_FOR_REFUND_MONEY_OF_NOT_SEND_GOOD_TYPE = 30; //待发货状态下的退款详情


    int COUPONS_AVAILABLE_TYPE = 31;// 可用优惠券

    int NOT_COUPONS_AVAILABLE_TYPE = 32;// 不可用优惠券

    int CHOOSE_COUPONS_RETURN_TO_ORDER_ACTIVITY = 33;//选择优惠券之后返回订单界面

    int CLASS_TYPE_OF_ABOUT_US = 34;//关于我们

    int ADVER_DETAILS_TYPE = 35;//广告详情

    int THE_USER_MESSAGE_TYPE = 36; //买家留言

    int RULES_OF_THE_PLATFORM_URL = 37;//平台规则

    int COMMON_PROBLEMS_URL = 38;//常见问题说明

    int COUPON_DESCRIPTION_TYPE = 39;//优惠券说明

    int COMMISSION_DESCRIPTION_TYPE = 40;//佣金说明

    int MEMBER_SERVICE_AGREEMENT_URL = 41;//会员服务协议

    int COLLECTION_THAT_GOOD_TYPE = 42; // 收藏该商品

    int CANCEL_COLLECTION_THAT_GOOD_TYPE = 43;// 取消收藏商品

    int ADD_THAT_GOOD_TO_SHOP_CAR_TYPE = 44; //添加商品到购物车

    int THAT_GOOD_IS_COLLECTION_TYPE = 45; //商品是否收藏

    int THE_TYPE_OF_BIND_WECHAT = 46;//绑定微信

    int PAY_OF_BALANCE_TYPE = 47; //余额支付
    int PAY_OF_ALIPAY_TYPE = 48; //支付宝支付
    int PAY_OF_WECHAT_TYPE = 49; // 微信支付

    int GET_ORDER_MESSAGE_CODE = 50; //获取订单信息

    int SUBMIT_ORDER_MESSAGE_CODE = 51; //提交订单

    int CANCEL_THE_APPLICATION_TYPE = 52;//撤销申请

    int CONTIUNE_TO_SUBMIT_TYPE = 53;//继续提交


}
