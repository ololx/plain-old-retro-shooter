package io.github.ololx.plain.old.retro.shooter.engine.physics;

import io.github.ololx.plain.old.retro.shooter.engine.Space2d;
import io.github.ololx.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import io.github.ololx.plain.old.retro.shooter.engine.unit.Enemy;
import io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The interface Bullet hit scanner.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 31.07.2020 09:19 <p>
 */
public interface BulletHitScanner {

    /**
     * Scan.
     *
     * @param bullets the bullets
     * @param enemies the enemies
     * @param hertz   the hertz
     * @param map     the map
     */
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
