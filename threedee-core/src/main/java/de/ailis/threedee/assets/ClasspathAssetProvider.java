/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;

import java.io.InputStream;


/**
 * Asset Provider which loads assets from the class path.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ClasspathAssetProvider extends StructuredAssetProvider
{
    /**
     * Constructor.
     */

    public ClasspathAssetProvider()
    {
        this("/");
    }


    /**
     * Constructor.
     *
     * @param baseDir
     *            The base directory.
     */

    public ClasspathAssetProvider(final String baseDir)
    {
        super(baseDir);
    }


    /**
     * @see de.ailis.threedee.assets.StructuredAssetProvider#exists(java.lang.String)
     */

    @Override
    public boolean exists(final String filename)
    {
        return (getClass().getResource(filename)) != null;
    }


    /**
     * @see de.ailis.threedee.assets.StructuredAssetProvider#openInputStream(java.lang.String)
     */

    @Override
    public InputStream openInputStream(final String filename)
    {
        return getClass().getResourceAsStream(filename);
    }
}
