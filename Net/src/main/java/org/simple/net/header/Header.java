package org.simple.net.header;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * org.simple.net.header
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 * 请求头
 */
public class Header {

    /**
     * headers的键值对
     */
    private Map<String,String> headers = new HashMap<>();

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(@NonNull String key, String value){
        headers.put(key,value);
    }

}
