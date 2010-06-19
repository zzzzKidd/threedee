/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.collada.entities;

import java.io.Serializable;


/**
 * A scene
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Scene implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /** The instance visual scene */
    private InstanceVisualScene instanceVisualScene;


    /**
     * Returns the instance visual scene.
     *
     * @return The instance visual scene. May return empty if not specified
     */

    public InstanceVisualScene getInstanceVisualScene()
    {
        return this.instanceVisualScene;
    }


    /**
     * Sets the instance visual scene.
     *
     * @param instanceVisualScene
     *            The instance visual scene to set. Null for unspecified
     */

    public void setInstanceVisualScene(
            final InstanceVisualScene instanceVisualScene)
    {
        this.instanceVisualScene = instanceVisualScene;
    }
}
