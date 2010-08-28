/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.sampling;

import de.ailis.gramath.Matrix4f;
import de.ailis.threedee.sampling.interpolators.LinearFloatInterpolator;
import de.ailis.threedee.sampling.interpolators.LinearMatrixInterpolator;


/**
 * This factory returns interpolators.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public final class InterpolatorFactory
{
    /** Singleton instance */
    private static final InterpolatorFactory instance = new InterpolatorFactory();

    /** The linear float interpolator */
    private final LinearFloatInterpolator linearFloatInterpolator = new LinearFloatInterpolator();

    /** The linear matrix interpolator */
    private final LinearMatrixInterpolator linearMatrixInterpolator = new LinearMatrixInterpolator();


    /**
     * Private constructor to prevent instantiation.
     */

    private InterpolatorFactory()
    {
        // Empty
    }


    /**
     * Returns the singleton instance.
     *
     * @return The singleton instance
     */

    public static InterpolatorFactory getInstance()
    {
        return instance;
    }


    /**
     * Returns an interpolator for the specified value type and interpolation
     * method.
     *
     * @param type
     *            The value type
     * @param interpolation
     *            The interpolation method
     * @return The interpolator
     * @param <T>
     *            The value type
     */

    @SuppressWarnings("unchecked")
    public <T> Interpolator<T> getInterpolator(final Class<T> type,
        final Interpolation interpolation)
    {
        switch (interpolation)
        {
            case LINEAR:
                if (Matrix4f.class.isAssignableFrom(type))
                    return (Interpolator<T>) this.linearMatrixInterpolator;
                else if (type == Float.class)
                    return (Interpolator<T>) this.linearFloatInterpolator;
                else
                    throw new UnsupportedOperationException(
                        "Linear interpolation for " + type
                            + " is not implemented");

            default:
                throw new UnsupportedOperationException(
                    "No implementation for " + interpolation
                        + " interpolation found");
        }
    }
}
