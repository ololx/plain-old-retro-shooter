package io.github.ololx.plain.old.retro.shooter.engine.unit;

import io.github.ololx.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Sprite;

import java.io.Serializable;

/**
 * The interface Unit.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 08.08.2020 17:52 <p>
 */
public interface Unit extends Serializable {

    /**
     * The enum Alignement.
     */
    enum ALIGNEMENT {
        /**
         * Top alignement.
         */
        TOP,
        /**
         * Bottom alignement.
         */
        BOTTOM,
        /**
         * Center alignement.
         */
        CENTER
    };

    /**
     * Gets aligement.
     *
     * @return the aligement
     */
    ALIGNEMENT getAligement();

    /**
     * Gets position.
     *
     * @return the position
     */
    Vector2d getPosition();

    /**
     * Gets sprite.
     *
     * @return the sprite
     */
    Sprite getSprite();

    /**
     * Gets radius.
     *
     * @return the radius
     */
    double getRadius();

    /**
     * Is exist boolean.
     *
     * @return the boolean
     */
    boolean isExist();

    /**
     * Destroy.
     */
    void destroy();

    /**
     * Calculate distance to current object double.
     *
     * @param otherObjectPosition the other object position
     * @return the double
     */
    double calculateDistanceToCurrentObject(Vector2d otherObjectPosition);

    /**
     * Gets distance to current object.
     *
     * @return the distance to current object
     */
    double getDistanceToCurrentObject();
}
