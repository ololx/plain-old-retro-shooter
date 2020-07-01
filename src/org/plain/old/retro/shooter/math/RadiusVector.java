package org.plain.old.retro.shooter.math;

/**
 * The type Radius vector.
 *
 * @param <P> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:32 <p>
 */
public class RadiusVector<P extends Point> extends AbstractVector<P> {

    /**
     * Instantiates a new Radius vector.
     *
     * @param b the b
     */
    public RadiusVector(P b) {
        super((P) new Point2D(), b);
    }
}
