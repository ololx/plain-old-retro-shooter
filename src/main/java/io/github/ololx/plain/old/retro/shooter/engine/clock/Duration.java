package io.github.ololx.plain.old.retro.shooter.engine.clock;

/**
 * The enum Duration.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 05.08.2020 15:06 <p>
 */
public enum  Duration {

    /**
     * Ns in second duration.
     */
    NS_IN_SECOND(1000000000L),
    /**
     * Ms in second duration.
     */
    MS_IN_SECOND(1000000L);

    /**
     * The Value.
     */
    private final long value;

    /**
     * Instantiates a new Duration.
     *
     * @param value the value
     */
    Duration(long value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public long getValue() {
        return this.value;
    }
}
