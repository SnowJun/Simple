package org.simple.util.util;

import android.os.Build;

import org.simple.util.SimpleLog;

import java.util.Locale;
import java.util.UUID;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/13
 * @desc
 */
public class DeviceUtil {

    /**
     * 获取手机制造商
     *
     * @return
     */
    public String getPhoneManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public String getPhoneMode() {
        return Build.MODEL;
    }

    /**
     * 获取手机名称
     *
     * @return
     */
    public String getPhoneName() {
        return Build.DEVICE;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public String getPhoneBrand() {
        return Build.BRAND;
    }

    /**
     * 获取产品的名称
     * @return
     */
    public String getPhoneProduct(){
        return Build.PRODUCT;
    }

    /**
     * 获取手机主板的名称
     * @return
     */
    public String getPhoneBoard(){
        return Build.BOARD;
    }

    /**
     * 获取手机的硬件
     * @return
     */
    public String getPhoneHardware(){
        return Build.HARDWARE;
    }

    /**
     * 获取手机Host
     * @return
     */
    public String getPhoneHost(){
        return Build.HOST;
    }

    /**
     * 获取手机的显示id
     * @return
     */
    public String getPhoneDisplay(){
        return Build.DISPLAY;
    }

    /**
     * 获取手机id
     * @return
     */
    public String getPhoneId(){
        return Build.ID;
    }

    /**
     * 获取手机的用户名
     * @return
     */
    public String getPhoneUser(){
        return Build.USER;
    }

    /**
     * 获取手机语言
     * @return
     */
    public String getPhoneLanguage(){
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前设备的sdk版本（int）
     * @return
     */
    public int getDeviceSdkInt(){
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取当前设备的sdk版本 如1.0等
     * @return
     */
    public String getDeviceSdk(){
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取设备id
     *
     * @return
     */
    public String getDeviceId() {
        String serial = null;
        String mszDevIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(mszDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            SimpleLog.e("exception->" + exception.getMessage());
        }
        return new UUID(mszDevIDShort.hashCode(), serial.hashCode()).toString();
    }


}
