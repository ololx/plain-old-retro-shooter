package org.plain.old.retro.shooter.engine.physics;

import org.plain.old.retro.shooter.unit.Enemy;
import org.plain.old.retro.shooter.engine.Space2d;
import org.plain.old.retro.shooter.unit.equipment.bullet.Bullet;
import org.plain.old.retro.shooter.engine.linear.Vector2d;

import java.util.Vector;

/**
 * @project plain-old-retro-shooter
 * @created 31.07.2020 09:19
 * <p>
 * @author Alexander A. Kropotin
 */
public interface BulletHitScanner {

    static void scan(Vector<Bullet> bullets, Vector<Enemy> enemies, short hertz, Space2d map) {
        bullets = (Vector<Bullet>) bullets.clone();
        enemies = (Vector<Enemy>) enemies.clone();

        double hitScanStep = (100 * Bullet.MOVE_SPEED);
        double bulletSpeed = (Bullet.MOVE_SPEED / hertz) / hitScanStep;

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            if (bullet.isHited) continue;

            for (int m = 0; m < hitScanStep; m++) {
                bullet.move(map.getSpace(), bulletSpeed);
                Vector2d bVec = bullet.position;

                for (int j = 0; j < enemies.size(); j++) {
                    Enemy enemy = enemies.get(j);
                    Vector2d eVec = new Vector2d(enemy.xPosition, enemy.yPosition);

                    if (!enemy.isAlive) continue;

                    if (eVec.subtract(bVec).getModule() <= Bullet.RADIUS + enemy.radius) {
                        enemy.isAlive = false;
                        bullet.isHited = true;
                        break;
                    }
                }
            }
        }
    }
}