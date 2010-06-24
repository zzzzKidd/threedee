/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.textures;

import java.util.ArrayList;
import java.util.List;

import de.ailis.threedee.events.TextureListener;
import de.ailis.threedee.rendering.GL;


/**
 * Texture interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @param <T>
 *            The concrete dynamic texture type
 */

public abstract class DynamicTexture<T extends DynamicTexture<T>> implements
        Texture
{
    /** If loaded texture is up-to-date */
    private boolean valid;

    /** The texture listeners */
    private final List<TextureListener<T>> listeners = new ArrayList<TextureListener<T>>();


    /**
     * Updates the texture with the specified time delta.
     *
     * @param delta
     *            The time elapsed since the last scene update (in seconds)
     */

    @SuppressWarnings("unchecked")
    public void update(final float delta)
    {
        for (final TextureListener<T> listener : this.listeners)
            listener.update((T) this, delta);
    }


    /**
     * Reloads the texture into the specified OpenGL context. This must call the
     * glTexSubImage2D command to update the texture data in video memory.
     *
     * @param gl
     *            The OpenGL context
     */

    public abstract void reload(GL gl);


    /**
     * Checks if texture is valid.
     *
     * @return True if texture is valid, false if it must be redrawn
     */

    public boolean isValid()
    {
        return this.valid;
    }


    /**
     * Invalidates the texture so it is redrawn on next frame. You may also need
     * to call the renderRequest() method on the canvas to enforce a redraw of
     * the scene.
     */

    public void invalidate()
    {
        this.valid = false;
    }


    /**
     * Validates the texture so it is not redrawn again on next frame when it
     * not changed again. Must be called as last command in the reload method.
     */

    protected void validate()
    {
        this.valid = true;
    }


    /**
     * Adds a texture listener.
     *
     * @param listener
     *            The texture listener to add
     */

    public void addTextureListener(final TextureListener<T> listener)
    {
        this.listeners.add(listener);
    }


    /**
     * Removes a texture listener.
     *
     * @param listener
     *            The texture listener to remove
     */

    public void removeTextureListener(final TextureListener<T> listener)
    {
        this.listeners.remove(listener);
    }
}
