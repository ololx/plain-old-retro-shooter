package org.plain.old.retro.shooter.engine.graphics;

import org.plain.old.retro.shooter.engine.linear.Matrix2d;
import org.plain.old.retro.shooter.engine.linear.Vector2d;
import org.plain.old.retro.shooter.engine.unit.Enemy;
import org.plain.old.retro.shooter.engine.unit.Player;
import org.plain.old.retro.shooter.engine.unit.Unit;
import org.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;
import org.plain.old.retro.shooter.engine.unit.equipment.weapon.BoomStick;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

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

    public int[] renderFloor(int[] pixels, Camera playerCamera) {
        int floorTexture = 5;
        double angle = playerCamera.getAngle() / 2;
        Vector2d rayDirLeft = playerCamera.getDirection().rotate(playerCamera.getRotationMatrix(angle));
        Vector2d rayDirRight = playerCamera.getDirection().rotate(playerCamera.getRotationMatrix(-angle));

        int drawStart = ((this.height) >> 1);
        int drawEnd = this.height;
        for (int y = drawStart; y < drawEnd; y++) {
            int p = y - ((this.height) >> 1);
            double posZ = 0.5 * this.height;
            double rowDistance = posZ / p;
            double floorStepX = rowDistance * (rayDirRight.getX() - rayDirLeft.getX()) / this.width;
            double floorStepY = rowDistance * (rayDirRight.getY() - rayDirLeft.getY()) / this.width;
            double floorX = playerCamera.getPosition().getX() + rowDistance * rayDirLeft.getX();
            double floorY = playerCamera.getPosition().getY() + rowDistance * rayDirLeft.getY();

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

    public int[] renderCeiling(int[] pixels, Camera playerCamera) {
        int ceilingTexture = 4;
        double angle = playerCamera.getAngle() / 2;
        Vector2d rayDirLeft = playerCamera.getDirection().rotate(playerCamera.getRotationMatrix(angle));
        Vector2d rayDirRight = playerCamera.getDirection().rotate(playerCamera.getRotationMatrix(-angle));
        int drawStart = 0;
        int drawEnd = ((this.height) >> 1);

        for (int y = drawStart; y < drawEnd; y++) {
            int p = ((this.height) >> 1) - y;
            double posZ = 0.5 * this.height;
            double rowDistance = posZ / p;
            double ceilingStepX = rowDistance * (rayDirRight.getX() - rayDirLeft.getX()) / this.width;
            double ceilingStepY = rowDistance * (rayDirRight.getY() - rayDirLeft.getY()) / this.width;

            double ceilingX = playerCamera.getPosition().getX() + rowDistance * rayDirLeft.getX();
            double ceilingY = playerCamera.getPosition().getY() + rowDistance * rayDirLeft.getY();

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

    public int[] renderWall(int[] pixels, Camera playerCamera) {
        for (int x = 0; x < width; x++) {
            Matrix2d rayRot = playerCamera.getRotationMatrix(x);
            Vector2d rayDir = playerCamera.getDirection().rotate(rayRot);
            Vector2d rayPos = playerCamera.getPosition().clone();

            boolean hit = false;
            double steps = 0.01;
            while (!hit) {
                rayPos = rayPos.add(rayDir.multiply(steps));

                if (map[(int) rayPos.getX()][(int) rayPos.getY()] != 0) hit = true;

            }

            double rayLength = Math.hypot(rayPos.getX() - playerCamera.getPosition().getX(), rayPos.getY() - playerCamera.getPosition().getY());
            int wallHeight = (rayLength == 0) ? height : (int) ((int) (height / (rayLength * rayRot.getX1())));

            int drawStart = (int) (-wallHeight / 2 + (height >> 1));
            if (drawStart < 0) drawStart = 0;

            int drawEnd = (int) (wallHeight / 2 + (height >> 1));
            if (drawEnd >= height) drawEnd = height;

            int texNum = map[(int) rayPos.getX()][(int) rayPos.getY()] - 1;

            double intersectionX = playerCamera.getPosition().getX() < rayPos.getX()
                    ? Math.abs(rayPos.getX() - (int) rayPos.getX())
                    : 1 - Math.abs(rayPos.getX() - (int) rayPos.getX());
            double intersectionY = playerCamera.getPosition().getY() < rayPos.getY()
                    ? Math.abs(rayPos.getY() - (int) rayPos.getY())
                    : 1 - Math.abs(rayPos.getY() - (int) rayPos.getY());
            boolean horizontal = intersectionX <= intersectionY ? true : false;

            double wallX = horizontal ? intersectionY : intersectionX;
            wallX -= Math.floor(wallX);
            int texX = (int)(wallX * (textures.get(texNum).getWidth()));
            if (!horizontal && rayDir.getX() > 0) texX = (textures.get(texNum).getWidth()) - texX - 1;
            if (horizontal && rayDir.getY() < 0) texX = (textures.get(texNum).getWidth()) - texX - 1;

            double imgPixYSize = 1.0 * textures.get(texNum).getHeight() / wallHeight;
            int imgPixYStart = height >= wallHeight ? 0 : (int) (((wallHeight / 2) - (height >> 1)) * imgPixYSize);

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

        int gunWidth = (width >> 2);
        int drawXStart = (width >> 1) - (gunWidth >> 1);
        int drawXEnd = drawXStart + gunWidth;

        int gunHeight = (height >> 1);
        int drawYStart = (height - gunHeight);
        int drawYEnd = drawYStart + gunHeight;

        double imgPixXSize = 1.0 * gun.getWidth() / gunWidth;
        double imgPixYSize = 1.0 * gun.getHeight() / gunHeight;

        for (int x = drawXStart; x < drawXEnd; x++) {
            for (int y = drawYStart; y < drawYEnd; y++) {
                int pixelColor = gun.getPixelSafty(
                        (int) ((x - drawXStart) * imgPixXSize),
                        (int) ((y - drawYStart) * imgPixYSize)
                );
                if (pixelColor != 0) pixels[x + y * width] = pixelColor;
            }
        }

        int aimXStart = (width >> 1) - ((width / 25) >> 2);
        int aimXEnd = (width >> 1) + ((width / 25) >> 2);
        int aimXEmptyStart = (width >> 1) - ((width / 75) >> 2);
        int aimXEmptyEnd = (width >> 1) + ((width / 75) >> 2);

        for (int x = aimXStart; x <= aimXEnd; x++) {
            for (int y = (height >> 1) - 2; y <= (height >> 1) + 2; y++) {
                if (x <= aimXEmptyEnd && x >= aimXEmptyStart) continue;

                pixels[x + y * width] = Color.yellow.getRGB();
            }
        }

        int aimYStart = (height >> 1) - ((aimXEnd - aimXStart) / 2);
        int aimYEnd = aimYStart + (aimXEnd - aimXStart);
        int aimYEmptyStart = (height >> 1) - ((width / 75) >> 2);
        int aimYEmptyEnd = (height >> 1) + ((width / 75) >> 2);

        for (int y = aimYStart; y <= aimYEnd; y++) {
            for (int x = (width >> 1) - 2; x <= (width >> 1) + 2; x++) {
                if (y <= aimYEmptyEnd && y >= aimYEmptyStart) continue;

                pixels[x + y * width] = Color.yellow.getRGB();
            }
        }

        return pixels;
    }

    public int[] renderUnit(int[] pixels, Camera playerCamera, List<Unit> units) {
        units = units.stream()
                .map(s -> {
                    s.calculateDistanceToCurrentObject(playerCamera.getPosition());
                    return s;
                })
                .sorted((s1, s2) -> (s2.getDistanceToCurrentObject() > s1.getDistanceToCurrentObject()) ? 1 : -1 )
                .collect(Collectors.toList());

        double angleStep = playerCamera.getPlain().getWidth() / playerCamera.getAngle();
        double angle = playerCamera.getAngle() / 2;

        for (Unit unit : units) {
            Sprite sprite = unit.getSprite();
            Vector2d unitPos = unit.getPosition();
            Vector2d rayToUnit = unitPos.subtract(playerCamera.getPosition());
            double angleToUnitLeft = playerCamera.getDirection()
                    .rotate(playerCamera.getRotationMatrix(0))
                    .getAngle(rayToUnit);
            double angleToUnitCenter = playerCamera.getDirection().getAngle(rayToUnit);
            boolean isVisible = angleToUnitCenter <= angle && angleToUnitLeft  <= angle * 2 ? true : false;

            if (!isVisible || !unit.isExist()) continue;

            double angles = angleStep * (angleToUnitLeft);
            double rayLength = unit.getDistanceToCurrentObject();

            int unitHeight = (rayLength == 0) ? sprite.getHeight() : (int) ((int) (sprite.getHeight() / (rayLength)));
            if (unitHeight > sprite.getHeight()) unitHeight = sprite.getHeight();

            int unitWidth = (rayLength == 0) ? sprite.getWidth() : (int) ((int) (sprite.getWidth() / (rayLength)));
            if (unitWidth > sprite.getWidth()) unitWidth = sprite.getWidth();

            int drawYStart = (int) (-unitHeight / 2 + (height >> 1));
            if (drawYStart < 0) drawYStart = 0;

            int drawYEnd = (int) (unitHeight / 2 + (height >> 1));
            if (drawYEnd >= height) drawYEnd = height;

            double imgPixYSize = 1.0 * sprite.getHeight() / unitHeight;
            int drawXStart = (int)angles - (unitWidth / 2);
            int drawXEnd = (int) drawXStart + (unitWidth);
            double imgPixXSize = 1.0 * sprite.getWidth() / unitWidth;

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
                        Camera playerCamera,
                        BoomStick gun,
                        Vector<Enemy> enemies,
                        Vector<Bullet> bullets,
                        Vector<Player> players) {
        Vector<Unit> units = new Vector<>();
        units.addAll(enemies);
        units.addAll(bullets);
        units.addAll(players);

        pixels = this.renderFloor(pixels, playerCamera);
        pixels = this.renderCeiling(pixels, playerCamera);
        pixels = this.renderWall(pixels, playerCamera);
        pixels = this.renderUnit(pixels, playerCamera, units);
        pixels = this.renderGun(pixels, gun.getSprite());

        return pixels;
    }
}