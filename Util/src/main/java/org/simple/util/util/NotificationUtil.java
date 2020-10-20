package org.simple.util.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import org.simple.util.SimpleLog;
import org.simple.util.constants.Constants;

import java.util.List;
import java.util.Random;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/14
 * @desc
 * 大图 大文本
 * @see NotificationCompat.Builder#setStyle(NotificationCompat.Style)
 * {@link androidx.core.app.NotificationCompat.BigPictureStyle}
 * {@link androidx.core.app.NotificationCompat.BigTextStyle}
 */
public class NotificationUtil {


    private Context context;

    private static NotificationUtil ourInstance;

    private NotificationUtil() {
    }

    public static NotificationUtil getInstance() {
        if (null == ourInstance) {
            synchronized (NotificationUtil.class) {
                if (null == ourInstance) {
                    ourInstance = new NotificationUtil();
                }
            }
        }
        return ourInstance;
    }

    /**
     * 初始化通知工具类
     * 在Application完成
     *
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        if (null == context) {
            SimpleLog.e("context 为null  无法初始化通知渠道");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0以上初始化通知的渠道
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channelHigh = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_HIGH_ID, Constants.NOTIFICATION_CHANNEL_IMPORTANCE_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel channelDefault = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_DEFAULT_ID, Constants.NOTIFICATION_CHANNEL_IMPORTANCE_DEFAULT_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channelLow = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_LOW_ID, Constants.NOTIFICATION_CHANNEL_IMPORTANCE_LOW_NAME, NotificationManager.IMPORTANCE_LOW);
            NotificationChannel channelMin = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_MIN_ID, Constants.NOTIFICATION_CHANNEL_IMPORTANCE_MIN_NAME, NotificationManager.IMPORTANCE_MIN);
            manager.createNotificationChannel(channelHigh);
            manager.createNotificationChannel(channelDefault);
            manager.createNotificationChannel(channelLow);
            manager.createNotificationChannel(channelMin);
        }
    }

    /**
     * 初始化通知工具类
     * 在Application完成
     *
     * @param context
     * @param channels 通知渠道
     */
    public void init(Context context, List<NotificationChannel> channels) {
        this.context = context;
        if (null == context) {
            SimpleLog.e("context 为null  无法初始化通知渠道");
            return;
        }
        if (null == channels || channels.size() == 0) {
            SimpleLog.e("channels 为null或者size为0  无法初始化通知渠道");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0以上初始化通知的渠道
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannels(channels);
        }
    }


    /**
     * 显示普通的通知
     *
     * @param title         标题
     * @param content       内容
     * @param iconRes       logo
     * @param pendingIntent 意图
     * @return identifier通知的标识
     */
    public int showNotify(String title, String content, int iconRes, PendingIntent pendingIntent) {
        check(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_DEFAULT_ID);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification
                = createNotifyBuilder(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_DEFAULT_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(iconRes)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        int identifier = new Random().nextInt();
        manager.notify(identifier, notification);
        return identifier;
    }


    /**
     * 显示普通的通知
     *
     * @param title             标题
     * @param content           内容
     * @param iconRes           logo
     * @param defaultNotifyMode 提示模式 提示  仅对低版本有效  8.0以上跟通知通道有关
     *                          {@link Notification#DEFAULT_ALL 所有提示
     * @return identifier通知的标识
     * @link Notification#DEFAULT_SOUND 声音提示
     * @link Notification#DEFAULT_VIBRATE 震动提示
     * @link Notification#DEFAULT_LIGHTS 呼吸灯提示
     * }
     */
    public int showNotify(String title, String content, int iconRes, int defaultNotifyMode, PendingIntent pendingIntent) {
        check(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_DEFAULT_ID);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification
                = createNotifyBuilder(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_DEFAULT_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setDefaults(defaultNotifyMode)
                .setSmallIcon(iconRes)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).build();
        int identifier = new Random().nextInt();
        manager.notify(identifier, notification);
        return identifier;
    }



    /**
     * 带进度的通知显示
     * identifier通知的标识
     *
     * @param title    标题
     * @param tick     进度提示文字
     * @param content  内容
     * @param iconRes  logo
     * @param progress 进度  0-100
     * @return
     */
    public int showNotify(String title, String content, int iconRes,int id, String tick, int progress, PendingIntent pendingIntent) {
        check(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_DEFAULT_ID);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification
                = createNotifyBuilder(Constants.NOTIFICATION_CHANNEL_IMPORTANCE_DEFAULT_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(iconRes)
                //进度提示文字
                .setTicker(tick)
                //展示一次通知
                .setOnlyAlertOnce(true)
                //进度设置
                .setProgress(100, progress, false)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        manager.notify(id, notification);
        return id;
    }


    /**
     * 创建notifyBuilder
     * 生成之后的标题等等的设置和之前的通知一样
     *
     * @param channelId 渠道id
     *                  {@link Constants#NOTIFICATION_CHANNEL_IMPORTANCE_HIGH_ID 高级别
     * @return
     * @link Constants#NOTIFICATION_CHANNEL_IMPORTANCE_DEFAULT_ID 默认级别
     * @link Constants#NOTIFICATION_CHANNEL_IMPORTANCE_LOW_ID 低级别
     * @link Constants#NOTIFICATION_CHANNEL_IMPORTANCE_MIN_ID 最小级别
     * }
     */
    public NotificationCompat.Builder createNotifyBuilder(String channelId) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context, null);
        }
        return builder;
    }

    /**
     * 提示通知
     *
     * @param notification
     * @return
     */
    public int showNotify(Notification notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            check(notification.getChannelId());
        } else {
            check(null);
        }
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int identifier = new Random().nextInt();
        manager.notify(identifier, notification);
        return identifier;
    }


    /**
     * 取消特定id的预制的通知
     *
     * @param id
     */
    public void cancelNotify(int id) {
        check(null);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(id);
    }

    /**
     * 取消所有预制通知
     */
    public void cancelAllNotify() {
        check(null);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }


    /**
     * 检测是否进行了初始化
     */
    private void check(String channelId) {
        if (null == context) {
            throw new RuntimeException("请先进行通知的初始化 ，即调用init方法");
        }
        if (null == channelId) {
            SimpleLog.e("channelId 为null  无法检测");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = manager.getNotificationChannel(channelId);
            if (null == channel) {
                throw new RuntimeException("未初始化的通知id：" + channelId);
            }
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                SimpleLog.e("用户未打开权限,无法提示通知");
            }
        }
    }


}
