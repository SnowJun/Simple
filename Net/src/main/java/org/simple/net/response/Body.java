package org.simple.net.response;

import java.io.InputStream;

/**
 * org.simple.net.response
 *
 * @author Simple
 * @date 2020/9/10
 * @desc
 * 网络返回的返回体
 */
public class Body {

    /**
     * 长度
     */
    private long length;
    /**
     * 返回的流
     */
    private InputStream inputStreamData;

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public InputStream getInputStreamData() {
        return inputStreamData;
    }

    public void setInputStreamData(InputStream inputStreamData) {
        this.inputStreamData = inputStreamData;
    }
}
