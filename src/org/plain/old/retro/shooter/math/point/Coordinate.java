package org.plain.old.retro.shooter.math.point;

/**
 * The interface Coordinate.
 *
 * @param <D> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:33 <p>
 */
public interface Coordinate<D extends Number> {

    /**
     * The constant ZERO.
     */
    Number ZERO = 0;

    /**
     * Returns a shallow copy of this {@code Point<D>} instance.
     *
     * @return a clone of this {@code Point<D>} instance
     */
    Coordinate<D> clone();
}
