package org.plain.old.retro.shooter;

public class Entity {

	public double xPosition;
	public double yPosition;

	public double distanceToCamera;

	public boolean isAlive = true;

	public Sprite texture;

	public Entity(double xPosition, double yPosition, Sprite texture) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.texture = texture;
	}

	public Sprite getSprite() {
		return texture;
	}

}
