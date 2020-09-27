package org.simple.net.response;

import org.simple.net.request.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * org.simple.net.response
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 * 返回的内容
 */
public class Response {

    /**
     * code值
     */
    private int code;
    /**
     * message信息值
     */
    private String message;
    /**
     * http协议
     */
    private String protocal;
    /**
     * 请求
     */
    private Request request;
    /**
     * 返回的Body体
     */
    private Body body;

    private Map<String,String> headers = new HashMap<String, String>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Map<String,String> getHeader() {
        return headers;
    }

    public void setHeader(Map<String,String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key,String value){
        headers.put(key,value);
    }

}
