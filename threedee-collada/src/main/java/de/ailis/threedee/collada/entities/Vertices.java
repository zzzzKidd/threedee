/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * Vertices
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Vertices implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /** The vertices id */
    private String id;

    /** The inputs */
    private final UnsharedInputs inputs = new UnsharedInputs();



    /**
     * Constructs vertices with the given id.
     *
     * @param id
     *            The id
     */

    public Vertices(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the vertices id.
     *
     * @return The vertices id
     */

    @Override
    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the vertices id
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        if (id == null) throw new IllegalArgumentException("id must be set");
        this.id = id;
    }


    /**
     * Returns the inputs.
     *
     * @return The inputs
     */

    public UnsharedInputs getInputs()
    {
        return this.inputs;
    }
}
