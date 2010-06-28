/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.net.URI;

import de.ailis.threedee.collada.support.Identifiable;


/**
 * A image.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Image implements Identifiable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The image id */
    private String id;

    /** The URI of the image to use */
    private URI uri;


    /**
     * Constructs an image without an id
     */

    public Image()
    {
        this(null);
    }


    /**
     * Constructs an image with the given id.
     *
     * @param id
     *            The id
     */

    public Image(final String id)
    {
        this.id = id;
    }


    /**
     * @see de.ailis.threedee.collada.support.Identifiable#getId()
     */

    @Override
    public String getId()
    {
        return this.id;
    }


    /**
     * Sets the image id
     *
     * @param id
     *            The id to set
     */

    public void setId(final String id)
    {
        this.id = id;
    }


    /**
     * Returns the URI of the image.
     *
     * @return URI
     */

    public URI getURI()
    {
        return this.uri;
    }


    /**
     * Sets the URI of the image.
     *
     * @param uri
     *            The URI to set
     */

    public void setURI(final URI uri)
    {
        this.uri = uri;
    }
}
