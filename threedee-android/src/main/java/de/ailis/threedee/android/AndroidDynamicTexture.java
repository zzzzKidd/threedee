/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.android;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.opengl.GLUtils;
import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.rendering.GL;
import de.ailis.threedee.scene.textures.DynamicTexture;
import de.ailis.threedee.scene.textures.Texture;


/**
 * A dynamic texture based on Android imaging.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AndroidDynamicTexture extends
        DynamicTexture<AndroidDynamicTexture>
{
    /** The image */
    private final Bitmap bitmap;

    /** The canvas */
    private final Canvas canvas;

    /** The texture width */
    private final int width;

    /** The texture height */
    private final int height;


    /**
     * Creates a new texture.
     *
     * @param id
     *            The texture ID.
     * @param width
     *            The texture width
     * @param height
     *            The texture height
     */

    public AndroidDynamicTexture(final String id, final int width,
        final int height)
    {
        super(id);
        this.bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);
        this.canvas.translate(0, height);
        this.canvas.scale(1, -1);
        this.width = width;
        this.height = height;
    }


    /**
     * @see Texture#load(GL, ResourceProvider)
     */

    @Override
    public void load(final GL gl, final ResourceProvider resourceProvider)
    {
        GLUtils.texImage2D(GL.GL_TEXTURE_2D, 0, this.bitmap, 0);
    }


    /**
     * @see DynamicTexture#reload(GL)
     */

    @Override
    public void reload(final GL gl)
    {
        gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 4);
        GLUtils.texSubImage2D(GL.GL_TEXTURE_2D, 0, 0, 0, this.bitmap);
        validate();
    }


    /**
     * Returns a canvas context for drawing on the texture.
     *
     * @return The canvas context
     */

    public Canvas getCanvas()
    {
        return this.canvas;
    }


    /**
     * Returns the width of the texture.
     *
     * @return The texture width
     */

    public int getWidth()
    {
        return this.width;
    }


    /**
     * Returns the height of the texture.
     *
     * @return The texture height
     */

    public int getHeight()
    {
        return this.height;
    }
}
