/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model.reader;

import java.io.IOException;
import java.io.InputStream;

import de.ailis.threedee.exceptions.ReaderException;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.model.Model;


/**
 * Abstract model reader providing the base functionality a model reader
 * must have.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class ModelReader
{
    /** The resource provider */
    protected ResourceProvider resourceProvider;

    /**
     * Creates a new model reader.
     *
     * @param resourceProvider
     *            The resource provider to use
     */

    public ModelReader(final ResourceProvider resourceProvider)
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

    public static ModelReader forFile(final String filename,
            final ResourceProvider resourceProvider)
    {
        final String fn = filename.toLowerCase();
        if (fn.endsWith(".obj"))
            return new ObjReader(resourceProvider);
        else if (fn.endsWith(".tdm"))
            return new TDMReader(resourceProvider);
        else
            throw new ReaderException("Unknown file format: " + filename);
    }


    /**
     * Reads a model.
     *
     * @param filename
     *            The filename of the model
     * @param resourceProvider
     *            The resource provider
     * @return The loaded model
     * @throws IOException
     *             When model could not be loaded
     */

    public static Model read(final String filename,
            final ResourceProvider resourceProvider) throws IOException
    {
        return forFile(filename, resourceProvider).read(filename);
    }


    /**
     * Reads a model from the specified resource.
     *
     * @param filename
     *            The filename of model to read
     * @return The loaded model
     * @throws IOException
     *             When read fails
     */

    public Model read(final String filename) throws IOException
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
     * Reads a model from the specified stream.
     *
     * @param stream
     *            The stream from which to load the model
     * @return The loaded model
     * @throws IOException
     *             When read fails
     */

    public abstract Model read(final InputStream stream) throws IOException;
}
