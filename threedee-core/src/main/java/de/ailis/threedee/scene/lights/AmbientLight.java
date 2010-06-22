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
        this(Color.WHITE);
        this.position = ambientLightPosition;
    }


    /**
     * Constructs a new light with the specified color.
     *
     * @param color
     *            The color of the light
     */

    public AmbientLight(final Color color)
    {
        super(color, Color.BLACK, Color.BLACK);
        this.position = ambientLightPosition;
    }
}