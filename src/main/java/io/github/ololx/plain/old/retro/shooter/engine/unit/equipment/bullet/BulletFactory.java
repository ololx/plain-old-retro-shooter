package io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.bullet;

import io.github.ololx.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Sprite;

/**
 * The interface Bullet factory.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 31.07.2020 09:33 <p>
 */
public interface BulletFactory {

    /**
     * The constant SPRITE.
     */
    Sprite SPRITE = new Sprite("fire-ball.png",0.5 , 0.5);

    /**
     * Gets instance.
     *
     * @param position  the position
     * @param direction the direction
     * @return the instance
     */
    static Bullet getInstance(Vector2d position, Vector2d direction) {
        return new Bullet(
                position.getX(),
                position.getY(),
                direction.getX(),
                direction.getY(),
                Bullet.MOVE_SPEED,
                Bullet.DEFAULT_RADIUS,
                SPRITE
        );
    }
}
