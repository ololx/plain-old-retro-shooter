package org.plain.old.retro.shooter; /**
 * Copyright 2020 the project plain-old-retro-shooter authors
 * and the original author or authors annotated by {@author}
 */

import java.util.Arrays;
import java.util.Objects;

/**
 * The type Clock rate.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 21.06.2020 19:16 <p>
 */
public class ClockRate {

    /**
     * The interface Processing.
     *
     * @param <T> the type parameter
     */
    @FunctionalInterface
    public interface Processing<T> {
        /**
         * Process.
         *
         * //@param arg the args
         */
        void process();

        default Processing<T> andThen(Processing<? super T> after) {
            Objects.requireNonNull(after);

            return () -> {
                process();
                after.process();
            };
        }
    }

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

    private long fps = 0;

    /**
     * Instantiates a new Clock rate.
     */
    public ClockRate() {
        this(MAX_FREQUENCY);
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

    /**
     * Clock.
     *
     * @param <T> the type parameter
     */
    public void clock() {
        this.clock(null);
    }

    /**
     * Clock.
     *
     * //@param <T>        the type parameter
     * @param processing the processing
     */
    public void clock(Processing<?>... processing) {
        this.beep();

        if (processing != null && processing.length > 0) {
            Arrays.stream(processing).forEach(p -> p.process());
        }

        this.sleep();
    }

    private void beep() {
        long currentTime = System.nanoTime();
        long updateLength = currentTime - this.previousMoment;
        this.previousMoment = currentTime;

        lastFpsTime += updateLength;
        fps++;

        if (lastFpsTime >= NS_IN_SECOND) {
            System.out.printf("fps: %s\r", fps);
            lastFpsTime = 0;
            fps = 0;
        }
    }

    private void sleep() {
        try {
            long timeOut = (this.previousMoment - System.nanoTime() + this.rateTime);

            if (timeOut <= 0) return;

            Thread.sleep(timeOut / MS_IN_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
