package com.resistance.theresistance.logic.updaters;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Updates something every five seconds.
 * Right now it just starts when created.
 * TBD: Be able to start and stop the timer.
 */
public abstract class Updater {

    public Updater() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(getRunnable(), 0, 5, TimeUnit.SECONDS);
    }

    protected abstract Runnable getRunnable();
}
