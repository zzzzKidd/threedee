/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.io;

import java.io.IOException;
import java.io.OutputStream;

import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.scene.Model;


/**
 * Abstract model writer providing the base functionality a model writer
 * must have.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class ModelWriter
{
    /** The resource provider */
    protected ResourceProvider resourceProvider;


    /**
     * Creates a new model reader.
     *
     * @param resourceProvider
     *            The resource provider to use
     */

    public ModelWriter(final ResourceProvider resourceProvider)
    {
        this.resourceProvider = resourceProvider;
    }


    /**
     * Writes a model to the specified resource.
     *
     * @param model
     *            The model to write
     * @param filename
     *            The filename of the file to write the model to
     * @throws IOException
     *             When write fails
     */

    public void write(final Model model, final String filename)
            throws IOException
    {
        final OutputStream stream = this.resourceProvider
                .openForWrite(filename);
        try
        {
            write(model, stream);
        }
        finally
        {
            stream.close();
        }
    }


    /**
     * Writes a model to the specified stream.
     *
     * @param model
     *            The model to write
     * @param stream
     *            The stream to write the model to
     * @throws IOException
     *             When write fails
     */

    public abstract void write(Model model, final OutputStream stream)
            throws IOException;
}
