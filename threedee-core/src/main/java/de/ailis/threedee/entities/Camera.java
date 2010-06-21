/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.io.Serializable;


/**
 * A camera
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Camera implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The field of view angle, in degrees, in the y direction. */
    private float fovY;

    /**
     * The aspect ratio (width/height) that determines the field of view in the
     * x direction.
     * If null then aspect ratio of the output rectangle is used.
     */
    private Float aspectRatio;

    /** The distance from the viewer to the near clipping plane */
    private float zNear;

    /** The distance from the viewer to the far clipping plane */
    private float zFar;


    /**
     * Creates a new camera with default settings.
     */

    public Camera()
    {
        this(45, 0.1f, 10000f);
    }


    /**
     * Constructs a new camera.
     *
     * @param fovY
     *            The field of view angle, in degrees, in the y direction.
     * @param zNear
     *            The distance from the viewer to the near clipping plane
     * @param zFar
     *            The distance from the viewer to the far clipping plane
     */

    public Camera(final float fovY, final float zNear, final float zFar)
    {
        this.fovY = fovY;
        this.zNear = zNear;
        this.zFar = zFar;
        this.aspectRatio = null;
    }

    /**
     * Constructs a new camera.
     *
     * @param fovY
     *            The field of view angle, in degrees, in the y direction.
     * @param aspectRatio
     *            The aspect ratio (width/height) that determines the field of
     *            view in the x direction.
     * @param zNear
     *            The distance from the viewer to the near clipping plane
     * @param zFar
     *            The distance from the viewer to the far clipping plane
     */

    public Camera(final float fovY, final float aspectRatio, final float zNear,
            final float zFar)
    {
        this(fovY, zNear, zFar);
        this.aspectRatio = aspectRatio;
    }


    /**
     * Copies camera data from the specified camera.
     *
     * @param src
     *            The camera to copy the data from
     */

    public void copyFrom(final Camera src)
    {
        this.fovY = src.fovY;
        this.aspectRatio = src.aspectRatio;
        this.zFar = src.zFar;
        this.zNear = src.zNear;
    }


    /**
     * Returns the field of view angle, in degrees, in the y direction..
     *
     * @return The field of view angle, in degrees, in the y direction.
     */

    public float getFovY()
    {
        return this.fovY;
    }


    /**
     * Sets the field of view angle, in degrees, in the y direction..
     *
     * @param fovY
     *            The field of view angle, in degrees, in the y direction.
     */

    public void setFovY(final float fovY)
    {
        this.fovY = fovY;
    }


    /**
     * Returns the aspect ratio (width/height) that determines the field of view
     * in the x direction. If null is returned then the aspect ratio of the
     * output rectangle is used.
     *
     * @return The aspect ratio or null if automatically calculated
     */

    public Float getAspectRatio()
    {
        return this.aspectRatio;
    }


    /**
     * Sets the aspect ratio (width/height) that determines the field of view in
     * the x direction. Set to null to use the aspect ratio of the output
     * rectangle instead.
     *
     * @param aspectRatio
     *            The aspect ratio to set
     */

    public void setAspectRatio(final Float aspectRatio)
    {
        this.aspectRatio = aspectRatio;
    }


    /**
     * Returns the distance from the viewer to the near clipping plane.
     *
     * @return The distance from the viewer to the near clipping plane
     */

    public float getZNear()
    {
        return this.zNear;
    }


    /**
     * Sets the distance from the viewer to the near clipping plane.
     *
     * @param zNear
     *            The distance from the viewer to the near clipping plane
     */

    public void setZNear(final float zNear)
    {
        this.zNear = zNear;
    }


    /**
     * Returns the distance from the viewer to the far clipping plane.
     *
     * @return The distance from the viewer to the far clipping plane
     */

    public float getZFar()
    {
        return this.zFar;
    }


    /**
     * Sets the distance from the viewer to the far clipping plane.
     *
     * @param zFar
     *            The distance from the viewer to the far clipping plane
     */

    public void setZFar(final float zFar)
    {
        this.zFar = zFar;
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((this.aspectRatio == null) ? 0 : this.aspectRatio.hashCode());
        result = prime * result + Float.floatToIntBits(this.fovY);
        result = prime * result + Float.floatToIntBits(this.zFar);
        result = prime * result + Float.floatToIntBits(this.zNear);
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
        final Camera other = (Camera) obj;
        if (this.aspectRatio == null)
        {
            if (other.aspectRatio != null) return false;
        }
        else if (!this.aspectRatio.equals(other.aspectRatio)) return false;
        if (Float.floatToIntBits(this.fovY) != Float.floatToIntBits(other.fovY))
            return false;
        if (Float.floatToIntBits(this.zFar) != Float.floatToIntBits(other.zFar))
            return false;
        if (Float.floatToIntBits(this.zNear) != Float
                .floatToIntBits(other.zNear)) return false;
        return true;
    }


    /**
     * Returns the field of view aspect ratio (width/height). If a custom one
     * was set then this is returned. Otherwise it is automatically calculated
     * from the specified viewport size.
     *
     * @param width
     *            The viewport width
     * @param height
     *            The viewport height
     * @return The field of view aspect ratio
     */

    public float getAspectRatio(final int width, final int height)
    {
        if (this.aspectRatio == null)
            return (float) width / (float) height;
        else
            return this.aspectRatio;
    }
}