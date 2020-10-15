package org.simple.util.util;

import org.simple.util.SimpleLog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/14
 * @desc 精确计算及格式化
 */
public class DecimalUtil {

    /**
     * 加法运算
     *
     * @param val1
     * @param val2
     * @return
     */
    public BigDecimal add(String val1, String val2) {
        try {
            checkParas(val1);
            checkParas(val2);
        } catch (Exception e) {
            SimpleLog.e("参数异常：" + e.getMessage());
            return null;
        }
        BigDecimal value1 = new BigDecimal(val1);
        BigDecimal value2 = new BigDecimal(val2);
        return value1.add(value2);
    }

    /**
     * 减法运算
     *
     * @param val1
     * @param val2
     * @return
     */
    public BigDecimal subtract(String val1, String val2) {
        try {
            checkParas(val1);
            checkParas(val2);
        } catch (Exception e) {
            SimpleLog.e("参数异常：" + e.getMessage());
            return null;
        }
        BigDecimal value1 = new BigDecimal(val1);
        BigDecimal value2 = new BigDecimal(val2);
        return value1.subtract(value2);
    }


    /**
     * 乘法运算
     *
     * @param val1
     * @param val2
     * @return
     */
    public BigDecimal multiply(String val1, String val2) {
        try {
            checkParas(val1);
            checkParas(val2);
        } catch (Exception e) {
            SimpleLog.e("参数异常：" + e.getMessage());
            return null;
        }
        BigDecimal value1 = new BigDecimal(val1);
        BigDecimal value2 = new BigDecimal(val2);
        return value1.multiply(value2);
    }

    /**
     * 除法运算
     *
     * @param val1
     * @param val2
     * @return
     */
    public BigDecimal divide(String val1, String val2) {
        try {
            checkParas(val1);
            checkParas(val2);
        } catch (Exception e) {
            SimpleLog.e("参数异常：" + e.getMessage());
            return null;
        }
        BigDecimal value1 = new BigDecimal(val1);
        BigDecimal value2 = new BigDecimal(val2);
        if (value2.compareTo(BigDecimal.valueOf(0)) == 0){
            throw new ArithmeticException("除数为0");
        }
        BigDecimal result = null;
        try {
            result = value1.divide(value2);
        } catch (ArithmeticException e) {
            //除不尽的情况需要制定小数位数   取两个小数的最多的位数
            int scale = Math.max(value1.scale(), value2.scale());
            result = value1.divide(value2, scale, RoundingMode.HALF_UP);
        }
        return result;
    }


    /**
     * 比较大小运算
     *
     * @param val1
     * @param val2
     * @return -1  val1 小于val2
     * 0 val1 等于val2
     * 1 val1 大于val2
     */
    public int compareTo(String val1, String val2) throws Exception {
        try {
            checkParas(val1);
            checkParas(val2);
        } catch (Exception e) {
            SimpleLog.e("参数异常：" + e.getMessage());
            throw new Exception("参数异常：" + e.getMessage());
        }
        BigDecimal value1 = new BigDecimal(val1);
        BigDecimal value2 = new BigDecimal(val2);
        return value1.compareTo(value2);
    }


    /**
     * 参数合法性检测
     *
     * @param val
     * @throws Exception
     */
    private void checkParas(String val) throws Exception {
        if (null == val || val.length() == 0) {
            throw new Exception("入参为 null 或者长度为0");
        }
    }


    /**
     * 格式化double值
     * 默认为四舍五入
     *
     * @param scale 小数位数
     * @param value 值
     * @return
     */
    public double format(int scale, double value) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        return bigDecimal.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 格式化double值
     *
     * @param scale        小数位数
     * @param roundingMode 舍入模式 {@link RoundingMode}
     *                     RoundingMode.HALF_DOWN  5舍6入
     *                     RoundingMode.HALF_UP  4舍5入
     *                     RoundingMode.UP  远离0   进1法  正数越来越大  负数越来越小
     *                     RoundingMode.DOWN  靠近0   舍去  正数越来越小  负数越来越大
     *                     RoundingMode.CEILING 都是变大   正数相当于UP  负数相当于DOWN
     *                     RoundingMode.FLOOR 都是变小   正数相当于DOWN  负数相当于UP
     * @param value        值
     * @return
     */
    public double format(int scale, RoundingMode roundingMode, double value) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        return bigDecimal.setScale(scale, roundingMode).doubleValue();
    }

    /**
     * 格式化double值
     * 默认为四舍五入
     *
     * @param format 格式  如"0.00为保留两位小数"
     * @param value
     * @return
     */
    public String format(String format, double value) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(value);
    }

    /**
     * 格式化double值
     * 默认为四舍五入
     *
     * @param roundingMode 舍入模式 {@link RoundingMode}
     *                     RoundingMode.HALF_DOWN  5舍6入
     *                     RoundingMode.HALF_UP  4舍5入
     *                     RoundingMode.UP  远离0   进1法  正数越来越大  负数越来越小
     *                     RoundingMode.DOWN  靠近0   舍去  正数越来越小  负数越来越大
     *                     RoundingMode.CEILING 都是变大   正数相当于UP  负数相当于DOWN
     *                     RoundingMode.FLOOR 都是变小   正数相当于DOWN  负数相当于UP
     * @param format       格式  如"0.00为保留两位小数"
     * @param value
     * @return
     */
    public String format(String format, RoundingMode roundingMode, double value) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        decimalFormat.setRoundingMode(roundingMode);
        return decimalFormat.format(value);
    }


}
