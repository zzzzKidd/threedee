/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;


/**
 * A RGBA color
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaColor implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The red component */
    private float red;

    /** The green component */
    private float green;

    /** The blue component */
    private float blue;

    /** The alpha component */
    private float alpha;


    /**
     * Constructs a new color which is initially black
     */

    public ColladaColor()
    {
        this(0, 0, 0);
    }


    /**
     * Constructs a RGB color.
     *
     * @param red
     *            The red component
     * @param green
     *            The green component
     * @param blue
     *            The blue component
     */

    public ColladaColor(final float red, final float green, final float blue)
    {
        this(red, green, blue, 1);
    }


    /**
     * Constructs a RGBA color.
     *
     * @param red
     *            The red component
     * @param green
     *            The green component
     * @param blue
     *            The blue component
     * @param alpha
     *            The alpha component
     */

    public ColladaColor(final float red, final float green, final float blue,
            final float alpha)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }


    /**
     * Returns the red component.
     *
     * @return The red component
     */

    public float getRed()
    {
        return this.red;
    }


    /**
     * Sets the red component.
     *
     * @param red
     *            The red component to set
     */

    public void setRed(final float red)
    {
        this.red = red;
    }


    /**
     * Returns the green component.
     *
     * @return The green component
     */

    public float getGreen()
    {
        return this.green;
    }


    /**
     * Sets the green component.
     *
     * @param green
     *            The green component to set
     */

    public void setGreen(final float green)
    {
        this.green = green;
    }


    /**
     * Returns the blue component.
     *
     * @return The blue component
     */

    public float getBlue()
    {
        return this.blue;
    }


    /**
     * Sets the blue component.
     *
     * @param blue
     *            The blue component to set
     */

    public void setBlue(final float blue)
    {
        this.blue = blue;
    }


    /**
     * Returns the alpha component.
     *
     * @return The alpha component
     */

    public float getAlpha()
    {
        return this.alpha;
    }


    /**
     * Sets the alpha component.
     *
     * @param alpha
     *            The alpha component to set
     */

    public void setAlpha(final float alpha)
    {
        this.alpha = alpha;
    }
}
