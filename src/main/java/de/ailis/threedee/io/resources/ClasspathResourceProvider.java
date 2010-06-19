/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.io.resources;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Class-path stream provider.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ClasspathResourceProvider implements ResourceProvider
{
    /**
     * @see ResourceProvider#openForRead(String)
     */

    @Override
    public InputStream openForRead(final String filename)
            throws FileNotFoundException
    {
        final InputStream stream = getClass().getClassLoader()
                .getResourceAsStream(filename);
        if (stream == null) throw new FileNotFoundException(filename);
        return stream;
    }


    /**
     * @see ResourceProvider#openForWrite(String)
     */

    @Override
    public OutputStream openForWrite(final String filename)
    {
        throw new UnsupportedOperationException(
                "Can't write to classpath resources");
    }
}
