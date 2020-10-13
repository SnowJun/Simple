package org.simple.util;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.simple.R;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/10/12
 * @desc
 */
public class UtilTestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSp;
    private Button btnTask;
    private Button btnPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_test);
        initView();
    }

    private void initView() {
        btnSp = findViewById(R.id.btn_sp);
        btnTask = findViewById(R.id.btn_task);
        btnPhone = findViewById(R.id.btn_phone);
        btnSp.setOnClickListener(this);
        btnTask.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sp:
                toSpUtilTest();
                break;
            case R.id.btn_task:
                toTaskTest();
                break;
            case R.id.btn_phone:
                toPhoneTest();
                break;
            default:
                break;
        }
    }

    private void toPhoneTest() {
        Intent intent = new Intent(this,PhoneInfoTestActivity.class);
        startActivity(intent);
    }

    private void toTaskTest() {
        Intent intent = new Intent(this,TaskManagerTestActivity.class);
        startActivity(intent);
    }

    private void toSpUtilTest() {
        Intent intent = new Intent(this,SPUtilTestActivity.class);
        startActivity(intent);
    }

}
