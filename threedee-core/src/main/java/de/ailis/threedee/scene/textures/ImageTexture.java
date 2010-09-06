/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.textures;

import java.io.IOException;
import java.io.InputStream;

import de.ailis.threedee.exceptions.TextureException;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.rendering.GL;


/**
 * Texture based on a static image.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ImageTexture implements Texture
{
    /** The texture filename */
    public String filename;


    /**
     * Creates a new texture.
     *
     * @param filename
     *            The texture filename
     */

    public ImageTexture(final String filename)
    {
        this.filename = filename;
    }


    /**
     * Returns the filename of the texture.
     *
     * @return The texture filename
     */

    public String getFilename()
    {
        return this.filename;
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return this.filename;
    }


    /**
     * @see Texture#load(GL, ResourceProvider)
     */

    @Override
    public void load(final GL gl, final ResourceProvider resourceProvider)
    {
        // Produce a texture from the bitmap
        try
        {
            final InputStream stream = resourceProvider
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
    }


    /**
     * @see de.ailis.threedee.scene.textures.Texture#getId()
     */

    @Override
    public String getId()
    {
        return this.filename;
    }
}
