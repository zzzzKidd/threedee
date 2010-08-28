/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import de.ailis.gramath.Color4f;
import de.ailis.threedee.exceptions.LightException;
import de.ailis.threedee.rendering.GL;
import de.ailis.threedee.rendering.Viewport;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A light node.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class Light extends SceneNode
{
    /** The currently associated light id */
    private int lightId = -1;

    /** The next free light id */
    private static int nextLightId = GL.GL_LIGHT0;

    /** The maximum lights */
    private static int maxLights = -1;

    /** Direction for a spot light */
    private static final FloatBuffer direction = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(3).put(0).put(0).put(-1).rewind();

    /** The ambient color of the light */
    private Color4f ambientColor = Color4f.BLACK;

    /** The specular color of the light */
    private Color4f specularColor = Color4f.WHITE;

    /** The diffuse color of the light */
    private Color4f diffuseColor = Color4f.WHITE;

    /** The light position */
    protected FloatBuffer position;


    /**
     * Creates a new light with default colors (White).
     */

    public Light()
    {
        this(Color4f.BLACK, Color4f.WHITE, Color4f.WHITE);
    }


    /**
     * Constructs a new light with the specified color.
     *
     * @param color
     *            The color of the light
     */

    public Light(final Color4f color)
    {
        this(Color4f.BLACK, color, color);
    }


    /**
     * Constructs a new light with the specified colors.
     *
     * @param ambientColor
     *            The ambient color
     * @param specularColor
     *            The specular color
     * @param diffuseColor
     *            The diffuse color
     */

    public Light(final Color4f ambientColor, final Color4f specularColor,
            final Color4f diffuseColor)
    {
        this.ambientColor = ambientColor.asImmutable();
        this.specularColor = specularColor.asImmutable();
        this.diffuseColor = diffuseColor.asImmutable();
    }


    /**
     * Returns the ambient color.
     *
     * @return The ambient color
     */

    public Color4f getAmbientColor()
    {
        return this.ambientColor;
    }


    /**
     * Sets the ambient color.
     *
     * @param ambientColor
     *            The ambient color to set
     */

    public void setAmbientColor(final Color4f ambientColor)
    {
        this.ambientColor = ambientColor.asImmutable();
    }


    /**
     * Returns the specular color.
     *
     * @return The specular color
     */

    public Color4f getSpecularColor()
    {
        return this.specularColor;
    }


    /**
     * Sets the specular color.
     *
     * @param specularColor
     *            The specularColor to set
     */

    public void setSpecularColor(final Color4f specularColor)
    {
        this.specularColor = specularColor.asImmutable();
    }


    /**
     * Returns the diffuse color.
     *
     * @return The diffuse color
     */

    public Color4f getDiffuseColor()
    {
        return this.diffuseColor;
    }


    /**
     * Sets the diffuse color.
     *
     * @param diffuseColor
     *            The diffuse color to set
     */

    public void setDiffuseColor(final Color4f diffuseColor)
    {
        this.diffuseColor = diffuseColor.asImmutable();
    }


    /**
     * Sets the light color. This sets the ambient, diffuse and specular
     * color to the same value.
     *
     * @param color
     *            The color to set
     */

    public void setColor(final Color4f color)
    {
        setAmbientColor(Color4f.BLACK);
        setDiffuseColor(color);
        setSpecularColor(Color4f.BLACK);
    }


    /**
     * Returns the cut off angle in degree. For a point light or a directional
     * light this always returns 180.
     *
     * @return The cut off angle in degree.
     */

    public float getCutOff()
    {
        return 180f;
    }


    /**
     * Applies the light.
     *
     * @param viewport
     *            The viewport
     */

    public void apply(final Viewport viewport)
    {
        final GL gl = viewport.getGL();
        final float cutOff = getCutOff();

        final int index = getLightId(gl);
        gl.glLight(index, GL.GL_POSITION, this.position);
        if (cutOff < 180f)
        {
            gl.glLight(index, GL.GL_SPOT_DIRECTION, direction);
            gl.glLightf(index, GL.GL_SPOT_CUTOFF, cutOff);
        }
        else
        {
            gl.glLightf(index, GL.GL_SPOT_CUTOFF, 180f);
        }
        gl.glLight(index, GL.GL_AMBIENT, this.ambientColor.getBuffer());
        gl.glLight(index, GL.GL_DIFFUSE, this.diffuseColor.getBuffer());
        gl.glLight(index, GL.GL_SPECULAR, this.specularColor

                .getBuffer());
        gl.glEnable(index);
    }


    /**
     * Removes the light.
     *
     * @param viewport
     *            The viewport
     */

    public void remove(final Viewport viewport)
    {
        viewport.getGL().glDisable(this.lightId);
        this.lightId = -1;
        nextLightId--;
    }


    /**
     * Returns the light id for this light. If it has none yet then a new
     * one is reserved.
     *
     * @param gl
     *            The GL context
     * @return The light index
     */

    private int getLightId(final GL gl)
    {
        // If light has already a light id then return this one
        int id = this.lightId;
        if (id >= 0) return id;

        // Get the maximum number of lights once
        if (maxLights == -1)
        {
            final IntBuffer buffer = BufferUtils.createDirectIntegerBuffer(1);
            gl.glGetIntegerv(GL.GL_MAX_LIGHTS, buffer);
            maxLights = buffer.get(0);
        }

        // Check if there is a light index available
        if (nextLightId >= maxLights + GL.GL_LIGHT0 -1)
            throw new LightException("Too many lights active (Max is "
                    + maxLights + ")");

        // Claim light index and return it
        id = nextLightId++;
        this.lightId = id;
        return id;
    }
}
