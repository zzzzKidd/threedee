/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.lights;

import java.nio.FloatBuffer;

import de.ailis.threedee.entities.Color;
import de.ailis.threedee.scene.Light;
import de.ailis.threedee.utils.BufferUtils;



/**
 * A point light.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class PointLight extends Light
{
    /** Position for a point light */
    private final static FloatBuffer pointLightPosition = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(0).put(1).rewind();

    /**
     * Creates a new light with default colors (White).
     */

    public PointLight()
    {
        super();
        this.position = pointLightPosition;
    }


    /**
     * Constructs a new light with the specified color.
     *
     * @param color
     *            The color of the light
     */

    public PointLight(final Color color)
    {
        super(color);
        this.position = pointLightPosition;
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

    public PointLight(final Color ambientColor, final Color specularColor,
            final Color diffuseColor)
    {
        super(ambientColor, specularColor, diffuseColor);
        this.position = pointLightPosition;
    }
}