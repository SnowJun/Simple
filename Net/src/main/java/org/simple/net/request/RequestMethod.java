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


    /**
     * 请求方法名
     */
    private String method;
    /**
     *
     */
    private boolean hasBody;

    public String getMethod() {
        return method;
    }

    RequestMethod(String method) {
        this.method = method;
        switch (method){
            case "POST":
            case "PUT":
            case "DELETE":
            case "PATCH":
            case "OPTIONS":
                hasBody = true;
                break;
            default:
                hasBody = false;
                break;
        }
    }

    /**
     * 获取是否有body
     * @return
     */
    public boolean isHasBody() {
        return hasBody;
    }

    /**
     * 设置是否有body
     * 因为没有严格的要求说是哪些请求可以有body  哪些不能有
     * 所以可以设置
     * @param hasBody
     */
    public void setHasBody(boolean hasBody) {
        this.hasBody = hasBody;
    }

}
