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
    private Button btnDecimal;
    private Button btnTime;
    private Button btnFile;

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
        btnDecimal = findViewById(R.id.btn_decimal);
        btnTime = findViewById(R.id.btn_time);
        btnFile = findViewById(R.id.btn_file);
        btnSp.setOnClickListener(this);
        btnTask.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
        btnDecimal.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sp:
                toSpUtilTest();
                break;
            case R.id.btn_task:
                toTaskTest();
                break;
            case R.id.btn_phone:
                toPhoneTest();
                break;
            case R.id.btn_decimal:
                toDecimalTest();
                break;
            case R.id.btn_time:
                toTimeTest();
                break;
            case R.id.btn_file:
                toFileTest();
                break;
            default:
                break;
        }
    }

    private void toFileTest() {
        Intent intent = new Intent(this, FileUtilTestActivity.class);
        startActivity(intent);
    }

    private void toTimeTest() {
        Intent intent = new Intent(this, TimeUtilTestActivity.class);
        startActivity(intent);
    }

    private void toDecimalTest() {
        Intent intent = new Intent(this, DecimalUtilTestActivity.class);
        startActivity(intent);
    }

    private void toPhoneTest() {
        Intent intent = new Intent(this, PhoneInfoTestActivity.class);
        startActivity(intent);
    }

    private void toTaskTest() {
        Intent intent = new Intent(this, TaskManagerTestActivity.class);
        startActivity(intent);
    }

    private void toSpUtilTest() {
        Intent intent = new Intent(this, SPUtilTestActivity.class);
        startActivity(intent);
    }

}
