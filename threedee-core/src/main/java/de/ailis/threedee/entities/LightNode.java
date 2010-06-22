/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import de.ailis.threedee.exceptions.LightException;
import de.ailis.threedee.rendering.opengl.GL;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A light node.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class LightNode extends SceneNode
{
    /** The light */
    private final Light light;

    /** The currently associated light id */
    private int lightId = -1;

    /** The next free light id */
    private static int nextLightId = GL.GL_LIGHT0;

    /** The maximum lights */
    private static int maxLights = -1;

    /** Position for a directional light */
    private final static FloatBuffer directionalLightPosition = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(1).put(0).rewind();

    /** Position for a point light */
    private final static FloatBuffer pointLightPosition = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(0).put(1).rewind();

    /** Direction for a spot light */
    private static final FloatBuffer spotLightDirection = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(-1).put(1).rewind();


    /**
     * Constructor
     *
     * @param light
     *            The light to instance
     */

    public LightNode(final Light light)
    {
        this.light = light;
    }


    /**
     * Returns the light.
     *
     * @return The light
     */

    public Light getLight()
    {
        return this.light;
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

        final Light light = this.light;
        final int index = getLightId(gl);
        if (light instanceof DirectionalLight)
            gl.glLight(index, GL.GL_POSITION, directionalLightPosition);
        else
            gl.glLight(index, GL.GL_POSITION, pointLightPosition);
        if (light instanceof SpotLight)
        {
            gl.glLight(index, GL.GL_SPOT_DIRECTION, spotLightDirection);
            gl.glLightf(index, GL.GL_SPOT_CUTOFF, 90f);
        }
        else
        {
            gl.glLightf(index, GL.GL_SPOT_CUTOFF, 180f);
        }
        gl.glLight(index, GL.GL_AMBIENT, light.getAmbientColor().getBuffer());
        gl.glLight(index, GL.GL_DIFFUSE, light.getDiffuseColor().getBuffer());
        gl.glLight(index, GL.GL_SPECULAR, light.getSpecularColor().getBuffer());
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
