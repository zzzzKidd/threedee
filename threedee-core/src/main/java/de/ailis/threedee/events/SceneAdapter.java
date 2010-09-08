/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.events;


/**
 * Adapter with empty implementations of the scene listener.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SceneAdapter implements SceneListener
{
    /**
     * @see de.ailis.threedee.events.SceneListener#sceneInsertedIntoViewport()
     */

    @Override
    public void sceneInsertedIntoViewport()
    {
        // Empty
    }


    /**
     * @see de.ailis.threedee.events.SceneListener#sceneRemovedFromViewport()
     */

    @Override
    public void sceneRemovedFromViewport()
    {
        // Empty
    }
}
