package org.plain.old.retro.shooter.engine.unit;

import org.plain.old.retro.shooter.engine.linear.RotationMatrix2d;
import org.plain.old.retro.shooter.engine.linear.Vector2d;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class GeneralPlayer {

    public static final double MOVE_SPEED = 0.10;

    public static final double ROTATION_SPEED = 0.05;

    private Vector2d position;

    private Vector2d direction;

    private Vector2d movementVector;

    private RotationMatrix2d rotationMatrix;

    public GeneralPlayer(double x, double y, double x2, double y2) {
        this(x, y, x2, y2, MOVE_SPEED, ROTATION_SPEED);
    }

    public GeneralPlayer(double x, double y, double x2, double y2, double moveStep, double rotationStep) {
        this.position = new Vector2d(x, y);
        this.direction = new Vector2d(x2, y2);
        this.movementVector = new Vector2d(moveStep, moveStep);
        this.rotationMatrix = new RotationMatrix2d(rotationStep);
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

    public Vector2d getPosition() {
        return this.position;
    }

    public Vector2d getDirection() {
        return this.direction;
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
        return "POS: " + this.position.toString() + " DIR: " + direction.toString();
    }

    public String getMessage() {
        return this.position.getX() + "&" + this.position.getY();
    }
}
