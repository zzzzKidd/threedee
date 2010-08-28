/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * Profile parameter
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class ProfileParam implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;


    /** The parameter id */
    private String id;


    /**
     * Constructs a new profile parameter.
     *
     * @param id
     *            The parameter id
     */

    public ProfileParam(final String id)
    {
        setId(id);
    }


    /**
     * Returns the id.
     *
     * @return The id
     */

    @Override
    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the id.
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        if (id == null)
            throw new IllegalArgumentException("id must not be null");
        this.id = id;
    }
}
