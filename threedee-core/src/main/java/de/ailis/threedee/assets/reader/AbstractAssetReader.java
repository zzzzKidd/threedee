/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.reader;

import de.ailis.threedee.assets.Asset;
import de.ailis.threedee.assets.AssetFormat;


/**
 * Base class for all asset readers.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The asset type
 */

public abstract class AbstractAssetReader<T extends Asset> implements
    AssetReader<T>
{
    /** The ID for the read asset. */
    protected final String id;

    /** The asset format this read can read. */
    protected final AssetFormat format;


    /**
     * Constructor.
     *
     * @param id
     *            The ID for the read asset.
     * @param format
     *            The asset format this reader can read.
     */

    public AbstractAssetReader(final String id, final AssetFormat format)
    {
        this.id = id;
        this.format = format;
    }
}
