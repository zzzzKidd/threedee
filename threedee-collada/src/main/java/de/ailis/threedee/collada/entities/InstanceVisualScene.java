/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;
import java.net.URI;


/**
 * Instance visual scene
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class InstanceVisualScene implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The URL of the visual scene to instantiate */
    private URI url;


    /**
     * Constructs a new instance visual scene.
     *
     * @param url
     *            The URL of the visual scene to instantiate. Must not be null.
     */

    public InstanceVisualScene(final URI url)
    {
        setURL(url);
    }


    /**
     * Sets the url of the visual scene to instantiate.
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
     * Returns the URL of the visual scene to instantiate.
     *
     * @return The URL. Never null
     */

    public URI getURL()
    {
        return this.url;
    }
}
