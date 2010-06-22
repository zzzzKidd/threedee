/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import java.nio.FloatBuffer;

import de.ailis.threedee.utils.BufferUtils;


/**
 * A spot light.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class SpotLight extends LightNode
{
    /** Position for a directional light */
    private final static FloatBuffer spotLightPosition = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(1).put(0).rewind();

    /** The cut off angle in degree */
    private float cutOff = 180f;

    /**
     * Creates a new light with default colors (White).
     */

    public SpotLight()
    {
        super();
        this.position = spotLightPosition;
    }


    /**
     * Constructs a new light with the specified color.
     *
     * @param color
     *            The color of the light
     */

    public SpotLight(final Color color)
    {
        super(color);
        this.position = spotLightPosition;
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

    public SpotLight(final Color ambientColor, final Color specularColor,
            final Color diffuseColor)
    {
        super(ambientColor, specularColor, diffuseColor);
        this.position = spotLightPosition;
    }


    /**
     * @see de.ailis.threedee.entities.LightNode#getCutOff()
     */

    @Override
    public float getCutOff()
    {
        return this.cutOff;
    }


    /**
     * Sets the cut off angle in degree.
     *
     * @param cutOff
     *            The cut off angle to set
     */

    public void setCutOff(final float cutOff)
    {
        this.cutOff = cutOff;
    }
}