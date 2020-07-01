package org.plain.old.retro.shooter.math;

/**
 * The interface Vector.
 *
 * @param <P> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:30 <p>
 */
interface Vector<P extends Point> {

    /**
     * Gets a.
     *
     * @return the a
     */
    Point getA();

    /**
     * Gets b.
     *
     * @return the b
     */
    Point getB();
}
