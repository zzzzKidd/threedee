/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene;

import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.scene.light.Light;
import de.ailis.threedee.scene.rendering.PolygonBuffer;


/**
 * A light node.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class LightNode extends SceneNode
{
    /** The light */
    private Light light;


    /**
     * Constructs a new light node with the specified light.
     * 
     * @param light
     *            The light to set
     */

    public LightNode(final Light light)
    {
        setLight(light);
    }


    /**
     * Sets the light.
     * 
     * @param light
     *            The light to set
     */

    public void setLight(final Light light)
    {
        if (light == null)
            throw new IllegalArgumentException("light must not be null");
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
     * @see de.ailis.threedee.scene.SceneNode#render(de.ailis.threedee.scene.rendering.PolygonBuffer, de.ailis.threedee.math.Matrix4d)
     */
    
    @Override
    public void render(final PolygonBuffer buffer, final Matrix4d transform)
    {
        buffer.add(this.light, transform);
    }
}
