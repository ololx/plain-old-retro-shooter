package org.plain.old.retro.shooter.calculus.linear;

/**
 * @project plain-old-retro-shooter
 * @created 02.08.2020 10:44
 * <p>
 * @author Alexander A. Kropotin
 */
public class Matrix2d implements Matrix {

    private double x1;

    private double y1;

    private double x2;

    private double y2;

    public Matrix2d(double x1, double x2, double y1, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getX1() {
        return this.x1;
    }

    public void setX1(double x) {
        this.x1 = x1;
    }

    public double getY1() {
        return this.y1;
    }

    public void setY1(double y) {
        this.y1 = y1;
    }

    public double getX2() {
        return this.x2;
    }

    public void setX2(double x) {
        this.x2 = x2;
    }

    public double getY2() {
        return this.y2;
    }

    public void setY2(double y) {
        this.y2 = y2;
    }

    @Override
    public String toString() {
        return String.format("<%s, %s>\n<%s, %s>", this.getX1(), this.getX2(), this.getY1(), this.getY2());
    }
}
