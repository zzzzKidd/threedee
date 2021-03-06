/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.android;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLU;
import android.opengl.GLUtils;
import de.ailis.threedee.rendering.GL;


/**
 * Android implementation of the ThreeDee GL context.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AndroidGL implements GL
{
    /** The android GL context */
    private GL11 gl;


    /**
     * @see de.ailis.threedee.rendering.GL#init()
     */

    @Override
    public void init()
    {
        // Empty
    }


    /**
     * Sets the android GL context.
     *
     * @param gl
     *            The android GL context
     */

    public void setGL(final GL11 gl)
    {
        this.gl = gl;
    }


    /**
     * @see GL#glColorPointer(int, int, java.nio.FloatBuffer)
     */

    @Override
    public void glColorPointer(final int size, final int stride,
            final FloatBuffer pointer)
    {
        this.gl.glColorPointer(size, GL10.GL_FLOAT, stride, pointer);
    }


    /**
     * @see GL#glMaterial(int, int, java.nio.FloatBuffer)
     */

    @Override
    public void glMaterial(final int face, final int pname,
            final FloatBuffer param)
    {
        this.gl.glMaterialfv(face, pname, param);
    }


    /**
     * @see GL#glMaterialf(int, int, float)
     */

    @Override
    public void glMaterialf(final int face, final int pname, final float param)
    {
        this.gl.glMaterialf(face, pname, param);
    }


    /**
     * @see GL#glDisableClientState(int)
     */

    @Override
    public void glDisableClientState(final int cap)
    {
        this.gl.glDisableClientState(cap);
    }


    /**
     * @see GL#glEnableClientState(int)
     */

    @Override
    public void glEnableClientState(final int cap)
    {
        this.gl.glEnableClientState(cap);
    }


    /**
     * @see GL#glVertexPointer(int, int, java.nio.FloatBuffer)
     */

    @Override
    public void glVertexPointer(final int size, final int stride,
            final FloatBuffer pointer)
    {
        this.gl.glVertexPointer(size, GL10.GL_FLOAT, stride, pointer);
    }


    /**
     * @see GL#glNormalPointer(int, java.nio.FloatBuffer)
     */

    @Override
    public void glNormalPointer(final int stride, final FloatBuffer pointer)
    {
        this.gl.glNormalPointer(GL10.GL_FLOAT, stride, pointer);
    }


    /**
     * @see GL#glTexCoordPointer(int, int, java.nio.FloatBuffer)
     */

    @Override
    public void glTexCoordPointer(final int size, final int stride,
            final FloatBuffer pointer)
    {
        this.gl.glTexCoordPointer(size, GL10.GL_FLOAT, stride, pointer);
    }


    /**
     * @see GL#glDrawElements(int, int, java.nio.Buffer)
     */

    @Override
    public void glDrawElements(final int mode, final int type,
            final Buffer indices)
    {
        this.gl.glDrawElements(mode, indices.remaining(), type, indices);
    }


    /**
     * @see GL#glMultMatrix(java.nio.FloatBuffer)
     */

    @Override
    public void glMultMatrix(final FloatBuffer m)
    {
        this.gl.glMultMatrixf(m);
    }


    /**
     * @see GL#glPopMatrix()
     */

    @Override
    public void glPopMatrix()
    {
        this.gl.glPopMatrix();
    }


    /**
     * @see GL#glPushMatrix()
     */

    @Override
    public void glPushMatrix()
    {
        this.gl.glPushMatrix();
    }


    /**
     * @see GL#glClear(int)
     */

    @Override
    public void glClear(final int mask)
    {
        this.gl.glClear(mask);
    }


    /**
     * @see GL#glFlush()
     */

    @Override
    public void glFlush()
    {
        this.gl.glFlush();
    }


    /**
     * @see GL#glLoadIdentity()
     */

    @Override
    public void glLoadIdentity()
    {
        this.gl.glLoadIdentity();
    }


    /**
     * @see GL#glMatrixMode(int)
     */

    @Override
    public void glMatrixMode(final int mode)
    {
        this.gl.glMatrixMode(mode);
    }


    /**
     * @see GL#glBlendFunc(int, int)
     */

    @Override
    public void glBlendFunc(final int sfactor, final int dfactor)
    {
        this.gl.glBlendFunc(sfactor, dfactor);
    }


    /**
     * @see GL#glClearColor(float, float, float, float)
     */

    @Override
    public void glClearColor(final float red, final float green,
            final float blue, final float alpha)
    {
        this.gl.glClearColor(red, green, blue, alpha);
    }


    /**
     * @see GL#glClearDepth(double)
     */

    @Override
    public void glClearDepth(final double depth)
    {
        this.gl.glClearDepthf((float) depth);
    }


    /**
     * @see GL#glDepthFunc(int)
     */

    @Override
    public void glDepthFunc(final int func)
    {
        this.gl.glDepthFunc(func);
    }


    /**
     * @see GL#glDisable(int)
     */

    @Override
    public void glDisable(final int cap)
    {
        this.gl.glDisable(cap);
    }


    /**
     * @see GL#glEnable(int)
     */

    @Override
    public void glEnable(final int cap)
    {
        this.gl.glEnable(cap);
    }


    /**
     * @see GL#glHint(int, int)
     */

    @Override
    public void glHint(final int target, final int mode)
    {
        this.gl.glHint(target, mode);
    }


    /**
     * @see GL#glLight(int, int, java.nio.FloatBuffer)
     */

    @Override
    public void glLight(final int light, final int pname,
            final FloatBuffer params)
    {
        this.gl.glLightfv(light, pname, params);
    }


    /**
     * @see GL#glShadeModel(int)
     */

    @Override
    public void glShadeModel(final int mode)
    {
        this.gl.glShadeModel(mode);
    }


    /**
     * @see GL#glViewport(int, int, int, int)
     */

    @Override
    public void glViewport(final int x, final int y, final int width,
            final int height)
    {
        this.gl.glViewport(x, y, width, height);
    }


    /**
     * @see GL#gluPerspective(float, float, float, float)
     */

    @Override
    public void gluPerspective(final float fovy, final float aspect,
            final float zNear, final float zFar)
    {
        GLU.gluPerspective(this.gl, fovy, aspect, zNear, zFar);
    }


    /**
     * @see GL#glBindTexture(int, int)
     */

    @Override
    public void glBindTexture(final int target, final int texture)
    {
        this.gl.glBindTexture(target, texture);
    }


    /**
     * @see GL#glDeleteTextures(java.nio.IntBuffer)
     */

    @Override
    public void glDeleteTextures(final IntBuffer textures)
    {
        this.gl.glDeleteTextures(textures.remaining(), textures);
    }


    /**
     * @see GL#glGenTextures(java.nio.IntBuffer)
     */

    @Override
    public void glGenTextures(final IntBuffer textures)
    {
        this.gl.glGenTextures(textures.remaining(), textures);
    }


    /**
     * @see GL#glTexParameteri(int, int, int)
     */

    @Override
    public void glTexParameteri(final int target, final int pname,
            final int param)
    {
        this.gl.glTexParameteri(target, pname, param);
    }


    /**
     * @see GL#glTexImage2D(int, int, java.io.InputStream, int)
     */

    @Override
    public void glTexImage2D(final int target, final int level,
            final InputStream stream, final int border)
    {
        final Bitmap tmp = BitmapFactory.decodeStream(stream);
        final Matrix flip = new Matrix();
        flip.postScale(1f, -1f);
        final Bitmap bitmap = Bitmap.createBitmap(tmp, 0, 0, tmp.getWidth(),
                tmp.getHeight(), flip, true);
        try
        {
            stream.close();
        }
        catch (final IOException e)
        {
            throw new RuntimeException(e.toString(), e);
        }
        GLUtils.texImage2D(target, level, bitmap, border);
        bitmap.recycle();
    }


    /**
     * @see GL#glLightModelfv(int, java.nio.FloatBuffer)
     */

    @Override
    public void glLightModelfv(final int pname, final FloatBuffer params)
    {
        this.gl.glLightModelfv(pname, params);
    }


    /**
     * @see GL#glGetFloatv(int, java.nio.FloatBuffer)
     */

    @Override
    public void glGetFloatv(final int pname, final FloatBuffer params)
    {
        this.gl.glGetFloatv(pname, params);
    }


    /**
     * @see GL#glIsEnabled(int)
     */

    @Override
    public boolean glIsEnabled(final int cap)
    {
        return this.gl.glIsEnabled(cap);
    }


    /**
     * @see GL#glGetIntegerv(int, java.nio.IntBuffer)
     */

    @Override
    public void glGetIntegerv(final int pname, final IntBuffer params)
    {
        this.gl.glGetIntegerv(pname, params);
    }


    /**
     * @see GL#glLightf(int, int, float)
     */

    @Override
    public void glLightf(final int light, final int pname, final float param)
    {
        this.gl.glLightf(light, pname, param);
    }


    /**
     * @see GL#glTexImage2D(int, int, int, int, int, int, int, int,
     *      java.nio.ByteBuffer)
     */

    @Override
    public void glTexImage2D(final int target, final int level,
            final int internalFormat, final int width, final int height,
            final int border, final int format, final int type,
            final ByteBuffer data)
    {
        this.gl.glTexImage2D(target, level, internalFormat, width, height,
                border, format, type, data);
    }


    /**
     * @see GL#glTranslate(float, float, float)
     */

    @Override
    public void glTranslate(final float x, final float y, final float z)
    {
        this.gl.glTranslatef(x, y, z);
    }


    /**
     * @see GL#glTexParameterf(int, int, float)
     */

    @Override
    public void glTexParameterf(final int target, final int pname,
            final float param)
    {
        this.gl.glTexParameterf(target, pname, param);
    }


    /**
     * @see GL#glColorMaterial(int, int)
     */

    @Override
    public void glColorMaterial(final int face, final int mode)
    {
        // Not supported
    }


    /**
     * @see GL#glTexSubImage2D(int, int, int, int, int, int, int, int,
     *      java.nio.Buffer)
     */

    @Override
    public void glTexSubImage2D(final int target, final int level,
            final int xOffset, final int yOffset, final int width,
            final int height, final int format, final int type,
            final Buffer data)
    {
        this.gl.glTexSubImage2D(target, level, xOffset, yOffset, width, height,
                format, type, data);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glRotatef(float, float, float, float)
     */

    @Override
    public void glRotatef(final float angle, final float x, final float y,
            final float z)
    {
        this.gl.glRotatef(angle, x, y, z);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glScalef(float, float, float)
     */

    @Override
    public void glScalef(final float x, final float y, final float z)
    {
        this.gl.glScalef(x, y, z);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glPixelStorei(int, int)
     */

    @Override
    public void glPixelStorei(final int pname, final int param)
    {
        this.gl.glPixelStorei(pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glIsTexture(int)
     */

    @Override
    public boolean glIsTexture(final int texture)
    {
        return this.gl.glIsTexture(texture);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glTexEnvi(int, int, int)
     */

    @Override
    public void glTexEnvi(final int target, final int pname, final int param)
    {
        this.gl.glTexEnvi(target, pname, param);
    }
}
