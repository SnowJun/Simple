package org.simple.util.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.simple.util.SimpleLog;

import java.util.ArrayList;
import java.util.List;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/14
 * @desc
 */
public class PermissionUtil {


    public static final int REQUEST_CODE = 100;
    public static final int REQUEST_SETTING_CODE = 200;

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
     *
     * @param callBack    权限回调
     * @param permissions
     */
    public void requestPermission(PermissionPort.PermissionCallBack callBack, String... permissions) {
        if (checkPermission(permissions)) {
            callBack.success();
        }
        List<String> rationalePermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                rationalePermissions.add(permission);
                SimpleLog.d("再次提示：" + permission);
            }
        }
        if (rationalePermissions.size() > 0) {
            //显示提示
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("权限");
            builder.setMessage("应用运行需要的权限");
            builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    toAppSetting();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                }
            });
            builder.create().show();
        } else {
            requestPermissionExcute(callBack, permissions);
        }
    }

    private void toAppSetting() {
        PackageManager manager = activity.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = manager.getPackageInfo(activity.getPackageName(), 0);

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageInfo.packageName));
            activity.startActivityForResult(intent,REQUEST_SETTING_CODE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * 执行请求权限
     *
     * @param callBack
     * @param permissions
     */
    public void requestPermissionExcute(PermissionPort.PermissionCallBack callBack, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE);
    }


}
