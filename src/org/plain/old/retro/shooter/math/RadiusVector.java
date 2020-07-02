package org.plain.old.retro.shooter.math;

import org.plain.old.retro.shooter.math.point.Coordinate;
import org.plain.old.retro.shooter.math.point.Point;
import org.plain.old.retro.shooter.math.point.PointFactory;

/**
 * The type Radius vector.
 *
 * @param <P> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:32 <p>
 */
public class RadiusVector<P extends Coordinate> extends AbstractVector<P> {

    /**
     * Instantiates a new Radius vector.
     *
     * @param b the b
     */
    public RadiusVector(P a, P b) {
        super(a, b);
    }
}
