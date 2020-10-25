package org.plain.old.retro.shooter.engine.graphics;

import org.plain.old.retro.shooter.engine.calculus.SimpleMath;
import org.plain.old.retro.shooter.engine.calculus.linear.RotationMatrix2d;
import org.plain.old.retro.shooter.engine.unit.GeneralPlayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class Camera extends GeneralPlayer {

    public class CameraPlane implements Serializable {

        private int width;

        private int height;

        public CameraPlane(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return this.width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    private double z;

    private final Map<Double, RotationMatrix2d> rotationsByAngles = new HashMap<>();

    private final Map<Integer, RotationMatrix2d> rotationsByPixels = new HashMap<>();

    private final RotationMatrix2d[] rotations;

    private final CameraPlane plain;

    private final double angle;

    {
        z = 0.0;
    }

    public Camera(double x, double y, double x2, double y2, int width, int height, double angle) {
        super(x, y, x2, y2);
        this.plain = new CameraPlane(width, height);
        this.angle = angle;

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

    public RotationMatrix2d getRotationMatrix(int index) {
        return this.rotations[SimpleMath.min(this.plain.getWidth() - 1, index)];
    }

    public RotationMatrix2d getRotationMatrix(double angle) {
        RotationMatrix2d matrix = null;
        if ((this.rotationsByAngles.get(angle)) == null) return new RotationMatrix2d(0);

        return this.rotationsByAngles.get(angle);
    }

    public CameraPlane getPlain() {
        return this.plain;
    }

    public Double getAngle() {
        return this.angle;
    }

    public void up() {
        this.z += this.z < 0.5 ? 0.1 : 0.0;
    }

    public void down() {
        this.z -= this.z > -0.5 ? 0.1 : 0.0;
    }

    public double getZ() {
        return this.z;
    }

    public double getHorizont() {
        return this.z * this.getPlain().getHeight();
    }
}
