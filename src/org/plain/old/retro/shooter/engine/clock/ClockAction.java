package org.plain.old.retro.shooter.engine.clock;

import java.util.Objects;

/**
 * @project plain-old-retro-shooter
 * @created 05.08.2020 10:34
 * <p>
 * @author Alexander A. Kropotin
 *
 * The interface Action.
 *
 * @param <A> the type parameter
 */
@FunctionalInterface
public interface ClockAction<A> {

    /**
     * Act.
     */
    void act();

    /**
     * And then action.
     *
     * @param action the action
     * @return the action
     */
    default ClockAction<A> andThen(ClockAction<? super A> action) {
        Objects.requireNonNull(action);

        return () -> {
            this.act();
            action.act();
        };
    }
}