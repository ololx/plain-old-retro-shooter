package io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.bullet;

import io.github.ololx.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Sprite;
import io.github.ololx.plain.old.retro.shooter.engine.unit.AbstractUnit;
import io.github.ololx.plain.old.retro.shooter.engine.unit.components.DamageLogic;

/**
 * The type Bullet.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 29.07.2020 11:01 <p>
 */
public class Bullet extends AbstractUnit {

    /**
     * The constant MOVE_SPEED.
     */
    public final static double MOVE_SPEED = 30;

    /**
     * The constant DEFAULT_RADIUS.
     */
    public final static double DEFAULT_RADIUS = 0.01;

    public DamageLogic damage = new DamageLogic(20);

    /**
     * The Direction.
     */
    private Vector2d direction;

    /**
     * The Movement vector.
     */
    private Vector2d movementVector;

    /**
     * Instantiates a new Bullet.
     *
     * @param x        the x
     * @param y        the y
     * @param x2       the x 2
     * @param y2       the y 2
     * @param moveStep the move step
     * @param radius   the radius
     * @param texture  the texture
     */
    public Bullet(double x, double y, double x2, double y2, double moveStep, double radius, Sprite texture) {
        super(x, y, radius, texture);
        this.direction = new Vector2d(x2, y2);
        this.movementVector = new Vector2d(moveStep, moveStep);
        this.aligement = ALIGNEMENT.CENTER;
    }

    /**
     * Move.
     *
     * @param space the space
     */
    public void move(int[][] space) {
        this.shiftPosition(space, position.add(getStepPosition()));
    }

    /**
     * Move.
     *
     * @param space the space
     * @param speed the speed
     */
    public void move(int[][] space, double speed) {
        this.shiftPosition(space, position.add(direction.multiply(speed)));
    }

    /**
     * Shift position.
     *
     * @param space       the space
     * @param shiftVector the shift vector
     */
    private void shiftPosition(int[][] space, Vector2d shiftVector) {
        if ((shiftVector.getX() < space.length)
                && (shiftVector.getY() < space[0].length)
                && (space[(int) shiftVector.getX()][(int) shiftVector.getY()] == 0)) this.position = shiftVector;
        else this.destroy();
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
     * Gets sprite.
     *
     * @return the sprite
     */
    public Sprite getSprite() {
        return texture;
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
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "POS: " + this.position.toString() + " DIR: " + direction.toString();
    }
}
