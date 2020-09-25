package org.simple.net.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * org.simple.net.util
 *
 * @author Simple
 * @date 2020/9/11
 * @desc
 * 任务工具
 */
public class TaskUtil {

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

    private static TaskUtil taskUtil;

    private TaskUtil() {
        init();
    }

    public static TaskUtil getInstance() {
        if (null == taskUtil) {
            synchronized (TaskUtil.class) {
                if (null == taskUtil) {
                    taskUtil = new TaskUtil();
                }
            }
        }
        return taskUtil;
    }

    private void init() {
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new TaskThreadFactory());
    }


    /**
     * 执行任务
     *
     * @param runnable
     */
    public void runTask(Runnable runnable) {
        executor.execute(runnable);

    }

    static class TaskThreadFactory implements ThreadFactory {
        AtomicInteger atomicInteger = new AtomicInteger();
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "task-pool-" + atomicInteger.getAndIncrement());
            return thread;
        }
    }
}
