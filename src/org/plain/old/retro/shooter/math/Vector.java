package org.plain.old.retro.shooter.math;

import org.plain.old.retro.shooter.math.point.Coordinate;

/**
 * The interface Vector.
 *
 * @param <P> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:30 <p>
 */
interface Vector<P extends Coordinate> {

    /**
     * Gets a.
     *
     * @return the a
     */
    Coordinate getA();

    /**
     * Gets b.
     *
     * @return the b
     */
    Coordinate getB();
}
