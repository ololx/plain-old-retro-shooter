package org.plain.old.retro.shooter.engine.calculus.linear;

/**
 * The type Vector2d.
 * <p>
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:30
 */
public class Vector2d implements Vector {

	/**
	 * The X.
	 */
	protected double x;

	/**
	 * The Y.
	 */
	protected double y;

	/**
	 * Instantiates a new Vector 2 d.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets x.
	 *
	 * @return the x
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Sets x.
	 *
	 * @param x the x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Gets y.
	 *
	 * @return the y
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Sets y.
	 *
	 * @param y the y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Gets module.
	 *
	 * @return the module
	 */
	public double getModule() {
		return Math.hypot(this.x, this.y);
	}

	/**
	 * Gets normalized.
	 *
	 * @return the normalized
	 */
	public Vector2d getNormalized() {
		return new Vector2d(this.x / this.getModule(), this.y / this.getModule());
	}

	/**
	 * Dot product double.
	 *
	 * @param om the om
	 * @return the double
	 */
	public double dotProduct(Vector2d om) {
		return this.x * om.getX() + this.y * om.getY();
	}

	/**
	 * Gets angle.
	 *
	 * @param om the om
	 * @return the angle
	 */
	public double getAngle(Vector2d om) {
		return Math.acos(this.dotProduct(om) / (this.getModule() * om.getModule()));
	}

	/**
	 * Gets angle between normal.
	 *
	 * @param om the om
	 * @return the angle between normal
	 */
	public double getAngleBetweenNormal(Vector2d om) {
		return Math.acos(this.getNormalized().dotProduct(om.getNormalized()));
	}

	/**
	 * Rotate vector 2 d.
	 *
	 * @param angle the angle
	 * @return the vector 2 d
	 */
	public Vector2d rotate(double angle) {
		return new Vector2d(
				(this.x * Math.cos(angle) - this.y * Math.sin(angle)),
				(this.x * Math.sin(angle) + this.y * Math.cos(angle))
		);
	}

	/**
	 * Rotate vector 2 d.
	 *
	 * @param matrix the matrix
	 * @return the vector 2 d
	 */
	public Vector2d rotate(Matrix2d matrix) {
		return new Vector2d(
				this.x * matrix.getX1() + this.y * matrix.getX2(),
				this.x * matrix.getY1() + this.y * matrix.getY2()
		);
	}

	/**
	 * Add vector 2 d.
	 *
	 * @param om the om
	 * @return the vector 2 d
	 */
	public Vector2d add(Vector2d om) {
		return new Vector2d(
				this.x + om.getX(),
				this.y + om.getY()
		);
	}

	/**
	 * Add vector 2 d.
	 *
	 * @param k the k
	 * @return the vector 2 d
	 */
	public Vector2d add(double k) {
		return new Vector2d(
				this.x + k,
				this.y + k
		);
	}

	/**
	 * Subtract vector 2 d.
	 *
	 * @param om the om
	 * @return the vector 2 d
	 */
	public Vector2d subtract(Vector2d om) {
		return new Vector2d(this.x - om.getX(), this.y - om.getY());
	}

	/**
	 * Subtract vector 2 d.
	 *
	 * @param k the k
	 * @return the vector 2 d
	 */
	public Vector2d subtract(double k) {
		return new Vector2d(this.x - k, this.y - k);
	}

	/**
	 * Multiply vector 2 d.
	 *
	 * @param om the om
	 * @return the vector 2 d
	 */
	public Vector2d multiply(Vector2d om) {
		return new Vector2d(
				this.x * om.getX(),
				this.y * om.getY()
		);
	}

	/**
	 * Multiply vector 2 d.
	 *
	 * @param k the k
	 * @return the vector 2 d
	 */
	public Vector2d multiply(double k) {
		return new Vector2d(
				this.x * k,
				this.y * k
		);
	}

	/**
	 * Divide vector 2 d.
	 *
	 * @param om the om
	 * @return the vector 2 d
	 */
	public Vector2d divide(Vector2d om) {
		return new Vector2d(this.x / om.getX(), this.y / om.getY());
	}

	/**
	 * Divide vector 2 d.
	 *
	 * @param k the k
	 * @return the vector 2 d
	 */
	public Vector2d divide(double k) {
		return new Vector2d(this.x / k, this.y / k);
	}

	/**
	 * Abs vector 2 d.
	 *
	 * @return the vector 2 d
	 */
	public Vector2d abs() {
		return new Vector2d(Math.abs(this.x), Math.abs(this.y));
	}

	/**
	 * To string string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return String.format("<%s, %s>", this.getX(), this.getY());
	}

	/**
	 * Clone vector 2 d.
	 *
	 * @return the vector 2 d
	 */
	public Vector2d clone() {
		return new Vector2d(this.getX(), this.getY());
	}

	/**
	 * Gets scaled.
	 *
	 * @param value the value
	 * @param scale the scale
	 * @return the scaled
	 */
	private double getScaled(double value, int scale) {
		return 1d * (((int) (value * scale)) / scale);
	}

	/**
	 * Gets scaled.
	 *
	 * @param value the value
	 * @return the scaled
	 */
	private double getScaled(double value) {
		return this.getScaled(value, 100);
	}

	public boolean isSame(Vector2d om) {
		return this.getX() == om.getX() && this.getY() == om.getY();
	}
}
