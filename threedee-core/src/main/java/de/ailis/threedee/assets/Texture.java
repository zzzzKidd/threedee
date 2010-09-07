/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.assets;

import de.ailis.threedee.rendering.GL;


/**
 * Base class for all textures.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class Texture extends Asset
{
    /**
     * Constructor
     *
     * @param id
     *            The texture ID
     */

    public Texture(final String id)
    {
        super(id, AssetType.TEXTURE);
    }


    /**
     * Load the texture into the specified GL context. This must call
     * the glTexImage2D command to update the texture data in video memory.
     *
     * @param gl
     *            The OpenGL context
     * @param assetProvider
     *            The asset provider to use for loading data
     */

    public abstract void load(GL gl, AssetProvider assetProvider);
}
