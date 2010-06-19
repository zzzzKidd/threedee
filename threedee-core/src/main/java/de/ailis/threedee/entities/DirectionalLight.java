/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import de.ailis.threedee.model.Color;


/**
 * A directional light.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class DirectionalLight extends Light
{
    /**
     * Creates a new light with default colors (White).
     */

    public DirectionalLight()
    {
        super();
    }


    /**
     * Constructs a new light with the specified color.
     *
     * @param color
     *            The color of the light
     */

    public DirectionalLight(final Color color)
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

    public DirectionalLight(final Color ambientColor, final Color specularColor,
            final Color diffuseColor)
    {
        super(ambientColor, specularColor, diffuseColor);
    }
}