package org.simple.util.util;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/12
 * @desc
 */
public class NullUtil {

    /**
     * 空值检测
     *
     * @param o       检测的对象
     * @param handler 处理类
     * @return o 为null  返回true  不为null  返回false
     */
    public boolean checkNull(Object o, NullHandler handler) {
        if (null != o) {
            if (null != handler) {
                handler.onNotNull();
            }
            return false;
        } else {
            if (null != handler) {
                handler.onNull();
            }
            return true;
        }
    }


    public interface NullHandler {
        /**
         * 对象不为空调用
         */
        void onNotNull();

        /**
         * 对象为空调用
         */
        void onNull();
    }

}
