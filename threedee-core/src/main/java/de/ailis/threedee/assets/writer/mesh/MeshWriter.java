/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.writer.mesh;

import java.io.OutputStream;

import de.ailis.threedee.assets.Mesh;
import de.ailis.threedee.exceptions.AssetIOException;


/**
 * Mesh writer interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface MeshWriter
{
    /**
     * Writes a mesh to the specified stream. The stream is NOT closed after
     * writing.
     *
     * @param mesh
     *            The mesh to write
     * @param stream
     *            The stream
     * @throws AssetIOException
     *             When mesh could not be written
     */

    void write(Mesh mesh, OutputStream stream) throws AssetIOException;
}
