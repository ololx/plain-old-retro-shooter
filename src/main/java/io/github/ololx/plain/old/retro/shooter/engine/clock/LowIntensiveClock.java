package io.github.ololx.plain.old.retro.shooter.engine.clock;

/**
 * The type Low intensive clock.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 05.08.2020 20:29 <p>
 */
public class LowIntensiveClock extends AbstractClock {

    /**
     * Instantiates a new Low Intensive Clock Driver.
     *
     * @param frequency the frequency
     * @param actions   the actions
     */
    public LowIntensiveClock(final long frequency, final ClockAction<?>... actions) {
        super(frequency, actions);
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        long previousMoment = System.nanoTime();
        double deltaMoment = 0;
        short currentFrequency = 0;
        long duration = 0;

        while (this.isActive) {
            long currentMoment = System.nanoTime();
            deltaMoment = currentMoment - previousMoment;
            previousMoment = currentMoment;
            duration += deltaMoment;
            currentFrequency++;

            if (duration > Duration.NS_IN_SECOND.getValue()) {
                this.frequency = currentFrequency;
                currentFrequency = 0;
                duration = 0;
            }

            this.actions.forEach(action -> action.act());

            try {
                long sleepTime = (long) (currentMoment - System.nanoTime() + this.time);

                if (sleepTime > 0) Thread.currentThread().sleep(sleepTime / Duration.MS_IN_SECOND.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
