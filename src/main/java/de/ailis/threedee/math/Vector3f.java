/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.math;


/**
 * A vector with three float components. Because of speed reasons this class is
 * mutable.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 84727 $
 */

public class Vector3f extends Coord3f
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** If cached length is valid */
    private boolean cachedLengthValid = false;

    /** The cached vector length */
    private float cachedLength = 0;


    /**
     * Creates an uninitialized vector.
     */

    public Vector3f()
    {
        // Empty
    }


    /**
     * Constructs a new vector with three float components.
     *
     * @param x
     *            The X component
     * @param y
     *            The Y component
     * @param z
     *            The Z component
     */

    public Vector3f(final float x, final float y, final float z)
    {
        super(x, y, z);
    }


    /**
     * Returns a copy of this vector.
     *
     * @return A copy of this vector
     */

    public Vector3f copy()
    {
        return new Vector3f(this.x, this.y, this.z);
    }


    /**
     * Copies the values of this vector into the specified target vector.
     *
     * @param target
     *            The target vector
     * @return This vector (not the target!) for chaining
     */

    public Vector3f copy(final Vector3f target)
    {
        target.x = this.x;
        target.y = this.y;
        target.z = this.z;
        target.cachedLengthValid = false;
        return this;
    }


    /**
     * Sets the values of this vector to the values of the specifies source
     * vector.
     *
     * @param source
     *            The source vector
     * @return This vector (not the source!) for chaining
     */

    public Vector3f set(final Vector3f source)
    {
        this.x = source.x;
        this.y = source.y;
        this.z = source.z;
        this.cachedLengthValid = false;
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

    @Override
    public Vector3f set(final float x, final float y, final float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.cachedLengthValid = false;
        return this;
    }


    /**
     * Sets the x.
     *
     * @param x
     *            The x to set
     * @return Chain object
     */

    @Override
    public Vector3f setX(final float x)
    {
        this.x = x;
        this.cachedLengthValid = false;
        return this;
    }


    /**
     * Sets the y.
     *
     * @param y
     *            The y to set
     * @return Chain object
     */

    @Override
    public Vector3f setY(final float y)
    {
        this.y = y;
        this.cachedLengthValid = false;
        return this;
    }


    /**
     * Sets the z.
     *
     * @param z
     *            The z to set
     * @return Chain object
     */

    @Override
    public Vector3f setZ(final float z)
    {
        this.z = z;
        this.cachedLengthValid = false;
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
        final Vector3f other = (Vector3f) obj;
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
        return "[ " + this.x + ", " + this.y + ", " + this.z + " ]";
    }


    /**
     * Checks if the vector is empty (All components are 0)
     *
     * @return True if vector is empty, false if not
     */

    @Override
    public boolean isEmpty()
    {
        return this.x == 0 && this.y == 0 && this.z == 0;
    }


    /**
     * Returns the length of the vector
     *
     * @return The length of the vector
     */

    public float getLength()
    {
        if (!this.cachedLengthValid)
        {
            this.cachedLength = (float) Math.sqrt(this.x * this.x + this.y
                    * this.y + this.z * this.z);
            this.cachedLengthValid = true;
        }
        return this.cachedLength;
    }


    /**
     * Normalizes the vector.
     *
     * @return This object for chaining
     */

    public Vector3f normalize()
    {
        final float l = getLength();
        if (l == 0) return this;
        this.x /= l;
        this.y /= l;
        this.z /= l;
        return this;
    }


    /**
     * Subtracts the coordinates of the specified vector from this one.
     *
     * @param v
     *            The vector to subtract
     * @return This vector for chaining
     */

    public Vector3f sub(final Vector3f v)
    {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }


    /**
     * Adds the coordinates of the specified vector to this one.
     *
     * @param v
     *            The vector to add
     * @return This vector for chaining
     */

    public Vector3f add(final Vector3f v)
    {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }


    /**
     * Multiplies all coordinates of the vector with the specified factor.
     *
     * @param f
     *            The factor to multiply with
     * @return This vector for chaining
     */

    public Vector3f multiply(final float f)
    {
        this.x *= f;
        this.y *= f;
        this.z *= f;
        return this;
    }


    /**
     * Divides all coordinates of the vector with the specified factor.
     * Diving by zero does NOT throw an exception but instead it sets the
     * vector values to infinity.
     *
     * @param f
     *            The factor to multiply with
     * @return This vector for chaining
     */

    public Vector3f divide(final float f)
    {
        if (f == 0)
        {
            this.x = Float.POSITIVE_INFINITY;
            this.y = Float.POSITIVE_INFINITY;
            this.z = Float.POSITIVE_INFINITY;
        }
        else
        {
            this.x /= f;
            this.y /= f;
            this.z /= f;
        }
        return this;
    }


    /**
     * Returns the dot product between this vector and the specified vector.
     *
     * @param v
     *            The vector to dot-multiplicate this one with
     * @return The dot product
     */

    public float dot(final Vector3f v)
    {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }


    /**
     * Creates the cross product of this vector and the specified one and stores
     * the result back into this vector.
     *
     * @param v
     *            The other vector
     * @return This vector for chaining
     */

    public Vector3f cross(final Vector3f v)
    {
        return set(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x);
    }


    /**
     * Returns the angle between this vector and the specified one.
     *
     * @param v
     *            The other vector
     * @return The angle in clockwise RAD.
     */

    public float getAngle(final Vector3f v)
    {
        return (float) Math.acos(this.copy().normalize().dot(v.normalize()));
    }


    /**
     * Multiplies this vector with the specified matrix.
     *
     * @param matrix
     *            The transformation matrix
     * @return This vector for chaining
     */

    public Vector3f multiply(final Matrix4f matrix)
    {
        final float[] m = matrix.getArray();
        return this.set(m[0] * this.x + m[4] * this.y + m[8] * this.z + m[12],
                m[1] * this.x + m[5] * this.y + m[9] * this.z + m[13], m[2]
                        * this.x + m[6] * this.y + m[10] * this.z + m[14]);

    }


    /**
     * Inverts the vector.
     *
     * @return This vector for chaining
     */

    public Vector3f invert()
    {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }
}
