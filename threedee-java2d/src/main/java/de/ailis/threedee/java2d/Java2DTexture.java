/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.java2d;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.util.Hashtable;

import de.ailis.threedee.io.resources.ResourceProvider;
import de.ailis.threedee.rendering.GL;
import de.ailis.threedee.scene.textures.DynamicTexture;
import de.ailis.threedee.scene.textures.Texture;


/**
 * A dynamic texture based on Java2D imaging.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Java2DTexture extends DynamicTexture<Java2DTexture>
{
    /** The colour model including alpha for the GL image */
    private static ColorModel glAlphaColorModel = new ComponentColorModel(
            ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[] { 8, 8, 8, 8 }, true, false, Transparency.TRANSLUCENT,
            DataBuffer.TYPE_BYTE);

    /** The colour model for the GL image */
    private static ColorModel glColorModel = new ComponentColorModel(ColorSpace
            .getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 0 }, false,
            false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

    /** The image */
    public BufferedImage image;

    /** The source pixel format */
    private final int srcPixelFormat;

    /** The image buffer */
    private final ByteBuffer imageBuffer;

    /** The texture width */
    private final int width;

    /** The texture height */
    private final int height;


    /**
     * Creates a new texture.
     *
     * @param width
     *            The texture width
     * @param height
     *            The texture height
     * @param alpha
     *            If texture should be alphatransparent
     */

    public Java2DTexture(final int width, final int height, final boolean alpha)
    {
        this.width = width;
        this.height = height;

        int srcPixelFormat;
        ColorModel colorModel;
        int bands;
        if (alpha)
        {
            bands = 4;
            colorModel = glAlphaColorModel;
            srcPixelFormat = GL.GL_RGBA;
        }
        else
        {
            bands = 3;
            colorModel = glColorModel;
            srcPixelFormat = GL.GL_RGB;
        }
        final ByteBuffer imageBuffer = ByteBuffer.allocate(width * height
                * bands);
        this.imageBuffer = imageBuffer;
        final DataBufferByte dataBuffer = new DataBufferByte(imageBuffer
                .array(), imageBuffer.capacity());
        final int[] bandOffsets = new int[bands];
        for (int i = 0; i < bands; i++)
            bandOffsets[i] = i;
        final WritableRaster raster = Raster.createInterleavedRaster(
                dataBuffer, width, height, width * bands, bands, bandOffsets,
                null);
        final BufferedImage texImage = new BufferedImage(colorModel, raster,
                false, new Hashtable<Byte, Byte>());
        this.image = texImage;
        this.srcPixelFormat = srcPixelFormat;
    }


    /**
     * @see Texture#load(GL, ResourceProvider)
     */

    @Override
    public void load(final GL gl, final ResourceProvider resourceProvider)
    {
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, this.width,
                this.height, 0, this.srcPixelFormat, GL.GL_UNSIGNED_BYTE,
                this.imageBuffer);
    }


    /**
     * @see DynamicTexture#reload(GL)
     */

    @Override
    public void reload(final GL gl)
    {
        gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, 0, 0, this.width, this.height,
                this.srcPixelFormat, GL.GL_UNSIGNED_BYTE, this.imageBuffer);
        validate();
    }


    /**
     * Returns a Graphics2D context for drawing on the texture.
     *
     * @return The Graphics2D context
     */

    public Graphics2D createGraphics()
    {
        return this.image.createGraphics();
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
