/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A light.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Light implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The effect id */
    private String id;

    /** The light color */
    private final Color color = new Color(1f, 1f, 1f, 1f);


    /**
     * Constructs a light without an id.
     */

    public Light()
    {
        this(null);
    }


    /**
     * Constructs a light with the given id.
     *
     * @param id
     *            The id
     */

    public Light(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the light id.
     *
     * @return The light id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the light id
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        this.id = id;
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
}
