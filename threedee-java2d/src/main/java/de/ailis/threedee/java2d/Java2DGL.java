/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.java2d;

import java.awt.Color;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import de.ailis.threedee.exceptions.TextureException;
import de.ailis.threedee.rendering.GL;


/**
 * Base class for OpenGL implementations which can use Java2D for image
 * processing.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class Java2DGL implements GL
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


    /**
     * @see de.ailis.threedee.rendering.GL#glTexImage2D(int, int,
     *      java.io.InputStream, int)
     */

    public void glTexImage2D(final int target, final int level,
            final InputStream stream, final int border)
    {
        BufferedImage bufferedImage;
        try
        {
            bufferedImage = ImageIO.read(stream);
        }
        catch (final IOException e)
        {
            throw new TextureException("Unable to load texture from stream");
        }

        glTexImage2D(target, level, bufferedImage, border);
    }


    /**
     * Specify a two-dimensional texture image
     *
     * @param target
     *            Specifies the target texture. Must be GL_TEXTURE_2D,
     *            GL_PROXY_TEXTURE_2D, GL_TEXTURE_CUBE_MAP_POSITIVE_X,
     *            GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
     *            GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
     *            GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
     *            GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
     *            GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, or GL_PROXY_TEXTURE_CUBE_MAP.
     * @param level
     *            Specifies the level-of-detail number. Level 0 is the base
     *            image level. Level n is the nth mipmap reduction image.
     * @param image
     *            The texture image
     * @param border
     *            Specifies the width of the border. Must be either 0 or 1.
     */

    public void glTexImage2D(final int target, final int level,
            final BufferedImage image, final int border)
    {
        final int width = image.getWidth();
        final int height = image.getHeight();

        // create a raster that can be used by OpenGL as a source
        // for a texture
        WritableRaster raster;
        BufferedImage texImage;
        int srcPixelFormat;
        if (image.getColorModel().hasAlpha())
        {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                    width, height, 4, null);
            texImage = new BufferedImage(glAlphaColorModel, raster, false,
                    new Hashtable<Byte, Byte>());
            srcPixelFormat = GL_RGBA;
        }
        else
        {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                    width, height, 3, null);
            texImage = new BufferedImage(glColorModel, raster, false,
                    new Hashtable<Byte, Byte>());
            srcPixelFormat = GL_RGB;
        }

        // copy the source image into the produced image
        final Graphics2D g = (Graphics2D) texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, width, height);
        g.drawImage(image, 0, 0, null);

        // build a byte buffer from the temporary image
        // that be used by OpenGL to produce a texture.
        final byte[] data = ((DataBufferByte) texImage.getRaster()
                .getDataBuffer()).getData();

        final ByteBuffer imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        glTexImage2D(target, level, GL_RGBA, width, height, border,
                srcPixelFormat, GL_UNSIGNED_BYTE, imageBuffer);
    }
}
