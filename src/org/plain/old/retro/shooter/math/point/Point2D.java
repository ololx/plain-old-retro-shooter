package org.plain.old.retro.shooter.math.point;

import java.util.Objects;

/**
 * The type Point2D.
 *
 * @param <D> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:34 <p>
 */
public class Point2D<D extends Number> implements Coordinate2D<D> {

    protected D x;

    protected D y;

    /**
     * Instantiates a new Point2D with zero coordinates.
     */
    public Point2D() {
        this((D) ZERO, (D) ZERO);
    }

    /**
     * Instantiates a new Point2D.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point2D(D x, D y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public D getX() {
        return this.x;
    }

    @Override
    public void setX(D x) {
        Objects.requireNonNull(x);
        this.x = x;
    }

    @Override
    public D getY() {
        return this.y;
    }

    @Override
    public void setY(D y) {
        Objects.requireNonNull(y);
        this.y = y;
    }

    @Override
    public Coordinate<D> clone() {
        return new Point2D<D>(this.getX(), this.getY());
    }
}
