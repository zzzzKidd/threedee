/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;
import java.net.URI;


/**
 * Unshared input
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class UnsharedInput implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The semantic */
    private Semantic semantic;

    /** The input source */
    private URI source;


    /**
     * Constructs a new unshared input
     *
     * @param semantic
     *            The semantic. Must not be null
     * @param source
     *            The source. Must not be null
     */

    public UnsharedInput(final Semantic semantic, final URI source)
    {
        setSemantic(semantic);
        setSource(source);
    }


    /**
     * Sets the semantic.
     *
     * @param semantic
     *            The semantic to set. Must not be null
     */

    public void setSemantic(final Semantic semantic)
    {
        if (semantic == null)
            throw new IllegalArgumentException("semantic must be set");
        this.semantic = semantic;
    }


    /**
     * Sets the source.
     *
     * @param source
     *            The source to set. Must not be null
     */

    public void setSource(final URI source)
    {
        if (source == null)
            throw new IllegalArgumentException("source must be set");
        this.source = source;
    }


    /**
     * Returns the source.
     *
     * @return The source. Never null
     */

    public URI getSource()
    {
        return this.source;
    }


    /**
     * Returns the semantic.
     *
     * @return The semantic. Never null
     */

    public Semantic getSemantic()
    {
        return this.semantic;
    }
}
