/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.textures;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ailis.threedee.assets.AssetProvider;
import de.ailis.threedee.assets.Texture;
import de.ailis.threedee.exceptions.TextureException;
import de.ailis.threedee.rendering.GL;


/**
 * Texture based on a static image.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ImageTexture extends Texture
{
    /** The logger. */
    private static final Log log = LogFactory.getLog(ImageTexture.class);


    /**
     * Creates a new texture.
     *
     * @param id
     *            The texture id
     */

    public ImageTexture(final String id)
    {
        super(id);
    }


    /**
     * @see Texture#load(GL, AssetProvider)
     */

    @Override
    public void load(final GL gl, final AssetProvider resourceProvider)
    {
        // Produce a texture from the bitmap
        try
        {
            final InputStream stream = resourceProvider
                    .openInputStream(this.type, this.id);
            try
            {
                log.trace("Started loading texture " + this.id);
                gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, stream, 0);
                log.trace("Finished loading texture");
            }
            finally
            {
                stream.close();
            }
        }
        catch (final IOException e)
        {
            throw new TextureException("Unable to load texture '"
                    + this.id + "': " + e, e);
        }
    }
}
