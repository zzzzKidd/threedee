/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.animation;

import de.ailis.threedee.math.Matrix4f;
import de.ailis.threedee.sampling.Sampler;
import de.ailis.threedee.scene.SceneNode;


/**
 * TransformAnimation
 *
 * @author k
 */

public class TransformAnimation implements Animation
{
    /** The sampler */
    private final Sampler<Matrix4f> sampler;

    /** The current time index */
    private float time;


    /**
     * Constructs a new transformation animation.
     *
     * @param sampler
     *            The sampler
     */

    public TransformAnimation(final Sampler<Matrix4f> sampler)
    {
        this.sampler = sampler;
        this.time = 0;
    }


    /**
     * @see Animation#animate(SceneNode, float)
     */

    @Override
    public void animate(final SceneNode sceneNode, final float delta)
    {
        this.time += delta;
        sceneNode.getTransform().set(this.sampler.getSample(this.time));
    }
}
