/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets.reader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

import de.ailis.threedee.assets.Asset;
import de.ailis.threedee.assets.Assets;
import de.ailis.threedee.exceptions.AssetIOException;
import de.ailis.threedee.exceptions.ReaderException;
import de.ailis.threedee.io.StreamReader;


/**
 * Base class for all TDB readers.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The asset type
 */

public abstract class TDBReader<T extends Asset> extends AssetReader
{
    /** The stream reader */
    protected StreamReader reader;

    /** The file version. */
    protected byte version;

    /** The minimum supported file format version */
    private final byte minVersion;

    /** The maximum supported file format version */
    private final byte maxVersion;

    /** The byte order of the model file */
    private ByteOrder byteOrder;


    /**
     * Constructor
     *
     * @param id
     *            The ID for the read asset.
     * @param minVersion
     *            The minimum supported file version.
     * @param maxVersion
     *            The maximum supported file version.
     */

    public TDBReader(final String id, final byte minVersion,
        final byte maxVersion)
    {
        super(id);
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }


    /**
     * Reads the asset.
     *
     * @param stream
     *            The stream from which to read the asset.
     * @param assets
     *            Assets for loading referenced data.
     * @return The read asset.
     * @throws AssetIOException
     *             When reading fails.
     */

    public T read(final InputStream stream, final Assets assets)
        throws AssetIOException
    {
        try
        {
            this.reader = new StreamReader(stream);
            readHeader();
            readVersion();
            readFileFlags();
            return readAsset(assets);
        }
        catch (final IOException e)
        {
            throw new AssetIOException(e.toString(), e);
        }
    }


    /**
     * Reads and checks the TDB header. Throws an exception if file is not a TDB
     * file.
     *
     * @throws IOException
     *             When header could not be read or file is not a TDM file
     */

    private void readHeader() throws IOException
    {
        final String header = this.reader.readString(3);
        if (!header.equals("TDB"))
            throw new ReaderException("File is not a ThreeDee object");
    }


    /**
     * Reads the flags of the model file.
     *
     * Bit 0: Clear = Little-endian, Set = Big-endian
     *
     * @throws IOException
     *             When flags could not be read
     */

    private void readFileFlags() throws IOException
    {
        final byte flags = (byte) this.reader.readByte();
        this.byteOrder = ((flags & 1) == 0) ? ByteOrder.LITTLE_ENDIAN
                : ByteOrder.BIG_ENDIAN;
        this.reader.setByteOrder(this.byteOrder);
    }


    /**
     * Reads and checks the version of the TDM file.
     *
     * @throws IOException
     *             When version could not be read or version is invalid for this
     *             reader.
     */

    private void readVersion() throws IOException
    {
        final byte version = (byte) this.reader.readByte();
        if (version < this.minVersion)
            throw new ReaderException("File format version is too old");
        if (version > this.maxVersion)
            throw new ReaderException("File format version is too new");
    }


    /**
     * Reads the asset data.
     *
     * @param assets
     *            Assets for loading referenced data.
     * @return The read asset.
     * @throws IOException
     *             When read fails
     */

    protected abstract T readAsset(Assets assets) throws IOException;
}
