/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.net.URI;


/**
 * A data accessor.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Accessor
{
    /** The source */
    private URI source;

    /** The count */
    private int count;

    /** The parameters */
    private final Params params = new Params();


    /**
     * Constructor.
     *
     * @param source
     *            The source
     * @param count
     *            The count
     */

    public Accessor(final URI source, final int count)
    {
        setSource(source);
        setCount(count);
    }


    /**
     * Returns the source.
     *
     * @return The source
     */

    public URI getSource()
    {
        return this.source;
    }


    /**
     * Sets the source.
     *
     * @param source
     *            The source to set
     */

    public void setSource(final URI source)
    {
        if (source == null)
            throw new IllegalArgumentException("source must not be null");
        this.source = source;
    }


    /**
     * Returns the parameters.
     *
     * @return The parameters
     */

    public Params getParams()
    {
        return this.params;
    }


    /**
     * Returns the count.
     *
     * @return The count
     */

    public int getCount()
    {
        return this.count;
    }


    /**
     * Sets the count.
     *
     * @param count
     *            The count to set
     */

    public void setCount(final int count)
    {
        this.count = count;
    }
}
