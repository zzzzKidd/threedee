/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.math;


/**
 * Axis-aligned bounds marked with a minimum and a maximum vector.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Bounds
{
    /** The minimum vector */
    private final Vector3f min;

    /** The maximum vector */
    private final Vector3f max;

    /** The center vector */
    private final Vector3f center;

    /** The The width */
    private float width;

    /** The height */
    private float height;

    /** The depth */
    private float depth;

    /** The size */
    private float size;


    /**
     * Constructs an empty bounds object.
     */

    public Bounds()
    {
        this.min = new Vector3f();
        this.max = new Vector3f();
        this.center = new Vector3f();
    }


    /**
     * Constructs new bounds with the specified minimum and maximum
     * vectors.
     *
     * @param min
     *            The minimum vector
     * @param max
     *            The maximum vector
     */

    public Bounds(final Vector3f min, final Vector3f max)
    {
        this.min = min;
        this.max = max;
        this.center = new Vector3f();
        updateCenter();
    }


    /**
     * Updates the center vector.
     */

    private void updateCenter()
    {
        this.center.setX((this.max.getX() + this.min.getX()) / 2);
        this.center.setY((this.max.getY() + this.min.getY()) / 2);
        this.center.setZ((this.max.getZ() + this.min.getZ()) / 2);
    }

    /**
     * Returns the minimum vector.
     *
     * @return The minimum vector
     */

    public Vector3f getMin()
    {
        return this.min;
    }


    /**
     * Returns the maximum vector.
     *
     * @return The maxmimum vector
     */

    public Vector3f getMax()
    {
        return this.max;
    }


    /**
     * Returns a copy of the bounds.
     *
     * @return A copy of the bounds
     */

    public Bounds copy()
    {
        return new Bounds(this.min.copy(), this.max.copy());
    }


    /**
     * Updates the bounds with the specified vector.
     *
     * @param v
     *            The vector to update the bounds with
     */

    public void update(final Vector3f v)
    {
        float x, y, z;

        x = v.getX();
        y = v.getY();
        z = v.getZ();
        if (x < this.min.getX()) this.min.setX(x);
        if (x > this.max.getX()) this.max.setX(x);
        if (y < this.min.getY()) this.min.setY(y);
        if (y > this.max.getY()) this.max.setY(y);
        if (z < this.min.getZ()) this.min.setZ(z);
        if (z > this.max.getZ()) this.max.setZ(z);

        this.width = this.max.getX() - this.min.getX();
        this.height = this.max.getY() - this.min.getY();
        this.depth = this.max.getZ() - this.min.getZ();
        this.size = Math.max(this.width, Math.max(this.height, this.depth));

        updateCenter();
    }


    /**
     * Updates this bounds with the specified bounds.
     *
     * @param box
     *            The bounds to update this one with
     */

    public void update(final Bounds box)
    {
        update(box.getMin());
        update(box.getMax());
    }


    /**
     * Returns the width of the bounds.
     *
     * @return The width
     */

    public float getWidth()
    {
        return this.width;
    }


    /**
     * Returns the height of the bounds.
     *
     * @return The height
     */

    public float getHeight()
    {
        return this.height;
    }


    /**
     * Returns the depth of the bounds.
     *
     * @return The depth
     */

    public float getDepth()
    {
        return this.depth;
    }


    /**
     * Returns the size of the bounds. This the largest side
     * (width, height, depth).
     *
     * @return The size
     */

    public float getSize()
    {
        return this.size;
    }


    /**
     * Returns the center vector.
     *
     * @return The center vector
     */

    public Vector3f getCenter()
    {
        return this.center;
    }
}
