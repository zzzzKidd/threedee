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
    private final Map<String, TextureReference> textures = new HashMap<String, TextureReference>();

    /** The dynamic textures. */
    private final Map<String, DynamicTexture<?>> dynamicTextures = new HashMap<String, DynamicTexture<?>>();

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
     * Sets the resource provider.
     *
     * @param resourceProvider
     *            The resource provider to set
     */

    public void setResourceProvider(final ResourceProvider resourceProvider)
    {
        this.resourceProvider = resourceProvider;
    }


    /**
     * Returns the resource provider.
     *
     * @return The resource provider.
     */

    public ResourceProvider getResourceProvider()
    {
        return this.resourceProvider;
    }


    /**
     * Unloads all currently loaded textures from the manager.
     *
     * @param gl
     *            The OpenGL context
     */

    public void clear(final GL gl)
    {
        for (final TextureReference ref : this.textures.values())
        {
            if (ref.isLoaded()) ref.unload(gl);
        }
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
        final String id = texture.getId();
        TextureReference ref = this.textures.get(id);
        if (ref == null)
        {
            ref = new TextureReference(texture);
            this.textures.put(id, ref);
            if (texture instanceof DynamicTexture<?>)
                this.dynamicTextures.put(id, (DynamicTexture<?>) texture);
            log.debug("Cached texture: " + id);
        }
        ref.addReference();

        log.debug("Referenced texture: " + id);
    }


    /**
     * Dereferences a texture.
     *
     * @param texture
     *            The texture to dereference
     */

    public void dereferenceTexture(final Texture texture)
    {
        final String id = texture.getId();
        final TextureReference ref = this.textures.get(id);
        if (ref == null)
            throw new IllegalStateException(
                    "Tried to dereference unknown texture");
        ref.removeReference();
        log.debug("Dereferenced texture: " + id);
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
        final String id = texture.getId();
        final TextureReference ref = this.textures.get(id);
        if (ref == null)
            throw new IllegalStateException("Unknown texture: " + id);
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
        final Iterator<Map.Entry<String, TextureReference>> iterator = this.textures
                .entrySet().iterator();
        while (iterator.hasNext())
        {
            final Map.Entry<String, TextureReference> entry = iterator.next();
            final String id = entry.getKey();
            final TextureReference ref = entry.getValue();
            if (!ref.isReferenced())
            {
                if (ref.isLoaded()) ref.unload(gl);
                iterator.remove();
                this.dynamicTextures.remove(id);
                log.debug("Removed texture " + id);
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
        for (final Texture texture : this.dynamicTextures.values())
        {
            if (texture instanceof DynamicTexture<?>)
            {
                final DynamicTexture<?> dynamicTexture = (DynamicTexture<?>) texture;
                dynamicTexture.update(delta);
                changed |= !dynamicTexture.isValid();
            }
        }
        log.trace("Updated");
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
        final String id = texture.getId();
        final TextureReference ref = this.textures.get(id);
        if (ref == null)
            throw new IllegalStateException("Tried to bind unknown texture: "
                    + id);

        // If texture is not loaded then load it now.
        if (!ref.isLoaded()) ref.load(gl, this.resourceProvider);

        // Bind texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, ref.getTextureId());

        // Re-render the texture if needed.
        if (texture instanceof DynamicTexture<?>)
        {
            final DynamicTexture<?> dynTexture = (DynamicTexture<?>) texture;
            if (!dynTexture.isValid()) dynTexture.reload(gl);
        }
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
