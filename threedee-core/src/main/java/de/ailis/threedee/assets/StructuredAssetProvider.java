/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ailis.threedee.exceptions.AssetNotFoundException;


/**
 * Base class for structured asset providers.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class StructuredAssetProvider implements AssetProvider
{
    /** The asset type directory mapping. */
    private final Map<AssetType, List<String>> directories = new HashMap<AssetType, List<String>>();

    /** The base directory. */
    private final String baseDir;


    /**
     * Constructor.
     *
     * @param baseDir
     *            The base directory.
     */

    public StructuredAssetProvider(final String baseDir)
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
                    final String filename = addBaseDir(new File(dir, id
                        + extension).getPath());
                    if (exists(filename)) return true;
                }
            }
        }
        return false;
    }


    /**
     * Adds base directory to filename if specified.
     *
     * @param filename
     *            The filename
     * @return The filename with added base directory or unchanged filename if
     *         no base dir exists.
     */

    private String addBaseDir(final String filename)
    {
        if (this.baseDir == null) return filename;
        return new File(this.baseDir, filename).getPath();
    }


    /**
     * Check if the specified file exists.
     *
     * @param filename
     *            The file to check.
     * @return True if file exists, false if not.
     */

    protected abstract boolean exists(String filename);


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
                    final String filename = addBaseDir(new File(
                        dir, id + extension).getPath());
                    final InputStream stream = openInputStream(filename);
                    if (stream != null)
                        return new AssetInputStream(format, stream);
                }
            }
        }
        throw new AssetNotFoundException("Asset not found: " + type + " / "
            + id);
    }


    /**
     * Returns the input stream for reading the specified file.
     *
     * @param filename
     *            The file to read
     * @return The input stream or null of file was not found.
     */

    protected abstract InputStream openInputStream(String filename);
}
