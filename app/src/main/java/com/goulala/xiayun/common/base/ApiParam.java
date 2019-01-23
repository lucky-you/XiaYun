package com.goulala.xiayun.common.base;

/**
 * author : Z_B
 * date : 2018/8/30
 * function : 参数常量表
 */
public interface ApiParam {

    String BASE_METHOD_KEY = "method";
    String ITEM_ID_KEY = "item_id";
    String ITEM_NUMBER_KEY = "item_num";
    String ITEM_IDS_KEY = "item_ids";
    String KEYWORD_KEY = "keyword";
    String SORT_KEY = "sort";
    String PAGE_KEY = "page";
    String SIZE_KEY = "size";
    String SIZE_NUMBER_VALUE = "10";//每页的数量为10条
    String ID_DESC_KEY = "id desc";
    String PRICE_DESC_KEY = "price desc";
    String PRICE_ASC_KEY = "price asc";
    String SALES_DESC_KEY = "sales desc";
    String SALES_ASC_KEY = "sales asc";
    String MOBILE_KEY = "mobile";
    String EVENT_KEY = "event";
    String MOBILE_LOGIN_VALUE = "mobilelogin"; // 手机号码登录
    String CAPTCHA_KEY = "captcha";
    String PAGE_NAME = "pagename";
    String TOP_BANNER = "master_banner"; //首页顶部banner
    String RIGHT_BANNER_ONE = "master_top_01"; //首页右侧banner1
    String RIGHT_BANNER_TWO = "master_top_02"; //首页右侧banner2
    String ACTIVE_ID = "active_id";
    String CART_KEY = "carts";
    String MERCHANT_ID_KEY = "merchant_id";
    String CART_IDS = "cart_ids";
    String NAME = "name";
    String PROVINCE_ID = "province_id";
    String CITY_ID = "city_id";
    String AREA_ID = "area_id";
    String ADDRESS = "address";
    String UNIT = "unit";
    String POSTCODE = "postcode";
    String SWITCH = "switch";
    String ADDRESS_ID = "address_id";
    String ITEM_INFO = "item_info";
    String SHARE_USER_ID = "share_user_id";
    String COUPON_ID = "coupon_id";
    String ARRAY_LIST = "array_list";
    String MONEY = "money";
    String TYPE = "type";
    String PAY_NO = "pay_no";
    String PAYPASS = "paypass";
    String OPENID = "openid";
    String UNIONID = "unionid";
    String NICK_NAME = "nickname";
    String HEAD_IMAGE_URL = "headimgurl";
    String SEX = "sex";
    String CATEGORY = "category";
    String CAPTCHA = "captcha";
    String NEW_PASSWORD = "newpassword";
    String AMOUNT = "amount";
    String SHOP_ORDER = "shop_order";
    String ITEM_ORDER = "item_order";
    String NUM = "num";
    String REASON = "reason";
    String DESCRIPTION = "description";
    String IMAGES = "images";
    String ITEM_NUM = "item_num";
    String REMARK = "remark";
    String SERVICE_NO = "service_no";
    String BIO = "bio";
    String AVATAR = "avatar";
    String GENDER = "gender";
    String BIRTHDAY = "birthday";
    String BANK_NAME = "bankname";
    String BANK_CARD = "bankcard";
    String BANK_ID = "bank_id";
    String CARD_NAME = "cardname";
    String VIP_RULE_ID = "vip_rule_id";
    String OPEN_TYPE = "open_type";
    String SERVICE_INFO = "service_info";
    String EXPRESS_COMPANY_ID = "express_company_id";
    String DEVICE_KEY = "device";
    String DEVICE_VALUE = "ios";
    String EXPRESS_NUMBER = "express_number";
    String STATUS = "status";
    String MESSAGE_ID = "message_id";


    /**
     * 订单状态 -1 删除 0 待付款 1待发货（退款中）  2 待收货 （退款退货） 3 确认收货 （退款退货）4 交易失败 (交易取消  超时关闭  主动取消) 5 交易完成
     */
    int THE_ORDER_STATUS_TYPE_OF_TO_PAID = 0;
    int THE_ORDER_STATUS_TYPE_OF_SEND_THE_GOOD = 1;
    int THE_ORDER_STATUS_TYPE_OF_FOR_THE_GOOD = 2;
    int THE_ORDER_STATUS_TYPE_OF_DEAL = 3;
    int THE_ORDER_STATUS_TYPE_OF_TRANCSATION_FAILED = 4;
    int THE_ORDER_STATUS_TYPE_COMPLETED = 5;

    /**
     * 申请售后的状态码
     * 10 申请
     * 20 撤销申请
     * 31 客服审核拒绝
     * 32 客服审核通过
     * 40 买家发货
     * 51 商品审核失败
     * 52 商品审核通过
     * 61 财务审核不通过
     * 62 财务审核通过急速打款
     * 70 完成
     * 80 关闭
     */
    int TO_APPLY_FOR_TYPE = 10;
    int CANCEL_THE_APPLICATION = 20;
    int CUSTOMER_SERVICE_REVIEW_REJECTION = 31;
    int CUSTOMER_SERVICE_APPROVAL = 32;
    int THE_BUYER_TO_DELIVER_GOODS = 40;
    int FAILURE_OF_COMMODITY_AUDIT = 51;
    int APPROVAL_OF_GOODS = 52;
    int FINANCIAL_AUDIT_DID_PASS = 61;
    int FINANCIAL_AUDIT_THROUGH_THE_RAPID_PATMENT = 62;
    int THAT_COMPLETE_TYPE = 70;
    int THAT_SHUT_DOWN_TYPE = 80;

