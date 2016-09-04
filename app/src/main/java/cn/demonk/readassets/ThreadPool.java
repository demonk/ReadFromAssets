package cn.demonk.readassets;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ligs on 8/27/16.
 */
public class ThreadPool {
    private static final int MIN_POOL_SIZE = 1;
    private static final int MAX_POOL_SIZE = 10;
    private static final int MAX_IDLE_TIME = 60;//s

    private static ThreadPool sInstance = new ThreadPool(MIN_POOL_SIZE);

    private int mPoolSize;

    private ExecutorService mThreadPool;

    private ThreadPool(int size) {
        this.mPoolSize = size;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                MIN_POOL_SIZE, MAX_POOL_SIZE,
                MAX_IDLE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new ThreadPoolFactory());

        executor.allowCoreThreadTimeOut(true);
        mThreadPool = executor;
    }

    public static final ThreadPool instance() {
        return sInstance;
    }

    public void post(Runnable task) {
        mThreadPool.execute(task);
    }
}
