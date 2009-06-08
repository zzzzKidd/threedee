/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.math;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * A vector with three elements of type double.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Vector3d implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 6049078497430956466L;

    /** The cached vector length */
    private transient double lenCache = -1;

    /** The cached unit vector */
    private transient Vector3d unit = null;

    /** The X coordinate of the vector */
    private final double x;

    /** The Y coordinate of the vector */
    private final double y;

    /** The Z coordinate of the vector */
    private final double z;


    /**
     * Constructs a new vector.
     * 
     * @param x
     *            The X coordinate
     * @param y
     *            The Y coordinate
     * @param z
     *            The Z coordinate
     */

    public Vector3d(final double x, final double y, final double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * Returns the X coordinate.
     * 
     * @return The X coordinate
     */

    public double getX()
    {
        return this.x;
    }


    /**
     * Returns the Y coordinate.
     * 
     * @return The Y coordinate
     */

    public double getY()
    {
        return this.y;
    }


    /**
     * Returns the Z coordinate.
     * 
     * @return The Z coordinate
     */

    public double getZ()
    {
        return this.z;
    }


    /**
     * @see Object#equals(Object)
     */

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        final Vector3d that = (Vector3d) obj;
        return new EqualsBuilder().append(this.x, that.x).append(this.y,
            that.y).append(this.z, that.z).isEquals();

    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.x).append(this.y).append(
            this.z).toHashCode();
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return "[" + this.x + "," + this.y + "," + this.z + "]";
    }


    /**
     * Adds the specified vector to the current vector and returns the result.
     * 
     * @param v
     *            The vector to add to the current one
     * @return The resulting vector
     */

    public Vector3d add(final Vector3d v)
    {
        return new Vector3d(this.x + v.x, this.y + v.y, this.z + v.z);
    }


    /**
     * Subtracts the specified vector to the current vector and returns the
     * result.
     * 
     * @param v
     *            The vector to subtract from the current one
     * @return The resulting vector
     */

    public Vector3d sub(final Vector3d v)
    {
        return new Vector3d(this.x - v.x, this.y - v.y, this.z - v.z);
    }


    /**
     * Multiplies the current vector with the specified factor and returns the
     * result.
     * 
     * @param f
     *            The factor to multiply the vector with
     * @return The resulting vector
     */

    public Vector3d multiply(final double f)
    {
        return new Vector3d(this.x * f, this.y * f, this.z * f);
    }


    /**
     * Calculates and returns the dot product of the current vector and the
     * specified one.
     * 
     * @param v
     *            The second vector for calculating the dot product
     * @return The dot product of the vectors
     */

    public double multiply(final Vector3d v)
    {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }


    /**
     * Multiplies this vector with the specified matrix and returns the
     * resulting vector.
     * 
     * @param m
     *            The matrix to multiply this vector with
     * @return The resulting vector
     */

    public Vector3d multiply(final Matrix4d m)
    {
        return m.multiply(this);
    }


    /**
     * Calculates the cross product of the current vector and the specified one.
     * 
     * @param v
     *            The second vector for calculating the cross product
     * @return The cross product of the vectors
     */

    public Vector3d cross(final Vector3d v)
    {
        return new Vector3d(this.y * v.z - this.z * v.y, this.z * v.x - this.x
            * v.z, this.x * v.y - this.y * v.x);
    }


    /**
     * Divides the current vector with the specified factor and returns the
     * result.
     * 
     * @param f
     *            The factor to divide the vector with
     * @return The resulting vector
     */

    public Vector3d divide(final double f)
    {
        return new Vector3d(this.x / f, this.y / f, this.z / f);
    }


    /**
     * Calculates the length of the vector and returns it. The result is cached
     * in a transient variable for speed reasons.
     * 
     * @return The length of the vector
     */

    public double getLength()
    {
        if (this.lenCache == -1)
            this.lenCache =
                Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return this.lenCache;
    }


    /**
     * Returns the unit vector of the current vector. The result is cached in a
     * transient variable for speed reasons.
     * 
     * @return The unit vector
     */

    public Vector3d toUnit()
    {
        if (this.unit == null)
        {
            final double length = getLength();
            this.unit =
                new Vector3d(this.x / length, this.y / length, this.z / length);
        }
        return this.unit;
    }


    /**
     * Returns the angle between the current vector and the specified vector.
     * 
     * @param v
     *            The second vector for calculating the angle
     * @return The angle in clockwise RAD
     */

    public double getAngle(final Vector3d v)
    {
        return Math.acos(toUnit().multiply(v.toUnit()));
    }
}
