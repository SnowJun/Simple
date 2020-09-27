package org.simple.net.request;

import androidx.annotation.NonNull;

import org.simple.net.SimpleNet;
import org.simple.net.callback.NetCallBack;
import org.simple.net.constants.Constants;
import org.simple.net.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/9
 * @desc 请求
 * B   body体的内容
 */
public class Request<R extends Request> {

    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求头
     */
    private Map<String, String> headers;
    /**
     * 请求参数
     */
    private Map<String, String> paras;

    /**
     * 请求方式
     * 默认post请求
     */
    private RequestMethod method = RequestMethod.METHOD_POST;
    /**
     * 重试次数
     */
    private int retryCount = Constants.RETRY_COUNT;
    /**
     * 请求的tag值
     */
    private Object tag;

    /**
     * 是否取消了
     */
    private boolean isCanceled;


    public Request tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public Object getTag() {
        return tag;
    }


    public  R url(String url) {
        this.url = url;
        return (R) this;
    }

    /**
     * 添加请求头
     *
     * @param key
     * @param value
     * @return
     */
    public  R addHeader(@NonNull String key, String value) {
        if (null == headers) {
            headers = new HashMap<String, String>();
        }
        headers.put(key, value);
        return (R) this;
    }

    /**
     * 请求头
     *
     * @param headers
     * @return
     */
    public  R headers(Map<String, String> headers) {
        this.headers = headers;
        return (R) this;
    }

    /**
     * 添加参数
     *
     * @param key
     * @param value
     * @return
     */
    public R addParas(@NonNull String key, String value) {
        if (null == paras) {
            paras = new HashMap<String, String>();
        }
        paras.put(key, value);
        return (R) this;
    }

    /**
     * 请求参数
     *
     * @param paras
     * @return
     */
    public  R paras(Map<String, String> paras) {
        this.paras = paras;
        return (R) this;
    }

    /**
     * 设置请求方式
     *
     * @param requestMethod
     */
    public  R method(RequestMethod requestMethod) {
        method = requestMethod;
        return (R) this;
    }

    /**
     * 获取重试次数
     *
     * @return
     */
    public int getRetryCount() {
        return retryCount;
    }

    /**
     * 设置重试次数
     *
     * @param retryCount
     */
    public  R setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return (R) this;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeader() {
        return headers;
    }

    public Map<String, String> getParas() {
        return paras;
    }


    public RequestMethod getMethod() {
        return method;
    }

    /**
     * 使用同步的时候注意线程问题
     *
     * @return
     */
    public Response excute() {
        return SimpleNet.getInstance().getNetProxy().excute(this);
    }

    /**
     * 异步执行
     *
     * @param callBack
     */
    public void excute(NetCallBack callBack) {
        SimpleNet.getInstance().getNetProxy().excute(this, callBack);
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void cancel() {
        isCanceled = true;
    }

}
