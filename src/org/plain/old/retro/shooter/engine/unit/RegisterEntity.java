package org.plain.old.retro.shooter.engine.unit;

import org.plain.old.retro.shooter.calculus.linear.Vector2d;
import org.plain.old.retro.shooter.engine.graphics.Sprite;

import java.io.Serializable;
import java.util.UUID;

/**
 * @project plain-old-retro-shooter
 * @created 08.08.2020 17:52
 * <p>
 * @author Alexander A. Kropotin
 */
public interface RegisterEntity {

    UUID getUid();
}
