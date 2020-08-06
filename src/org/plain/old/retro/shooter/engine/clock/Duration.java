package org.plain.old.retro.shooter.engine.clock;

/**
 * @project plain-old-retro-shooter
 * @created 05.08.2020 15:06
 * <p>
 * @author Alexander A. Kropotin
 */
public enum  Duration {

    NS_IN_SECOND(1000000000L),
    MS_IN_SECOND(1000000L);

    private final long value;

    Duration(long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }
}
