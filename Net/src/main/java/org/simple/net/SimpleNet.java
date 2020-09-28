package org.simple.net;

import org.simple.net.angency.NetProxyFactory;
import org.simple.net.constants.Constants;
import org.simple.net.proxy.NetProxy;
import org.simple.net.request.FileRequest;
import org.simple.net.request.FormRequest;
import org.simple.net.request.JsonRequest;
import org.simple.net.request.MultiRequest;
import org.simple.net.request.Request;
import org.simple.net.request.RequestMethod;

import java.util.Map;

/**
 * org.simple.net
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 */
public class SimpleNet {


    /**
     * 网络请求
     */
    private NetProxy netProxy;

    /**
     * 公共请求头
     */
    private Map<String,String> commonHeaders;
    /**
     * 获取单例对象
     *
     * @return
     */
    public static SimpleNet getInstance() {
        if (null == ourInstance) {
            synchronized (SimpleNet.class) {
                if (null == ourInstance) {
                    ourInstance = new SimpleNet();
                }
            }
        }
        return ourInstance;
    }

    private static SimpleNet ourInstance;

    private SimpleNet() {
        netProxy = NetProxyFactory.genProxy(Constants.NET_AGENCY_ENUM.getNetAgencyClass());
        netProxy.setConnectionTimeOut(Constants.CONNECT_TIME_OUT);
        netProxy.setReadTimeOut(Constants.READ_TIME_OUT);
        netProxy.setWriteTimeOut(Constants.WRITE_TIME_OUT);
        netProxy.init();
    }


    /**
     * 初始化
     *
     * @param builder builder对象
     * @return
     */
    public void init(SimpleNetBuilder builder) {
        netProxy = NetProxyFactory.genProxy(builder.getNetAgencyEnum().getNetAgencyClass());
        netProxy.setConnectionTimeOut(builder.getConnectionTimeOut());
        netProxy.setReadTimeOut(builder.getReadTimeOut());
        netProxy.setWriteTimeOut(builder.getWriteTimeOut());
        netProxy.init();
        commonHeaders = builder.getCommonHeaders();
    }

    /**
     * 获取网络请求端口
     *
     * @return
     */
    public NetProxy getNetProxy() {
        return netProxy;
    }

    /**
     * 获取公共的请求头
     * @return
     */
    public Map<String,String> getCommonHeaders(){
        return commonHeaders;
    }

    /**
     * 生成post请求
     * 表单提交
     * @param url
     * @return
     */
    public static FormRequest postForm(String url) {
        return new FormRequest().url(url);
    }
    /**
     * 生成post请求
     * Json提交
     * @param url
     * @return
     */
    public static JsonRequest postJson(String url) {
        return new JsonRequest().url(url);
    }
    /**
     * 生成post请求
     * 文件提交
     * @param url
     * @return
     */
    public static FileRequest postFile(String url) {
        return new FileRequest().url(url);
    }
    /**
     * 生成post请求
     * 多文件或者混合提交
     * @param url
     * @return
     */
    public static MultiRequest postMulti(String url) {
        return new MultiRequest().url(url);
    }

    /**
     * 生成get请求
     *
     * @param url
     * @return
     */
    public static  Request get(String url) {
        return new Request().url(url).method(RequestMethod.METHOD_GET);
    }


}
