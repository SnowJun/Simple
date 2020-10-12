package org.simple.util.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.simple.util.SimpleLog;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/10/12
 * @desc
 */
public class SPUtil {

    private static final String NAME = "sp_default";

    private static SPUtil ourInstance;

    private SPUtil() {
    }

    public static SPUtil getInstance() {
        if (null == ourInstance) {
            synchronized (SPUtil.class) {
                if (null == ourInstance) {
                    ourInstance = new SPUtil();
                }
            }
        }
        return ourInstance;
    }


    /**
     * 获取String 默认空字符换 ""
     *
     * @param context
     * @param key
     * @return
     */
    public String getString(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * 获取String
     *
     * @param context
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    /**
     * 存储String值
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public boolean putString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.edit().putString(key, value).commit();
    }

    /**
     * 获取int值 默认0
     *
     * @param context
     * @param key
     * @return
     */
    public int getInt(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    /**
     * 获取int 值
     *
     * @param context
     * @param key
     * @param defalutValue 默认值
     * @return
     */
    public int getInt(Context context, String key, int defalutValue) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key, defalutValue);
    }

    /**
     * 存储int值
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public boolean putInt(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.edit().putInt(key, value).commit();
    }

    /**
     * 获取Long值  默认0
     *
     * @param context
     * @param key
     * @return
     */
    public long getLong(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    /**
     * 获取Long值
     *
     * @param context
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public long getLong(Context context, String key, long defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getLong(key, defaultValue);
    }

    /**
     * 存储Long值
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public boolean putLong(Context context, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.edit().putLong(key, value).commit();
    }


    /**
     * 获取Float值   默认0
     *
     * @param context
     * @param key
     * @return
     */
    public float getFloat(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getFloat(key, 0);
    }

    /**
     * 获取Float值
     *
     * @param context
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getFloat(key, defaultValue);
    }

    /**
     * 存储Float值
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public boolean putFloat(Context context, String key, float value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.edit().putFloat(key, value).commit();
    }

    /**
     * 获取Boolean值  默认false
     *
     * @param context
     * @param key
     * @return
     */
    public boolean getBoolean(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    /**
     * 获取Boolean值
     *
     * @param context
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * 存储Boolean 值
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取String Set 默认为null
     *
     * @param context
     * @param key
     * @return
     */
    public Set<String> getStringSet(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getStringSet(key, null);
    }

    /**
     * 获取String Set
     *
     * @param context
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public Set<String> getStringSet(Context context, String key, Set<String> defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getStringSet(key, defaultValue);
    }

    /**
     * 存储String Set
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public boolean putStringSet(Context context, String key, Set<String> value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.edit().putStringSet(key, value).commit();
    }

    /**
     * 在String Set中添加值
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public boolean addStringInSet(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        if (isContainKey(context, key)) {
            //原来存在
            try {
                Set<String> stringSet = preferences.getStringSet(key, null);
                if (null == stringSet) {
                    stringSet = new HashSet<String>();
                }
                stringSet.add(value);
                return preferences.edit().putStringSet(key, stringSet).commit();
            } catch (ClassCastException e) {
                SimpleLog.e("e:"+e.getMessage());
                throw new RuntimeException("原来的key对应的类型不是Set<String>,无法添加，请更换key重试");
            }
        } else {
            //原来不存在
            Set<String> stringSet = new HashSet<>();
            stringSet.add(value);
            return preferences.edit().putStringSet(key, stringSet).commit();
        }
    }

    /**
     * 在String Set中移除值
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public boolean removeStringInSet(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        if (isContainKey(context, key)) {
            //原来存在
            try {
                Set<String> stringSet = preferences.getStringSet(key, null);
                if (null == stringSet) {
                    SimpleLog.d("本身set不存在  代表移除成功");
                    return true;
                }
                stringSet.remove(value);
                return preferences.edit().putStringSet(key, stringSet).commit();
            } catch (ClassCastException e) {
                SimpleLog.e("e:"+e.getMessage());
                throw new RuntimeException("原来的key对应的类型不是Set<String>,无法添加，请更换key重试");
            }
        } else {
            SimpleLog.d("key 不存在  代表移除成功");
            return true;
        }
    }


    public boolean remove(Context context,String key){
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.edit().remove(key).commit();
    }

    /**
     * 是否包含特定的key
     *
     * @param context
     * @param key
     * @return
     */
    public boolean isContainKey(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.contains(key);
    }

    /**
     * 获取所有的存储项
     *
     * @param context
     * @return
     */
    public Map<String, ?> getAll(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getAll();
    }

}
