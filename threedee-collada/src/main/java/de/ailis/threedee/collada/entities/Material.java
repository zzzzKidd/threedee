/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.net.URI;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A material.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Material implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The material id */
    private String id;

    /** The URI of the image to use */
    private URI effectURI;


    /**
     * Constructs a material without an id
     */

    public Material()
    {
        this(null);
    }


    /**
     * Constructs a material with the given id.
     *
     * @param id
     *            The id
     */

    public Material(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the material id.
     *
     * @return The material id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the material id
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the effect URI of the material.
     *
     * @return The effect URI
     */

    public URI getEffectURI()
    {
        return this.effectURI;
    }


    /**
     * Sets the URI of the material.
     *
     * @param effectURI
     *            The effect URI to set
     */

    public void setEffectURI(final URI effectURI)
    {
        this.effectURI = effectURI;
    }
}
