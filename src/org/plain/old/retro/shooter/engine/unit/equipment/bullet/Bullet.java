package org.plain.old.retro.shooter.engine.unit.equipment.bullet;

import org.plain.old.retro.shooter.engine.graphics.Sprite;
import org.plain.old.retro.shooter.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.unit.AbstractUnit;

/**
 * @project plain-old-retro-shooter
 * @created 29.07.2020 11:01
 * <p>
 * @author Alexander A. Kropotin
 */
public class Bullet extends AbstractUnit {

    public final static double MOVE_SPEED = 30;

    public final static double DEFAULT_RADIUS = 0.01;

    private Vector2d direction;

    private Vector2d movementVector;

    public Bullet(double x, double y, double x2, double y2, double moveStep, double radius, Sprite texture) {
        super(x, y, radius, texture);
        this.direction = new Vector2d(x2, y2);
        this.movementVector = new Vector2d(moveStep, moveStep);
    }

    public void move(int[][] space) {
        this.shiftPosition(space, position.add(getStepPosition()));
    }

    public void move(int[][] space, double speed) {
        this.shiftPosition(space, position.add(direction.multiply(speed)));
    }

    private void shiftPosition(int[][] space, Vector2d shiftVector) {
        if (space[(int) shiftVector.getX()][(int) shiftVector.getY()] == 0) this.position = shiftVector;
        else this.destroy();
    }

    private Vector2d getStepPosition(Vector2d direction) {
        return movementVector.multiply(direction);
    }

    private Vector2d getStepPosition() {
        return getStepPosition(this.direction);
    }

    public Sprite getSprite() {
        return texture;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public Vector2d getDirection() {
        return this.direction;
    }

    @Override
    public String toString() {
        return "POS: " + this.position.toString() + " DIR: " + direction.toString();
    }
}
