package org.plain.old.retro.shooter.engine.unit;

import org.plain.old.retro.shooter.engine.graphics.Sprite;

/**
 * The type Enemy.
 */
public class Enemy extends AbstractUnit {

    /**
     * The constant DEFAULT_RADIUS.
     */
    public static final double DEFAULT_RADIUS = 0.25d;

    /**
     * Instantiates a new Enemy.
     *
     * @param x       the x
     * @param y       the y
     * @param texture the texture
     */
    public Enemy(double x, double y, Sprite texture) {
		this(x, y, DEFAULT_RADIUS, texture);
	}

    /**
     * Instantiates a new Enemy.
     *
     * @param x       the x
     * @param y       the y
     * @param radius  the radius
     * @param texture the texture
     */
    public Enemy(double x, double y, double radius, Sprite texture) {
		super(x, y, radius, texture);
	}
}
