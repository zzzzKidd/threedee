/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;



/**
 * A light.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class PointLight extends Light
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;


    /**
     * Constructs a point light without an id.
     */

    public PointLight()
    {
        this(null);
    }


    /**
     * Constructs a point light with the given id.
     *
     * @param id
     *            The id
     */

    public PointLight(final String id)
    {
        super(id);
    }
}
