package org.plain.old.retro.shooter.math;

import org.plain.old.retro.shooter.math.point.Coordinate;

import java.util.Objects;

/**
 * The type Abstract vector.
 *
 * @param <P> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:32 <p>
 */
public class AbstractVector<P extends Coordinate> implements Vector<P> {

    /**
     * The A.
     */
    protected P a;

    /**
     * The B.
     */
    protected P b;

    /**
     * Instantiates a new Abstract vector.
     *
     * @param a the a
     * @param b the b
     */
    public AbstractVector(P a, P b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public P getA() {
        return this.a;
    }

    @Override
    public P getB() {
        return this.b;
    }

    @Override
    public void setB(P b) {
        Objects.nonNull(b);
        this.b = b;
    }
}
