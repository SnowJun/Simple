package org.simple.util.util;

import org.simple.util.SimpleLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/14
 * @desc
 */
public class TimeUtil {

    /**
     * 获取当前的时间戳
     * @return
     */
    public long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 获取当前的日历
     * @return
     */
    public Calendar getCurrentCalendar(){
        return Calendar.getInstance();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getCurrentFormatDate() {
        long currentTimeLong = Calendar.getInstance().getTimeInMillis();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(currentTimeLong);
    }

    /**
     * 获取当前时间
     *
     * @param format 格式化规则 {@link SimpleDateFormat}
     * @param locale 地方 @{@link Locale}
     * @return
     */
    public String getCurrentFormatDate(String format, Locale locale) {
        long currentTimeLong = Calendar.getInstance().getTimeInMillis();
        return new SimpleDateFormat(format, locale).format(currentTimeLong);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public String formatDate(long date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(date);
    }

    /**
     * 格式化日期
     *
     * @param format 格式化规则 {@link SimpleDateFormat}
     * @param locale 地方 @{@link Locale}
     * @param date
     * @return
     */
    public String formatDate(String format, Locale locale, long date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(date);
    }

    /**
     * 解析格式化的时间
     *
     * @param date
     * @return
     */
    public Date parseTime(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            SimpleLog.e("解析异常：" + e.getMessage());
        }
        return null;
    }

    /**
     * 解析格式化的时间
     *
     * @param format 格式化规则 {@link SimpleDateFormat}
     * @param locale 地方 @{@link Locale}
     * @param date
     * @return
     */
    public Date parseTime(String format, Locale locale, String date) {
        try {
            return new SimpleDateFormat(format, locale).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            SimpleLog.e("解析异常：" + e.getMessage());
        }
        return null;
    }

}
