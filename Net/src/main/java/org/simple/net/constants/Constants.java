package org.simple.net.constants;

import org.simple.net.angency.NetAgencyEnum;

/**
 * org.simple.net.constants
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 * 常亮
 */
public class Constants {

    /**
     * TAG值
     */
    public static final String TAG = "Simple-Net";

    /**
     * 默认连接超时时间  3s
     */
    public static final long CONNECT_TIME_OUT = 3000;
    /**
     * 默认读取超时时间
     */
    public static final long READ_TIME_OUT = 3000;
    /**
     * 默认写入超时时间
     */
    public static final long WRITE_TIME_OUT = 3000;
    /**
     * 默认重试次数
     */
    public static final int RETRY_COUNT = 3;
    /**
     * 默认的线程数
     */
    public static final int COUNT_THREADS = 5;
    /**
     * 默认网络代理为HttpUrlConnection
     */
    public static final NetAgencyEnum NET_AGENCY_ENUM = NetAgencyEnum.AGENCY_HTTPURLCONNECTION;

}
