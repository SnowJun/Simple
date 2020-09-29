package org.simple.net.exception;

import org.simple.net.util.SimpleLog;

/**
 * org.simple.net.exception
 *
 * @author Simple
 * @date 2020/9/9
 * @desc 网络异常
 */
public class NetException extends RuntimeException {


    /**
     * 异常码
     */
    private int httpCode;
    private String httpMessage;
    private String message;

    private NetException(String message) {
        super(message);
        this.message = message;
    }

    public NetException(String message, int httpCode, String httpMessage) {
        super(message);
        this.httpCode = httpCode;
        this.httpMessage = httpMessage;
        this.message = message;
    }


    /**
     * 抛出异常
     * @param msg 异常信息
     * @return
     */
    public static NetException exception( String msg) {
        SimpleLog.e("抛出异常： msg->" + msg);
        return new NetException(msg);
    }

    /**
     * 抛出http异常
     * @param httpCode http码
     * @param httpMessage http信息
     * @param msg 异常信息
     * @return
     */
    public static NetException exception(int httpCode, String httpMessage, String msg) {
        SimpleLog.e("抛出异常： httpCode->" + httpCode+"\nhttpMessage->"+httpMessage+"\nmsg->"+msg);
        return new NetException(msg,httpCode,httpMessage);
    }

    @Override
    public String toString() {
        return "NetException{" +
                "httpCode=" + httpCode +
                ", httpMessage='" + httpMessage + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
