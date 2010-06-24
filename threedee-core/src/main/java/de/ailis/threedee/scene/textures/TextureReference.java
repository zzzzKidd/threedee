/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.textures;

import java.nio.IntBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.rendering.GL;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A texture reference.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

class TextureReference
{
    /** The logger */
    private static final Log log = LogFactory.getLog(ImageTexture.class);

    /** The texture data */
    private final Texture texture;

    /** The currently active references to this texture */
    private int references = 0;

    /** If texture is loaded or not */
    private boolean loaded = false;

    /** Buffer containing the texture ID */
    private final IntBuffer textureId = BufferUtils
            .createDirectIntegerBuffer(1);


    /**
     * Constructs a new texture reference.
     *
     * @param texture
     *            The texture data
     */

    TextureReference(final Texture texture)
    {
        this.texture = texture;
    }


    /**
     * Adds a reference.
     */

    void addReference()
    {
        this.references++;
    }


    /**
     * Removes a reference.
     */

    void removeReference()
    {
        if (this.references == 0)
            throw new IllegalStateException("Texture is not referenced");
        this.references--;
    }


    /**
     * Checks if this texture is referenced.
     *
     * @return True if texture is referenced, false if not
     */

    boolean isReferenced()
    {
        return this.references > 0;
    }


    /**
     * Checks if this texture is currently loaded.
     *
     * @return True if texture is loaded, false if not
     */

    boolean isLoaded()
    {
        return this.loaded;
    }


    /**
     * Loads the texture.
     *
     * @param gl
     *            The OpenGL context
     * @param resourceProvider
     *            The resource provider to use for loading the texture
     */

    void load(final GL gl, final ResourceProvider resourceProvider)
    {
        if (this.loaded)
            throw new IllegalStateException("Texture is already loaded: "
                    + this.texture);

        // Generate a new texture id
        gl.glGenTextures(this.textureId);

        // Bind the texture to the GL context
        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId.get(0));

        // Set high quality texture filters
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_LINEAR);

        // Setup texture wrap mode
        gl
                .glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S,
                        GL.GL_REPEAT);
        gl
                .glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T,
                        GL.GL_REPEAT);

        // Enable hardware mipmaps
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_GENERATE_MIPMAP, GL.GL_TRUE);

        this.texture.load(gl, resourceProvider);

        // Unbind the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);

        // Remember that we have loaded the texture
        this.loaded = true;

        log.debug("Loaded texture " + this.texture);
    }


    /**
     * Unloads the texture
     *
     * @param gl
     *            The OpenGL context
     */

    void unload(final GL gl)
    {
        if (!this.loaded)
            throw new IllegalStateException("Texture is already unloaded");

        // Remove texture from GL context
        this.textureId.put(0, 0);
        gl.glDeleteTextures(this.textureId);

        // Remember that texture is now unloaded
        this.loaded = false;

        log.debug("Unloaded texture " + this.texture);
    }


    /**
     * Returns the texture id.
     *
     * @return The texture id
     */

    int getTextureId()
    {
        return this.textureId.get(0);
    }
}
