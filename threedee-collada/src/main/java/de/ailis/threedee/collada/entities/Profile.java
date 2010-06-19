/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A effect profile.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class Profile implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The id */
    private String id;


    /**
     * Returns the ID
     *
     * @return The ID
     */

    @Override
    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the ID.
     *
     * @param id
     *            The id to set. Null for removing the ID
     */

    public void setId(final String id)
    {
        this.id = id;
    }
}
