package org.plain.old.retro.shooter.math.point;

import java.util.Objects;

/**
 * The type Point3D.
 *
 * @param <D> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:34 <p>
 */
public class Point3D<D extends Number> extends Point2D<D> implements Coordinate3D<D> {

    protected D z;

    /**
     * Instantiates a new Point3D with zero coordinates.
     */
    public Point3D() {
        this((D) ZERO, (D) ZERO, (D) ZERO);
    }

    /**
     * Instantiates a new Point2D.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point3D(D x, D y, D z) {
        super(x, y);
        this.z = z;
    }

    @Override
    public D getZ() {
        return this.z;
    }

    @Override
    public void setZ(D x) {
        Objects.requireNonNull(z);
        this.z = z;
    }

    @Override
    public Coordinate<D> clone() {
        return new Point3D<D>(this.getX(), this.getY(), this.getZ());
    }
}
