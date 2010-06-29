/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.sampling.interpolators;

import de.ailis.threedee.math.Matrix4f;
import de.ailis.threedee.sampling.Interpolator;


/**
 * Linear interpolator for 4x4 matrices. This class is thread safe but be aware
 * that this interpolator always returns the same matrix instance. So if you
 * need to keep this matrix for a longer time you have to clone it or otherwise
 * the next interpolator will modify the matrix values.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class LinearMatrixInterpolator implements Interpolator<Matrix4f>
{
    /** The working matrix */
    private final ThreadLocal<Matrix4f> storage = new ThreadLocal<Matrix4f>();


    /**
     * Constructor
     */

    public LinearMatrixInterpolator()
    {
        this.storage.set(Matrix4f.identity());
    }


    /**
     * @see Interpolator#interpolate(Object, Object, float)
     */

    @Override
    public Matrix4f interpolate(final Matrix4f a, final Matrix4f b,
        final float pos)
    {
        // Get working matrix from thread local variable
        Matrix4f tmp = this.storage.get();
        if (tmp == null)
        {
            tmp = Matrix4f.identity();
            this.storage.set(tmp);
        }

        final float[] va = a.getArray();
        final float[] vb = b.getArray();
        return tmp.set(
            va[0] + (vb[0] - va[0]) * pos,
            va[1] + (vb[1] - va[1]) * pos,
            va[2] + (vb[2] - va[2]) * pos,
            va[3] + (vb[3] - va[3]) * pos,
            va[4] + (vb[4] - va[4]) * pos,
            va[5] + (vb[5] - va[5]) * pos,
            va[6] + (vb[6] - va[6]) * pos,
            va[7] + (vb[7] - va[7]) * pos,
            va[8] + (vb[8] - va[8]) * pos,
            va[9] + (vb[9] - va[9]) * pos,
            va[10] + (vb[10] - va[10]) * pos,
            va[11] + (vb[11] - va[11]) * pos,
            va[12] + (vb[12] - va[12]) * pos,
            va[13] + (vb[13] - va[13]) * pos,
            va[14] + (vb[14] - va[14]) * pos,
            va[15] + (vb[15] - va[15]) * pos);
    }
}
