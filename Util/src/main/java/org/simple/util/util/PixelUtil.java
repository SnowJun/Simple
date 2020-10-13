package org.simple.util.util;

import android.content.Context;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/13
 * @desc 像素相关的工具
 */
public class PixelUtil {

    /**
     * dp转换为px
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f);
        return px;
    }

    /**
     * dp转换为px
     * @param context
     * @param px
     * @return
     */
    public int pxToDp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        int dp = (int) (px / density + 0.5f);
        return dp;
    }

    /**
     * sp转换为px
     * @param context
     * @param sp
     * @return
     */
    public int spToPx(Context context, float sp) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        int px = (int) (sp * scaleDensity + 0.5f);
        return px;
    }

    /**
     * px转换为sp
     * @param context
     * @param px
     * @return
     */
    public int pxToSp(Context context, float px) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        int sp = (int) (px / scaleDensity + 0.5f);
        return sp;
    }

    /**
     * 获取density 1.5 2 等
     * @param context
     * @return
     */
    public float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取densityDpi   240 320 等
     * @param context
     * @return
     */
    public int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取 scaledDensity
     * @param context
     * @return
     */
    public float getScaleDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }


}
