package io.github.ololx.plain.old.retro.shooter.engine.unit;

import io.github.ololx.plain.old.retro.shooter.engine.Space2d;
import io.github.ololx.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Camera;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Sprite;
import io.github.ololx.plain.old.retro.shooter.engine.unit.components.DamageLogic;
import io.github.ololx.plain.old.retro.shooter.engine.unit.components.GameObject;
import io.github.ololx.plain.old.retro.shooter.engine.unit.components.Health;
import io.github.ololx.plain.old.retro.shooter.engine.unit.components.UnitHealth;
import io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;
import io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.weapon.MonsterSpliting;

import java.util.ArrayList;
import java.util.Vector;

/**
 * The type Enemy.
 */
public class Enemy extends AbstractUnit implements GameObject<DamageLogic> {

    /**
     * The constant MOVE_SPEED.
     */
    public static final double MOVE_SPEED = 0.05;

    /**
     * The constant ROTATION_SPEED.
     */
    public static final double ROTATION_SPEED = 0.15;

    /**
     * The constant DEFAULT_RADIUS.
     */
    public static final double DEFAULT_RADIUS = 0.25d;

    /**
     * The Direction.
     */
    private Vector2d direction;

    /**
     * The Movement vector.
     */
    private Vector2d movementVector;

    MonsterSpliting stick = new MonsterSpliting(
            new ArrayList<>(){{
                add(new Sprite("equip/boomstick-1.png", 2, 2));
                add(new Sprite("equip/boomstick-2.png", 2, 2));
                add(new Sprite("equip/boomstick-3.png", 2, 2));
                add(new Sprite("equip/boomstick-4.png", 2, 2));
                add(new Sprite("equip/boomstick-5.png", 2, 2));
                add(new Sprite("equip/boomstick-6.png", 2, 2));
            }},
            new ArrayList<>(){{
                add(1);
                add(2);
            }},
            new ArrayList<>(){{
                add(3);
                add(4);
                add(5);
            }},
            0
    );

    public Health health = new UnitHealth(650);

    /**
     * Instantiates a new Enemy.
     *
     * @param x       the x
     * @param y       the y
     * @param texture the texture
     */
    public Enemy(double x, double y, Sprite texture) {
		    this(x, y, DEFAULT_RADIUS, texture);
	  }

    /**
     * Instantiates a new Enemy.
     *
     * @param x       the x
     * @param y       the y
     * @param radius  the radius
     * @param texture the texture
     */
    public Enemy(double x, double y, double radius, Sprite texture) {
        super(x, y, radius, texture);
        this.direction = new Vector2d(++x, ++y);
        this.movementVector = new Vector2d(MOVE_SPEED, MOVE_SPEED);

        this.health.set(150);
	  }

    public void update(Camera otherUnit, Space2d map) {
        Vector2d shortLine = otherUnit.getPosition().subtract(this.position);
        double distanceToOther = shortLine.getModule();

        if (distanceToOther > 5d) return;
        else if (distanceToOther <= 0.2d) {
            otherUnit.update(new DamageLogic(25));
        }

        double angle = otherUnit.getAngle() / 2;
        double angleToUnitCenter = otherUnit.getDirection().getAngle(this.position.subtract(otherUnit.getPosition()));
        this.direction = shortLine.getNormalized();

        this.stick.update(this.position.add(DEFAULT_RADIUS * 2), this.direction);

        if (angleToUnitCenter < angle) return;

        this.moveForward(map.getSpace());
    }

    public Vector<Bullet> update(Camera otherUnit, Space2d map, Vector<Bullet> bullets) {
        Vector2d shortLine = otherUnit.getPosition().subtract(this.position);
        double distanceToOther = shortLine.getModule();

        if (distanceToOther <= DEFAULT_RADIUS) {
            otherUnit.update(new DamageLogic(1));
        }

        if (distanceToOther > 8d) {
            return new Vector<>();
        }

        double angle = otherUnit.getAngle() / 2;
        double angleToUnitCenter = otherUnit.getDirection().getAngle(this.position.subtract(otherUnit.getPosition()));
        this.direction = shortLine.getNormalized();

        Vector<Bullet> vectors = this.stick.update(this.position.add(new Vector2d(DEFAULT_RADIUS * 2, DEFAULT_RADIUS * 2).multiply(direction)), this.direction);

        if (distanceToOther >= 2d || angleToUnitCenter < angle) return vectors;

        this.moveForward(map.getSpace());

        return vectors;
    }

    /**
     * Move forward.
     *
     * @param space the space
     */
    private void moveForward(int[][] space) {
        this.shiftPosition(space, position.add(getStepPosition()));
    }

    /**
     * Shift position.
     *
     * @param space       the space
     * @param shiftVector the shift vector
     */
    private void shiftPosition(int[][] space, Vector2d shiftVector) {
        if (shiftVector.getX() >= space.length - 1 || shiftVector.getX() <= 1 
            || shiftVector.getY() >= space[0].length - 1 || shiftVector.getY() <= 1) return;
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

    @Override
    public void update(DamageLogic logic) {
        logic.apply(health);
    }
}
