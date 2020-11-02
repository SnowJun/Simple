package org.simple.util.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/14
 * @desc
 */
public class PermissionUtil {


    public static final int REQUEST_CODE  = 100;

    private Activity activity;

    public PermissionUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * 检测是否有权限
     *
     * @param permissions 需要检测的权限
     * @return
     */
    public boolean checkPermission(String... permissions) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            //6.0以下不需要动态权限  直接返回true
            return true;
        }
        if (null == permissions || permissions.length == 0) {
            throw new RuntimeException("请至少检测一个权限是否拥有");
        }
        for (String permission :
                permissions) {
            if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 请求权限
     * @param callBack 权限回调
     * @param permissions
     */
    public void requestPermission(PermissionPort.PermissionCallBack callBack, String... permissions){
        if (checkPermission(permissions)){
            callBack.success();
        }
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                //显示提示
            }
        }
        requestPermissionExcute(callBack,permissions);
    }

    /**
     * 执行请求权限
     * @param callBack
     * @param permissions
     */
    public void requestPermissionExcute(PermissionPort.PermissionCallBack callBack, String... permissions){
        ActivityCompat.requestPermissions(activity,permissions,REQUEST_CODE);
    }





}
