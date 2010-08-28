/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.net.URI;


/**
 * A channel
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Channel
{
    /** The source */
    private URI source;

    /** The target */
    private String target;


    /**
     * Constructor.
     *
     * @param source
     *            The source
     * @param target
     *            The target
     */

    public Channel(final URI source, final String target)
    {
        setSource(source);
        setTarget(target);
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
     * Returns the target.
     *
     * @return The target
     */

    public String getTarget()
    {
        return this.target;
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
     * Sets the target.
     *
     * @param target
     *            The target to set
     */

    public void setTarget(final String target)
    {
        if (target == null)
            throw new IllegalArgumentException("target must not be null");
        this.target = target;
    }
}
