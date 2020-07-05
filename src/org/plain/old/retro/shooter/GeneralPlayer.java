package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.math.Vector2d;

public class GeneralPlayer {

    public final double MOVE_SPEED = 0.08;
    public final double ROTATION_SPEED = 0.045;

    private Vector2d position;

    private Vector2d direction;

    public GeneralPlayer(double x, double y, double x2, double y2) {
        this.position = new Vector2d(x, y);
        this.direction = new Vector2d(x2, y2);
    }

    public void moveForward(int[][] space) {
        if (space[(int)(position.getX() + direction.getX() * MOVE_SPEED)][(int) position.getY()] == 0
                && space[(int) position.getX()][(int)(position.getY() + direction.getY() * MOVE_SPEED)] == 0) {
            position.setX(position.getX() + direction.getX() * MOVE_SPEED);
            position.setY(position.getY() + direction.getY() * MOVE_SPEED);
        }
    }

    public void moveBackward(int[][] space) {
        if (space[(int)(position.getX() - direction.getX() * MOVE_SPEED)][(int) position.getY()] == 0
                && space[(int) position.getX()][(int)(position.getY() - direction.getY() * MOVE_SPEED)] == 0) {
            position.setX(position.getX() - direction.getX() * MOVE_SPEED);
            position.setY(position.getY() - direction.getY() * MOVE_SPEED);
        }
    }

    public void moveLeft() {
        direction = direction.rotate(-ROTATION_SPEED);
    }

    public void moveRight() {
        direction = direction.rotate(ROTATION_SPEED);
    }

    @Override
    public String toString() {
        return "POS: " + this.position.toString() + "DIR: " + direction.toString();
    }
}
