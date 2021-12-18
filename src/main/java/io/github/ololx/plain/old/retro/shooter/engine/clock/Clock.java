package io.github.ololx.plain.old.retro.shooter.engine.clock;

/**
 * The interface Clock.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 05.08.2020 15:29 <p>
 */
public interface Clock extends Runnable {

    /**
     * Run.
     */
    void run();

    /**
     * Start.
     */
    void start();

    /**
     * Stop.
     */
    void stop();

    /**
     * Pause.
     */
    void pause();

    /**
     * Gets frequency.
     *
     * @return the frequency
     */
    long getFrequency();
}
