/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.entities;


/**
 * A instance of a light.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class LightInstance
{
    /** The light */
    private final Light light;

    /** The scene node the light is connected to */
    private SceneNode sceneNode;

    /** The currently associated light id */
    private int lightId = -1;


    /**
     * Constructor
     *
     * @param light
     *            The light to instance
     */

    public LightInstance(final Light light)
    {
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
     * Returns the scene node this light is connected to.
     *
     * @return The scene node
     */

    public SceneNode getSceneNode()
    {
        return this.sceneNode;
    }


    /**
     * Sets the scene node. This method is called when adding a light instance
     * to a scene node.
     *
     * @param sceneNode
     *            The scene node to connect the light to
     */

    void setSceneNode(final SceneNode sceneNode)
    {
        this.sceneNode = sceneNode;
    }


    /**
     * Sets the light id. Don't call this yourself, this is used automatically
     * during rendering.
     *
     * @param lightId
     *            The light id to set
     */

    public void setLightId(final int lightId)
    {
        this.lightId = lightId;
    }


    /**
     * Returns the currently associated light id. Returns 0 if no id is
     * associated.
     *
     * @return The light id or 0 if none set
     */

    public int getLightId()
    {
        return this.lightId;
    }
}
