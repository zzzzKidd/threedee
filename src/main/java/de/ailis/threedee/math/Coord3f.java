/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.math;

import java.io.Serializable;


/**
 * A base container for three dimensional coordinates measured as floats.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Coord3f implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The X component */
    protected float x;

    /** The Y component */
    protected float y;

    /** The Z component */
    protected float z;


    /**
     * Creates uninitialized coordinates.
     */

    public Coord3f()
    {
        // Empty
    }


    /**
     * Constructs new coordinates.
     *
     * @param x
     *            The X component
     * @param y
     *            The Y component
     * @param z
     *            The Z component
     */

    public Coord3f(final float x, final float y, final float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * Sets the values of this coordinate to the values of the specifies
     * coordinates.
     *
     * @param source
     *            The source coordinates
     * @return This vector (not the source!) for chaining
     */

    public Coord3f set(final Coord3f source)
    {
        this.x = source.x;
        this.y = source.y;
        this.z = source.z;
        return this;
    }


    /**
     * Sets the values of this vector.
     *
     * @param x
     *            The X coordinate
     * @param y
     *            The Y coordinate
     * @param z
     *            The Z coordinate
     * @return This vector for chaining
     */

    public Coord3f set(final float x, final float y, final float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
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
     * Returns the Z component
     *
     * @return The Z component
     */

    public float getZ()
    {
        return this.z;
    }


    /**
     * Sets the x.
     *
     * @param x
     *            The x to set
     * @return Chain object
     */

    public Coord3f setX(final float x)
    {
        this.x = x;
        return this;
    }


    /**
     * Sets the y.
     *
     * @param y
     *            The y to set
     * @return Chain object
     */

    public Coord3f setY(final float y)
    {
        this.y = y;
        return this;
    }


    /**
     * Sets the z.
     *
     * @param z
     *            The z to set
     * @return Chain object
     */

    public Coord3f setZ(final float z)
    {
        this.z = z;
        return this;
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
        result = prime * result + Float.floatToIntBits(this.z);
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
        final Coord3f other = (Coord3f) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return this.x + "," + this.y + "," + this.z;
    }


    /**
     * Checks if the vector is empty (All components are 0)
     *
     * @return True if vector is empty, false if not
     */

    public boolean isEmpty()
    {
        return this.x == 0 && this.y == 0 && this.z == 0;
    }
}
