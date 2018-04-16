package com.shch.lz.ssm.util.key;

import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Link at 15:44 on 4/16/18.
 */
// todo why?
public class SystemClock {
    private final long period;

    private final AtomicLong now;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "System Clock");
            thread.setDaemon(true);
            return thread;
        });
        scheduledExecutorService.scheduleAtFixedRate(() -> now.set(System.currentTimeMillis()), period, period, TimeUnit.MILLISECONDS);
    }

    private SystemClock(long period) {
        this.period = period;
        this.now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    private static class InstanceHolder {
        public static final SystemClock INSTANCE = new SystemClock(1);
    }

    private static SystemClock instance() {
        return InstanceHolder.INSTANCE;
    }

    private long currentTimeMillis() {
        return now.get();
    }

    public static long now() {
        return instance().currentTimeMillis();
    }

    public static String nowDate() {
        return new Timestamp(instance().currentTimeMillis()).toString();
    }

//    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 1000000000; i++) {
//            SystemClock.now();
//        }
//        System.out.println("Time Cost：" + (System.currentTimeMillis() - start));
//        start = System.currentTimeMillis();
//        for (int i = 0; i < 1000000000; i++) {
//            System.currentTimeMillis();
//        }
//        System.out.println("Time Cost：" + (System.currentTimeMillis() - start));
//    }
}
