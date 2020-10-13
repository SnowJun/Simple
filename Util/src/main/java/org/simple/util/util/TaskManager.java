package org.simple.util.util;

import org.simple.util.SimpleLog;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/13
 * @desc
 */
public class TaskManager {

    /**
     * 核心线程数
     */
    private final int CORE_POOL_SIZE = 3;
    /**
     * 最大县城数
     */
    private final int MAXIMUM_POOL_SIZE = 5;
    /**
     * 空闲状态下活跃时间
     * 2s
     */
    private final int KEEP_ALIVE_TIME = 2000;
    /**
     * 线程池
     */
    private ThreadPoolExecutor executor;
    /**
     * 延迟、周期执行任务
     */
    private ScheduledExecutorService executorService;

    private static TaskManager ourInstance;

    private TaskManager() {
        init();
    }

    public static TaskManager getInstance() {
        if (null == ourInstance) {
            synchronized (TaskManager.class) {
                if (null == ourInstance) {
                    ourInstance = new TaskManager();
                }
            }
        }
        return ourInstance;
    }

    private void init() {
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new TaskThreadFactory());
        executorService = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE, new TaskThreadFactory());
    }

    /**
     * 执行任务
     * <p>
     *
     * @param task
     */
    public Future<?> submit(Runnable task) {
        if (null == task) {
            SimpleLog.e("任务为null，无法提交");
            return null;
        }
        return executor.submit(task);
    }

    /**
     * 延迟执行任务
     *
     * @param task     任务
     * @param delay    延迟时间
     * @param timeUnit 时间单位
     * @return
     */
    public ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit timeUnit) {
        if (null == task) {
            SimpleLog.e("任务为null，无法提交");
            return null;
        }
        return executorService.schedule(task, delay, timeUnit);
    }

    /**
     * 延迟周期任务
     *
     * @param task     任务
     * @param delay    延迟时间
     * @param period   周期 不等第一个任务结束  直接开始第二个任务   周期为两个任务开始时间的间隔
     * @param timeUnit 时间单位
     * @return
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long delay, long period, TimeUnit timeUnit) {
        if (null == task) {
            SimpleLog.e("任务为null，无法提交");
            return null;
        }
        return executorService.scheduleAtFixedRate(task, delay, period, timeUnit);
    }

    /**
     * 延迟周期任务
     *
     * @param task     任务
     * @param delay    延迟时间
     * @param period   周期 等第一个任务结束  开始第二个任务   周期为第一个任务的结束时间和第二个任务的开始时间的间隔
     * @param timeUnit 时间单位
     * @return
     */
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay, long period, TimeUnit timeUnit) {
        if (null == task) {
            SimpleLog.e("任务为null，无法提交");
            return null;
        }
        return executorService.scheduleWithFixedDelay(task, delay, period, timeUnit);
    }

    /**
     * 取消任务
     * <p>
     * try {
     *  Thread.sleep(100);
     *   SimpleLog.d("循环执行：" + i);
     * } catch (InterruptedException e) {
     *   e.printStackTrace();
     *   //显式执行拦截  才能支持取消
     *  Thread.currentThread().interrupt();
     * }
     *
     * @param future
     * @return
     */
    public boolean cancelTask(Future<?> future) {
        if (null == future) {
            SimpleLog.e("入参future为null，无法取消");
            return false;
        }
        return future.cancel(true);
    }


    static class TaskThreadFactory implements ThreadFactory {
        AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "task-manager-pool-" + atomicInteger.getAndIncrement());
            return thread;
        }
    }

}
