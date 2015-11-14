package com.resistance.theresistance.logic.updaters;

/**
 * Says 'Hello World' in the command line. A proof of concept.
 */
public class HelloWorldUpdater extends Updater {
    protected Runnable getRunnable() {
        return new Runnable() {
            public void run() {
                System.out.println("Hello world");
            }
        };
    }
}
