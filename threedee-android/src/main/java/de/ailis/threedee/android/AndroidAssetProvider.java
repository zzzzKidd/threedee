/*
 * Copyright (C) 2010 IP Labs GmbH <http://www.iplabs.de/>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.android;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import de.ailis.threedee.assets.StructuredAssetProvider;


/**
 * Asset provider which reads the files from an android asset directory.
 *
 * @author Klaus Reimer (k.reimer@iplabs.de)
 */

public class AndroidAssetProvider extends StructuredAssetProvider
{
    /** The asset manager. */
    private final AssetManager assetManager;


    /**
     * Constructor
     *
     * @param assetManager
     *            The asset manager.
     * @param baseDir
     *            The base directory.
     */

    public AndroidAssetProvider(final AssetManager assetManager,
        final String baseDir)
    {
        super(baseDir);
        this.assetManager = assetManager;
    }


    /**
     * @see de.ailis.threedee.assets.StructuredAssetProvider#exists(java.lang.String)
     */

    @Override
    protected boolean exists(final String filename)
    {
        try
        {
            this.assetManager.openFd(filename).close();
            return true;
        }
        catch (final IOException e)
        {
            return false;
        }
    }


    /**
     * @see de.ailis.threedee.assets.StructuredAssetProvider#openInputStream(java.lang.String)
     */

    @Override
    protected InputStream openInputStream(final String filename)
    {
        try
        {
            return this.assetManager.open(filename);
        }
        catch (final IOException e)
        {
            return null;
        }
    }
}
