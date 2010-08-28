/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;


/**
 * Triangles
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Triangles extends Primitives
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The triangles data */
    private int[] indices;


    /**
     * Sets the indices
     *
     * @param indices
     *            The indices to set
     */

    public void setIndices(final int[] indices)
    {
        this.indices = indices;
    }


    /**
     * Returns the indices
     *
     * @return The indices
     */

    public int[] getIndices()
    {
        return this.indices;
    }


    /**
     * @see de.ailis.threedee.collada.entities.Primitives#getCount()
     */

    @Override
    public int getCount()
    {
        return this.indices.length;
    }


    /**
     * @see de.ailis.threedee.collada.entities.Primitives#getPrimitivesType()
     */

    @Override
    public PrimitivesType getPrimitivesType()
    {
        return PrimitivesType.TRIANGLES;
    }
}
