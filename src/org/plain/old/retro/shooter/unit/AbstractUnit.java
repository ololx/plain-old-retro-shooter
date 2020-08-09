package org.plain.old.retro.shooter.unit;

import org.plain.old.retro.shooter.engine.graphics.Sprite;
import org.plain.old.retro.shooter.engine.linear.Vector2d;

/**
 * @project plain-old-retro-shooter
 * @created 08.08.2020 17:53
 * <p>
 * @author Alexander A. Kropotin
 */
public class AbstractUnit implements Unit {

    protected Vector2d position;

    protected double radius;

    protected double distanceToObject;

    protected boolean isExist = true;

    protected Sprite texture;

    public AbstractUnit(double x, double y, double radius, Sprite texture) {
        this.position = new Vector2d(x, y);
        this.texture = texture;
        this.radius = radius;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public Sprite getSprite() {
        return texture;
    }

    public double getRadius() {
        return this.radius;
    }

    public boolean isExist() {
        return this.isExist;
    }

    public void destroy() {
        this.isExist = false;
    }

    public double calculateDistanceToCurrentObject(Vector2d otherObjectPosition) {
        this.distanceToObject = Math.hypot(
                Math.abs(otherObjectPosition.getX() - this.position.getX()),
                Math.abs(otherObjectPosition.getY() - this.position.getY())
        );

        return this.getDistanceToCurrentObject();
    }

    public double getDistanceToCurrentObject() {
        return this.distanceToObject;
    }
}
