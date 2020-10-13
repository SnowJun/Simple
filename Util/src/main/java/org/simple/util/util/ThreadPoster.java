package org.simple.util.util;


import android.os.Handler;
import android.os.Looper;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/13
 * @desc
 */
public class ThreadPoster {

    private Handler handler;

    private static ThreadPoster ourInstance;
    private ThreadPoster(){
        handler = new Handler(Looper.getMainLooper());
    }
    public static ThreadPoster getInstance(){
        if (null == ourInstance){
            synchronized (ThreadPoster.class){
                if (null == ourInstance){
                    ourInstance = new ThreadPoster();
                }
            }
        }
        return ourInstance;
    }


    /**
     * 将任务投递到UI线程
     * @param runnable
     */
    public void postUIThread(Runnable runnable){
        handler.post(runnable);
    }


}
