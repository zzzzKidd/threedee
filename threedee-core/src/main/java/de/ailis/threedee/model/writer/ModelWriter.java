/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.model.writer;

import java.io.IOException;
import java.io.OutputStream;

import de.ailis.threedee.exceptions.ReaderException;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.model.Model;


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
     * Creates a new model writer.
     *
     * @param resourceProvider
     *            The resource provider to use
     */

    public ModelWriter(final ResourceProvider resourceProvider)
    {
        this.resourceProvider = resourceProvider;
    }


    /**
     * Writes a ThreeDee model to the specified file.
     *
     * @param model
     *            The model to write
     * @param filename
     *            The filename of the ThreeDee model file to write
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
     * Writes a ThreeDee model to the specified stream.
     *
     * @param model
     *            The model to write
     * @param stream
     *            The stream to write to
     * @throws IOException
     *             When write fails
     */

    public abstract void write(final Model model, final OutputStream stream)
            throws IOException;


    /**
     * Returns a model reader which can read the specified file.
     *
     * @param filename
     *            The filename
     * @param resourceProvider
     *            The resource provider
     * @return The model reader
     */

    public static ModelWriter forFile(final String filename,
            final ResourceProvider resourceProvider)
    {
        final String fn = filename.toLowerCase();
        if (fn.endsWith(".tdm"))
            return new TDMWriter(resourceProvider);
        else
            throw new ReaderException("Unknown file format: " + filename);
    }
}
