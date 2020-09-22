package org.simple.net.proxy;

import org.simple.net.angency.NetProxyFactory;
import org.simple.net.constants.Constants;

/**
 * org.simple.net.proxy
 *
 * @author Simple
 * @date 2020/9/10
 * @desc
 * 代理的单例
 * 如果没有代理  则设置默认代理
 */
public class ProxyUtil {

    private NetProxy netProxy;

    private static ProxyUtil ourInstance;

    public static ProxyUtil getInstance() {
        if (null == ourInstance) {
            synchronized (ProxyUtil.class) {
                if (null == ourInstance) {
                    ourInstance = new ProxyUtil();
                }
            }
        }
        return ourInstance;
    }

    private ProxyUtil() {
    }

    public NetProxy getNetProxy() {
        if (null == netProxy){
            netProxy = NetProxyFactory.genProxy(Constants.NET_AGENCY_ENUM.getNetAgencyClass());
            netProxy.setConnectionTimeOut(Constants.CONNECT_TIME_OUT);
            netProxy.setReadTimeOut(Constants.READ_TIME_OUT);
            netProxy.setWriteTimeOut(Constants.WRITE_TIME_OUT);
            netProxy.init();
        }
        return netProxy;
    }

    public ProxyUtil setNetProxy(NetProxy netProxy) {
        this.netProxy = netProxy;
        return this;
    }

}
