/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.textures;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import de.ailis.threedee.exceptions.TextureException;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.rendering.opengl.GL;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A texture
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Texture
{
    /** The texture name */
    private final String filename;

    /** The resource provider */
    private final ResourceProvider resourceProvider;

    /** If texture is loaded into OpenGL */
    private boolean loaded;

    /** If texture is released */
    private boolean released;

    /** If texture should be released on next frame */
    private boolean release;

    /** The texture ID */
    private int textureId;


    /**
     * Creates a new texture. Only the TextureCache is allowed to do that.
     *
     * @param filename
     *            The texture filename
     * @param resourceProvider
     *            The resource provider
     */

    Texture(final String filename, final ResourceProvider resourceProvider)
    {
        this.filename = filename;
        this.resourceProvider = resourceProvider;
        this.loaded = false;
        this.released = false;
        this.release = false;
    }


    /**
     * Binds the texture so it is applied to all subsequent vertex commands.
     *
     * @param gl
     *            The GL context
     */

    public void bind(final GL gl)
    {
        // Do nothing if texture has been released
        if (this.released) return;

        // Load the texture if necessary
        if (!this.loaded) create(gl);

        // Bind texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId);
    }


    /**
     * Unbinds the current texture so all subsequent vertex commands are not
     * using any texture.
     *
     * @param gl
     *            The GL context
     */

    public void unbind(final GL gl)
    {
        // Do nothing if texture has been released
        if (this.released) return;

        // Unbind texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }


    /**
     * Creates the texture.
     *
     * @param gl
     *            The GL context
     */

    private void create(final GL gl)
    {
        final IntBuffer buffer = BufferUtils.createDirectIntegerBuffer(1);

        gl.glGenTextures(buffer);
        this.textureId = buffer.get(0);

        // Bind the texture to the GL context
        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textureId);

        // High quality texture filters
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_LINEAR);

        // Setup texture wrap mode
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

        // Produce a texture from the bitmap
        try
        {
            final InputStream stream = this.resourceProvider
                    .openForRead(this.filename);
            try
            {
                gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, stream, 0);
            }
            finally
            {
                stream.close();
            }
        }
        catch (final IOException e)
        {
            throw new TextureException("Unable to load texture '"
                    + this.filename + "': " + e, e);
        }

        // Unbind the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);

        // Remember that we have loaded the texture
        this.loaded = true;
    }


    /**
     * Mark texture for release.
     */

    public void release()
    {
        this.release = true;
    }


    /**
     * Checks if texture should be released.
     *
     * @return True if texture should be released, false if not
     */

    public boolean isRelease()
    {
        return this.release;
    }


    /**
     * Frees all resources. Only the TextureCache is allowed to call this.
     *
     * @param gl
     *            The GL context
     */

    void free(final GL gl)
    {
        // Do nothing if already released
        if (this.released) return;

        // Delete texture from OpenGL context if needed
        if (this.loaded)
        {
            // Remove texture from GL context
            final IntBuffer buffer = BufferUtils.createDirectIntegerBuffer(1).put(
                0, this.textureId);
            gl.glDeleteTextures(buffer);
            this.loaded = false;
        }

        // Remember that texture is now released
        this.released = true;
    }


    /**
     * Checks if texture has been released.
     *
     * @return True if texture has been releasd, false if not
     */

    public boolean isReleased()
    {
        return this.released;
    }
}
