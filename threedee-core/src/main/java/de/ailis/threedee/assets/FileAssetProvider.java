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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final Map<AssetType, List<String>> directories = new HashMap<AssetType, List<String>>();

    /** The base directory. */
    private final File baseDir;


    /**
     * Constructor.
     *
     * @param baseDir
     *            The base directory.
     */

    public FileAssetProvider(final File baseDir)
    {
        this.baseDir = baseDir;
        setupDefaultDirectories();
    }


    /**
     * Setup the default directories.
     */

    private void setupDefaultDirectories()
    {
        for (final AssetType type : AssetType.values())
        {
            this.directories.put(type, new ArrayList<String>());
        }
        this.directories.get(AssetType.TEXTURE).add("textures");
        this.directories.get(AssetType.MATERIAL).add("materials");
        this.directories.get(AssetType.ANIMATION).add("animations");
        this.directories.get(AssetType.MESH).add("meshes");
        this.directories.get(AssetType.SCENE).add("scenes");
        this.directories.get(AssetType.ASSETS).add("assets");
    }


    /**
     * Returns the list of directories which are search for the specified asset
     * type. You are free to modifiy the content of the list to change the
     * loading behaviour of this asset provider.
     *
     * @param type
     *            The asset type.
     * @return The list of directories.
     */

    public List<String> getDirectories(final AssetType type)
    {
        return this.directories.get(type);
    }


    /**
     * @see AssetProvider#exists(AssetType, String)
     */

    @Override
    public boolean exists(final AssetType type, final String id)
    {
        for (final String dir : this.directories.get(type))
        {
            for (final AssetFormat format : type.getFormats())
            {
                for (final String extension : format.getExtensions())
                {
                    final String filename = new File(dir, id + extension)
                        .getPath();
                    if (new File(this.baseDir, filename + ".gz").exists())
                        return true;
                    if (new File(this.baseDir, filename).exists()) return true;
                }
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
        for (final String dir : this.directories.get(type))
        {
            for (final AssetFormat format : type.getFormats())
            {
                for (final String extension : format.getExtensions())
                {
                    final String filename = new File(dir, id + extension)
                        .getPath();

                    // Try to load GZIP compressed file.
                    File file = new File(this.baseDir, filename + ".gz");
                    if (file.exists())
                    {
                        InputStream stream;
                        try
                        {
                            stream = new GZIPInputStream(new FileInputStream(
                                file));
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
        }
        throw new AssetNotFoundException("Asset not found: " + type + " / "
            + id);
    }
}
