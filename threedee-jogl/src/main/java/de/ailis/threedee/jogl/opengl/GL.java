/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.jogl.opengl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.media.opengl.glu.GLU;

import de.ailis.threedee.exceptions.TextureException;


/**
 * LWJGL implementation of the ThreeDee GL context.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class GL implements de.ailis.threedee.rendering.opengl.GL
{
    /** The real GL interface */
    private final javax.media.opengl.GL gl;

    /** The real GLU interface */
    private final javax.media.opengl.glu.GLU glu;

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
     * Private constructor to prevent instantiation from outside. Use
     * getInstance instead.
     *
     * @param gl
     *            The real GL interface
     * @param glu
     *            The real GLU interface
     */

    public GL(final javax.media.opengl.GL gl, final GLU glu)
    {
        this.gl = gl;
        this.glu = glu;
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glColorPointer(int, int, FloatBuffer)
     */

    public void glColorPointer(final int size, final int stride,
            final FloatBuffer pointer)
    {
        this.gl.glColorPointer(size, GL_FLOAT, stride, pointer);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glMaterial(int, int,
     *      java.nio.FloatBuffer)
     */

    public void glMaterial(final int face, final int pname,
            final FloatBuffer params)
    {
        this.gl.glMaterialfv(face, pname, params);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glMaterialf(int, int, float)
     */

    public void glMaterialf(final int face, final int pname, final float param)
    {
        this.gl.glMaterialf(face, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glDisableClientState(int)
     */

    public void glDisableClientState(final int cap)
    {
        this.gl.glDisableClientState(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glEnableClientState(int)
     */

    public void glEnableClientState(final int cap)
    {
        this.gl.glEnableClientState(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glVertexPointer(int, int,
     *      java.nio.FloatBuffer)
     */

    public void glVertexPointer(final int size, final int stride,
            final FloatBuffer pointer)
    {
        this.gl.glVertexPointer(size, GL_FLOAT, stride, pointer);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glNormalPointer(int,
     *      java.nio.FloatBuffer)
     */

    public void glNormalPointer(final int stride, final FloatBuffer pointer)
    {
        this.gl.glNormalPointer(GL_FLOAT, stride, pointer);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glTexCoordPointer(int, int,
     *      java.nio.FloatBuffer)
     */

    public void glTexCoordPointer(final int size, final int stride,
            final FloatBuffer pointer)
    {
        this.gl.glTexCoordPointer(size, GL_FLOAT, stride, pointer);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glDrawElements(int, int,
     *      java.nio.Buffer)
     */

    public void glDrawElements(final int mode, final int type,
            final Buffer indices)
    {
        this.gl.glDrawElements(mode, indices.remaining(), type, indices);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glMultMatrix(java.nio.FloatBuffer)
     */

    public void glMultMatrix(final FloatBuffer m)
    {
        this.gl.glMultMatrixf(m);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glPopMatrix()
     */

    public void glPopMatrix()
    {
        this.gl.glPopMatrix();
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glPushMatrix()
     */

    public void glPushMatrix()
    {
        this.gl.glPushMatrix();
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glClear(int)
     */

    public void glClear(final int mask)
    {
        this.gl.glClear(mask);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glFlush()
     */

    public void glFlush()
    {
        this.gl.glFlush();
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glLoadIdentity()
     */

    public void glLoadIdentity()
    {
        this.gl.glLoadIdentity();
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glMatrixMode(int)
     */

    public void glMatrixMode(final int mode)
    {
        this.gl.glMatrixMode(mode);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glBlendFunc(int, int)
     */

    public void glBlendFunc(final int sfactor, final int dfactor)
    {
        this.gl.glBlendFunc(sfactor, dfactor);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glClearColor(float, float, float, float)
     */

    public void glClearColor(final float red, final float green,
            final float blue, final float alpha)
    {
        this.gl.glClearColor(red, green, blue, alpha);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glClearDepth(double)
     */

    public void glClearDepth(final double depth)
    {
        this.gl.glClearDepth(depth);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glDepthFunc(int)
     */

    public void glDepthFunc(final int func)
    {
        this.gl.glDepthFunc(func);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glDisable(int)
     */

    public void glDisable(final int cap)
    {
        this.gl.glDisable(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glEnable(int)
     */

    public void glEnable(final int cap)
    {
        this.gl.glEnable(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glHint(int, int)
     */

    public void glHint(final int target, final int mode)
    {
        this.gl.glHint(target, mode);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glLight(int, int, java.nio.FloatBuffer)
     */

    public void glLight(final int light, final int pname,
            final FloatBuffer param)
    {
        this.gl.glLightfv(light, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glShadeModel(int)
     */

    public void glShadeModel(final int mode)
    {
        this.gl.glShadeModel(mode);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glViewport(int, int, int, int)
     */

    public void glViewport(final int x, final int y, final int width,
            final int height)
    {
        this.gl.glViewport(x, y, width, height);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#gluPerspective(float, float, float,
     *      float)
     */

    public void gluPerspective(final float fovy, final float aspect,
            final float zNear, final float zFar)
    {
        this.glu.gluPerspective(fovy, aspect, zNear, zFar);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glBindTexture(int, int)
     */

    public void glBindTexture(final int target, final int texture)
    {
        this.gl.glBindTexture(target, texture);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glDeleteTextures(java.nio.IntBuffer)
     */

    public void glDeleteTextures(final IntBuffer textures)
    {
        this.gl.glDeleteTextures(textures.remaining(), textures);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glGenTextures(java.nio.IntBuffer)
     */

    public void glGenTextures(final IntBuffer textures)
    {
        this.gl.glGenTextures(textures.remaining(), textures);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glTexParameteri(int, int, int)
     */

    public void glTexParameteri(final int target, final int pname,
            final int param)
    {
        this.gl.glTexParameteri(target, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glTexParameterf(int, int, float)
     */

    public void glTexParameterf(final int target, final int pname,
            final float param)
    {
        this.gl.glTexParameterf(target, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glTexImage2D(int, int,
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

        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();

        // create a raster that can be used by OpenGL as a source
        // for a texture
        WritableRaster raster;
        BufferedImage texImage;
        int srcPixelFormat;
        if (bufferedImage.getColorModel().hasAlpha())
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
        g.translate(0, height);
        final AffineTransform t = AffineTransform.getScaleInstance(1, -1);
        g.drawImage(bufferedImage, t, null);

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


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glTexImage2D(int, int, int, int, int,
     *      int, int, int, java.nio.ByteBuffer)
     */

    public void glTexImage2D(final int target, final int level,
            final int internalFormat, final int width, final int height,
            final int border, final int format, final int type,
            final ByteBuffer data)
    {
        this.gl.glTexImage2D(target, level, internalFormat, width, height,
                border, format, type, data);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glLightModelfv(int,
     *      java.nio.FloatBuffer)
     */

    public void glLightModelfv(final int pname, final FloatBuffer params)
    {
        this.gl.glLightModelfv(pname, params);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glGetFloatv(int, java.nio.FloatBuffer)
     */

    public void glGetFloatv(final int pname, final FloatBuffer params)
    {
        this.gl.glGetFloatv(pname, params);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glIsEnabled(int)
     */

    public boolean glIsEnabled(final int cap)
    {
        return this.gl.glIsEnabled(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glGetIntegerv(int, java.nio.IntBuffer)
     */

    public void glGetIntegerv(final int pname, final IntBuffer params)
    {
        this.gl.glGetIntegerv(pname, params);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glLightf(int, int, float)
     */

    public void glLightf(final int light, final int pname, final float param)
    {
        this.gl.glLightf(light, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glTranslate(float, float, float)
     */

    @Override
    public void glTranslate(final float x, final float y, final float z)
    {
        this.gl.glTranslatef(x, y, z);
    }


    /**
     * @see de.ailis.threedee.rendering.opengl.GL#glColorMaterial(int, int)
     */

    @Override
    public void glColorMaterial(final int face, final int mode)
    {
        this.gl.glColorMaterial(face, mode);
    }
}
