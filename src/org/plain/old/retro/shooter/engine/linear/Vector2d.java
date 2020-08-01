package org.plain.old.retro.shooter.engine.linear;

/**
 * The type Vector2d.
 * <p>
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:30
 */
public class Vector2d implements Vector {
	protected double x;
	protected double y;

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getModule() {
		return Math.hypot(this.x, this.y);
	}

	public Vector2d getNormalized() {
		return new Vector2d(this.x / this.getModule(), this.y / this.getModule());
	}

	public double dotProduct(Vector2d om) {
		return this.x * om.getX() + this.y * om.getY();
	}

	public double getAngle(Vector2d om) {
		return Math.acos(this.dotProduct(om) / (this.getModule() * om.getModule()));
	}

	public double getAngleBetweenNormal(Vector2d om) {
		return Math.acos(this.getNormalized().dotProduct(om.getNormalized()));
	}

	public Vector2d rotate(double angle) {
		return new Vector2d(
				(this.x * Math.cos(angle) - this.y * Math.sin(angle)),
				(this.x * Math.sin(angle) + this.y * Math.cos(angle))
		);
	}

	public Vector2d add(Vector2d om) {
		return new Vector2d(this.x + om.getX(), this.y + om.getY());
	}

	public Vector2d add(double k) {
		return new Vector2d(this.x + k, this.y + k);
	}

	public Vector2d subtract(Vector2d om) {
		return new Vector2d(this.x - om.getX(), this.y - om.getY());
	}

	public Vector2d subtract(double k) {
		return new Vector2d(this.x - k, this.y - k);
	}

	public Vector2d multiply(Vector2d om) {
		return new Vector2d(this.x * om.getX(), this.y * om.getY());
	}

	public Vector2d multiply(double k) {
		return new Vector2d(this.x * k, this.y * k);
	}

	public Vector2d divide(Vector2d om) {
		return new Vector2d(this.x / om.getX(), this.y / om.getY());
	}

	public Vector2d divide(double k) {
		return new Vector2d(this.x / k, this.y / k);
	}

	public Vector2d abs() {
		return new Vector2d(Math.abs(this.x), Math.abs(this.y));
	}

	@Override
	public String toString() {
		return String.format("<%s, %s>", this.getX(), this.getY());
	}

	public Vector2d clone() {
		return new Vector2d(this.getX(), this.getY());
	}
}
