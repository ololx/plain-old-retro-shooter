package org.plain.old.retro.shooter.math.point;

/**
 * The interface Coordinate2D.
 *
 * @param <D> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:33 <p>
 */
public interface Coordinate2D<D extends Number> extends Coordinate<D> {

   D getX();

   void setX(D x);

    D getY();

    void setY(D y);
}
