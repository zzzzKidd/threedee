/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.sampling;


/**
 * Interpolator interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The data type
 */

public interface Interpolator<T>
{
    /**
     * Interpolates from value a to value b by using the specified position.
     * Position 0 means exactly a, Position 1 means exactly b.
     *
     * @param a
     *            The first value
     * @param b
     *            The second value
     * @param pos
     *            The position
     * @return The interpolated value
     */

    public T interpolate(T a, T b, float pos);
}
