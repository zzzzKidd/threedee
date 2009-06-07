/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.threedee.scene.updater;

import de.ailis.threedee.scene.SceneNode;


/**
 * Interface which must be implemented by all node updater implementations.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public interface NodeUpdater
{
    /**
     * Updates the node with the specified time delta.
     * 
     * @param node
     *            The node to update
     * @param delta
     *            The time elapsed since the last scene update (in nanoseconds)
     */

    public void update(SceneNode node, final long delta);
}
