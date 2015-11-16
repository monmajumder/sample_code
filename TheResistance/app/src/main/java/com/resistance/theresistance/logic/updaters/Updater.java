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

    /**
     * Makes a new updater. Doesn't automatically start it.
     * http://english.stackexchange.com/questions/16161/one-who-creates-is-a-creator-what-is-one-who-updates
     */
    public Updater() {
        executor = Executors.newScheduledThreadPool(1);
    }

    /**
     * Finds the update method.
     * @return the method that does the updating.
     */
    protected abstract Runnable getRunnable();

    /**
     * Starts the Updater.
     * If already updating, it continues updating.
     */
    public void start() {
        if (update != null && !update.isCancelled())
            System.err.println("Already updating");
        else
            update = executor.scheduleAtFixedRate(getRunnable(), 0, 5, TimeUnit.SECONDS);
    }

    /**
     * Stops the Updater.
     * If already stopped it remains stopped.
     */
    public void stop() {
        if (update != null)
            update.cancel(true);
    }
}
