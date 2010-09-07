/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import de.ailis.threedee.assets.AssetInputStream;
import de.ailis.threedee.assets.AssetProvider;
import de.ailis.threedee.assets.AssetType;


/**
 * Resource provider using Android assets.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AndroidAssetProvider implements AssetProvider
{
    /** The asset manager */
    public AssetManager manager;


    /**
     * Creates a new asset provider.
     *
     * @param manager
     *            The asset manager
     */

    public AndroidAssetProvider(final AssetManager manager)
    {
        this.manager = manager;
    }


    /**
     * @see de.ailis.threedee.io.resources.ResourceProvider#openForRead(java.lang.String)
     */

    @Override
    public InputStream openForRead(final String filename) throws IOException
    {
    }

    /**
     * @see de.ailis.threedee.io.resources.ResourceProvider#openForWrite(java.lang.String)
     */

    @Override
    public OutputStream openForWrite(final String filename) throws IOException
    {
        throw new UnsupportedOperationException("Can't write assets");
    }


    /**
     * @see de.ailis.threedee.assets.AssetProvider#exists(de.ailis.threedee.assets.AssetType, java.lang.String)
     */
    
    @Override
    public boolean exists(AssetType type, String id)
    {
        // Empty
    }


    /**
     * @see de.ailis.threedee.assets.AssetProvider#openInputStream(de.ailis.threedee.assets.AssetType, java.lang.String)
     */
    
    @Override
    public AssetInputStream openInputStream(AssetType type, String id)
    {
        return this.manager.open(id);
    }
}
