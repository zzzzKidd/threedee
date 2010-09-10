/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import de.ailis.threedee.exceptions.AssetIOException;


/**
 * Asset Provider which loads assets from files.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class FileAssetProvider extends StructuredAssetProvider
{
    /**
     * Constructor.
     *
     * @param baseDir
     *            The base directory.
     */

    public FileAssetProvider(final File baseDir)
    {
        super(baseDir.getPath());
    }


    /**
     * @see de.ailis.threedee.assets.StructuredAssetProvider#exists(java.lang.String)
     */

    @Override
    public boolean exists(final String filename)
    {
        if (new File(filename + ".gz").exists()) return true;
        if (new File(filename).exists()) return true;
        return false;
    }


    /**
     * @see de.ailis.threedee.assets.StructuredAssetProvider#openInputStream(java.lang.String)
     */

    @Override
    public InputStream openInputStream(final String filename)
    {
        // Try to load GZIP compressed file.
        File file = new File(filename + ".gz");
        if (file.exists())
        {
            try
            {
                return new GZIPInputStream(new FileInputStream(
                    file));
            }
            catch (final FileNotFoundException e)
            {
                // Ignored
            }
            catch (final IOException e)
            {
                throw new AssetIOException(e.toString(), e);
            }
        }

        // Try to load plain file
        file = new File(filename);
        if (file.exists())
        {
            try
            {
                return new FileInputStream(file);
            }
            catch (final FileNotFoundException e)
            {
                // Ignored
            }
        }

        // Nothing found.
        return null;
    }
}
