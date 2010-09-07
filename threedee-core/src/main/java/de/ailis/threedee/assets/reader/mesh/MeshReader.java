/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.reader.mesh;

import java.io.InputStream;

import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.assets.Mesh;
import de.ailis.threedee.exceptions.AssetIOException;


/**
 * Mesh reader interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface MeshReader
{
    /**
     * Reads and returns a mesh from the specified stream. The stream is NOT
     * closed after reading.
     *
     * @param stream
     *            The stream.
     * @param assets
     *            The assets for loading referenced data.
     * @return The mesh. Never null.
     * @throws AssetIOException
     *             When mesh could not be read
     */

    Mesh read(InputStream stream, Assets assets) throws AssetIOException;
}
