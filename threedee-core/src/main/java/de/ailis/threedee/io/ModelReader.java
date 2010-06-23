/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.io;

import java.io.IOException;
import java.io.InputStream;

import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.scene.Model;


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
     * Reads a model from the specified resource.
     *
     * @param filename
     *            The filename of the model to read
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
     * @return The model scene
     * @throws IOException
     *             When read fails
     */

    public abstract Model read(final InputStream stream) throws IOException;
}
