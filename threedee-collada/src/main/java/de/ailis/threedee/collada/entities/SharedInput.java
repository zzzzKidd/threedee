/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;
import java.net.URI;


/**
 * A shared input
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SharedInput implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The semantic */
    private Semantic semantic;

    /** The input source */
    private URI source;

    /** The offset into the references data */
    int offset;

    /** The set number */
    private Integer set;


    /**
     * Constructs a new unshared input
     *
     * @param semantic
     *            The semantic. Must not be null
     * @param offset
     *            The offset
     * @param source
     *            The source. Must not be null
     */

    public SharedInput(final Semantic semantic, final int offset,
            final URI source)
    {
        setSemantic(semantic);
        setOffset(offset);
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
     * Sets the offset.
     *
     * @param offset
     *            The offset to set
     */

    public void setOffset(final int offset)
    {
        this.offset = offset;
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


    /**
     * Returns the offset.
     *
     * @return The offset
     */

    public int getOffset()
    {
        return this.offset;
    }


    /**
     * Returns the set.
     *
     * @return The set. May be null if no set is specified
     */

    public Integer getSet()
    {
        return this.set;
    }


    /**
     * Sets the set.
     *
     * @param set
     *            The set to set. May be null for unspecified
     */

    public void setSet(final Integer set)
    {
        this.set = set;
    }
}
