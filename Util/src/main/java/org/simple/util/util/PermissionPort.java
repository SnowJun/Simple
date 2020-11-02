package org.simple.util.util;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/11/2
 * @desc
 */
public class PermissionPort {


    private static PermissionPort ourInstance;

    private PermissionPort(){
    }

    public  static PermissionPort getInstance(){
        if (null == ourInstance){
            synchronized (PermissionPort.class){
                if (null == ourInstance){
                    ourInstance = new PermissionPort();
                }
            }
        }
        return ourInstance;
    }


    private  PermissionCallBack callBack;

    public  PermissionCallBack getCallBack(){
        return callBack;
    }

    /**
     * 请求权限
     * @param callBack
     * @param permissions
     */
    public  void requestPermission(Activity activity,PermissionCallBack callBack, String... permissions){
        Intent intent = new Intent(activity,PermissionActivity.class);
        ArrayList<String> permissionList = new ArrayList<>();
        if (null != permissions && permissions.length > 0){
            Collections.addAll(permissionList, permissions);
        }
        intent.putStringArrayListExtra("permissions",permissionList);
        this.callBack = callBack;
        activity.startActivity(intent);
    }

    /**
     * 权限回调
     */
    public interface PermissionCallBack {
        /**
         * 权限成功
         */
        void success();

        /**
         * 权限拒绝
         *
         * @param definePermissions 拒绝的权限列表
         */
        void define(List<String> definePermissions);
    }



}
