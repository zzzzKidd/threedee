/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.reader;

import java.io.InputStream;

import de.ailis.threedee.assets.Asset;
import de.ailis.threedee.assets.Assets;


/**
 * Asset reader interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The asset type
 */

public interface AssetReader<T extends Asset>
{
    /**
     * Reads the asset from the specified input stream and returns it.
     *
     * @param stream
     *            The stream from which to read the asset.
     * @param assets
     *            To read references assets from.
     * @return The read asset. Never null.
     */

    public T read(InputStream stream, Assets assets);
}
