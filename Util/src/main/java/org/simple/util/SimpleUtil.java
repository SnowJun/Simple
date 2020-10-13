package org.simple.util;

import org.simple.util.util.AppUtil;
import org.simple.util.util.DeviceUtil;
import org.simple.util.util.NullUtil;
import org.simple.util.util.PixelUtil;
import org.simple.util.util.SPUtil;
import org.simple.util.util.StringUtil;
import org.simple.util.util.TaskManager;
import org.simple.util.util.ThreadPoster;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 */
public class SimpleUtil {


    /**
     * 获取SPUtil类
     *
     * @return
     */
    public static SPUtil getSPUtil() {
        return SPUtil.getInstance();
    }


    /**
     * null 判断util
     *
     * @return
     */
    public static NullUtil getNullUtil() {
        return new NullUtil();
    }

    /**
     * String Util
     *
     * @return
     */
    public static StringUtil getStringUtil() {
        return new StringUtil();
    }

    /**
     * 获取任务管理类
     *
     * @return
     */
    public static TaskManager getTaskManager() {
        return TaskManager.getInstance();
    }


    /**
     * 获取线程投递类
     * 切换到UI线程执行任务
     * @return
     */
    public static ThreadPoster getThreadPoster() {
        return ThreadPoster.getInstance();
    }

    /**
     * 获取pixelutil  dp、sp转换  屏幕宽高等
     * @return
     */
    public static PixelUtil getPixelUtil(){
        return new PixelUtil();
    }

    /**
     * 获取设备工具
     * @return
     */
    public static DeviceUtil getDeviceUtil(){
        return new DeviceUtil();
    }

    /**
     * 获取App工具
     * @return
     */
    public static AppUtil getAppUtil(){
        return new AppUtil();
    }


}
