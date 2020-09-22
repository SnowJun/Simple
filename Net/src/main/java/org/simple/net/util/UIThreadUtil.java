package org.simple.net.util;

import android.os.Handler;
import android.os.Looper;

/**
 * org.simple.net.util
 *
 * @author Simple
 * @date 2020/9/11
 * @desc
 * 主线程任务工具
 */
public class UIThreadUtil {


    private static Handler poster = new Handler(Looper.getMainLooper());


    /**
     * 将任务运行在主线程
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        poster.post(runnable);
    }

}
