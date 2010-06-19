/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.ScopedIdentifiable;


/**
 * Base class for effect techniques.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class FxTechnique implements ScopedIdentifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The ID */
    private String id;

    /** The scoped ID */
    private String scopedId;


    /**
     * Constructs a new technique.
     *
     * @param scopedId
     *            The scoped ID
     */

    public FxTechnique(final String scopedId)
    {
        this.scopedId = scopedId;
    }


    /**
     * Returns the scoped id.
     *
     * @return The scoped id
     */

    @Override
    public String getScopedId()
    {
        return this.scopedId;
    }


    /**
     * Sets the scoped ID.
     *
     * @param scopedId
     *            The scoped ID to set. Must not be null.
     */

    public void setScopedId(final String scopedId)
    {
        if (scopedId == null)
            throw new IllegalArgumentException("scopedId must be set");
        this.scopedId = scopedId;
    }


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
