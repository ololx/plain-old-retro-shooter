package org.plain.old.retro.shooter.monitor;

import org.plain.old.retro.shooter.BoomStick;
import org.plain.old.retro.shooter.Enemy;
import org.plain.old.retro.shooter.Sprite;
import org.plain.old.retro.shooter.equip.Bullet;
import org.plain.old.retro.shooter.linear.Vector2d;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class Screen {

    private int[][] map;
    private int width;
    private int height;

    private List<Sprite> textures;

    private Map<Integer, Double> rayLengths = new HashMap<>();

    public Screen(int[][] map, int width, int height, List<Sprite> textures) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.textures = textures;
    }

    public int[] renderFloor(int[] pixels, Vector2d pos, Vector2d dir) {
        int floorTexture = 5;
        double angle = 0.60;
        Vector2d rayDirLeft = dir.rotate(angle);
        Vector2d rayDirRight = dir.rotate(-angle);

        int drawStart = this.height / 2;
        int drawEnd = this.height;
        for (int y = drawStart; y < drawEnd; y++) {
            int p = y - this.height / 2;
            double posZ = 0.5 * this.height;
            double rowDistance = posZ / p;
            double floorStepX = rowDistance * (rayDirRight.getX() - rayDirLeft.getX()) / this.width;
            double floorStepY = rowDistance * (rayDirRight.getY() - rayDirLeft.getY()) / this.width;
            double floorX = pos.getX() + rowDistance * rayDirLeft.getX();
            double floorY = pos.getY() + rowDistance * rayDirLeft.getY();

            for (int x = 0; x < this.width; x++) {
                int cellX = (int)(floorX);
                int cellY = (int)(floorY);
                int tx = (int)(textures.get(floorTexture).getWidth() * Math.abs(floorX - cellX));
                int ty = (int)(textures.get(floorTexture).getHeight() * Math.abs(floorY - cellY));
                int colorFloor = textures.get(floorTexture).getPixelSafty(tx, ty);

                pixels[x + y * width] = colorFloor;

                floorX += floorStepX;
                floorY += floorStepY;
            }
        }

        return pixels;
    }

    public int[] renderCeiling(int[] pixels, Vector2d pos, Vector2d dir) {
        int ceilingTexture = 4;
        double angle = 0.60;
        Vector2d rayDirLeft = dir.rotate(angle);
        Vector2d rayDirRight = dir.rotate(-angle);
        int drawStart = 0;
        int drawEnd = this.height / 2;

        for (int y = drawStart; y < drawEnd; y++) {
            int p = this.height / 2 - y;
            double posZ = 0.5 * this.height;
            double rowDistance = posZ / p;
            double ceilingStepX = rowDistance * (rayDirRight.getX() - rayDirLeft.getX()) / this.width;
            double ceilingStepY = rowDistance * (rayDirRight.getY() - rayDirLeft.getY()) / this.width;

            double ceilingX = pos.getX() + rowDistance * rayDirLeft.getX();
            double ceilingY = pos.getY() + rowDistance * rayDirLeft.getY();

            for (int x = 0; x < this.width; x++) {
                int cellX = (int)(ceilingX);
                int cellY = (int)(ceilingY);
                int tx = (int)(textures.get(ceilingTexture).getWidth() * Math.abs(ceilingX - cellX));
                int ty = (int)(textures.get(ceilingTexture).getHeight() * Math.abs(ceilingY - cellY));
                int colorCeiling = textures.get(ceilingTexture).getPixelSafty(tx, ty);

                pixels[x + y * width] = colorCeiling;

                ceilingX += ceilingStepX;
                ceilingY += ceilingStepY;
            }
        }

        return pixels;
    }

    public int[] renderWall(int[] pixels, Vector2d pos, Vector2d dir) {
        double angleStep = 1.20 / width;
        double angle = 0.60;

        for (int x = 0; x < width; x++) {
            Vector2d rayDir = dir.rotate(angle);
            Vector2d rayPos = pos.clone();

            boolean hit = false;
            double steps = 0.01;
            while (!hit) {
                rayPos = rayPos.add(rayDir.multiply(steps));

                if (map[(int) rayPos.getX()][(int) rayPos.getY()] != 0) hit = true;

            }

            angle -= angleStep;

            double rayLength = Math.hypot(rayPos.getX() - pos.getX(), rayPos.getY() - pos.getY());
            int wallHeight = (rayLength == 0) ? height : (int) ((int) (height / (rayLength * Math.cos(angle))));

            int drawStart = (int) (-wallHeight / 2 + height / 2);
            if (drawStart < 0) drawStart = 0;

            int drawEnd = (int) (wallHeight / 2 + height / 2);
            if (drawEnd >= height) drawEnd = height;

            int texNum = map[(int) rayPos.getX()][(int) rayPos.getY()] - 1;

            double intersectionX = pos.getX() < rayPos.getX() ? Math.abs(rayPos.getX() - (int) rayPos.getX()) : 1 - Math.abs(rayPos.getX() - (int) rayPos.getX());
            double intersectionY = pos.getY() < rayPos.getY() ? Math.abs(rayPos.getY() - (int) rayPos.getY()) : 1 - Math.abs(rayPos.getY() - (int) rayPos.getY());
            boolean horizontal = intersectionX < intersectionY ? true : false;

            double wallX = horizontal ? intersectionY  : intersectionX;
            wallX -= Math.floor(wallX);
            int texX = (int)(wallX * (textures.get(texNum).getWidth()));
            if (!horizontal && rayDir.getX() > 0) texX = (textures.get(texNum).getWidth()) - texX - 1;
            if (horizontal && rayDir.getY() < 0) texX = (textures.get(texNum).getWidth()) - texX - 1;

            double imgPixYSize = 1.0 * textures.get(texNum).getHeight() / wallHeight;
            int imgPixYStart = height >= wallHeight ? 0 : (int) (((wallHeight / 2) - (height / 2)) * imgPixYSize);

            for (int y = drawStart; y < drawEnd; y++) {
                int texY = imgPixYStart + (int)(((y - drawStart) * imgPixYSize));
                int color = textures.get(texNum).getPixelSafty(texX, texY);
                pixels[x + y * width] = color;
            }

            this.rayLengths.put(x, rayLength);
        }

        return pixels;
    }

    public int[] renderGun(int[] pixels, Sprite gun) {
        int xStart = width / 2 - gun.getWidth() / 2;
        int yStart = height - gun.getHeight();

        for (int x = 0; x < gun.getWidth(); x++) {
            for (int y = 0; y < gun.getHeight(); y++) {
                int pixelColor = gun.getPixel(x, y);
                if (pixelColor != 0) pixels[(x + xStart) + (y + yStart) * width] = gun.getPixel(x, y);
            }
        }

        int aimXStart = (width / 2) - (width / 100);
        int aimXEnd = (width / 2) + (width / 100);
        int aimXEmptyStart = (width / 2) - (width / 300);
        int aimXEmptyEnd = (width / 2) + (width / 300);

        for (int x = aimXStart; x <= aimXEnd; x++) {
            for (int y = (height / 2) - 2; y <= (height / 2) + 2; y++) {
                if (x <= aimXEmptyEnd && x >= aimXEmptyStart) continue;

                pixels[x + y * width] = Color.yellow.getRGB();
            }
        }

        int aimYStart = (height / 2) - ((aimXEnd - aimXStart) / 2);
        int aimYEnd = aimYStart + (aimXEnd - aimXStart);
        int aimYEmptyStart = (height / 2) - (width / 300);
        int aimYEmptyEnd = (height / 2) + (width / 300);

        for (int y = aimYStart; y <= aimYEnd; y++) {
            for (int x = (width / 2) - 2; x <= (width / 2) + 2; x++) {
                if (y <= aimYEmptyEnd && y >= aimYEmptyStart) continue;

                pixels[x + y * width] = Color.yellow.getRGB();
            }
        }

        return pixels;
    }

    public int[] renderEnemies(int[] pixels, Vector2d pos, Vector2d dir, List<Enemy> enemies) {
        enemies.stream()
                .forEach(s -> s.distanceToCamera = Math.pow(
                        Math.abs(pos.getX() - s.xPosition),
                        Math.abs(pos.getY() - s.yPosition))
                );
        enemies.sort((s1, s2) -> (s2.distanceToCamera > s1.distanceToCamera) ? 1 : -1 );

        double angleStep = width / 1.20;
        double angle = 0.60;

        for (Enemy entity : enemies) {
            Sprite sprite = entity.getSprite();

            Vector2d enemyPos = new Vector2d(entity.xPosition, entity.yPosition);
            Vector2d rayToEnemy = enemyPos.subtract(pos);
            double angleToEnemenyLeft = dir.rotate(angle).getAngle(rayToEnemy);
            double angleToEnemenyCenter = dir.getAngle(rayToEnemy);
            boolean isVisible = angleToEnemenyCenter <= angle && angleToEnemenyLeft  <= angle * 2 ? true : false;

            if (!isVisible || !entity.isAlive) continue;

            double angles = angleStep * (angleToEnemenyLeft);
            double rayLength = Math.hypot(Math.abs(pos.getX() - enemyPos.getX()), Math.abs(pos.getY() - enemyPos.getY()));

            int enemyHeight = (rayLength == 0) ? sprite.getHeight() : (int) ((int) (sprite.getHeight() / (rayLength)));
            if (enemyHeight > sprite.getHeight()) enemyHeight = sprite.getHeight();

            int enemyWidth = (rayLength == 0) ? sprite.getWidth() : (int) ((int) (sprite.getWidth() / (rayLength)));
            if (enemyWidth > sprite.getWidth()) enemyWidth = sprite.getWidth();

            int drawYStart = (int) (-enemyHeight / 2 + height / 2);
            if (drawYStart < 0) drawYStart = 0;

            int drawYEnd = (int) (enemyHeight / 2 + height / 2);
            if (drawYEnd >= height) drawYEnd = height;

            double imgPixYSize = 1.0 * sprite.getHeight() / enemyHeight;
            int drawXStart = (int)angles - (enemyWidth / 2);
            int drawXEnd = (int) drawXStart + (enemyWidth);
            double imgPixXSize = 1.0 * sprite.getWidth() / enemyWidth;

            int texXOffset = 0;
            if (drawXStart < 0) {
                texXOffset = -drawXStart;
                drawXStart = 0;
            }

            for (int y = drawYStart; y < drawYEnd; y++) {
                int texY = (int)((y - drawYStart) * imgPixYSize);
                for (int x = drawXStart; x < drawXEnd; x++) {
                    int color = sprite.getPixelSafty((int) ((x - drawXStart + texXOffset) * imgPixXSize), texY);

                    if (this.rayLengths.get(x) == null || this.rayLengths.get(x) <= rayLength) continue;

                    if (color != 0) pixels[x + y * width] = color;
                }
            }
        }

        return pixels;
    }

    public int[] renderBullets(int[] pixels, Vector2d pos, Vector2d dir, List<Bullet> bullets) {

        double angleStep = width / 1.20;
        double angle = 0.60;

        for (Bullet entity : bullets) {
            Sprite sprite = entity.getSprite();

            Vector2d bulletPos = entity.position;
            Vector2d rayToBullet = bulletPos.subtract(pos);
            double angleToBulletLeft = dir.rotate(angle).getAngle(rayToBullet);
            double angleToBulletCenter = dir.getAngle(rayToBullet);
            boolean isVisible = angleToBulletCenter <= angle && angleToBulletLeft  <= angle * 2 ? true : false;

            if (!isVisible || entity.isHited) continue;

            double angles = angleStep * (angleToBulletLeft);
            double rayLength = Math.hypot(Math.abs(pos.getX() - bulletPos.getX()), Math.abs(pos.getY() - bulletPos.getY()));

            int enemyHeight = (rayLength == 0) ? sprite.getHeight() : (int) ((int) (sprite.getHeight() / (rayLength)));
            if (enemyHeight > sprite.getHeight()) enemyHeight = sprite.getHeight();

            int enemyWidth = (rayLength == 0) ? sprite.getWidth() : (int) ((int) (sprite.getWidth() / (rayLength)));
            if (enemyWidth > sprite.getWidth()) enemyWidth = sprite.getWidth();

            int drawYStart = (int) (-enemyHeight / 2 + height / 2);
            if (drawYStart < 0) drawYStart = 0;

            int drawYEnd = (int) (enemyHeight / 2 + height / 2);
            if (drawYEnd >= height) drawYEnd = height;

            double imgPixYSize = 1.0 * sprite.getHeight() / enemyHeight;
            int drawXStart = (int)angles - (enemyWidth / 2);
            int drawXEnd = (int) drawXStart + (enemyWidth);
            double imgPixXSize = 1.0 * sprite.getWidth() / enemyWidth;

            int texXOffset = 0;
            if (drawXStart < 0) {
                texXOffset = -drawXStart;
                drawXStart = 0;
            }

            for (int y = drawYStart; y < drawYEnd; y++) {
                int texY = (int)((y - drawYStart) * imgPixYSize);
                for (int x = drawXStart; x < drawXEnd; x++) {
                    int color = sprite.getPixelSafty((int) ((x - drawXStart + texXOffset) * imgPixXSize), texY);

                    if (this.rayLengths.get(x) == null || this.rayLengths.get(x) <= rayLength) continue;

                    if (color != 0) pixels[x + y * width] = color;
                }
            }
        }

        return pixels;
    }

    public int[] render(int[] pixels,
                        Vector2d pos,
                        Vector2d dir,
                        Vector2d plain,
                        BoomStick gun,
                        List<Enemy> enemies,
                        List<Bullet> bullets) {

        pixels = this.renderFloor(pixels, pos, dir);
        pixels = this.renderCeiling(pixels, pos, dir);
        pixels = this.renderWall(pixels, pos, dir);
        pixels = this.renderEnemies(pixels, pos, dir, enemies);
        pixels = this.renderBullets(pixels, pos, dir, bullets);
        pixels = this.renderGun(pixels, gun.getSprite());

        return pixels;
    }
}