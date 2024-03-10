package com.wymm.common.core.constant;


public interface CommonConstants {
    
    /**
     * 如果自定义代码不是 20000，则判断是失败
     */
    Integer SUCCESS = 20000;
    
    
    /**
     * 客户端错误
     */
    Integer BAD_REQUEST = 40000;
    /**
     * unauthorized,客户端必须对自身进行身份验证才能获得请求的响应
     */
    Integer UNAUTHORIZED = 40001;
    /**
     * 客户端没有访问内容的权限；也就是说，它是未经授权的，因此服务器拒绝提供请求的资源
     */
    Integer FORBIDDEN = 40003;
    
    
    /**
     * 非法 token
     */
    Integer ILLEGAL_TOKEN = 50008;
    /**
     * 其他客户端已登录
     */
    Integer OTHER_CLIENTS_LOGGED_IN = 50012;
    /**
     * token 过期
     */
    Integer TOKEN_EXPIRED = 50014;
}
