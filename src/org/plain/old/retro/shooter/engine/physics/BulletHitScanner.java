package org.plain.old.retro.shooter.engine.physics;

import org.plain.old.retro.shooter.engine.Space2d;
import org.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.unit.Enemy;
import org.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @project plain-old-retro-shooter
 * @created 31.07.2020 09:19
 * <p>
 * @author Alexander A. Kropotin
 */
public interface BulletHitScanner {

    static void scan(ConcurrentSkipListSet<Bullet> bullets, ConcurrentSkipListSet<Enemy> enemies, long hertz, Space2d map) {
        double hitScanStep = (100 * Bullet.MOVE_SPEED);
        double bulletSpeed = (Bullet.MOVE_SPEED / hertz) / hitScanStep;

        for (Bullet bullet : bullets) {
            if (!bullet.isExist()) continue;

            for (int m = 0; m < hitScanStep; m++) {
                bullet.move(map.getSpace(), bulletSpeed);
                Vector2d bVec = bullet.getPosition();

                for (Enemy enemy : enemies) {
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
