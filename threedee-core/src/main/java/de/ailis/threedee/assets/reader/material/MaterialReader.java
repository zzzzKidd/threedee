/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.reader.material;

import java.io.InputStream;

import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.assets.Material;
import de.ailis.threedee.exceptions.AssetIOException;


/**
 * Material reader interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface MaterialReader
{
    /**
     * Reads and returns a material from the specified stream. The stream is NOT
     * closed after reading.
     *
     * @param stream
     *            The stream.
     * @param assets
     *            Assets for referencing textures.
     * @return The material. Never null.
     * @throws AssetIOException
     *             When material could not be read
     */

    Material read(InputStream stream, Assets assets) throws AssetIOException;
}
