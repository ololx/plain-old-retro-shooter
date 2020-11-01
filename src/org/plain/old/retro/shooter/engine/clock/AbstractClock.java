package org.plain.old.retro.shooter.engine.clock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type Abstract clock.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 05.08.2020 20:29 <p>
 */
public abstract class AbstractClock implements Clock {

    /**
     * The Flow.
     */
    protected final Thread flow;

    /**
     * The Actions.
     */
    protected final List<ClockAction> actions;

    /**
     * The Frequency.
     */
    protected long frequency;

    /**
     * The Time.
     */
    protected double time;

    /**
     * The Is active.
     */
    protected boolean isActive;

    {
        this.flow = new Thread(this);
        this.isActive = false;

    }

    /**
     * Instantiates a new Clock.
     *
     * @param frequency the frequency
     * @param actions   the actions
     */
    public AbstractClock(final long frequency, final ClockAction<?>... actions) {
        this.frequency = frequency != 0 ? frequency : Duration.NS_IN_SECOND.getValue();
        this.time = Duration.NS_IN_SECOND.getValue() / this.frequency;
        this.actions = actions != null ? Arrays.asList(actions) : Collections.EMPTY_LIST;
    }

    /**
     * Start.
     */
    @Override
    public void start() {
        this.activate();
        this.flow.start();
    }

    /**
     * Stop.
     */
    @Override
    public void stop() {
        this.deactivate();

        try {
            this.flow.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pause.
     */
    @Override
    public void pause() {
        this.deactivate();
    }

    /**
     * Gets frequency.
     *
     * @return the frequency
     */
    @Override
    public long getFrequency() {
        return this.frequency;
    }

    /**
     * Activate.
     */
    private void activate() {
        this.isActive = true;
    }

    /**
     * Deactivate.
     */
    private void deactivate() {
        this.isActive = false;
    }
}
