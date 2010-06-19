/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;


/**
 * Perspective optic.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class PerspectiveOptic implements Optic
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The horizontal field of view in degrees */
    private Float xfov;

    /** The vertical field of view in degrees */
    private Float yfov;

    /** The aspect ratio of the field of view. */
    private Float aspectRatio;

    /** The distance of the near clipping plane. */
    private float znear;

    /** The distance of the far clipping plane. */
    private float zfar;


    /**
     * Returns the horizontal field of view in degrees.
     *
     * @return The horizontal field of view in degrees or null if not set
     */

    public Float getXfov()
    {
        return this.xfov;
    }


    /**
     * Sets the horizontal field of view in degrees.
     *
     * @param xfov
     *            The horizontal field of view in degrees or null for
     *            unspecified
     */

    public void setXfov(final Float xfov)
    {
        this.xfov = xfov;
    }


    /**
     * Returns the vertical field of view in degrees.
     *
     * @return The vertical field of view in degrees or null if not set
     */

    public Float getYfov()
    {
        return this.yfov;
    }


    /**
     * Sets the vertical field of view in degrees.
     *
     * @param yfov
     *            The vertical field of view in degrees or null for unspecified
     */

    public void setYfov(final Float yfov)
    {
        this.yfov = yfov;
    }


    /**
     * Returns the aspect ration of the field of view..
     *
     * @return The aspectRatio The aspect ratio of the field of view or null
     *         if not specified
     */

    public Float getAspectRatio()
    {
        return this.aspectRatio;
    }


    /**
     * Sets the aspect ratio of the field of view.
     *
     * @param aspectRatio
     *            The aspect ratio of the field of view set. Null for
     *            unspecified.
     */

    public void setAspectRatio(final Float aspectRatio)
    {
        this.aspectRatio = aspectRatio;
    }


    /**
     * Returns the distance of the near clipping plane.
     *
     * @return The distance of the near clipping plane
     */

    public float getZnear()
    {
        return this.znear;
    }


    /**
     * Sets the distance of the near clipping plane.
     *
     * @param znear
     *            The distance of the near clipping plane to set
     */

    public void setZnear(final float znear)
    {
        this.znear = znear;
    }


    /**
     * Returns the distance of the far clipping plane.
     *
     * @return The distance of the far clipping plane
     */

    public float getZfar()
    {
        return this.zfar;
    }


    /**
     * Sets the distance of the far clipping plane.
     *
     * @param zfar
     *            The distance of the far clipping plane to set
     */

    public void setZfar(final float zfar)
    {
        this.zfar = zfar;
    }
}
