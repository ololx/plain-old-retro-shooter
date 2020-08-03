package org.plain.old.retro.shooter.unit;

import org.plain.old.retro.shooter.engine.graphics.Sprite;
import org.plain.old.retro.shooter.engine.linear.Vector2d;

public class Enemy {

	private Vector2d position;

	public double radius;

	public double distanceToCamera;

	public boolean isAlive = true;

	public Sprite texture;

	public Enemy(double x, double y, Sprite texture) {
		this.position = new Vector2d(x, y);
		this.texture = texture;
		this.radius = 0.25;
	}

	public Vector2d getPosition() {
		return this.position;
	}

	public Sprite getSprite() {
		return texture;
	}

}
