/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.threedee.scene.animation;

import de.ailis.gramath.Matrix4f;
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


    /**
     * @see de.ailis.threedee.scene.animation.Animation#trimIndex(float)
     */

    @Override
    public float trimIndex(final float index)
    {
        return this.sampler.trimInput(index);
    }


    /**
     * Returns the sampler.
     *
     * @return The sampler
     */

    public Sampler<Matrix4f> getSampler()
    {
        return this.sampler;
    }
}
