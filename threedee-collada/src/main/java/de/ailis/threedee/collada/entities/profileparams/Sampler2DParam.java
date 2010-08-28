/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities.profileparams;

import de.ailis.threedee.collada.entities.Filter;
import de.ailis.threedee.collada.entities.ProfileParam;


/**
 * Sampler2D profile parameter
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Sampler2DParam extends ProfileParam
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The source */
    private String source;

    /** The minimize filter */
    private Filter minFilter;

    /** The magnification filter */
    private Filter magFilter;


    /**
     * Constructor
     *
     * @param id
     *            The parameter id
     */

    public Sampler2DParam(final String id)
    {
        super(id);
    }


    /**
     * Returns the source.
     *
     * @return The source
     */

    public String getSource()
    {
        return this.source;
    }


    /**
     * Sets the source.
     *
     * @param source
     *            The source to set
     */

    public void setSource(final String source)
    {
        this.source = source;
    }


    /**
     * Returns the minFilter.
     *
     * @return The minFilter
     */

    public Filter getMinFilter()
    {
        return this.minFilter;
    }


    /**
     * Sets the minFilter.
     *
     * @param minFilter
     *            The minFilter to set
     */

    public void setMinFilter(final Filter minFilter)
    {
        this.minFilter = minFilter;
    }


    /**
     * Returns the magFilter.
     *
     * @return The magFilter
     */

    public Filter getMagFilter()
    {
        return this.magFilter;
    }


    /**
     * Sets the magFilter.
     *
     * @param magFilter
     *            The magFilter to set
     */

    public void setMagFilter(final Filter magFilter)
    {
        this.magFilter = magFilter;
    }
}
