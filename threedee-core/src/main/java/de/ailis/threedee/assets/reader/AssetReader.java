/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.reader;


/**
 * Base class for all asset readers.
 *
 * @author Klaus Reimer (k@ailis.de
 */

public abstract class AssetReader
{
    /** The ID for the read asset. */
    protected final String id;


    /**
     * Constructor.
     *
     * @param id
     *            The ID for the read asset.
     */

    public AssetReader(final String id)
    {
        this.id = id;
    }
}
