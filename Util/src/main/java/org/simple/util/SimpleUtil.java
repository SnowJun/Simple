package org.simple.util;

import org.simple.util.util.NullUtil;
import org.simple.util.util.SPUtil;
import org.simple.util.util.StringUtil;

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
     * @return
     */
    public static NullUtil getNullUtil() {
        return new NullUtil();
    }

    /**
     * String Util
     * @return
     */
    public static StringUtil getStringUtil(){
        return new StringUtil();
    }

}
