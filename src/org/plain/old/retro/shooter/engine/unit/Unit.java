package org.plain.old.retro.shooter.engine.unit;

import org.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.graphics.Sprite;

import java.io.Serializable;

/**
 * @project plain-old-retro-shooter
 * @created 08.08.2020 17:52
 * <p>
 * @author Alexander A. Kropotin
 */
public interface Unit extends Serializable {

    Vector2d getPosition();

    Sprite getSprite();

    double getRadius();

    boolean isExist();

    void destroy();

    double calculateDistanceToCurrentObject(Vector2d otherObjectPosition);

    double getDistanceToCurrentObject();
}
