/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;
import java.net.URI;


/**
 * Instance geometry
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class InstanceGeometry implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The URL of the geometry to instantiate */
    private URI url;

    /** The instance materials */
    private final InstanceMaterials instanceMaterials = new InstanceMaterials();


    /**
     * Constructs a new instance geometry.
     *
     * @param url
     *            The URL of the geometry to instantiate. Must not be null.
     */

    public InstanceGeometry(final URI url)
    {
        setURL(url);
    }


    /**
     * Sets the url of the geometry to instantiate.
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
     * Returns the URL of the geometry to instantiate.
     *
     * @return The URL. Never null
     */

    public URI getURL()
    {
        return this.url;
    }


    /**
     * Returns the instance materials.
     *
     * @return The instance materials
     */

    public InstanceMaterials getInstanceMaterials()
    {
        return this.instanceMaterials;
    }
}
