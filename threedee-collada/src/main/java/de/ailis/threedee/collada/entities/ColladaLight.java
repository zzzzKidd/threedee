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

public class ColladaLight implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The effect id */
    private String id;

    /** The light color */
    private final ColladaColor color = new ColladaColor(1f, 1f, 1f, 1f);


    /**
     * Constructs a light without an id.
     */

    public ColladaLight()
    {
        this(null);
    }


    /**
     * Constructs a light with the given id.
     *
     * @param id
     *            The id
     */

    public ColladaLight(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the light id.
     *
     * @return The light id
     */

    @Override
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

    public ColladaColor getColor()
    {
        return this.color;
    }
}
