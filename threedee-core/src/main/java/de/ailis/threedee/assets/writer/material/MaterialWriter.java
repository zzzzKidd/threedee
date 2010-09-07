/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.writer.material;

import java.io.OutputStream;

import de.ailis.threedee.assets.Material;
import de.ailis.threedee.exceptions.AssetIOException;


/**
 * Material writer interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface MaterialWriter
{
    /**
     * Writes a material to the specified stream. The stream is NOT closed after
     * writing.
     *
     * @param material
     *            The material to write
     * @param stream
     *            The stream
     * @throws AssetIOException
     *             When material could not be written
     */

    void write(Material material, OutputStream stream) throws AssetIOException;
}
