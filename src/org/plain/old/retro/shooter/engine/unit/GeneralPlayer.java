package org.plain.old.retro.shooter.engine.unit;

import org.plain.old.retro.shooter.engine.calculus.linear.RotationMatrix2d;
import org.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.graphics.Sprite;

/**
 * The type General player.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 05.07.2020 08:37 <p>
 */
public class GeneralPlayer extends AbstractUnit {

    /**
     * The constant MOVE_SPEED.
     */
    public static final double MOVE_SPEED = 0.10;

    /**
     * The constant ROTATION_SPEED.
     */
    public static final double ROTATION_SPEED = 0.05;

    //private Vector2d position;

    /**
     * The Direction.
     */
    protected Vector2d direction;

    /**
     * The Movement vector.
     */
    private Vector2d movementVector;

    /**
     * The Left rotation matrix.
     */
    private RotationMatrix2d leftRotationMatrix;

    /**
     * The Right rotation matrix.
     */
    private RotationMatrix2d rightRotationMatrix;

    /**
     * Instantiates a new General player.
     *
     * @param x  the x
     * @param y  the y
     * @param x2 the x 2
     * @param y2 the y 2
     */
    public GeneralPlayer(double x, double y, double x2, double y2) {
        this(x, y, x2, y2, MOVE_SPEED, ROTATION_SPEED);
    }

    /**
     * Instantiates a new General player.
     *
     * @param x            the x
     * @param y            the y
     * @param x2           the x 2
     * @param y2           the y 2
     * @param moveStep     the move step
     * @param rotationStep the rotation step
     */
    public GeneralPlayer(double x, double y, double x2, double y2, double moveStep, double rotationStep) {
        super(x, y, 0.2d, new Sprite("src/resources/player.png"));
        //this.position = new Vector2d(x, y);
        this.direction = new Vector2d(x2, y2);
        this.movementVector = new Vector2d(moveStep, moveStep);
        this.leftRotationMatrix = new RotationMatrix2d(rotationStep);
        this.rightRotationMatrix = new RotationMatrix2d(-rotationStep);
    }

    /**
     * Move forward.
     *
     * @param space the space
     */
    public void moveForward(int[][] space) {
        this.shiftPosition(space, position.add(getStepPosition()));
    }

    /**
     * Move backward.
     *
     * @param space the space
     */
    public void moveBackward(int[][] space) {
        this.shiftPosition(space, position.subtract(getStepPosition()));
    }

    /**
     * Turn left.
     *
     * @param space the space
     */
    public void turnLeft(int[][] space) {
        this.shiftPosition(space, position.add(getStepPosition(this.direction.rotate(Math.PI / 2))));
    }

    /**
     * Turn right.
     *
     * @param space the space
     */
    public void turnRight(int[][] space) {
        this.shiftPosition(space, position.subtract(getStepPosition(this.direction.rotate(Math.PI / 2))));
    }

    /**
     * Move left.
     */
    public void moveLeft() {
        direction = direction.rotate(this.leftRotationMatrix);
    }

    /**
     * Move right.
     */
    public void moveRight() {
        direction = direction.rotate(this.rightRotationMatrix);
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public Vector2d getPosition() {
        return this.position;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Vector2d getDirection() {
        return this.direction;
    }

    /**
     * Shift position.
     *
     * @param space       the space
     * @param shiftVector the shift vector
     */
    private void shiftPosition(int[][] space, Vector2d shiftVector) {
        if (space[(int) shiftVector.getX()][(int) shiftVector.getY()] == 0) this.position = shiftVector;
    }

    /**
     * Gets step position.
     *
     * @param direction the direction
     * @return the step position
     */
    private Vector2d getStepPosition(Vector2d direction) {
        return movementVector.multiply(direction);
    }

    /**
     * Gets step position.
     *
     * @return the step position
     */
    private Vector2d getStepPosition() {
        return getStepPosition(this.direction);
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "POS: " + this.position.toString() + " DIR: " + direction.toString();
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.position.getX() + "&" + this.position.getY();
    }
}
