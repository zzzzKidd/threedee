/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.io.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Interface for stream providers.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface ResourceProvider
{
    /**
     * Creates an output stream for the specified filename.
     *
     * @param filename
     *            The filename of the resource to open for writing.
     * @return The input stream to write the resource
     * @throws IOException
     *             When file could not be opened
     */

    public OutputStream openForWrite(String filename)
            throws IOException;


    /**
     * Creates an input stream for the specified filename.
     *
     * @param filename
     *            The filename of the resource to open for reading.
     * @return The input stream to read the resource
     * @throws IOException
     *             When file could not be opened
     */

    public InputStream openForRead(String filename)
            throws IOException;
}
