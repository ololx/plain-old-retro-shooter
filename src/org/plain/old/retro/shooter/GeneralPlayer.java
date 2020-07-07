package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.math.Vector2d;

public class GeneralPlayer {

    public final static double MOVE_SPEED = 0.08;
    public final static double ROTATION_SPEED = 0.045;

    public Vector2d position;

    public Vector2d direction;

    public Vector2d movementVector;

    public Vector2d rotationVector;

    public GeneralPlayer(double x, double y, double x2, double y2) {
        this(x, y, x2, y2, MOVE_SPEED, ROTATION_SPEED);
    }

    public GeneralPlayer(double x, double y, double x2, double y2, double moveStep, double rotationStep) {
        this.position = new Vector2d(x, y);
        this.direction = new Vector2d(x2, y2);
        this.movementVector = new Vector2d(moveStep, moveStep);
    }

    public void moveForward(int[][] space) {
        Vector2d shiftVector = position.add(movementVector.multiply(direction));

        if (space[(int)shiftVector.getX()][(int) position.getY()] == 0
                && space[(int) position.getX()][(int)shiftVector.getY()] == 0) {
            this.position = shiftVector;
        }
    }

    public void moveBackward(int[][] space) {
        Vector2d shiftVector = position.subtract(movementVector.multiply(direction));

        if (space[(int)shiftVector.getX()][(int) position.getY()] == 0
                && space[(int) position.getX()][(int)shiftVector.getY()] == 0) {
            this.position = shiftVector;
        }
    }

    public void turnLeft(int[][] space) {
        Vector2d shiftVector = this.direction.rotate(1 * Math.PI / 2);

        if(space[(int)(position.getX() + shiftVector.getX() * MOVE_SPEED)][(int) position.getY()] == 0)
            position.setX(position.getX() + shiftVector.getX() * MOVE_SPEED);

        if(space[(int) position.getX()][(int)(position.getY() + shiftVector.getY() * MOVE_SPEED)] == 0)
            position.setY(position.getY() + shiftVector.getY() * MOVE_SPEED);
    }

    public void turnRight(int[][] space) {
        Vector2d shiftVector = this.direction.rotate(-1 * Math.PI / 2);

        if(space[(int)(position.getX() + shiftVector.getX() * MOVE_SPEED)][(int) position.getY()] == 0)
            position.setX(position.getX() + shiftVector.getX() * MOVE_SPEED);

        if(space[(int) position.getX()][(int)(position.getY() + shiftVector.getY() * MOVE_SPEED)] == 0)
            position.setY(position.getY() + shiftVector.getY() * MOVE_SPEED);
    }

    public void moveLeft() {
        direction = direction.rotate(ROTATION_SPEED);
    }

    public void moveRight() {
        direction = direction.rotate(-ROTATION_SPEED);
    }

    @Override
    public String toString() {
        return "POS: " + this.position.toString() + "DIR: " + direction.toString();
    }
}