    int CANCEL_THAT_ORDER_TYPE = 105; //取消订单
    int DELETE_THAT_ORDER_TYPE = 106; // 删除订单
    int CONFIRM_THAT_GOOD_TYPE = 107;//确认收货

    int GET_ACCOUNT_BALANCE_TYPE = 111;//获取用户的余额
    int CHECK_PASSWORD_EXIST_TYPE = 112;//检查是否设置了支付密码


    //检查token是否过期
    String CHECK_TOKEN_IS_OVERDUE_VALUE = "api/token/check";

    //获取商品详情
    String GET_GOOD_DETAILS_VALUE = "api/item/detail";

    //收藏商品
    String COLLECTION_THAT_GOOD_VALUE = "api/item_favorite/add";

    //取消商品收藏
    String CANCEL_COLLECTION_THAT_GOOD_VALUE = "api/item_favorite/remove";

    //添加商品到购物车
    String ADD_GOOD_TO_SHOP_CAR_VALUE = "api/item_shoppingcart/inc";

    //搜索结果的获取
    String GET_SEARCH_RESULT_LIST_VALUE = "api/item/search";

    //关键字联想
    String THE_KEYWORD_ASSOCIATION_VALUE = "api/keyword/search";

    //热门搜索
    String GET_HOT_SEARCH_GOOD_LIST_VALUE = "api/keyword/hot";

    //默认关键字
    String GET_DEFAULT_SEARCH_KEYWORDS_VALUE = "api/keyword/get";

    //获取验证码
    String GET_VERIFICATION_CODE_VALUE = "api/sms/send";

    //手机号码登录
    String USE_PHONE_TO_LOGIN_VALUE = "api/user_token/mobilelogin";

    //获取首页广告信息
    String GET_HOME_BANNER_LIST = "api/adver/get";

    //首页超值热卖
    String HOME_VALUE_SEAL_GOOD_LIST = "api/active/hot";

    //首页爆品秒杀
    String HOME_HOT_STYLE_KILL_GOOD_LIST = "api/active/miaosha";

    //首页品质优选
    String THE_QUALITY_OF_THE_OPTIMIZATION_LIST = "api/active/pinzhi";

    //获取购物车列表
    String GET_CHOP_CAR_DATE_LIST_VALUE = "api/item_shoppingcart/index";

    //购物车游客购物车
    String THE_TOURIST_SHOP_LIST = "api/item_shoppingcart/guest";

    //购物车批量添加商品
    String BLUK_ITEMS_TO_YOU_SHOP_CAR = "api/item_shoppingcart/add";

    //购物车添加商品
    String ADD_GOOD_ITEM_TO_YOU_SHOP_CAR = "api/item_shoppingcart/inc";

    //购物车减少商品
    String REDUCE_GOOD_ITEM_FORM_YOU_SHOP_CAR = "api/item_shoppingcart/dec";

    //购物车移除商品
    String DELETE_GOOD_ITEM_FORM_YOU_SHOP_CAR = "api/item_shoppingcart/remove";

    //购物车统计商品总数
    String GET_THE_TOTAL_GOOD_ITEMS_OF_YOU_SHOP_CAR = "api/item_shoppingcart/usercount";

    //猜你喜欢
    String GUESS_YOU_LIKE_URL = "api/item/like";

    //添加收货地址
    String ADD_SHOP_ADDRESS_URL = "api/express_address/create";

    //修改收货地址
    String EDIT_SHOP_ADDRESS_URL = "api/express_address/edit";

    //获取收货地址
    String GET_THE_ADDRESS_LIST_URL = "api/express_address/index";

    //设置为默认收货地址
    String SET_THE_DEFAULT_ADDRESS_URL = "api/express_address/isdefault";

    //删除地址
    String DELETE_THAT_ADDRESS_URL = "api/express_address/remove";

    //确认订单，获取订单信息
    String GET_ORDER_MESSAGE_URL = "api/trade_order/im_settlement";

    //提交订单
    String SUBMIT_ORDER_URL = "api/trade_order/confirm";

    //支付订单
    String PAYMENT_THAT_ORDER_URL = "api/payment/pay_data";

    //检查支付密码是否存在
    String DOES_PASSWORD_EXIST_URL = "api/user_token/payexist";

    //用户余额
    String ACCOUNT_BALANCE_URL = "api/amount/balances";

    //使用微信登录
    String USE_WCCHAT_LOGIN_URL = "api/wechat/signup";

    //微信账号绑定
    String WECHAT_ACCOUNT_BINDING_URL = "api/wechat/bind";

