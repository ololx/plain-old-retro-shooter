package io.github.ololx.plain.old.retro.shooter.engine.unit;

import io.github.ololx.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Sprite;
import io.github.ololx.plain.old.retro.shooter.engine.unit.components.DamageLogic;
import io.github.ololx.plain.old.retro.shooter.engine.unit.components.GameObject;
import io.github.ololx.plain.old.retro.shooter.engine.unit.components.Health;
import io.github.ololx.plain.old.retro.shooter.engine.unit.components.UnitHealth;

import java.util.UUID;

import static io.github.ololx.plain.old.retro.shooter.engine.unit.Unit.ALIGNEMENT.BOTTOM;

/**
 * The type Abstract unit.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 08.08.2020 17:53 <p>
 */
public abstract class AbstractUnit implements Unit, RegisterEntity, Comparable<RegisterEntity>  {

    /**
     * The Aligement.
     */
    protected ALIGNEMENT aligement = BOTTOM;

    /**
     * The Position.
     */
    protected Vector2d position;

    /**
     * The Radius.
     */
    protected double radius;

    /**
     * The Distance to object.
     */
    protected double distanceToObject;

    /**
     * The Is exist.
     */
    protected boolean isExist = true;

    /**
     * The Texture.
     */
    protected Sprite texture;

    /**
     * The Uid.
     */
    private final UUID uid = UUID.randomUUID();

    /**
     * Instantiates a new Abstract unit.
     *
     * @param x       the x
     * @param y       the y
     * @param radius  the radius
     * @param texture the texture
     */
    public AbstractUnit(double x, double y, double radius, Sprite texture) {
        this.position = new Vector2d(x, y);
        this.texture = texture;
        this.radius = radius;
    }

    /**
     * Gets aligement.
     *
     * @return the aligement
     */
    @Override
    public ALIGNEMENT getAlignment() {
        return this.aligement;
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
     * Gets sprite.
     *
     * @return the sprite
     */
    public Sprite getSprite() {
        return texture;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * Is exist boolean.
     *
     * @return the boolean
     */
    public boolean isExist() {
        return this.isExist;
    }

    /**
     * Destroy.
     */
    public void destroy() {
        this.isExist = false;
    }

    /**
     * Calculate distance to current object double.
     *
     * @param otherObjectPosition the other object position
     * @return the double
     */
    public double calculateDistanceToCurrentObject(Vector2d otherObjectPosition) {
        this.distanceToObject = Math.hypot(
                Math.abs(otherObjectPosition.getX() - this.position.getX()),
                Math.abs(otherObjectPosition.getY() - this.position.getY())
        ) + 0.0001;

        return this.getDistanceToCurrentObject();
    }

    /**
     * Gets distance to current object.
     *
     * @return the distance to current object
     */
    public double getDistanceToCurrentObject() {
        return this.distanceToObject;
    }

    /**
     * Sets position.
     *
     * @param x the x
     * @param y the y
     */
    public void setPosition(double x, double y) {
        this.position = new Vector2d(x, y);
    }

    /**
     * Gets uid.
     *
     * @return the uid
     */
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
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it                              from being compared to this object.
     */
    @Override
    public int compareTo(RegisterEntity obj) {
        return this.getUid().compareTo(obj.getUid());
    }
}
