package org.plain.old.retro.shooter.engine.graphics;

import org.plain.old.retro.shooter.engine.linear.RotationMatrix2d;
import org.plain.old.retro.shooter.unit.GeneralPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class Camera extends GeneralPlayer {

    public class CameraPlane {

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

    private final Map<Double, RotationMatrix2d> rotationsByAngles = new HashMap<>();

    private final Map<Integer, RotationMatrix2d> rotationsByPixels = new HashMap<>();

    private final CameraPlane plain;

    private final double angle;

    public Camera(double x, double y, double x2, double y2, int width, int height, double angle) {
        super(x, y, x2, y2);
        this.plain = new CameraPlane(width, height);
        this.angle = angle;

        double angleStep = angle / plain.getWidth();
        double angleStart = angle / 2;
        for (int i = 0; i < plain.getWidth(); i++) {
            RotationMatrix2d rotationMatrix = new RotationMatrix2d(angleStart);
            this.rotationsByAngles.put(angleStart, rotationMatrix);
            this.rotationsByPixels.put(i, rotationMatrix);

            angleStart -= angleStep;
        }
    }

    public RotationMatrix2d getRotationMatrix(int pixel) {
        if (!this.rotationsByPixels.containsKey(pixel)) return new RotationMatrix2d(0);

        return this.rotationsByPixels.get(pixel);
    }

    public RotationMatrix2d getRotationMatrix(double angle) {
        if (!this.rotationsByPixels.containsKey(angle)) return new RotationMatrix2d(0);

        return this.rotationsByAngles.get(angle);
    }

    public CameraPlane getPlain() {
        return this.plain;
    }

    public Double getAngle() {
        return this.angle;
    }
}
