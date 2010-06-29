/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;



/**
 * A ambient light.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaAmbientLight extends ColladaLight
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;


    /**
     * Constructs a ambient light without an id.
     */

    public ColladaAmbientLight()
    {
        this(null);
    }


    /**
     * Constructs a ambient light with the given id.
     *
     * @param id
     *            The id
     */

    public ColladaAmbientLight(final String id)
    {
        super(id);
    }
}