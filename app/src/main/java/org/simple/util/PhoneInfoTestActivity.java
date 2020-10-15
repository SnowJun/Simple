package org.simple.util;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.simple.R;
import org.simple.SimpleLog;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/10/13
 * @desc
 */
public class PhoneInfoTestActivity extends AppCompatActivity {

    private TextView tvInfo;
    private ImageView ivIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_info_test);
        initView();
        setInfo();
    }

    private void setInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("手机信息\n");
        buffer.append("制造商：" + SimpleUtil.getDeviceUtil().getPhoneManufacturer() + "\n");
        buffer.append("品牌：" + SimpleUtil.getDeviceUtil().getPhoneBrand() + "\n");
        buffer.append("型号：" + SimpleUtil.getDeviceUtil().getPhoneMode() + "\n");
        buffer.append("主板：" + SimpleUtil.getDeviceUtil().getPhoneBoard() + "\n");
        buffer.append("名称：" + SimpleUtil.getDeviceUtil().getPhoneName() + "\n");
        buffer.append("产品：" + SimpleUtil.getDeviceUtil().getPhoneProduct() + "\n");
        buffer.append("硬件：" + SimpleUtil.getDeviceUtil().getPhoneHardware() + "\n");
        buffer.append("host：" + SimpleUtil.getDeviceUtil().getPhoneHost() + "\n");
        buffer.append("显示id：" + SimpleUtil.getDeviceUtil().getPhoneDisplay() + "\n");
        buffer.append("手机id：" + SimpleUtil.getDeviceUtil().getPhoneId() + "\n");
        buffer.append("User：" + SimpleUtil.getDeviceUtil().getPhoneUser() + "\n");
        buffer.append("sdk：" + SimpleUtil.getDeviceUtil().getDeviceSdk() + "\n");
        buffer.append("sdkInit：" + SimpleUtil.getDeviceUtil().getDeviceSdkInt() + "\n");
        buffer.append("设备id：" + SimpleUtil.getDeviceUtil().getDeviceId() + "\n");
        buffer.append("app版本号：" + SimpleUtil.getAppUtil().getVersionCode(this) + "\n");
        buffer.append("app版本名称：" + SimpleUtil.getAppUtil().getVersionName(this) + "\n");
        buffer.append("app包名：" + SimpleUtil.getAppUtil().getPackageName(this) + "\n");
        buffer.append("app名称：" + SimpleUtil.getAppUtil().getAppName(this) + "\n");
        tvInfo.setText(buffer.toString());

        Drawable icon = SimpleUtil.getAppUtil().getAppIcon(this);
        if (null != icon ){
            ivIcon.setImageDrawable(icon);
        }else {
            SimpleLog.d("icon 为null");
        }

    }

    private void initView() {
        tvInfo = findViewById(R.id.tv_info);
        ivIcon = findViewById(R.id.iv_icon);
    }


}
