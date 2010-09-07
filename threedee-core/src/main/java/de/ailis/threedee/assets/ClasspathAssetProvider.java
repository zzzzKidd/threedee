/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.ailis.threedee.exceptions.AssetNotFoundException;


/**
 * Asset Provider which loads assets from the class path.
 *
 * @author Klaus Reimer (k@ailis.de
 */

public class ClasspathAssetProvider implements AssetProvider
{
    /** The asset type directory mapping. */
    private static Map<AssetType, String> directories;

    {
        directories = new HashMap<AssetType, String>();
        directories.put(AssetType.TEXTURE, "/textures/");
        directories.put(AssetType.MATERIAL, "/materials/");
        directories.put(AssetType.ASSETS, "/assets/");
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
                if (getClass().getResource(filename) != null) return true;
            }
        }
        return false;
    }


    /**
     * @see AssetProvider#openInputStream(AssetType, String)
     */

    @Override
    public AssetInputStream openInputStream(final AssetType type, final String id)
    {
        final String dir = directories.get(type);
        for (final AssetFormat format : type.getFormats())
        {
            for (final String extension : format.getExtensions())
            {
                final String filename = dir + id + extension;
                final InputStream stream = getClass()
                    .getResourceAsStream(filename);
                if (stream != null)
                    return new AssetInputStream(format, stream);
            }
        }
        throw new AssetNotFoundException("Asset not found: " + type + " / "
            + id);
    }
}
