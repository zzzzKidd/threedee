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
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.filename == null) ? 0 : this.filename.hashCode());
        return result;
    }


    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final ImageTexture other = (ImageTexture) obj;
        if (this.filename == null)
        {
            if (other.filename != null) return false;
        }
        else if (!this.filename.equals(other.filename)) return false;
        return true;
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
}
