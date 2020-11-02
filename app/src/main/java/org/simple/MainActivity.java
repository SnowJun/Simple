package org.simple;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;

import org.simple.image.ImageTestActivity;
import org.simple.net.HttpsTestActivity;
import org.simple.net.NormalTestActivity;
import org.simple.util.UtilTestActivity;

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

        AndPermission.with(this).runtime().permission(Permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .rationale(new Rationale<List<String>>() {
                    @Override
                    public void showRationale(Context context, List<String> data, RequestExecutor executor) {

                    }
                }).start();

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
