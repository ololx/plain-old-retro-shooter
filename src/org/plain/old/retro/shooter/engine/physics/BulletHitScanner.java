package org.plain.old.retro.shooter.engine.physics;

import org.plain.old.retro.shooter.engine.Space2d;
import org.plain.old.retro.shooter.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.unit.Enemy;
import org.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;

import java.util.Collection;
import java.util.Vector;

/**
 * @project plain-old-retro-shooter
 * @created 31.07.2020 09:19
 * <p>
 * @author Alexander A. Kropotin
 */
public interface BulletHitScanner {

    static void scan(Vector<Bullet> bullets, Vector<Enemy> enemies, long hertz, Space2d map) {
        bullets = (Vector<Bullet>) bullets.clone();
        enemies = (Vector<Enemy>) enemies.clone();

        double hitScanStep = (100 * Bullet.MOVE_SPEED);
        double bulletSpeed = (Bullet.MOVE_SPEED / hertz) / hitScanStep;

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            if (!bullet.isExist()) continue;

            for (int m = 0; m < hitScanStep; m++) {
                bullet.move(map.getSpace(), bulletSpeed);
                Vector2d bVec = bullet.getPosition();

                for (int j = 0; j < enemies.size(); j++) {
                    Enemy enemy = enemies.get(j);
                    Vector2d eVec = enemy.getPosition();

                    if (!enemy.isExist()) continue;

                    if (eVec.subtract(bVec).getModule() <= Bullet.DEFAULT_RADIUS + enemy.getRadius()) {
                        enemy.destroy();
                        bullet.destroy();
                        break;
                    }
                }
            }
        }
    }
}
