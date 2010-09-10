/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.ailis.threedee.exceptions.AssetIOException;


/**
 * Asset Provider which loads assets from a ZIP file.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ZipAssetProvider extends StructuredAssetProvider
{
    /** The ZIP file. */
    private final ZipFile zipFile;


    /**
     * Constructor.
     *
     * @param file
     *            The ZIP file.
     */

    public ZipAssetProvider(final File file)
    {
        this(file, null);
    }


    /**
     * Constructor.
     *
     * @param file
     *            The ZIP file.
     * @param baseDir
     *            The base directory inside the ZIP file.
     */

    public ZipAssetProvider(final File file, final String baseDir)
    {
        super(baseDir);
        try
        {
            this.zipFile = new ZipFile(file);
        }
        catch (final IOException e)
        {
            throw new AssetIOException(e.toString(), e);
        }
    }


    /**
     * Constructor.
     *
     * @param zipFile
     *            The ZIP file.
     */

    public ZipAssetProvider(final ZipFile zipFile)
    {
        this(zipFile, null);
    }

    /**
     * Constructor.
     *
     * @param zipFile
     *            The ZIP file.
     * @param baseDir
     *            The base directory inside the ZIP file.
     */

    public ZipAssetProvider(final ZipFile zipFile, final String baseDir)
    {
        super(baseDir);
        this.zipFile = zipFile;
    }


    /**
     * @see de.ailis.threedee.assets.StructuredAssetProvider#exists(java.lang.String)
     */

    @Override
    protected boolean exists(final String filename)
    {
        return this.zipFile.getEntry(filename) != null;
    }


    /**
     * @see de.ailis.threedee.assets.StructuredAssetProvider#openInputStream(java.lang.String)
     */

    @Override
    protected InputStream openInputStream(final String filename)
    {
        final ZipEntry entry = this.zipFile.getEntry(filename);
        if (entry == null) return null;
        try
        {
            return this.zipFile.getInputStream(entry);
        }
        catch (final IOException e)
        {
            throw new AssetIOException(e.toString(), e);
        }
    }
}
