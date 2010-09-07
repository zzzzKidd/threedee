/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;

import java.io.IOException;
import java.io.InputStream;


/**
 * Asset stream to read an asset from.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AssetInputStream extends InputStream
{
    /** The asset format. */
    private final AssetFormat format;

    /** The wrapped input stream. */
    private final InputStream stream;


    /**
     * Constructor.
     *
     * @param format
     *            The asset format.
     * @param stream
     *            The wrapped input stream.
     */

    public AssetInputStream(final AssetFormat format, final InputStream stream)
    {
        this.format = format;
        this.stream = stream;
    }

    /**
     * @see java.io.InputStream#read()
     */

    @Override
    public int read() throws IOException
    {
        return this.stream.read();
    }


    /**
     * @see java.io.InputStream#close()
     */

    @Override
    public void close() throws IOException
    {
        this.stream.close();
    }


    /**
     * Returns the asset format.
     *
     * @return The asset format. Never null.
     */

    public AssetFormat getFormat()
    {
        return this.format;
    }
}
