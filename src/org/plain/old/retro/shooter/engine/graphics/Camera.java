package org.plain.old.retro.shooter.engine.graphics;

import org.plain.old.retro.shooter.engine.calculus.SimpleMath;
import org.plain.old.retro.shooter.engine.calculus.linear.RotationMatrix2d;
import org.plain.old.retro.shooter.engine.unit.GeneralPlayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Camera.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 05.07.2020 08:37 <p>
 */
public class Camera extends GeneralPlayer {

    /**
     * The type Camera plane.
     */
    public class CameraPlane implements Serializable {

        /**
         * The Width.
         */
        private int width;

        /**
         * The Height.
         */
        private int height;

        /**
         * Instantiates a new Camera plane.
         *
         * @param width  the width
         * @param height the height
         */
        public CameraPlane(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * Gets width.
         *
         * @return the width
         */
        public int getWidth() {
            return this.width;
        }

        /**
         * Sets width.
         *
         * @param width the width
         */
        public void setWidth(int width) {
            this.width = width;
        }

        /**
         * Gets height.
         *
         * @return the height
         */
        public int getHeight() {
            return this.height;
        }

        /**
         * Sets height.
         *
         * @param height the height
         */
        public void setHeight(int height) {
            this.height = height;
        }
    }

    /**
     * The Z.
     */
    private double z;

    /**
     * The Rotations by angles.
     */
    private final Map<Double, RotationMatrix2d> rotationsByAngles = new HashMap<>();

    /**
     * The Rotations by pixels.
     */
    private final Map<Integer, RotationMatrix2d> rotationsByPixels = new HashMap<>();

    /**
     * The Rotations.
     */
    private final RotationMatrix2d[] rotations;

    /**
     * The Plain.
     */
    private final CameraPlane plain;

    /**
     * The Angle.
     */
    private final double angle;

    /**
     * The Distance to plain.
     */
    private double distanceToPlain;

    {
        z = 0.0;
    }

    /**
     * Instantiates a new Camera.
     *
     * @param x      the x
     * @param y      the y
     * @param x2     the x 2
     * @param y2     the y 2
     * @param width  the width
     * @param height the height
     * @param angle  the angle
     */
    public Camera(double x, double y, double x2, double y2, int width, int height, int angle) {
        this(x, y, x2, y2, width, height, Math.toRadians(angle));
    }

    /**
     * Instantiates a new Camera.
     *
     * @param x      the x
     * @param y      the y
     * @param x2     the x 2
     * @param y2     the y 2
     * @param width  the width
     * @param height the height
     * @param angle  the angle
     */
    public Camera(double x, double y, double x2, double y2, int width, int height, double angle) {
        super(x, y, x2, y2);
        this.plain = new CameraPlane(width, height);
        this.angle = angle;
        this.distanceToPlain = (width / 2) / Math.tan(angle / 2);

        rotations = new RotationMatrix2d[plain.getWidth()];

        double angleStep = angle / plain.getWidth();
        double angleStart = angle / 2;

        this.rotationsByAngles.put(angleStart, new RotationMatrix2d(angleStart));
        this.rotationsByAngles.put(-angleStart, new RotationMatrix2d(-angleStart));

        for (int i = 0; i < plain.getWidth(); i++) {
            RotationMatrix2d rotationMatrix = new RotationMatrix2d(angleStart);
            this.rotations[i] = rotationMatrix;
            this.rotationsByAngles.put(angleStart, rotationMatrix);
            this.rotationsByPixels.put(i, rotationMatrix);
            angleStart -= angleStep;
        }
    }

    /**
     * Gets rotation matrix.
     *
     * @param index the index
     * @return the rotation matrix
     */
    public RotationMatrix2d getRotationMatrix(int index) {
        return this.rotations[SimpleMath.min(this.plain.getWidth() - 1, index)];
    }

    /**
     * Gets rotation matrix.
     *
     * @param angle the angle
     * @return the rotation matrix
     */
    public RotationMatrix2d getRotationMatrix(double angle) {
        RotationMatrix2d matrix = null;
        if ((this.rotationsByAngles.get(angle)) == null) return new RotationMatrix2d(0);

        return this.rotationsByAngles.get(angle);
    }

    /**
     * Gets plain.
     *
     * @return the plain
     */
    public CameraPlane getPlain() {
        return this.plain;
    }

    /**
     * Gets angle.
     *
     * @return the angle
     */
    public Double getAngle() {
        return this.angle;
    }

    /**
     * Up.
     */
    public void up() {
        this.z += this.z < 0.45 ? 0.25 : 0.0;
    }

    /**
     * Down.
     */
    public void down() {
        this.z -= this.z > -0.25 ? 0.25 : 0.0;
    }

    /**
     * Gets z.
     *
     * @return the z
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Gets horizont.
     *
     * @return the horizont
     */
    public double getHorizont() {
        return this.z * this.getPlain().getHeight();
    }

    /**
     * Gets distance to plain.
     *
     * @return the distance to plain
     */
    public double getDistanceToPlain() {
        return this.distanceToPlain;
    }
}
