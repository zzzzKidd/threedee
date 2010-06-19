/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;

import de.ailis.threedee.model.Color;


/**
 * A ambient light.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class AmbientLight extends Light
{
    /**
     * Creates a new light with default colors (White).
     */

    public AmbientLight()
    {
        this(Color.WHITE);
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
    }
}