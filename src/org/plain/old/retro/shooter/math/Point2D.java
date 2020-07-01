package org.plain.old.retro.shooter.math;

/**
 * The type Point 2 d.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:34 <p>
 */
public class Point2D implements Point {

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Point2D getInstance() {
        return new Point2D();
    }

    private double x;

    private double y;

    /**
     * Instantiates a new Point 2 d.
     */
    public Point2D() {
        this(ZERO, ZERO);
    }

    /**
     * Instantiates a new Point 2 d.
     *
     * @param x the x
     * @param y the y
     */
    public Point2D(double x, double y) {
        this.setX(x);
        this.setY(y);
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
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
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
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }
}
