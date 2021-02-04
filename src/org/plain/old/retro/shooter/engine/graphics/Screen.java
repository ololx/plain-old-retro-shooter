package org.plain.old.retro.shooter.engine.graphics;

import org.plain.old.retro.shooter.engine.calculus.SimpleMath;
import org.plain.old.retro.shooter.engine.calculus.linear.Matrix2d;
import org.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.unit.Enemy;
import org.plain.old.retro.shooter.engine.unit.Unit;
import org.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;
import org.plain.old.retro.shooter.engine.unit.equipment.weapon.BoomStick;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * The type Screen.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 05.07.2020 08:37 <p>
 */
public class Screen {

    private boolean showMap = false;

    /**
     * The type Ray.
     */
    public static class Ray {

        /**
         * The Position.
         */
        private final Vector2d position;

        /**
         * The Direction.
         */
        private final Vector2d direction;

        /**
         * The Lendth.
         */
        private final double lendth;

        /**
         * Instantiates a new Ray.
         *
         * @param position  the position
         * @param direction the direction
         * @param lendth    the lendth
         */
        public Ray(Vector2d position, Vector2d direction, double lendth) {
            this.position = position;
            this.direction = direction;
            this.lendth = lendth;
        }

        /**
         * Gets position.
         *
         * @return the position
         */
        public Vector2d getPosition() {
            return this.position;
        }

        /**
         * Gets direction.
         *
         * @return the direction
         */
        public Vector2d getDirection() {
            return this.direction;
        }

        /**
         * Gets length.
         *
         * @return the length
         */
        public double getLength() {
            return this.lendth;
        }
    }

    /**
     * The type Rays.
     */
    public static class Rays {

        /**
         * The Rays.
         */
        private List<Ray> rays;

        /**
         * The Max ray length.
         */
        private double maxRayLength;

        {
            this.rays = new ArrayList<>();
            this.maxRayLength = 0;
        }

        /**
         * Sets ray.
         *
         * @param index the index
         * @param ray   the ray
         */
        public void setRay(int index, Ray ray) {
            this.rays.add(index, ray);
        }

        /**
         * Gets ray.
         *
         * @param index the index
         * @return the ray
         */
        public Ray getRay(int index) {
            return this.rays.size() > index ? this.rays.get(index) : null;
        }

        /**
         * Gets max ray length.
         *
         * @return the max ray length
         */
        public double getMaxRayLength() {
            return this.maxRayLength;
        }

        /**
         * Sets max ray length.
         *
         * @param maxRayLength the max ray length
         */
        public void setMaxRayLength(double maxRayLength) {
            this.maxRayLength = maxRayLength;
        }
    }

    /**
     * The Even.
     */
    int even = 0;

    /**
     * The Flick.
     */
    boolean flick = false;
    /**
     * The Map.
     */
    private int[][] map;

    /**
     * The Width.
     */
    private int width;

    /**
     * The Height.
     */
    private int height;

    /**
     * The Textures.
     */
    private List<Sprite> textures;

    /**
     * The Rays casted.
     */
    private Rays raysCasted;

    /**
     * The Screen mask.
     */
    boolean[] screenMask;

