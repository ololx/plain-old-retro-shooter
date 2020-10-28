package org.plain.old.retro.shooter.engine.unit.equipment.bullet;

import org.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.graphics.Sprite;

/**
 * @project plain-old-retro-shooter
 * @created 31.07.2020 09:33
 * <p>
 * @author Alexander A. Kropotin
 */
public interface BulletFactory {

    Sprite SPRITE = new Sprite("src/resources/fire-ball.png",1.5 , 1.5);

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
