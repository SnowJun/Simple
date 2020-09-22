package org.simple.net.request;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 * 请求方式枚举
 */
public enum  RequestMethod {

    /**
     * get请求
     */
    METHOD_GET("GET"),
    /**
     * post请求
     */
    METHOD_POST("POST"),
    /**
     * put请求
     */
    METHOD_PUT("PUT"),
    /**
     * head请求
     */
    METHOD_HEAD("HEAD"),
    /**
     * patch请求
     */
    METHOD_PATCH("PATCH"),
    /**
     * options请求
     */
    METHOD_OPTIONS("OPTIONS"),
    /**
     * trace请求
     */
    METHOD_TRACE("TRACE"),
    /**
     * connect请求
     */
    METHOD_CONNECT("CONNECT"),
    /**
     * delete请求
     */
    METHOD_DELETE("DELETE");


    private String method;

    public String getMethod() {
        return method;
    }

    RequestMethod(String method) {
        this.method = method;
    }

}
