package org.simple.net;

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
     * 初始化
     * @return
     */
    public static SimpleNetBuilder init(){
        return new SimpleNetBuilder();
    }



    /**
     * 生成post请求
     * @param url
     * @return
     */
    public static <T> Request<T> post(String url){
        return new Request<T>().url(url);
    }

    /**
     * 生成get请求
     * @param url
     * @return
     */
    public static <T> Request<T> get(String url){
        return new Request<T>().url(url).method(RequestMethod.METHOD_GET);
    }

    /**
     * 生成put请求
     * @param url
     * @return
     */
    public static <T> Request<T> put(String url){
        return new Request<T>().url(url).method(RequestMethod.METHOD_PUT);
    }

    /**
     * 生成delete请求
     * @param url
     * @return
     */
    public static <T> Request<T> delete(String url){
        return new Request<T>().url(url).method(RequestMethod.METHOD_DELETE);
    }



}
