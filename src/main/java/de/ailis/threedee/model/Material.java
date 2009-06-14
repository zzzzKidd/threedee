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

    /** The diffuse color */
    private Color diffuse;


    /**
     * Constructs a new material with a single color which is used for all color
     * parts except the emissive color (Which is set to black).
     * 
     * @param color
     *            The general color
     */

    public Material(final Color color)
    {
        this(color, color, Color.BLACK);
    }


    /**
     * Constructs a new material with the specified properties.
     * 
     * @param ambient
     *            The ambient color
     * @param diffuse
     *            The diffuse color
     * @param emissive
     *            The emissive color
     */

    public Material(final Color ambient, final Color diffuse,
        final Color emissive)
    {
        setAmbient(ambient);
        setDiffuse(diffuse);
        setEmissive(emissive);
    }

    
    /**
     * Constructs a new material by copying the data from the specified
     * material.
     * 
     * @param material
     *            The material from which data is copied
     */
    
    public Material(final Material material)
    {
        this.ambient = material.ambient;
        this.diffuse = material.diffuse;
        this.emissive = material.emissive;
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


    /**
     * Returns the diffuse color.
     * 
     * @return The diffuse color
     */

    public Color getDiffuse()
    {
        return this.diffuse;
    }


    /**
     * Sets the diffuse color.
     * 
     * @param diffuse
     *            The diffuse color to set
     */

    public void setDiffuse(final Color diffuse)
    {
        if (diffuse == null)
            throw new IllegalArgumentException("diffuse must not be null");
        this.diffuse = diffuse;
    }
}
