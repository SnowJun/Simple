package org.simple.net.angency;

import org.simple.net.proxy.NetProxy;
import org.simple.net.util.SimpleLog;

/**
 * org.simple.net
 *
 * @author Simple
 * @date 2020/9/9
 * @desc 网络请求口生成工厂
 */
public class NetProxyFactory {


    /**
     * 生成代理口
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends NetProxy> T genProxy(Class<T> clazz) {
        NetProxy netProxy = null;
        try {
            netProxy = (NetProxy) Class.forName(clazz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            SimpleLog.e("工厂生产NetProxy异常"+e.getMessage());
        }
        return (T) netProxy;
    }

}
