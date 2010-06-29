/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.sampling.interpolators;

import de.ailis.threedee.sampling.Interpolator;


/**
 * Linear interpolator for a float value.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class LinearFloatInterpolator implements Interpolator<Float>
{
    /**
     * @see Interpolator#interpolate(Object, Object, float)
     */

    @Override
    public Float interpolate(final Float a, final Float b,
        final float pos)
    {
        return a + (b - a) * pos;
    }
}
