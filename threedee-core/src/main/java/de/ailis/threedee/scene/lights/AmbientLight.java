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
 * A ambient light.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class AmbientLight extends Light
{
    /** Position for a point light */
    private final static FloatBuffer ambientLightPosition = (FloatBuffer) BufferUtils
            .createDirectFloatBuffer(4).put(0).put(0).put(0).put(1).rewind();

    /**
     * Creates a new light with default colors (White).
     */

    public AmbientLight()
    {
        this(Color4f.WHITE);
        this.position = ambientLightPosition;
    }


    /**
     * Constructs a new light with the specified color.
     *
     * @param color
     *            The color of the light
     */

    public AmbientLight(final Color4f color)
    {
        super(color, Color4f.BLACK, Color4f.BLACK);
        this.position = ambientLightPosition;
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

    public AmbientLight(final Color4f ambientColor,
        final Color4f specularColor,
            final Color4f diffuseColor)
    {
        super(ambientColor, specularColor, diffuseColor);
        this.position = ambientLightPosition;
    }


    /**
     * @see java.lang.Object#clone()
     */

    @Override
    public AmbientLight clone()
    {
        final AmbientLight light = new AmbientLight(getAmbientColor(),
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
