package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.linear.Vector2d;

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
        this.shiftPosition(space, position.add(getStepPosition()));
    }

    public void moveBackward(int[][] space) {
        this.shiftPosition(space, position.subtract(getStepPosition()));
    }

    public void turnLeft(int[][] space) {
        this.shiftPosition(space, position.add(getStepPosition(this.direction.rotate(Math.PI / 2))));
    }

    public void turnRight(int[][] space) {
        this.shiftPosition(space, position.subtract(getStepPosition(this.direction.rotate(Math.PI / 2))));
    }

    public void moveLeft() {
        direction = direction.rotate(ROTATION_SPEED);
    }

    public void moveRight() {
        direction = direction.rotate(-ROTATION_SPEED);
    }

    private void shiftPosition(int[][] space, Vector2d shiftVector) {
        if (space[(int) shiftVector.getX()][(int) shiftVector.getY()] == 0) this.position = shiftVector;
    }

    private Vector2d getStepPosition(Vector2d direction) {
        return movementVector.multiply(direction);
    }

    private Vector2d getStepPosition() {
        return getStepPosition(this.direction);
    }

    @Override
    public String toString() {
        return "POS: " + this.position.toString() + "DIR: " + direction.toString();
    }
}
