package org.simple.util;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.simple.R;
import org.simple.SimpleLog;

import java.util.HashSet;
import java.util.Set;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/10/12
 * @desc
 */
public class SPUtilTestActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnSaveInt;
    private Button btnSaveLong;
    private Button btnSaveFloat;
    private Button btnSaveBoolean;
    private Button btnSaveString;
    private Button btnSaveStringSet;
    private Button btnGetInt;
    private Button btnGetLong;
    private Button btnGetFloat;
    private Button btnGetBoolean;
    private Button btnGetString;
    private Button btnGetStringSet;
    private Button btnGetStringSet1;
    private Button btnGetStringSet2;
    private Button btnAddStringSet;
    private Button btnRemoveStringSet;

    private Button btnRemove;

    private TextView tvResultInt;
    private TextView tvResultLong;
    private TextView tvResultFloat;
    private TextView tvResultBoolean;
    private TextView tvResultString;
    private TextView tvResultStringSet;
    private TextView tvResultStringSet1;
    private TextView tvResultStringSet2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_test_sp);
        initView();
    }

    private void initView() {
        btnSaveInt = findViewById(R.id.btn_save_int);
        btnSaveLong = findViewById(R.id.btn_save_long);
        btnSaveFloat = findViewById(R.id.btn_save_float);
        btnSaveBoolean = findViewById(R.id.btn_save_boolean);
        btnSaveString = findViewById(R.id.btn_save_string);
        btnSaveStringSet = findViewById(R.id.btn_save_string_set);
        btnGetInt = findViewById(R.id.btn_get_int);
        btnGetLong = findViewById(R.id.btn_get_long);
        btnGetFloat = findViewById(R.id.btn_get_float);
        btnGetBoolean = findViewById(R.id.btn_get_boolean);
        btnGetString = findViewById(R.id.btn_get_string);
        btnGetStringSet = findViewById(R.id.btn_get_string_set);
        btnGetStringSet1 = findViewById(R.id.btn_get_string_set1);
        btnGetStringSet2 = findViewById(R.id.btn_get_string_set2);
        btnAddStringSet = findViewById(R.id.btn_add_string_set);
        btnRemoveStringSet = findViewById(R.id.btn_remove_string_set);

        btnRemove = findViewById(R.id.btn_remove);

        btnSaveInt.setOnClickListener(this);
        btnSaveLong.setOnClickListener(this);
        btnSaveFloat.setOnClickListener(this);
        btnSaveBoolean.setOnClickListener(this);
        btnSaveString.setOnClickListener(this);
        btnSaveStringSet.setOnClickListener(this);
        btnAddStringSet.setOnClickListener(this);
        btnRemoveStringSet.setOnClickListener(this);
        btnRemove.setOnClickListener(this);

        btnGetInt.setOnClickListener(this);
        btnGetLong.setOnClickListener(this);
        btnGetFloat.setOnClickListener(this);
        btnGetBoolean.setOnClickListener(this);
        btnGetString.setOnClickListener(this);
        btnGetStringSet.setOnClickListener(this);
        btnGetStringSet1.setOnClickListener(this);
        btnGetStringSet2.setOnClickListener(this);


        tvResultInt = findViewById(R.id.tv_result_int);
        tvResultLong = findViewById(R.id.tv_result_long);
        tvResultFloat = findViewById(R.id.tv_result_float);
        tvResultBoolean = findViewById(R.id.tv_result_boolean);
        tvResultString = findViewById(R.id.tv_result_string);
        tvResultStringSet = findViewById(R.id.tv_result_string_set);
        tvResultStringSet1 = findViewById(R.id.tv_result_string_set1);
        tvResultStringSet2 = findViewById(R.id.tv_result_string_set2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_int:
                SimpleUtil.getSPUtil().putInt(this, "int", 10);
                break;
            case R.id.btn_save_long:
                SimpleUtil.getSPUtil().putLong(this, "long", 100);
                break;
            case R.id.btn_save_float:
                SimpleUtil.getSPUtil().putFloat(this, "float", 1.5f);
                break;
            case R.id.btn_save_boolean:
                SimpleUtil.getSPUtil().putBoolean(this, "boolean", true);
                break;
            case R.id.btn_save_string:
                SimpleUtil.getSPUtil().putString(this, "string", "nick");
                break;
            case R.id.btn_save_string_set:
                Set<String> stringSet = new HashSet<>();
                stringSet.add("nick");
                stringSet.add("age");
                SimpleUtil.getSPUtil().putStringSet(this, "string-set", stringSet);
                break;
            case R.id.btn_get_int:
                tvResultInt.setText("Int值：" + SimpleUtil.getSPUtil().getInt(this, "int"));
                break;
            case R.id.btn_get_long:
                tvResultLong.setText("Long值：" + SimpleUtil.getSPUtil().getLong(this, "long"));
                break;
            case R.id.btn_get_float:
                tvResultFloat.setText("Float值：" + SimpleUtil.getSPUtil().getFloat(this, "float"));
                break;
            case R.id.btn_get_boolean:
                tvResultBoolean.setText("Boolean值：" + SimpleUtil.getSPUtil().getBoolean(this, "boolean"));
                break;
            case R.id.btn_get_string:
                tvResultString.setText("String值：" + SimpleUtil.getSPUtil().getString(this, "string"));
                break;
            case R.id.btn_get_string_set:
                Set<String> stringSet1 = SimpleUtil.getSPUtil().getStringSet(this, "string-set");
                if (null == stringSet1) {
                    tvResultStringSet.setText("string-set不存在");
                } else {
                   StringBuffer buffer = new StringBuffer();
                   for (String str:stringSet1){
                       buffer.append(str);
                   }
                   tvResultStringSet.setText("string-set:"+buffer.toString());
                }
                break;
            case R.id.btn_get_string_set1:
                Set<String> stringSet2 = SimpleUtil.getSPUtil().getStringSet(this, "string-set");
                if (null == stringSet2) {
                    tvResultStringSet1.setText("string-set不存在");
                } else {
                    StringBuffer buffer = new StringBuffer();
                    for (String str:stringSet2){
                        buffer.append(str);
                    }
                    tvResultStringSet1.setText("string-set:"+buffer.toString());
                }
                break;
            case R.id.btn_get_string_set2:
                Set<String> stringSet3 = SimpleUtil.getSPUtil().getStringSet(this, "string-set");
                if (null == stringSet3) {
                    tvResultStringSet2.setText("string-set不存在");
                } else {
                    StringBuffer buffer = new StringBuffer();
                    for (String str:stringSet3){
                        buffer.append(str);
                    }
                    tvResultStringSet2.setText("string-set:"+buffer.toString());
                }
                break;
            case R.id.btn_add_string_set:
                boolean addResult = SimpleUtil.getSPUtil().addStringInSet(this,"string-set","birthday");
                SimpleLog.d("添加结果："+addResult);
                break;
            case R.id.btn_remove_string_set:
                boolean removeResult = SimpleUtil.getSPUtil().removeStringInSet(this,"string-set","age");
                SimpleLog.d("移除结果："+removeResult);
                break;
            case R.id.btn_remove:
                SimpleUtil.getSPUtil().remove(this,"int");
                SimpleUtil.getSPUtil().remove(this,"long");
                SimpleUtil.getSPUtil().remove(this,"float");
                SimpleUtil.getSPUtil().remove(this,"boolean");
                SimpleUtil.getSPUtil().remove(this,"string");
                SimpleUtil.getSPUtil().remove(this,"string-set");
                break;
            default:
                break;

        }
    }

}
