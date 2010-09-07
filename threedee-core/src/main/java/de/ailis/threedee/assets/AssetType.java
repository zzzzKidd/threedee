/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;


/**
 * Asset type.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public enum AssetType
{
    /** Texture asset. */
    TEXTURE(new AssetFormat[] { AssetFormat.JPEG, AssetFormat.PNG }),

    /** Material asset. */
    MATERIAL(new AssetFormat[] { AssetFormat.TDB, AssetFormat.TDA }),

    /** Mesh asset. */
    MESH(new AssetFormat[] { AssetFormat.TDB, AssetFormat.TDA }),

    /** Scene asset. */
    SCENE(new AssetFormat[] { AssetFormat.TDB, AssetFormat.TDA }),

    /** Animation asset. */
    ANIMATION(new AssetFormat[] { AssetFormat.TDB, AssetFormat.TDA }),

    /** Assets asset. */
    ASSETS(new AssetFormat[] { AssetFormat.DAE });

    /** The supported formats for this asset type. */
    private AssetFormat[] formats;


    /**
     * Constructor.
     *
     * @param formats
     *            The supported formats for this asset type.
     */

    private AssetType(final AssetFormat[] formats)
    {
        this.formats = formats;
    }


    /**
     * Returns the supported formats.
     *
     * @return The supported formats
     */

    public AssetFormat[] getFormats()
    {
        return this.formats;
    }
}
