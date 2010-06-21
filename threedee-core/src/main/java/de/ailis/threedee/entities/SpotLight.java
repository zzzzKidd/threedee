/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;



/**
 * A spot light.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class SpotLight extends Light
{
    /**
     * Creates a new light with default colors (White).
     */

    public SpotLight()
    {
        super();
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
    }
}