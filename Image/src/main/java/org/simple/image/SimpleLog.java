package org.simple.image;

import android.util.Log;

import org.simple.image.constants.Constants;

/**
 * org.simple.net.util
 *
 * @author Simple
 * @date 2020/9/10
 * @desc
 * Simple日志
 */
public class SimpleLog {

    public static void v(String log){
        Log.v(Constants.TAG,log);
    }

    public static void d(String log){
        Log.d(Constants.TAG,log);
    }

    public static void i(String log){
        Log.i(Constants.TAG,log);
    }

    public static void w(String log){
        Log.w(Constants.TAG,log);
    }

    public static void e(String log){
        Log.e(Constants.TAG,log);
    }

}
