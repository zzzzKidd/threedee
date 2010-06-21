/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;



/**
 * A directional light.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaDirectionalLight extends ColladaLight
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;


    /**
     * Constructs a directional light without an id.
     */

    public ColladaDirectionalLight()
    {
        this(null);
    }


    /**
     * Constructs a directional light with the given id.
     *
     * @param id
     *            The id
     */

    public ColladaDirectionalLight(final String id)
    {
        super(id);
    }
}
