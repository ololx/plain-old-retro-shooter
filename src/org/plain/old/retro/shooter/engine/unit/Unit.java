package org.plain.old.retro.shooter.engine.unit;

import org.plain.old.retro.shooter.engine.graphics.Sprite;
import org.plain.old.retro.shooter.calculus.linear.Vector2d;

/**
 * @project plain-old-retro-shooter
 * @created 08.08.2020 17:52
 * <p>
 * @author Alexander A. Kropotin
 */
public interface Unit {

    Vector2d getPosition();

    Sprite getSprite();

    double getRadius();

    boolean isExist();

    void destroy();

    double calculateDistanceToCurrentObject(Vector2d otherObjectPosition);

    double getDistanceToCurrentObject();
}
