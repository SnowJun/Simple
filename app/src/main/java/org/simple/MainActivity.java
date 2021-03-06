package org.simple;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.simple.image.ImageTestActivity;
import org.simple.net.HttpsTestActivity;
import org.simple.net.NormalTestActivity;
import org.simple.util.SimpleUtil;
import org.simple.util.UtilTestActivity;
import org.simple.util.util.PermissionPort;

import java.util.List;

/**
 * org.simple
 *
 * @author Simple
 * @date 2020/9/28
 * @desc
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnNormal;
    private Button btnHttps;
    private Button btnImage;
    private Button btnUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnNormal = findViewById(R.id.btn_normal);
        btnHttps = findViewById(R.id.btn_https);
        btnImage = findViewById(R.id.btn_image);
        btnUtil = findViewById(R.id.btn_util);
        btnNormal.setOnClickListener(this);
        btnHttps.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        btnUtil.setOnClickListener(this);


        SimpleUtil.getPermissionUtil().requestPermission(this, new PermissionPort.PermissionCallBack() {
            @Override
            public void success() {
                SimpleLog.d("请求权限成功");
            }

            @Override
            public void define(List<String> definePermissions) {
                for (String string : definePermissions) {
                    SimpleLog.e("请求权限失败：" + string);
                }
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                toNormalTest();
                break;
            case R.id.btn_https:
                toHttpsTest();
                break;
            case R.id.btn_image:
                toImageTest();
                break;
            case R.id.btn_util:
                toUtilTest();
                break;
            default:
                break;
        }
    }

    private void toUtilTest() {
        Intent intent = new Intent(this, UtilTestActivity.class);
        startActivity(intent);
    }

    private void toImageTest() {
        Intent intent = new Intent(this, ImageTestActivity.class);
        startActivity(intent);
    }

    private void toHttpsTest() {
        Intent intent = new Intent(this, HttpsTestActivity.class);
        startActivity(intent);
    }

    private void toNormalTest() {
        Intent intent = new Intent(this, NormalTestActivity.class);
        startActivity(intent);
    }


}
