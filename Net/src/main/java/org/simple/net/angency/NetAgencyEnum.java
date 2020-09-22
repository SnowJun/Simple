package org.simple.net.angency;

import org.simple.net.proxy.HttpUrlConnectionProxy;
import org.simple.net.proxy.NetProxy;
import org.simple.net.proxy.OkHttpNetProxy;

/**
 * org.simple.net
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 * 网络代理枚举
 */
public enum NetAgencyEnum {
    /**
     * OkHttp代理
     */
    AGENCY_OKHTTP(OkHttpNetProxy.class),
    /**
     * HttpUrlConnection代理
     */
    AGENCY_HTTPURLCONNECTION(HttpUrlConnectionProxy.class);

    private Class<? extends NetProxy>  netAgencyClass;

    NetAgencyEnum(Class<? extends NetProxy> netAgencyClass) {
        this.netAgencyClass = netAgencyClass;
    }

    public Class<? extends NetProxy> getNetAgencyClass() {
        return netAgencyClass;
    }
}
