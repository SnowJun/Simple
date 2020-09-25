package org.simple.net;

import org.simple.net.angency.NetProxyFactory;
import org.simple.net.constants.Constants;
import org.simple.net.proxy.NetProxy;
import org.simple.net.request.Request;
import org.simple.net.request.RequestMethod;

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
     * @param builder builder对象
     * @return
     */
    public void init(SimpleNetBuilder builder) {
        netProxy = NetProxyFactory.genProxy(builder.getNetAgencyEnum().getNetAgencyClass());
        netProxy.setConnectionTimeOut(builder.getConnectionTimeOut());
        netProxy.setReadTimeOut(builder.getReadTimeOut());
        netProxy.setWriteTimeOut(builder.getWriteTimeOut());
        netProxy.init();
    }

    /**
     * 获取网络请求端口
     * @return
     */
    public NetProxy getNetProxy() {
        return netProxy;
    }

    /**
     * 生成post请求
     *
     * @param url
     * @return
     */
    public static <T> Request<T> post(String url) {
        return new Request<T>().url(url);
    }

    /**
     * 生成get请求
     *
     * @param url
     * @return
     */
    public static <T> Request<T> get(String url) {
        return new Request<T>().url(url).method(RequestMethod.METHOD_GET);
    }

    /**
     * 生成put请求
     *
     * @param url
     * @return
     */
    public static <T> Request<T> put(String url) {
        return new Request<T>().url(url).method(RequestMethod.METHOD_PUT);
    }

    /**
     * 生成delete请求
     *
     * @param url
     * @return
     */
    public static <T> Request<T> delete(String url) {
        return new Request<T>().url(url).method(RequestMethod.METHOD_DELETE);
    }


}
