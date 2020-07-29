package org.plain.old.retro.shooter;

public class Enemy {

	public double xPosition;
	public double yPosition;

	public double radius;

	public double distanceToCamera;

	public boolean isAlive = true;

	public Sprite texture;

	public Enemy(double xPosition, double yPosition, Sprite texture) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.texture = texture;
		this.radius = 0.3;
	}

	public Sprite getSprite() {
		return texture;
	}

}
