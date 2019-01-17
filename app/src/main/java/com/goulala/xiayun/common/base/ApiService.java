package com.goulala.xiayun.common.base;


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


}
