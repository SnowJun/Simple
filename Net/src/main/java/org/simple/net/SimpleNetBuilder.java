package org.simple.net;

import org.simple.net.angency.NetAgencyEnum;
import org.simple.net.constants.Constants;

/**
 * org.simple.net
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 * SimpleNet建造类
 */
public class SimpleNetBuilder {


    /**
     * 代理类型
     */
    private NetAgencyEnum netAgencyEnum = Constants.NET_AGENCY_ENUM;
    /**
     * 连接超时时间
     */
    private long connectionTimeOut = Constants.CONNECT_TIME_OUT;
    /**
     * 读取超时时间
     */
    private long readTimeOut = Constants.READ_TIME_OUT;
    /**
     * 写入超时时间
     */
    private long writeTimeOut = Constants.WRITE_TIME_OUT;
    /**
     * 重试次数
     */
    private int retryCount = Constants.RETRY_COUNT;


    public SimpleNetBuilder setNetAgency(NetAgencyEnum netAgency) {
        this.netAgencyEnum = netAgency;
        return this;
    }


    public SimpleNetBuilder connectTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
        return this;
    }

    public SimpleNetBuilder readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public SimpleNetBuilder writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public SimpleNetBuilder retryCount(int count) {
        retryCount = count;
        return this;
    }

    public NetAgencyEnum getNetAgencyEnum() {
        return netAgencyEnum;
    }

    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public long getReadTimeOut() {
        return readTimeOut;
    }

    public long getWriteTimeOut() {
        return writeTimeOut;
    }

    public int getRetryCount() {
        return retryCount;
    }
}
