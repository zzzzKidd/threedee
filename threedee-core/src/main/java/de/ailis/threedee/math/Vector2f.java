/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.math;

import java.io.Serializable;


/**
 * A vector with two float components.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Vector2f implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /** The X component */
    private final float x;

    /** The Y component */
    private final float y;


    /**
     * Constructs a new vector with two float components.
     *
     * @param x
     *            The X component
     * @param y
     *            The Y component
     */

    public Vector2f(final float x, final float y)
    {
        this.x = x;
        this.y = y;
    }


    /**
     * Returns the X component.
     *
     * @return The X component
     */

    public float getX()
    {
        return this.x;
    }


    /**
     * Returns the Y component.
     *
     * @return The Y component
     */

    public float getY()
    {
        return this.y;
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(this.x);
        result = prime * result + Float.floatToIntBits(this.y);
        return result;
    }


    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Vector2f other = (Vector2f) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y))
            return false;
        return true;
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return this.x + "," + this.y;
    }
}
