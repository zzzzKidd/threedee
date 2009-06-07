/*
 * $Id: JsCodeWriter.java 873 2009-05-24 16:12:59Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.starglance.math


/**
 * A vector with 3 double elements.
 */

@serializable
final class Vector3d(val x: Double, val y: Double, val z: Double)
{
    /** The cached vector length */
    @transient
    private var lenCache: Double = -1;

    /** The cached unit vector */
    @transient
    private var unit: Vector3d = null

    
    /**
     * @see java.lang.equals(Object)
     */

    override def equals(v: Any): Boolean =
    {
        v match
        {
            case v: Vector3d => x == v.x && y == v.y && z == v.z
            case _ => false
        }
    }


    /**
     * @see java.lang.hashCode
     */
    
    override def hashCode: Int =
    {
        41 * (41 * (41 + x.hashCode) + y.hashCode) + z.hashCode
    }


    /**
     * @see java.lang.toString
     */

    override def toString: String =
    {
        x + "," + y + "," + z
    }


    /**
     * Adds the specified vector to the current vector and returns the
     * result.
     *
     * @param v
     *            The vector to add to the current one
     * @return The resulting vector
     */

    def + (v: Vector3d) = new Vector3d(x + v.x, y + v.y, z + v.z)

    
    /**
     * Subtracts the specified vector to the current vector and returns the
     * result.
     *
     * @param v
     *            The vector to subtract from the current one
     * @return The resulting vector
     */

    def - (v: Vector3d) = new Vector3d(x - v.x, y - v.y, z - v.z)


    /**
     * Multiplies the current vector with the specified factor and returns
     * the result.
     *
     * @param f
     *            The factor to multiply the vector with
     * @return The resulting vector
     */

    def * (f: Double) = new Vector3d(x * f, y * f, z * f)


    /**
     * Calculates and returns the dot product of the current vector and the
     * specified one.
     *
     * @param v
     *            The second vector for calculating the dot product
     * @return The dot product of the vectors
     */

    def * (v: Vector3d) = x * v.x + y * v.y + z * v.z



    /**
     * Multiplies this vector with the specified matrix and returns the
     * resulting vector.
     *
     * @param m
     *            The matrix to multiply this vector with
     * @return The resulting vector
     */

    def * (m: Matrix4d): Vector3d = m * this
    

    /**
     * Calculates the cross product of the current vector and the
     * specified one.
     *
     * @param v
     *            The second vector for calculating the cross product
     * @return The cross product of the vectors
     */

    def cross (v: Vector3d) =
        new Vector3d(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)


    /**
     * Divides the current vector with the specified factor and returns
     * the result.
     *
     * @param f
     *            The factor to divide the vector with
     * @return The resulting vector
     */

    def / (f: Double) = new Vector3d(x / f, y / f, z / f)


    /**
     * Calculates the length of the vector and returns it. The result is
     * cached in a transient variable for speed reasons.
     *
     * @return The length of the vector
     */
    
    def length =
    {
        if (lenCache == -1) lenCache = Math.sqrt(x * x + y * y + z * z)
        lenCache
    }


    /**
     * Returns the unit vector of the current vector. The result is cached in
     * a transient variable for speed reasons.
     *
     * @return The unit vector
     */

    def toUnit =
    {
        if (unit == null) unit = new Vector3d(x / length, y / length, z / length)
        unit
    }


    /**
     * Returns the angle between the current vector and the specified vector.
     *
     * @param v
     *            The second vector for calculating the angle
     * @return The angle in clockwise RAD
     */

    def angle(v: Vector3d) = Math.acos(toUnit * v.toUnit)
}
