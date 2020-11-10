package org.simple.util.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/22
 * @desc
 */
public class PermissionActivity extends Activity {

    private PermissionUtil permissionUtil;
    private List<String> permissions = null;
    private PermissionPort.PermissionCallBack callBack;

    private List<String> grantPermissions;
    private List<String> definePermissions;
    List<String> rationalePermissions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissions = getIntent().getStringArrayListExtra("permissions");
        callBack = PermissionPort.getInstance().getCallBack();
        grantPermissions = new ArrayList<>();
        definePermissions = new ArrayList<>();
        rationalePermissions = new ArrayList<>();
        permission();
    }

    private void permission() {
        if (null == permissions || permissions.size() == 0) {
            throw new RuntimeException("请求权限为空");
        }
        permissionUtil = new PermissionUtil(this);
        permissionUtil.requestPermission(callBack, permissions.toArray(new String[permissions.size()]));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionUtil.REQUEST_SETTING_CODE) {
            List<String> permissions = new ArrayList<>();
            for (String permission : this.permissions) {
                if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(this, permission)) {
                    permissions.add(permission);
                }
            }
            if (permissions.size() > 0) {
                //设置之后还有未拿到的权限
                callBack.define(permissions);
            } else {
                callBack.success();
            }
            finish();
        }
    }


    private void toAppSetting() {
        PackageManager manager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = manager.getPackageInfo(this.getPackageName(), 0);

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageInfo.packageName));
            this.startActivityForResult(intent, PermissionUtil.REQUEST_SETTING_CODE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.REQUEST_CODE) {
            //权限请求码
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    grantPermissions.add(permissions[i]);
                } else {
                    definePermissions.add(permissions[i]);
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        rationalePermissions.add(permissions[i]);
                    }
                }
            }
            if (rationalePermissions.size() > 0) {
                //显示提示
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                        callBack.define(definePermissions);
                        finish();
                    }
                });
                builder.create().show();
            } else {
                if (definePermissions.size() == 0) {
                    callBack.success();
                } else {
                    callBack.define(definePermissions);
                }
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {

    }
}
