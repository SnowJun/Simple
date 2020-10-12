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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_test);
        initView();
    }

    private void initView() {
        btnSp = findViewById(R.id.btn_sp);
        btnSp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sp:
                toSpUtilTest();
                break;
            default:
                break;
        }
    }

    private void toSpUtilTest() {
        Intent intent = new Intent(this,SPUtilTestActivity.class);
        startActivity(intent);
    }

}
