package io.github.ololx.plain.old.retro.shooter.engine.unit.components;

/**
 * project plain-old-retro-shooter
 * created 2021-12-19 14:36
 *
 * @author Alexander A. Kropotin
 */
public interface Health extends Stats {

    void reset();

    void increase(int value);

    void decrease(int value);

    void set(int value);

    int get();
}
