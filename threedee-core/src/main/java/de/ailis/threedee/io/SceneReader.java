/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.io;

import java.io.IOException;
import java.io.InputStream;

import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.scene.Scene;


/**
 * Abstract scene reader providing the base functionality a scene reader
 * must have.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class SceneReader
{
    /** The resource provider */
    protected ResourceProvider resourceProvider;


    /**
     * Creates a new model reader.
     *
     * @param resourceProvider
     *            The resource provider to use
     */

    public SceneReader(final ResourceProvider resourceProvider)
    {
        this.resourceProvider = resourceProvider;
    }


    /**
     * Reads a scene from the specified resource.
     *
     * @param filename
     *            The filename of the scene to read
     * @return The loaded scene
     * @throws IOException
     *             When read fails
     */

    public Scene read(final String filename) throws IOException
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
     * Reads a scene from the specified stream.
     *
     * @param stream
     *            The stream from which to load the scene
     * @return The loaded scene
     * @throws IOException
     *             When read fails
     */

    public abstract Scene read(final InputStream stream) throws IOException;
}
