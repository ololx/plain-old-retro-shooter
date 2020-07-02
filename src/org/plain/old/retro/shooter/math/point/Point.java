package org.plain.old.retro.shooter.math.point;

/**
 * The type Point.
 *
 * @param <D> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:34 <p>
 */
public class Point<D extends Number> extends AbstractCoordinate<D> {

    /**
     * Instantiates a new Point.
     *
     * @param coordinate the coordinate
     */
    public Point(D... coordinate) {
        super(coordinate);
    }
}
