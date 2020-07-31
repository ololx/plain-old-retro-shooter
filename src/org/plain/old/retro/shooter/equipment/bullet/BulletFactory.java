package org.plain.old.retro.shooter.equipment.bullet;

import org.plain.old.retro.shooter.Sprite;
import org.plain.old.retro.shooter.linear.Vector2d;

/**
 * @project plain-old-retro-shooter
 * @created 31.07.2020 09:33
 * <p>
 * @author Alexander A. Kropotin
 */
public interface BulletFactory {

    Sprite SPRITE = new Sprite("src/resources/fire-ball.png", 0.25, 0.25);

    static Bullet getInstance(Vector2d position, Vector2d direction) {
        return new Bullet(position, direction, SPRITE);
    }
}
