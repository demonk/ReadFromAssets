package cn.demonk.readassets;

import java.util.concurrent.ThreadFactory;

/**
 * Created by ligs on 8/27/16.
 */
public class ThreadPoolFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, NameGentor.genName());
    }

    private static class NameGentor {
        private volatile static int sec = 0;

        static synchronized String genName() {
            return "tr-" + sec++;
        }
    }
}
