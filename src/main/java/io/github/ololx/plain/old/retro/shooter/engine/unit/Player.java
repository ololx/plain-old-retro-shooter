package io.github.ololx.plain.old.retro.shooter.engine.unit;

import io.github.ololx.plain.old.retro.shooter.engine.graphics.Sprite;

import java.util.UUID;

/**
 * The type Player.
 */
public class Player extends AbstractUnit implements RegisterEntity {

    /**
     * The constant DEFAULT_RADIUS.
     */
    public static final double DEFAULT_RADIUS = 0.25d;

    /**
     * The Uid.
     */
    private final UUID uid = UUID.randomUUID();

    /**
     * Instantiates a new Player.
     *
     * @param x       the x
     * @param y       the y
     * @param texture the texture
     */
    public Player(double x, double y, Sprite texture) {
		this(x, y, DEFAULT_RADIUS, texture);
	}

    /**
     * Instantiates a new Player.
     *
     * @param x       the x
     * @param y       the y
     * @param radius  the radius
     * @param texture the texture
     */
    public Player(double x, double y, double radius, Sprite texture) {
		super(x, y, radius, texture);
	}

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
		return this.position.getX() + "&" + this.position.getY();
	}

    /**
     * Gets uid.
     *
     * @return the uid
     */
    @Override
	public UUID getUid() {
		return this.uid;
	}
}
