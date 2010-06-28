/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A camera.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaCamera implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The camera id */
    private String id;

    /** The optic */
    private Optic optic;


    /**
     * Constructs an camera without an id
     */

    public ColladaCamera()
    {
        this(null);
    }


    /**
     * Constructs an camera with the given id.
     *
     * @param id
     *            The id
     */

    public ColladaCamera(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the camera id.
     *
     * @return The camera id
     */

    @Override
    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the camera id
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the optic.
     *
     * @return The optic
     */

    public Optic getOptic()
    {
        return this.optic;
    }


    /**
     * Sets the optic.
     *
     * @param optic
     *            The optic to set
     */

    public void setOptic(final Optic optic)
    {
        this.optic = optic;
    }
}
