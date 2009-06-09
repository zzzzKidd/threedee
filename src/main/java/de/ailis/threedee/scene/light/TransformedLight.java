/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene.light;

import de.ailis.threedee.math.Matrix4d;
import de.ailis.threedee.math.Vector3d;


/**
 * A transformed light. This is simply a container for a light source with a
 * connected transformation.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class TransformedLight
{
    /** The light */
    private final Light light;

    /** The transformation */
    private final Matrix4d transform;

    /** The position of the light in the scene */
    private final Vector3d position;
    
    
    /**
     * Constructs a new light with the specified light and transformation.
     * 
     * @param light
     *            The light
     * @param transform
     *            The transformation
     */

    public TransformedLight(final Light light, final Matrix4d transform)
    {
        this.light = light;
        this.transform = transform;
        this.position = new Vector3d(0, 0, 0).multiply(transform);
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
     * Returns the transformation.
     * 
     * @return The transformation
     */

    public Matrix4d getTransform()
    {
        return this.transform;
    }
    
    
    /**
     * Returns the position of the light in the scene.
     * 
     * @return The light position
     */
    
    public Vector3d getPosition()
    {
        return this.position;
    }
}
