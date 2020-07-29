package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.equip.Bullet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @project plain-old-retro-shooter
 * @created 21.07.2020 13:41
 * <p>
 * @author Alexander A. Kropotin
 */
public class BoomStick {

    int fireTimes = 15;

    int currentFireTimes = 0;

    int reloadTimes = 30;

    int currentReloadTime = 0;

    private boolean isShooting;

    private boolean isReload;

    private List<Sprite> sprites;

    private List<Integer> fireFrames;

    private List<Integer> reloadFrames;

    private int mainFrame;

    {
        this.sprites = new ArrayList<>();
        List<Integer> fireFrames = new ArrayList<>();
        List<Integer> reloadFrames = new ArrayList<>();
        this.mainFrame = 0;
        this.isShooting = false;
        this.isReload = false;
    }

    public BoomStick(List<Sprite> sprites,
                     List<Integer> fireFrames,
                     List<Integer> reloadFrames,
                     int mainFrame) {
        this.mainFrame = mainFrame;
        this.sprites = sprites;
        this.fireFrames = fireFrames;
        this.reloadFrames = reloadFrames;
    }

    public void update() {
        if (this.isShooting()) {
            if (currentFireTimes < this.fireTimes) {
                this.currentFireTimes++;
            } else {
                this.currentFireTimes = 0;
                this.isShooting = false;
                this.isReload = true;
            }
        }
        else if (this.isReload())
            if (currentReloadTime < this.reloadTimes) {
                this.currentReloadTime++;
            } else {
                this.currentReloadTime = 0;
                this.isReload = false;
            }
    }

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

    public void shoot() {
        isShooting = !isReload ? true : false;
    }

    public void reload() {
        isReload = !isShooting ? true : false;
    }

    public boolean isShooting() {
        return this.isShooting;
    }

    public boolean isReload() {
        return this.isReload;
    }
}
