package org.plain.old.retro.shooter.math;

/**
 * The type Vector2d.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:30 <p>
 */
public class Vector2d implements Vector {
	private double x;
	private double y;

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

	public double length() {
		return Math.hypot(this.x, this.y);
	}

	public double dotProduct(Vector2d om) {
		return this.x * om.getX() + this.y * om.getY();
	}

	public Vector2d getNormalized() {
		return new Vector2d(this.x / this.length(), this.y / this.length());
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

	public String toString() {
		return String.format("<%d, %d>", this.getX(), this.getY());
	}
}
