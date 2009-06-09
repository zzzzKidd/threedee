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
    private Color ambient;

    /** The emissive color */
    private Color emissive;


    /**
     * Constructs a new material with the specified properties.
     * 
     * @param ambient
     *            The ambient color
     */

    public Material(final Color ambient)
    {
        this(ambient, Color.BLACK);
    }


    /**
     * Constructs a new material with the specified properties.
     * 
     * @param ambient
     *            The ambient color
     * @param emissive
     *            The emissive color
     */

    public Material(final Color ambient, final Color emissive)
    {
        setAmbient(ambient);
        setEmissive(emissive);
    }


    /**
     * Constructs a new material with default properties.
     */

    public Material()
    {
        this(Color.WHITE);
    }


    /**
     * Returns the ambient color.
     * 
     * @return The ambient color
     */

    public Color getAmbient()
    {
        return this.ambient;
    }


    /**
     * Sets the ambient color.
     * 
     * @param ambient
     *            The ambient color to set
     */

    public void setAmbient(final Color ambient)
    {
        if (ambient == null)
            throw new IllegalArgumentException("ambient must not be null");
        this.ambient = ambient;
    }


    /**
     * Returns the emissive color.
     * 
     * @return The emissive color
     */

    public Color getEmissive()
    {
        return this.emissive;
    }


    /**
     * Sets the emissive color.
     * 
     * @param emissive
     *            The emissive color to set
     */

    public void setEmissive(final Color emissive)
    {
        if (emissive == null)
            throw new IllegalArgumentException("emissive must not be null");
        this.emissive = emissive;
    }
}
