package io.github.ololx.plain.old.retro.shooter.engine.calculus.linear;

/**
 * The type Rotation matrix 2 d.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 02.08.2020 10:44 <p>
 */
public class RotationMatrix2d extends Matrix2d {

    /**
     * Instantiates a new Rotation matrix 2 d.
     *
     * @param angle the angle
     */
    public RotationMatrix2d(double angle) {
        super(
                Math.cos(angle), -Math.sin(angle),
                Math.sin(angle), Math.cos(angle)
        );
    }
}
