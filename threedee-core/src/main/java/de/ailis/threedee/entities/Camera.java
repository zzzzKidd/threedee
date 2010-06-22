/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;




/**
 * A camera
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Camera
{
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