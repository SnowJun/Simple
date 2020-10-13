package org.simple.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.simple.R;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/10/13
 * @desc
 */
public class TaskManagerTestActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tvInfo;
    private TextView tvInfoDelay;
    private TextView tvInfoRate;
    private Button btnTask;
    private Button btnTaskDelay;
    private Button btnTaskRate;
    private Button btnTaskRateCancel;
    private Button btnTaskCancel;
    private Button btnTaskCancel1;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_test_task);
        initView();
    }

    private void initView() {
        tvInfo = findViewById(R.id.tv_info);
        tvInfoDelay = findViewById(R.id.tv_info_delay);
        tvInfoRate = findViewById(R.id.tv_info_rate);
        btnTask = findViewById(R.id.btn_task);
        btnTaskDelay = findViewById(R.id.btn_task_delay);
        btnTaskRate = findViewById(R.id.btn_task_rate);
        btnTaskRateCancel = findViewById(R.id.btn_task_rate_cancel);
        btnTaskCancel = findViewById(R.id.btn_task_cancel);
        btnTaskCancel1 = findViewById(R.id.btn_task_cancel1);
        btnTask.setOnClickListener(this);
        btnTaskCancel.setOnClickListener(this);
        btnTaskCancel1.setOnClickListener(this);
        btnTaskDelay.setOnClickListener(this);
        btnTaskRate.setOnClickListener(this);
        btnTaskRateCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_task:
                doTask();
                break;
            case R.id.btn_task_cancel:
                doTaskCancel();
                break;
            case R.id.btn_task_cancel1:
                doTaskCancel1();
                break;
            case R.id.btn_task_delay:
                doDelayTask();
                break;
            case R.id.btn_task_rate:
                doRateTask();
                break;
            case R.id.btn_task_rate_cancel:
                cancelRateTask();
                break;
            default:
                break;
        }
    }

    /**
     * 取消周期任务
     */
    private void cancelRateTask() {
        SimpleUtil.getTaskManager().cancelTask(future);
    }

    private int i = 0;
    private Future<?> future;

    /**
     * 执行周期任务
     */
    private void doRateTask() {
        future = SimpleUtil.getTaskManager().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                final int current = i++;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvInfoRate.setText("周期任务现象：" + current);
                    }
                });
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * 执行延时任务
     */
    private void doDelayTask() {
        SimpleUtil.getTaskManager().schedule(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvInfoDelay.setText("延时任务现象：10s了");
                    }
                });
            }
        }, 10, TimeUnit.SECONDS);
    }

    /**
     * 执行无阻塞的可取消任务
     */
    private void doTaskCancel1() {
        final Future<?> future = SimpleUtil.getTaskManager().submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000000; i++) {
                    org.simple.SimpleLog.d("循环执行：" + i);
                    final int current = i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvInfo.setText("无阻塞任务打印：" + current);
                        }
                    });
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                }
            }
        });
        SimpleUtil.getTaskManager().schedule(new Runnable() {
            @Override
            public void run() {
                SimpleUtil.getTaskManager().cancelTask(future);
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行有阻塞的可取消任务
     */
    private void doTaskCancel() {
        final Future<?> future = SimpleUtil.getTaskManager().submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        Thread.sleep(1000);
                        final int current = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvInfo.setText("阻塞任务打印：" + current);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        });
        SimpleUtil.getTaskManager().schedule(new Runnable() {
            @Override
            public void run() {
                SimpleUtil.getTaskManager().cancelTask(future);
            }
        }, 10000, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行普通任务
     */
    private void doTask() {
        SimpleUtil.getTaskManager().submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(100);
                        final int current = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvInfo.setText("普通任务打印：" + current);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