    /**
     * Instantiates a new Screen.
     *
     * @param map          the map
     * @param width        the width
     * @param height       the height
     * @param textures     the textures
     * @param playerCamera the player camera
     */
    public Screen(int[][] map, int width, int height, List<Sprite> textures, Camera playerCamera) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.textures = textures;
        this.raysCasted = new Rays();
        this.rayCast(playerCamera);
    }

    public void switchShowMap() {
        this.showMap ^= true;
    }

    public int[] renderFloor(int[] pixels, Camera playerCamera) {
        return this.drawHorizontalSurface(
                pixels,
                playerCamera,
                this.textures.get(4),
                1,
                this.height >> 1,
                this.height
        );
    }

    /**
     * Render ceiling int [ ].
     *
     * @param pixels       the pixels
     * @param playerCamera the player camera
     * @return the int [ ]
     */
    public int[] renderCeiling(int[] pixels, Camera playerCamera) {
        return this.drawHorizontalSurface(
                pixels,
                playerCamera,
                this.textures.get(5),
                -1,
                0,
                this.height >> 1
        );
    }

    public int[] drawHorizontalSurface(int[] pixels, Camera playerCamera, Sprite texture, int pQ, int drawStart, int drawEnd) {
        for (int x = even; x < this.width - even; x+= 1 + even) {
            for (int y = drawStart; y < drawEnd; y++) {
                if (this.screenMask[x + y * width]) continue;
                else this.screenMask[x + y * width] = true;

                int p = (pQ * y) + (-pQ * (height / 2));
                double posZ = 0.5 * playerCamera.getDistanceToPlain();
                double rowDistance = posZ / p;

                Vector2d currentRayDirection = this.raysCasted.getRay(x).getDirection().clone();
                Vector2d currentRayPosition = playerCamera.getPosition()
                        .add(currentRayDirection.multiply((rowDistance / playerCamera.getRotationMatrix(x).getX1())));

                double cellX = Math.abs(((currentRayPosition.getX())) - (int)(currentRayPosition.getX()));
                double cellY = Math.abs(((currentRayPosition.getY())) - (int)(currentRayPosition.getY()));

                int tX = (int)(texture.getWidth() * cellX);
                int tY = (int)(texture.getHeight() * cellY);
                int colorFloor = texture.getPixelSafty(tX, tY, 1 / (rowDistance));

                pixels[x + y * width] = colorFloor;
            }
        }

        return pixels;
    }

    /**
     * Ray cast.
     *
     * @param playerCamera the player camera
     */
    public void rayCast(Camera playerCamera) {
        double maxRayLength = 0;
        Rays rys = new Rays();
        for (int x = even; x < width - even; x+= 1 + even) {
            Matrix2d rayRot = playerCamera.getRotationMatrix(x);
            Vector2d rayDir = playerCamera.getDirection().rotate(rayRot);
            Vector2d rayPos = playerCamera.getPosition().clone();

            boolean hit = false;
            double steps = 0.05;
            while (!hit) {
                if ((int) rayPos.getX() > map.length || (int) rayPos.getY() > map[0].length) hit = true;
                else if (map[(int) rayPos.getX()][(int) rayPos.getY()] != 0) hit = true;

                if (!hit) rayPos = rayPos.add(rayDir.getNormalized().multiply(steps));
            }

            double rayLength = (playerCamera.getPosition().subtract(rayPos).getModule() * rayRot.getX1()) + 0.001;

            rys.setRay(x, new Ray(rayPos, rayDir, rayLength));

            maxRayLength = rayLength > maxRayLength ? rayLength : maxRayLength;
        }

        rys.setMaxRayLength(maxRayLength);
        this.raysCasted = rys;
        this.raysCasted.setMaxRayLength(maxRayLength);
    }

    public int[] renderMap(int[] pixels, Camera playerCamera) {
        if (!playerCamera.isMoved()) return pixels;

        int scale = 5;
        int mapW = map.length * scale;
        int mapH = map[0].length * scale;
        int startFromTop = (height - mapH - 1) * width;

        for (int i = 1; i <= mapW; i++) {
            for (int j = 1; j <= mapH; j++) {
                pixels[startFromTop + i + j * width] = Color.BLACK.getRGB();

                if (i == 1 || i == mapW || i % scale == 0
                        || j == 1 || j == mapH || j % scale == 0) {
                    pixels[startFromTop + i + j * width] = Color.LIGHT_GRAY.getRGB();

                    if (this.screenMask[startFromTop + i + j * width]) continue;
                    else this.screenMask[startFromTop + i + j * width] = true;
                }
            }
        }

        Vector2d currentPLayerPosition = playerCamera.getPosition();
        int xPosition = (int) (scale * currentPLayerPosition.getX());
        int yPosition = (int) (scale * currentPLayerPosition.getY());
        int xInit = xPosition - (scale >> 2);
        int yInit = yPosition - (scale >> 2);
        int xEnd = xPosition + (scale >> 2);
        int yEnd = yPosition + (scale >> 2);
        for (int px = xInit; px < xEnd; px++) {
            for (int py = yInit; py < yEnd; py++) {
                this.screenMask[startFromTop + px + py * width] = true;
                pixels[startFromTop + px + py * width] = Color.WHITE.getRGB();
            }
        }

        for (int x = even; x < this.width - even; x+= 1 + even) {
            Vector2d currentRayPosition = this.raysCasted.getRay(x).getPosition();

            for (int px = scale * (int) currentRayPosition.getX(); px <= (scale * (int) currentRayPosition.getX()) + scale; px++) {
                for (int py = scale * (int) currentRayPosition.getY(); py <= (scale * (int) currentRayPosition.getY()) + scale; py++) {
                    this.screenMask[startFromTop + px + py * width] = true;
                    pixels[startFromTop + px + py * width] = Color.LIGHT_GRAY.getRGB();
                }
            }
        }

        return pixels;
    }

    /**
     * Render wall int [ ].
     *
     * @param pixels       the pixels
     * @param playerCamera the player camera
     * @return the int [ ]
     */
    public int[] renderWall(int[] pixels, Camera playerCamera) {
        for (int x = 0; x < width; x++) {
            Vector2d rayPos = this.raysCasted.getRay(x).getPosition();
            Vector2d rayDir = this.raysCasted.getRay(x).getDirection();
            double rayLength = this.raysCasted.getRay(x).getLength();

            int wallHeight = (int) ((1 / rayLength) * playerCamera.getDistanceToPlain());
            int drawStart = SimpleMath.max(
                    (int) (-(wallHeight >> 1) + (height >> 1) + playerCamera.getHorizont()),
                    0
            );
            int drawEnd = SimpleMath.min(
                    (int) ((wallHeight >> 1) + (height >> 1) + playerCamera.getHorizont()),
                    height
            );

            int texNum = SimpleMath.max(map[(int) rayPos.getX()][(int) rayPos.getY()] - 1, 0);

            double intersectionX = playerCamera.getPosition().getX() <= rayPos.getX()
                    ? Math.abs(((rayPos.getX())) - (int) rayPos.getX())
                    : 1 - Math.abs(((rayPos.getX())) - (int) rayPos.getX());
            double intersectionY = playerCamera.getPosition().getY() <= rayPos.getY()
                    ? Math.abs(((rayPos.getY())) - (int) rayPos.getY())
                    : 1 - Math.abs(((rayPos.getY())) - (int) rayPos.getY());
            boolean horizontal = intersectionX  <= intersectionY ? true : false;

            double wallX = horizontal ? intersectionY : intersectionX;
            wallX -= Math.floor(wallX);
            int tX = (int)(wallX * (textures.get(texNum).getWidth()));
            if (!horizontal && rayDir.getX() > 0) tX = (textures.get(texNum).getWidth()) - tX - 1;
            if (horizontal && rayDir.getY() < 0) tX = (textures.get(texNum).getWidth()) - tX - 1;

            double imgPixYSize = 1.0 * textures.get(texNum).getHeight() / wallHeight;
            int imgPixYStart = height >= wallHeight ? 0 : (int) ((((wallHeight >> 1)) - (height >> 1)) * imgPixYSize);

            for (int y = drawStart + even; y < drawEnd - even; y+= 1 + even) {
                if (this.screenMask[x + y * width]) continue;
                else this.screenMask[x + y * width] = true;

                int tY = imgPixYStart + (int)(((y - drawStart) * imgPixYSize));
                int color = textures.get(texNum).getPixelSafty(tX, tY, 1 / rayLength);
                pixels[x + y * width] = color;
            }
        }

        return pixels;
    }

    /**
     * Render gun int [ ].
     *
     * @param pixels the pixels
     * @param gun    the gun
     * @return the int [ ]
     */
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

    /**
     * Render unit int [ ].
     *
     * @param pixels       the pixels
     * @param playerCamera the player camera
     * @param units        the units
     * @return the int [ ]
     */
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
            boolean isVisible = angleToUnitCenter <= angle ? true : false;

            if (!isVisible || !unit.isExist()) continue;

            double angles = angleStep * (angleToUnitLeft);
            double rayLength = unit.getDistanceToCurrentObject();

            int unitHeight = (int) ((sprite.getWidth() / rayLength) * playerCamera.getDistanceToPlain());

            //int unitHeight = (int) (((sprite.getHeight() / 100) / rayLength) * playerCamera.getDistanceToPlain());
            unitHeight = SimpleMath.min(
                    sprite.getHeight(),
                    (int) (sprite.getHeight() / rayLength)
            );

            int unitWidth = SimpleMath.min(
                    sprite.getWidth(),
                    (int) (sprite.getWidth() / rayLength)
            );

            int drawYStart = 0;
            int drawYEnd = 0;
            if (unit.getAligement().equals(Unit.ALIGNEMENT.CENTER)) {
                drawYStart = SimpleMath.max(
                        (int) (-(unitHeight >> 1) + (height >> 1) + playerCamera.getHorizont()),
                        0
                );
                drawYEnd = SimpleMath.min(
                        (int) ((unitHeight >> 1) + (height >> 1) + playerCamera.getHorizont()),
                        height
                );
            } else if (unit.getAligement().equals(Unit.ALIGNEMENT.BOTTOM)) {
                int wallHeight = (int) (height / rayLength);
                int drawEnd = SimpleMath.min(
                        (int) ((wallHeight >> 1) + (height >> 1) + playerCamera.getHorizont()),
                        height
                );
                drawYStart = SimpleMath.max(
                        drawEnd - unitHeight,
                        0
                );
                drawYEnd = SimpleMath.min(
                        drawEnd,
                        height
                );
            }

            double imgPixYSize = 1.0 * sprite.getHeight() / unitHeight;
            int drawXStart = (int)angles - (unitWidth >> 1);
            int drawXEnd = (int) drawXStart + (unitWidth);
            double imgPixXSize = 1.0 * sprite.getWidth() / unitWidth;

            int tXOffset = 0;
            if (drawXStart < 0) {
                tXOffset = - drawXStart;
                drawXStart = 0;
            }

            for (int y = drawYStart + even; y < drawYEnd - even; y+= 1 + even) {
                int tY = (int)((y - drawYStart) * imgPixYSize);
                for (int x = drawXStart + even; x < drawXEnd - even; x+= 1 + even) {
                    if (this.raysCasted.getRay(x) == null
                            || (this.raysCasted.getRay(x).getLength()
                            < (rayLength * playerCamera.getRotationMatrix(x).getX1()))
                    ) continue;
                    int color = sprite.getPixelSafty((int) ((x - drawXStart + tXOffset) * imgPixXSize), tY, 1 / rayLength);

                    if (color != 0) {
                        pixels[x + y * width] = color;
                        this.screenMask[x + y * width] = true;
                    }
                }
            }
        }

        return pixels;
    }

    /**
     * Render int [ ].
     *
     * @param pixels       the pixels
     * @param playerCamera the player camera
     * @param gun          the gun
     * @param enemies      the enemies
     * @param bullets      the bullets
     * @param players      the players
     * @param flicker      the flicker
     * @return the int [ ]
     */
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

    /**
     * Render int [ ].
     *
     * @param pixels       the pixels
     * @param playerCamera the player camera
     * @param gun          the gun
     * @param enemies      the enemies
     * @param bullets      the bullets
     * @param players      the players
     * @return the int [ ]
     */
    public int[] render(int[] pixels,
                        Camera playerCamera,
                        BoomStick gun,
                        ConcurrentSkipListSet<Enemy> enemies,
                        ConcurrentSkipListSet<Bullet> bullets,
                        Collection<Unit> players) {
        //this.rayCast(playerCamera);

        this.screenMask = new boolean[pixels.length];
        pixels = this.renderUnit(
                pixels,
                playerCamera,
                new ArrayList<>(){{
                    addAll(enemies);
                    addAll(bullets);
                    addAll(players);
                }}
        );
        pixels = this.renderWall(pixels, playerCamera);
        pixels = this.renderFloor(pixels, playerCamera);
        pixels = this.renderCeiling(pixels, playerCamera);
        pixels = this.renderGun(pixels, gun.getSprite());
        if (this.showMap) pixels = this.renderMap(pixels, playerCamera);

        else playerCamera.update();
        return pixels;
    }

    /**
     * Click flick.
     */
    public void clickFlick() {
        this.flick ^= true;
    }
}