/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A effect.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Effect implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The effect id */
    private String id;

    /** The profiles */
    private final Profiles profiles = new Profiles();


    /**
     * Constructs a effect with the given id.
     *
     * @param id
     *            The id
     */

    public Effect(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the effect id.
     *
     * @return The effect id
     */

    @Override
    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the effect id
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
     * Returns the profiles of this effect.
     *
     * @return The profiles
     */

    public Profiles getProfiles()
    {
        return this.profiles;
    }
}
