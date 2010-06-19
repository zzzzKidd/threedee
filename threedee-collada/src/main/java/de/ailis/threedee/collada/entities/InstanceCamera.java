/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;
import java.net.URI;


/**
 * Instance camera
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class InstanceCamera implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The URL of the camera to instantiate */
    private URI url;


    /**
     * Constructs a new instance camera.
     *
     * @param url
     *            The URL of the camera to instantiate. Must not be null.
     */

    public InstanceCamera(final URI url)
    {
        setURL(url);
    }


    /**
     * Sets the url of the camera to instantiate.
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
     * Returns the URL of the camera to instantiate.
     *
     * @return The URL. Never null
     */

    public URI getURL()
    {
        return this.url;
    }
}
