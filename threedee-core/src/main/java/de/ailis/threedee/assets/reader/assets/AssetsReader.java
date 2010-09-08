/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.reader.assets;

import java.io.InputStream;

import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.exceptions.AssetIOException;


/**
 * Assets reader interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface AssetsReader
{
    /**
     * Reads assets from the specified stream and appends them to the specified
     * assets. The stream is NOT closed after reading.
     *
     * @param stream
     *            The stream.
     * @param assets
     *            The assets to append to
     * @return Reference to the same assets as already specified
     * @throws AssetIOException
     *             When assets could not be read
     */

    Assets read(InputStream stream, Assets assets) throws AssetIOException;
}
