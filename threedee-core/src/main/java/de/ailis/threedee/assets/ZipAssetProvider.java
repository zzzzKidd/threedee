/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.ailis.threedee.exceptions.AssetIOException;
import de.ailis.threedee.exceptions.AssetNotFoundException;


/**
 * Asset Provider which loads assets from a ZIP file.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ZipAssetProvider implements AssetProvider
{
    /** The asset type directory mapping. */
    private static Map<AssetType, String> directories;

    /** The ZIP file. */
    private final ZipFile zipFile;

    {
        directories = new HashMap<AssetType, String>();
        directories.put(AssetType.TEXTURE, "textures/");
        directories.put(AssetType.MATERIAL, "materials/");
        directories.put(AssetType.ANIMATION, "animations/");
        directories.put(AssetType.MESH, "meshes/");
        directories.put(AssetType.SCENE, "scenes/");
        directories.put(AssetType.ASSETS, "assets/");
    }


    /**
     * Constructor.
     *
     * @param file
     *            The ZIP file.
     */

    public ZipAssetProvider(final File file)
    {
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
        this.zipFile = zipFile;
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
                if (this.zipFile.getEntry(filename) != null) return true;
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
                final ZipEntry entry = this.zipFile.getEntry(filename);
                if (entry != null)
                {
                    InputStream stream;
                    try
                    {
                        stream = this.zipFile.getInputStream(entry);
                    }
                    catch (final IOException e)
                    {
                        throw new AssetIOException(e.toString(), e);
                    }
                    return new AssetInputStream(format, stream);
                }
            }
        }
        throw new AssetNotFoundException("Asset not found: " + type + " / "
            + id);
    }
}
