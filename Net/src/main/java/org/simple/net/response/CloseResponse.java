package org.simple.net.response;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * org.simple.net.response
 *
 * @author Simple
 * @date 2020/9/15
 * @desc
 * 针对UrlConnection的关闭问题
 */
public class CloseResponse extends Response {

    private HttpURLConnection connection;

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }


    /**
     * 关闭response的方法
     */
    public void close(){
        if (null != getBody() &&null != getBody().getInputStreamData()){
            try {
                getBody().getInputStreamData().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != connection){
            connection.disconnect();
        }
    }
}
