package org.simple.net.request;

import androidx.annotation.NonNull;

import org.simple.net.callback.NetCallBack;
import org.simple.net.constants.Constants;
import org.simple.net.header.Header;
import org.simple.net.paras.Paras;
import org.simple.net.proxy.ProxyUtil;
import org.simple.net.response.Response;

import java.util.Map;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/9
 * @desc 请求
 * B   body体的内容
 */
public class Request<B> {

    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求头
     */
    private Header header = new Header();
    /**
     * 请求的参数
     */
    private Paras paras = new Paras();
    /**
     * 请求体
     */
    private B body;
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


    public Request<B> tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public Object getTag() {
        return tag;
    }

    /**
     * 设置body内容
     *
     * @param body
     * @return
     */
    public Request<B> body(B body) {
        this.body = body;
        return this;
    }

    /**
     * 设置url地址
     *
     * @param url
     * @return
     */
    public Request<B> url(@NonNull String url) {
        this.url = url;
        return this;
    }

    /**
     * 添加请求头
     *
     * @param key
     * @param value
     * @return
     */
    public Request<B> addHeader(@NonNull String key, String value) {
        this.header.addHeader(key, value);
        return this;
    }

    /**
     * 请求头
     *
     * @param headers
     * @return
     */
    public Request<B> headers(Map<String, String> headers) {
        this.header.setHeaders(headers);
        return this;
    }

    /**
     * 添加参数
     *
     * @param key
     * @param value
     * @return
     */
    public Request<B> addParas(@NonNull String key, String value) {
        paras.addParas(key, value);
        return this;
    }

    /**
     * 请求参数
     *
     * @param paras
     * @return
     */
    public Request<B> paras(Map<String, String> paras) {
        this.paras.setParas(paras);
        return this;
    }

    /**
     * 设置请求方式
     *
     * @param requestMethod
     */
    public Request<B> method(RequestMethod requestMethod) {
        method = requestMethod;
        return this;
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
    public Request<B> setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Header getHeader() {
        return header;
    }

    public Paras getParas() {
        return paras;
    }

    public B getBody() {
        return body;
    }

    public RequestMethod getMethod() {
        return method;
    }

    /**
     * 使用同步的时候注意线程问题
     * @return
     */
    public Response excute() {
        return ProxyUtil.getInstance().getNetProxy().excute(this);
    }

    public void excute(NetCallBack callBack) {
        ProxyUtil.getInstance().getNetProxy().excute(this, callBack);
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void cancel(){
        isCanceled = true;
    }
}
