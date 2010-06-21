/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.lights;

import java.nio.IntBuffer;

import de.ailis.threedee.entities.Color;
import de.ailis.threedee.entities.SceneNode;
import de.ailis.threedee.exceptions.LightException;
import de.ailis.threedee.opengl.GL;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A light node.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public abstract class Light extends SceneNode
{
    /** The next free light index */
    private static int nextIndex = GL.GL_LIGHT0;

    /** The maximum light index */
    private static int maxLightIndex = GL.GL_LIGHT7;

    /** The ambient color of the light */
    private Color ambientColor = Color.BLACK;

    /** The specular color of the light */
    private Color specularColor = Color.WHITE;

    /** The diffuse color of the light */
    private Color diffuseColor = Color.WHITE;

    /** The light index of this light */
    private int index = -1;


    /**
     * Returns the light index.
     *
     * @param gl
     *            The GL context
     * @return The light index
     */

    protected int getLightIndex(final GL gl)
    {
        // If we already have an index, then return this one
        if (this.index != -1) return this.index;

        // Get the maximum number of lights once
        if (maxLightIndex == -1)
        {
            final IntBuffer buffer = BufferUtils.createDirectIntegerBuffer(1);
            gl.glGetIntegerv(GL.GL_MAX_LIGHTS, buffer);
            maxLightIndex = buffer.get(0);
        }

        // Check if there is a light index available
        if (nextIndex >= maxLightIndex)
            throw new LightException("Too many lights active (Max is "
                + maxLightIndex + ")");

        // Claim light index and return it
        this.index = nextIndex++;
        return this.index;
    }


    /**
     * Applies the light to the specified GL context.
     *
     * @param gl
     *            The GL context
     */

    public void apply(final GL gl)
    {
        final int index = getLightIndex(gl);
        gl.glLight(index, GL.GL_AMBIENT, this.ambientColor.getBuffer());
        gl.glLight(index, GL.GL_DIFFUSE, this.diffuseColor.getBuffer());
        gl.glLight(index, GL.GL_SPECULAR, this.specularColor.getBuffer());
        gl.glEnable(index);
    }


    /**
     * Removes the current light from the specified GL context.
     *
     * @param gl
     *            The GL context
     */

    public void remove(final GL gl)
    {
        gl.glDisable(this.index);
        this.index = -1;
        nextIndex--;
    }


    /**
     * Returns the ambient color.
     *
     * @return The ambient color
     */

    public Color getAmbientColor()
    {
        return this.ambientColor;
    }


    /**
     * Sets the ambient color.
     *
     * @param ambientColor
     *            The ambient color to set
     */

    public void setAmbientColor(final Color ambientColor)
    {
        this.ambientColor = ambientColor;
    }


    /**
     * Returns the specular color.
     *
     * @return The specular color
     */

    public Color getSpecularColor()
    {
        return this.specularColor;
    }


    /**
     * Sets the specular color.
     *
     * @param specularColor
     *            The specularColor to set
     */

    public void setSpecularColor(final Color specularColor)
    {
        this.specularColor = specularColor;
    }


    /**
     * Returns the diffuse color.
     *
     * @return The diffuse color
     */

    public Color getDiffuseColor()
    {
        return this.diffuseColor;
    }


    /**
     * Sets the diffuse color.
     *
     * @param diffuseColor
     *            The diffuse color to set
     */

    public void setDiffuseColor(final Color diffuseColor)
    {
        this.diffuseColor = diffuseColor;
    }
}