    // 我的收藏列表
    String THE_CLASS_OF_MY_COLLECTION_TYPE = "api/item_favorite/index";

    //我的足迹列表
    String THE_CLASS_OF_MY_FOOTPRINT_TYPE = "api/item_history/index";

    //商品订单列表
    String THAT_ORDER_LIST_URL = "api/trade_order/order_list";

    //验证手机验证码
    String VERIFICATION_CODE_URL = "api/sms/check";

    //获取优惠券的数据
    String GET_COUPON_LIST_URL = "api/coupon/index";

    //设置或者重置佣金支付密码
    String SET_OR_RESET_PASSWORD_URL = "api/user_token/payconfim";

    //取消订单
    String CANCEL_THAT_ORDER_URL = "api/trade_order/cancel";

    //订单详情
    String THAT_ORDER_DETAILS_URL = "api/trade_order/detail";

    //删除订单
    String DELETE_THAT_ORDER_URL = "api/trade_order/del";

    //确认收货
    String CONFIRM_THAT_GOOD = "api/trade_order/delivery";

    //申请售后
    String APPLY_FOR_AFTER_SALES_URL = "api/trade_order/apply_sale";

    //售后确认订单信息
    String REFUND_AFTER_ORDER_MESSAGE_URL = "api/trade_order/sale_settlement";

    //售后详情
    String AFTER_THE_DETAILS_URL = "api/trade_order/sale_detail";

    //撤销申请
    String CANCEL_THAT_APPLICATION_URL = "api/trade_order/revoke";

    //清空足迹
    String EMPEY_THE_FOOTPRINT_URL = "api/item_history/remove";

    //我的收藏和我的足迹统计--个人中心
    String MY_CENTER_COLLECTION_URL = "api/item_favorite/total_count";
    String MY_CENTER_HISTORY_URL = "api/item_history/total_count";

    //获取用户的基本信息
    String GET_USER_BASE_INFO_URL = "api/user_profile/get";

    //修改个人信息
    String EDIT_MEMBER_MESSAGE_URL = "api/user_profile/modify";

    //收支明细
    String PAYMENT_DETAILS_LIST_URL = "api/amount/index";

    //银行卡列表
    String BANK_CARD_LIST_URL = "api/amount/bank";

    //申请提现
    String WITHDRAWAL_AMOUNT_URL = "api/amount/withdraw";

    //开通或者续费会员的规则
    String OPEN_OR_RENEWAL_MEMBER_URL = "api/vip_rule/rule";

    //用户的身份，是否是plus会员
    String CHECK_USER_IS_MEMBER_URL = "api/vip_user/identity";

    //开通或者续费
    String OPEN_OR_RENEWAL_MEMBER_CLICK_URL = "api/vip_user/confirm";

    //获取商品分享的地址链接
    String GET_GOOD_SHARE_URL = "api/item_share/address";

    //获取七牛云的配置
    String GET_QIN_IU_SET_UP_URL = "api/set_up/qiniu";

    //撤销申请
    String CANCEL_THE_APPLICATION_URL = "api/trade_order/revoke";

    //沟通记录
    String COMMUNICATION_RECORD_UTL = "api/service_record/record";

    // 配送企业信息 -->选择物流公司
    String SELECT_LOGISTICS_COMPANY_URL = "api/express_company/get";

    //买家发货-->继续提交 -->买家寄回商品给卖家
    String CONTINUE_TO_SUBMIT_URL = "api/trade_order/after_delivery";

    //新人专享优惠券
    String NEW_COUPLE_COUPONS_LIST_URL = "api/coupon/newbie";

    //领取优惠券
    String GET_THE_COUPONS_URL = "api/coupon/receive";

    //关于我们
    String ABOUT_US_URL = "api/set_up/about";

    //vip优惠榜单
    String MEMBERSHIP_LIST_URL = "api/vip_user/discount";

    //消息中心--》获取分类
    String THE_MESSAGE_CENTER_URL = "api/message/category";

    //获取站内信
    String GET_CATEGORY_MESSAGE_URL = "api/message/index";

    //获取物流信息
    String ACCESS_TO_LOGISTICS_MESSAGE_URL = "api/express_company/detail";

    //阅读站内信
    String READ_STATION_LETTERS_URL = "api/message/read";

    //获取客服中心的群组
    String GET_SERVICE_LIST_URL = "api/custom_service/list";

    //意见箱提交
    String SUBMIT_PLATFORM_MESSAGE_URL = "api/user_opinion/add";


    //获取优惠劵
    String COUPON_URL = "api/coupon/vip";

    //获取优惠券使用说明
    String GET_COUPONS_RULES_URL = "api/set_up/coupon_explain";

    //提现说明
    String GET_WITHDRAWAL_INSTRUCTIONS_URL = "api/set_up/withdrawal_explain";

    //平台规则说明
    String RULES_OF_THE_PLATFORM_URL = "api/set_up/platform_rules";

    //常用说明
    String COMMON_PROBLEMS_URL = "api/set_up/question";

    //会员服务协议
    String MEMBER_SERVICE_AGREEMENT_URL = "api/set_up/vip_explain";
}
