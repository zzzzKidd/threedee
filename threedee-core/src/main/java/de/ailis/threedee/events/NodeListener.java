/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.events;


/**
 * Listener interface for node events.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface NodeListener
{
    /**
     * Called when node is going to be removed from it's parent node.
     */

    public void nodeRemoved();


    /**
     * Called when node has been added to a parent node.
     */

    public void nodeInserted();


    /**
     * Called when node is going to be removed from a scene.
     */

    public void nodeRemovedFromScene();


    /**
     * Called when node is inserted into a scene.
     */

    public void nodeInsertedIntoScene();
}
