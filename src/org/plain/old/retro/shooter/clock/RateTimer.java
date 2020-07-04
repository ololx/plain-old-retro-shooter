package org.plain.old.retro.shooter.clock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The type Rate timer.
 *
 * @author Alexander A. Kropotin
 * @project plain-old-retro-shooter
 * @created 24.06.2020 19:20 <p>
 */
public class RateTimer implements Runnable {

    /**
     * The interface Action.
     *
     * @param <T> the type parameter
     */
    @FunctionalInterface
    public interface Action<T> {

        /**
         * Act.
         */
        void act();

        /**
         * And then action.
         *
         * @param action the action
         * @return the action
         */
        default Action<T> andThen(Action<? super T> action) {
            Objects.requireNonNull(action);

            return () -> {
                this.act();
                action.act();
            };
        }
    }

    /**
     * The constant NS_IN_SECOND.
     */
    public final static long NS_IN_SECOND = 1000000000;

    /**
     * The constant DEFAULT_FREQUENCY.
     */
    public final static long DEFAULT_FREQUENCY = 200;

    private final long frequency;

    private final double rateTime;

    private short herz;

    private Thread thread;

    private boolean isRunning = false;

    private List<Action> actions;

    /**
     * Instantiates a new Rate timer.
     */
    public RateTimer() {
        this(DEFAULT_FREQUENCY);
    }

    /**
     * Instantiates a new Rate timer.
     *
     * @param actions the actions
     */
    public RateTimer(Action<?>... actions) {
        this(DEFAULT_FREQUENCY, actions);
    }

    /**
     * Instantiates a new Rate timer.
     *
     * @param frequency the frequency
     * @param actions   the actions
     */
    public RateTimer(long frequency, Action<?>... actions) {
        this.actions = actions != null ? Arrays.asList(actions) : Collections.EMPTY_LIST;
        this.frequency = frequency;
        this.rateTime = NS_IN_SECOND / this.frequency;
        this.herz = (short) frequency;
    }

    @Override
    public void run() {
        long previousMoment = System.nanoTime();
        double deltaMoment = 0;
        short currentHerz = 0;
        long timer = System.nanoTime();

        while (this.isRunning) {
            long currentMoment = System.nanoTime();
            deltaMoment += (currentMoment - previousMoment) / rateTime;
            previousMoment = currentMoment;

            if (deltaMoment >= 1) {
                this.actions.forEach(action -> action.act());
                currentHerz++;
                deltaMoment--;
            }

            if (System.nanoTime() - timer > NS_IN_SECOND) {
                this.herz = currentHerz;
                currentHerz = 0;
                timer += NS_IN_SECOND;
            }
        }
    }

    /**
     * Start.
     */
    public synchronized void start() {
        this.isRunning = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Stop.
     */
    public synchronized void stop() {
        this.isRunning = false;

        try {
            this.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets herz.
     *
     * @return the herz
     */
    public short getHerz() {
        return this.herz;
    }
}
