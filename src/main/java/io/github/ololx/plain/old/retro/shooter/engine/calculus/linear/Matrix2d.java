package io.github.ololx.plain.old.retro.shooter.engine.calculus.linear;

/**
 * The type Matrix 2 d.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 02.08.2020 10:44 <p>
 */
public class Matrix2d implements Matrix {

    /**
     * The X 1.
     */
    private double x1;

    /**
     * The Y 1.
     */
    private double y1;

    /**
     * The X 2.
     */
    private double x2;

    /**
     * The Y 2.
     */
    private double y2;

    /**
     * Instantiates a new Matrix 2 d.
     *
     * @param x1 the x 1
     * @param x2 the x 2
     * @param y1 the y 1
     * @param y2 the y 2
     */
    public Matrix2d(double x1, double x2, double y1, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Gets x 1.
     *
     * @return the x 1
     */
    public double getX1() {
        return this.x1;
    }

    /**
     * Sets x 1.
     *
     * @param x the x
     */
    public void setX1(double x) {
        this.x1 = x1;
    }

    /**
     * Gets y 1.
     *
     * @return the y 1
     */
    public double getY1() {
        return this.y1;
    }

    /**
     * Sets y 1.
     *
     * @param y the y
     */
    public void setY1(double y) {
        this.y1 = y1;
    }

    /**
     * Gets x 2.
     *
     * @return the x 2
     */
    public double getX2() {
        return this.x2;
    }

    /**
     * Sets x 2.
     *
     * @param x the x
     */
    public void setX2(double x) {
        this.x2 = x2;
    }

    /**
     * Gets y 2.
     *
     * @return the y 2
     */
    public double getY2() {
        return this.y2;
    }

    /**
     * Sets y 2.
     *
     * @param y the y
     */
    public void setY2(double y) {
        this.y2 = y2;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return String.format("<%s, %s>\n<%s, %s>", this.getX1(), this.getX2(), this.getY1(), this.getY2());
    }
}
