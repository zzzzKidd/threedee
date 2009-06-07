/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.model;

import java.awt.Color;
import java.io.Serializable;


/**
 * A material
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Material implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = -8995477823163477454L;
    
    /** The default material */
    public static final Material DEFAULT = new Material(Color.WHITE);

    /** The material color */
    private Color color;


    /**
     * Constructs a new material with the specified properties
     * 
     * @param color
     *            The color
     */

    public Material(final Color color)
    {
        this.color = color;
    }


    /**
     * Constructs a new material with default properties.
     */

    public Material()
    {
        this(Color.WHITE);
    }


    /**
     * Returns the color.
     * 
     * @return The color
     */

    public Color getColor()
    {
        return this.color;
    }


    /**
     * Sets the color.
     * 
     * @param color
     *            The color to set
     */

    public void setColor(final Color color)
    {
        this.color = color;
    }
}
