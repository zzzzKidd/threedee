/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.jogl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.glu.GLU;

import de.ailis.threedee.java2d.Java2DGL;


/**
 * JoGL implementation of the ThreeDee GL context.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class JoGL extends Java2DGL
{
    /** The real GL interface */
    private final javax.media.opengl.GL gl;

    /** The real GLU interface */
    private final javax.media.opengl.glu.GLU glu;


    /**
     * Private constructor to prevent instantiation from outside. Use
     * getInstance instead.
     *
     * @param gl
     *            The real GL interface
     * @param glu
     *            The real GLU interface
     */

    public JoGL(final javax.media.opengl.GL gl, final GLU glu)
    {
        this.gl = gl;
        this.glu = glu;
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glColorPointer(int, int, FloatBuffer)
     */

    @Override
    public void glColorPointer(final int size, final int stride,
            final FloatBuffer pointer)
    {
        this.gl.glColorPointer(size, GL_FLOAT, stride, pointer);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glMaterial(int, int,
     *      java.nio.FloatBuffer)
     */

    @Override
    public void glMaterial(final int face, final int pname,
            final FloatBuffer params)
    {
        this.gl.glMaterialfv(face, pname, params);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glMaterialf(int, int, float)
     */

    @Override
    public void glMaterialf(final int face, final int pname, final float param)
    {
        this.gl.glMaterialf(face, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glDisableClientState(int)
     */

    @Override
    public void glDisableClientState(final int cap)
    {
        this.gl.glDisableClientState(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glEnableClientState(int)
     */

    @Override
    public void glEnableClientState(final int cap)
    {
        this.gl.glEnableClientState(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glVertexPointer(int, int,
     *      java.nio.FloatBuffer)
     */

    @Override
    public void glVertexPointer(final int size, final int stride,
            final FloatBuffer pointer)
    {
        this.gl.glVertexPointer(size, GL_FLOAT, stride, pointer);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glNormalPointer(int,
     *      java.nio.FloatBuffer)
     */

    @Override
    public void glNormalPointer(final int stride, final FloatBuffer pointer)
    {
        this.gl.glNormalPointer(GL_FLOAT, stride, pointer);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glTexCoordPointer(int, int,
     *      java.nio.FloatBuffer)
     */

    @Override
    public void glTexCoordPointer(final int size, final int stride,
            final FloatBuffer pointer)
    {
        this.gl.glTexCoordPointer(size, GL_FLOAT, stride, pointer);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glDrawElements(int, int,
     *      java.nio.Buffer)
     */

    @Override
    public void glDrawElements(final int mode, final int type,
            final Buffer indices)
    {
        this.gl.glDrawElements(mode, indices.remaining(), type, indices);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glMultMatrix(java.nio.FloatBuffer)
     */

    @Override
    public void glMultMatrix(final FloatBuffer m)
    {
        this.gl.glMultMatrixf(m);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glPopMatrix()
     */

    @Override
    public void glPopMatrix()
    {
        this.gl.glPopMatrix();
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glPushMatrix()
     */

    @Override
    public void glPushMatrix()
    {
        this.gl.glPushMatrix();
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glClear(int)
     */

    @Override
    public void glClear(final int mask)
    {
        this.gl.glClear(mask);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glFlush()
     */

    @Override
    public void glFlush()
    {
        this.gl.glFlush();
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glLoadIdentity()
     */

    @Override
    public void glLoadIdentity()
    {
        this.gl.glLoadIdentity();
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glMatrixMode(int)
     */

    @Override
    public void glMatrixMode(final int mode)
    {
        this.gl.glMatrixMode(mode);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glBlendFunc(int, int)
     */

    @Override
    public void glBlendFunc(final int sfactor, final int dfactor)
    {
        this.gl.glBlendFunc(sfactor, dfactor);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glClearColor(float, float, float,
     *      float)
     */

    @Override
    public void glClearColor(final float red, final float green,
            final float blue, final float alpha)
    {
        this.gl.glClearColor(red, green, blue, alpha);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glClearDepth(double)
     */

    @Override
    public void glClearDepth(final double depth)
    {
        this.gl.glClearDepth(depth);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glDepthFunc(int)
     */

    @Override
    public void glDepthFunc(final int func)
    {
        this.gl.glDepthFunc(func);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glDisable(int)
     */

    @Override
    public void glDisable(final int cap)
    {
        this.gl.glDisable(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glEnable(int)
     */

    @Override
    public void glEnable(final int cap)
    {
        this.gl.glEnable(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glHint(int, int)
     */

    @Override
    public void glHint(final int target, final int mode)
    {
        this.gl.glHint(target, mode);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glLight(int, int,
     *      java.nio.FloatBuffer)
     */

    @Override
    public void glLight(final int light, final int pname,
            final FloatBuffer param)
    {
        this.gl.glLightfv(light, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glShadeModel(int)
     */

    @Override
    public void glShadeModel(final int mode)
    {
        this.gl.glShadeModel(mode);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glViewport(int, int, int, int)
     */

    @Override
    public void glViewport(final int x, final int y, final int width,
            final int height)
    {
        this.gl.glViewport(x, y, width, height);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#gluPerspective(float, float, float,
     *      float)
     */

    @Override
    public void gluPerspective(final float fovy, final float aspect,
            final float zNear, final float zFar)
    {
        this.glu.gluPerspective(fovy, aspect, zNear, zFar);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glBindTexture(int, int)
     */

    @Override
    public void glBindTexture(final int target, final int texture)
    {
        this.gl.glBindTexture(target, texture);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glDeleteTextures(java.nio.IntBuffer)
     */

    @Override
    public void glDeleteTextures(final IntBuffer textures)
    {
        this.gl.glDeleteTextures(textures.remaining(), textures);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glGenTextures(java.nio.IntBuffer)
     */

    @Override
    public void glGenTextures(final IntBuffer textures)
    {
        this.gl.glGenTextures(textures.remaining(), textures);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glTexParameteri(int, int, int)
     */

    @Override
    public void glTexParameteri(final int target, final int pname,
            final int param)
    {
        this.gl.glTexParameteri(target, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glTexParameterf(int, int, float)
     */

    @Override
    public void glTexParameterf(final int target, final int pname,
            final float param)
    {
        this.gl.glTexParameterf(target, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glTexImage2D(int, int, int, int, int,
     *      int, int, int, java.nio.ByteBuffer)
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
     * @see de.ailis.threedee.rendering.GL#glLightModelfv(int,
     *      java.nio.FloatBuffer)
     */

    @Override
    public void glLightModelfv(final int pname, final FloatBuffer params)
    {
        this.gl.glLightModelfv(pname, params);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glGetFloatv(int,
     *      java.nio.FloatBuffer)
     */

    @Override
    public void glGetFloatv(final int pname, final FloatBuffer params)
    {
        this.gl.glGetFloatv(pname, params);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glIsEnabled(int)
     */

    @Override
    public boolean glIsEnabled(final int cap)
    {
        return this.gl.glIsEnabled(cap);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glGetIntegerv(int,
     *      java.nio.IntBuffer)
     */

    @Override
    public void glGetIntegerv(final int pname, final IntBuffer params)
    {
        this.gl.glGetIntegerv(pname, params);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glLightf(int, int, float)
     */

    @Override
    public void glLightf(final int light, final int pname, final float param)
    {
        this.gl.glLightf(light, pname, param);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glTranslate(float, float, float)
     */

    @Override
    public void glTranslate(final float x, final float y, final float z)
    {
        this.gl.glTranslatef(x, y, z);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glColorMaterial(int, int)
     */

    @Override
    public void glColorMaterial(final int face, final int mode)
    {
        this.gl.glColorMaterial(face, mode);
    }


    /**
     * @see de.ailis.threedee.rendering.GL#glTexSubImage2D(int, int, int, int,
     *      int, int, int, int, java.nio.Buffer)
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
}
