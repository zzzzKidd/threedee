/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.lights;

import java.nio.FloatBuffer;

import de.ailis.gramath.Color4f;
import de.ailis.threedee.scene.Light;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A spot light.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class SpotLight extends Light
{
    /** Position for a directional light */
    private final static FloatBuffer spotLightPosition = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(0).put(1).rewind();

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

    public SpotLight(final Color4f color)
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

    public SpotLight(final Color4f ambientColor, final Color4f specularColor,
            final Color4f diffuseColor)
    {
        super(ambientColor, specularColor, diffuseColor);
        this.position = spotLightPosition;
    }


    /**
     * @see de.ailis.threedee.scene.Light#getCutOff()
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