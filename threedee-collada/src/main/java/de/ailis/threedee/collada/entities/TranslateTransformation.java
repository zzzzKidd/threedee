/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;


/**
 * A translation transformation.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class TranslateTransformation implements Transformation
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /** The matrix values */
    private final float[] values = new float[3];


    /**
     * Returns the matrix values.
     *
     * @return The matrix values
     */

    public float[] getValues()
    {
        return this.values;
    }
}
