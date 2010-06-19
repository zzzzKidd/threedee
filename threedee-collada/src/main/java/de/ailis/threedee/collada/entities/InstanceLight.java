/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;
import java.net.URI;


/**
 * Instance light
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class InstanceLight implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The URL of the light to instantiate */
    private URI url;


    /**
     * Constructs a new instance light.
     *
     * @param url
     *            The URL of the light to instantiate. Must not be null.
     */

    public InstanceLight(final URI url)
    {
        setURL(url);
    }


    /**
     * Sets the url of the light to instantiate.
     *
     * @param url
     *            The url to set. Must not be null
     */

    public void setURL(final URI url)
    {
        if (url == null) throw new IllegalArgumentException("url must be set");
        this.url = url;
    }


    /**
     * Returns the URL of the light to instantiate.
     *
     * @return The URL. Never null
     */

    public URI getURL()
    {
        return this.url;
    }
}
