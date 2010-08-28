/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.animation;

import de.ailis.threedee.math.Matrix4f;
import de.ailis.threedee.sampling.Sampler;
import de.ailis.threedee.scene.SceneNode;


/**
 * Transformation matrix animation.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class TransformAnimation extends Animation
{
    /** The sampler */
    private final Sampler<Matrix4f> sampler;


    /**
     * Constructs a new transformation animation.
     *
     * @param sampler
     *            The sampler
     */

    public TransformAnimation(final Sampler<Matrix4f> sampler)
    {
        this(null, sampler);
    }


    /**
     * Constructs a new transformation animation.
     *
     * @param id
     *            The animation id
     * @param sampler
     *            The sampler
     */

    public TransformAnimation(final String id, final Sampler<Matrix4f> sampler)
    {
        super(id);
        this.sampler = sampler;
    }


    /**
     * @see Animation#animate(SceneNode, float)
     */

    @Override
    public void animate(final SceneNode sceneNode, final float time)
    {
        sceneNode.getTransform().set(this.sampler.getSample(time));
    }
}
