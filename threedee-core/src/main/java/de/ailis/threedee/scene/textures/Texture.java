/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.textures;

import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.rendering.GL;


/**
 * Texture interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface Texture
{
    /**
     * Load the texture into the specified GL context. This must call
     * the glTexImage2D command to update the texture data in video memory.
     *
     * @param gl
     *            The OpenGL context
     * @param resourceProvider
     *            The resource provider to use for loading data
     */

    public void load(GL gl, ResourceProvider resourceProvider);
}
