/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene.light;

import java.awt.Color;


/**
 * An ambient light.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class PointLight implements Light
{
    /** The light color */
    private Color color = Color.WHITE;


    /**
     * Constructs a new point light with the default color.
     */

    public PointLight()
    {
        // Empty
    }


    /**
     * Constructs a new point light with the specified color.
     * 
     * @param color
     *            The color to set
     */

    public PointLight(final Color color)
    {
        setColor(color);
    }


    /**
     * Sets the light color.
     * 
     * @param color
     *            The light color to set
     */

    public void setColor(final Color color)
    {
        if (color == null)
            throw new IllegalArgumentException("color must not be null");
        this.color = color;
    }


    /**
     * Returns the light color.
     * 
     * @return The light color
     */

    public Color getColor()
    {
        return this.color;
    }


    /**
     * Returns the specified component value of the light color. 0=Red, 1=Green,
     * 2=Blue.
     * 
     * @param i
     *            The component
     * @return The color value of this component
     */

    public float getColorComponent(final int i)
    {
        return this.color.getRGBColorComponents(new float[3])[i];
    }
}
