/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.animation;

import de.ailis.threedee.scene.SceneNode;


/**
 * Animation interface.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface Animation
{
    /**
     * Animates the scene node.
     *
     * @param node
     *            The scene node to animate
     * @param delta
     *            The time delta
     */

    public void animate(SceneNode node, float delta);
}
