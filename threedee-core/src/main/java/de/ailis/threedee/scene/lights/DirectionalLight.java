/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.lights;

import java.nio.FloatBuffer;

import de.ailis.gramath.Color4f;
import de.ailis.threedee.scene.Light;
import de.ailis.threedee.scene.SceneNode;
import de.ailis.threedee.utils.BufferUtils;


/**
 * A directional light.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class DirectionalLight extends Light
{
    /** Position for a directional light */
    private final static FloatBuffer directionalLightPosition = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(1).put(0).rewind();

    /**
     * Creates a new light with default colors (White).
     */

    public DirectionalLight()
    {
        super();
        this.position = directionalLightPosition;
    }


    /**
     * Constructs a new light with the specified color.
     *
     * @param color
     *            The color of the light
     */

    public DirectionalLight(final Color4f color)
    {
        super(color);
        this.position = directionalLightPosition;
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

    public DirectionalLight(final Color4f ambientColor,
        final Color4f specularColor,
            final Color4f diffuseColor)
    {
        super(ambientColor, specularColor, diffuseColor);
        this.position = directionalLightPosition;
    }


    /**
     * @see java.lang.Object#clone()
     */

    @Override
    public DirectionalLight clone()
    {
        final DirectionalLight light = new DirectionalLight(getAmbientColor(),
            getSpecularColor(), getDiffuseColor());
        light.setTransform(getTransform());
        SceneNode child = getFirstChild();
        while (child != null)
        {
            light.appendChild(child.clone());
            child = child.getNextSibling();
        }
        return light;
    }
}
