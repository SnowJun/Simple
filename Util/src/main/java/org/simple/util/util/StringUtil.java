package org.simple.util.util;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/12
 * @desc
 * 字符串工具
 */
public class StringUtil {

    /**
     * 是否为空
     *
     * @param info
     * @return  为null  或者  长度为0
     */
    public boolean isEmpty(String info) {
        return null == info || info.length() == 0;
    }

    /**
     * 是否相等
     *
     * @param str1
     * @param str2
     * @return  都为null 也视为向相等  返回true
     */
    public boolean isEquals(String str1, String str2) {
        if (null == str1) {
            if (null == str2) {
                return true;
            } else {
                return false;
            }
        }
        if (str1 == str2) {
            return true;
        }
        return str1.equals(str2);
    }

    /**
     * 字符串长度
     *
     * @param str
     * @return null的时候返回-1
     */
    public int length(String str) {
        if (null == str) {
            return -1;
        }
        return str.length();
    }


}
