/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.animation;

import de.ailis.threedee.scene.SceneNode;


/**
 * Transformation matrix animation.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AnimationGroup extends Animation
{
    /**
     * Constructs a new animation group with an id
     */

    public AnimationGroup()
    {
        this(null);
    }


    /**
     * Constructs a new animation group with the specified id.
     *
     * @param id
     *            The animation id
     */

    public AnimationGroup(final String id)
    {
        super(id);
    }


    /**
     * @see Animation#animate(SceneNode, float)
     */

    @Override
    public void animate(final SceneNode sceneNode, final float time)
    {
        // Empty
    }
}
