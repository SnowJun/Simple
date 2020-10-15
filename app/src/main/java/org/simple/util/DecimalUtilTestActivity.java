package org.simple.util;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.simple.R;

import java.math.BigDecimal;


/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/10/14
 * @desc
 */
public class DecimalUtilTestActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtParas1;
    private EditText edtParas2;

    private TextView tvResult;

    private Button btnAdd;
    private Button btnSubstract;
    private Button btnMulti;
    private Button btnDivide;
    private Button btnFormat1;
    private Button btnFormat2;

    private String value1;
    private String value2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_decimal);
        initView();
    }

    private void initView() {
        edtParas1 = findViewById(R.id.edt_paras1);
        edtParas2 = findViewById(R.id.edt_paras2);

        tvResult = findViewById(R.id.tv_result);

        btnAdd = findViewById(R.id.btn_add);
        btnSubstract = findViewById(R.id.btn_substract);
        btnMulti = findViewById(R.id.btn_multi);
        btnDivide = findViewById(R.id.btn_divide);
        btnFormat1 = findViewById(R.id.btn_format);
        btnFormat2 = findViewById(R.id.btn_format2);

        btnAdd.setOnClickListener(this);
        btnSubstract.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnFormat1.setOnClickListener(this);
        btnFormat2.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_add:
                    add();
                    break;
                case R.id.btn_substract:
                    substract();
                    break;
                case R.id.btn_multi:
                    multi();
                    break;
                case R.id.btn_divide:
                    divide();
                    break;
                case R.id.btn_format:
                    format1();
                    break;
                case R.id.btn_format2:
                    format2();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            tvResult.setText("出现异常：" + e.getMessage());
        }

    }


    private void format2() {
        getValue();
        String result = SimpleUtil.getDecimalUtil().format("0.00", Double.valueOf(value1));
        tvResult.setText("结果：" + result);
    }

    private void format1() {
        getValue();
        double result = SimpleUtil.getDecimalUtil().format(2, Double.valueOf(value1));
        tvResult.setText("结果：" + result);
    }

    private void divide() {
        getValue();
        BigDecimal bigDecimal = SimpleUtil.getDecimalUtil().divide(value1, value2);
        if (null != bigDecimal) {
            tvResult.setText("结果：" + bigDecimal.toString());
        } else {
            tvResult.setText("结果为null");
        }
    }

    private void multi() {
        getValue();
        BigDecimal bigDecimal = SimpleUtil.getDecimalUtil().multiply(value1, value2);
        if (null != bigDecimal) {
            tvResult.setText("结果：" + bigDecimal.toString());
        } else {
            tvResult.setText("结果为null");
        }
    }

    private void substract() {
        getValue();
        BigDecimal bigDecimal = SimpleUtil.getDecimalUtil().subtract(value1, value2);
        if (null != bigDecimal) {
            tvResult.setText("结果：" + bigDecimal.toString());
        } else {
            tvResult.setText("结果为null");
        }
    }

    private void add() {
        getValue();
        BigDecimal bigDecimal = SimpleUtil.getDecimalUtil().add(value1, value2);
        if (null != bigDecimal) {
            tvResult.setText("结果：" + bigDecimal.toString());
        } else {
            tvResult.setText("结果为null");
        }
    }

    private void getValue() {
        value1 = edtParas1.getText().toString();
        value2 = edtParas2.getText().toString();
    }

}
