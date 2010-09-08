/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.events;


/**
 * Listener interface for scene events.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface SceneListener
{
    /**
     * Called when scene is inserted into a view port.
     */

    public void sceneInsertedIntoViewport();


    /**
     * Called when scene is removed from a view port.
     */

    public void sceneRemovedFromViewport();
}
