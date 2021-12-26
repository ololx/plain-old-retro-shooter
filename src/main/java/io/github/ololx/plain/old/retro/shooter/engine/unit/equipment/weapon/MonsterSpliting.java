package io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.weapon;

import io.github.ololx.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Sprite;
import io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;
import io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.bullet.BulletFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * The type Boom stick.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 21.07.2020 13:41 <p>
 */
public class MonsterSpliting {

    /**
     * The constant BULLETS_IN_SHOT.
     */
    public static final int BULLETS_IN_SHOT = 1;

    /**
     * The Fire times.
     */
    int fireTimes = 10;

    /**
     * The Current fire times.
     */
    int currentFireTimes = 0;

    /**
     * The Reload times.
     */
    int reloadTimes = 20;

    /**
     * The Current reload time.
     */
    int currentReloadTime = 0;

    /**
     * The Is shooting.
     */
    private boolean isShooting;

    /**
     * The Is reload.
     */
    private boolean isReload;

    /**
     * The Sprites.
     */
    private List<Sprite> sprites;

    /**
     * The Fire frames.
     */
    private List<Integer> fireFrames;

    /**
     * The Reload frames.
     */
    private List<Integer> reloadFrames;

    /**
     * The Main frame.
     */
    private int mainFrame;

    {
        this.sprites = new ArrayList<>();
        List<Integer> fireFrames = new ArrayList<>();
        List<Integer> reloadFrames = new ArrayList<>();
        this.mainFrame = 0;
        this.isShooting = false;
        this.isReload = false;
    }

    /**
     * Instantiates a new Boom stick.
     *
     * @param sprites      the sprites
     * @param fireFrames   the fire frames
     * @param reloadFrames the reload frames
     * @param mainFrame    the main frame
     */
    public MonsterSpliting(List<Sprite> sprites,
                           List<Integer> fireFrames,
                           List<Integer> reloadFrames,
                           int mainFrame) {
        this.mainFrame = mainFrame;
        this.sprites = sprites;
        this.fireFrames = fireFrames;
        this.reloadFrames = reloadFrames;
    }

    /**
     * Update.
     */
    public Vector<Bullet> update(Vector2d position, Vector2d direction) {
        if (!this.isShooting() && !isReload()) {
            return this.shoot(position, direction);
        } else if (this.isShooting()) {
            if (currentFireTimes < this.fireTimes) {
                this.currentFireTimes++;
            } else {
                this.currentFireTimes = 0;
                this.isShooting = false;
                this.isReload = true;
            }
        } else if (this.isReload()) {
            if (currentReloadTime < this.reloadTimes) {
                this.currentReloadTime++;
            } else {
                this.currentReloadTime = 0;
                this.isReload = false;
            }
        }

        return new Vector<>();
    }

    /**
     * Gets sprite.
     *
     * @return the sprite
     */
    public Sprite getSprite() {
        if (this.isShooting()) {
            int currentFrameNum = (int) Math.floor(currentFireTimes / ((fireTimes + 1) / fireFrames.size()));

            return this.sprites.get(this.fireFrames.get(currentFrameNum));
        }

        if (this.isReload()) {
            int currentFrameNum = (int) Math.floor(currentReloadTime / ((reloadTimes + 3) / reloadFrames.size()));

            return this.sprites.get(this.reloadFrames.get(currentFrameNum));
        }

        return this.sprites.get(mainFrame);
    }

    /**
     * Shoot.
     */
    public void shoot() {
        isShooting = !isReload ? true : false;
    }

    /**
     * Shoot vector.
     *
     * @param position  the position
     * @param direction the direction
     * @return the vector
     */
    public Vector<Bullet> shoot(Vector2d position, Vector2d direction) {
        this.shoot();

        Vector<Bullet> bullets = new Vector();
        if (this.isShooting && (this.currentFireTimes == 0)) {

            for (double c = 0; c < BULLETS_IN_SHOT; c++) {
                bullets.add(BulletFactory.getInstance(position, direction));
            }
        }

        return bullets;
    }

    /**
     * Reload.
     */
    public void reload() {
        isReload = !isShooting ? true : false;
    }

    /**
     * Is shooting boolean.
     *
     * @return the boolean
     */
    public boolean isShooting() {
        return this.isShooting;
    }

    /**
     * Is reload boolean.
     *
     * @return the boolean
     */
    public boolean isReload() {
        return this.isReload;
    }
}
