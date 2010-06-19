/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.reader;

import java.io.IOException;
import java.io.InputStream;

import de.ailis.threedee.entities.Asset;
import de.ailis.threedee.exceptions.ReaderException;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.reader.collada.ColladaAssetReader;


/**
 * Abstract asset reader providing the base functionality an asset reader
 * must have.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class AssetReader
{
    /** The resource provider */
    protected ResourceProvider resourceProvider;


    /**
     * Creates a new model reader.
     *
     * @param resourceProvider
     *            The resource provider to use
     */

    public AssetReader(final ResourceProvider resourceProvider)
    {
        this.resourceProvider = resourceProvider;
    }


    /**
     * Returns a model reader which can read the specified file.
     *
     * @param filename
     *            The filename
     * @param resourceProvider
     *            The resource provider
     * @return The model reader
     */

    public static AssetReader forFile(final String filename,
            final ResourceProvider resourceProvider)
    {
        final String fn = filename.toLowerCase();
        if (fn.endsWith(".dae"))
            return new ColladaAssetReader(resourceProvider);
        else
            throw new ReaderException("Unknown file format: " + filename);
    }


    /**
     * Reads an asset.
     *
     * @param filename
     *            The filename of the asset
     * @param resourceProvider
     *            The resource provider
     * @return The loaded asset
     * @throws IOException
     *             When asset could not be loaded
     */

    public static Asset read(final String filename,
            final ResourceProvider resourceProvider) throws IOException
    {
        return forFile(filename, resourceProvider).read(filename);
    }


    /**
     * Reads a asset from the specified resource.
     *
     * @param filename
     *            The filename of asset to read
     * @return The loaded asset
     * @throws IOException
     *             When read fails
     */

    public Asset read(final String filename) throws IOException
    {
        final InputStream stream = this.resourceProvider.openForRead(filename);
        try
        {
            return read(stream);
        }
        finally
        {
            stream.close();
        }
    }


    /**
     * Reads a asset from the specified stream.
     *
     * @param stream
     *            The stream from which to load the asset
     * @return The loaded asset
     * @throws IOException
     *             When read fails
     */

    public abstract Asset read(final InputStream stream) throws IOException;
}
