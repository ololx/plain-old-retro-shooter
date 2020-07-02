package org.plain.old.retro.shooter.math.point;

/**
 * The interface Coordinate.
 *
 * @param <D> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 01.07.2020 17:33 <p>
 */
public interface Coordinate<D extends Number> {

    /**
     * The constant ZERO.
     */
    Number ZERO = 0;

    /**
     * Returns the coordinate space dimension.
     *
     * @return the coordinate space dimension
     */
    int dimension();

    /**
     * Returns the coordinate value at the specified axis
     * of this space space dimension.
     *
     * @param axisIndex an index of the axis for value to return
     * @return the value at the specified axis of this space space dimension
     */
    D get(int axisIndex);

    /**
     * Replaces the coordinateValue value at the specified axis
     * of this space space dimension with the specified value.
     *
     * @param axisIndex       an index of the axis for value to replace
     * @param coordinateValue a value to be stored at the specified axis
     */
    void set(int axisIndex, D coordinateValue);
}
