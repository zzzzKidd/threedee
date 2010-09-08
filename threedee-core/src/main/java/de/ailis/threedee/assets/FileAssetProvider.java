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
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import de.ailis.threedee.exceptions.AssetIOException;
import de.ailis.threedee.exceptions.AssetNotFoundException;


/**
 * Asset Provider which loads assets from files.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class FileAssetProvider implements AssetProvider
{
    /** The asset type directory mapping. */
    private static Map<AssetType, String> directories;

    /** The base directory. */
    private final File baseDir;

    {
        directories = new HashMap<AssetType, String>();
        directories.put(AssetType.TEXTURE, "/textures/");
        directories.put(AssetType.MATERIAL, "/materials/");
        directories.put(AssetType.ANIMATION, "/animations/");
        directories.put(AssetType.MESH, "/meshes/");
        directories.put(AssetType.SCENE, "/scenes/");
        directories.put(AssetType.ASSETS, "/assets/");
    }


    /**
     * Constructor.
     *
     * @param baseDir
     *            The base directory.
     */

    public FileAssetProvider(final File baseDir)
    {
        this.baseDir = baseDir;
    }


    /**
     * @see AssetProvider#exists(AssetType, String)
     */

    @Override
    public boolean exists(final AssetType type, final String id)
    {
        final String dir = directories.get(type);
        for (final AssetFormat format : type.getFormats())
        {
            for (final String extension : format.getExtensions())
            {
                final String filename = dir + id + extension;
                if (new File(this.baseDir, filename + ".gz").exists()) return true;
                if (new File(this.baseDir, filename).exists()) return true;
            }
        }
        return false;
    }


    /**
     * @see AssetProvider#openInputStream(AssetType, String)
     */

    @Override
    public AssetInputStream openInputStream(final AssetType type,
        final String id)
    {
        final String dir = directories.get(type);
        for (final AssetFormat format : type.getFormats())
        {
            for (final String extension : format.getExtensions())
            {
                final String filename = dir + id + extension;

                // Try to load GZIP compressed file.
                File file = new File(this.baseDir, filename + ".gz");
                if (file.exists())
                {
                    InputStream stream;
                    try
                    {
                        stream = new GZIPInputStream(new FileInputStream(file));
                        return new AssetInputStream(format, stream);
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
                file = new File(this.baseDir, filename);
                if (file.exists())
                {
                    InputStream stream;
                    try
                    {
                        stream = new FileInputStream(file);
                        return new AssetInputStream(format, stream);
                    }
                    catch (final FileNotFoundException e)
                    {
                        // Ignored
                    }
                }
            }
        }
        throw new AssetNotFoundException("Asset not found: " + type + " / "
            + id);
    }
}
