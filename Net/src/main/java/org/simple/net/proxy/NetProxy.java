package org.simple.net.proxy;

import org.simple.net.callback.NetCallBack;
import org.simple.net.request.Request;
import org.simple.net.response.Response;

/**
 * org.simple.net
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 * 实际进行网络请求的类
 */
public interface NetProxy {

    /**
     * 初始化
     */
    void init();


    /**
     * 设置连接超时时间
     * @param connectionTimeOut 超时时间 单位为ms
     */
    void setConnectionTimeOut(long connectionTimeOut);

    /**
     * 设置写入超时时间
     * 对HttpUrLConnection无效
     * @param writeTimeOut 超时时间 单位为ms
     */
    void setWriteTimeOut(long writeTimeOut);

    /**
     * 设置读出超时时间
     * @param readTimeOut 超时时间 单位为ms
     */
    void setReadTimeOut(long readTimeOut);


    /**
     * 设置重试次数
     * @param count
     */
    void retryCount(int count);

    /**
     * 异步请求
     * @param request
     * @param callBack
     */
    void excute(Request request,NetCallBack callBack);

    /**
     * 同步请求
     * @param request
     * @return
     */
    Response excute(Request request);

    /**
     * 取消
     * @param tag
     */
    void cancel(Object tag);

    /**
     * 取消所有
     */
    void cancelAll();


}
