package org.plain.old.retro.shooter.engine.clock;

/**
 * @project plain-old-retro-shooter
 * @created 05.08.2020 15:29
 * <p>
 * @author Alexander A. Kropotin
 */
public interface Clock extends Runnable {

    void run();

    void start();

    void stop();

    void pause();

    long getFrequency();
}
