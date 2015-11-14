package com.resistance.theresistance.logic.updaters;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Updates something every five seconds.
 * Right now it just starts when created.
 * TBD: Be able to start and stop the timer.
 */
public abstract class Updater {

    private ScheduledExecutorService executor;
    private ScheduledFuture<?> update;

    public Updater() {
        executor = Executors.newScheduledThreadPool(1);
    }

    protected abstract Runnable getRunnable();

    public void start() {
        if (update != null && !update.isCancelled())
            System.err.println("Already updating");
        else
            update = executor.scheduleAtFixedRate(getRunnable(), 0, 5, TimeUnit.SECONDS);
    }
    public void stop() {
        if (update != null)
            update.cancel(true);
    }
}
