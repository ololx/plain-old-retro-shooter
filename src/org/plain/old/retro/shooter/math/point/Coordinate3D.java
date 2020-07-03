package org.plain.old.retro.shooter.math.point;

/**
 * The interface Coordinate3D.
 *
 * @param <D> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:33 <p>
 */
public interface Coordinate3D<D extends Number> extends Coordinate2D<D> {

    D getZ();

    void setZ(D z);
}
