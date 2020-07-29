package org.plain.old.retro.shooter.equip;

import org.plain.old.retro.shooter.linear.Vector2d;

/**
 * @project plain-old-retro-shooter
 * @created 29.07.2020 11:01
 * <p>
 * @author Alexander A. Kropotin
 */
public class Bullet {

    public final static double MOVE_SPEED = 1.0;

    public boolean isHited = false;

    public Vector2d position;

    public Vector2d direction;

    public Vector2d movementVector;

    public Bullet(Vector2d position, Vector2d direction) {
        this(position.getX(), position.getY(), direction.getX(), direction.getY(), MOVE_SPEED);
    }

    public Bullet(double x, double y, double x2, double y2) {
        this(x, y, x2, y2, MOVE_SPEED);
    }

    public Bullet(double x, double y, double x2, double y2, double moveStep) {
        this.position = new Vector2d(x, y);
        this.direction = new Vector2d(x2, y2);
        this.movementVector = new Vector2d(moveStep, moveStep);
    }

    public void move(int[][] space) {
        this.shiftPosition(space, position.add(getStepPosition()));
    }

    private void shiftPosition(int[][] space, Vector2d shiftVector) {
        if (space[(int) shiftVector.getX()][(int) shiftVector.getY()] == 0) this.position = shiftVector;
        else this.isHited = true;
    }

    private Vector2d getStepPosition(Vector2d direction) {
        return movementVector.multiply(direction);
    }

    private Vector2d getStepPosition() {
        return getStepPosition(this.direction);
    }

    @Override
    public String toString() {
        return "POS: " + this.position.toString() + " DIR: " + direction.toString();
    }
}
