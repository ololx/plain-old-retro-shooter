package org.plain.old.retro.shooter.engine.unit;

import org.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.graphics.Sprite;

import java.util.UUID;

import static org.plain.old.retro.shooter.engine.unit.Unit.ALIGNEMENT.BOTTOM;

/**
 * @project plain-old-retro-shooter
 * @created 08.08.2020 17:53
 * <p>
 * @author Alexander A. Kropotin
 */
public class AbstractUnit implements Unit, RegisterEntity, Comparable<RegisterEntity>  {

    protected ALIGNEMENT aligement = BOTTOM;

    protected Vector2d position;

    protected double radius;

    protected double distanceToObject;

    protected boolean isExist = true;

    protected Sprite texture;

    private final UUID uid = UUID.randomUUID();

    public AbstractUnit(double x, double y, double radius, Sprite texture) {
        this.position = new Vector2d(x, y);
        this.texture = texture;
        this.radius = radius;
    }

    @Override
    public ALIGNEMENT getAligement() {
        return this.aligement;
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

    public void setPosition(double x, double y) {
        this.position = new Vector2d(x, y);
    }

    @Override
    public UUID getUid() {
        return this.uid;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param obj the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(RegisterEntity obj) {
        return this.getUid().compareTo(obj.getUid());
    }
}
