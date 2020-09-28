package org.simple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
     btnNormal = findViewById(R.id.btn_normal);
     btnHttps = findViewById(R.id.btn_https);
     btnNormal.setOnClickListener(this);
     btnHttps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_normal:
                toNormalTest();
                break;
            case R.id.btn_https:
                toHttpsTest();
                break;
            default:
                break;
        }
    }

    private void toHttpsTest() {
        Intent intent = new Intent(this,HttpsTestActivity.class);
        startActivity(intent);
    }

    private void toNormalTest() {
        Intent intent = new Intent(this,NormalTestActivity.class);
        startActivity(intent);
    }

}
