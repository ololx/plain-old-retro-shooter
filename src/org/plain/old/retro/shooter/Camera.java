package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.linear.Vector2d;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class Camera extends GeneralPlayer {

    public Vector2d plain;

    public Camera(double x, double y, double x2, double y2, double x3, double y3) {
        this(x, y, x2, y2, x3, y3, MOVE_SPEED, ROTATION_SPEED);
    }

    public Camera(double x, double y, double x2, double y2, double x3, double y3, double moveStep, double rotationStep) {
        super(x, y, x2, y2, MOVE_SPEED, ROTATION_SPEED);
        this.plain = new Vector2d(x3, y3);
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        plain = plain.rotate(ROTATION_SPEED);
    }

    @Override
    public void moveRight() {
        super.moveRight();
        plain = plain.rotate(-ROTATION_SPEED);
    }
}
