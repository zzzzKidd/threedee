/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

import de.ailis.threedee.assets.Asset;
import de.ailis.threedee.exceptions.AssetIOException;
import de.ailis.threedee.io.StreamWriter;


/**
 * Base class for all TDB writers.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The asset type
 */

public abstract class TDBWriter<T extends Asset> extends AssetWriter
{
    /** The stream reader */
    protected StreamWriter writer;

    /** The file version. */
    protected byte version;

    /** The byte order of the model file */
    private final ByteOrder byteOrder = ByteOrder.nativeOrder();


    /**
     * Constructor
     *
     * @param version
     *            The file version.
     */

    public TDBWriter(final byte version)
    {
        super();
        this.version = version;
    }


    /**
     * Writes the asset.
     *
     * @param asset
     *            The asset to write
     * @param stream
     *            The stream to which to write the asset.
     * @throws AssetIOException
     *             When write fails.
     */

    public void write(final T asset, final OutputStream stream)
        throws AssetIOException
    {
        try
        {
            this.writer = new StreamWriter(stream);
            this.writer.setByteOrder(this.byteOrder);
            writeHeader();
            writeVersion();
            writeFileFlags();
            writeAsset(asset);
        }
        catch (final IOException e)
        {
            throw new AssetIOException(e.toString(), e);
        }
    }


    /**
     * Writes TDM header
     *
     * @throws IOException
     *             When write fails
     */

    private void writeHeader() throws IOException
    {
        this.writer.writeString("TDB");
    }


    /**
     * Writes file flags.
     *
     * @throws IOException
     *             When write fails
     */

    private void writeFileFlags() throws IOException
    {
        int flags = 0;
        flags |= this.writer.getByteOrder() == ByteOrder.BIG_ENDIAN ? 1 : 0;
        this.writer.writeByte((byte) flags);
    }


    /**
     * Writes TDM version.
     *
     * @throws IOException
     *             When write fails
     */

    private void writeVersion() throws IOException
    {
        this.writer.writeByte(this.version);
    }


    /**
     * Writes the asset data.
     *
     * @param asset
     *            The asset data to write.
     * @throws IOException
     *             When write fails
     */

    protected abstract void writeAsset(T asset) throws IOException;
}
