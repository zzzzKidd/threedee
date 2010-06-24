/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.textures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ailis.threedee.io.resources.ClasspathResourceProvider;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.rendering.GL;


/**
 * The texture manager.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public final class TextureManager
{
    /** The logger */
    private static final Log log = LogFactory.getLog(TextureManager.class);

    /** Singleton instance of the texture manager */
    private static final TextureManager instance = new TextureManager();

    /** The cached textures */
    private final Map<Texture, TextureReference> textures = new HashMap<Texture, TextureReference>();

    /** The resource provider */
    public ResourceProvider resourceProvider = new ClasspathResourceProvider();


    /**
     * Private constructor to prevent instantiation from outside
     */

    private TextureManager()
    {
        // Empty
    }


    /**
     * Returns the singleton instance of the texture manager.
     *
     * @return The texture manager.
     */

    public static TextureManager getInstance()
    {
        return instance;
    }


    /**
     * References a texture.
     *
     * @param texture
     *            The texture to reference
     */

    public void referenceTexture(final Texture texture)
    {
        TextureReference ref = this.textures.get(texture);
        if (ref == null)
        {
            ref = new TextureReference(texture);
            this.textures.put(texture, ref);
            log.debug("Cached texture: " + texture);
        }
        ref.addReference();

        log.debug("Referenced texture: " + texture);
    }


    /**
     * Dereferences a texture.
     *
     * @param texture
     *            The texture to dereference
     */

    public void dereferenceTexture(final Texture texture)
    {
        final TextureReference ref = this.textures.get(texture);
        if (ref == null)
            throw new IllegalStateException(
                    "Tried to dereference unknown texture");
        ref.removeReference();
        log.debug("Dereferenced texture: " + texture);
    }


    /**
     * Returns the texture reference for the specified texture.
     *
     * @param texture
     *            The texture
     * @return The texture reference
     */

    public TextureReference get(final Texture texture)
    {
        final TextureReference ref = this.textures.get(texture);
        if (ref == null)
            throw new IllegalStateException("Unknown texture: " + texture);
        return ref;
    }


    /**
     * Clean-up the textures. This removes unreferenced textures from the cache
     * (And also unloads them from the OpenGL context if still loaded). This
     * method should be called once during rendering.
     *
     * TODO Add a time check so the cleanUp only does something once per second
     * or something like that.
     *
     * @param gl
     *            The OpenGL context
     */

    public void cleanUp(final GL gl)
    {
        final Iterator<Map.Entry<Texture, TextureReference>> iterator = this.textures
                .entrySet().iterator();
        while (iterator.hasNext())
        {
            final Map.Entry<Texture, TextureReference> entry = iterator.next();
            final Texture texture = entry.getKey();
            final TextureReference ref = entry.getValue();
            if (!ref.isReferenced())
            {
                if (ref.isLoaded()) ref.unload(gl);
                iterator.remove();
                log.debug("Removed texture " + texture);
            }
        }
    }


    /**
     * Updates the textures.
     *
     * @param delta
     *            The time delta in seconds
     * @return True if a texture was changed and therefor the scene must be
     *         rendered again. False if nothing was changed.
     */

    public boolean update(final float delta)
    {
        boolean changed = false;
        for (final Texture texture : this.textures.keySet())
        {
            if (texture instanceof DynamicTexture<?>)
            {
                final DynamicTexture<?> dynamicTexture = (DynamicTexture<?>) texture;
                dynamicTexture.update(delta);
                changed |= !dynamicTexture.isValid();
            }
        }
        return changed;
    }


    /**
     * Binds a texture to the specified OpenGL context.
     *
     * @param gl
     *            The OpenGL context
     * @param texture
     *            The texture to bind
     */

    public void bind(final GL gl, final Texture texture)
    {
        final TextureReference ref = this.textures.get(texture);
        if (ref == null)
            throw new IllegalStateException("Tried to bind unknown texture: "
                    + texture);

        // If texture is not loaded then load it now.
        if (!ref.isLoaded()) ref.load(gl, this.resourceProvider);

        // Bind texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, ref.getTextureId());

        // Re-render the texture if needed.
        if (texture instanceof DynamicTexture<?>)
            ((DynamicTexture<?>) texture).reload(gl);
    }


    /**
     * Unbinds the currently bound texture from the specified GL context.
     *
     * @param gl
     *            The OpenGL context
     */

    public void unbindTexture(final GL gl)
    {
        // Unbind texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }
}
