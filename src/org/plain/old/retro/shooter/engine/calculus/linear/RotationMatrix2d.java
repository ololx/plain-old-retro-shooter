package org.plain.old.retro.shooter.engine.calculus.linear;

/**
 * @project plain-old-retro-shooter
 * @created 02.08.2020 10:44
 * <p>
 * @author Alexander A. Kropotin
 */
public class RotationMatrix2d extends Matrix2d {

    public RotationMatrix2d(double angle) {
        super(
                Math.cos(angle), -Math.sin(angle),
                Math.sin(angle), Math.cos(angle)
        );
    }
}
