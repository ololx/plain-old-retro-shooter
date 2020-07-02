package org.plain.old.retro.shooter.math.point;

import java.util.Objects;

/**
 * The type Abstract coordinate.
 *
 * @param <D> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 02.07.2020 14:47 <p>
 */
public class AbstractCoordinate<D extends Number> implements Coordinate<D> {

    /**
     * The Coordinate.
     */
    protected D[] coordinate;

    /**
     * Instantiates a new Abstract coordinate.
     *
     * @param coordinate the coordinate
     */
    public AbstractCoordinate(D... coordinate) {
        Objects.requireNonNull(coordinate);
        this.coordinate = coordinate;
    }

    /**
     * Returns the coordinate space dimension
     * which is equals of elements count.
     *
     * @return the coordinate space dimension.
     */
    @Override
    public int dimension() {
        return this.coordinate.length;
    }

    /**
     * Returns the coordinate value at the specified axis
     * of this space space dimension.
     *
     * @param axisIndex an index of the axis for value to return
     * @return the value at the specified axis
     * of this space dimension
     */
    @Override
    public D get(int axisIndex) {
        Objects.checkIndex(axisIndex, this.dimension());
        return this.coordinate[axisIndex];
    }

    /**
     * Replaces the coordinateValue value at the specified axis
     * of this space space dimension with the specified value.
     *
     * @param axisIndex an index of the axis for value to replace
     * @param coordinateValue a value to be stored at the specified axis
     */
    @Override
    public void set(int axisIndex, D coordinateValue) {
        Objects.checkIndex(axisIndex, this.dimension());
        this.coordinate[axisIndex] = coordinateValue;
    }
}
