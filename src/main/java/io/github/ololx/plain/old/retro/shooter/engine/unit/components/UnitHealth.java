package io.github.ololx.plain.old.retro.shooter.engine.unit.components;

/**
 * project plain-old-retro-shooter
 * created 2021-12-19 14:51
 *
 * @author Alexander A. Kropotin
 */
public class UnitHealth implements Health {

    public static final int DEFAULT_VALUE = 100;

    private int initialValue;

    private int value;

    public UnitHealth() {
        this(DEFAULT_VALUE);
    }

    public UnitHealth(int value) {
        this.initialValue = value;
        this.value = value;
    }

    @Override
    public void reset() {
        this.value = this.initialValue;
    }

    @Override
    public void increase(int value) {
        this.value += value;
    }

    @Override
    public void decrease(int value) {
        this.value -= value;
        if (this.value <= 0) {
            this.value = 0;
        }
        System.out.println("The new health value = " + this.value);
    }

    @Override
    public void set(int value) {
        this.value = this.initialValue;
    }

    @Override
    public int get() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
