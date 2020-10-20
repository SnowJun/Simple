package org.simple.util;

import android.content.Context;

import org.simple.util.util.AppUtil;
import org.simple.util.util.DecimalUtil;
import org.simple.util.util.DeviceUtil;
import org.simple.util.util.FileUtil;
import org.simple.util.util.NotificationUtil;
import org.simple.util.util.NullUtil;
import org.simple.util.util.PixelUtil;
import org.simple.util.util.SPUtil;
import org.simple.util.util.StringUtil;
import org.simple.util.util.TaskManager;
import org.simple.util.util.ThreadPoster;
import org.simple.util.util.TimeUtil;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 *

 *
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

    /**
     * 获取时间工具
     * @return
     */
    public static TimeUtil getTimeUtil(){
        return new TimeUtil();
    }

    /**
     * 获取精确计算及格式化工具
     * @return
     */
    public static DecimalUtil getDecimalUtil(){
        return new DecimalUtil();
    }

    /**
     * 获取文件工具
     * @return
     */
    public static FileUtil getFileUtil(Context context){
        return new FileUtil(context);
    }

    /**
     * 获取通知工具
     * @return
     */
    public static NotificationUtil getNotificationUtil(){
        return NotificationUtil.getInstance();
    }


}
