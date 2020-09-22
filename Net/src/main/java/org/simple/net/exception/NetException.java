package org.simple.net.exception;

import org.simple.net.util.SimpleLog;

/**
 * org.simple.net.exception
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 * 网络异常
 */
public class NetException extends RuntimeException {

    /**
     * 异常码
     */
    private int code;

    private NetException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     * 抛出异常
     * @param code
     * @param msg
     * @return
     */
    public static NetException exception(int code,String msg){
        SimpleLog.e("抛出异常：code->"+code+"  msg->"+msg);
        return new NetException(msg,code);
    }


    @Override
    public String toString() {
        return "NetException{" +
                "code=" + code + "msg" + getMessage() +'}';
    }
}
