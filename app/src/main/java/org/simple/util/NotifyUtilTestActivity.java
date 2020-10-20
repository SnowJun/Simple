package org.simple.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.simple.MainActivity;
import org.simple.R;
import org.simple.util.constants.Constants;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/10/20
 * @desc
 */
public class NotifyUtilTestActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnNormal;
    private Button btnNotify;
    private Button btnProgress;
    private Button btnOwn;
    private Button btnDelay;
    private Button btnDelayCancel;
    private Button btnAllCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_test_notify);
        initView();
    }

    private void initView() {
        btnNormal = findViewById(R.id.btn_normal);
        btnNotify = findViewById(R.id.btn_notify);
        btnProgress = findViewById(R.id.btn_progress);
        btnOwn = findViewById(R.id.btn_own);
        btnDelay = findViewById(R.id.btn_delay);
        btnDelayCancel = findViewById(R.id.btn_delay_cancel);
        btnAllCancel = findViewById(R.id.btn_all_cancel);

        btnNormal.setOnClickListener(this);
        btnNotify.setOnClickListener(this);
        btnProgress.setOnClickListener(this);
        btnOwn.setOnClickListener(this);
        btnDelay.setOnClickListener(this);
        btnDelayCancel.setOnClickListener(this);
        btnAllCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                showNormal();
                break;
            case R.id.btn_notify:
                showNotify();
                break;
            case R.id.btn_progress:
                showProgress();
                break;
            case R.id.btn_own:
                showOwn();
                break;
            case R.id.btn_delay:
                showDelay();
                break;
            case R.id.btn_delay_cancel:
                cancelDelay();
                break;
            case R.id.btn_all_cancel:
                cancelAll();
                break;
            default:
                break;
        }
    }

    private void cancelAll() {
        SimpleUtil.getNotificationUtil().cancelAllNotify();
    }

    private void cancelDelay() {
        SimpleUtil.getNotificationUtil().cancelNotify(id);
    }

    private int id;


    private void showDelay() {
        id = SimpleUtil.getNotificationUtil().showNotify("延时标题", "延时内容：3s延时", R.mipmap.ic_launcher,null);
    }

    private void showOwn() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.timg);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = SimpleUtil.getNotificationUtil()
                .createNotifyBuilder(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_HIGH_ID)
                .setContentTitle("自定义有标题")
                .setSubText("副标题")
                .setContentText("内容信息")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                //大图  在右侧
                .setLargeIcon(bitmap)
                .build();
        SimpleUtil.getNotificationUtil().showNotify(notification);
    }

    private void showProgress() {
        int id = 10;
        SimpleUtil.getNotificationUtil().showNotify("标题", "内容内容内容", R.mipmap.ic_launcher,id, "正在下载", 99,null);
    }

    private void showNotify() {
        SimpleUtil.getNotificationUtil().showNotify("标题", "内容内容内容", R.mipmap.ic_launcher, Notification.DEFAULT_ALL,null);
    }

    private void showNormal() {
        SimpleUtil.getNotificationUtil().showNotify("标题", "内容内容内容", R.mipmap.ic_launcher,null);
    }
}
