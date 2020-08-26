package org.plain.old.retro.shooter.engine.unit;

import org.plain.old.retro.shooter.engine.graphics.Sprite;

public class Enemy extends AbstractUnit {

	public static final double DEFAULT_RADIUS = 0.25d;

	public Enemy(double x, double y, Sprite texture) {
		this(x, y, DEFAULT_RADIUS, texture);
	}
	public Enemy(double x, double y, double radius, Sprite texture) {
		super(x, y, radius, texture);
	}
}
