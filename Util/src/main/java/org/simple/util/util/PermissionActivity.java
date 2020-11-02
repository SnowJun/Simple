package org.simple.util.util;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissions = getIntent().getStringArrayListExtra("permissions");
        callBack = PermissionPort.getInstance().getCallBack();
        permission();
    }

    private void permission() {
        if (null == permissions){
            throw new RuntimeException("请求权限为空");
        }
        permissionUtil = new PermissionUtil(this);
        permissionUtil.requestPermission(callBack, (String[]) permissions.toArray());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.REQUEST_CODE){
            //权限请求码
            if (grantResults.length > 0){

            }
        }
    }

}
