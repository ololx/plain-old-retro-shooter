package org.plain.old.retro.shooter.math;

/**
 * The type Abstract vector.
 *
 * @param <P> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:32 <p>
 */
public class AbstractVector<P extends Point> implements Vector<P> {

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
    public Point getA() {
        return this.a;
    }

    @Override
    public Point getB() {
        return this.b;
    }
}
