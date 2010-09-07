/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;


/**
 * Base class for all assets.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class Asset
{
    /** The asset ID. */
    protected final String id;

    /** The asset type. */
    protected final AssetType type;


    /**
     * Constructor.
     *
     * @param id
     *            The asset ID.
     * @param type
     *            The asset type.
     */

    public Asset(final String id, final AssetType type)
    {
        if (id == null)
            throw new IllegalArgumentException("id must not be null");
        if (type == null)
            throw new IllegalArgumentException("type must not be null");
        this.type = type;
        this.id = id;
    }


    /**
     * Returns the asset ID.
     *
     * @return The asset ID. Never null.
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Returns the asset type.
     *
     * @return The asset type. Never null.
     */

    public AssetType getType()
    {
        return this.type;
    }
}
