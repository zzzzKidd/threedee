/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.events;


/**
 * Adapter with empty implementations of the node listener.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class NodeAdapter implements NodeListener
{
    /**
     * @see de.ailis.threedee.events.NodeListener#nodeInserted()
     */

    @Override
    public void nodeInserted()
    {
        // Empty
    }


    /**
     * @see de.ailis.threedee.events.NodeListener#nodeInsertedIntoScene()
     */

    @Override
    public void nodeInsertedIntoScene()
    {
        // Empty
    }


    /**
     * @see de.ailis.threedee.events.NodeListener#nodeRemoved()
     */

    @Override
    public void nodeRemoved()
    {
        // Empty
    }


    /**
     * @see de.ailis.threedee.events.NodeListener#nodeRemovedFromScene()
     */

    @Override
    public void nodeRemovedFromScene()
    {
        // Empty
    }
}
