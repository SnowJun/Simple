package org.simple.util;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.simple.R;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/10/14
 * @desc
 */
public class TimeUtilTestActivity extends AppCompatActivity {

    private TextView tvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_time);
        initView();
        setInfo();
    }

    private void setInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("时间信息\n");
        buffer.append("当前时间：" + SimpleUtil.getTimeUtil().getCurrentFormatDate() + "\n");
        buffer.append("当前时间戳：" + SimpleUtil.getTimeUtil().getCurrentTime() + "\n");
        buffer.append("格式化时间：" + SimpleUtil.getTimeUtil().formatDate(System.currentTimeMillis()) + "\n");
        buffer.append("解析时间：" + SimpleUtil.getTimeUtil().parseTime("2020-10-15 10:10:10") + "\n");

        tvInfo.setText(buffer.toString());
    }

    private void initView() {
        tvInfo = findViewById(R.id.tv_info);
    }


}
