package org.plain.old.retro.shooter.engine.clock;

import java.util.List;

/**
 * The type Clock rate.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 21.06.2020 19:16 <p>
 */
public class ClockRate {

    /**
     * The constant NS_IN_SECOND.
     */
    public final static long NS_IN_SECOND = 1000000000;

    /**
     * The constant MS_IN_SECOND.
     */
    public final static long MS_IN_SECOND = 1000000;

    /**
     * The constant MAX_FREQUENCY.
     */
    public final static long MAX_FREQUENCY = 120;

    /**
     * The constant MIN_FREQUENCY.
     */
    public final static long MIN_FREQUENCY = 10;

    private long frequency;

    private long rateTime;

    private long previousMoment;

    private long lastFpsTime = 0;

    private short herz = 0;

    /**
     * Instantiates a new Clock rate.
     */
    public ClockRate() {
        this(ClockRate.MAX_FREQUENCY);
    }

    /**
     * Instantiates a new Clock rate.
     *
     * @param frequency the frequency
     */
    public ClockRate(long frequency) {
        this.frequency = frequency;
        this.rateTime = NS_IN_SECOND / this.frequency;
        this.previousMoment = System.nanoTime();
    }

    public void clock(List<RateTimer.Action> actions, boolean slp, long currentMoment) {
        this.beep();

        if (actions != null && !actions.isEmpty()) {
            actions.forEach(p -> p.act());
        }

        if (slp) this.sleep(currentMoment);
    }

    private void beep() {
        long currentTime = System.nanoTime();
        long updateLength = currentTime - this.previousMoment;
        this.previousMoment = currentTime;

        lastFpsTime += updateLength;
        this.herz++;

        if (lastFpsTime >= NS_IN_SECOND) {
            lastFpsTime = 0;
            this.herz = 0;
        }
    }

    private void sleep(long currentMoment) {
        try {
            long timeOut = (currentMoment - System.nanoTime() + this.rateTime);

            if (timeOut <= 0) return;

            Thread.currentThread().sleep(timeOut / MS_IN_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public short getHerz() {
        return this.herz;
    }
}
