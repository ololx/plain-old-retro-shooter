package org.plain.old.retro.shooter.engine.unit;

import org.plain.old.retro.shooter.engine.graphics.Sprite;

import java.util.UUID;

public class Player extends AbstractUnit implements RegisterEntity {

	public static final double DEFAULT_RADIUS = 0.25d;

	private final UUID uid = UUID.randomUUID();

	public Player(double x, double y, Sprite texture) {
		this(x, y, DEFAULT_RADIUS, texture);
	}
	public Player(double x, double y, double radius, Sprite texture) {
		super(x, y, radius, texture);
	}

	public String getMessage() {
		return this.position.getX() + "&" + this.position.getY();
	}

	@Override
	public UUID getUid() {
		return this.uid;
	}
}
