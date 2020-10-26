package org.plain.old.retro.shooter.engine.graphics;

import org.plain.old.retro.shooter.engine.calculus.SimpleMath;
import org.plain.old.retro.shooter.engine.calculus.linear.Matrix2d;
import org.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.unit.Enemy;
import org.plain.old.retro.shooter.engine.unit.Unit;
import org.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;
import org.plain.old.retro.shooter.engine.unit.equipment.weapon.BoomStick;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class Screen {

    public static class Ray {

        private final Vector2d position;

        private final Vector2d direction;

        private final double lendth;

        public Ray(Vector2d position, Vector2d direction, double lendth) {
            this.position = position;
            this.direction = direction;
            this.lendth = lendth;
        }

        public Vector2d getPosition() {
            return this.position;
        }

        public Vector2d getDirection() {
            return this.direction;
        }

        public double getLength() {
            return this.lendth;
        }
    }

    public static class Rays {

        private List<Ray> rays;

        private double maxRayLength;

        {
            this.rays = new ArrayList<>();
            this.maxRayLength = 0;
        }

        public void setRay(int index, Ray ray) {
            this.rays.add(index, ray);
        }

        public Ray getRay(int index) {
            return this.rays.size() > index ? this.rays.get(index) : null;
        }

        public double getMaxRayLength() {
            return this.maxRayLength;
        }

        public void setMaxRayLength(double maxRayLength) {
            this.maxRayLength = maxRayLength;
        }
    }

    int even = 0;

    boolean flick = false;
    private int[][] map;

    private int width;

    private int height;

    private List<Sprite> textures;

    private Map<Integer, Double> rayLengths = new HashMap<>();

    private Rays raysCasted;

    boolean[] screenMask;

    public Screen(int[][] map, int width, int height, List<Sprite> textures, Camera playerCamera) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.textures = textures;
        this.raysCasted = new Rays();
        this.rayCast(playerCamera);
    }

    public int[] renderFloor(int[] pixels, Camera playerCamera) {
        int floorTexture = 5;
        Vector2d rayDirLeft = this.raysCasted.getRay(0).getDirection();//playerCamera.getDirection().rotate(playerCamera.getRotationMatrix(angle));
        Vector2d rayDirRight = this.raysCasted.getRay(width - 1).getDirection();//playerCamera.getDirection().rotate(playerCamera.getRotationMatrix(-angle));

        int drawStart = (int) (((this.height) >> 1) + playerCamera.getHorizont());
        int drawEnd = this.height;
        for (int y = drawStart; y < drawEnd; y++) {
            double posZ = drawStart;
            int p = (int) y - drawStart;
            double rowDistance = posZ / p;
            double floorStepX = rowDistance * (rayDirRight.getX() - rayDirLeft.getX()) / this.width;
            double floorStepY = rowDistance * (rayDirRight.getY() - rayDirLeft.getY()) / this.width;
            double floorX = playerCamera.getPosition().getX() + rowDistance * rayDirLeft.getX();
            double floorY = playerCamera.getPosition().getY() + rowDistance * rayDirLeft.getY();

            for (int x = even; x < this.width - even; x+= 1 + even) {
                double cellX = Math.abs(floorX - (int)(floorX));
                double cellY = Math.abs(floorY - (int)(floorY));
                floorX += floorStepX;
                floorY += floorStepY;

                if (this.screenMask[x + y * width]) continue;
                else this.screenMask[x + y * width] = true;

                int tx = (int)(textures.get(floorTexture).getWidth() * cellX);
                int ty = (int)(textures.get(floorTexture).getHeight() * cellY);
                int colorFloor = textures.get(floorTexture).getPixelSafty(tx, ty);

                pixels[x + y * width] = colorFloor;
            }
        }

        return pixels;
    }

    public int[] renderCeiling(int[] pixels, Camera playerCamera) {
        int ceilingTexture = 4;
        Vector2d rayDirLeft = this.raysCasted.getRay(0).getDirection();//playerCamera.getDirection().rotate(playerCamera.getRotationMatrix(angle));
        Vector2d rayDirRight = this.raysCasted.getRay(width - 1).getDirection();//playerCamera.getDirection().rotate(playerCamera.getRotationMatrix(-angle));

        int drawStart = 0;
        int drawEnd = (int) (((this.height) >> 1) + playerCamera.getHorizont());
        for (int y = drawStart; y < drawEnd; y++) {
            double posZ = drawEnd;
            int p = (int) drawEnd - y;
            double rowDistance = posZ / p;
            double ceilingStepX = rowDistance * (rayDirRight.getX() - rayDirLeft.getX()) / this.width;
            double ceilingStepY = rowDistance * (rayDirRight.getY() - rayDirLeft.getY()) / this.width;

            double ceilingX = playerCamera.getPosition().getX() + rowDistance * rayDirLeft.getX();
            double ceilingY = playerCamera.getPosition().getY() + rowDistance * rayDirLeft.getY();

            for (int x = even; x < this.width - even; x+= 1 + even) {
                double cellX = Math.abs(ceilingX - (int)(ceilingX));
                double cellY = Math.abs(ceilingY - (int)(ceilingY));
                ceilingX += ceilingStepX;
                ceilingY += ceilingStepY;

                if (this.screenMask[x + y * width]) continue;
                else this.screenMask[x + y * width] = true;

                int tx = (int)(textures.get(ceilingTexture).getWidth() * cellX);
                int ty = (int)(textures.get(ceilingTexture).getHeight() * cellY);
                int colorCeiling = textures.get(ceilingTexture).getPixelSafty(tx, ty);

                pixels[x + y * width] = colorCeiling;
            }
        }

        return pixels;
    }

    public void rayCast(Camera playerCamera) {
        double maxRayLEngth = 0;
        Rays rys = new Rays();
        for (int x = even; x < width - even; x+= 1 + even) {
            Matrix2d rayRot = playerCamera.getRotationMatrix(x);
            Vector2d rayDir = playerCamera.getDirection().rotate(rayRot);
            Vector2d rayPos = playerCamera.getPosition().clone();

            boolean hit = false;
            double steps = 0.01;
            while (!hit) {
                if ((int) rayPos.getX() > map.length || (int) rayPos.getY() > map[0].length) hit = true;
                else if (map[(int) rayPos.getX()][(int) rayPos.getY()] != 0) hit = true;

                rayPos = rayPos.add(rayDir.multiply(steps));
            }

            double rayLength = Math.hypot(
                    rayPos.getX() - playerCamera.getPosition().getX(),
                    rayPos.getY() - playerCamera.getPosition().getY()
            );

            rys.setRay(x, new Ray(rayPos, rayDir, rayLength));

            maxRayLEngth = rayLength > maxRayLEngth ? rayLength : maxRayLEngth;
        }
        rys.setMaxRayLength(maxRayLEngth);
        this.raysCasted = rys;
        this.raysCasted.setMaxRayLength(maxRayLEngth);
    }

    public int[] renderWall(int[] pixels, Camera playerCamera) {
        for (int x = even; x < width - even; x+= 1 + even) {
            Matrix2d rayRot = playerCamera.getRotationMatrix(x);
            Vector2d rayPos = this.raysCasted.getRay(x).getPosition();
            Vector2d rayDir = this.raysCasted.getRay(x).getDirection();
            double rayLength = this.raysCasted.getRay(x).getLength();

            int wallHeight = (rayLength == 0) ? height : (int) ((height / (rayLength * rayRot.getX1())));

            int drawStart = (int) (-(wallHeight >> 1) + (height >> 1) + playerCamera.getHorizont());
            if (drawStart < 0) drawStart = 0;

            int drawEnd = (int) ((wallHeight >> 1) + (height >> 1) + playerCamera.getHorizont());
            if (drawEnd >= height) drawEnd = height;

            int texNum = SimpleMath.max(map[(int) rayPos.getX()][(int) rayPos.getY()] - 1, 0);

            double intersectionX = playerCamera.getPosition().getX() <= rayPos.getX()
                    ? Math.abs(rayPos.getX() - (int) rayPos.getX())
                    : 1 - Math.abs(rayPos.getX() - (int) rayPos.getX());
            double intersectionY = playerCamera.getPosition().getY() <= rayPos.getY()
                    ? Math.abs(rayPos.getY() - (int) rayPos.getY())
                    : 1 - Math.abs(rayPos.getY() - (int) rayPos.getY());
            boolean horizontal = intersectionX * 0.001 < intersectionY * 0.001 ? true : false;

            double wallX = horizontal ? intersectionY : intersectionX;
            wallX -= Math.floor(wallX);
            int texX = (int)(wallX * (textures.get(texNum).getWidth()));
            if (!horizontal && rayDir.getX() > 0) texX = (textures.get(texNum).getWidth()) - texX - 1;
            if (horizontal && rayDir.getY() < 0) texX = (textures.get(texNum).getWidth()) - texX - 1;

            double imgPixYSize = 1.0 * textures.get(texNum).getHeight() / wallHeight;
            int imgPixYStart = height >= wallHeight ? 0 : (int) ((((wallHeight >> 1)) - (height >> 1)) * imgPixYSize);

            for (int y = drawStart + even; y < drawEnd - even; y+= 1 + even) {
                if (this.screenMask[x + y * width]) continue;
                else this.screenMask[x + y * width] = true;

                int texY = imgPixYStart + (int)(((y - drawStart) * imgPixYSize));
                int color = textures.get(texNum).getPixelSafty(texX, texY);
                pixels[x + y * width] = color;
            }
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

        for (int x = drawXStart + even; x < drawXEnd - even; x+= 1 + even) {
            for (int y = drawYStart + even; y < drawYEnd - even; y+= 1 + even) {
                int pixelColor = gun.getPixelSafty(
                        (int) ((x - drawXStart) * imgPixXSize),
                        (int) ((y - drawYStart) * imgPixYSize)
                );
                if (pixelColor != 0) pixels[x + y * width] = pixelColor;
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

            int drawYStart = (int) (-(unitHeight >> 1) + (height >> 1) + playerCamera.getHorizont());
            if (drawYStart < 0) drawYStart = 0;

            int drawYEnd = (int) ((unitHeight >> 1) + (height >> 1) + playerCamera.getHorizont());
            if (drawYEnd >= height) drawYEnd = height;

            double imgPixYSize = 1.0 * sprite.getHeight() / unitHeight;
            int drawXStart = (int)angles - (unitWidth >> 1);
            int drawXEnd = (int) drawXStart + (unitWidth);
            double imgPixXSize = 1.0 * sprite.getWidth() / unitWidth;

            int texXOffset = 0;
            if (drawXStart < 0) {
                texXOffset = -drawXStart;
                drawXStart = 0;
            }

            for (int y = drawYStart + even; y < drawYEnd - even; y+= 1 + even) {
                int texY = (int)((y - drawYStart) * imgPixYSize);
                for (int x = drawXStart + even; x < drawXEnd - even; x+= 1 + even) {
                    int color = sprite.getPixelSafty((int) ((x - drawXStart + texXOffset) * imgPixXSize), texY);

                    if (this.raysCasted.getRay(x) == null || this.raysCasted.getRay(x).getLength() <= rayLength) continue;

                    if (color != 0) pixels[x + y * width] = color;
                }
            }
        }

        return pixels;
    }

    public int[] render(int[] pixels,
                        Camera playerCamera,
                        BoomStick gun,
                        ConcurrentSkipListSet<Enemy> enemies,
                        ConcurrentSkipListSet<Bullet> bullets,
                        Collection<Unit> players,
                        int flicker) {
        this.even ^= 1;
        return this.render(
                pixels,
                playerCamera,
                gun,
                enemies,
                bullets,
                players
        );
    }

    public int[] render(int[] pixels,
                        Camera playerCamera,
                        BoomStick gun,
                        ConcurrentSkipListSet<Enemy> enemies,
                        ConcurrentSkipListSet<Bullet> bullets,
                        Collection<Unit> players) {
        this.screenMask = new boolean[pixels.length];
        if (this.flick) this.even ^= 1;

        pixels = this.renderWall(pixels, playerCamera);
        pixels = this.renderFloor(pixels, playerCamera);
        pixels = this.renderCeiling(pixels, playerCamera);
        pixels = this.renderUnit(
                pixels,
                playerCamera,
                    new ArrayList<>(){{
                        addAll(enemies);
                        addAll(bullets);
                        addAll(players);
                    }}
                );
        pixels = this.renderGun(pixels, gun.getSprite());

        return pixels;
    }

    public void clickFlick() {
        this.flick ^= true;
    }
}