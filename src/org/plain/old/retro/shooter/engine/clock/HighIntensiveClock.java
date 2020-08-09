package org.plain.old.retro.shooter.engine.clock;

/**
 * @project plain-old-retro-shooter
 * @created 05.08.2020 20:29
 * <p>
 * @author Alexander A. Kropotin
 */
public class HighIntensiveClock extends AbstractClock {

    /**
     * Instantiates a new High Intensive Clock Driver.
     *
     * @param frequency the frequency
     * @param actions   the actions
     */
    public HighIntensiveClock(final long frequency, final ClockAction<?>... actions) {
        super(frequency, actions);
    }

    @Override
    public void run() {
        long previousMoment = System.nanoTime();
        double deltaMoment = 0;
        short currentFrequency = 0;
        long duration = System.nanoTime();

        while (this.isActive) {
            long currentMoment = System.nanoTime();
            deltaMoment += (currentMoment - previousMoment) / time;
            previousMoment = currentMoment;

            if (deltaMoment >= 1) {
                this.actions.forEach(action -> action.act());
                currentFrequency++;
                deltaMoment--;
            }

            if (System.nanoTime() - duration > Duration.NS_IN_SECOND.getValue()) {
                this.frequency = currentFrequency;
                currentFrequency = 0;
                duration += Duration.NS_IN_SECOND.getValue();
            }
        }
    }
}
