package org.simple.image.agency;

import org.simple.image.proxy.ImageProxy;

/**
 * org.simple.image.agency
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public class ImageProxyFactory {

    /**
     * 生成代理
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T extends ImageProxy<T>> T genProxy(Class<T> tClass){
        T proxy = null;
        try {
            proxy = (T) Class.forName(tClass.getName()).newInstance();
        }catch (Exception e){

        }
        return proxy;
    }

}
