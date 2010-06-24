/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.events;

import de.ailis.threedee.scene.textures.DynamicTexture;


/**
 * Listener interface for texture events.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The texture type
 */

public interface TextureListener<T extends DynamicTexture<T>>
{
    /**
     * Called to update a texture.
     *
     * @param texture
     *            The texture to update
     * @param delta
     *            The time delta since last update in seconds
     */

    public void update(T texture, float delta);
}
