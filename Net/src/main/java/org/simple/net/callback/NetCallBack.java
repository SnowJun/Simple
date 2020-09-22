package org.simple.net.callback;

import org.simple.net.response.Response;

/**
 * org.simple.net
 *
 * @author Simple
 * @date 2020/9/9
 * @desc 网络回调
 * T 期望的返回类型
 */
public interface NetCallBack<T> {

    /**
     * 解析
     *
     * @param response
     */
    void parse(Response response);

    /**
     * 请求成功
     *
     * @param result
     */
    void onSuccess(T result);

    /**
     * 请求失败
     *
     * @param reason
     */
    void onFail(String reason);

    /**
     * 请求异常
     *
     * @param e
     */
    void onException(Exception e);

}
