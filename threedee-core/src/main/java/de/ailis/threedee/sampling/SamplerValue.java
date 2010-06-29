/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.sampling;


/**
 * Interface for sampler values
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The sampler value type
 */

public class SamplerValue<T>
{
    /** The sampler value */
    private final T value;

    /** The interpolation type */
    private final Interpolation interpolation;


    /**
     * Constructs a new sampler value.
     *
     * @param value
     *            The sampler value
     * @param interpolation
     *            The interpolation type
     */

    public SamplerValue(final T value, final Interpolation interpolation)
    {
        this.value = value;
        this.interpolation = interpolation;
    }


    /**
     * Returns the sampler value.
     *
     * @return The sampler value
     */

    public T getValue()
    {
        return this.value;
    }


    /**
     * Returns the interpolation type.
     *
     * @return The interpolation type
     */

    public Interpolation getInterpolation()
    {
        return this.interpolation;
    }
}
