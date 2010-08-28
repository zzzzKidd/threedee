/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A sampler
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColladaSampler implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The inputs */
    private final UnsharedInputs inputs = new UnsharedInputs();

    /** The sampler id */
    private String id;


    /**
     * Constructor.
     */

    public ColladaSampler()
    {
        this(null);
    }


    /**
     * Constructs a sampler with the specified id.
     *
     * @param id
     *            The sampler id
     */

    public ColladaSampler(final String id)
    {
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


    /**
     * Sets the id.
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        this.id = id;
    }


    /**
     * @see de.ailis.threedee.collada.support.Identifiable#getId()
     */

    @Override
    public String getId()
    {
        return this.id;
    }
}
